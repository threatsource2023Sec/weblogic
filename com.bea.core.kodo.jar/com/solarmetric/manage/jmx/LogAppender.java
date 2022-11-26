package com.solarmetric.manage.jmx;

import org.apache.openjpa.lib.log.Log;

public interface LogAppender {
   void init(LogMBean var1, NotificationBroadcasterImpl var2, Log var3);

   void start();

   void stop();
}
