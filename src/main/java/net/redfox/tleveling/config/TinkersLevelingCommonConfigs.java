package net.redfox.tleveling.config;


import net.minecraftforge.common.ForgeConfigSpec;

public class TinkersLevelingCommonConfigs {
  public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
  public static final ForgeConfigSpec SPEC;

  public static final ForgeConfigSpec.ConfigValue<Double> PICKAXE_EXP_MULTIPLIER;
  public static final ForgeConfigSpec.ConfigValue<Double> KILL_EXP_MULTIPLIER;
  public static final ForgeConfigSpec.ConfigValue<Double> ARMOR_EXP_MULTIPLIER;
  public static final ForgeConfigSpec.ConfigValue<Integer> LEVELUP_EXP_REQUIRED;
  public static final ForgeConfigSpec.ConfigValue<Double> LEVELUP_INCREASE;
  public static final ForgeConfigSpec.ConfigValue<Double> RANDOMNESS_FACTOR;
  public static final ForgeConfigSpec.ConfigValue<Double> EXP_LOSS_ON_REPLACE;
  public static final ForgeConfigSpec.ConfigValue<Double> BASE_EXPERIENCE_GAIN;
  public static final ForgeConfigSpec.ConfigValue<Integer> LEVEL_BONUS_MODIFIER;
  public static final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_CUSTOM_EXP;

  static {
    BUILDER.push("Common Configs for Tinker's Tool Leveling");

    PICKAXE_EXP_MULTIPLIER = BUILDER.comment("The multiplier for the exp granted to a mining tool after each block break. Default is 1.0")
        .defineInRange("blockBreakMultiplier", 1d, 0, Integer.MAX_VALUE);
    KILL_EXP_MULTIPLIER = BUILDER.comment("The multiplier for the exp granted to a melee weapon after each block break. Default is 1.0")
        .defineInRange("damageEntityMultiplier", 1d, 0, Integer.MAX_VALUE);
    ARMOR_EXP_MULTIPLIER = BUILDER.comment("The multiplier for the exp granted to an armor piece after each block break. Default is 1.0")
        .defineInRange("armorHitMultiplier", 1d, 0, Integer.MAX_VALUE);
    LEVEL_BONUS_MODIFIER = BUILDER.comment("The amount of levels between each bonus modifier. Set to 0 to disable bonus modifiers. Default is 3")
        .define("modifierLevelGap", 3);
    LEVELUP_EXP_REQUIRED = BUILDER.comment("The base exp required to level up a tool. Default is 200")
        .defineInRange("levelupExpRequired", 200, 1, Integer.MAX_VALUE);
    LEVELUP_INCREASE = BUILDER.comment("The exponential increase between tool exp levels. Default is 2.0")
        .defineInRange("levelupExpIncrease", 2d, 0, Integer.MAX_VALUE);
    RANDOMNESS_FACTOR = BUILDER.comment("The randomness factor when gaining exp.")
        .comment("A value of 1 means that the tool's exp gain will be randomized from +-100% of what it was")
        .comment("For example, if a tool gains 5 exp with a randomness factor of 1, it can gain anywhere from 0 to 10 exp.")
        .defineInRange("randomnessFactor", 0.5, 0, 1);
    EXP_LOSS_ON_REPLACE = BUILDER.comment("The amount the current exp is divided by when a part of the tool is replaced.")
        .comment("A value of 0 means that it will be reset to 0")
            .comment("A value of 1 means that the exp will not be affected")
        .comment("A value of > 1 is what the exp will be divded by when a tool part is replaced")
            .comment("For example, a value of 2 means that if a tool has 50 exp it will have 25 exp after a part is replaced")
                .defineInRange("expLossOnReplace", 2d, 0, Integer.MAX_VALUE);
    BASE_EXPERIENCE_GAIN = BUILDER.comment("Base experience gain, from which other values modify or replace")
                    .defineInRange("baseExpGain", 5d, 0, 100);
    ENABLE_CUSTOM_EXP = BUILDER.comment("Enables custom experience values for certain actions.")
            .comment("Customizable events and filters are found in \"config/tleveling/custom_experience\"")
            .define("enableCustomExpValues",false);

    BUILDER.pop();
    SPEC = BUILDER.build();
  }
}