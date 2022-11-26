package weblogic.cluster.migration;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import java.util.Collection;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class MigrationManagerService_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$java$util$Collection;

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
      String var30;
      switch (var1) {
         case 0:
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = (String)var6.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var26) {
               throw new UnmarshalException("error unmarshalling arguments", var26);
            } catch (ClassNotFoundException var27) {
               throw new UnmarshalException("error unmarshalling arguments", var27);
            }

            ((RemoteMigrationControl)var4).activateTarget(var5);
            this.associateResponseData(var2, var3);
            break;
         case 1:
            Collection var28 = ((RemoteMigrationControl)var4).activatedTargets();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var28, class$java$util$Collection == null ? (class$java$util$Collection = class$("java.util.Collection")) : class$java$util$Collection);
               break;
            } catch (IOException var25) {
               throw new MarshalException("error marshalling return", var25);
            }
         case 2:
            String var29;
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var29 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var23) {
               throw new UnmarshalException("error unmarshalling arguments", var23);
            } catch (ClassNotFoundException var24) {
               throw new UnmarshalException("error unmarshalling arguments", var24);
            }

            ((RemoteMigrationControl)var4).deactivateTarget(var5, var29);
            this.associateResponseData(var2, var3);
            break;
         case 3:
            try {
               MsgInput var7 = var2.getMsgInput();
               var5 = (String)var7.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var21) {
               throw new UnmarshalException("error unmarshalling arguments", var21);
            } catch (ClassNotFoundException var22) {
               throw new UnmarshalException("error unmarshalling arguments", var22);
            }

            int var31 = ((RemoteMigrationControl)var4).getMigratableState(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var31);
               break;
            } catch (IOException var20) {
               throw new MarshalException("error marshalling return", var20);
            }
         case 4:
            try {
               MsgInput var10 = var2.getMsgInput();
               var5 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var30 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var18) {
               throw new UnmarshalException("error unmarshalling arguments", var18);
            } catch (ClassNotFoundException var19) {
               throw new UnmarshalException("error unmarshalling arguments", var19);
            }

            ((RemoteMigrationControl)var4).manualActivateDynamicService(var5, var30);
            this.associateResponseData(var2, var3);
            break;
         case 5:
            String var11;
            try {
               MsgInput var12 = var2.getMsgInput();
               var5 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var30 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var11 = (String)var12.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var16) {
               throw new UnmarshalException("error unmarshalling arguments", var16);
            } catch (ClassNotFoundException var17) {
               throw new UnmarshalException("error unmarshalling arguments", var17);
            }

            ((RemoteMigrationControl)var4).manualDeactivateDynamicService(var5, var30, var11);
            this.associateResponseData(var2, var3);
            break;
         case 6:
            try {
               MsgInput var9 = var2.getMsgInput();
               var5 = (String)var9.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var14) {
               throw new UnmarshalException("error unmarshalling arguments", var14);
            } catch (ClassNotFoundException var15) {
               throw new UnmarshalException("error unmarshalling arguments", var15);
            }

            ((RemoteMigrationControl)var4).restartTarget(var5);
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
            ((RemoteMigrationControl)var3).activateTarget((String)var2[0]);
            return null;
         case 1:
            return ((RemoteMigrationControl)var3).activatedTargets();
         case 2:
            ((RemoteMigrationControl)var3).deactivateTarget((String)var2[0], (String)var2[1]);
            return null;
         case 3:
            return new Integer(((RemoteMigrationControl)var3).getMigratableState((String)var2[0]));
         case 4:
            ((RemoteMigrationControl)var3).manualActivateDynamicService((String)var2[0], (String)var2[1]);
            return null;
         case 5:
            ((RemoteMigrationControl)var3).manualDeactivateDynamicService((String)var2[0], (String)var2[1], (String)var2[2]);
            return null;
         case 6:
            ((RemoteMigrationControl)var3).restartTarget((String)var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
