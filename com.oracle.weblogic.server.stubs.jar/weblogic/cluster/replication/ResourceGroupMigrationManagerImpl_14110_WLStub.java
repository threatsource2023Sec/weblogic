package weblogic.cluster.replication;

import java.lang.reflect.Method;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import javax.transaction.Transaction;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class ResourceGroupMigrationManagerImpl_14110_WLStub extends Stub implements StubInfoIntf, ResourceGroupMigrationManager {
   // $FF: synthetic field
   private static Class class$weblogic$cluster$replication$ResourceGroupMigrationManager;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public ResourceGroupMigrationManagerImpl_14110_WLStub(StubInfo var1) {
      super(var1);
      this.stubinfo = var1;
      this.ror = this.stubinfo.getRemoteRef();
      ensureInitialized(this.stubinfo);
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         md0 = new MethodDescriptor(m[0], class$weblogic$cluster$replication$ResourceGroupMigrationManager == null ? (class$weblogic$cluster$replication$ResourceGroupMigrationManager = class$("weblogic.cluster.replication.ResourceGroupMigrationManager")) : class$weblogic$cluster$replication$ResourceGroupMigrationManager, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$cluster$replication$ResourceGroupMigrationManager == null ? (class$weblogic$cluster$replication$ResourceGroupMigrationManager = class$("weblogic.cluster.replication.ResourceGroupMigrationManager")) : class$weblogic$cluster$replication$ResourceGroupMigrationManager, false, false, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         initialized = true;
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final Future initiateResourceGroupMigration(String var1, String var2, String var3) throws MigrationInProgressException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Future var18;
      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         var18 = (Future)this.ror.invoke(this, md0, var5, m[0]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (MigrationInProgressException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

      return var18;
   }

   public final Status initiateResourceGroupMigration(String var1, String var2, String var3, int var4) throws TimeoutException, IllegalStateException, MigrationInProgressException {
      Transaction var5 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Status var23;
      try {
         Object[] var6 = new Object[]{var1, var2, var3, new Integer(var4)};
         var23 = (Status)this.ror.invoke(this, md1, var6, m[1]);
      } catch (Error var16) {
         throw var16;
      } catch (RuntimeException var17) {
         throw var17;
      } catch (TimeoutException var18) {
         throw var18;
      } catch (IllegalStateException var19) {
         throw var19;
      } catch (MigrationInProgressException var20) {
         throw var20;
      } catch (Throwable var21) {
         throw new RemoteRuntimeException("Unexpected Exception", var21);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var5);
      }

      return var23;
   }
}
