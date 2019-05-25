package de.mcgregordev.kiara.core.command;

import de.mcgregordev.kiara.core.command.subcommand.ListSubCommand;
import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.core.module.ModuleLoader;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModuleCommand extends Command {

    public static void addSubCommand(SubCommand subCommand) {
        subCommands.add(subCommand);
    }

    private static List<SubCommand> subCommands = new ArrayList<>();

    public ModuleCommand() {
        super("module");
        setPermission("command.module");
        new ListSubCommand("list");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        for (SubCommand subCommand : subCommands) {
            for (String command : subCommand.getSubCommands()) {
                if (command.equalsIgnoreCase(strings[0])) {
                    subCommand.onCommand(commandSender, null, (String[]) ArrayUtils.subarray(strings, 0, 1));
                    return true;
                }
            }
        }
        for (SubCommand command : subCommands) {
            commandSender.sendMessage("/module " + command.getSubCommands()[0]);
        }
        return false;
    }
}
