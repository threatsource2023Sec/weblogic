package weblogic.corba.cos.transactions;

import java.lang.reflect.Method;
import javax.transaction.Transaction;
import org.omg.CORBA.Object;
import org.omg.CosTransactions.NotPrepared;
import org.omg.CosTransactions.RecoveryCoordinator;
import org.omg.CosTransactions.Resource;
import org.omg.CosTransactions.Status;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class RecoveryCoordinatorImpl_14110_WLStub extends Stub implements StubInfoIntf, RecoveryCoordinator, Object {
   // $FF: synthetic field
   private static Class class$org$omg$CosTransactions$RecoveryCoordinator;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public RecoveryCoordinatorImpl_14110_WLStub(StubInfo var1) {
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
         md0 = new MethodDescriptor(m[0], class$org$omg$CosTransactions$RecoveryCoordinator == null ? (class$org$omg$CosTransactions$RecoveryCoordinator = class$("org.omg.CosTransactions.RecoveryCoordinator")) : class$org$omg$CosTransactions$RecoveryCoordinator, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         initialized = true;
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final Status replay_completion(Resource var1) throws NotPrepared {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Status var16;
      try {
         java.lang.Object[] var3 = new java.lang.Object[]{var1};
         var16 = (Status)this.ror.invoke(this, md0, var3, m[0]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (NotPrepared var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var16;
   }
}
