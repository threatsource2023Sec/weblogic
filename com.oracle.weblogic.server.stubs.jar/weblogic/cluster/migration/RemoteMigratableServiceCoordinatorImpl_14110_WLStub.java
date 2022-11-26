package weblogic.cluster.migration;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import javax.transaction.Transaction;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.store.PersistentStoreException;
import weblogic.transaction.TransactionHelper;

public final class RemoteMigratableServiceCoordinatorImpl_14110_WLStub extends Stub implements StubInfoIntf, RemoteMigratableServiceCoordinator {
   // $FF: synthetic field
   private static Class class$weblogic$cluster$migration$RemoteMigratableServiceCoordinator;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md2;
   private static RuntimeMethodDescriptor md3;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public RemoteMigratableServiceCoordinatorImpl_14110_WLStub(StubInfo var1) {
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

   public final void deactivateJTA(String var1, String var2) throws RemoteException, MigrationException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md0, var4, m[0]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (RemoteException var14) {
         throw var14;
      } catch (MigrationException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         md0 = new MethodDescriptor(m[0], class$weblogic$cluster$migration$RemoteMigratableServiceCoordinator == null ? (class$weblogic$cluster$migration$RemoteMigratableServiceCoordinator = class$("weblogic.cluster.migration.RemoteMigratableServiceCoordinator")) : class$weblogic$cluster$migration$RemoteMigratableServiceCoordinator, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$cluster$migration$RemoteMigratableServiceCoordinator == null ? (class$weblogic$cluster$migration$RemoteMigratableServiceCoordinator = class$("weblogic.cluster.migration.RemoteMigratableServiceCoordinator")) : class$weblogic$cluster$migration$RemoteMigratableServiceCoordinator, false, false, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$cluster$migration$RemoteMigratableServiceCoordinator == null ? (class$weblogic$cluster$migration$RemoteMigratableServiceCoordinator = class$("weblogic.cluster.migration.RemoteMigratableServiceCoordinator")) : class$weblogic$cluster$migration$RemoteMigratableServiceCoordinator, false, false, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$cluster$migration$RemoteMigratableServiceCoordinator == null ? (class$weblogic$cluster$migration$RemoteMigratableServiceCoordinator = class$("weblogic.cluster.migration.RemoteMigratableServiceCoordinator")) : class$weblogic$cluster$migration$RemoteMigratableServiceCoordinator, false, false, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         initialized = true;
      }
   }

   public final String getCurrentLocationOfJTA(String var1) throws RemoteException, PersistentStoreException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      String var18;
      try {
         Object[] var3 = new Object[]{var1};
         var18 = (String)this.ror.invoke(this, md1, var3, m[1]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (RemoteException var14) {
         throw var14;
      } catch (PersistentStoreException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var18;
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final void migrateJTA(String var1, String var2, boolean var3, boolean var4) throws MigrationException {
      Transaction var5 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var6 = new Object[]{var1, var2, new Boolean(var3), new Boolean(var4)};
         this.ror.invoke(this, md2, var6, m[2]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (MigrationException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var5);
      }

   }

   public final void setCurrentLocation(String var1, String var2) throws RemoteException, PersistentStoreException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md3, var4, m[3]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (RemoteException var14) {
         throw var14;
      } catch (PersistentStoreException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }
}
