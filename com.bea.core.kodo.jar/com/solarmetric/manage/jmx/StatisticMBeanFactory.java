package com.solarmetric.manage.jmx;

import com.solarmetric.manage.AggregatingStatistic;
import com.solarmetric.manage.BoundedStatistic;
import com.solarmetric.manage.ManagementLog;
import com.solarmetric.manage.Statistic;
import com.solarmetric.manage.Watchable;
import com.solarmetric.manage.WatchableEvent;
import com.solarmetric.manage.WatchableListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class StatisticMBeanFactory implements WatchableListener {
   private static final Localizer _loc = Localizer.forPackage(StatisticMBeanFactory.class);
   private Configuration _conf;
   private MBeanServer _mbServer;
   private String _watchName;
   private String _subDomain;

   public StatisticMBeanFactory(Configuration conf, MBeanServer mbServer, Watchable watchable, String subDomain, String watchName) {
      this._conf = conf;
      this._mbServer = mbServer;
      this._watchName = watchName;
      this._subDomain = subDomain;
      Iterator i = watchable.getStatistics().iterator();

      while(i.hasNext()) {
         Statistic stat = (Statistic)i.next();
         this.createMBean(stat);
      }

      watchable.addListener(this);
   }

   public void watchableChanged(WatchableEvent e) {
      Statistic stat = e.getStatistic();
      this.createMBean(stat);
   }

   public void createMBean(Statistic stat) {
      StatisticMBean mb = createStatisticMBean(stat, this._conf);

      try {
         ObjectName mbName = new ObjectName(this._conf.getProductName() + "." + this._subDomain + ":type=stat,name=" + this._watchName + ",stat=" + stat.getName());
         MBeanHelper.register(mb, mbName, this._mbServer, this._conf);
      } catch (Exception var5) {
         Log log = ManagementLog.get(this._conf);
         if (log.isWarnEnabled()) {
            log.warn(_loc.get("cant-reg-mbean"), var5);
         }
      }

   }

   public static StatisticMBean createStatisticMBean(Statistic stat, Configuration conf) {
      Object mb;
      if (stat instanceof AggregatingStatistic) {
         mb = new AggregatingStatisticMBean((AggregatingStatistic)stat, conf);
      } else if (stat instanceof BoundedStatistic) {
         mb = new BoundedStatisticMBean((BoundedStatistic)stat, conf);
      } else {
         mb = new StatisticMBean(stat, conf);
      }

      return (StatisticMBean)mb;
   }

   public static Collection createStatisticMBeans(StatisticsProvider provider, Configuration conf) {
      Collection stats = new ArrayList();
      Iterator i = provider.getStatistics().iterator();

      while(i.hasNext()) {
         Statistic stat = (Statistic)i.next();
         stats.add(new StatisticSubMBean(createStatisticMBean(stat, conf), stat.getName()));
      }

      return stats;
   }
}
