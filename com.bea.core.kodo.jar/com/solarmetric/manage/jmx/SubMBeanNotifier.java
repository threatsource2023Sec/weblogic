package com.solarmetric.manage.jmx;

import javax.management.MBeanNotificationInfo;
import javax.management.NotificationBroadcaster;

public interface SubMBeanNotifier extends SubMBean, NotificationBroadcaster {
   MBeanNotificationInfo[] createMBeanNotificationInfo();
}
