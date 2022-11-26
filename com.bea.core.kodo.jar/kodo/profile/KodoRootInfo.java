package kodo.profile;

import com.solarmetric.profile.ResettableEventInfo;
import com.solarmetric.profile.RootInfoImpl;
import java.io.BufferedReader;
import java.io.StringReader;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.collections.set.ListOrderedSet;

public class KodoRootInfo extends RootInfoImpl implements ResettableEventInfo {
   private static final long serialVersionUID = 1L;
   private ListOrderedSet metaSet = new ListOrderedSet();
   private ListOrderedMap fetchStatsMap = new ListOrderedMap();
   private ListOrderedMap resultListStatsMap = new ListOrderedMap();
   private ListOrderedMap proxyStatsMap = new ListOrderedMap();

   public KodoRootInfo(String name, String description) {
      super(name, description);
   }

   public boolean equals(Object o) {
      return !(o instanceof KodoRootInfo) ? false : super.equals(o);
   }

   public synchronized void recordInitialLoad(InitialLoadInfo info, ProfilingClassMetaData pmeta) {
      this.metaSet.add(pmeta);

      for(int i = 0; i < pmeta.getFields().length; ++i) {
         ProfilingFieldMetaData fmd = pmeta.getFields()[i];
         FetchStats fetchStats = (FetchStats)this.fetchStatsMap.get(fmd);
         if (fetchStats == null) {
            fetchStats = new FetchStats();
            this.fetchStatsMap.put(fmd, fetchStats);
         }

         fetchStats.initialLoad(info.getLoaded().get(i), fmd.isInDefaultFetchGroup());
      }

   }

   public synchronized void recordIsLoaded(IsLoadedInfo info, ProfilingClassMetaData pmeta) {
      this.metaSet.add(pmeta);
      FetchStats fetchStats = (FetchStats)this.fetchStatsMap.get(pmeta.getFields()[info.getField()]);
      if (fetchStats == null) {
         fetchStats = new FetchStats();
         this.fetchStatsMap.put(pmeta.getFields()[info.getField()], fetchStats);
      }

      fetchStats.isLoaded(!info.getIsLoaded());
   }

   public synchronized void recordProxyStats(ProxyUpdateInfo info) {
      ProxyStats stats = (ProxyStats)this.proxyStatsMap.get(info.toString());
      if (stats == null) {
         stats = new ProxyStats(info.toString());
         this.proxyStatsMap.put(info.toString(), stats);
      }

      stats.record(info);
   }

   public synchronized void recordResultListStats(ResultListSummaryInfo info) {
      ResultListStats stats = (ResultListStats)this.resultListStatsMap.get(info.toString());
      if (stats == null) {
         stats = new ResultListStats(info.toString());
         this.resultListStatsMap.put(info.toString(), stats);
      }

      stats.record(info);
   }

   public ListOrderedMap getFetchStatsMap() {
      return this.fetchStatsMap;
   }

   public ListOrderedMap getResultListStatsMap() {
      return this.resultListStatsMap;
   }

   public ListOrderedMap getProxyStatsMap() {
      return this.proxyStatsMap;
   }

   public ListOrderedSet getMetaSet() {
      return this.metaSet;
   }

   public String getViewerClassName() {
      return "kodo.profile.gui.KodoRootInfoPanel";
   }

   public void resetEventInfo() {
      this.metaSet = new ListOrderedSet();
      this.fetchStatsMap = new ListOrderedMap();
      this.resultListStatsMap = new ListOrderedMap();
   }

   public String toString() {
      return this.getName();
   }

   public String getName() {
      try {
         BufferedReader br = new BufferedReader(new StringReader(this.getDescription()));
         String line = null;
         boolean found = false;

         for(line = br.readLine(); line != null; line = br.readLine()) {
            if (line.indexOf("getBroker") == -1 && line.indexOf("Helper") == -1) {
               found = true;
               break;
            }
         }

         if (found) {
            String method = line.substring(line.lastIndexOf("."));
            line = line.substring(0, line.lastIndexOf("."));
            String clsName = line.substring(line.lastIndexOf(".") + 1);
            String lineNo = method.substring(method.lastIndexOf(" ") + 1);
            method = method.substring(0, method.indexOf(" "));
            return clsName + method + ":" + lineNo;
         } else {
            return super.toString();
         }
      } catch (Exception var7) {
         return super.toString();
      }
   }
}
