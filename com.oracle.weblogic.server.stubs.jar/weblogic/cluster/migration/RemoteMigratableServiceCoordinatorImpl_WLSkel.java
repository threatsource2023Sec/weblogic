package weblogic.cluster.migration;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class RemoteMigratableServiceCoordinatorImpl_WLSkel extends Skeleton {
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
      String var8;
      switch (var1) {
         case 0:
            String var22;
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (String)var7.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var22 = (String)var7.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            } catch (ClassNotFoundException var21) {
               throw new UnmarshalException("error unmarshalling arguments", var21);
            }

            ((RemoteMigratableServiceCoordinator)var4).deactivateJTA(var5, var22);
            this.associateResponseData(var2, var3);
            break;
         case 1:
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = (String)var6.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var18) {
               throw new UnmarshalException("error unmarshalling arguments", var18);
            } catch (ClassNotFoundException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            }

            var8 = ((RemoteMigratableServiceCoordinator)var4).getCurrentLocationOfJTA(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var8, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var17) {
               throw new MarshalException("error marshalling return", var17);
            }
         case 2:
            boolean var10;
            boolean var23;
            try {
               MsgInput var11 = var2.getMsgInput();
               var5 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var8 = (String)var11.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var23 = var11.readBoolean();
               var10 = var11.readBoolean();
            } catch (IOException var15) {
               throw new UnmarshalException("error unmarshalling arguments", var15);
            } catch (ClassNotFoundException var16) {
               throw new UnmarshalException("error unmarshalling arguments", var16);
            }

            ((RemoteMigratableServiceCoordinator)var4).migrateJTA(var5, var8, var23, var10);
            this.associateResponseData(var2, var3);
            break;
         case 3:
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var8 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var13) {
               throw new UnmarshalException("error unmarshalling arguments", var13);
            } catch (ClassNotFoundException var14) {
               throw new UnmarshalException("error unmarshalling arguments", var14);
            }

            ((RemoteMigratableServiceCoordinator)var4).setCurrentLocation(var5, var8);
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
            ((RemoteMigratableServiceCoordinator)var3).deactivateJTA((String)var2[0], (String)var2[1]);
            return null;
         case 1:
            return ((RemoteMigratableServiceCoordinator)var3).getCurrentLocationOfJTA((String)var2[0]);
         case 2:
            ((RemoteMigratableServiceCoordinator)var3).migrateJTA((String)var2[0], (String)var2[1], (Boolean)var2[2], (Boolean)var2[3]);
            return null;
         case 3:
            ((RemoteMigratableServiceCoordinator)var3).setCurrentLocation((String)var2[0], (String)var2[1]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
