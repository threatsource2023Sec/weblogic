package weblogic.cluster.singleton;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class SingletonServicesManagerService_WLSkel extends Skeleton {
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
      switch (var1) {
         case 0:
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = (String)var6.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            } catch (ClassNotFoundException var23) {
               throw new UnmarshalException("error unmarshalling arguments", var23);
            }

            ((RemoteSingletonServicesControl)var4).activateService(var5);
            this.associateResponseData(var2, var3);
            break;
         case 1:
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (String)var7.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var20) {
               throw new UnmarshalException("error unmarshalling arguments", var20);
            } catch (ClassNotFoundException var21) {
               throw new UnmarshalException("error unmarshalling arguments", var21);
            }

            ((RemoteSingletonServicesControl)var4).deactivateService(var5);
            this.associateResponseData(var2, var3);
            break;
         case 2:
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var18) {
               throw new UnmarshalException("error unmarshalling arguments", var18);
            } catch (ClassNotFoundException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            }

            boolean var24 = ((RemoteSingletonServicesControl)var4).isServiceActive(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var24);
               break;
            } catch (IOException var17) {
               throw new MarshalException("error marshalling return", var17);
            }
         case 3:
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var15) {
               throw new UnmarshalException("error unmarshalling arguments", var15);
            } catch (ClassNotFoundException var16) {
               throw new UnmarshalException("error unmarshalling arguments", var16);
            }

            boolean var25 = ((RemoteSingletonServicesControl)var4).isServiceRegistered(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var25);
               break;
            } catch (IOException var14) {
               throw new MarshalException("error marshalling return", var14);
            }
         case 4:
            try {
               MsgInput var10 = var2.getMsgInput();
               var5 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var12) {
               throw new UnmarshalException("error unmarshalling arguments", var12);
            } catch (ClassNotFoundException var13) {
               throw new UnmarshalException("error unmarshalling arguments", var13);
            }

            ((RemoteSingletonServicesControl)var4).restartService(var5);
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
            ((RemoteSingletonServicesControl)var3).activateService((String)var2[0]);
            return null;
         case 1:
            ((RemoteSingletonServicesControl)var3).deactivateService((String)var2[0]);
            return null;
         case 2:
            return new Boolean(((RemoteSingletonServicesControl)var3).isServiceActive((String)var2[0]));
         case 3:
            return new Boolean(((RemoteSingletonServicesControl)var3).isServiceRegistered((String)var2[0]));
         case 4:
            ((RemoteSingletonServicesControl)var3).restartService((String)var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
