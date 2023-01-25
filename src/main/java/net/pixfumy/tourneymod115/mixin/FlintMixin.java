package net.pixfumy.tourneymod115.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.pixfumy.tourneymod115.ILevelProperties;
import net.pixfumy.tourneymod115.RNGStreamGenerator;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(Block.class)
public class FlintMixin {
    @Inject(method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;",
    at = @At("HEAD"), cancellable = true)
    private static void dropFlint(BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack stack, CallbackInfoReturnable<List<ItemStack>> cir) {
        RNGStreamGenerator rngStreamGenerator = ((ILevelProperties)world.getServer().getWorld(DimensionType.OVERWORLD).getLevelProperties()).getRNGStreamGenerator();
        LootContext.Builder builder = (new LootContext.Builder(world)).setRandom(new Random(rngStreamGenerator.getAndUpdateSeed("flintSeed"))).put(LootContextParameters.POSITION, pos).put(LootContextParameters.TOOL, stack);
        cir.setReturnValue(state.getDroppedStacks(builder));
    }
}
