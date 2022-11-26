package com.solarmetric.manage.jmx.remote.jboss4;

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.rmi.server.RemoteRef;
import java.rmi.server.RemoteStub;
import javax.management.Notification;
import org.jboss.jmx.adaptor.rmi.RMINotificationListener;

public final class JBoss4RMINotificationListener_Stub extends RemoteStub implements RMINotificationListener, Remote {
   private static final long serialVersionUID = 2L;
   private static Method $method_handleNotification_0;
   // $FF: synthetic field
   static Class class$org$jboss$jmx$adaptor$rmi$RMINotificationListener;
   // $FF: synthetic field
   static Class class$javax$management$Notification;
   // $FF: synthetic field
   static Class class$java$lang$Object;

   static {
      try {
         $method_handleNotification_0 = (class$org$jboss$jmx$adaptor$rmi$RMINotificationListener != null ? class$org$jboss$jmx$adaptor$rmi$RMINotificationListener : (class$org$jboss$jmx$adaptor$rmi$RMINotificationListener = class$("org.jboss.jmx.adaptor.rmi.RMINotificationListener"))).getMethod("handleNotification", class$javax$management$Notification != null ? class$javax$management$Notification : (class$javax$management$Notification = class$("javax.management.Notification")), class$java$lang$Object != null ? class$java$lang$Object : (class$java$lang$Object = class$("java.lang.Object")));
      } catch (NoSuchMethodException var0) {
         throw new NoSuchMethodError("stub class initialization failed");
      }
   }

   public JBoss4RMINotificationListener_Stub(RemoteRef var1) {
      super(var1);
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public void handleNotification(Notification var1, Object var2) throws RemoteException {
      try {
         super.ref.invoke(this, $method_handleNotification_0, new Object[]{var1, var2}, -1982336311472540462L);
      } catch (RuntimeException var4) {
         throw var4;
      } catch (RemoteException var5) {
         throw var5;
      } catch (Exception var6) {
         throw new UnexpectedException("undeclared checked exception", var6);
      }
   }
}
