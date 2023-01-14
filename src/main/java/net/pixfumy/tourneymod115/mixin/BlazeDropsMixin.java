package net.pixfumy.tourneymod115.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.pixfumy.tourneymod115.ILevelProperties;
import net.pixfumy.tourneymod115.RNGStreamGenerator;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(BlazeEntity.class)
public class BlazeDropsMixin extends HostileEntity {
    protected BlazeDropsMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        RNGStreamGenerator rngStreamGenerator = ((ILevelProperties)this.getEntityWorld().getServer().getWorld(DimensionType.OVERWORLD).getLevelProperties()).getRNGStreamGenerator();
        Identifier identifier = this.getLootTable();
        LootTable lootTable = this.world.getServer().getLootManager().getSupplier(identifier);
        LootContext.Builder builder = (new LootContext.Builder((ServerWorld)this.world)).setRandom(new Random(rngStreamGenerator.getAndUpdateSeed("blazeRodSeed"))).put(LootContextParameters.THIS_ENTITY, this).put(LootContextParameters.POSITION, new BlockPos(this)).put(LootContextParameters.DAMAGE_SOURCE, source).putNullable(LootContextParameters.KILLER_ENTITY, source.getAttacker()).putNullable(LootContextParameters.DIRECT_KILLER_ENTITY, source.getSource());
        if (causedByPlayer && this.attackingPlayer != null) {
            builder = builder.put(LootContextParameters.LAST_DAMAGE_PLAYER, this.attackingPlayer).setLuck(this.attackingPlayer.getLuck());
        }
        lootTable.dropLimited(builder.build(LootContextTypes.ENTITY), this::dropStack);
    }
}