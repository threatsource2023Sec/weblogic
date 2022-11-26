package weblogic.cluster.replication;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import java.util.concurrent.Future;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class ResourceGroupMigrationManagerImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$lang$String;
   // $FF: synthetic field
   private static Class class$java$util$concurrent$Future;
   // $FF: synthetic field
   private static Class class$weblogic$cluster$replication$Status;

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
      switch (var1) {
         case 0:
            try {
               MsgInput var8 = var2.getMsgInput();
               var5 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var6 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var7 = (String)var8.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
            } catch (IOException var17) {
               throw new UnmarshalException("error unmarshalling arguments", var17);
            } catch (ClassNotFoundException var18) {
               throw new UnmarshalException("error unmarshalling arguments", var18);
            }

            Future var19 = ((ResourceGroupMigrationManager)var4).initiateResourceGroupMigration(var5, var6, var7);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var19, class$java$util$concurrent$Future == null ? (class$java$util$concurrent$Future = class$("java.util.concurrent.Future")) : class$java$util$concurrent$Future);
               break;
            } catch (IOException var16) {
               throw new MarshalException("error marshalling return", var16);
            }
         case 1:
            int var9;
            try {
               MsgInput var10 = var2.getMsgInput();
               var5 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var6 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var7 = (String)var10.readObject(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               var9 = var10.readInt();
            } catch (IOException var14) {
               throw new UnmarshalException("error unmarshalling arguments", var14);
            } catch (ClassNotFoundException var15) {
               throw new UnmarshalException("error unmarshalling arguments", var15);
            }

            Status var11 = ((ResourceGroupMigrationManager)var4).initiateResourceGroupMigration(var5, var6, var7, var9);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var11, class$weblogic$cluster$replication$Status == null ? (class$weblogic$cluster$replication$Status = class$("weblogic.cluster.replication.Status")) : class$weblogic$cluster$replication$Status);
               break;
            } catch (IOException var13) {
               throw new MarshalException("error marshalling return", var13);
            }
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }

      return var3;
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            return ((ResourceGroupMigrationManager)var3).initiateResourceGroupMigration((String)var2[0], (String)var2[1], (String)var2[2]);
         case 1:
            return ((ResourceGroupMigrationManager)var3).initiateResourceGroupMigration((String)var2[0], (String)var2[1], (String)var2[2], (Integer)var2[3]);
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
