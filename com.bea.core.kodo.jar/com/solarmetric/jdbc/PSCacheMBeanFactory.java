package com.solarmetric.jdbc;

import com.solarmetric.manage.Statistic;
import com.solarmetric.manage.jmx.MultiMBean;
import com.solarmetric.manage.jmx.StatisticMBeanFactory;
import com.solarmetric.manage.jmx.StatisticSubMBean;
import com.solarmetric.manage.jmx.SubMBean;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.openjpa.lib.conf.Configuration;

public class PSCacheMBeanFactory {
   public static MultiMBean createPSCacheMBean(PSCacheConnectionDecorator pscache, Configuration conf) {
      ArrayList subs = new ArrayList();
      subs.add(new PSCacheSubMBean(pscache, ""));
      Iterator i = pscache.getStatistics().iterator();

      while(i.hasNext()) {
         Statistic stat = (Statistic)i.next();
         subs.add(new StatisticSubMBean(StatisticMBeanFactory.createStatisticMBean(stat, conf), stat.getName()));
      }

      return new MultiMBean("PreparedStatement Cache", (SubMBean[])((SubMBean[])subs.toArray(new SubMBean[subs.size()])));
   }
}
