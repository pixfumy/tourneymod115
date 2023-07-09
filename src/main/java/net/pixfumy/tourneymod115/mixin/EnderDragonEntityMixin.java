package net.pixfumy.tourneymod115.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.PhaseManager;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: DuncanRuns
 */
@Mixin(EnderDragonEntity.class)
public abstract class EnderDragonEntityMixin
        extends MobEntity {
    private static final List<PhaseType<?>> perchPhases = Arrays.asList(
            PhaseType.LANDING_APPROACH,
            PhaseType.LANDING,
            PhaseType.TAKEOFF,
            PhaseType.SITTING_FLAMING,
            PhaseType.SITTING_SCANNING,
            PhaseType.SITTING_ATTACKING,
            PhaseType.DYING
    );
    private static final boolean DO_NATURAL_PERCHES = true;
    @Shadow
    @Final
    private PhaseManager phaseManager;
    private int perchCounter = 0;
    private int perchTime = -1;

    protected EnderDragonEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void setPerchTime(EntityType entityType, World world, CallbackInfo ci) {
        if (!world.isClient) {
            perchTime = 3600; // 3 minute forced perch even though the dragon's random is standardized, just to avoid any Illumina 1.14 tournament nonsense
            this.random.setSeed(world.getServer().getWorld(DimensionType.OVERWORLD).getSeed() ^ -8422260959436812016L);
        }
    }

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void tickMixin(CallbackInfo ci) {
        if (!world.isClient()) {
            if (perchCounter == -1) {
                // Wait for dragon to finish perching
                if (!perchPhases.contains(phaseManager.getCurrent().getType())) {
                    perchCounter++;
                }
            } else {
                // Wait for 150 seconds then perch
                if (perchCounter >= perchTime) {
                    // 150 seconds is done
                    phaseManager.setPhase(PhaseType.LANDING_APPROACH);
                    perchCounter = -1;
                } else {
                    // 150 seconds is not done
                    if (perchPhases.contains(phaseManager.getCurrent().getType())) {
                        // Perch before 150 seconds
                        if (!DO_NATURAL_PERCHES) {
                            phaseManager.setPhase(PhaseType.HOLDING_PATTERN);
                            perchCounter++;
                        }
                    } else {
                        perchCounter++;
                    }
                }
            }
        }
    }
}