package net.darkhax.stagetables.entry;

import java.util.ArrayList;
import java.util.List;

import crafttweaker.api.minecraft.CraftTweakerMC;
import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.stagetables.condition.CrTStageCondition;
import net.darkhax.stagetables.condition.StageCondition;
import net.minecraft.entity.player.EntityPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * This class is used to define an entry within a stage table.
 */
@ZenClass("mods.stagetables.StageEntry")
public class StageEntry {

    /**
     * The stage represented by the table. This is not guaranteed to be unique between entries.
     */
    private final String stage;

    /**
     * The weight of this specific entry.
     */
    private final int weight;

    /**
     * All conditions for this entry.
     */
    private final List<StageCondition> conditions;

    public StageEntry (String stage, int weight) {

        this.stage = stage;
        this.weight = weight;
        this.conditions = new ArrayList<>();
    }

    /**
     * Gets the stage that will be unlocked by this entry.
     *
     * @return The stage to unlock.
     */
    @ZenMethod
    public String getStage () {

        return this.stage;
    }

    /**
     * Gets the weight of the entry.
     *
     * @return The weight of this entry.
     */
    @ZenMethod
    public int getWeight () {

        return this.weight;
    }

    /**
     * Adds a zenscript defined condition to the stage entry.
     *
     * @param condition A zenscript defined function.
     */
    @ZenMethod
    public void addCondition (CrTStageCondition condition) {

        this.conditions.add(player -> condition.testCondition(CraftTweakerMC.getIPlayer(player)));
    }

    /**
     * Adds a stage requirement to the entry.
     *
     * @param stage The required stage to use.
     */
    @ZenMethod
    public void addStageCondition (String stage) {

        this.conditions.add(player -> GameStageHelper.hasStage(player, stage));
    }

    /**
     * Checks if a player can obtain this entry. For this to happen, they must not already have
     * the stage, and all conditions must return true when tested.
     *
     * @param player The player to test against.
     * @return Whether or not the player can unlock this entry.
     */
    public boolean canPlayerObtain (EntityPlayer player) {

        // Prevent the player from getting the entry if they already have it.
        if (GameStageHelper.hasStage(player, this.getStage())) {

            return false;
        }

        // Make sure the player has all of the conditions.
        for (final StageCondition condition : this.conditions) {

            if (!condition.testCondition(player)) {

                return false;
            }
        }

        // If all conditions are fine, assume they can obtain it.
        return true;
    }

    /**
     * Gets the total number of conditions.
     *
     * @return The number of conditions.
     */
    public int getConditionCount () {

        return this.conditions.size();
    }
}