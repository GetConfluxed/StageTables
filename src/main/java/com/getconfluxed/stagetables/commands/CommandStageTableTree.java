package com.getconfluxed.stagetables.commands;

import net.darkhax.bookshelf.command.CommandTree;
import net.minecraft.command.ICommandSender;

public class CommandStageTableTree extends CommandTree {

    public CommandStageTableTree () {

        this.addSubcommand(new CommandDumpTables());
        this.addSubcommand(new CommandAwardStage("award", false));
        this.addSubcommand(new CommandAwardStage("silentaward", true));
    }

    @Override
    public int getRequiredPermissionLevel () {

        return 0;
    }

    @Override
    public String getName () {

        return "stagetable";
    }

    @Override
    public String getUsage (ICommandSender sender) {

        return "/stagetable";
    }
}