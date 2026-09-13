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
import java.util.ArrayList;
import java.util.List;

public class JsonConfigReader {
  public static final Runnable MODIFIERS = () -> writeJsonFile(getFilePathAsString("modifiers"), createDefaultJsonObject(createJsonArray(
      new JsonObjectBuilder().add("item", "tconstruct:pickaxe").add("source", createJsonArray("tleveling:break_block")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:experienced").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).add("message", "You sense a magical aura surround your tool... (+1 experienced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool pull you closer... (+1 magnetic)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:haste").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel the tool become lighter in your hands... (+1 haste)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:sledge_hammer").add("source", createJsonArray("tleveling:break_block")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:experienced").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).add("message", "You sense a magical aura surround your tool... (+1 experienced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool pull you closer... (+1 magnetic)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:haste").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel the tool become lighter in your hands... (+1 haste)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:vein_hammer").add("source", createJsonArray("tleveling:break_block")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:experienced").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).add("message", "You sense a magical aura surround your tool... (+1 experienced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool pull you closer... (+1 magnetic)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:haste").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel the tool become lighter in your hands... (+1 haste)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:mattock").add("source", createJsonArray("tleveling:break_block")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool pull you closer... (+1 magnetic)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:haste").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel the tool become lighter in your hands... (+1 haste)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:pickadze").add("source", createJsonArray("tleveling:break_block")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool pull you closer... (+1 magnetic)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:haste").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel the tool become lighter in your hands... (+1 haste)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:excavator").add("source", createJsonArray("tleveling:break_block")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool pull you closer... (+1 magnetic)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:haste").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel the tool become lighter in your hands... (+1 haste)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:hand_axe").add("source", createJsonArray("tleveling:damage_entity", "tleveling:break_block")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool pull you closer... (+1 magnetic)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:haste").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel the tool become lighter in your hands... (+1 haste)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:broad_axe").add("source", createJsonArray("tleveling:damage_entity", "tleveling:break_block")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool pull you closer... (+1 magnetic)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:haste").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel the tool become lighter in your hands... (+1 haste)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:kama").add("source", createJsonArray("tleveling:break_block", "tleveling:till", "tleveling:shear")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 1).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool pull you closer... (+1 magnetic)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 1).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:scythe").add("source", createJsonArray("tleveling:break_block", "tleveling:till", "tleveling:shear")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:magnetic").add("weight", 1).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool pull you closer... (+1 magnetic)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 1).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:dagger").add("source", createJsonArray("tleveling:damage_entity")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:experienced").add("weight", 4).add("max", 5).add("exceptions", createJsonArray()).add("message", "You sense a magical aura surround your tool... (+1 experienced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:fiery").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).add("message", "You tool feels much warmer than it was before... (+1 fiery)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:knockback").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool become more forceful... (+1 knockback)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:necrotic").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your sword feels oddly rotten... (+1 necrotic)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:severing").add("weight", 3).add("max", 3).add("exceptions", createJsonArray()).add("message", "You feel your tool become much sharper than before... (+1 severing)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:sweeping_edge").add("weight", 3).add("max", 3).add("exceptions", createJsonArray()).add("message", "You tool whistles through the air as it strikes... (+1 sweeping)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:pierce").add("weight", 2).add("max", 3).add("exceptions", createJsonArray()).add("message", "You feel your tool become very pointy... (+1 pierce)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:sharpness").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool has become much more deadly... (+1 sharpness)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:smite").add("weight", 4).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool sends the undead running... (+1 smite)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:swiftstrike").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool become much lighter and faster... (+1 swiftstrike)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:sword").add("source", createJsonArray("tleveling:damage_entity")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:experienced").add("weight", 4).add("max", 5).add("exceptions", createJsonArray()).add("message", "You sense a magical aura surround your tool... (+1 experienced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:fiery").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).add("message", "You tool feels much warmer than it was before... (+1 fiery)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:knockback").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool become more forceful... (+1 knockback)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:necrotic").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your sword feels oddly rotten... (+1 necrotic)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:severing").add("weight", 3).add("max", 3).add("exceptions", createJsonArray()).add("message", "You feel your tool become much sharper than before... (+1 severing)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:sweeping_edge").add("weight", 3).add("max", 3).add("exceptions", createJsonArray()).add("message", "You tool whistles through the air as it strikes... (+1 sweeping)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:pierce").add("weight", 2).add("max", 3).add("exceptions", createJsonArray()).add("message", "You feel your tool become very pointy... (+1 pierce)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:sharpness").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool has become much more deadly... (+1 sharpness)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:smite").add("weight", 4).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool sends the undead running... (+1 smite)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:swiftstrike").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool become much lighter and faster... (+1 swiftstrike)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:cleaver").add("source", createJsonArray("tleveling:damage_entity")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:experienced").add("weight", 4).add("max", 5).add("exceptions", createJsonArray()).add("message", "You sense a magical aura surround your tool... (+1 experienced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:fiery").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).add("message", "You tool feels much warmer than it was before... (+1 fiery)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:knockback").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool become more forceful... (+1 knockback)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:necrotic").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your sword feels oddly rotten... (+1 necrotic)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:severing").add("weight", 3).add("max", 3).add("exceptions", createJsonArray()).add("message", "You feel your tool become much sharper than before... (+1 severing)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:sweeping_edge").add("weight", 3).add("max", 3).add("exceptions", createJsonArray()).add("message", "You tool whistles through the air as it strikes... (+1 sweeping)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:pierce").add("weight", 2).add("max", 3).add("exceptions", createJsonArray()).add("message", "You feel your tool become very pointy... (+1 pierce)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:sharpness").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool has become much more deadly... (+1 sharpness)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:smite").add("weight", 4).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool sends the undead running... (+1 smite)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:swiftstrike").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool become much lighter and faster... (+1 swiftstrike)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:crossbow").add("source", createJsonArray("tleveling:ranged_damage_entity")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 4).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:power").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your projectiles seem especially powerful... (+1 power)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:punch").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your projectiles seem like they would pack a punch... (+1 punch)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:quick_charge").add("weight", 4).add("max", 4).add("exceptions", createJsonArray()).add("message", "Your crossbow seems especially easy to charge... (+1 quick_charge)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:longbow").add("source", createJsonArray("tleveling:ranged_damage_entity")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 4).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:power").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your projectiles seem especially powerful... (+1 power)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:punch").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your projectiles seem like they would pack a punch... (+1 punch)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:quick_charge").add("weight", 4).add("max", 4).add("exceptions", createJsonArray()).add("message", "Your crossbow seems especially easy to charge... (+1 quick_charge)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:javelin").add("source", createJsonArray("tleveling:damage_entity", "tleveling:ranged_damage_entity")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:experienced").add("weight", 4).add("max", 5).add("exceptions", createJsonArray()).add("message", "You sense a magical aura surround your tool... (+1 experienced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:fiery").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool feels much warmer than it was before... (+1 fiery)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:knockback").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool become more forceful... (+1 knockback)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:necrotic").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your javelin feels oddly rotten... (+1 necrotic)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:severing").add("weight", 3).add("max", 3).add("exceptions", createJsonArray()).add("message", "You feel your tool become much sharper than before... (+1 severing)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:pierce").add("weight", 2).add("max", 3).add("exceptions", createJsonArray()).add("message", "You feel your tool become very pointy... (+1 pierce)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:sharpness").add("weight", 5).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool has become much more deadly... (+1 sharpness)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:smite").add("weight", 4).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool sends the undead running... (+1 smite)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:swiftstrike").add("weight", 2).add("max", 5).add("exceptions", createJsonArray()).add("message", "You feel your tool become much lighter and faster... (+1 swiftstrike)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:trueshot").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your javelin flies straighter and truer... (+1 trueshot)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:quick_charge").add("weight", 3).add("max", 4).add("exceptions", createJsonArray()).add("message", "Your javelin feels much lighter to throw... (+1 quick charge)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:plate_helmet").add("source", createJsonArray("tleveling:take_damage")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:thorns").add("weight", 1).add("max", 3).add("exceptions", createJsonArray()).add("message", "Your armor has become very spikey... (+1 thorns)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:respiration").add("weight", 3).add("max", 3).add("exceptions", createJsonArray()).add("message", "You find it strangely easy to breathe in your armor... (+1 respiration)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:protection").add("weight", 5).add("max", 3).add("exceptions", createJsonArray()).add("message", "Your feel much safer in your armor now... (+1 protection)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:plate_chestplate").add("source", createJsonArray("tleveling:take_damage")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:thorns").add("weight", 1).add("max", 3).add("exceptions", createJsonArray()).add("message", "Your armor has become very spikey... (+1 thorns)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:protection").add("weight", 5).add("max", 3).add("exceptions", createJsonArray()).add("message", "Your feel much safer in your armor now... (+1 protection)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:plate_leggings").add("source", createJsonArray("tleveling:take_damage")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:thorns").add("weight", 1).add("max", 3).add("exceptions", createJsonArray()).add("message", "Your armor has become very spikey... (+1 thorns)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:protection").add("weight", 5).add("max", 3).add("exceptions", createJsonArray()).add("message", "Your feel much safer in your armor now... (+1 protection)").build()
      )).build(),
      new JsonObjectBuilder().add("item", "tconstruct:plate_boots").add("source", createJsonArray("tleveling:take_damage")).add("modifiers", createJsonArray(
          new JsonObjectBuilder().add("modifier", "tconstruct:reinforced").add("weight", 3).add("max", 5).add("exceptions", createJsonArray()).add("message", "Your tool seems much more durable now... (+1 reinforced)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:thorns").add("weight", 1).add("max", 3).add("exceptions", createJsonArray()).add("message", "Your armor has become very spikey... (+1 thorns)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:feather_falling").add("weight", 3).add("max", 4).add("exceptions", createJsonArray()).add("message", "Your boots feel as light as a feather... (+1 feather falling)").build(),
          new JsonObjectBuilder().add("modifier", "tconstruct:protection").add("weight", 5).add("max", 3).add("exceptions", createJsonArray()).add("message", "Your feel much safer in your armor now... (+1 protection)").build()
      )).build()
  )));

  public static JsonObject getOrCreateJsonFile(String fileName, Runnable create) {
    if (!new File(getFilePathAsString(fileName)).exists())
      create.run();
    return readJsonFile(fileName);
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
    File file = new File(getFilePathAsString(fileName));
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

  private static JsonArray createJsonArray(JsonObject... jsonObjects) {
    JsonArray jsonArray = new JsonArray();
    for (JsonObject jsonObject : jsonObjects) {
      jsonArray.add(jsonObject);
    }
    return jsonArray;
  }
  private static JsonArray createJsonArray(String first, String... strings) {
    JsonArray jsonArray = new JsonArray();
    for (String string : strings) {
      jsonArray.add(string);
    }
    jsonArray.add(first);
    return jsonArray;
  }

  private static String getFilePathAsString(String filePath) {
    return FMLPaths.CONFIGDIR.get().resolve(TinkersLeveling.MOD_ID + "/" + filePath + ".json").toString();
  }

  public static class JsonObjectBuilder {
    private final List<String> keys;
    private final List<Object> values;

    public JsonObjectBuilder() {
      this.keys = new ArrayList<>();
      this.values = new ArrayList<>();
    }

    public JsonObjectBuilder add(String key, Object value) {
      this.keys.add(key);
      this.values.add(value);
      return this;
    }

    public JsonObject build() {
      JsonObject jsonObject = new JsonObject();
      for (int i = 0; i < this.keys.size(); i++) {
        Object value = this.values.get(i);
        if (value instanceof String s) {
          jsonObject.addProperty(this.keys.get(i), s);
        } else if (value instanceof Number n) {
          jsonObject.addProperty(this.keys.get(i), n);
        } else if (value instanceof Boolean b) {
          jsonObject.addProperty(this.keys.get(i), b);
        } else if (value instanceof JsonElement e) {
          jsonObject.add(this.keys.get(i), e);
        }
      }

      return jsonObject;
    }
  }
}
