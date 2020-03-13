package dev.nestedvar.Discord.Command.commands;

import dev.nestedvar.Discord.Command.CommandContext;
import dev.nestedvar.Discord.Command.ICommand;
import dev.nestedvar.Discord.Utilities.Locale;
import dev.nestedvar.Discord.Utilities.Logging;
import dev.nestedvar.Discord.Utilities.Utilities;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class List implements ICommand {

    final Locale locale = new Locale();

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Message msg = ctx.getMessage();
        final Member member = ctx.getMember();
        final java.util.List<String> args = ctx.getArgs();
        final Guild guild = ctx.getGuild();
        final Utilities utils = new Utilities();
        final Logging logging = new Logging();

        if(args.size() < 1){
            channel.sendMessage(locale.getMessage(guild, "list", "optionsInsufficientArguments")).queue();
            return;
        }
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getHelp(CommandContext ctx) {
        return locale.getMessage(ctx.getGuild(), "list", "help");
    }
}
