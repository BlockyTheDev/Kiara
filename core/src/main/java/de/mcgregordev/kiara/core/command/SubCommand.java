package de.mcgregordev.kiara.core.command;

import de.mcgregordev.kiara.core.module.Module;
import lombok.Getter;
import org.bukkit.command.CommandSender;

@Getter
public abstract class SubCommand {

    private String[] subCommands;

    public SubCommand(String... subCommands) {
        this.subCommands = subCommands;
        ModuleCommand.addSubCommand(this);
    }

    /**
     * @param commandSender the user of the /module command
     * @param module null if module isnt needed
     * @param args all arguments after the module selector
     */
    public abstract void onCommand(CommandSender commandSender, Module module, String[] args);

}
