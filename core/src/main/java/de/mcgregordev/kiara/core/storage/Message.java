package de.mcgregordev.kiara.core.storage;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Getter
@Setter
public class Message {

    private String key;
    private String value;

    public Message(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String translate(Variable... arguments) {
        String finalMessage = value;
        for (Variable argument : arguments) {
            finalMessage = finalMessage.replace("%" + argument.getVariableName() + "%", String.valueOf(argument.getValue()));
        }
        return ChatColor.translateAlternateColorCodes('&', finalMessage);
    }

}
