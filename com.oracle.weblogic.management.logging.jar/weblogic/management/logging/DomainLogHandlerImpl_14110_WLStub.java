package weblogic.management.logging;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.List;
import javax.transaction.Transaction;
import weblogic.logging.LogEntry;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class DomainLogHandlerImpl_14110_WLStub extends Stub implements StubInfoIntf, DomainLogHandler {
   // $FF: synthetic field
   private static Class class$weblogic$management$logging$DomainLogHandler;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md2;
   private static RuntimeMethodDescriptor md3;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public DomainLogHandlerImpl_14110_WLStub(StubInfo var1) {
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
         md0 = new MethodDescriptor(m[0], class$weblogic$management$logging$DomainLogHandler == null ? (class$weblogic$management$logging$DomainLogHandler = class$("weblogic.management.logging.DomainLogHandler")) : class$weblogic$management$logging$DomainLogHandler, true, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$management$logging$DomainLogHandler == null ? (class$weblogic$management$logging$DomainLogHandler = class$("weblogic.management.logging.DomainLogHandler")) : class$weblogic$management$logging$DomainLogHandler, true, false, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$management$logging$DomainLogHandler == null ? (class$weblogic$management$logging$DomainLogHandler = class$("weblogic.management.logging.DomainLogHandler")) : class$weblogic$management$logging$DomainLogHandler, true, false, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$management$logging$DomainLogHandler == null ? (class$weblogic$management$logging$DomainLogHandler = class$("weblogic.management.logging.DomainLogHandler")) : class$weblogic$management$logging$DomainLogHandler, true, false, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         initialized = true;
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final void ping() throws RemoteException {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var2 = new Object[0];
         this.ror.invoke(this, md0, var2, m[0]);
      } catch (Error var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw var10;
      } catch (RemoteException var11) {
         throw var11;
      } catch (Throwable var12) {
         throw new RemoteRuntimeException("Unexpected Exception", var12);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

   }

   public final void publishLogEntries(LogEntry[] var1) throws RemoteException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var3 = new Object[]{var1};
         this.ror.invoke(this, md1, var3, m[1]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (RemoteException var12) {
         throw var12;
      } catch (Throwable var13) {
         throw new RemoteRuntimeException("Unexpected Exception", var13);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

   }

   public final void sendALAlertTrap(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12) throws RemoteException {
      Transaction var13 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var14 = new Object[]{var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12};
         this.ror.invoke(this, md2, var14, m[2]);
      } catch (Error var21) {
         throw var21;
      } catch (RuntimeException var22) {
         throw var22;
      } catch (RemoteException var23) {
         throw var23;
      } catch (Throwable var24) {
         throw new RemoteRuntimeException("Unexpected Exception", var24);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var13);
      }

   }

   public final void sendTrap(String var1, List var2) throws RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md3, var4, m[3]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (RemoteException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }
}
