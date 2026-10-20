package net.redfox.tleveling.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.redfox.tleveling.leveling.ToolExp;

public class LevelGetCommand {
	public LevelGetCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("tleveling").requires(commandSource -> commandSource.hasPermission(2)).then(Commands.literal("level").then(Commands.literal("get").executes((command) -> getExp(command.getSource())))));
	}
	private int getExp(CommandSourceStack source) {
		Player player = source.getPlayer();
		if (player == null) {
			return -1;
		}
		ItemStack stack = player.getMainHandItem();
		if (!ToolExp.isLevelableTool(stack.getItem())) {
			source.sendFailure(Component.literal("This item does not have a tool level!"));
			return -1;
		}
		int level = ToolExp.getToolLevel(stack);
		source.sendSystemMessage(Component.literal("This tool is currently level " + level));
		return level;
	}
}
