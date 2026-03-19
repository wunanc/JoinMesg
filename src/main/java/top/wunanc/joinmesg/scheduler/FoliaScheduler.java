package top.wunanc.joinmesg.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class FoliaScheduler {

    public static void runAtLocation(Plugin plugin, Location location, Runnable task) {
        if (isFolia()) {
            Bukkit.getRegionScheduler().run(plugin, location, scheduledTask -> task.run());
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
        }
    }

    public static void runAtLocationDelayed(Plugin plugin, Location location, Runnable task, long delayTicks) {
        if (isFolia()) {
            Bukkit.getRegionScheduler().runDelayed(plugin, location, scheduledTask -> task.run(), delayTicks);
        } else {
            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delayTicks);
        }
    }

    public static void runAsync(Plugin plugin, Runnable task) {
        if (isFolia()) {
            Bukkit.getGlobalRegionScheduler().run(plugin, scheduledTask -> task.run());
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
        }
    }

    public static void runAtEntity(Plugin plugin, Entity entity, Consumer<Object> task) {
        if (isFolia()) {
            entity.getScheduler().run(plugin, scheduledTask -> task.accept(scheduledTask), null);
        } else {
            Bukkit.getScheduler().runTask(plugin, () -> task.accept(null));
        }
    }

    public static void runAtEntitySimple(Plugin plugin, Entity entity, Runnable task) {
        if (isFolia()) {
            entity.getScheduler().run(plugin, scheduledTask -> task.run(), null);
        } else {
            Bukkit.getScheduler().runTask(plugin, task);
        }
    }

    public static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}