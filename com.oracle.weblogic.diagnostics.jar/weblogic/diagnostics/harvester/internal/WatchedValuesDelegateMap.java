package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.harvester.Harvester;
import com.bea.adaptive.harvester.WatchedValues;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;

class WatchedValuesDelegateMap {
   private WatchedValues parentList;
   private int delegateWVID;
   private HashSet vids;
   private Harvester delegateHarvester;
   private HashMap vidsToValuesMap;
   private HashMap allSlotsMap;
   private static DebugLogger dbg = DebugLogger.getDebugLogger("DebugDiagnosticsHarvester");

   public WatchedValuesDelegateMap(Harvester harvester, ArrayList vids, int wvid, WatchedValues parentList) {
      this.delegateHarvester = harvester;
      this.vids = new HashSet(vids);
      this.delegateWVID = wvid;
      this.parentList = parentList;
   }

   public WatchedValues.Values getValue(int vid) {
      return this.parentList.getMetric(vid);
   }

   public Collection findMatchingValuesSlots(Collection slots) {
      ArrayList delegateSlotsList = new ArrayList(this.vids.size());
      Iterator var3 = slots.iterator();

      while(var3.hasNext()) {
         WatchedValues.Values slot = (WatchedValues.Values)var3.next();
         WatchedValues.Values delegateSlot = (WatchedValues.Values)this.getVidsToValuesMap().get(slot.getVID());
         if (delegateSlot != null) {
            delegateSlotsList.add(delegateSlot);
         }
      }

      return delegateSlotsList;
   }

   public Harvester getDelegateHarvester() {
      return this.delegateHarvester;
   }

   public void setDelegateHarvester(Harvester delegateHarvester) {
      this.delegateHarvester = delegateHarvester;
   }

   public int getDelegateWVID() {
      return this.delegateWVID;
   }

   public void setDelegateWVID(int delegateWVID) {
      this.delegateWVID = delegateWVID;
   }

   public Set getVids() {
      return this.vids;
   }

   public void setVids(Set vids) {
      this.vids = new HashSet(vids);
   }

   public WatchedValues getParentList() {
      return this.parentList;
   }

   public void setParentList(WatchedValues parentList) {
      this.parentList = parentList;
   }

   public void harvest(Collection slots) {
      if (slots == null) {
         if (dbg.isDebugEnabled()) {
            dbg.debug("Harvesting all slots for Harvester " + this.delegateHarvester.getName());
         }

         this.delegateHarvester.harvest(this.getAllSlotsMap());
      } else {
         Map slotsSubSet = this.getSlotsSubSet(slots);
         if (dbg.isDebugEnabled()) {
            dbg.debug("Harvesting slots {" + slotsSubSet + "} of " + this.parentList.getName() + " for Harvester " + this.delegateHarvester.getName());
         }

         this.delegateHarvester.harvest(slotsSubSet);
      }

   }

   public Map getSlotsSubSet(Collection slots) {
      HashSet subset = new HashSet(slots.size());
      Iterator vIt = slots.iterator();

      while(vIt.hasNext()) {
         Integer vid = (Integer)vIt.next();
         if (this.vids.contains(vid)) {
            subset.add(vid);
         }
      }

      HashMap subMap = new HashMap();
      subMap.put(this.delegateWVID, subset);
      return subMap;
   }

   public List getPendingMetrics() {
      WatchedValues.Values[] allPendingMetrics = this.delegateHarvester.getPendingMetrics(this.delegateWVID);
      ArrayList pendingMetrics = new ArrayList(allPendingMetrics.length);
      WatchedValues.Values[] var3 = allPendingMetrics;
      int var4 = allPendingMetrics.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         WatchedValues.Values v = var3[var5];
         if (this.vids.contains(v.getVID())) {
            pendingMetrics.add(v);
         }
      }

      return pendingMetrics;
   }

   public List getUnharvestableAttributes(String typeNameRegex, String attrNameRegex) {
      return this.delegateHarvester.getUnharvestableAttributes(this.delegateWVID, typeNameRegex, attrNameRegex);
   }

   public List getDisabledAttributes(String typeNameRegex, String attrNameRegex) {
      return this.delegateHarvester.getDisabledAttributes(this.delegateWVID, typeNameRegex, attrNameRegex);
   }

   public List getHarvestedTypes(String typeNameRegex) {
      return this.delegateHarvester.getHarvestedTypes(this.delegateWVID, typeNameRegex);
   }

   public int disableMetrics(Integer[] vids) {
      int totalDisabledMetrics = 0;
      Set intersection = this.findVidIntersection(vids);
      if (intersection.size() > 0) {
         totalDisabledMetrics += this.delegateHarvester.disableMetrics(this.delegateWVID, (Integer[])intersection.toArray(new Integer[intersection.size()]));
      }

      return totalDisabledMetrics;
   }

   public int enableMetrics(Integer[] vids) {
      int totalEnabledMetrics = 0;
      Set intersection = this.findVidIntersection(vids);
      if (intersection.size() > 0) {
         totalEnabledMetrics += this.delegateHarvester.enableMetrics(this.delegateWVID, (Integer[])intersection.toArray(new Integer[intersection.size()]));
      }

      return totalEnabledMetrics;
   }

   private Set findVidIntersection(Integer[] vids) {
      HashSet intersectingVids = new HashSet(vids.length);
      Integer[] var3 = vids;
      int var4 = vids.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Integer vid = var3[var5];
         if (this.vids.contains(vid)) {
            intersectingVids.add(vid);
         }
      }

      return intersectingVids;
   }

   private HashMap getVidsToValuesMap() {
      if (this.vidsToValuesMap == null) {
         this.vidsToValuesMap = this.constructVidsToValuesMap();
      }

      return this.vidsToValuesMap;
   }

   private HashMap constructVidsToValuesMap() {
      HashMap map = new HashMap(this.vids.size());
      Iterator var2 = this.vids.iterator();

      while(var2.hasNext()) {
         Integer vid = (Integer)var2.next();
         map.put(vid, this.parentList.getMetric(vid));
      }

      return map;
   }

   private synchronized HashMap getAllSlotsMap() {
      if (this.allSlotsMap == null) {
         this.allSlotsMap = new HashMap();
      }

      this.allSlotsMap.put(this.delegateWVID, this.vids);
      return this.allSlotsMap;
   }

   public void extendWatchedValues(WatchedValues newWV, ArrayList newDelegateVids) {
      List actualMetrics = this.parentList.extendValues(newWV, newDelegateVids);
      HashSet actualVIDs = new HashSet(actualMetrics.size());
      Iterator var5 = actualMetrics.iterator();

      while(var5.hasNext()) {
         WatchedValues.Values v = (WatchedValues.Values)var5.next();
         actualVIDs.add(v.getVID());
      }

      this.delegateHarvester.resolveMetrics(this.delegateWVID, actualVIDs);
      synchronized(this) {
         this.vids.addAll(actualVIDs);
         this.allSlotsMap = null;
      }
   }

   public void resolveMetrics(Set vidsToResolve) {
      Set vidSubset = null;
      if (vidsToResolve != null) {
         vidSubset = this.findVidIntersection((Integer[])vidsToResolve.toArray(new Integer[0]));
      } else {
         vidSubset = this.vids;
      }

      this.delegateHarvester.resolveMetrics(this.delegateWVID, (Set)vidSubset);
   }
}
