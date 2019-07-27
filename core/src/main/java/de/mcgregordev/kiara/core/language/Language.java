package de.mcgregordev.kiara.core.language;

import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.core.storage.Message;
import de.mcgregordev.kiara.core.storage.MessageStorage;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@Getter
public abstract class Language {

    // something like de_DE
    private final String name;
    private final File file;
    private final FileConfiguration config;
    private final MessageStorage messageStorage;

    public Language(Module module, String name, File file) {
        this.name = name;
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
        this.messageStorage = new MessageStorage(module);
        config.getConfigurationSection("message").getValues(true).forEach((s, o) -> {
            messageStorage.addMessage(s, new Message(s, (String) o));
        });
    }

}
