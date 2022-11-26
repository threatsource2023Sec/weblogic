package weblogic.cluster.singleton;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import weblogic.management.provider.DomainMigrationHistory;
import weblogic.management.provider.MigrationData;
import weblogic.management.provider.ServerMachineMigrationData;
import weblogic.management.runtime.MigrationDataRuntimeMBean;
import weblogic.management.runtime.ServiceMigrationDataRuntimeMBean;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class DomainMigrationHistoryImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class array$Lweblogic$management$provider$ServerMachineMigrationData;
   // $FF: synthetic field
   private static Class array$Lweblogic$management$runtime$MigrationDataRuntimeMBean;
   // $FF: synthetic field
   private static Class array$Lweblogic$management$runtime$ServiceMigrationDataRuntimeMBean;
   // $FF: synthetic field
   private static Class class$weblogic$management$provider$MigrationData;

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
            MigrationDataRuntimeMBean[] var15 = ((DomainMigrationHistory)var4).getMigrationDataRuntimes();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var15, array$Lweblogic$management$runtime$MigrationDataRuntimeMBean == null ? (array$Lweblogic$management$runtime$MigrationDataRuntimeMBean = class$("[Lweblogic.management.runtime.MigrationDataRuntimeMBean;")) : array$Lweblogic$management$runtime$MigrationDataRuntimeMBean);
               break;
            } catch (IOException var12) {
               throw new MarshalException("error marshalling return", var12);
            }
         case 1:
            ServerMachineMigrationData[] var14 = ((DomainMigrationHistory)var4).getServerMachineMigrations();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var14, array$Lweblogic$management$provider$ServerMachineMigrationData == null ? (array$Lweblogic$management$provider$ServerMachineMigrationData = class$("[Lweblogic.management.provider.ServerMachineMigrationData;")) : array$Lweblogic$management$provider$ServerMachineMigrationData);
               break;
            } catch (IOException var11) {
               throw new MarshalException("error marshalling return", var11);
            }
         case 2:
            ServiceMigrationDataRuntimeMBean[] var13 = ((DomainMigrationHistory)var4).getServiceMigrationDataRuntimes();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var13, array$Lweblogic$management$runtime$ServiceMigrationDataRuntimeMBean == null ? (array$Lweblogic$management$runtime$ServiceMigrationDataRuntimeMBean = class$("[Lweblogic.management.runtime.ServiceMigrationDataRuntimeMBean;")) : array$Lweblogic$management$runtime$ServiceMigrationDataRuntimeMBean);
               break;
            } catch (IOException var10) {
               throw new MarshalException("error marshalling return", var10);
            }
         case 3:
            MigrationData var5;
            try {
               MsgInput var6 = var2.getMsgInput();
               var5 = (MigrationData)var6.readObject(class$weblogic$management$provider$MigrationData == null ? (class$weblogic$management$provider$MigrationData = class$("weblogic.management.provider.MigrationData")) : class$weblogic$management$provider$MigrationData);
            } catch (IOException var8) {
               throw new UnmarshalException("error unmarshalling arguments", var8);
            } catch (ClassNotFoundException var9) {
               throw new UnmarshalException("error unmarshalling arguments", var9);
            }

            ((DomainMigrationHistory)var4).update(var5);
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
            return ((DomainMigrationHistory)var3).getMigrationDataRuntimes();
         case 1:
            return ((DomainMigrationHistory)var3).getServerMachineMigrations();
         case 2:
            return ((DomainMigrationHistory)var3).getServiceMigrationDataRuntimes();
         case 3:
            ((DomainMigrationHistory)var3).update((MigrationData)var2[0]);
            return null;
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
