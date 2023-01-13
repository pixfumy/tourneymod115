package net.pixfumy.tourneymod115.mixin;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;

@Mixin(TradeOffers.class)
public class TradeOffersMixin {
    @Redirect(method = "method_16929", at = @At(value = "INVOKE", target = "Ljava/util/HashMap;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 6))
    private static Object redirectGlassBottle(HashMap instance, Object key, Object value) {
        return instance.put(VillagerProfession.CLERIC, new Int2ObjectOpenHashMap(ImmutableMap.of(1, new TradeOffers.Factory[]{new TradeOffers.BuyForOneEmeraldFactory(Items.ROTTEN_FLESH, 32, 16, 2), new TradeOffers.SellItemFactory(Items.REDSTONE, 1, 2, 1)}, 2, new TradeOffers.Factory[]{new TradeOffers.BuyForOneEmeraldFactory(Items.GOLD_INGOT, 3, 12, 10), new TradeOffers.SellItemFactory(Items.LAPIS_LAZULI, 1, 1, 5)}, 3, new TradeOffers.Factory[]{new TradeOffers.BuyForOneEmeraldFactory(Items.RABBIT_FOOT, 2, 12, 20), new TradeOffers.SellItemFactory(Blocks.GLOWSTONE, 4, 1, 12, 10)}, 4, new TradeOffers.Factory[]{new TradeOffers.BuyForOneEmeraldFactory(Items.GLASS_BOTTLE, 9, 12, 30), new TradeOffers.SellItemFactory(Items.ENDER_PEARL, 5, 1, 15)}, 5, new TradeOffers.Factory[]{new TradeOffers.BuyForOneEmeraldFactory(Items.NETHER_WART, 22, 12, 30), new TradeOffers.SellItemFactory(Items.EXPERIENCE_BOTTLE, 3, 1, 30)})));
    }
}
