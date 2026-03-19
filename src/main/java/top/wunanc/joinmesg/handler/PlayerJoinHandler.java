package top.wunanc.joinmesg.handler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import top.wunanc.joinmesg.JoinMesg;
import top.wunanc.joinmesg.configuration.Configuration;
import top.wunanc.joinmesg.data.JoinDataManager;
import top.wunanc.joinmesg.utils.LegacyToMiniMessage;

public class PlayerJoinHandler implements Listener {
    private final JoinMesg plugin;
    private final Configuration configManager;
    private final JoinDataManager joinDataManager;

    public PlayerJoinHandler(JoinMesg plugin, Configuration configManager, JoinDataManager joinDataManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.joinDataManager = joinDataManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (configManager.isWelcomeMessageEnabled()) {
            String playerUuid = player.getUniqueId().toString();
            if (joinDataManager.isFirstJoin(playerUuid)) {
                joinDataManager.recordJoin(playerUuid);
                String welcomeMessage = configManager.getWelcomeMessage();
                welcomeMessage = welcomeMessage.replace("%player%", player.getName());
                for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                    onlinePlayer.sendMessage(LegacyToMiniMessage.parse(welcomeMessage));
                }
                Bukkit.getConsoleSender().sendMessage(LegacyToMiniMessage.parse(welcomeMessage));
            }
        }

        if (configManager.isJoinMessageEnabled()) {
            String joinMessage = configManager.getJoinMessage();
            joinMessage = joinMessage.replace("%player%", player.getName());

            if (!configManager.isCompatible()){
                event.joinMessage(LegacyToMiniMessage.parse(joinMessage));
            } else {
                event.joinMessage(null);
                for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                    onlinePlayer.sendMessage(LegacyToMiniMessage.parse(joinMessage));
                }
                Bukkit.getConsoleSender().sendMessage(LegacyToMiniMessage.parse(joinMessage));
            }
        }
    }
}