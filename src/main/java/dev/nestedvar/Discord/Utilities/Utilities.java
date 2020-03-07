package dev.nestedvar.Discord.Utilities;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;

import static com.mongodb.client.model.Filters.eq;

public class Utilities {

    Logging logging = new Logging();
    Database db = new Database();

    public static HashMap<String, String> locales = new HashMap<>();
    public static HashMap<String, String> prefixes = new HashMap<>();
    public static HashMap<String, String> logChannels = new HashMap<>();
    public static HashMap<String, String> loggerMessages = new HashMap<>();

    public static HashMap<String, HashMap<String, String>> settings = new HashMap<String, HashMap<String, String>>();

    //Loads existing guild settings from database
    public void load() {
        db.connect();
        MongoCollection<Document> guilds = db.getCollection("guilds");

        FindIterable<Document> iterable = guilds.find();
        MongoCursor<Document> cursor = iterable.iterator();
        try {
            while (cursor.hasNext()) {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(cursor.next().toJson());
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("locale", obj.get("locale").toString());
                data.put("prefix", obj.get("prefix").toString());
                data.put("logChannel", obj.get("logChannelID").toString());
                data.put("joinLog", obj.get("joinLogID").toString());

                settings.put(obj.get("guildID").toString(), data);
            }
        } catch (ParseException ex) {
            logging.error(Utilities.class, ex.toString());
        } finally {
            settings.forEach((k, v) -> {
                System.out.format("Key: %s, Value: %s", k, v);
            });
            cursor.close();
        }

    }

    // Return prefix for guild
    public String getPrefix(Guild guild) {
        HashMap<String, String> map = settings.get(guild.getId());
        return map.get("prefix");
    }


    public void setPrefix(Guild guild, String prefix) {
        db.connect();
        MongoCollection<Document> guilds = db.getCollection("guilds");
        Bson filter = new Document("prefix", getPrefix(guild));
        Bson newPrefix = new Document("prefix", prefix);
        Bson updatePrefix = new Document("$set", newPrefix);
        guilds.updateOne(filter, updatePrefix);
        db.close();

        HashMap<String, String> map = settings.get(guild.getId());
        map.put("prefix", prefix);
        settings.put(guild.getId(), map);

    }

    // Return locale for guild
    public String getLocale(Guild guild) {
        HashMap<String, String> map = settings.get(guild.getId());
        return map.get("locale");
    }

    // Return default locale
    public String getDefaultLocale(Guild guild) {
        /*if (guild.getRegionRaw().equals("us-east")) return "en_US";
        else if (guild.getRegionRaw().equals("us-west")) return "en_US";
        else if (guild.getRegionRaw().equals("us-central")) return "en_US";
        else if (guild.getRegionRaw().equals("us-south")) return "en_US";
        else if (guild.getRegionRaw().equals("brazil")) return "pt_BR";
        else if (guild.getRegionRaw().equals("europe")) return "en_GB";
        else if (guild.getRegionRaw().equals("india")) return "hi_IN";
        else if (guild.getRegionRaw().equals("hongkong")) return "en_HK";
        else if (guild.getRegionRaw().equals("japan")) return "ja_JP";
        else if (guild.getRegionRaw().equals("russia")) return "ru_RU";
        else if (guild.getRegionRaw().equals("singapore")) return "en_SG";
        else if (guild.getRegionRaw().equals("southafrica")) return "en_ZA";
        else if (guild.getRegionRaw().equals("sydney")) return "en_AU";
        else return "en_US";*/
        switch (guild.getRegionRaw()) {
            case "us-east":
                return "en_US";
            case "us-west":
                return "en_US";
            case "us-south":
                return "en_US";
            case "us-central":
                return "en_US";
            case "brazil":
                return "pt_BR";
            case "europe":
                return "en_GB";
            case "india":
                return "hi_IN";
            case "hongkong":
                return "en_HK";
            case "japan":
                return "ja_JP";
            case "russia":
                return "ru_RU";
            case "singapore":
                return "en_SG";
            case "southafrica":
                return "en_ZA";
            case "sydney":
                return "en_AU";
            default:
                return "en_US";
        }
    }

    // Set locale for guild
    public void setLocale(Guild guild, String locale) {
        db.connect();
        MongoCollection<Document> guilds = db.getCollection("guilds");
        Bson filter = new Document("locale", getLocale(guild));
        Bson newLocale = new Document("locale", locale);
        Bson updateLocale = new Document("$set", newLocale);
        guilds.updateOne(filter, updateLocale);
        db.close();
        HashMap<String, String> map = settings.get(guild.getId());
        map.put("locale", locale);
        settings.put(guild.getId(), map);
    }

    public TextChannel getLogChannel(Guild guild){
        HashMap<String, String> map = settings.get(guild.getId());
        if(map.get("logChannel").equalsIgnoreCase("null")){
            return guild.getDefaultChannel();
        }
        else {
            return  guild.getTextChannelById(map.get("logChannel"));
        }
    }

    public void setLogChannel(Guild guild, String channelID){
        db.connect();
        MongoCollection<Document> guilds = db.getCollection("guilds");
        Bson filter = new Document("logChannelID", getLogChannel(guild));
        Bson newLogChannel = new Document("logChannelID", channelID);
        Bson updateLogChannel = new Document("$set", newLogChannel);
        guilds.updateOne(filter, updateLogChannel);
        db.close();
        HashMap<String, String> map = settings.get(guild.getId());
        map.put("logChannel", channelID);
        settings.put(guild.getId(), map);
    }

    public TextChannel getJoinLogChannel(Guild guild){
        HashMap<String, String> map = settings.get(guild.getId());
        if(map.get("joinLog").equalsIgnoreCase("null")){
            return guild.getDefaultChannel();
        }
        else {
            return guild.getTextChannelById(map.get("joinLog"));
        }

    }

    public void setJoinLogChannel(Guild guild, String channelID){
        db.connect();
        MongoCollection<Document> guilds = db.getCollection("guilds");
        Bson filter = new Document("joinLogID", getLogChannel(guild));
        Bson newLogChannel = new Document("joinLogID", channelID);
        Bson updateLogChannel = new Document("$set", newLogChannel);
        guilds.updateOne(filter, updateLogChannel);
        db.close();
        HashMap<String, String> map = settings.get(guild.getId());
        map.put("joinLog", channelID);
        settings.put(guild.getId(), map);
    }

    public String getSelfAvatar(GuildMessageReceivedEvent event) {
        return event.getJDA().getSelfUser().getAvatarUrl();
    }

    public String getSelfAvatar(MessageReceivedEvent event) {
        return event.getJDA().getSelfUser().getAvatarUrl();
    }

    public String getSelfAvatar(GuildJoinEvent event) {
        return event.getJDA().getSelfUser().getAvatarUrl();
    }

}
