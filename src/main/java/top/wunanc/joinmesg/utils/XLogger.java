package top.wunanc.joinmesg.utils;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class XLogger {
    private static ConsoleCommandSender console;
    private static String notificationPrefix;
    public static void init(JavaPlugin plugin, String pre) {
        console = plugin.getServer().getConsoleSender();
        notificationPrefix = pre;
    }

    public static void info(String message) {
        send("info", message);
    }

    public static void warn(String message) {
        send("warn", message);
    }

    public static void error(String message) {
        send("error", message);
    }

    public static void debug(String message) {
        send("debug", message);
    }

    private static void send(String l,String msg){
        String color = switch (l) {
            case "warn" -> "&e";
            case "error" -> "&c";
            case "debug" -> "&9";
            default -> "&a";
        };
        console.sendMessage(LegacyToMiniMessage.parse(notificationPrefix + color + msg));
    }
}
