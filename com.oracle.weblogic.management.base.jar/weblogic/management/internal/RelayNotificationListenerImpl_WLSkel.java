package weblogic.management.internal;

import java.io.IOException;
import java.rmi.UnmarshalException;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class RelayNotificationListenerImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$lang$Object;
   // $FF: synthetic field
   private static Class class$javax$management$Notification;
   // $FF: synthetic field
   private static Class class$javax$management$NotificationFilter;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      Object var6;
      switch (var1) {
         case 0:
            NotificationFilter var14;
            try {
               MsgInput var7 = var2.getMsgInput();
               var14 = (NotificationFilter)var7.readObject(class$javax$management$NotificationFilter == null ? (class$javax$management$NotificationFilter = class$("javax.management.NotificationFilter")) : class$javax$management$NotificationFilter);
               var6 = (Object)var7.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var12) {
               throw new UnmarshalException("error unmarshalling arguments", var12);
            } catch (ClassNotFoundException var13) {
               throw new UnmarshalException("error unmarshalling arguments", var13);
            }

            ((BaseNotificationListener)var4).addFilterAndHandback(var14, var6);
            this.associateResponseData(var2, var3);
            break;
         case 1:
            Notification var5;
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = (Notification)var8.readObject(class$javax$management$Notification == null ? (class$javax$management$Notification = class$("javax.management.Notification")) : class$javax$management$Notification);
               var6 = (Object)var8.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var10) {
               throw new UnmarshalException("error unmarshalling arguments", var10);
            } catch (ClassNotFoundException var11) {
               throw new UnmarshalException("error unmarshalling arguments", var11);
            }

            ((NotificationListener)var4).handleNotification(var5, var6);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            ((BaseNotificationListener)var4).remove();
            this.associateResponseData(var2, var3);
            break;
         case 3:
            ((BaseNotificationListener)var4).unregister();
            this.associateResponseData(var2, var3);
            break;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }

      return var3;
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            ((BaseNotificationListener)var3).addFilterAndHandback((NotificationFilter)var2[0], (Object)var2[1]);
            return null;
         case 1:
            ((NotificationListener)var3).handleNotification((Notification)var2[0], (Object)var2[1]);
            return null;
         case 2:
            ((BaseNotificationListener)var3).remove();
            return null;
         case 3:
            ((BaseNotificationListener)var3).unregister();
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
