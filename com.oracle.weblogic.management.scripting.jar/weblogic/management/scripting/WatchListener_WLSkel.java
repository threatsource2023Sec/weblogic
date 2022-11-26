package weblogic.management.scripting;

import java.io.IOException;
import java.rmi.UnmarshalException;
import javax.management.Notification;
import weblogic.management.RemoteNotificationListener;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class WatchListener_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$lang$Object;
   // $FF: synthetic field
   private static Class class$javax$management$Notification;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      switch (var1) {
         case 0:
            Notification var5;
            Object var6;
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (Notification)var7.readObject(class$javax$management$Notification == null ? (class$javax$management$Notification = class$("javax.management.Notification")) : class$javax$management$Notification);
               var6 = (Object)var7.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var9) {
               throw new UnmarshalException("error unmarshalling arguments", var9);
            } catch (ClassNotFoundException var10) {
               throw new UnmarshalException("error unmarshalling arguments", var10);
            }

            ((RemoteNotificationListener)var4).handleNotification(var5, var6);
            this.associateResponseData(var2, var3);
            return var3;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            ((RemoteNotificationListener)var3).handleNotification((Notification)var2[0], (Object)var2[1]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
