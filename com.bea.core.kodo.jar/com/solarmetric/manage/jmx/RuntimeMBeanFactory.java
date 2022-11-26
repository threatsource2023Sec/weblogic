package com.solarmetric.manage.jmx;

import com.solarmetric.manage.ManageRuntimeException;
import com.solarmetric.manage.Statistic;
import java.util.ArrayList;
import java.util.Iterator;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.apache.openjpa.lib.conf.Configuration;

public class RuntimeMBeanFactory implements MBeanProvider {
   private Configuration _conf;

   public RuntimeMBeanFactory(Configuration conf) {
      this._conf = conf;
   }

   private MultiMBean createRuntimeMBean() {
      ArrayList subs = new ArrayList();
      RuntimeSubMBean sub = new RuntimeSubMBean("");
      subs.add(sub);
      Iterator i = sub.getStatistics().iterator();

      while(i.hasNext()) {
         Statistic stat = (Statistic)i.next();
         subs.add(new StatisticSubMBean(StatisticMBeanFactory.createStatisticMBean(stat, this._conf), stat.getName()));
      }

      return new MultiMBean("Runtime MBean", (SubMBean[])((SubMBean[])subs.toArray(new SubMBean[subs.size()])));
   }

   public Object getMBean() {
      return this.createRuntimeMBean();
   }

   public ObjectName getMBeanName() {
      try {
         return new ObjectName(this._conf.getProductName() + ":type=runtime");
      } catch (MalformedObjectNameException var2) {
         throw new ManageRuntimeException(var2);
      }
   }
}
