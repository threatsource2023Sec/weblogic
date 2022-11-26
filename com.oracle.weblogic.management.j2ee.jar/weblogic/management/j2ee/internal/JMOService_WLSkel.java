package weblogic.management.j2ee.internal;

import java.io.IOException;
import java.rmi.UnmarshalException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import weblogic.management.j2ee.ListenerRegistry;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class JMOService_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$lang$Object;
   // $FF: synthetic field
   private static Class class$javax$management$NotificationFilter;
   // $FF: synthetic field
   private static Class class$javax$management$NotificationListener;
   // $FF: synthetic field
   private static Class class$javax$management$ObjectName;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      ObjectName var5;
      NotificationListener var6;
      switch (var1) {
         case 0:
            Object var8;
            NotificationFilter var15;
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = (ObjectName)var9.readObject(class$javax$management$ObjectName == null ? (class$javax$management$ObjectName = class$("javax.management.ObjectName")) : class$javax$management$ObjectName);
               var6 = (NotificationListener)var9.readObject(class$javax$management$NotificationListener == null ? (class$javax$management$NotificationListener = class$("javax.management.NotificationListener")) : class$javax$management$NotificationListener);
               var15 = (NotificationFilter)var9.readObject(class$javax$management$NotificationFilter == null ? (class$javax$management$NotificationFilter = class$("javax.management.NotificationFilter")) : class$javax$management$NotificationFilter);
               var8 = (Object)var9.readObject(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
            } catch (IOException var13) {
               throw new UnmarshalException("error unmarshalling arguments", var13);
            } catch (ClassNotFoundException var14) {
               throw new UnmarshalException("error unmarshalling arguments", var14);
            }

            ((ListenerRegistry)var4).addListener(var5, var6, var15, var8);
            this.associateResponseData(var2, var3);
            break;
         case 1:
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (ObjectName)var7.readObject(class$javax$management$ObjectName == null ? (class$javax$management$ObjectName = class$("javax.management.ObjectName")) : class$javax$management$ObjectName);
               var6 = (NotificationListener)var7.readObject(class$javax$management$NotificationListener == null ? (class$javax$management$NotificationListener = class$("javax.management.NotificationListener")) : class$javax$management$NotificationListener);
            } catch (IOException var11) {
               throw new UnmarshalException("error unmarshalling arguments", var11);
            } catch (ClassNotFoundException var12) {
               throw new UnmarshalException("error unmarshalling arguments", var12);
            }

            ((ListenerRegistry)var4).removeListener(var5, var6);
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
            ((ListenerRegistry)var3).addListener((ObjectName)var2[0], (NotificationListener)var2[1], (NotificationFilter)var2[2], (Object)var2[3]);
            return null;
         case 1:
            ((ListenerRegistry)var3).removeListener((ObjectName)var2[0], (NotificationListener)var2[1]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
