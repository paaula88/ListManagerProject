package co.edu.uptc.config;

import co.edu.uptc.loadProperties.PropertiesManager;

public class Language {
    private static final PropertiesManager manager = new PropertiesManager();

    static {
        manager.loadInternal("messages_es.properties");
        String lang = AppConfig.getProperty("app.language");
        if (lang != null) {
            manager.loadExternal("resources/messages_" + lang + ".properties");
        }
    }

    public static String get(String key) {
        return manager.get(key);
    }
}
