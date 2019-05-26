package de.mcgregordev.kiara.core.storage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    private String key;
    private String value;

    public Message(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String translate(Object... arguments) {
        return String.format(value, arguments);
    }

}
