package net.darkhax.stagetables.condition;

import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * This functional interface is used to define a condition for a stage table. If a condition
 * returns false, the player will not be able to unlock the stage. This is the CrT version
 * though.
 */
@FunctionalInterface
@ZenClass("mods.stagetables.CrTStageCondition")
public interface CrTStageCondition {

    /**
     * Tests if the player can pass this condition. Conditions should be deterministic and not
     * use random elements.
     *
     * @param player The player to test against.
     * @return Whether or not the player passes the condition.
     */
    boolean testCondition (IPlayer player);
}