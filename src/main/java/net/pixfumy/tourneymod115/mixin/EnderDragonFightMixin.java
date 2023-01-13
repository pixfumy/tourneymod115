package net.pixfumy.tourneymod115.mixin;

import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnderDragonFight.class)
public class EnderDragonFightMixin {
    @Redirect(method = "createDragon", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/boss/dragon/EnderDragonEntity;refreshPositionAndAngles(DDDFF)V"))
    private void chooseAngle(EnderDragonEntity instance, double x, double y, double z, float yaw, float pitch) {
        instance.refreshPositionAndAngles(x, y, z, (instance.world.getServer().getWorld(DimensionType.OVERWORLD).getSeed() ^ 579723852935L) % 360, pitch);
    }
}
