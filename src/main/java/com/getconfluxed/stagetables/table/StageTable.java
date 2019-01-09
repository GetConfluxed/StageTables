package com.getconfluxed.stagetables.table;

import java.util.List;

import com.getconfluxed.stagetables.entry.StageEntry;

import net.darkhax.bookshelf.lib.WeightedSelector;
import net.darkhax.bookshelf.lib.WeightedSelector.WeightedEntry;
import net.minecraft.entity.player.EntityPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * A stage table is a table of stages that are given a weight. Players can then receive random
 * stages from this table if they meet the requirements of that stage.
 */
@ZenClass("mods.stagetables.StageTable")
public class StageTable {

    /**
     * Gets the name of the table. This must be unique, as it is used to register the table and
     * invoke it later using the command.
     */
    private final String name;

    /**
     * A weighted registry containing all the entries for this table.
     */
    private final WeightedSelector<StageEntry> entries;

    public StageTable (String name) {

        this.name = name;
        this.entries = new WeightedSelector<>();
    }

    /**
     * Gets the name for this table. This is the name used to invoke the table via command and
     * script.
     *
     * @return The name of the table.
     */
    @ZenMethod
    public String getName () {

        return this.name;
    }

    /**
     * Gets a random entry from the table.
     *
     * @return A random entry from the table based on weight. This can be null if it is empty.
     */
    @ZenMethod
    public StageEntry getRandomEntry () {

        return this.entries.getRandomEntry().getEntry();
    }

    @ZenMethod
    public int getTotalWeight () {

        return this.entries.updateTotal();
    }

    public List<WeightedEntry<StageEntry>> getEntries () {

        return this.entries.getEntries();
    }

    /**
     * Checks if a player can receive any entry from this table. Only one entry needs to be
     * valid for this to return true.
     *
     * @param player The player to test against.
     * @return Whether or not the table has at least one valid entry that the player can
     *         receive.
     */
    public boolean canPlayerUse (EntityPlayer player) {

        // Iterate all of the current entries
        for (final WeightedEntry<StageEntry> entry : this.entries.getEntries()) {

            // Check if the entry is valid
            if (entry.getEntry().canPlayerObtain(player)) {

                // If at least one entry is available, return true.
                return true;
            }
        }

        // Default is to assume false.
        return false;
    }

    @ZenMethod
    public StageEntry createEntry (String stage, int weight) {

        final StageEntry entry = new StageEntry(stage, weight);
        this.entries.addEntry(entry, weight);
        return entry;
    }
}