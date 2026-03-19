package top.wunanc.joinmesg;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import top.wunanc.joinmesg.configuration.Configuration;
import top.wunanc.joinmesg.data.JoinDataManager;
import top.wunanc.joinmesg.handler.PlayerJoinHandler;
import top.wunanc.joinmesg.handler.PlayerQuitHandler;
import top.wunanc.joinmesg.hooks.bstats;
import top.wunanc.joinmesg.utils.XLogger;

public final class JoinMesg extends JavaPlugin {

    private JoinDataManager joinDataManager;

    public String loadingConfig = "Loading Configurations...";
    public String pluginEnabled = "Plugin Enabled!";
    public String pluginDisabled = "Plugin Disabled!";
    public String pluginVersion = "Plugin Version: {0}";
    public String notificationPrefix = "&3[&bJoinMesg&3]&f";

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 30316);
        XLogger.init(this, notificationPrefix);

        XLogger.info(loadingConfig);
        Configuration configuration = new Configuration(this);
        joinDataManager = new JoinDataManager(this);

        pluginVersion = pluginVersion.replace("{0}", getDescription().getVersion());
        XLogger.info(pluginVersion);
        getServer().getPluginManager().registerEvents(new PlayerJoinHandler(this, configuration, joinDataManager), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitHandler(this, configuration), this);
        bstats bstats = new bstats(metrics, joinDataManager);
        bstats.Number_of_Welcome_Players();
        XLogger.info(pluginEnabled);
    }

    @Override
    public void onDisable() {
        joinDataManager.saveJoinDataSync();
        XLogger.info(pluginDisabled);
    }

}
