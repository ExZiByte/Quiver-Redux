package dev.nestedvar.Discord.Command.commands;

import dev.nestedvar.Discord.Command.CommandContext;
import dev.nestedvar.Discord.Command.ICommand;
import dev.nestedvar.Discord.Utilities.Utilities;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Set implements ICommand {


    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Message msg = ctx.getMessage();
        final Member member = ctx.getMember();
        final List<String> args = ctx.getArgs();
        final Utilities utils = new Utilities();
        final TextChannel logChannel = utils.getLogChannel(ctx.getGuild());

        if(args.size() < 1){
            channel.sendMessage("You need to tell me what setting you want to configure!").queue();
            return;
        }
        if(args.size() < 2){
            if(args.get(0).equalsIgnoreCase("prefix")){
                channel.sendMessage("I need to know what you would like to use as this server's prefix").queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            }else if(args.get(0).equalsIgnoreCase("locale")){
                channel.sendMessage("I need to know what locale you wanted to use for future messages").queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            } else if(args.get(0).equalsIgnoreCase("logchannel") || args.get(0).equalsIgnoreCase("logging") || args.get(0).equalsIgnoreCase("log")){
                channel.sendMessage("I need to know which channel you want to use as a logging channel").queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            } else if(args.get(0).equalsIgnoreCase("joinlog") || args.get(0).equalsIgnoreCase("join") || args.get(0).equalsIgnoreCase("joinlogging")){
                channel.sendMessage("I need to know which channel you want to use as a join/leave log").queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            }
        }

        if(args.get(0).equalsIgnoreCase("prefix")){
            utils.setPrefix(ctx.getGuild(), args.get(1));
            channel.sendMessageFormat("Successfully set the prefix for all future commands to `%s`", args.get(1)).queue(
                    (message) -> msg.delete().queueAfter(15, TimeUnit.SECONDS)
            );
            logChannel.sendMessageFormat("%s has set the prefix to `%s`", member.getAsMention(), args.get(1)).queue();
            return;
        }

        if(args.get(0).equalsIgnoreCase("locale")){
            utils.setLocale(ctx.getGuild(), args.get(1));
            channel.sendMessageFormat("Successfully set the locale to `%s`", args.get(1)).queue(
                    (message) -> msg.delete().queueAfter(15, TimeUnit.SECONDS)

            );
            logChannel.sendMessageFormat("%s has set the locale to `%s`", member.getAsMention(), args.get(1)).queue();
            return;
        }

        if(args.get(0).equalsIgnoreCase("logchannel") || args.get(0).equalsIgnoreCase("logging") || args.get(0).equalsIgnoreCase("log")){
            utils.setLogChannel(ctx.getGuild(), msg.getMentionedChannels().get(0));
            channel.sendMessageFormat("Successfully set the logging channel to %s", msg.getMentionedChannels().get(0).getAsMention()).queue(
                    (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
            );
            logChannel.sendMessageFormat("%s has set the log channel to %s", member.getAsMention(), msg.getMentionedChannels().get(0).getAsMention()).queue();
            return;
        }

        if(args.get(0).equalsIgnoreCase("joinlog") || args.get(0).equalsIgnoreCase("join") || args.get(0).equalsIgnoreCase("joinlogging")){
            utils.setJoinLogChannel(ctx.getGuild(), msg.getMentionedChannels().get(0));
            channel.sendMessageFormat("Successfully set the join/leave log to %s", msg.getMentionedChannels().get(0)).queue(
                    (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
            );
            logChannel.sendMessageFormat("%s has set the join/leave log to %s", member.getAsMention(), msg.getMentionedChannels().get(0).getAsMention()).queue();
            return;
        }




    }

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public String getHelp() {
        return "Configure settings for the server";
    }
}
