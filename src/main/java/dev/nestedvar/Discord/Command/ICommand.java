package dev.nestedvar.Discord.Command;

import java.util.Arrays;
import java.util.List;

public interface ICommand {

    void handle(CommandContext ctx);

    String getName();

    String getHelp(CommandContext ctx);

    default List<String> getAliases() {
        return Arrays.asList();
    }
}
