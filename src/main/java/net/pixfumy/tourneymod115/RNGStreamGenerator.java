package net.pixfumy.tourneymod115;

import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RNGStreamGenerator {
    protected HashMap<String, Long> rngSeeds = new HashMap();

    public RNGStreamGenerator(long worldSeed) {
        /*  abstracts some of the standardization into this HashMap. To add a new standardized source of RNG, put your entries
            into this map, use your world's RNGStreamGenerator in a mixin, and then update tellPlayerCurrentRates if you want to.
        */
        rngSeeds = new HashMap() {
            {
                //  the first 3 seeds use the same XOR salt as sharpie did in the original forge racemod FeelsStrongMan
                put("blazeRodSeed", worldSeed ^ 64711520272L);
                put("blazeSpawnSeed", worldSeed ^ 0x101100F11F01L);
                put("enderEyeSeed", worldSeed ^ 0x99A2B75BBL);
                put("flintSeed", worldSeed ^ 0xF110301001B2L);
            }
        };
    }

    public long getSeed(String id) {
        if (rngSeeds.containsKey(id)) {
            return rngSeeds.get(id);
        }
        return -1;
    }

    public long getAndUpdateSeed(String id) {
        if (rngSeeds.containsKey(id)) {
            long oldSeed = rngSeeds.get(id);
            Random random = new Random(oldSeed);
            long ret = Math.abs((int) oldSeed) % (int) Math.pow(16.0D, 4.0D);
            rngSeeds.put(id, random.nextLong());
            return ret;
        }
        return -1;
    }

    public void setSeed(String id, long seed) {
        rngSeeds.put(id, seed);
    }

    public Set<Map.Entry<String, Long>> entrySet() {
        return rngSeeds.entrySet();
    }

    public Set<String> keySet() {
        return rngSeeds.keySet();
    }

    public static void tellPlayerCurrentRates(World world) {
        long seed = world.getSeed();
        RNGStreamGenerator main = ((ILevelProperties)world.getLevelProperties()).getRNGStreamGenerator();
        RNGStreamGenerator dummy = new RNGStreamGenerator(seed);
        dummy.rngSeeds = new HashMap() {
            {
                put("blazeRodSeed", main.getSeed("blazeRodSeed"));
                put("enderPearlSeed", main.getSeed("enderPearlSeed"));
                put("flintSeed", main.getSeed("flintSeed"));
            }
        };
    }
}
