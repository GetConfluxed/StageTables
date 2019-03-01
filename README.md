# Stage Tables [![](http://cf.way2muchnoise.eu/311363.svg)](https://minecraft.curseforge.com/projects/311363) [![](http://cf.way2muchnoise.eu/versions/311363.svg)](https://minecraft.curseforge.com/projects/311363)

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

## CraftTweaker Scripts

**Note:** This mod uses CraftTweaker and ZenScript for designing tables. These are advanced pack tools and a basic understanding and familiarity with them is considered a prerequisite for using this mod. If you don't understand something, please refer to the example script.

**Global Variable stageTables**
The global variable `stageTables` can be used anywhere in a script to create stage tables or get existing tables. This is done by using `createTable("name")` and `getTable("name")`. 

**StageTable**
The StageTable object has the following methods.
- **createEntry(String stage, int weight)** - Creates a new entry in the table.
- **getName** - Provides the name of the table.
- **getRandomEntry** - Gets a random entry in the table.
- **getTotalWeight** - Gets the total weight of the table.
- **getEntries** - Gives a list of all the entries.

**StageEntry**
The StageEntry object represents a value in the table. It has the following methods.
- **getStage** - Gets the name of the stage represented.
- **getWeight** - Gets the weight of the entry.
- **addStageCondition(String stage)** - Prevents access to the entry unless the player has the stage defined.
- **addCondition(CrTStageCondition condition) - Adds a ZenScript condition to the entry.

**CrTStageCondition**
This is a basic function. You are given an IPlayer and are expected to return a boolean of true or false, depending on if the player has access to it or not.

## Weighted Randomness
Weight is a form of probability system where the possible outcomes  have unequal odds of happening. The percent chance of any given outcome can be calculated by dividing the weight of the entry by the total weight of all entries on the table. This table demonstrates how a weighted system may look. 

| Entry Stage | Weight | % Chance |
|-------------|--------|----------|
| one         | 5      | 45.45%   |
| two         | 5      | 45.45%   |
| three       | 1      | 9%       |

## Example Script

```
// Basic imports used in this script
import crafttweaker.player.IPlayer;

// Creates a new stage table with the name exampleTable
val testTable = stageTables.createTable("exampleTable");

// Creates a new stage entry for stage "one" with a weight of 5.
val entryOne = testTable.createEntry("one", 5);

// Creates a new stage entry for stage "two" with a weight of 5.
val entryTwo = testTable.createEntry("two", 5);

// Prevent entryTwo from being selected unless the player has stage "four".
entryTwo.addStageCondition("four");

// Creates a new stage entry for stage "three" with a weight of 1.
val entryThree = testTable.createEntry("three", 1);

// Adds a custom condition to aquiring this entry using ZenScript.
// In this case, it's only valid if the player is in creative mode.
entryThree.addCondition(function(player as IPlayer) {
    return player.creative;
});
```