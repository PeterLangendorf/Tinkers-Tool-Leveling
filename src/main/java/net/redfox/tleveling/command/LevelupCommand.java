package net.redfox.tleveling.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.redfox.tleveling.leveling.ToolExp;

public class LevelupCommand {
	public LevelupCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("tleveling").requires(commandSource -> commandSource.hasPermission(2)).then(Commands.literal("levelup").executes(LevelupCommand::levelup)));
	}
	private static int levelup(CommandContext<CommandSourceStack> context) {
		CommandSourceStack source = context.getSource();
		Player player = source.getPlayer();
		if (player == null) {
			return -1;
		}
		ItemStack stack = player.getMainHandItem();
		if (!ToolExp.isLevelableTool(stack.getItem())) {
			source.sendFailure(Component.literal("This item cannot level up!"));
			return -1;
		}
		int requiredExp = (int) Math.round(ToolExp.getRequiredExp(ToolExp.getToolLevel(stack) + 1));
		ToolExp.setToolExp(stack, requiredExp);
    ToolExp.checkForToolLevelUp(player, stack, ToolExp.getCurrentExp(stack), ToolExp.getToolLevel(stack));
		source.sendSystemMessage(Component.literal("Tool successfully leveled up."));
		return Command.SINGLE_SUCCESS;
	}
}
