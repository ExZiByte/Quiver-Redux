package dev.nestedvar.Discord.Utilities;

import io.github.cdimascio.dotenv.Dotenv;

public class ConstantsConfig {

    private static final Dotenv env = Dotenv.load();

    public static String get(String key) {
        return env.get(key.toUpperCase());
    }

}
