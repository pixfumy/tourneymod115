# Tourney Mod 1.15
This is a general tournament mod for 1.15. Implementation is based on Seedcycle, MSL and Pre1.9 Racemod. This is a self-fork of [Tourney Mod 1.16](https://github.com/pixfumy/tourneymod116).

## Features

- Seed-standardized blaze rod drops, blaze spawn positions and cooldown between cycles, dragon fights (zero, node choices, perch), flint rates, ender eye breaks
- Zero cycles are also possible on more seeds. Node height ranges from 0-5 blocks above the minimum height, instead of the vanilla range of 0-20. the 1/8 chance of the dragon picking a horizontal node is now 1/16.
- Removes 1/3, pearl trade is guaranteed when leveling up a cleric
- Golems always drop 4 iron
- No mining fatigue from elder guardians
- Dragon always perches after 3 minutes after entering end, resets on relog.
- Removes mob spawns in temples

## Commands for seedfinders

If you have cheats enabled, there are two commands you can use to modify RNG. Make sure you disable cheats before giving a world save to runners.

`/rates` will print the RNG information of a world, in the following format:

![image](https://github.com/pixfumy/tourneymod115/assets/95588510/8f7896e0-9c1e-435d-8315-d6615999492a)

`/resetRNGSeed [blazeRodSeed|blazeSpawnSeed|enderEyeSeed|flintSeed]` will reroll a specific source of RNG specified in the argument. For example, if you notice that `/rates` is giving you bad blaze rates, you can run `/resetRNGSeed blazeRodSeed` and run `/rates` again until you're happy with them. Note that the seeds auto-complete:

![image](https://github.com/pixfumy/tourneymod115/assets/95588510/c572a8cc-2fd4-48fa-9e74-e174ebaa8f56)




