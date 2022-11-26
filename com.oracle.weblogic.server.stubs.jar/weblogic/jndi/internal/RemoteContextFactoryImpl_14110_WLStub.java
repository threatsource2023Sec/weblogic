package weblogic.jndi.internal;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.Transaction;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class RemoteContextFactoryImpl_14110_WLStub extends Stub implements StubInfoIntf, RemoteContextFactory {
   // $FF: synthetic field
   private static Class class$weblogic$jndi$internal$RemoteContextFactory;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public RemoteContextFactoryImpl_14110_WLStub(StubInfo var1) {
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
         md0 = new MethodDescriptor(m[0], class$weblogic$jndi$internal$RemoteContextFactory == null ? (class$weblogic$jndi$internal$RemoteContextFactory = class$("weblogic.jndi.internal.RemoteContextFactory")) : class$weblogic$jndi$internal$RemoteContextFactory, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         initialized = true;
      }
   }

   public final Context getContext(Hashtable var1, String var2) throws NamingException, RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Context var19;
      try {
         Object[] var4 = new Object[]{var1, var2};
         var19 = (Context)this.ror.invoke(this, md0, var4, m[0]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NamingException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

      return var19;
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }
}
