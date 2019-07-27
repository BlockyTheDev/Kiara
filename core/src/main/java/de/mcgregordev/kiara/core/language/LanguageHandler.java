package de.mcgregordev.kiara.core.language;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Getter
public class LanguageHandler {

    private Map<String, Language> languageMap = new HashMap<>();
    private Language defaultLanguage;

    public LanguageHandler() {

    }

    public String getMessage(Player player, String value) {
        String locale = player.getLocale();
        Language language = languageMap.getOrDefault(locale, defaultLanguage);
        return language.getMessageStorage().getMessage(value);
    }

}
