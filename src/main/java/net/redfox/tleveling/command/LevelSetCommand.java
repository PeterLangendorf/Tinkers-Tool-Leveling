package net.redfox.tleveling.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.redfox.tleveling.leveling.ToolExp;

public class LevelSetCommand {
	public LevelSetCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("tleveling").requires(commandSource -> commandSource.hasPermission(2)).then(Commands.literal("level").then(Commands.literal("set").then(Commands.argument("value", IntegerArgumentType.integer(0)).executes(LevelSetCommand::setExp)))));
	}
	private static int setExp(CommandContext<CommandSourceStack> context) {
		CommandSourceStack source = context.getSource();
		Player player = source.getPlayer();
		if (player == null) {
			return -1;
		}
		ItemStack stack = player.getMainHandItem();
		if (!ToolExp.isLevelableTool(stack.getItem())) {
			source.sendFailure(Component.literal("This item does not have a tool level!"));
			return -1;
		}
		int value = IntegerArgumentType.getInteger(context, "value");
		source.sendSystemMessage(Component.literal("Set tool level to " + value + "."));
    ToolExp.setToolLevel(stack, value);
		return Command.SINGLE_SUCCESS;
	}
}
