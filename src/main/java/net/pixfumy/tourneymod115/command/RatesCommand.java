package net.pixfumy.tourneymod115.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.pixfumy.tourneymod115.SeedfinderUtil;

public class RatesCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = (LiteralArgumentBuilder) CommandManager
                .literal("rates")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                .executes(RatesCommand::execute);
        dispatcher.register(literalArgumentBuilder);
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        try {
            SeedfinderUtil.tellPlayerCurrentRates(context.getSource().getPlayer(), context.getSource().getWorld());
            return 0;
        } catch (CommandSyntaxException e) {
            return 1;
        }
    }
}
