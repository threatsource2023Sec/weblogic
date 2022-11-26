package com.solarmetric.jdbc;

import com.solarmetric.manage.Statistic;
import com.solarmetric.manage.jmx.MultiMBean;
import com.solarmetric.manage.jmx.StatisticMBeanFactory;
import com.solarmetric.manage.jmx.StatisticSubMBean;
import com.solarmetric.manage.jmx.SubMBean;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.openjpa.lib.conf.Configuration;

public class PoolingDataSourceMBeanFactory {
   public static MultiMBean createPoolingDataSourceMBean(PoolingDataSource ds, Configuration conf) {
      ArrayList subs = new ArrayList();
      subs.add(new PoolingDataSourceSubMBean(ds, ""));
      ConnectionPoolImpl cp = (ConnectionPoolImpl)ds.getConnectionPool();
      subs.add(new ConnectionPoolImplSubMBean(cp, "Pool"));
      Iterator i = cp.getStatistics().iterator();

      while(i.hasNext()) {
         Statistic stat = (Statistic)i.next();
         subs.add(new StatisticSubMBean(StatisticMBeanFactory.createStatisticMBean(stat, conf), "Pool." + stat.getName()));
      }

      return new MultiMBean("Pooling DataSource", (SubMBean[])((SubMBean[])subs.toArray(new SubMBean[subs.size()])));
   }
}
