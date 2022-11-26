package weblogic.messaging.path;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import javax.transaction.Transaction;
import weblogic.rmi.extensions.AsyncResult;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class AsyncMapImpl_14110_WLStub extends Stub implements StubInfoIntf, AsyncMapRemote {
   // $FF: synthetic field
   private static Class class$weblogic$messaging$path$AsyncMapRemote;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md2;
   private static RuntimeMethodDescriptor md3;
   private static RuntimeMethodDescriptor md4;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public AsyncMapImpl_14110_WLStub(StubInfo var1) {
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
         md0 = new MethodDescriptor(m[0], class$weblogic$messaging$path$AsyncMapRemote == null ? (class$weblogic$messaging$path$AsyncMapRemote = class$("weblogic.messaging.path.AsyncMapRemote")) : class$weblogic$messaging$path$AsyncMapRemote, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$messaging$path$AsyncMapRemote == null ? (class$weblogic$messaging$path$AsyncMapRemote = class$("weblogic.messaging.path.AsyncMapRemote")) : class$weblogic$messaging$path$AsyncMapRemote, false, true, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$messaging$path$AsyncMapRemote == null ? (class$weblogic$messaging$path$AsyncMapRemote = class$("weblogic.messaging.path.AsyncMapRemote")) : class$weblogic$messaging$path$AsyncMapRemote, false, false, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$messaging$path$AsyncMapRemote == null ? (class$weblogic$messaging$path$AsyncMapRemote = class$("weblogic.messaging.path.AsyncMapRemote")) : class$weblogic$messaging$path$AsyncMapRemote, false, false, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         md4 = new MethodDescriptor(m[4], class$weblogic$messaging$path$AsyncMapRemote == null ? (class$weblogic$messaging$path$AsyncMapRemote = class$("weblogic.messaging.path.AsyncMapRemote")) : class$weblogic$messaging$path$AsyncMapRemote, false, false, false, false, var0.getTimeOut(m[4]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[4]));
         initialized = true;
      }
   }

   public final void get(Serializable var1, AsyncResult var2) throws RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md0, var4, m[0]);
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

   public final String getJndiName() {
      try {
         Object[] var1 = new Object[0];
         return (String)this.ror.invoke(this, md1, var1, m[1]);
      } catch (Error var2) {
         throw var2;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RemoteRuntimeException("Unexpected Exception", var4);
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final void put(Serializable var1, Serializable var2, AsyncResult var3) throws RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         this.ror.invoke(this, md2, var5, m[2]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (RemoteException var14) {
         throw var14;
      } catch (Throwable var15) {
         throw new RemoteRuntimeException("Unexpected Exception", var15);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }

   public final void putIfAbsent(Serializable var1, Serializable var2, AsyncResult var3) throws RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         this.ror.invoke(this, md3, var5, m[3]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (RemoteException var14) {
         throw var14;
      } catch (Throwable var15) {
         throw new RemoteRuntimeException("Unexpected Exception", var15);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }

   public final void remove(Serializable var1, Serializable var2, AsyncResult var3) throws RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         this.ror.invoke(this, md4, var5, m[4]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (RemoteException var14) {
         throw var14;
      } catch (Throwable var15) {
         throw new RemoteRuntimeException("Unexpected Exception", var15);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }
}
