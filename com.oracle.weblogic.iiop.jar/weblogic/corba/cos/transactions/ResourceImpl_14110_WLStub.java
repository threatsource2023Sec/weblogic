package weblogic.corba.cos.transactions;

import java.lang.reflect.Method;
import javax.transaction.Transaction;
import org.omg.CORBA.Object;
import org.omg.CosTransactions.HeuristicCommit;
import org.omg.CosTransactions.HeuristicHazard;
import org.omg.CosTransactions.HeuristicMixed;
import org.omg.CosTransactions.HeuristicRollback;
import org.omg.CosTransactions.NotPrepared;
import org.omg.CosTransactions.Resource;
import org.omg.CosTransactions.Vote;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class ResourceImpl_14110_WLStub extends Stub implements StubInfoIntf, Resource, Object {
   // $FF: synthetic field
   private static Class class$org$omg$CosTransactions$Resource;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md2;
   private static RuntimeMethodDescriptor md3;
   private static RuntimeMethodDescriptor md4;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public ResourceImpl_14110_WLStub(StubInfo var1) {
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

   public final void commit() throws NotPrepared, HeuristicRollback, HeuristicMixed, HeuristicHazard {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         java.lang.Object[] var2 = new java.lang.Object[0];
         this.ror.invoke(this, md0, var2, m[0]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (NotPrepared var14) {
         throw var14;
      } catch (HeuristicRollback var15) {
         throw var15;
      } catch (HeuristicMixed var16) {
         throw var16;
      } catch (HeuristicHazard var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

   }

   public final void commit_one_phase() throws HeuristicHazard {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         java.lang.Object[] var2 = new java.lang.Object[0];
         this.ror.invoke(this, md1, var2, m[1]);
      } catch (Error var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw var10;
      } catch (HeuristicHazard var11) {
         throw var11;
      } catch (Throwable var12) {
         throw new RemoteRuntimeException("Unexpected Exception", var12);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

   }

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         md0 = new MethodDescriptor(m[0], class$org$omg$CosTransactions$Resource == null ? (class$org$omg$CosTransactions$Resource = class$("org.omg.CosTransactions.Resource")) : class$org$omg$CosTransactions$Resource, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$org$omg$CosTransactions$Resource == null ? (class$org$omg$CosTransactions$Resource = class$("org.omg.CosTransactions.Resource")) : class$org$omg$CosTransactions$Resource, false, false, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$org$omg$CosTransactions$Resource == null ? (class$org$omg$CosTransactions$Resource = class$("org.omg.CosTransactions.Resource")) : class$org$omg$CosTransactions$Resource, false, false, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$org$omg$CosTransactions$Resource == null ? (class$org$omg$CosTransactions$Resource = class$("org.omg.CosTransactions.Resource")) : class$org$omg$CosTransactions$Resource, false, false, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         md4 = new MethodDescriptor(m[4], class$org$omg$CosTransactions$Resource == null ? (class$org$omg$CosTransactions$Resource = class$("org.omg.CosTransactions.Resource")) : class$org$omg$CosTransactions$Resource, false, false, false, false, var0.getTimeOut(m[4]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[4]));
         initialized = true;
      }
   }

   public final void forget() {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         java.lang.Object[] var2 = new java.lang.Object[0];
         this.ror.invoke(this, md2, var2, m[2]);
      } catch (Error var8) {
         throw var8;
      } catch (RuntimeException var9) {
         throw var9;
      } catch (Throwable var10) {
         throw new RemoteRuntimeException("Unexpected Exception", var10);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final Vote prepare() throws HeuristicMixed, HeuristicHazard {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Vote var17;
      try {
         java.lang.Object[] var2 = new java.lang.Object[0];
         var17 = (Vote)this.ror.invoke(this, md3, var2, m[3]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (HeuristicMixed var13) {
         throw var13;
      } catch (HeuristicHazard var14) {
         throw var14;
      } catch (Throwable var15) {
         throw new RemoteRuntimeException("Unexpected Exception", var15);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

      return var17;
   }

   public final void rollback() throws HeuristicCommit, HeuristicMixed, HeuristicHazard {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         java.lang.Object[] var2 = new java.lang.Object[0];
         this.ror.invoke(this, md4, var2, m[4]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (HeuristicCommit var13) {
         throw var13;
      } catch (HeuristicMixed var14) {
         throw var14;
      } catch (HeuristicHazard var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

   }
}
