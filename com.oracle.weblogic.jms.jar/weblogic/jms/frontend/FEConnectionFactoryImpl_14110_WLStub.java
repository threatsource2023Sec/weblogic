package weblogic.jms.frontend;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import javax.jms.JMSException;
import javax.transaction.Transaction;
import weblogic.jms.client.JMSConnection;
import weblogic.jms.dispatcher.DispatcherWrapper;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class FEConnectionFactoryImpl_14110_WLStub extends Stub implements StubInfoIntf, FEConnectionFactoryRemote {
   // $FF: synthetic field
   private static Class class$weblogic$jms$frontend$FEConnectionFactoryRemote;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md2;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public FEConnectionFactoryImpl_14110_WLStub(StubInfo var1) {
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

   public final JMSConnection connectionCreate(DispatcherWrapper var1) throws JMSException, RemoteException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      JMSConnection var18;
      try {
         Object[] var3 = new Object[]{var1};
         var18 = (JMSConnection)this.ror.invoke(this, md0, var3, m[0]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (JMSException var14) {
         throw var14;
      } catch (RemoteException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var18;
   }

   public final JMSConnection connectionCreate(DispatcherWrapper var1, String var2, String var3) throws JMSException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      JMSConnection var20;
      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         var20 = (JMSConnection)this.ror.invoke(this, md1, var5, m[1]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (JMSException var16) {
         throw var16;
      } catch (RemoteException var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

      return var20;
   }

   public final JMSConnection connectionCreateRequest(FEConnectionCreateRequest var1) throws JMSException, RemoteException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      JMSConnection var18;
      try {
         Object[] var3 = new Object[]{var1};
         var18 = (JMSConnection)this.ror.invoke(this, md2, var3, m[2]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (JMSException var14) {
         throw var14;
      } catch (RemoteException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var18;
   }

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         md0 = new MethodDescriptor(m[0], class$weblogic$jms$frontend$FEConnectionFactoryRemote == null ? (class$weblogic$jms$frontend$FEConnectionFactoryRemote = class$("weblogic.jms.frontend.FEConnectionFactoryRemote")) : class$weblogic$jms$frontend$FEConnectionFactoryRemote, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$jms$frontend$FEConnectionFactoryRemote == null ? (class$weblogic$jms$frontend$FEConnectionFactoryRemote = class$("weblogic.jms.frontend.FEConnectionFactoryRemote")) : class$weblogic$jms$frontend$FEConnectionFactoryRemote, false, false, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$jms$frontend$FEConnectionFactoryRemote == null ? (class$weblogic$jms$frontend$FEConnectionFactoryRemote = class$("weblogic.jms.frontend.FEConnectionFactoryRemote")) : class$weblogic$jms$frontend$FEConnectionFactoryRemote, false, false, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         initialized = true;
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }
}
