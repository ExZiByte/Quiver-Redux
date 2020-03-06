package dev.nestedvar.Discord.Command.commands;

import dev.nestedvar.Discord.Command.CommandContext;
import dev.nestedvar.Discord.Command.ICommand;
import dev.nestedvar.Discord.Utilities.Utilities;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

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

        if (args.size() < 1) {
            channel.sendMessage("You need to tell me what setting you want to configure!").queue();
            return;
        }
        if (args.size() < 2) {
            if (args.get(0).equalsIgnoreCase("prefix")) {
                channel.sendMessage("I need to know what you would like to use as this server's prefix").queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            } else if (args.get(0).equalsIgnoreCase("locale")) {
                channel.sendMessage("I need to know what locale you wanted to use for future messages").queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            } else if (args.get(0).equalsIgnoreCase("logchannel") || args.get(0).equalsIgnoreCase("logging") || args.get(0).equalsIgnoreCase("log")) {
                channel.sendMessage("I need to know which channel you want to use as a logging channel").queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            } else if (args.get(0).equalsIgnoreCase("joinlog") || args.get(0).equalsIgnoreCase("join") || args.get(0).equalsIgnoreCase("joinlogging")) {
                channel.sendMessage("I need to know which channel you want to use as a join/leave log").queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            } else if (args.get(0).equalsIgnoreCase("remove") || args.get(0).equalsIgnoreCase("reset")) {
                channel.sendMessage("What setting did you want to reset/remove?").queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
            }
        }

        if (args.get(0).equalsIgnoreCase("prefix")) {
            utils.setPrefix(ctx.getGuild(), args.get(1));
            channel.sendMessageFormat("Successfully set the prefix for all future commands to `%s`", args.get(1)).queue(
                    (message) -> msg.delete().queueAfter(15, TimeUnit.SECONDS)
            );
            logChannel.sendMessageFormat("%s has set the prefix to `%s`", member.getAsMention(), args.get(1)).queue();
            return;
        }

        if (args.get(0).equalsIgnoreCase("locale") || args.get(0).equalsIgnoreCase("language") || args.get(0).equalsIgnoreCase("locale")) {
            utils.setLocale(ctx.getGuild(), args.get(1));
            channel.sendMessageFormat("Successfully set the locale to `%s`", args.get(1)).queue(
                    (message) -> msg.delete().queueAfter(15, TimeUnit.SECONDS)

            );
            logChannel.sendMessageFormat("%s has set the locale to `%s`", member.getAsMention(), args.get(1)).queue();
            return;
        }

        if (args.get(0).equalsIgnoreCase("logchannel") || args.get(0).equalsIgnoreCase("logging") || args.get(0).equalsIgnoreCase("log")) {
            utils.setLogChannel(ctx.getGuild(), msg.getMentionedChannels().get(0));
            channel.sendMessageFormat("Successfully set the logging channel to %s", msg.getMentionedChannels().get(0).getAsMention()).queue(
                    (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
            );
            logChannel.sendMessageFormat("%s has set the log channel to %s", member.getAsMention(), msg.getMentionedChannels().get(0).getAsMention()).queue();
            return;
        }

        if (args.get(0).equalsIgnoreCase("joinlog") || args.get(0).equalsIgnoreCase("join") || args.get(0).equalsIgnoreCase("joinlogging")) {
            utils.setJoinLogChannel(ctx.getGuild(), msg.getMentionedChannels().get(0));
            channel.sendMessageFormat("Successfully set the join/leave log to %s", msg.getMentionedChannels().get(0)).queue(
                    (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
            );
            logChannel.sendMessageFormat("%s has set the join/leave log to %s", member.getAsMention(), msg.getMentionedChannels().get(0).getAsMention()).queue();
            return;
        }

        if (args.get(0).equalsIgnoreCase("remove") || args.get(0).equalsIgnoreCase("reset")) {
            if (args.get(1).equalsIgnoreCase("prefix")) {
                utils.setPrefix(ctx.getGuild(), "Q!");
                channel.sendMessage("Successfully reset the prefix to `Q!`").queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                logChannel.sendMessageFormat("%s has reset the prefix to the default prefix of `Q!`").queue();
                return;
            }
            if (args.get(1).equalsIgnoreCase("locale") || args.get(1).equalsIgnoreCase("language") || args.get(1).equalsIgnoreCase("locale")) {
                utils.setLocale(ctx.getGuild(), utils.getDefaultLocale(ctx.getGuild()));
                channel.sendMessageFormat("Successfully reset the language setting to the default for this guild based on this guild's location `%s`", utils.getDefaultLocale(ctx.getGuild())).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                logChannel.sendMessageFormat("%s has reset the language setting to the default for this guild based on this guild's location `%s`", member.getAsMention(), utils.getDefaultLocale(ctx.getGuild())).queue();
                return;
            }
            if (args.get(1).equalsIgnoreCase("logchannel") || args.get(1).equalsIgnoreCase("logging") || args.get(1).equalsIgnoreCase("log")) {
                utils.setLogChannel(ctx.getGuild(), null);
                channel.sendMessage("Successfully removed the log channel").queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                logChannel.sendMessageFormat("%s has removed the log channel", member.getAsMention()).queue();
                return;
            }
            if(args.get(1).equalsIgnoreCase("joinlog") || args.get(1).equalsIgnoreCase("join") || args.get(1).equalsIgnoreCase("joinlogging")){
                utils.setJoinLogChannel(ctx.getGuild(), null);
                channel.sendMessage("Successfully removed the join/leave log channel").queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                logChannel.sendMessageFormat("%s has removed the join/leave log channel", member.getAsMention()).queue();
                return;
            }
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
