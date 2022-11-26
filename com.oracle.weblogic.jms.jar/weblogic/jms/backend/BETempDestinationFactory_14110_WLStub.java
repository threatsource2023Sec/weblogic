package weblogic.jms.backend;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.transaction.Transaction;
import weblogic.jms.common.JMSID;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class BETempDestinationFactory_14110_WLStub extends Stub implements StubInfoIntf, BETempDestinationFactoryRemote {
   // $FF: synthetic field
   private static Class class$weblogic$jms$backend$BETempDestinationFactoryRemote;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public BETempDestinationFactory_14110_WLStub(StubInfo var1) {
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

   public final Destination createTempDestination(DispatcherId var1, JMSID var2, boolean var3, int var4, long var5, String var7, boolean var8, int var9, String var10) throws RemoteException, JMSException {
      Transaction var11 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Destination var27;
      try {
         Object[] var12 = new Object[]{var1, var2, new Boolean(var3), new Integer(var4), new Long(var5), var7, new Boolean(var8), new Integer(var9), var10};
         var27 = (Destination)this.ror.invoke(this, md0, var12, m[0]);
      } catch (Error var21) {
         throw var21;
      } catch (RuntimeException var22) {
         throw var22;
      } catch (RemoteException var23) {
         throw var23;
      } catch (JMSException var24) {
         throw var24;
      } catch (Throwable var25) {
         throw new RemoteRuntimeException("Unexpected Exception", var25);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var11);
      }

      return var27;
   }

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         md0 = new MethodDescriptor(m[0], class$weblogic$jms$backend$BETempDestinationFactoryRemote == null ? (class$weblogic$jms$backend$BETempDestinationFactoryRemote = class$("weblogic.jms.backend.BETempDestinationFactoryRemote")) : class$weblogic$jms$backend$BETempDestinationFactoryRemote, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         initialized = true;
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }
}
