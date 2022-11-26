package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.harvester.HarvestCallback;
import com.bea.adaptive.harvester.Harvester;
import com.bea.adaptive.harvester.WatchedValues;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.diagnostics.harvester.HarvesterRuntimeException;

public class WatchedValuesControl {
   private WatchedValues watchedValues;
   private List mapList;
   private HarvestCallback harvestCallback;

   public WatchedValuesControl(WatchedValues values, HarvestCallback callback, List mapList) {
      this.setMapList(mapList);
      this.setWatchedValues(values);
      this.harvestCallback = callback;
   }

   public HarvestCallback getHarvestCallback() {
      return this.harvestCallback;
   }

   public ArrayList extendDelegateMap(Harvester delegate, WatchedValues newWV, ArrayList newDelegateVids) {
      List newValues = this.getWatchedValues().extendValues(newWV, newDelegateVids);
      ArrayList delegateVids = new ArrayList(newValues.size());
      Iterator var6 = newValues.iterator();

      while(var6.hasNext()) {
         WatchedValues.Values v = (WatchedValues.Values)var6.next();
         delegateVids.add(v.getVID());
      }

      try {
         int childWVID = delegate.addWatchedValues(this.getWatchedValues().getName(), this.getWatchedValues(), this.harvestCallback);
         WatchedValuesDelegateMap delegateMap = new WatchedValuesDelegateMap(delegate, delegateVids, childWVID, this.getWatchedValues());
         this.getMapList().add(delegateMap);
         return delegateVids;
      } catch (Exception var8) {
         throw new HarvesterRuntimeException(var8);
      }
   }

   public WatchedValuesDelegateMap findDelegateMap(Harvester delegate) {
      WatchedValuesDelegateMap foundMap = null;
      Iterator var3 = this.getMapList().iterator();

      while(var3.hasNext()) {
         WatchedValuesDelegateMap map = (WatchedValuesDelegateMap)var3.next();
         if (map.getDelegateHarvester() == delegate) {
            foundMap = map;
            break;
         }
      }

      return foundMap;
   }

   public void resolveMetrics(Set vidsToResolve) {
      Iterator var2 = this.getMapList().iterator();

      while(var2.hasNext()) {
         WatchedValuesDelegateMap delegateMap = (WatchedValuesDelegateMap)var2.next();
         delegateMap.resolveMetrics(vidsToResolve);
      }

   }

   public void setWatchedValues(WatchedValues watchedValues) {
      this.watchedValues = watchedValues;
   }

   public WatchedValues getWatchedValues() {
      return this.watchedValues;
   }

   public void setMapList(List mapList) {
      this.mapList = mapList;
   }

   public List getMapList() {
      return this.mapList;
   }
}
