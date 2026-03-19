package top.wunanc.joinmesg.handler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import top.wunanc.joinmesg.JoinMesg;
import top.wunanc.joinmesg.configuration.Configuration;
import top.wunanc.joinmesg.utils.LegacyToMiniMessage;

public class PlayerQuitHandler implements Listener {
    private final JoinMesg plugin;
    private final Configuration configManager;

    public PlayerQuitHandler(JoinMesg plugin, Configuration configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (configManager.isLeaveMessageEnabled()) {
            String leaveMessage = configManager.getLeaveMessage();
            leaveMessage = leaveMessage.replace("%player%", player.getName());
            if (!configManager.isCompatible()){
                event.quitMessage(LegacyToMiniMessage.parse(leaveMessage));
            } else {
                event.quitMessage(null);
                for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                    onlinePlayer.sendMessage(LegacyToMiniMessage.parse(leaveMessage));
                }
                Bukkit.getConsoleSender().sendMessage(LegacyToMiniMessage.parse(leaveMessage));
            }
        }
    }
}
