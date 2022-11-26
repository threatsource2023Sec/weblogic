package weblogic.corba.cos.naming;

import java.lang.reflect.Method;
import javax.transaction.Transaction;
import org.omg.CORBA.Object;
import org.omg.CosNaming.BindingHolder;
import org.omg.CosNaming.BindingListHolder;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class BindingIteratorImpl_14110_WLStub extends Stub implements StubInfoIntf, BindingIterator, org.omg.CosNaming.BindingIterator, Object {
   // $FF: synthetic field
   private static Class class$weblogic$corba$cos$naming$BindingIterator;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md2;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public BindingIteratorImpl_14110_WLStub(StubInfo var1) {
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

   public final void destroy() {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         java.lang.Object[] var2 = new java.lang.Object[0];
         this.ror.invoke(this, md0, var2, m[0]);
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

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         md0 = new MethodDescriptor(m[0], class$weblogic$corba$cos$naming$BindingIterator == null ? (class$weblogic$corba$cos$naming$BindingIterator = class$("weblogic.corba.cos.naming.BindingIterator")) : class$weblogic$corba$cos$naming$BindingIterator, false, false, false, true, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$corba$cos$naming$BindingIterator == null ? (class$weblogic$corba$cos$naming$BindingIterator = class$("weblogic.corba.cos.naming.BindingIterator")) : class$weblogic$corba$cos$naming$BindingIterator, false, false, false, true, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$corba$cos$naming$BindingIterator == null ? (class$weblogic$corba$cos$naming$BindingIterator = class$("weblogic.corba.cos.naming.BindingIterator")) : class$weblogic$corba$cos$naming$BindingIterator, false, false, false, true, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         initialized = true;
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final boolean next_n(int var1, BindingListHolder var2) {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      boolean var15;
      try {
         java.lang.Object[] var4 = new java.lang.Object[]{new Integer(var1), var2};
         var15 = (Boolean)this.ror.invoke(this, md1, var4, m[1]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (Throwable var13) {
         throw new RemoteRuntimeException("Unexpected Exception", var13);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

      return var15;
   }

   public final boolean next_one(BindingHolder var1) {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      boolean var14;
      try {
         java.lang.Object[] var3 = new java.lang.Object[]{var1};
         var14 = (Boolean)this.ror.invoke(this, md2, var3, m[2]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (Throwable var12) {
         throw new RemoteRuntimeException("Unexpected Exception", var12);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var14;
   }
}
