package net.pixfumy.tourneymod115;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SeedfinderUtil {
    public static void tellPlayerCurrentRates(ServerPlayerEntity player, ServerWorld world) {
        ServerWorld overWorld = world.getServer().getWorld(DimensionType.OVERWORLD);
        RNGStreamGenerator originalRNGStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRNGStreamGenerator();
        RNGStreamGenerator dummyRNGStreamGenerator = new RNGStreamGenerator(overWorld.getSeed());

        for (Map.Entry<String, Long> mapEntry : originalRNGStreamGenerator.entrySet()) {
            dummyRNGStreamGenerator.setSeed(mapEntry.getKey(), mapEntry.getValue());
        }

        AtomicInteger blazeRods = new AtomicInteger();
        int blazeRolls = 0;
        while (blazeRods.get() < 7) {
            BlazeEntity blazeEntity = new BlazeEntity(EntityType.BLAZE, overWorld); // holy spaghetti (mojang's fault for abstracting this too much)
            Identifier identifier = blazeEntity.getLootTable();
            LootTable lootTable = overWorld.getServer().getLootManager().getSupplier(identifier);
            LootContext.Builder builder = (new LootContext.Builder(overWorld)).setRandom(new Random(dummyRNGStreamGenerator.getAndUpdateSeed("blazeRodSeed")))
                    .put(LootContextParameters.THIS_ENTITY, blazeEntity)
                    .put(LootContextParameters.POSITION, new BlockPos(blazeEntity))
                    .put(LootContextParameters.DAMAGE_SOURCE, DamageSource.GENERIC)
                    .put(LootContextParameters.LAST_DAMAGE_PLAYER, player);
            lootTable.dropLimited(builder.build(LootContextTypes.ENTITY), itemStack -> blazeRods.addAndGet(itemStack.getCount()));
            blazeRolls++;
        }

        boolean flint = false;
        int flintRolls = 0;
        while (!flint) {
            BlockState gravelDefaultState = Blocks.GRAVEL.getDefaultState();
            LootContext.Builder builder = (new LootContext.Builder(world)).setRandom(new Random(dummyRNGStreamGenerator.getAndUpdateSeed("flintSeed")))
                    .put(LootContextParameters.POSITION, player.getBlockPos())
                    .put(LootContextParameters.POSITION, new BlockPos(0, 0, 0))
                    .put(LootContextParameters.TOOL, new ItemStack(Items.DIAMOND_SHOVEL));
            List<ItemStack> droppedItems = gravelDefaultState.getDroppedStacks(builder);
            if (droppedItems.stream().anyMatch(itemStack -> itemStack.getItem() == Items.FLINT)) {
                flint = true;
            }
            flintRolls++;
        }

        boolean eyeBreak = false;
        int eyeThrows = 0;
        while (!eyeBreak) {
            eyeBreak = new Random(dummyRNGStreamGenerator.getAndUpdateSeed("enderEyeSeed")).nextInt(5) == 0;
            eyeThrows++;
        }

        player.sendMessage(new LiteralText(""));
        player.sendMessage(new LiteralText(String.format("Current rates on this world: Blazes: %d/%d, First flint will be after mining %d gravel, First ender eye will break after %d throws,", blazeRods.get(), blazeRolls, flintRolls, eyeThrows)));
    }
}
