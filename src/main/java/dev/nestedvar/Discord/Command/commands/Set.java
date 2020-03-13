package dev.nestedvar.Discord.Command.commands;

import dev.nestedvar.Discord.Command.CommandContext;
import dev.nestedvar.Discord.Command.ICommand;
import dev.nestedvar.Discord.Utilities.Colors;
import dev.nestedvar.Discord.Utilities.Locale;
import dev.nestedvar.Discord.Utilities.Logging;
import dev.nestedvar.Discord.Utilities.Utilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Set implements ICommand {

    final Locale locale = new Locale();

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Message msg = ctx.getMessage();
        final Member member = ctx.getMember();
        final List<String> args = ctx.getArgs();
        final Guild guild = ctx.getGuild();
        final Utilities utils = new Utilities();
        final Logging logging = new Logging();
        final EmbedBuilder eb = new EmbedBuilder();
        final Colors colors = new Colors();

        final TextChannel logChannel = utils.getLogChannel(guild);


        if(!member.getRoles().contains(guild.getRoleById(utils.getAdministratorRole(guild).getIdLong())) && !utils.isAdminEnabledForSet(guild)){

            eb.setDescription(locale.getMessage(guild, "permissions", "insufficientPermissionsNoAdmin"));
            eb.setColor(colors.errorRed);
            eb.setTimestamp(Instant.now());
            eb.setFooter(locale.getMessage(guild, "permissions", "insufficientPermissionsEmbedFooter"), utils.getSelfAvatar(ctx.getEvent()));

            channel.sendMessage(eb.build()).queue((message) -> {
                message.delete().queueAfter(15, TimeUnit.SECONDS);
                eb.clear();
            });
            return;
        }
        if (!member.getRoles().contains(guild.getRoleById(utils.getAdministratorRole(guild).getIdLong())) || !member.isOwner()) {

            eb.setDescription(locale.getMessage(guild, "permissions", "insufficientPermissionsOwnerAdmin").replace("[adminrole]", utils.getAdministratorRole(guild).getAsMention()));
            eb.setColor(colors.errorRed);
            eb.setTimestamp(Instant.now());
            eb.setFooter(locale.getMessage(guild, "permissions", "insufficientPermissionsEmbedFooter"),  utils.getSelfAvatar(ctx.getEvent()));

            channel.sendMessage(eb.build()).queue((message) -> {
                eb.clear();
                message.delete().queueAfter(15, TimeUnit.SECONDS);
            });
            return;
        }
        if (args.size() < 1) {
            channel.sendMessage(locale.getMessage(guild, "settings", "insufficientArguments")).queue((message) -> {
                message.delete().queueAfter(15, TimeUnit.SECONDS);
            });
            return;
        }
        if (args.size() < 2) {
            if (args.get(0).equalsIgnoreCase("prefix")) {
                channel.sendMessage(locale.getMessage(guild, "settings", "insufficientPrefixArguments")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            } else if (args.get(0).equalsIgnoreCase("locale")) {
                channel.sendMessage(locale.getMessage(guild, "settings", "insufficientLocaleArguments")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            } else if (args.get(0).equalsIgnoreCase("logchannel") || args.get(0).equalsIgnoreCase("logging") || args.get(0).equalsIgnoreCase("log")) {
                channel.sendMessage(locale.getMessage(guild, "settings", "insufficientLogChannelArguments")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            } else if (args.get(0).equalsIgnoreCase("joinlog") || args.get(0).equalsIgnoreCase("join") || args.get(0).equalsIgnoreCase("joinlogging")) {
                channel.sendMessage(locale.getMessage(guild, "settings", "insufficientJoinLeaveLogArguments")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            }  else if (args.get(0).equalsIgnoreCase("adminrole")) {
                channel.sendMessage(locale.getMessage(guild, "settings", "insufficientAdminRoleArguments")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                return;
            } else if (args.get(0).equalsIgnoreCase("remove") || args.get(0).equalsIgnoreCase("reset")) {
                channel.sendMessage(locale.getMessage(guild, "settings", "insufficientResetArguments")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
            }
        }

        if (args.get(0).equalsIgnoreCase("prefix")) {
            eb.setDescription(locale.getMessage(guild, "settings", "setPrefixSuccessLog").replace("[member]", member.getAsMention()).replace("[prefix]", args.get(1)));
            eb.setColor(colors.successGreen);
            eb.setTimestamp(Instant.now());
            eb.setFooter(locale.getMessage(guild, "settings", "embedFooterPrefixChangeLog"), utils.getSelfAvatar(ctx.getEvent()));

            utils.setPrefix(guild, args.get(1));
            channel.sendMessage(locale.getMessage(guild, "settings", "setPrefixSuccess").replace("[prefix]", args.get(1))).queue(
                    (message) -> msg.delete().queueAfter(15, TimeUnit.SECONDS)
            );
            logChannel.sendMessage(eb.build()).queue((__) -> eb.clear());
            return;
        }

        if (args.get(0).equalsIgnoreCase("adminrole")){
            eb.setDescription(locale.getMessage(guild, "settings", "setAdministratorRoleSuccessLog").replace("[member]", member.getAsMention()).replace("[role]", ctx.getMessage().getMentionedRoles().get(0).getAsMention()));
            eb.setColor(colors.successGreen);
            eb.setTimestamp(Instant.now());
            eb.setFooter(locale.getMessage(guild, "settings", "embedFooterAdministratorRoleChangeLog"));

            utils.setAdministratorRole(guild, ctx.getMessage().getMentionedRoles().get(0).getId());
            channel.sendMessage(locale.getMessage(guild, "settings", "setAdministratorRoleSuccess").replace("[role]", ctx.getMessage().getMentionedRoles().get(0).getAsMention())).queue((message) -> {
                message.delete().queueAfter(15, TimeUnit.SECONDS);
            });
            logChannel.sendMessage(eb.build()).queue((__) -> eb.clear());
            return;
        }

        if(args.get(0).equalsIgnoreCase("modrole")){
            eb.setDescription(locale.getMessage(guild, "settings", "setModeratorRoleSuccessLog").replace("[member]", member.getAsMention()).replace("[role]", ctx.getMessage().getMentionedRoles().get(0).getAsMention()));
            eb.setColor(colors.successGreen);
            eb.setTimestamp(Instant.now());
            eb.setFooter(locale.getMessage(guild, "settings", "embedFooterModeratorRoleChangeLog"));

            utils.setModeratorRole(guild, ctx.getMessage().getMentionedRoles().get(0).getId());
            channel.sendMessage(locale.getMessage(guild, "settings", "setModeratorRoleSuccess").replace("[role]", ctx.getMessage().getMentionedRoles().get(0).getAsMention())).queue((message) -> message.delete().queueAfter(15, TimeUnit.SECONDS));
            logChannel.sendMessage(eb.build()).queue((__) -> eb.clear());
            return;
        }

        if (args.get(0).equalsIgnoreCase("locale") || args.get(0).equalsIgnoreCase("language") || args.get(0).equalsIgnoreCase("locale")) {
            eb.setDescription(locale.getMessage(guild, "settings", "setLocaleSuccessLog").replace("[member]", member.getAsMention()).replace("[prefix]", args.get(1)));
            eb.setColor(colors.successGreen);
            eb.setTimestamp(Instant.now());
            eb.setFooter(locale.getMessage(guild, "settings", "embedFooterLocaleChangeLog"), utils.getSelfAvatar(ctx.getEvent()));

            utils.setLocale(guild, args.get(1));
            channel.sendMessage(locale.getMessage(guild, "settings", "setLocaleSuccess").replace("[locale]", args.get(1))).queue(
                    (message) -> msg.delete().queueAfter(15, TimeUnit.SECONDS)
            );
            logChannel.sendMessage(eb.build()).queue((__) -> eb.clear());
            return;
        }

        if (args.get(0).equalsIgnoreCase("logchannel") || args.get(0).equalsIgnoreCase("logging") || args.get(0).equalsIgnoreCase("log")) {
            eb.setDescription(locale.getMessage(guild, "settings", "setLogChannelSuccessLog").replace("[member]", member.getAsMention()).replace("[prefix]", args.get(1)));
            eb.setColor(colors.successGreen);
            eb.setTimestamp(Instant.now());
            eb.setFooter(locale.getMessage(guild, "settings", "embedFooterLogChannelChangeLog"), utils.getSelfAvatar(ctx.getEvent()));

            utils.setLogChannel(guild, msg.getMentionedChannels().get(0).getId());
            channel.sendMessage(locale.getMessage(guild, "settings", "setLogChannelSuccess").replace("[channel]", msg.getMentionedChannels().get(0).getAsMention())).queue(
                    (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
            );
            logChannel.sendMessage(eb.build()).queue((__) -> eb.clear());
            return;
        }

        if (args.get(0).equalsIgnoreCase("joinlog") || args.get(0).equalsIgnoreCase("join") || args.get(0).equalsIgnoreCase("joinlogging")) {
            eb.setDescription(locale.getMessage(guild, "settings", "setJoinLogChannelSuccessLog").replace("[member]", member.getAsMention()).replace("[prefix]", args.get(1)));
            eb.setColor(colors.successGreen);
            eb.setTimestamp(Instant.now());
            eb.setFooter(locale.getMessage(guild, "settings", "embedFooterJoinLogChannelChangeLog"), utils.getSelfAvatar(ctx.getEvent()));

            utils.setJoinLogChannel(guild, msg.getMentionedChannels().get(0).getId());
            channel.sendMessage(locale.getMessage(guild, "settings", "setJoinLeaveLogSuccess").replace("[channel]", msg.getMentionedChannels().get(0).getAsMention())).queue(
                    (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
            );
            logChannel.sendMessage(eb.build()).queue((__) -> eb.clear());
            return;
        }

        if (args.get(0).equalsIgnoreCase("remove") || args.get(0).equalsIgnoreCase("reset")) {
            if (args.get(1).equalsIgnoreCase("prefix")) {

                eb.setDescription(locale.getMessage(guild, "settings", "setDefaultPrefixSuccessLog").replace("[member]", member.getAsMention()).replace("[prefix]", args.get(1)));
                eb.setColor(colors.successGreen);
                eb.setTimestamp(Instant.now());
                eb.setFooter(locale.getMessage(guild, "settings", "embedFooterJoinLogChannelChangeLog"), utils.getSelfAvatar(ctx.getEvent()));

                utils.setPrefix(guild, "Q!");
                channel.sendMessage(locale.getMessage(guild, "settings", "setDefaultPrefix")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                logChannel.sendMessage(eb.build()).queue((__) -> eb.clear());
                return;
            }
            if (args.get(1).equalsIgnoreCase("locale") || args.get(1).equalsIgnoreCase("language") || args.get(1).equalsIgnoreCase("locale")) {
                utils.setLocale(guild, utils.getDefaultLocale(guild));
                channel.sendMessage(locale.getMessage(guild, "settings", "setDefaultLocale").replace("[locale]", utils.getDefaultLocale(guild))).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                logChannel.sendMessage(locale.getMessage(guild, "settings", "setDefaultLocaleLog").replace("[member]", member.getAsMention()).replace("[locale]", utils.getDefaultLocale(guild))).queue();
                return;
            }
            if (args.get(1).equalsIgnoreCase("logchannel") || args.get(1).equalsIgnoreCase("logging") || args.get(1).equalsIgnoreCase("log")) {
                utils.setLogChannel(guild, null);
                channel.sendMessage(locale.getMessage(guild, "settings", "removeLogChannelSuccess")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                logChannel.sendMessage(locale.getMessage(guild, "settings", "removeLogChannelSuccessLog").replace("[member]", member.getAsMention())).queue();
                return;
            }
            if (args.get(1).equalsIgnoreCase("joinlog") || args.get(1).equalsIgnoreCase("join") || args.get(1).equalsIgnoreCase("joinlogging")) {
                utils.setJoinLogChannel(guild, null);
                channel.sendMessage(locale.getMessage(guild, "settings", "removeJoinLeaveLogSuccess")).queue(
                        (message) -> message.delete().queueAfter(15, TimeUnit.SECONDS)
                );
                logChannel.sendMessage(locale.getMessage(guild, "settings", "removeJoinLeaveLogSuccessLog").replace("[member]", member.getAsMention())).queue();
                return;
            }
        }
    }


    @Override
    public String getName() {
        return "set";
    }

    @Override
    public String getHelp(CommandContext ctx) {
        return locale.getMessage(ctx.getGuild(), "settings", "help");
    }
}
