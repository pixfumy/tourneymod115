# Tourney Mod 1.15
This is a general tournament mod for 1.15. Implementation is based on Seedcycle, MSL and Pre1.9 Racemod. This is unofficially a self-fork of MSM3 Mod.

## Features

- Seed-standardized blaze rod drops, zero cycles (angle, 1/8, target node height), flint rates, ender eye breaks
- Zero cycles are also possible on more seeds. Node height ranges from 0-5 blocks above the minimum height, instead of the vanilla range of 0-20. the 1/8 chance of the dragon picking a horizontal node is now 1/16.
- Removes 1/3, pearl trade is guaranteed when leveling up a cleric
- Golems always drop 4 iron
- Dragon always perches after 2.5 minutes after entering end, resets on relog.
- Removes mob spawns in temples during the daytime 


## Modifying RNG

The seeds are stored in the world file, in level.dat. This can be modified using any NBT editor (I use intellij with the Minecraft Developer Plugin to edit these).
