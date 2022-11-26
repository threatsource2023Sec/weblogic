package com.solarmetric.manage.jmx;

import com.solarmetric.manage.ManageRuntimeException;
import com.solarmetric.manage.ManagementLog;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MalformedObjectNameException;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.log.LogFactoryImpl;
import org.apache.openjpa.lib.util.Localizer;

public class LogMBean extends BaseDynamicMBean implements NotificationBroadcaster, MBeanProvider {
   private static final Localizer s_loc = Localizer.forPackage(LogMBean.class);
   private boolean _appenderAdded = false;
   private LogAppender _appender;
   private NotificationBroadcasterImpl _bcastSupport;
   private Configuration _conf;
   private Log _log;

   public LogMBean(Configuration conf) {
      this._conf = conf;
      this._log = ManagementLog.get(conf);
      this._bcastSupport = new NotificationBroadcasterImpl(this._conf, false);
   }

   public Object getMBean() {
      if (this._log == null) {
         throw new ManageRuntimeException(s_loc.get("invalid-log"));
      } else {
         return this;
      }
   }

   public ObjectName getMBeanName() {
      try {
         return new ObjectName(this._conf.getProductName() + ":type=log,name=LogMBean");
      } catch (MalformedObjectNameException var2) {
         throw new ManageRuntimeException(var2);
      }
   }

   protected MBeanAttributeInfo[] createMBeanAttributeInfo() {
      return new MBeanAttributeInfo[]{new MBeanAttributeInfo("AppenderAdded", "boolean", "Whether or not the JMX appender is added to root category.", true, false, false)};
   }

   protected MBeanOperationInfo[] createMBeanOperationInfo() {
      return new MBeanOperationInfo[]{new MBeanOperationInfo("start", "Adds appender to root channel", new MBeanParameterInfo[0], Void.class.getName(), 1), new MBeanOperationInfo("stop", "Removes appender from root channel", new MBeanParameterInfo[0], Void.class.getName(), 1)};
   }

   protected MBeanNotificationInfo[] createMBeanNotificationInfo() {
      return new MBeanNotificationInfo[]{new MBeanNotificationInfo(new String[]{LogFactoryImpl.TRACE_STR, LogFactoryImpl.INFO_STR, LogFactoryImpl.WARN_STR, LogFactoryImpl.ERROR_STR, LogFactoryImpl.FATAL_STR}, "javax.management.Notification", "Log Notification")};
   }

   protected String getMBeanDescription() {
      return "Logging MBean";
   }

   public boolean getAppenderAdded() {
      return this._appenderAdded;
   }

   public void start() throws InstantiationException, ClassNotFoundException, IllegalAccessException {
      if (!this._appenderAdded) {
         if (this._appender == null) {
            if (!isInstanceOf(this._log, "org.apache.openjpa.lib.log.Log4JLogFactory$LogAdapter")) {
               this._log.info(s_loc.get("service-no-appender"));
               return;
            }

            Class appenderCls = Class.forName("com.solarmetric.manage.jmx.Log4JJMXAppender", true, this.getClass().getClassLoader());
            this._appender = (LogAppender)appenderCls.newInstance();
            this._appender.init(this, this._bcastSupport, this._log);
         }

         this._appender.start();
         this._appenderAdded = true;
      }
   }

   public void stop() {
      if (this._appender != null) {
         if (this._appenderAdded) {
            this._appender.stop();
            this._appenderAdded = false;
         }
      }
   }

   public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) {
      this._bcastSupport.addNotificationListener(listener, filter, handback);

      try {
         this.start();
      } catch (Exception var5) {
         this._log.error(s_loc.get("cant-start-appender"), var5);
      }

   }

   public MBeanNotificationInfo[] getNotificationInfo() {
      return this.createMBeanNotificationInfo();
   }

   public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
      this._bcastSupport.removeNotificationListener(listener);
      if (!this._bcastSupport.hasListeners()) {
         this.stop();
      }

   }

   private static boolean isInstanceOf(Object obj, String classname) {
      return isTypeOf(obj.getClass(), classname);
   }

   private static boolean isTypeOf(Class cls, String classname) {
      if (cls == null) {
         return false;
      } else if (cls.getName().equals(classname)) {
         return true;
      } else if (isTypeOf(cls.getSuperclass(), classname)) {
         return true;
      } else {
         Class[] ifaces = cls.getInterfaces();

         for(int i = 0; i < ifaces.length; ++i) {
            if (isTypeOf(ifaces[i], classname)) {
               return true;
            }
         }

         return false;
      }
   }
}
