package de.mcgregordev.kiara.core.language;

import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.core.storage.Variable;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Getter
public class LanguageHandler {

    private Map<String, Language> languageMap = new HashMap<>();
    private Language defaultLanguage;

    public LanguageHandler(Module module) {
        for (File locale : getResourceFolderFiles("locale")) {
            Language language = new Language(locale.getName(), locale);
            if (locale.getName().equals("en-EN")) {
                defaultLanguage = language;
            }
            languageMap.put(locale.getName(), language);
        }
    }

    public String getMessage(Player player, String value, Variable... variables) {
        String locale = player.getLocale();
        Language language = languageMap.getOrDefault(locale, defaultLanguage);
        return language.getMessages().get(value).translate(variables);
    }


    private static File[] getResourceFolderFiles(String folder) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(folder);
        String path = url.getPath();
        return new File(path).listFiles();
    }

}
