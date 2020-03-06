package dev.nestedvar.Discord.Command;

import com.mongodb.lang.Nullable;
import dev.nestedvar.Discord.Command.commands.Set;
import dev.nestedvar.Discord.Utilities.Logging;
import dev.nestedvar.Discord.Utilities.Utilities;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    Utilities utils = new Utilities();
    private final Logging logging = new Logging();
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager(){
        addCommand(new Set());
    }

    private void addCommand(ICommand cmd){
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if(nameFound) {
            logging.error(CommandManager.class, String.format("A command with the name %s is already present", cmd.getName()));
        }

        commands.add(cmd);
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }
        return null;
    }

    public void handle(GuildMessageReceivedEvent event){
        String[] split = event.getMessage().getContentRaw().replaceFirst("(?i)" + Pattern.quote(utils.getPrefix(event.getGuild())), "").split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if(cmd != null){
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        }
    }
}
