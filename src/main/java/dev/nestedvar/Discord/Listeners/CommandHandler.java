package dev.nestedvar.Discord.Listeners;

import dev.nestedvar.Discord.Command.CommandManager;
import dev.nestedvar.Discord.Utilities.ConstantsConfig;
import dev.nestedvar.Discord.Utilities.Logging;
import dev.nestedvar.Discord.Utilities.Utilities;
import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {

    private final CommandManager manager = new CommandManager();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Utilities utils = new Utilities();
        Logging logging = new Logging();
        User user = event.getAuthor();

        if (user.isBot() || event.isWebhookMessage()) return;

        String prefix = utils.getPrefix(event.getGuild());
        String raw = event.getMessage().getContentRaw();

        if (raw.startsWith(prefix)) {
            manager.handle(event);
        }
    }
}

