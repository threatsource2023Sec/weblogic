package weblogic.management.scripting;

import java.util.EmptyStackException;
import javax.management.MBeanServerNotification;
import javax.management.Notification;
import javax.management.ObjectName;
import weblogic.management.RemoteNotificationListener;

public class ChangeListener implements RemoteNotificationListener {
   private WLScriptContext ctx;

   public ChangeListener(WLScriptContext context) {
      this.ctx = context;
   }

   public void handleNotification(Notification notification, Object handback) {
      if (this.ctx != null) {
         if (!this.ctx.atBeanLevel) {
            MBeanServerNotification serverNotification = null;
            ObjectName wlsObjectName = null;
            if (notification instanceof MBeanServerNotification) {
               serverNotification = (MBeanServerNotification)notification;

               try {
                  wlsObjectName = serverNotification.getMBeanName();
                  if (this.ctx.inNewTree() && !wlsObjectName.getDomain().equals("com.bea")) {
                     return;
                  }

                  if (!this.ctx.inNewTree() && wlsObjectName.getDomain().equals("com.bea")) {
                     return;
                  }
               } catch (Exception var10) {
                  return;
               }

               boolean isRegister = serverNotification.getType().equals("JMX.mbean.registered");
               String mbeanType = null;

               try {
                  mbeanType = (String)this.ctx.prompts.peek();
               } catch (EmptyStackException var9) {
                  return;
               }

               mbeanType = this.ctx.getRightType(mbeanType);

               try {
                  if (mbeanType.equals(wlsObjectName.getKeyProperty("Type"))) {
                     if (isRegister) {
                        if (this.ctx.inMBeanType) {
                           this.ctx.setInstanceObjectName(wlsObjectName);
                        } else if (this.ctx.inMBeanTypes) {
                           this.ctx.addInstanceObjectName(wlsObjectName);
                        }
                     } else if (this.ctx.inMBeanType) {
                        this.ctx.setInstanceObjectName((ObjectName)null);
                     } else if (this.ctx.inMBeanTypes) {
                        this.ctx.removeInstanceObjectName(wlsObjectName);
                     }
                  }
               } catch (ArrayIndexOutOfBoundsException var8) {
               }
            }

         }
      }
   }
}
