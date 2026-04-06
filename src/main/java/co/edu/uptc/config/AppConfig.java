package co.edu.uptc.config;

import co.edu.uptc.loadProperties.PropertiesManager;

public class AppConfig {
    private static final PropertiesManager manager = new PropertiesManager();

    static {
        manager.loadInternal("config.properties");
        String externalPath = manager.get("app.external.config");
        if (externalPath != null) {
            manager.loadExternal(externalPath);
        }

    }

    public static String getProperty(String key) {
        return manager.get(key);
    }

    public static int getPropertyInt(String key) {
        return Integer.parseInt(getProperty(key));
    }

    public static double getPropertyDouble(String key) {
        return Double.parseDouble(getProperty(key));
    }
}
