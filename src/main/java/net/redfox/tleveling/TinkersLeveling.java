package net.redfox.tleveling;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.redfox.tleveling.config.TinkersLevelingCommonConfigs;
import net.redfox.tleveling.leveling.ToolExp;
import net.redfox.tleveling.util.ModSounds;
import org.slf4j.Logger;

@Mod(TinkersLeveling.MOD_ID)
public class TinkersLeveling {
	public static final String MOD_ID = "tleveling";
  public static final String VERSION = "-v1.2.0";
	public static final Logger LOGGER = LogUtils.getLogger();

	public TinkersLeveling(FMLJavaModLoadingContext context) {
		IEventBus modEventBus = context.getModEventBus();

    ModSounds.register(modEventBus);

		context.registerConfig(ModConfig.Type.COMMON, TinkersLevelingCommonConfigs.SPEC, "tleveling/common.toml");

		modEventBus.addListener(this::commonSetup);
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		ToolExp.init();
	}
}