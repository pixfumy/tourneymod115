package net.pixfumy.tourneymod115.mixin;

import net.minecraft.entity.mob.ElderGuardianEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mixin(ElderGuardianEntity.class)
public class ElderGuardianEntityMixin {
    @Redirect(method = "mobTick", at = @At(value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;"))
    private Iterator noPlayersToDong(List instance) {
        return new ArrayList().iterator();
    }
}
