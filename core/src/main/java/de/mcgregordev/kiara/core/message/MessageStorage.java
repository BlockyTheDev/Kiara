package de.mcgregordev.kiara.core.message;

import de.mcgregordev.kiara.core.module.Module;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
public class MessageStorage {

    private Module module;
    private YamlConfiguration messageConfig;
    private File file;
    private Map<String, Message> messageMap = new HashMap<>();

    public MessageStorage(Module module) {
        this.module = module;
        file = new File(module.getDataFolder(), "messages.yml");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        messageConfig = YamlConfiguration.loadConfiguration(file);
        messageConfig.getValues(true).forEach((s, o) -> messageMap.put(s, new Message(s, (String) o)));
    }

    public void addMessage(String key, Message message) {
        this.messageMap.put(key, message);
    }

    public String getMessage(String key, Object... arguments) {
        return messageMap.getOrDefault(key, new Message(key, "N/A ("+key+")")).translate(arguments);
    }

}
