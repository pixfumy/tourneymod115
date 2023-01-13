package net.pixfumy.tourneymod115.mixin;

import com.mojang.datafixers.DataFixer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelProperties;
import net.pixfumy.tourneymod115.ILevelProperties;
import net.pixfumy.tourneymod115.RNGStreamGenerator;
import net.pixfumy.tourneymod115.TourneyMod115;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LevelProperties.class)
public abstract class LevelPropertiesMixin implements ILevelProperties {
    @Shadow
    private long randomSeed;
    private final RNGStreamGenerator rngStreamGenerator = new RNGStreamGenerator();
    @Override
    public RNGStreamGenerator getRNGStreamGenerator(){
        return this.rngStreamGenerator;
    }

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/nbt/CompoundTag;Lcom/mojang/datafixers/DataFixer;ILnet/minecraft/nbt/CompoundTag;)V")
    public void initInject(CompoundTag compoundTag, DataFixer dataFixer, int i, CompoundTag compoundTag2, CallbackInfo ci) {
        rngStreamGenerator.init(this.randomSeed);
        for (Map.Entry<String, Long> pair: rngStreamGenerator.entrySet()) {
            String id = pair.getKey();
            if (compoundTag.contains(id)) {
                long seed = compoundTag.getLong(id);
                if (seed != 0) {
                    rngStreamGenerator.setSeed(id, seed);
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "updateProperties")
    private void writeSeedsToNBT(CompoundTag worldNBT, CompoundTag playerNBT, CallbackInfo ci) {
        for (Map.Entry<String, Long> pair: rngStreamGenerator.entrySet()) {
            String id = pair.getKey();
            worldNBT.putLong(id, rngStreamGenerator.getSeed(id));
        }
    }
}
