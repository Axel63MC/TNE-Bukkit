package com.github.tnerevival.worker;

import com.github.tnerevival.TNE;
import com.github.tnerevival.core.Statistics;
import org.bukkit.scheduler.BukkitRunnable;

public class StatisticsWorker extends BukkitRunnable {

  @SuppressWarnings("unused")
  private TNE plugin;

  public StatisticsWorker(TNE plugin) {
    this.plugin = plugin;
  }

  @Override
  public void run() {
    Statistics.send();
  }

  @Override
  public void cancel() {
    Statistics.kill();
    super.cancel();
  }
}