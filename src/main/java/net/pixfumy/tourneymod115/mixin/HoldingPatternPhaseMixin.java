package net.pixfumy.tourneymod115.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.boss.dragon.phase.HoldingPatternPhase;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.Random;

@Mixin(HoldingPatternPhase.class)
public class HoldingPatternPhaseMixin {
    @Redirect(method = "method_6841", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 3))
    private int redirectOneEighth(Random instance, int bound) {
        return new Random(MinecraftClient.getInstance().getServer().getWorld(DimensionType.OVERWORLD).getSeed() ^ 1241081035495L).nextInt(16);
    }

    @Redirect(method = "method_6842", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F"))
    private float redirectTargetHeight(Random instance) {
        return new Random(MinecraftClient.getInstance().getServer().getWorld(DimensionType.OVERWORLD).getSeed() ^ 7342345681031578012L).nextFloat() / 5;
    }
}