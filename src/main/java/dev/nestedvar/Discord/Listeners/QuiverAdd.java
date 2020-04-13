package dev.nestedvar.Discord.Listeners;

import com.mongodb.client.MongoCollection;
import dev.nestedvar.Discord.Utilities.Database;
import dev.nestedvar.Discord.Utilities.Logging;
import dev.nestedvar.Discord.Utilities.Utilities;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import java.util.List;

public class QuiverAdd extends ListenerAdapter {

    public void onGuildJoin(GuildJoinEvent event) {
        Utilities utils = new Utilities();
        Logging logging = new Logging();
        Database db = new Database();
        if (utils.isGuildAlreadyAvailable(event.getGuild())) {
            logging.info(this.getClass(), "Either I got readded to a server that I was previously in or Discord fired a GuildJoinEvent that was false");
        } else {
            db.connect();
            MongoCollection<Document> guilds = db.getCollection("guilds");

        }
    }
}
