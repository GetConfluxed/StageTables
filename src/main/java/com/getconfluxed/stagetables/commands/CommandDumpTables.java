package com.getconfluxed.stagetables.commands;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import com.getconfluxed.stagetables.StageTablesMod;
import com.getconfluxed.stagetables.entry.StageEntry;
import com.getconfluxed.stagetables.table.StageTable;
import com.google.common.base.Charsets;

import net.darkhax.bookshelf.command.Command;
import net.darkhax.bookshelf.lib.Constants;
import net.darkhax.bookshelf.lib.TableBuilder;
import net.darkhax.bookshelf.lib.WeightedSelector.WeightedEntry;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.event.ClickEvent;

public class CommandDumpTables extends Command {

    private static final File dumpFile = new File("logs/stage_tables_dump.md");

    @Override
    public String getName () {

        return "dump";
    }

    @Override
    public int getRequiredPermissionLevel () {

        return 2;
    }

    @Override
    public String getUsage (ICommandSender sender) {

        return "/stagetable dump";
    }

    @Override
    public void execute (MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        final Map<String, StageTable> tables = StageTablesMod.getStageTables();
        final StringBuilder dumpText = new StringBuilder();

        dumpText.append("Dumping data for " + tables.size() + " stage tables." + Constants.NEW_LINE + Constants.NEW_LINE);

        for (final Entry<String, StageTable> tablesEntry : tables.entrySet()) {

            final int totalWeight = tablesEntry.getValue().getTotalWeight();
            dumpText.append("# Table: " + tablesEntry.getKey() + " Total Weight: " + totalWeight + Constants.NEW_LINE + Constants.NEW_LINE);

            final TableBuilder<StageEntry> dumpTable = new TableBuilder<>();

            dumpTable.addColumn("Stage", StageEntry::getStage);
            dumpTable.addColumn("Weight", StageEntry::getWeight);
            dumpTable.addColumn("% Chance", stageEntry -> String.format("%.2f", (float) stageEntry.getWeight() / (float) totalWeight * 100f) + "%");
            dumpTable.addColumn("Conditions", StageEntry::getConditionCount);

            for (final WeightedEntry<StageEntry> stageEntry : tablesEntry.getValue().getEntries()) {

                dumpTable.addEntry(stageEntry.getEntry());
            }

            dumpText.append(dumpTable.createString() + Constants.NEW_LINE + Constants.NEW_LINE);
        }

        try {

            FileUtils.write(dumpFile, dumpText.toString(), Charsets.UTF_8);
            final ITextComponent component = new TextComponentTranslation("stagetables.info.dump");
            component.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, dumpFile.getAbsolutePath()));
            component.getStyle().setUnderlined(true);
            sender.sendMessage(component);
        }

        catch (final IOException e) {

            sender.sendMessage(new TextComponentTranslation("stagetables.info.faileddump", e.getMessage()));
            StageTablesMod.LOG.catching(e);
        }
    }
}