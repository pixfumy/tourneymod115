package net.pixfumy.tourneymod115.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.Feature;
import net.pixfumy.tourneymod115.TourneyMod115;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(MobEntity.class)
public class TempleMobSpawnMixin {
    @Inject(method = "canMobSpawn", at = @At("HEAD"), cancellable = true)
    private static void checkIfInTemple(EntityType<? extends MobEntity> type, IWorld world, SpawnType spawnType, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (Feature.DESERT_PYRAMID.isInsideStructure(world, pos)) {
            TourneyMod115.LOGGER.info("stopping mob spawn");
            cir.cancel();
        }
    }
}
