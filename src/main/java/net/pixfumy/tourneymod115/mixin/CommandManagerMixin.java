package net.pixfumy.tourneymod115.mixin;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.GameModeCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.pixfumy.tourneymod115.command.RatesCommand;
import net.pixfumy.tourneymod115.command.UpdateRNGSeedCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CommandManager.class)
public class CommandManagerMixin {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/GameModeCommand;register(Lcom/mojang/brigadier/CommandDispatcher;)V"))
    private void registerCustomCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        GameModeCommand.register(dispatcher);
        RatesCommand.register(dispatcher);
        UpdateRNGSeedCommand.register(dispatcher);
    }
}
