package com.solarmetric.manage.jmx.gui;

import com.solarmetric.manage.jmx.NotificationDispatchListener;
import java.util.HashMap;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import org.apache.openjpa.lib.conf.Configuration;

public class StatisticPanelFactory {
   public static HashMap createStatisticPanels(MBeanServer server, ObjectInstance instance, MBeanInfo mbInfo, NotificationDispatchListener dispatcher, Configuration conf) {
      HashMap chartPanels = new HashMap();
      return chartPanels;
   }

   public static HashMap createStatisticPanels(MBeanServer server, DashboardChartPanelMetaData cpmd, NotificationDispatchManager dispatcherManager, Configuration conf) {
      HashMap chartPanels = new HashMap();
      return chartPanels;
   }
}
