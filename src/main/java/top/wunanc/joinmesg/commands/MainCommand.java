package top.wunanc.joinmesg.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import top.wunanc.joinmesg.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor, TabExecutor {
    private final Configuration configuration;

    public MainCommand(Configuration configuration) {
        this.configuration = configuration;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        if (args[0].equals("reload")) {
            if (sender.hasPermission("joinmesg.reload")) {
                sender.sendMessage(Component.text("Reloading plugin...").color(NamedTextColor.GREEN));
                configuration.reload();
                sender.sendMessage(Component.text("Reload completed！").color(NamedTextColor.GREEN));
            } else {
                sender.sendMessage(Component.text("You do not have permission to execute this command！").color(NamedTextColor.RED));
            }
            return true;
        }
        sendHelp(sender);
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(Component.text("/joinmesg reload").color(NamedTextColor.GREEN));
    }

    @Override
    public java.util.List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            List<String> subCommands = new ArrayList<>(List.of("help"));
            if (sender.hasPermission("joinmesg.reload")) {
                subCommands.add("reload");
            }
            StringUtil.copyPartialMatches(args[0], subCommands, completions);
        }
        return completions;
    }
}