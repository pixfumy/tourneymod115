package net.pixfumy.tourneymod115.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextMixin {
    /**
     * @author Pixfumy
     * @reason Take priority over any other mods changing this
     */
    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void getSplash(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("Tourney Mod 1.15");
    }
}
