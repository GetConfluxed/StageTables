package com.getconfluxed.stagetables.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.getconfluxed.stagetables.StageTablesMod;
import com.getconfluxed.stagetables.entry.StageEntry;
import com.getconfluxed.stagetables.table.StageTable;

import net.darkhax.bookshelf.command.Command;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandAwardStage extends Command {

    private final String command;
    private final boolean silent;

    public CommandAwardStage (String command, boolean siletn) {

        this.command = command;
        this.silent = siletn;
    }

    @Override
    public String getName () {

        return this.command;
    }

    @Override
    public int getRequiredPermissionLevel () {

        return 2;
    }

    @Override
    public String getUsage (ICommandSender sender) {

        return "/stagetables " + this.command + " <player> <table>";
    }

    @Override
    public void execute (MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        System.out.println(args.length);
        if (args.length == 2) {

            final List<EntityPlayerMP> players = getPlayers(server, sender, args[0]);

            final StageTable table = StageTablesMod.instance.getTable(args[1]);

            if (table == null) {

                throw new WrongUsageException("No table found for name " + args[1]);
            }

            for (final EntityPlayerMP player : players) {

                if (table.canPlayerUse(player)) {

                    boolean hasAwardedSomething = false;

                    while (!hasAwardedSomething) {

                        final StageEntry entry = table.getRandomEntry();

                        if (entry.canPlayerObtain(player)) {

                            hasAwardedSomething = true;
                            GameStageHelper.addStage(player, entry.getStage());
                            GameStageHelper.syncPlayer(player);

                            if (!this.silent) {

                                player.sendMessage(new TextComponentTranslation("commands.gamestage.add.target", entry.getStage()));

                                if (player != sender) {
                                    sender.sendMessage(new TextComponentTranslation("commands.gamestage.add.sender", entry.getStage(), player.getDisplayNameString()));
                                }
                            }
                        }
                    }
                }

                else if (!this.silent) {

                    player.sendMessage(new TextComponentTranslation("stagetables.info.ineligable", args[1]));
                }
            }
        }
        else {
            throw new WrongUsageException(this.getUsage(sender));
        }
    }

    @Override
    public boolean isUsernameIndex (String[] args, int index) {

        return index == 0;
    }

    @Override
    public List<String> getTabCompletions (MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {

        return args.length == 2 ? new ArrayList<>(StageTablesMod.getStageTables().keySet()) : Collections.<String> emptyList();
    }
}