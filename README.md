# Stage Tables

//TODO add CurseForge and discord badged.
//TODO add sponsorship information.
//TODO add documentation for all zen classes and methods.
//TODO provide an example script.

This mod provides a framework for creating weighted randomized tables of [Game Stages](https://minecraft.curseforge.com/projects/game-stages). If you do not know what a game stage is, it is highly recommended that you familiarize yourself with the Game Stage mod first. The basic idea of this mod is that modpacks can define a table of game stages. They can then use a command provided by this mod to award a player a random stage from that table. 

## Basic Terms
**Stage Table** - A named table that contains stage entries.    
**Stage Entry** - An entry in a stage table. Each entry has a stage to award, a weight value, and stage conditions.     
**Stage Condition** - A condition that must be met for a stage entry to be awardable to a given player.    

## Commands
- **/stagetable** - Provides a list of all commands.
- **/stagetable dump** - Generates a file containing information about all currently existing tables.
- **/stagetable award <player> <tablename>** Attempts to award a player with a random stage from the specified table.
- **/stagetable silentaward <player> <tablename>** Attempts to award a player with a random stage from the specified table. Without telling the player it was awarded.

## Weighted Randomness
Weight is a form of probability system where the possible outcomes  have unequal odds of happening. The percent chance of any given outcome can be calculated by dividing the weight of the entry by the total weight of all entries on the table. This table demonstrates how a weighted system may look. 

| Entry Stage | Weight | % Chance |
|-------------|--------|----------|
| one         | 5      | 45.45%   |
| two         | 5      | 45.45%   |
| three       | 1      | 9%       |