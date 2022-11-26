package com.solarmetric.manage;

import java.util.EventObject;

public class WatchableEvent extends EventObject {
   private Statistic stat;

   public WatchableEvent(Watchable watchable, Statistic stat) {
      super(watchable);
      this.stat = stat;
   }

   public Watchable getWatchable() {
      return (Watchable)this.getSource();
   }

   public Statistic getStatistic() {
      return this.stat;
   }
}
