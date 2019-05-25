package de.mcgregordev.kiara.core.command.subcommand;

import de.mcgregordev.kiara.core.command.SubCommand;
import de.mcgregordev.kiara.core.module.Module;
import org.bukkit.command.CommandSender;

public class LoadModuleSubCommand extends SubCommand {

    public LoadModuleSubCommand(String name) {
        super(name);
    }

    @Override
    public void onCommand(CommandSender commandSender, Module module, String[] args) {

    }
}
