package top.wunanc.joinmesg.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Configuration {
    private final JavaPlugin plugin;
    private final File configFile;
    private YamlConfiguration config;

    public Configuration(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        load();
    }

    // 配置字段
    private boolean welcomeMessageEnabled;
    private String welcomeMessage;
    private boolean joinMessageEnabled;
    private String joinMessage;
    private boolean leaveMessageEnabled;
    private String leaveMessage;
    private boolean compatible;


    // 默认值
    private static final boolean DEFAULT_WELCOME_MESSAGE_ENABLED = true;
    private static final String DEFAULT_WELCOME_MESSAGE = "&d欢迎新玩家 &e%player% &d加入服务器!";
    private static final boolean DEFAULT_JOIN_MESSAGE_ENABLED = true;
    private static final String DEFAULT_JOIN_MESSAGE = "&a[+]%player%";
    private static final boolean DEFAULT_LEAVE_MESSAGE_ENABLED = true;
    private static final String DEFAULT_LEAVE_MESSAGE = "&c[-]%player%";
    private static final boolean DEFAULT_COMPATIBLE = true;

    /**
     * 加载配置文件，若不存在则创建默认配置
     */
    public void load() {
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        loadFields();
    }

    /**
     * 从YAML配置中读取字段到内存
     */
    private void loadFields() {
        welcomeMessageEnabled = config.getBoolean("welcomeFirstJoin.enabled", DEFAULT_WELCOME_MESSAGE_ENABLED);
        welcomeMessage = config.getString("welcomeFirstJoin.message", DEFAULT_WELCOME_MESSAGE);
        joinMessageEnabled = config.getBoolean("join.enabled", DEFAULT_JOIN_MESSAGE_ENABLED);
        joinMessage = config.getString("join.message", DEFAULT_JOIN_MESSAGE);
        leaveMessageEnabled = config.getBoolean("quit.enabled", DEFAULT_LEAVE_MESSAGE_ENABLED);
        leaveMessage = config.getString("quit.message", DEFAULT_LEAVE_MESSAGE);
        compatible = config.getBoolean("compatible", DEFAULT_COMPATIBLE);
    }

    /**
     * 重载配置文件（丢弃内存修改，重新从文件加载）
     */
    public void reload() {
        config = YamlConfiguration.loadConfiguration(configFile);
        loadFields();
    }
    // Getter 和 Setter 方法
    public boolean isWelcomeMessageEnabled() {
        return welcomeMessageEnabled;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public boolean isJoinMessageEnabled() {
        return joinMessageEnabled;
    }

    public String getJoinMessage() {
        return joinMessage;
    }

    public boolean isLeaveMessageEnabled() {
        return leaveMessageEnabled;
    }

    public String getLeaveMessage() {
        return leaveMessage;
    }

    public boolean isCompatible() {
        return compatible;
    }
}
