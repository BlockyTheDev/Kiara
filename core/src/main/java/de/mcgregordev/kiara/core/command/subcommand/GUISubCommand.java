package de.mcgregordev.kiara.core.command.subcommand;

import de.mcgregordev.kiara.core.CorePlugin;
import de.mcgregordev.kiara.core.command.SubCommand;
import de.mcgregordev.kiara.core.gui.GUI;
import de.mcgregordev.kiara.core.gui.InteractableItem;
import de.mcgregordev.kiara.core.item.ItemBuilder;
import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.core.module.ModuleLoader;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class GUISubCommand extends SubCommand {

    public GUISubCommand(String s) {
        super(s);
    }

    @Override
    public void onCommand(CommandSender commandSender, Module module, String[] args) {
        Player player = (Player) commandSender;
        GUI gui = new GUI(6, "§aModules");
        gui.close(o -> {
            gui.unregisterListener();
        });

        for (Module loadedModule : ModuleLoader.getInstance().getLoadedModules()) {
            gui.addItem(new InteractableItem(new ItemBuilder(Material.COMMAND).setDisplayName("§a" + loadedModule.getName()).setLore("§e" + loadedModule.getAuthor(), "§c" + Arrays.toString(loadedModule.getDependencies())))
                    .click(event -> {
                        loadedModule.onDisable();
                        player.sendMessage("Disabling " + loadedModule.getName());
                    }));
        }

        player.openInventory(gui.getInventory());

    }

}
