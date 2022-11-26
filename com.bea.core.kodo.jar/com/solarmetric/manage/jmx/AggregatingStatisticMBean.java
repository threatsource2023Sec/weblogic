package com.solarmetric.manage.jmx;

import com.solarmetric.manage.AggregatingStatistic;
import javax.management.MBeanAttributeInfo;
import org.apache.openjpa.lib.conf.Configuration;

public class AggregatingStatisticMBean extends StatisticMBean {
   public AggregatingStatisticMBean(AggregatingStatistic as, Configuration conf) {
      super(as, conf);
   }

   protected MBeanAttributeInfo[] createMBeanAttributeInfo() {
      return new MBeanAttributeInfo[]{new MBeanAttributeInfo("Value", "double", "Current Value of Statistic", true, false, false), new MBeanAttributeInfo("MinValue", "double", "Minimum Value of Statistic", true, false, false), new MBeanAttributeInfo("MaxValue", "double", "Maximum Value of Statistic", true, false, false), new MBeanAttributeInfo("AveValue", "double", "Average Value of Statistic", true, false, false)};
   }
}
