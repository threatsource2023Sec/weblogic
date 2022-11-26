package com.solarmetric.manage.jmx;

import com.solarmetric.manage.BoundedStatistic;
import javax.management.MBeanAttributeInfo;
import org.apache.openjpa.lib.conf.Configuration;

public class BoundedStatisticMBean extends StatisticMBean {
   public BoundedStatisticMBean(BoundedStatistic as, Configuration conf) {
      super(as, conf);
   }

   protected MBeanAttributeInfo[] createMBeanAttributeInfo() {
      return new MBeanAttributeInfo[]{new MBeanAttributeInfo("Value", "double", "Current Value of Statistic", true, false, false), new MBeanAttributeInfo("LowerBound", "double", "Lower Bound of Statistic", true, false, false), new MBeanAttributeInfo("UpperBound", "double", "Upper Bound of Statistic", true, false, false)};
   }
}
