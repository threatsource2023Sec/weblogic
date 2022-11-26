package com.solarmetric.manage.jmx;

import com.solarmetric.manage.Statistic;
import com.solarmetric.manage.Watchable;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.openjpa.lib.conf.Configuration;

public class WatchableMBeanFactory {
   public static MultiMBean createWatchableMBean(Watchable watch, String description, Configuration conf) {
      ArrayList subs = new ArrayList();
      Iterator i = watch.getStatistics().iterator();

      while(i.hasNext()) {
         Statistic stat = (Statistic)i.next();
         subs.add(new StatisticSubMBean(StatisticMBeanFactory.createStatisticMBean(stat, conf), stat.getName()));
      }

      return new MultiMBean(description, (SubMBean[])((SubMBean[])subs.toArray(new SubMBean[subs.size()])));
   }
}
