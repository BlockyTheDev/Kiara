package de.mcgregordev.kiara.core.language;

import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.core.storage.Message;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Language {

    // something like de_DE
    private final String name;
    private final File file;
    private final FileConfiguration config;
    private Map<String, Message> messages = new HashMap<>();

    public Language(String name, File file) {
        this.name = name;
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
        config.getValues(true).forEach((s, o) -> messages.put(s, new Message(s, (String) o)));
    }

}
