package net.redfox.tleveling.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import net.redfox.tleveling.TinkersLeveling;
import net.redfox.tleveling.command.ExpGetCommand;
import net.redfox.tleveling.command.ExpSetCommand;
import net.redfox.tleveling.command.LevelGetCommand;
import net.redfox.tleveling.command.LevelSetCommand;
import net.redfox.tleveling.command.LevelupCommand;
import net.redfox.tleveling.config.TinkersLevelingCommonConfigs;
import net.redfox.tleveling.leveling.ToolExp;
import net.redfox.tleveling.leveling.TooltipDisplay;
import net.redfox.tleveling.util.ModKeybinds;
import slimeknights.tconstruct.library.events.TinkerToolEvent;
import slimeknights.tconstruct.tools.entity.ThrownTool;

public class ForgeEvents {
  private static boolean skip = false;
  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
  public static class Client {
    @SubscribeEvent
    public static void onTooltipDisplay(ItemTooltipEvent event) {
      if (event.getEntity() == null) return;
      if (event.getToolTip() == null || event.getToolTip().isEmpty()) return;
      if (!ToolExp.isLevelableTool(event.getItemStack().getItem())) return;

      long window = Minecraft.getInstance().getWindow().getWindow();
      if (InputConstants.isKeyDown(window, InputConstants.KEY_LSHIFT) || InputConstants.isKeyDown(window, InputConstants.KEY_RSHIFT) || InputConstants.isKeyDown(window, InputConstants.KEY_LCONTROL) || InputConstants.isKeyDown(window, InputConstants.KEY_RCONTROL)) return;
      if (InputConstants.isKeyDown(window, ModKeybinds.SHOW_EXP_KEY.getKey().getValue())) {
        TooltipDisplay.insertExpTooltip(event.getToolTip(), event.getItemStack());
      } else {
        TooltipDisplay.insertAltTooltip(event.getToolTip());
      }
    }
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
      new ExpSetCommand(event.getDispatcher());
      new ExpGetCommand(event.getDispatcher());
      new LevelGetCommand(event.getDispatcher());
      new LevelSetCommand(event.getDispatcher());
      new LevelupCommand(event.getDispatcher());

      ConfigCommand.register(event.getDispatcher());
    }
  }

  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = TinkersLeveling.MOD_ID)
  public static class Server {
    @SubscribeEvent
    public static void onBreakBlock(BlockEvent.BreakEvent event) {
      if (event.getLevel().isClientSide()) return;
      if (isDuplicateEvent()) return;
      if (!ToolExp.BREAK_BLOCKS.contains(event.getPlayer().getMainHandItem().getItem())) return;
      Double exp = -1d;
      if (TinkersLevelingCommonConfigs.ENABLE_CUSTOM_EXP.get()) {
        exp = event.getState().getTags().map(TagKey::location)
                .map(rl -> ToolExp.BLOCK_EXPERIENCE.getOrDefault(rl, -1d))
                .reduce(Double::max).orElse(0d);
        if (exp == 0) return;
      }

      if (exp == -1d) exp = TinkersLevelingCommonConfigs.BASE_EXPERIENCE_GAIN.get();
      ToolExp.addExpToTool(event.getPlayer(), event.getPlayer().getMainHandItem(), exp * TinkersLevelingCommonConfigs.PICKAXE_EXP_MULTIPLIER.get());
    }
    @SubscribeEvent
    public static void onLivingEntityHurt(LivingHurtEvent event) {
      float amount = Math.min(100, event.getAmount());
      if (amount <= 0) return;
      if (event.getEntity().level().isClientSide()) return;
      if (event.getEntity() instanceof Player player) {
        if (ToolExp.TAKE_DAMAGES.contains(player.getItemBySlot(EquipmentSlot.HEAD).getItem())) {
          ToolExp.addExpToTool(player, player.getItemBySlot(EquipmentSlot.HEAD), amount * TinkersLevelingCommonConfigs.ARMOR_EXP_MULTIPLIER.get());
        }
        if (ToolExp.TAKE_DAMAGES.contains(player.getItemBySlot(EquipmentSlot.CHEST).getItem())) {
          ToolExp.addExpToTool(player, player.getItemBySlot(EquipmentSlot.CHEST), amount * TinkersLevelingCommonConfigs.ARMOR_EXP_MULTIPLIER.get());
        }
        if (ToolExp.TAKE_DAMAGES.contains(player.getItemBySlot(EquipmentSlot.LEGS).getItem())) {
          ToolExp.addExpToTool(player, player.getItemBySlot(EquipmentSlot.LEGS), amount * TinkersLevelingCommonConfigs.ARMOR_EXP_MULTIPLIER.get());
        }
        if (ToolExp.TAKE_DAMAGES.contains(player.getItemBySlot(EquipmentSlot.FEET).getItem())) {
          ToolExp.addExpToTool(player, player.getItemBySlot(EquipmentSlot.FEET), amount * TinkersLevelingCommonConfigs.ARMOR_EXP_MULTIPLIER.get());
        }
      }
    }
    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
      if (event.getEntity().level().isClientSide()) return;
      if (!ToolExp.DAMAGE_ENTITIES.contains(event.getEntity().getMainHandItem().getItem())) return;

      Double exp = -1d;
      if (TinkersLevelingCommonConfigs.ENABLE_CUSTOM_EXP.get()){
        String target = event.getTarget().getEncodeId();
        exp = ToolExp.MELEE_EXPERIENCE.getOrDefault(target, -1d);
        if (exp == 0) return;
      }

      if (exp == -1d) exp = TinkersLevelingCommonConfigs.BASE_EXPERIENCE_GAIN.get();
      ToolExp.addExpToTool(event.getEntity(), event.getEntity().getMainHandItem(), exp * TinkersLevelingCommonConfigs.KILL_EXP_MULTIPLIER.get());
    }

    @SubscribeEvent
    public static void onTillFarmland(BlockEvent.BlockToolModificationEvent event) {
      if (event.isSimulated()) return;
      if (!event.getToolAction().equals(ToolActions.HOE_TILL)) return;
      if (!ToolExp.TILLS.contains(event.getHeldItemStack().getItem())) return;

      ToolExp.addExpToTool(event.getPlayer(), event.getHeldItemStack(), TinkersLevelingCommonConfigs.BASE_EXPERIENCE_GAIN.get());
    }
    @SubscribeEvent
    public static void onShear(TinkerToolEvent.ToolShearEvent event) {
      ToolExp.addExpToTool(event.getPlayer(), event.getStack(), TinkersLevelingCommonConfigs.BASE_EXPERIENCE_GAIN.get());
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
      if (event.getEntity().level().isClientSide()) return;
      if (!event.getRayTraceResult().getType().equals(HitResult.Type.ENTITY)) return;

      if (event.getProjectile().getOwner() instanceof Player player) {
        ItemStack tool = player.getMainHandItem();
        if (event.getProjectile() instanceof ThrownTool thrownTool && tool.getItem() != thrownTool.getDisplayTool().getItem()) {
          tool = thrownTool.getDisplayTool();
        }

        if (!ToolExp.RANGED_DAMAGE_ENTITIES.contains(tool.getItem())) return;

        Double exp = -1d;
        if (TinkersLevelingCommonConfigs.ENABLE_CUSTOM_EXP.get()) {
          String target = ((EntityHitResult) event.getRayTraceResult()).getEntity().getEncodeId();
          exp = ToolExp.MELEE_EXPERIENCE.getOrDefault(target, -1d);
          if (exp == 0) return;
        }

        if (exp == -1d) exp = TinkersLevelingCommonConfigs.BASE_EXPERIENCE_GAIN.get();

        ToolExp.addExpToTool(player, tool, exp * TinkersLevelingCommonConfigs.KILL_EXP_MULTIPLIER.get());
      }
    }
  }
  public static boolean isDuplicateEvent() {
    skip = !skip;
    return !skip;
  }
}