package top.wunanc.joinmesg.data;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.bukkit.plugin.java.JavaPlugin;
import top.wunanc.joinmesg.scheduler.FoliaScheduler;
import top.wunanc.joinmesg.utils.XLogger;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class JoinDataManager {
    private final JavaPlugin plugin;
    private final Gson gson;
    private final File dataFile;

    private final Set<String> joinedPlayers = ConcurrentHashMap.newKeySet();
    private final Object fileLock = new Object();

    public JoinDataManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        // 确保数据文件夹存在
        File dataFolder = new File(plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) {
            boolean cs = dataFolder.mkdirs();
            if (!cs) {
                XLogger.error("无法创建数据文件夹");
            }
        }

        this.dataFile = new File(dataFolder, "join.json");

        // 异步加载数据
        loadJoinDataAsync();
    }

    private void loadJoinDataAsync() {
        FoliaScheduler.runAsync(plugin, () -> {
            synchronized (fileLock) {
                if (!dataFile.exists()) {
                    return;
                }

                try (Reader reader = new FileReader(dataFile)) {
                    Type setType = new TypeToken<HashSet<String>>(){}.getType();
                    Set<String> loadedPlayers = gson.fromJson(reader, setType);

                    if (loadedPlayers != null) {
                        joinedPlayers.addAll(loadedPlayers);
                        plugin.getLogger().info("已加载 " + loadedPlayers.size() + " 个已加入玩家的数据");
                    }
                } catch (IOException e) {
                    plugin.getLogger().severe("无法加载首次加入数据: " + e.getMessage());
                }
            }
        });
    }

    public boolean isFirstJoin(String playerUuid) {
        return !joinedPlayers.contains(playerUuid);
    }

    /**
     * 记录玩家已加入
     */
    public void recordJoin(String playerUuid) {
        joinedPlayers.add(playerUuid);

        FoliaScheduler.runAsync(plugin, () -> {
            synchronized (fileLock) {
                saveJoinDataToFile();
            }
        });
    }

    /**
     * 同步保存数据（插件禁用时使用）
     */
    public void saveJoinDataSync() {
        synchronized (fileLock) {
            saveJoinDataToFile();
        }
    }

    /**
     * 实际保存数据到文件的方法
     */
    private void saveJoinDataToFile() {
        try {
            File parentDir = dataFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean created = parentDir.mkdirs();
                if (!created) {
                    XLogger.error("无法创建数据文件夹");
                    return;
                }
            }
            try (Writer writer = new FileWriter(dataFile)) {
                gson.toJson(joinedPlayers, writer);
            }
        } catch (IOException e) {
            plugin.getLogger().severe("无法保存首次加入数据: " + e.getMessage());
        }
    }

    /**
     * 获取已加入玩家的数量
     */
    public int getJoinedPlayersCount() {
        return joinedPlayers.size();
    }
}