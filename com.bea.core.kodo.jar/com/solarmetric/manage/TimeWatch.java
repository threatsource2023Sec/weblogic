package com.solarmetric.manage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class TimeWatch implements Watchable {
   private HashMap _statMap = new HashMap();
   private Collection _listeners = new ArrayList();

   public Token createToken(String name) {
      return new Token(this, name);
   }

   private HashMap getStatMap() {
      return this._statMap;
   }

   private void notifyListeners(Statistic newStat) {
      WatchableEvent event = new WatchableEvent(this, newStat);
      Iterator i = this._listeners.iterator();

      while(i.hasNext()) {
         WatchableListener listener = (WatchableListener)i.next();
         listener.watchableChanged(event);
      }

   }

   public boolean addListener(WatchableListener listener) {
      return this._listeners.add(listener);
   }

   public boolean removeListener(WatchableListener listener) {
      return this._listeners.remove(listener);
   }

   public void clearListeners() {
      this._listeners.clear();
   }

   void stop(Token token) {
      long elapsed = token.getElapsed();
      AggregatingStatistic stat = (AggregatingStatistic)this.getStatMap().get(token.getBlockName());
      if (stat == null) {
         stat = new AggregatingStatistic(token.getBlockName(), token.getBlockName(), "Milliseconds", 1, false);
         this.getStatMap().put(token.getBlockName(), stat);
         this.notifyListeners(stat);
      }

      stat.setValue((double)elapsed);
   }

   public Collection getStatistics() {
      return this._statMap.values();
   }

   public static class Token {
      private long _startTime;
      private String _blockName;
      private TimeWatch _timeWatch;

      Token(TimeWatch timeWatch, String blockName) {
         this._timeWatch = timeWatch;
         this._blockName = blockName;
         this._startTime = System.currentTimeMillis();
      }

      String getBlockName() {
         return this._blockName;
      }

      long getElapsed() {
         return System.currentTimeMillis() - this._startTime;
      }

      public void stop() {
         this._timeWatch.stop(this);
         this._startTime = System.currentTimeMillis();
      }

      public void restart() {
         this._startTime = System.currentTimeMillis();
      }
   }
}
