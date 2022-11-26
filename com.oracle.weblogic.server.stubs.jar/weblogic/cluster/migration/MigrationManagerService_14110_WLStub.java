package weblogic.cluster.migration;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.Collection;
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

public final class MigrationManagerService_14110_WLStub extends Stub implements StubInfoIntf, RemoteMigrationControl {
   // $FF: synthetic field
   private static Class class$weblogic$cluster$migration$RemoteMigrationControl;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md2;
   private static RuntimeMethodDescriptor md3;
   private static RuntimeMethodDescriptor md4;
   private static RuntimeMethodDescriptor md5;
   private static RuntimeMethodDescriptor md6;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public MigrationManagerService_14110_WLStub(StubInfo var1) {
      super(var1);
      this.stubinfo = var1;
      this.ror = this.stubinfo.getRemoteRef();
      ensureInitialized(this.stubinfo);
   }

   public final void activateTarget(String var1) throws RemoteException, MigrationException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var3 = new Object[]{var1};
         this.ror.invoke(this, md0, var3, m[0]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (RemoteException var13) {
         throw var13;
      } catch (MigrationException var14) {
         throw var14;
      } catch (Throwable var15) {
         throw new RemoteRuntimeException("Unexpected Exception", var15);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

   }

   public final Collection activatedTargets() throws RemoteException {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Collection var15;
      try {
         Object[] var2 = new Object[0];
         var15 = (Collection)this.ror.invoke(this, md1, var2, m[1]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (RemoteException var12) {
         throw var12;
      } catch (Throwable var13) {
         throw new RemoteRuntimeException("Unexpected Exception", var13);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

      return var15;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public final void deactivateTarget(String var1, String var2) throws RemoteException, MigrationException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md2, var4, m[2]);
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
         md0 = new MethodDescriptor(m[0], class$weblogic$cluster$migration$RemoteMigrationControl == null ? (class$weblogic$cluster$migration$RemoteMigrationControl = class$("weblogic.cluster.migration.RemoteMigrationControl")) : class$weblogic$cluster$migration$RemoteMigrationControl, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$cluster$migration$RemoteMigrationControl == null ? (class$weblogic$cluster$migration$RemoteMigrationControl = class$("weblogic.cluster.migration.RemoteMigrationControl")) : class$weblogic$cluster$migration$RemoteMigrationControl, false, false, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$cluster$migration$RemoteMigrationControl == null ? (class$weblogic$cluster$migration$RemoteMigrationControl = class$("weblogic.cluster.migration.RemoteMigrationControl")) : class$weblogic$cluster$migration$RemoteMigrationControl, false, false, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$cluster$migration$RemoteMigrationControl == null ? (class$weblogic$cluster$migration$RemoteMigrationControl = class$("weblogic.cluster.migration.RemoteMigrationControl")) : class$weblogic$cluster$migration$RemoteMigrationControl, false, false, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         md4 = new MethodDescriptor(m[4], class$weblogic$cluster$migration$RemoteMigrationControl == null ? (class$weblogic$cluster$migration$RemoteMigrationControl = class$("weblogic.cluster.migration.RemoteMigrationControl")) : class$weblogic$cluster$migration$RemoteMigrationControl, false, false, false, false, var0.getTimeOut(m[4]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[4]));
         md5 = new MethodDescriptor(m[5], class$weblogic$cluster$migration$RemoteMigrationControl == null ? (class$weblogic$cluster$migration$RemoteMigrationControl = class$("weblogic.cluster.migration.RemoteMigrationControl")) : class$weblogic$cluster$migration$RemoteMigrationControl, false, false, false, false, var0.getTimeOut(m[5]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[5]));
         md6 = new MethodDescriptor(m[6], class$weblogic$cluster$migration$RemoteMigrationControl == null ? (class$weblogic$cluster$migration$RemoteMigrationControl = class$("weblogic.cluster.migration.RemoteMigrationControl")) : class$weblogic$cluster$migration$RemoteMigrationControl, false, false, false, false, var0.getTimeOut(m[6]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[6]));
         initialized = true;
      }
   }

   public final int getMigratableState(String var1) throws RemoteException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      int var16;
      try {
         Object[] var3 = new Object[]{var1};
         var16 = (Integer)this.ror.invoke(this, md3, var3, m[3]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (RemoteException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var16;
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final void manualActivateDynamicService(String var1, String var2) throws RemoteException, MigrationException, IllegalArgumentException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md4, var4, m[4]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (RemoteException var15) {
         throw var15;
      } catch (MigrationException var16) {
         throw var16;
      } catch (IllegalArgumentException var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   public final void manualDeactivateDynamicService(String var1, String var2, String var3) throws RemoteException, MigrationException, IllegalArgumentException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         this.ror.invoke(this, md5, var5, m[5]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (MigrationException var17) {
         throw var17;
      } catch (IllegalArgumentException var18) {
         throw var18;
      } catch (Throwable var19) {
         throw new RemoteRuntimeException("Unexpected Exception", var19);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }

   public final void restartTarget(String var1) throws RemoteException, MigrationException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var3 = new Object[]{var1};
         this.ror.invoke(this, md6, var3, m[6]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (RemoteException var13) {
         throw var13;
      } catch (MigrationException var14) {
         throw var14;
      } catch (Throwable var15) {
         throw new RemoteRuntimeException("Unexpected Exception", var15);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

   }
}
