package weblogic.management.scripting;

import java.util.TreeMap;
import javax.management.MBeanServerNotification;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;

public class CustomMBeanChangeListener implements NotificationListener {
   TreeMap customMBeanObjectNameMap;

   public CustomMBeanChangeListener(TreeMap objectMap) {
      this.customMBeanObjectNameMap = objectMap;
   }

   public void handleNotification(Notification notification, Object handback) {
      MBeanServerNotification serverNotification = null;
      ObjectName objectName = null;
      if (notification instanceof MBeanServerNotification) {
         serverNotification = (MBeanServerNotification)notification;

         try {
            objectName = serverNotification.getMBeanName();
            if (objectName.getDomain().equals("com.bea")) {
               return;
            }
         } catch (Exception var8) {
            return;
         }

         boolean isRegister = serverNotification.getType().equals("JMX.mbean.registered");

         try {
            if (isRegister) {
               WLSTTreeUtils.addCustomMBeanToMap(objectName, this.customMBeanObjectNameMap);
            } else {
               WLSTTreeUtils.removeCustomMBeanFromMap(objectName, this.customMBeanObjectNameMap);
            }
         } catch (Exception var7) {
            return;
         }
      }

   }
}
