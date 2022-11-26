package com.bea.adaptive.harvester.jmx;

import com.bea.adaptive.harvester.HarvestCallback;
import com.bea.adaptive.harvester.WatchedValues;
import java.util.ArrayList;

class WatchedValuesControlInfo {
   String name;
   WatchedValues watchedValues;
   ArrayList pendingMetrics = new ArrayList();
   ArrayList unharvestableAttributes = new ArrayList();
   private int wvid = -1;

   WatchedValuesControlInfo(String name, WatchedValues watchedValues, HarvestCallback callback) {
      this.name = name;
      this.watchedValues = watchedValues;
   }

   void setWVID(int wvid) {
      this.wvid = wvid;
   }

   int getWVID() {
      return this.wvid;
   }

   public String toString() {
      return (new StringBuffer(64)).append('[').append(this.watchedValues.getPartitionName()).append(']').append(this.name).append(" (").append(this.wvid).append(")").toString();
   }
}
