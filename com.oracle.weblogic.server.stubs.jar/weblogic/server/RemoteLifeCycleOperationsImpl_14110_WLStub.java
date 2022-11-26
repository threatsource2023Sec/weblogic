package weblogic.server;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.Map;
import javax.transaction.Transaction;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class RemoteLifeCycleOperationsImpl_14110_WLStub extends Stub implements StubInfoIntf, RemoteLifeCycleOperations {
   // $FF: synthetic field
   private static Class class$weblogic$server$RemoteLifeCycleOperations;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md10;
   private static RuntimeMethodDescriptor md11;
   private static RuntimeMethodDescriptor md12;
   private static RuntimeMethodDescriptor md13;
   private static RuntimeMethodDescriptor md14;
   private static RuntimeMethodDescriptor md15;
   private static RuntimeMethodDescriptor md16;
   private static RuntimeMethodDescriptor md17;
   private static RuntimeMethodDescriptor md18;
   private static RuntimeMethodDescriptor md19;
   private static RuntimeMethodDescriptor md2;
   private static RuntimeMethodDescriptor md20;
   private static RuntimeMethodDescriptor md21;
   private static RuntimeMethodDescriptor md22;
   private static RuntimeMethodDescriptor md23;
   private static RuntimeMethodDescriptor md24;
   private static RuntimeMethodDescriptor md25;
   private static RuntimeMethodDescriptor md26;
   private static RuntimeMethodDescriptor md27;
   private static RuntimeMethodDescriptor md28;
   private static RuntimeMethodDescriptor md29;
   private static RuntimeMethodDescriptor md3;
   private static RuntimeMethodDescriptor md30;
   private static RuntimeMethodDescriptor md4;
   private static RuntimeMethodDescriptor md5;
   private static RuntimeMethodDescriptor md6;
   private static RuntimeMethodDescriptor md7;
   private static RuntimeMethodDescriptor md8;
   private static RuntimeMethodDescriptor md9;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public RemoteLifeCycleOperationsImpl_14110_WLStub(StubInfo var1) {
      super(var1);
      this.stubinfo = var1;
      this.ror = this.stubinfo.getRemoteRef();
      ensureInitialized(this.stubinfo);
   }

   public final void bootPartition(String var1, String var2, String[] var3) throws PartitionLifeCycleException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         this.ror.invoke(this, md0, var5, m[0]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (PartitionLifeCycleException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

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
         md0 = new MethodDescriptor(m[0], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         md4 = new MethodDescriptor(m[4], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[4]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[4]));
         md5 = new MethodDescriptor(m[5], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[5]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[5]));
         md6 = new MethodDescriptor(m[6], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[6]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[6]));
         md7 = new MethodDescriptor(m[7], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[7]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[7]));
         md8 = new MethodDescriptor(m[8], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[8]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[8]));
         md9 = new MethodDescriptor(m[9], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[9]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[9]));
         md10 = new MethodDescriptor(m[10], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[10]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[10]));
         md11 = new MethodDescriptor(m[11], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[11]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[11]));
         md12 = new MethodDescriptor(m[12], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[12]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[12]));
         md13 = new MethodDescriptor(m[13], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[13]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[13]));
         md14 = new MethodDescriptor(m[14], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[14]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[14]));
         md15 = new MethodDescriptor(m[15], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[15]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[15]));
         md16 = new MethodDescriptor(m[16], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[16]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[16]));
         md17 = new MethodDescriptor(m[17], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[17]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[17]));
         md18 = new MethodDescriptor(m[18], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, true, false, false, false, var0.getTimeOut(m[18]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[18]));
         md19 = new MethodDescriptor(m[19], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, true, false, false, false, var0.getTimeOut(m[19]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[19]));
         md20 = new MethodDescriptor(m[20], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[20]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[20]));
         md21 = new MethodDescriptor(m[21], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[21]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[21]));
         md22 = new MethodDescriptor(m[22], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[22]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[22]));
         md23 = new MethodDescriptor(m[23], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[23]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[23]));
         md24 = new MethodDescriptor(m[24], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[24]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[24]));
         md25 = new MethodDescriptor(m[25], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[25]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[25]));
         md26 = new MethodDescriptor(m[26], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[26]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[26]));
         md27 = new MethodDescriptor(m[27], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[27]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[27]));
         md28 = new MethodDescriptor(m[28], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[28]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[28]));
         md29 = new MethodDescriptor(m[29], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[29]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[29]));
         md30 = new MethodDescriptor(m[30], class$weblogic$server$RemoteLifeCycleOperations == null ? (class$weblogic$server$RemoteLifeCycleOperations = class$("weblogic.server.RemoteLifeCycleOperations")) : class$weblogic$server$RemoteLifeCycleOperations, false, false, false, false, var0.getTimeOut(m[30]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[30]));
         initialized = true;
      }
   }

   public final void forceRestartPartition(String var1, String var2, String[] var3) throws PartitionLifeCycleException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         this.ror.invoke(this, md1, var5, m[1]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (PartitionLifeCycleException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }

   public final void forceShutDownPartition(String var1, String[] var2) throws PartitionLifeCycleException, RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md2, var4, m[2]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (PartitionLifeCycleException var14) {
         throw var14;
      } catch (RemoteException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   public final void forceShutDownResourceGroup(String var1, String var2, String[] var3) throws ResourceGroupLifecycleException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         this.ror.invoke(this, md3, var5, m[3]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (ResourceGroupLifecycleException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }

   public final void forceShutdown() throws ServerLifecycleException, RemoteException {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var2 = new Object[0];
         this.ror.invoke(this, md4, var2, m[4]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (ServerLifecycleException var12) {
         throw var12;
      } catch (RemoteException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

   }

   public final void forceSuspend() throws ServerLifecycleException, RemoteException {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var2 = new Object[0];
         this.ror.invoke(this, md5, var2, m[5]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (ServerLifecycleException var12) {
         throw var12;
      } catch (RemoteException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

   }

   public final void forceSuspendPartition(String var1, String[] var2) throws PartitionLifeCycleException, RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md6, var4, m[6]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (PartitionLifeCycleException var14) {
         throw var14;
      } catch (RemoteException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   public final void forceSuspendResourceGroup(String var1, String var2, String[] var3) throws ResourceGroupLifecycleException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         this.ror.invoke(this, md7, var5, m[7]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (ResourceGroupLifecycleException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }

   public final String getMiddlewareHome() throws RemoteException {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      String var15;
      try {
         Object[] var2 = new Object[0];
         var15 = (String)this.ror.invoke(this, md8, var2, m[8]);
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

   public final Map getRuntimeStates() {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Map var13;
      try {
         Object[] var2 = new Object[0];
         var13 = (Map)this.ror.invoke(this, md9, var2, m[9]);
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

   public final String getState() throws RemoteException {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      String var15;
      try {
         Object[] var2 = new Object[0];
         var15 = (String)this.ror.invoke(this, md10, var2, m[10]);
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

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final String getWeblogicHome() throws RemoteException {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      String var15;
      try {
         Object[] var2 = new Object[0];
         var15 = (String)this.ror.invoke(this, md11, var2, m[11]);
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

   public final void haltPartition(String var1, String var2, String[] var3) throws PartitionLifeCycleException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         this.ror.invoke(this, md12, var5, m[12]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (PartitionLifeCycleException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }

   public final void resume() throws ServerLifecycleException, RemoteException {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var2 = new Object[0];
         this.ror.invoke(this, md13, var2, m[13]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (ServerLifecycleException var12) {
         throw var12;
      } catch (RemoteException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

   }

   public final void resumePartition(String var1, String[] var2) throws PartitionLifeCycleException, RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md14, var4, m[14]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (PartitionLifeCycleException var14) {
         throw var14;
      } catch (RemoteException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   public final void resumeResourceGroup(String var1, String var2, String[] var3) throws ResourceGroupLifecycleException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         this.ror.invoke(this, md15, var5, m[15]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (ResourceGroupLifecycleException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }

   public final void setDesiredPartitionState(String var1, String var2, String[] var3) throws PartitionLifeCycleException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         this.ror.invoke(this, md16, var5, m[16]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (PartitionLifeCycleException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }

   public final void setDesiredResourceGroupState(String var1, String var2, String var3, String[] var4) throws ResourceGroupLifecycleException, RemoteException {
      Transaction var5 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var6 = new Object[]{var1, var2, var3, var4};
         this.ror.invoke(this, md17, var6, m[17]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (ResourceGroupLifecycleException var16) {
         throw var16;
      } catch (RemoteException var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var5);
      }

   }

   public final void setInvalid(String var1) throws RemoteException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var3 = new Object[]{var1};
         this.ror.invoke(this, md18, var3, m[18]);
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

   public final void setState(String var1, String var2) throws RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md19, var4, m[19]);
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

   public final void shutDownPartition(String var1, int var2, boolean var3, boolean var4, String[] var5) throws PartitionLifeCycleException, RemoteException {
      Transaction var6 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var7 = new Object[]{var1, new Integer(var2), new Boolean(var3), new Boolean(var4), var5};
         this.ror.invoke(this, md20, var7, m[20]);
      } catch (Error var15) {
         throw var15;
      } catch (RuntimeException var16) {
         throw var16;
      } catch (PartitionLifeCycleException var17) {
         throw var17;
      } catch (RemoteException var18) {
         throw var18;
      } catch (Throwable var19) {
         throw new RemoteRuntimeException("Unexpected Exception", var19);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var6);
      }

   }

   public final void shutDownResourceGroup(String var1, String var2, int var3, boolean var4, boolean var5, String[] var6) throws ResourceGroupLifecycleException, RemoteException {
      Transaction var7 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var8 = new Object[]{var1, var2, new Integer(var3), new Boolean(var4), new Boolean(var5), var6};
         this.ror.invoke(this, md21, var8, m[21]);
      } catch (Error var16) {
         throw var16;
      } catch (RuntimeException var17) {
         throw var17;
      } catch (ResourceGroupLifecycleException var18) {
         throw var18;
      } catch (RemoteException var19) {
         throw var19;
      } catch (Throwable var20) {
         throw new RemoteRuntimeException("Unexpected Exception", var20);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var7);
      }

   }

   public final void shutdown() throws ServerLifecycleException, RemoteException {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var2 = new Object[0];
         this.ror.invoke(this, md22, var2, m[22]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (ServerLifecycleException var12) {
         throw var12;
      } catch (RemoteException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

   }

   public final void shutdown(int var1, boolean var2) throws ServerLifecycleException, RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{new Integer(var1), new Boolean(var2)};
         this.ror.invoke(this, md23, var4, m[23]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (ServerLifecycleException var14) {
         throw var14;
      } catch (RemoteException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   public final void shutdown(int var1, boolean var2, boolean var3) throws ServerLifecycleException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{new Integer(var1), new Boolean(var2), new Boolean(var3)};
         this.ror.invoke(this, md24, var5, m[24]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (ServerLifecycleException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }

   public final void startPartition(String var1, String var2, boolean var3, String[] var4) throws PartitionLifeCycleException, RemoteException {
      Transaction var5 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var6 = new Object[]{var1, var2, new Boolean(var3), var4};
         this.ror.invoke(this, md25, var6, m[25]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (PartitionLifeCycleException var16) {
         throw var16;
      } catch (RemoteException var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var5);
      }

   }

   public final void startResourceGroup(String var1, String var2, boolean var3, String[] var4) throws ResourceGroupLifecycleException, RemoteException {
      Transaction var5 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var6 = new Object[]{var1, var2, new Boolean(var3), var4};
         this.ror.invoke(this, md26, var6, m[26]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (ResourceGroupLifecycleException var16) {
         throw var16;
      } catch (RemoteException var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var5);
      }

   }

   public final void suspend() throws ServerLifecycleException, RemoteException {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var2 = new Object[0];
         this.ror.invoke(this, md27, var2, m[27]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (ServerLifecycleException var12) {
         throw var12;
      } catch (RemoteException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

   }

   public final void suspend(int var1, boolean var2) throws ServerLifecycleException, RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{new Integer(var1), new Boolean(var2)};
         this.ror.invoke(this, md28, var4, m[28]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (ServerLifecycleException var14) {
         throw var14;
      } catch (RemoteException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   public final void suspendPartition(String var1, int var2, boolean var3, String[] var4) throws PartitionLifeCycleException, RemoteException {
      Transaction var5 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var6 = new Object[]{var1, new Integer(var2), new Boolean(var3), var4};
         this.ror.invoke(this, md29, var6, m[29]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (PartitionLifeCycleException var16) {
         throw var16;
      } catch (RemoteException var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var5);
      }

   }

   public final void suspendResourceGroup(String var1, String var2, int var3, boolean var4, String[] var5) throws ResourceGroupLifecycleException, RemoteException {
      Transaction var6 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var7 = new Object[]{var1, var2, new Integer(var3), new Boolean(var4), var5};
         this.ror.invoke(this, md30, var7, m[30]);
      } catch (Error var15) {
         throw var15;
      } catch (RuntimeException var16) {
         throw var16;
      } catch (ResourceGroupLifecycleException var17) {
         throw var17;
      } catch (RemoteException var18) {
         throw var18;
      } catch (Throwable var19) {
         throw new RemoteRuntimeException("Unexpected Exception", var19);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var6);
      }

   }
}
