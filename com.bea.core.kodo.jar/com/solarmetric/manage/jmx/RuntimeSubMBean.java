package com.solarmetric.manage.jmx;

import com.solarmetric.manage.TimeSamplingStatistic;
import com.solarmetric.manage.ValueProvider;
import java.util.ArrayList;
import java.util.Collection;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanOperationInfo;
import org.apache.openjpa.lib.util.Localizer;

public class RuntimeSubMBean implements SubMBean {
   private Runtime _runtime = Runtime.getRuntime();
   private String _prefix;
   private TimeSamplingStatistic _totalMemStat = null;
   private TimeSamplingStatistic _freeMemStat = null;
   private Collection _stats = null;
   private static final Localizer _loc = Localizer.forPackage(RuntimeSubMBean.class);

   public RuntimeSubMBean(String prefix) {
      this._prefix = prefix;
   }

   public String getPrefix() {
      return this._prefix;
   }

   public Object getSub() {
      return this;
   }

   public MBeanAttributeInfo[] createMBeanAttributeInfo() {
      return new MBeanAttributeInfo[]{new MBeanAttributeInfo("FreeMemory", "long", _loc.get("free-memory-desc").getMessage(), true, false, false), new MBeanAttributeInfo("TotalMemory", "long", _loc.get("total-memory-desc").getMessage(), true, false, false)};
   }

   public MBeanOperationInfo[] createMBeanOperationInfo() {
      return new MBeanOperationInfo[0];
   }

   protected String getMBeanDescription() {
      return "Runtime MBean";
   }

   public long getFreeMemory() {
      return this._runtime.freeMemory();
   }

   public long getTotalMemory() {
      return this._runtime.totalMemory();
   }

   public synchronized Collection getStatistics() {
      if (this._stats == null) {
         this._freeMemStat = new TimeSamplingStatistic(_loc.get("free-memory").getMessage(), _loc.get("free-memory-desc").getMessage(), _loc.get("memory-ord").getMessage(), 0, false, new ValueProvider() {
            public double getValue() {
               return (double)RuntimeSubMBean.this._runtime.freeMemory() / 1000.0;
            }
         });
         this._totalMemStat = new TimeSamplingStatistic(_loc.get("total-memory").getMessage(), _loc.get("total-memory-desc").getMessage(), _loc.get("memory-ord").getMessage(), 0, false, new ValueProvider() {
            public double getValue() {
               return (double)RuntimeSubMBean.this._runtime.totalMemory() / 1000.0;
            }
         });
         this._stats = new ArrayList(2);
         this._stats.add(this._freeMemStat);
         this._stats.add(this._totalMemStat);
      }

      return this._stats;
   }
}
