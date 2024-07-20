package com.hasoook.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.LiteralMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class NbtCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("nbt").requires(c -> c.hasPermissionLevel(4)).executes(c -> getNbt(c.getSource().getPlayer())));
    }

    private static int getNbt(PlayerEntity player) {
        ItemStack stack = player.getMainHandStack();
        // 获取玩家手中的物品
        if (stack.hasNbt()) {
            String s = stack.getNbt().toString();
            player.sendMessage(Text.of(s), false);
            return Command.SINGLE_SUCCESS;
        } else {
            player.sendMessage(Text.of("这个物品没有NBT"), false); {
            }
        }
        return 0;
    }

}
