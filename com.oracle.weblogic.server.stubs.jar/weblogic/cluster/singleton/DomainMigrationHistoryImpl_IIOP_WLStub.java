package weblogic.cluster.singleton;

import java.lang.reflect.Method;
import javax.transaction.Transaction;
import weblogic.corba.rmi.Stub;
import weblogic.management.provider.DomainMigrationHistory;
import weblogic.management.provider.MigrationData;
import weblogic.management.provider.ServerMachineMigrationData;
import weblogic.management.runtime.MigrationDataRuntimeMBean;
import weblogic.management.runtime.ServiceMigrationDataRuntimeMBean;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class DomainMigrationHistoryImpl_IIOP_WLStub extends Stub implements StubInfoIntf, DomainMigrationHistory {
   // $FF: synthetic field
   private static Class class$weblogic$management$provider$DomainMigrationHistory;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md2;
   private static RuntimeMethodDescriptor md3;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public DomainMigrationHistoryImpl_IIOP_WLStub(StubInfo var1) {
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
         md0 = new MethodDescriptor(m[0], class$weblogic$management$provider$DomainMigrationHistory == null ? (class$weblogic$management$provider$DomainMigrationHistory = class$("weblogic.management.provider.DomainMigrationHistory")) : class$weblogic$management$provider$DomainMigrationHistory, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$management$provider$DomainMigrationHistory == null ? (class$weblogic$management$provider$DomainMigrationHistory = class$("weblogic.management.provider.DomainMigrationHistory")) : class$weblogic$management$provider$DomainMigrationHistory, false, false, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$management$provider$DomainMigrationHistory == null ? (class$weblogic$management$provider$DomainMigrationHistory = class$("weblogic.management.provider.DomainMigrationHistory")) : class$weblogic$management$provider$DomainMigrationHistory, false, false, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$management$provider$DomainMigrationHistory == null ? (class$weblogic$management$provider$DomainMigrationHistory = class$("weblogic.management.provider.DomainMigrationHistory")) : class$weblogic$management$provider$DomainMigrationHistory, true, false, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         initialized = true;
      }
   }

   public final MigrationDataRuntimeMBean[] getMigrationDataRuntimes() {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      MigrationDataRuntimeMBean[] var13;
      try {
         Object[] var2 = new Object[0];
         var13 = (MigrationDataRuntimeMBean[])this.ror.invoke(this, md0, var2, m[0]);
      } catch (Error var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw var10;
      } catch (Throwable var11) {
         throw new RemoteRuntimeException("Unexpected Exception", var11);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

      return var13;
   }

   public final ServerMachineMigrationData[] getServerMachineMigrations() {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      ServerMachineMigrationData[] var13;
      try {
         Object[] var2 = new Object[0];
         var13 = (ServerMachineMigrationData[])this.ror.invoke(this, md1, var2, m[1]);
      } catch (Error var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw var10;
      } catch (Throwable var11) {
         throw new RemoteRuntimeException("Unexpected Exception", var11);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

      return var13;
   }

   public final ServiceMigrationDataRuntimeMBean[] getServiceMigrationDataRuntimes() {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      ServiceMigrationDataRuntimeMBean[] var13;
      try {
         Object[] var2 = new Object[0];
         var13 = (ServiceMigrationDataRuntimeMBean[])this.ror.invoke(this, md2, var2, m[2]);
      } catch (Error var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw var10;
      } catch (Throwable var11) {
         throw new RemoteRuntimeException("Unexpected Exception", var11);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

      return var13;
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final void update(MigrationData var1) {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var3 = new Object[]{var1};
         this.ror.invoke(this, md3, var3, m[3]);
      } catch (Error var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw var10;
      } catch (Throwable var11) {
         throw new RemoteRuntimeException("Unexpected Exception", var11);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

   }
}
