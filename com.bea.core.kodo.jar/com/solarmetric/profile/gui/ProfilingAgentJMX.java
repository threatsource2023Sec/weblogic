package com.solarmetric.profile.gui;

import com.solarmetric.manage.jmx.NotificationDispatchListener;
import com.solarmetric.profile.ProfilingAgentImpl;
import com.solarmetric.profile.ProfilingEvent;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.util.Localizer;

public class ProfilingAgentJMX extends ProfilingAgentImpl implements NotificationListener {
   private static final long serialVersionUID = 1L;
   private static final Localizer s_loc = Localizer.forPackage(ProfilingAgentJMX.class);

   public ProfilingAgentJMX(Configuration conf) {
      super(conf);
   }

   public ProfilingAgentJMX(MBeanServer server, ObjectInstance instance, MBeanInfo mbInfo, NotificationDispatchListener dispatcher, Configuration conf) {
      super(conf);
      String notifType = s_loc.get("prof-agent-notif-type").getMessage();
      MBeanNotificationInfo notif = null;
      MBeanNotificationInfo[] notifs = mbInfo.getNotifications();

      for(int i = 0; i < notifs.length; ++i) {
         String[] types = notifs[i].getNotifTypes();

         for(int j = 0; j < types.length; ++j) {
            if (notifType.equals(types[j])) {
               notif = notifs[i];
               break;
            }
         }
      }

      if (notif != null) {
         dispatcher.addListener(notif, this);
      }

   }

   public void handleNotification(Notification notification, Object handback) {
      this.handleEvent((ProfilingEvent)notification.getUserData());
   }
}
