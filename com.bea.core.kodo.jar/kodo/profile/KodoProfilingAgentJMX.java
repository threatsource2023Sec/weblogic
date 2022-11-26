package kodo.profile;

import com.solarmetric.manage.jmx.NotificationDispatchListener;
import com.solarmetric.profile.ProfilingEvent;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.util.Localizer;

public class KodoProfilingAgentJMX extends KodoProfilingAgentImpl implements NotificationListener {
   private static final long serialVersionUID = 1L;
   private static final Localizer s_loc = Localizer.forPackage(KodoProfilingAgentJMX.class);
   private transient MBeanServer _server;
   private transient ObjectInstance _instance;

   public KodoProfilingAgentJMX() {
   }

   public KodoProfilingAgentJMX(MBeanServer server, ObjectInstance instance, MBeanInfo mbInfo, NotificationDispatchListener dispatcher, OpenJPAConfiguration conf) {
      super(conf);
      this._server = server;
      this._instance = instance;
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
      ProfilingEvent ev = (ProfilingEvent)notification.getUserData();
      if (ev instanceof InitialLoadEvent || ev instanceof IsLoadedEvent) {
         String className = null;
         if (ev instanceof InitialLoadEvent) {
            InitialLoadEvent ilev = (InitialLoadEvent)ev;
            className = ilev.getInitialLoadInfo().getClassName();
         } else if (ev instanceof IsLoadedEvent) {
            IsLoadedEvent ilev = (IsLoadedEvent)ev;
            className = ilev.getIsLoadedInfo().getClassName();
         }

         ProfilingClassMetaData pmeta = this.getMetaData(className);
         if (pmeta == null) {
            try {
               pmeta = (ProfilingClassMetaData)this._server.invoke(this._instance.getObjectName(), "getMetaData", new Object[]{className}, new String[]{String.class.getName()});
               this.registerMetaData(className, pmeta);
            } catch (Exception var7) {
            }
         }
      }

      this.handleEvent(ev);
   }
}
