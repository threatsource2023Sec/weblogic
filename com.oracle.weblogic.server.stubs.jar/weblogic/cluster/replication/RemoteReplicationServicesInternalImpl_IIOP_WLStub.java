package weblogic.cluster.replication;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import javax.transaction.Transaction;
import weblogic.corba.rmi.Stub;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class RemoteReplicationServicesInternalImpl_IIOP_WLStub extends Stub implements StubInfoIntf, ReplicationServicesInternal {
   // $FF: synthetic field
   private static Class class$weblogic$cluster$replication$ReplicationServicesInternal;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md10;
   private static RuntimeMethodDescriptor md2;
   private static RuntimeMethodDescriptor md3;
   private static RuntimeMethodDescriptor md4;
   private static RuntimeMethodDescriptor md5;
   private static RuntimeMethodDescriptor md6;
   private static RuntimeMethodDescriptor md7;
   private static RuntimeMethodDescriptor md8;
   private static RuntimeMethodDescriptor md9;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public RemoteReplicationServicesInternalImpl_IIOP_WLStub(StubInfo var1) {
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

   public final void copyUpdate(ROID var1, int var2, Serializable var3, Object var4) throws NotFoundException, RemoteException {
      Transaction var5 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var6 = new Object[]{var1, new Integer(var2), var3, var4};
         this.ror.invoke(this, md0, var6, m[0]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (NotFoundException var16) {
         throw var16;
      } catch (RemoteException var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var5);
      }

   }

   public final void copyUpdateOneWay(ROID var1, int var2, Serializable var3, Object var4) throws RemoteException {
      Transaction var5 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var6 = new Object[]{var1, new Integer(var2), var3, var4};
         this.ror.invoke(this, md1, var6, m[1]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (RemoteException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var5);
      }

   }

   public final Object create(HostID var1, int var2, ROID var3, Serializable var4) throws RemoteException {
      Transaction var5 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Object var19;
      try {
         Object[] var6 = new Object[]{var1, new Integer(var2), var3, var4};
         var19 = (Object)this.ror.invoke(this, md2, var6, m[2]);
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

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         md0 = new MethodDescriptor(m[0], class$weblogic$cluster$replication$ReplicationServicesInternal == null ? (class$weblogic$cluster$replication$ReplicationServicesInternal = class$("weblogic.cluster.replication.ReplicationServicesInternal")) : class$weblogic$cluster$replication$ReplicationServicesInternal, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$cluster$replication$ReplicationServicesInternal == null ? (class$weblogic$cluster$replication$ReplicationServicesInternal = class$("weblogic.cluster.replication.ReplicationServicesInternal")) : class$weblogic$cluster$replication$ReplicationServicesInternal, true, false, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$cluster$replication$ReplicationServicesInternal == null ? (class$weblogic$cluster$replication$ReplicationServicesInternal = class$("weblogic.cluster.replication.ReplicationServicesInternal")) : class$weblogic$cluster$replication$ReplicationServicesInternal, false, false, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$cluster$replication$ReplicationServicesInternal == null ? (class$weblogic$cluster$replication$ReplicationServicesInternal = class$("weblogic.cluster.replication.ReplicationServicesInternal")) : class$weblogic$cluster$replication$ReplicationServicesInternal, false, false, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         md4 = new MethodDescriptor(m[4], class$weblogic$cluster$replication$ReplicationServicesInternal == null ? (class$weblogic$cluster$replication$ReplicationServicesInternal = class$("weblogic.cluster.replication.ReplicationServicesInternal")) : class$weblogic$cluster$replication$ReplicationServicesInternal, true, false, false, false, var0.getTimeOut(m[4]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[4]));
         md5 = new MethodDescriptor(m[5], class$weblogic$cluster$replication$ReplicationServicesInternal == null ? (class$weblogic$cluster$replication$ReplicationServicesInternal = class$("weblogic.cluster.replication.ReplicationServicesInternal")) : class$weblogic$cluster$replication$ReplicationServicesInternal, false, false, false, false, var0.getTimeOut(m[5]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[5]));
         md6 = new MethodDescriptor(m[6], class$weblogic$cluster$replication$ReplicationServicesInternal == null ? (class$weblogic$cluster$replication$ReplicationServicesInternal = class$("weblogic.cluster.replication.ReplicationServicesInternal")) : class$weblogic$cluster$replication$ReplicationServicesInternal, true, false, false, false, var0.getTimeOut(m[6]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[6]));
         md7 = new MethodDescriptor(m[7], class$weblogic$cluster$replication$ReplicationServicesInternal == null ? (class$weblogic$cluster$replication$ReplicationServicesInternal = class$("weblogic.cluster.replication.ReplicationServicesInternal")) : class$weblogic$cluster$replication$ReplicationServicesInternal, false, false, false, false, var0.getTimeOut(m[7]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[7]));
         md8 = new MethodDescriptor(m[8], class$weblogic$cluster$replication$ReplicationServicesInternal == null ? (class$weblogic$cluster$replication$ReplicationServicesInternal = class$("weblogic.cluster.replication.ReplicationServicesInternal")) : class$weblogic$cluster$replication$ReplicationServicesInternal, false, false, false, false, var0.getTimeOut(m[8]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[8]));
         md9 = new MethodDescriptor(m[9], class$weblogic$cluster$replication$ReplicationServicesInternal == null ? (class$weblogic$cluster$replication$ReplicationServicesInternal = class$("weblogic.cluster.replication.ReplicationServicesInternal")) : class$weblogic$cluster$replication$ReplicationServicesInternal, false, false, false, false, var0.getTimeOut(m[9]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[9]));
         md10 = new MethodDescriptor(m[10], class$weblogic$cluster$replication$ReplicationServicesInternal == null ? (class$weblogic$cluster$replication$ReplicationServicesInternal = class$("weblogic.cluster.replication.ReplicationServicesInternal")) : class$weblogic$cluster$replication$ReplicationServicesInternal, true, false, false, false, var0.getTimeOut(m[10]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[10]));
         initialized = true;
      }
   }

   public final ROObject fetch(ROID var1) throws RemoteException, NotFoundException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      ROObject var18;
      try {
         Object[] var3 = new Object[]{var1};
         var18 = (ROObject)this.ror.invoke(this, md3, var3, m[3]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (RemoteException var14) {
         throw var14;
      } catch (NotFoundException var15) {
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

   public final void remove(ROID[] var1) throws RemoteException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var3 = new Object[]{var1};
         this.ror.invoke(this, md4, var3, m[4]);
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

   public final void remove(ROID[] var1, Object var2) throws RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md5, var4, m[5]);
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

   public final void removeOneWay(ROID[] var1, Object var2) throws RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md6, var4, m[6]);
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

   public final void removeOrphanedSessionOnCondition(ROID var1, int var2, Object var3) throws RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, new Integer(var2), var3};
         this.ror.invoke(this, md7, var5, m[7]);
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

   public final void update(AsyncBatch var1) throws RemoteException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var3 = new Object[]{var1};
         this.ror.invoke(this, md8, var3, m[8]);
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

   public final void update(ROID var1, int var2, Serializable var3, Object var4) throws NotFoundException, RemoteException {
      Transaction var5 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var6 = new Object[]{var1, new Integer(var2), var3, var4};
         this.ror.invoke(this, md9, var6, m[9]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (NotFoundException var16) {
         throw var16;
      } catch (RemoteException var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var5);
      }

   }

   public final void updateOneWay(ROID var1, int var2, Serializable var3, Object var4) throws RemoteException {
      Transaction var5 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var6 = new Object[]{var1, new Integer(var2), var3, var4};
         this.ror.invoke(this, md10, var6, m[10]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (RemoteException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var5);
      }

   }
}
