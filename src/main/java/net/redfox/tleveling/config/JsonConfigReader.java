package net.redfox.tleveling.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraftforge.fml.loading.FMLPaths;
import net.redfox.tleveling.TinkersLeveling;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JsonConfigReader {
  public static JsonObject getOrCreateJsonFile(String fileName) {
    ensureLegacyFoldersMigrated();
    if (!new File(getFilePathAsString(fileName)).exists()) {
      writeJsonFile(getFilePathAsString(fileName), readDefaultResource(fileName));
    }
    return readJsonFile(fileName);
  }

  public static JsonArray getOrCreateJsonDirectory(String directoryName) {
    ensureLegacyFoldersMigrated();

    File directory = new File(getDirectoryPathAsString(directoryName));
    List<File> files = collectJsonFiles(directory);
    if (files.isEmpty()) {
      writeItemJsonFiles(directoryName, readDefaultResource(directoryName).getAsJsonArray("values"));
      files = collectJsonFiles(directory);
    }

    JsonArray merged = new JsonArray();
    files.sort(Comparator.comparing(File::getPath));
    for (File file : files) {
      JsonObject json = readJsonFile(file);
      if (json != null && json.has("values")) {
        merged.addAll(json.getAsJsonArray("values"));
      }
    }
    return merged;
  }

  private static JsonObject readDefaultResource(String name) {
    String resourcePath = "/data/tleveling/leveling/" + name + ".json";
    try (InputStream stream = JsonConfigReader.class.getResourceAsStream(resourcePath)) {
      if (stream == null) {
        throw new IllegalStateException("Missing bundled default resource: " + resourcePath);
      }
      try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
        return new Gson().fromJson(reader, JsonObject.class);
      }
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read bundled default resource: " + resourcePath, e);
    }
  }

  private static List<File> collectJsonFiles(File directory) {
    List<File> result = new ArrayList<>();
    File[] children = directory.isDirectory() ? directory.listFiles() : null;
    if (children == null) return result;
    for (File child : children) {
      if (child.isDirectory()) {
        result.addAll(collectJsonFiles(child));
      } else if (child.getName().endsWith(".json")) {
        result.add(child);
      }
    }
    return result;
  }

  private static boolean legacyFoldersMigrated = false;

  private static void ensureLegacyFoldersMigrated() {
    if (legacyFoldersMigrated) return;
    legacyFoldersMigrated = true;

    File configDir = FMLPaths.CONFIGDIR.get().toFile();
    String currentFolderName = getModDirectory().getName();
    File[] siblings = configDir.listFiles(File::isDirectory);
    if (siblings == null) return;

    List<File> legacyDirs = new ArrayList<>();
    for (File sibling : siblings) {
      String name = sibling.getName();
      if (name.equals(currentFolderName)) continue;
      if (name.equals(TinkersLeveling.MOD_ID) || name.startsWith(TinkersLeveling.MOD_ID + "-v")) {
        legacyDirs.add(sibling);
      }
    }

    // Most recently used legacy folder first, so its values win over an even older folder's for the same item.
    legacyDirs.sort(Comparator.comparingLong(File::lastModified).reversed());
    for (File legacyDir : legacyDirs) {
      migrateLegacyConfigFolder(legacyDir);
    }
  }

  private static void migrateLegacyConfigFolder(File legacyDir) {
    File[] children = legacyDir.listFiles();
    if (children != null) {
      for (File child : children) {
        if (child.getName().equals("modifiers") || child.getName().equals("modifiers.json")) {
          migrateLegacyModifiersSource(child);
        } else {
          copyMissingRecursively(child, new File(getModDirectory(), child.getName()));
        }
      }
    }

    deleteRecursively(legacyDir);
    TinkersLeveling.LOGGER.info("Migrated legacy config folder '{}' into '{}' and removed it", legacyDir.getName(), getModDirectory().getName());
  }

  private static void migrateLegacyModifiersSource(File source) {
    List<File> sourceFiles = source.isFile() ? List.of(source) : collectJsonFiles(source);
    for (File file : sourceFiles) {
      JsonObject json = readJsonFile(file);
      if (json == null || !json.has("values")) continue;
      for (JsonElement entry : json.getAsJsonArray("values")) {
        String item = entry.getAsJsonObject().get("item").getAsString();
        String[] namespaceAndPath = item.split(":", 2);
        String namespace = namespaceAndPath[0];
        String path = namespaceAndPath.length > 1 ? namespaceAndPath[1] : namespaceAndPath[0];

        File target = new File(getFilePathAsString("modifiers/" + namespace + "/" + path));
        if (!target.exists()) {
          JsonArray wrapped = new JsonArray();
          wrapped.add(entry);
          writeJsonFile(target.getPath(), createDefaultJsonObject(wrapped));
        }
      }
    }
  }

  private static void copyMissingRecursively(File source, File destination) {
    if (source.isDirectory()) {
      File[] children = source.listFiles();
      if (children != null) {
        for (File child : children) {
          copyMissingRecursively(child, new File(destination, child.getName()));
        }
      }
      return;
    }

    if (destination.exists()) return;
    try {
      if (destination.getParentFile() != null) {
        destination.getParentFile().mkdirs();
      }
      Files.copy(source.toPath(), destination.toPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void deleteRecursively(File file) {
    File[] children = file.listFiles();
    if (children != null) {
      for (File child : children) {
        deleteRecursively(child);
      }
    }
    file.delete();
  }

  private static void writeItemJsonFiles(String directoryName, JsonArray entries) {
    for (JsonElement entry : entries) {
      String item = entry.getAsJsonObject().get("item").getAsString();
      String[] namespaceAndPath = item.split(":", 2);
      String namespace = namespaceAndPath[0];
      String path = namespaceAndPath.length > 1 ? namespaceAndPath[1] : namespaceAndPath[0];

      JsonArray values = new JsonArray();
      values.add(entry);
      writeJsonFile(getFilePathAsString(directoryName + "/" + namespace + "/" + path), createDefaultJsonObject(values));
    }
  }

  private static void writeJsonFile(String fileName, JsonObject jsonObject) {
    File file = new File(fileName);
    try {
      if (file.getParentFile() != null) {
        file.getParentFile().mkdirs();
      }

      file.createNewFile();

      try (FileWriter writer = new FileWriter(file)) {
        new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject, writer);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static JsonObject readJsonFile(String fileName) {
    return readJsonFile(new File(getFilePathAsString(fileName)));
  }

  private static JsonObject readJsonFile(File file) {
    Gson gson = new Gson();
    try (FileReader reader = new FileReader(file)) {
      return gson.fromJson(reader, JsonObject.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static JsonObject createDefaultJsonObject(JsonElement jsonElement) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.add("values", jsonElement);
    return jsonObject;
  }

  private static File getModDirectory() {
    return FMLPaths.CONFIGDIR.get().resolve(TinkersLeveling.MOD_ID + TinkersLeveling.VERSION).toFile();
  }

  private static String getFilePathAsString(String filePath) {
    return new File(getModDirectory(), filePath + ".json").getPath();
  }

  private static String getDirectoryPathAsString(String directoryPath) {
    return new File(getModDirectory(), directoryPath).getPath();
  }
}
