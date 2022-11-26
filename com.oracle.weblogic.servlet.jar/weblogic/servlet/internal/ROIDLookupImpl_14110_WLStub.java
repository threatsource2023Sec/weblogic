package weblogic.servlet.internal;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import javax.transaction.Transaction;
import weblogic.cluster.replication.ROID;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class ROIDLookupImpl_14110_WLStub extends Stub implements StubInfoIntf, ROIDLookup {
   // $FF: synthetic field
   private static Class class$weblogic$servlet$internal$ROIDLookup;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md2;
   private static RuntimeMethodDescriptor md3;
   private static RuntimeMethodDescriptor md4;
   private static RuntimeMethodDescriptor md5;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public ROIDLookupImpl_14110_WLStub(StubInfo var1) {
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
         md0 = new MethodDescriptor(m[0], class$weblogic$servlet$internal$ROIDLookup == null ? (class$weblogic$servlet$internal$ROIDLookup = class$("weblogic.servlet.internal.ROIDLookup")) : class$weblogic$servlet$internal$ROIDLookup, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$servlet$internal$ROIDLookup == null ? (class$weblogic$servlet$internal$ROIDLookup = class$("weblogic.servlet.internal.ROIDLookup")) : class$weblogic$servlet$internal$ROIDLookup, false, false, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$servlet$internal$ROIDLookup == null ? (class$weblogic$servlet$internal$ROIDLookup = class$("weblogic.servlet.internal.ROIDLookup")) : class$weblogic$servlet$internal$ROIDLookup, false, false, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$servlet$internal$ROIDLookup == null ? (class$weblogic$servlet$internal$ROIDLookup = class$("weblogic.servlet.internal.ROIDLookup")) : class$weblogic$servlet$internal$ROIDLookup, true, false, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         md4 = new MethodDescriptor(m[4], class$weblogic$servlet$internal$ROIDLookup == null ? (class$weblogic$servlet$internal$ROIDLookup = class$("weblogic.servlet.internal.ROIDLookup")) : class$weblogic$servlet$internal$ROIDLookup, false, false, false, false, var0.getTimeOut(m[4]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[4]));
         md5 = new MethodDescriptor(m[5], class$weblogic$servlet$internal$ROIDLookup == null ? (class$weblogic$servlet$internal$ROIDLookup = class$("weblogic.servlet.internal.ROIDLookup")) : class$weblogic$servlet$internal$ROIDLookup, false, false, false, false, var0.getTimeOut(m[5]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[5]));
         initialized = true;
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final boolean isAvailableInOtherCtx(String var1, String var2, String var3, String var4, String var5) throws RemoteException {
      Transaction var6 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      boolean var20;
      try {
         Object[] var7 = new Object[]{var1, var2, var3, var4, var5};
         var20 = (Boolean)this.ror.invoke(this, md0, var7, m[0]);
      } catch (Error var15) {
         throw var15;
      } catch (RuntimeException var16) {
         throw var16;
      } catch (RemoteException var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var6);
      }

      return var20;
   }

   public final ROID lookupROID(String var1, String var2, String var3) throws RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      ROID var18;
      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         var18 = (ROID)this.ror.invoke(this, md1, var5, m[1]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (RemoteException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

      return var18;
   }

   public final ROID lookupROID(String var1, String var2, String var3, String var4) throws RemoteException {
      Transaction var5 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      ROID var19;
      try {
         Object[] var6 = new Object[]{var1, var2, var3, var4};
         var19 = (ROID)this.ror.invoke(this, md2, var6, m[2]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var5);
      }

      return var19;
   }

   public final void unregister(ROID var1, Object[] var2) throws RemoteException {
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

   public final void updateLastAccessTimes(String var1, ROID[] var2, long[] var3, long var4, String var6) throws RemoteException {
      Transaction var7 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var8 = new Object[]{var1, var2, var3, new Long(var4), var6};
         this.ror.invoke(this, md4, var8, m[4]);
      } catch (Error var15) {
         throw var15;
      } catch (RuntimeException var16) {
         throw var16;
      } catch (RemoteException var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var7);
      }

   }

   public final void updateLastAccessTimes(ROID[] var1, long[] var2, long var3, String var5) throws RemoteException {
      Transaction var6 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var7 = new Object[]{var1, var2, new Long(var3), var5};
         this.ror.invoke(this, md5, var7, m[5]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var6);
      }

   }
}
