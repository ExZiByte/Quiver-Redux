package dev.nestedvar.Discord;

import dev.nestedvar.Discord.Listeners.CommandHandler;
import dev.nestedvar.Discord.Listeners.Ready;
import dev.nestedvar.Discord.Utilities.ConstantsConfig;
import dev.nestedvar.Discord.Utilities.Utilities;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Quiver {
    static final Utilities utils = new Utilities();
    private final Logger LOGGER = LoggerFactory.getLogger(Quiver.class);
    private Quiver() throws LoginException {
        DefaultShardManagerBuilder quiver = new DefaultShardManagerBuilder().setToken(ConstantsConfig.get("token"));
        quiver.addEventListeners(
                new Ready(),
                new CommandHandler()
        );
        quiver.setShardsTotal(Integer.parseInt(ConstantsConfig.get("shardcount")));
        quiver.build();
        LOGGER.info("Loading shard 0");
    }

    public static void main(String[] args) throws LoginException {
        new Quiver();
        utils.load();
    }

}
