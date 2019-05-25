package de.mcgregordev.kiara.core.command.subcommand;

import de.mcgregordev.kiara.core.command.SubCommand;
import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.core.module.ModuleLoader;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class ListSubCommand extends SubCommand {

    public ListSubCommand(String s) {
        super(s);
    }

    @Override
    public void onCommand(CommandSender commandSender, Module module, String[] args) {
        List<Module> loadedModules = ModuleLoader.getInstance().getLoadedModules();
        commandSender.sendMessage("§aLoaded Modules: (" + loadedModules.size() + ")");
        commandSender.sendMessage(Arrays.toString(loadedModules.stream().map(Module::getName).toArray()));
        super.onCommand(commandSender, module, args);
    }
}
