package top.wunanc.joinmesg.hooks;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;
import top.wunanc.joinmesg.data.JoinDataManager;

public class bstats {
    private final Metrics metrics;
    private final JoinDataManager joinDataManager;
    public bstats(Metrics metrics, JoinDataManager  j) {
        this.metrics = metrics;
        this.joinDataManager = j;
    }
    public void Number_of_Welcome_Players() {
        metrics.addCustomChart(new SingleLineChart("Number_of_Welcome_Players", joinDataManager::getJoinedPlayersCount));
    }
}