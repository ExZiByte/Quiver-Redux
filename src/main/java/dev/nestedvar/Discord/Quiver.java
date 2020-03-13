package dev.nestedvar.Discord;

import dev.nestedvar.Discord.Listeners.CommandHandler;
import dev.nestedvar.Discord.Listeners.Ready;
import dev.nestedvar.Discord.Utilities.*;
import dev.nestedvar.Discord.Websockets.WebsocketServer;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Quiver {
    static final Utilities utils = new Utilities();
    static final Locale locale = new Locale();
    Logging logging = new Logging();

    private Quiver() throws LoginException, RateLimitedException, InterruptedException {
        DefaultShardManagerBuilder quiver = new DefaultShardManagerBuilder().setToken(ConstantsConfig.get("token"));
        quiver.addEventListeners(
                new Ready(),
                new CommandHandler()
        );
        quiver.setShardsTotal(Integer.parseInt(ConstantsConfig.get("shardcount")));
        quiver.build();
        logging.info(Quiver.class, "Loading shard 0");
    }

    public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException, IOException, NoSuchAlgorithmException {
        utils.load();
        locale.load();
        new Quiver();
        //new WebsocketServer();
    }

}
