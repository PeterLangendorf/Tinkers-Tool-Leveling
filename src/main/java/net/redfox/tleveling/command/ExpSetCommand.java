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

public class ExpSetCommand {
	public ExpSetCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("tleveling").requires(commandSource -> commandSource.hasPermission(2)).then(Commands.literal("exp").then(Commands.literal("set").then(Commands.argument("value", IntegerArgumentType.integer()).executes(ExpSetCommand::setExp)))));
	}
	private static int setExp(CommandContext<CommandSourceStack> context) {
		CommandSourceStack source = context.getSource();
		Player player = source.getPlayer();
		if (player == null) {
			return -1;
		}
		ItemStack stack = player.getMainHandItem();
		if (!ToolExp.isLevelableTool(stack.getItem())) {
			source.sendFailure(Component.literal("This item does not have tool exp!"));
			return -1;
		}
		double value = IntegerArgumentType.getInteger(context, "value");
		source.sendSystemMessage(Component.literal("Set tool exp to " + value + "."));
		ToolExp.setToolExp(stack, value);
    ToolExp.checkForToolLevelUp(player, stack, ToolExp.getCurrentExp(stack), ToolExp.getToolLevel(stack));
		return Command.SINGLE_SUCCESS;
	}
}
