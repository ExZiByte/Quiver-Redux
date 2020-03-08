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

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Set implements ICommand {


    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Message msg = ctx.getMessage();
        final Member member = ctx.getMember();
        final List<String> args = ctx.getArgs();
        final Guild guild = ctx.getGuild();
        final Utilities utils = new Utilities();
        final Locale locale = new Locale();
        final Logging logging = new Logging();





        final TextChannel logChannel = utils.getLogChannel(guild);

        if (args.size() < 1) {
            channel.sendMessage(locale.getMessage(guild, "settingsInsufficientArguments")).queue();
            return;
        }
        if (args.size() < 2) {
            if (args.get(0).equalsIgnoreCase("prefix")) {
                channel.sendMessage(locale.getMessage(guild,"settingsInsufficientPrefixArguments")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            } else if (args.get(0).equalsIgnoreCase("locale")) {
                channel.sendMessage(locale.getMessage(guild, "settingsInsufficientLocaleArguments")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            } else if (args.get(0).equalsIgnoreCase("logchannel") || args.get(0).equalsIgnoreCase("logging") || args.get(0).equalsIgnoreCase("log")) {
                channel.sendMessage(locale.getMessage(guild, "settingsInsufficientLogChannelArguments")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            } else if (args.get(0).equalsIgnoreCase("joinlog") || args.get(0).equalsIgnoreCase("join") || args.get(0).equalsIgnoreCase("joinlogging")) {
                channel.sendMessage(locale.getMessage(guild, "settingsInsufficientJoinLeaveLogArguments")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            } else if (args.get(0).equalsIgnoreCase("remove") || args.get(0).equalsIgnoreCase("reset")) {
                channel.sendMessage(locale.getMessage(guild, "settingsInsufficientResetArguments")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
            }
        }

        if (args.get(0).equalsIgnoreCase("prefix")) {
            utils.setPrefix(guild, args.get(1));
            channel.sendMessage(locale.getMessage(guild, "settingsSetPrefixSuccess").replace("[prefix]", args.get(1))).queue(
                    (message) -> msg.delete().queueAfter(15, TimeUnit.SECONDS)
            );
            logChannel.sendMessage(locale.getMessage(guild, "settingsSetPrefixSuccessLog").replace("[member]", member.getAsMention()).replace("[prefix]", args.get(1))).queue();
            return;
        }

        if (args.get(0).equalsIgnoreCase("locale") || args.get(0).equalsIgnoreCase("language") || args.get(0).equalsIgnoreCase("locale")) {
            utils.setLocale(guild, args.get(1));
            channel.sendMessage(locale.getMessage(guild, "settingsSetLocaleSuccess").replace("[locale]", args.get(1))).queue(
                    (message) -> msg.delete().queueAfter(15, TimeUnit.SECONDS)

            );
            logChannel.sendMessage(locale.getMessage(guild, "settingsSetLocaleSuccessLog").replace("[member]", member.getAsMention()).replace("[locale]", args.get(1))).queue();
            return;
        }

        if (args.get(0).equalsIgnoreCase("logchannel") || args.get(0).equalsIgnoreCase("logging") || args.get(0).equalsIgnoreCase("log")) {
            utils.setLogChannel(guild, msg.getMentionedChannels().get(0).getId());
            channel.sendMessage(locale.getMessage(guild, "settingsSetLogChannelSuccess").replace("[channel]", msg.getMentionedChannels().get(0).getAsMention())).queue(
                    (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
            );
            logChannel.sendMessage(locale.getMessage(guild, "settingsSetLogChannelSuccessLog").replace("[member]", member.getAsMention()).replace("[channel]",msg.getMentionedChannels().get(0).getAsMention())).queue();
            return;
        }

        if (args.get(0).equalsIgnoreCase("joinlog") || args.get(0).equalsIgnoreCase("join") || args.get(0).equalsIgnoreCase("joinlogging")) {
            utils.setJoinLogChannel(guild, msg.getMentionedChannels().get(0).getId());
            channel.sendMessage(locale.getMessage(guild, "settingsSetJoinLeaveLogSuccess").replace("[channel]" ,msg.getMentionedChannels().get(0).getAsMention())).queue(
                    (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
            );
            logChannel.sendMessage(locale.getMessage(guild, "settingsSetJoinLeaveLogSuccessLog").replace("[member]", member.getAsMention()).replace("[channel]", msg.getMentionedChannels().get(0).getAsMention())).queue();
            return;
        }

        if (args.get(0).equalsIgnoreCase("remove") || args.get(0).equalsIgnoreCase("reset")) {
            if (args.get(1).equalsIgnoreCase("prefix")) {
                utils.setPrefix(guild, "Q!");
                channel.sendMessage(locale.getMessage(guild, "settingsSetDefaultPrefix")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                logChannel.sendMessage(locale.getMessage(guild, "settingsSetDefaultPrefixLog").replace("[member]", member.getAsMention())).queue();
                return;
            }
            if (args.get(1).equalsIgnoreCase("locale") || args.get(1).equalsIgnoreCase("language") || args.get(1).equalsIgnoreCase("locale")) {
                utils.setLocale(guild, utils.getDefaultLocale(guild));
                channel.sendMessage(locale.getMessage(guild, "settingsSetDefaultLocale").replace("[locale]", utils.getDefaultLocale(guild))).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                logChannel.sendMessage(locale.getMessage(guild, "settingsSetDefaultLocaleLog").replace("[member]",  member.getAsMention()).replace("[locale]", utils.getDefaultLocale(guild))).queue();
                return;
            }
            if (args.get(1).equalsIgnoreCase("logchannel") || args.get(1).equalsIgnoreCase("logging") || args.get(1).equalsIgnoreCase("log")) {
                utils.setLogChannel(guild, null);
                channel.sendMessage(locale.getMessage(guild, "settingsRemoveLogChannelSuccess")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                logChannel.sendMessage(locale.getMessage(guild, "settingsRemoveLogChannelSuccessLog").replace("[member]", member.getAsMention())).queue();
                return;
            }
            if(args.get(1).equalsIgnoreCase("joinlog") || args.get(1).equalsIgnoreCase("join") || args.get(1).equalsIgnoreCase("joinlogging")){
                utils.setJoinLogChannel(guild, null);
                channel.sendMessage(locale.getMessage(guild, "settingsRemoveJoinLeaveLogSuccess")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                logChannel.sendMessage(locale.getMessage(guild, "settingsRemoveJoinLeaveLogSuccessLog").replace("[member]", member.getAsMention())).queue();
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
