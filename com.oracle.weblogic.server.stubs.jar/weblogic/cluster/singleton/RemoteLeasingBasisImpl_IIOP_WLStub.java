package weblogic.cluster.singleton;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;
import javax.transaction.Transaction;
import weblogic.corba.rmi.Stub;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class RemoteLeasingBasisImpl_IIOP_WLStub extends Stub implements StubInfoIntf, RemoteLeasingBasis {
   // $FF: synthetic field
   private static Class class$weblogic$cluster$singleton$RemoteLeasingBasis;
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

   public RemoteLeasingBasisImpl_IIOP_WLStub(StubInfo var1) {
      super(var1);
      this.stubinfo = var1;
      this.ror = this.stubinfo.getRemoteRef();
      ensureInitialized(this.stubinfo);
   }

   public final boolean acquire(String var1, String var2, int var3) throws IOException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      boolean var18;
      try {
         Object[] var5 = new Object[]{var1, var2, new Integer(var3)};
         var18 = (Boolean)this.ror.invoke(this, md0, var5, m[0]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (IOException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

      return var18;
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
         md0 = new MethodDescriptor(m[0], class$weblogic$cluster$singleton$RemoteLeasingBasis == null ? (class$weblogic$cluster$singleton$RemoteLeasingBasis = class$("weblogic.cluster.singleton.RemoteLeasingBasis")) : class$weblogic$cluster$singleton$RemoteLeasingBasis, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$cluster$singleton$RemoteLeasingBasis == null ? (class$weblogic$cluster$singleton$RemoteLeasingBasis = class$("weblogic.cluster.singleton.RemoteLeasingBasis")) : class$weblogic$cluster$singleton$RemoteLeasingBasis, false, false, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$cluster$singleton$RemoteLeasingBasis == null ? (class$weblogic$cluster$singleton$RemoteLeasingBasis = class$("weblogic.cluster.singleton.RemoteLeasingBasis")) : class$weblogic$cluster$singleton$RemoteLeasingBasis, false, false, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$cluster$singleton$RemoteLeasingBasis == null ? (class$weblogic$cluster$singleton$RemoteLeasingBasis = class$("weblogic.cluster.singleton.RemoteLeasingBasis")) : class$weblogic$cluster$singleton$RemoteLeasingBasis, false, false, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         md4 = new MethodDescriptor(m[4], class$weblogic$cluster$singleton$RemoteLeasingBasis == null ? (class$weblogic$cluster$singleton$RemoteLeasingBasis = class$("weblogic.cluster.singleton.RemoteLeasingBasis")) : class$weblogic$cluster$singleton$RemoteLeasingBasis, false, false, false, false, var0.getTimeOut(m[4]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[4]));
         md5 = new MethodDescriptor(m[5], class$weblogic$cluster$singleton$RemoteLeasingBasis == null ? (class$weblogic$cluster$singleton$RemoteLeasingBasis = class$("weblogic.cluster.singleton.RemoteLeasingBasis")) : class$weblogic$cluster$singleton$RemoteLeasingBasis, false, false, false, false, var0.getTimeOut(m[5]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[5]));
         md6 = new MethodDescriptor(m[6], class$weblogic$cluster$singleton$RemoteLeasingBasis == null ? (class$weblogic$cluster$singleton$RemoteLeasingBasis = class$("weblogic.cluster.singleton.RemoteLeasingBasis")) : class$weblogic$cluster$singleton$RemoteLeasingBasis, false, false, false, false, var0.getTimeOut(m[6]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[6]));
         initialized = true;
      }
   }

   public final String[] findExpiredLeases(int var1) throws IOException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      String[] var16;
      try {
         Object[] var3 = new Object[]{new Integer(var1)};
         var16 = (String[])this.ror.invoke(this, md1, var3, m[1]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (IOException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var16;
   }

   public final String findOwner(String var1) throws IOException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      String var16;
      try {
         Object[] var3 = new Object[]{var1};
         var16 = (String)this.ror.invoke(this, md2, var3, m[2]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (IOException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var16;
   }

   public final String findPreviousOwner(String var1) throws IOException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      String var16;
      try {
         Object[] var3 = new Object[]{var1};
         var16 = (String)this.ror.invoke(this, md3, var3, m[3]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (IOException var13) {
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

   public final void release(String var1, String var2) throws IOException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md4, var4, m[4]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (IOException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   public final int renewAllLeases(int var1, String var2) throws IOException, MissedHeartbeatException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      int var19;
      try {
         Object[] var4 = new Object[]{new Integer(var1), var2};
         var19 = (Integer)this.ror.invoke(this, md5, var4, m[5]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (IOException var15) {
         throw var15;
      } catch (MissedHeartbeatException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

      return var19;
   }

   public final int renewLeases(String var1, Set var2, int var3) throws IOException, MissedHeartbeatException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      int var20;
      try {
         Object[] var5 = new Object[]{var1, var2, new Integer(var3)};
         var20 = (Integer)this.ror.invoke(this, md6, var5, m[6]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (IOException var16) {
         throw var16;
      } catch (MissedHeartbeatException var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

      return var20;
   }
}
