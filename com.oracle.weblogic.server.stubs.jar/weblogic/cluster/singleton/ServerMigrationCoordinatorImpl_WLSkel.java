package weblogic.cluster.singleton;

import java.io.IOException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class ServerMigrationCoordinatorImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$lang$String;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      String var5;
      String var6;
      String var7;
      boolean var8;
      boolean var9;
      switch (var1) {
         case 0:
            try {
               MsgInput var10 = var2.getMsgInput();
               var5 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var6 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var7 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var8 = var10.readBoolean();
               var9 = var10.readBoolean();
            } catch (IOException var16) {
               throw new UnmarshalException("error unmarshalling arguments", var16);
            } catch (ClassNotFoundException var17) {
               throw new UnmarshalException("error unmarshalling arguments", var17);
            }

            ((ServerMigrationCoordinator)var4).migrate(var5, var6, var7, var8, var9);
            this.associateResponseData(var2, var3);
            break;
         case 1:
            String var11;
            try {
               MsgInput var12 = var2.getMsgInput();
               var5 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var6 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var7 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var8 = var12.readBoolean();
               var9 = var12.readBoolean();
               var11 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var14) {
               throw new UnmarshalException("error unmarshalling arguments", var14);
            } catch (ClassNotFoundException var15) {
               throw new UnmarshalException("error unmarshalling arguments", var15);
            }

            ((ServerMigrationCoordinator)var4).migrate(var5, var6, var7, var8, var9, var11);
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
            ((ServerMigrationCoordinator)var3).migrate((String)var2[0], (String)var2[1], (String)var2[2], (Boolean)var2[3], (Boolean)var2[4]);
            return null;
         case 1:
            ((ServerMigrationCoordinator)var3).migrate((String)var2[0], (String)var2[1], (String)var2[2], (Boolean)var2[3], (Boolean)var2[4], (String)var2[5]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
