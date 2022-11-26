package weblogic.io.common.internal;

import java.lang.reflect.Method;
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

public final class T3RemoteInputStream_14110_WLStub extends Stub implements StubInfoIntf, OneWayInputClient {
   // $FF: synthetic field
   private static Class class$weblogic$io$common$internal$OneWayInputClient;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md2;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public T3RemoteInputStream_14110_WLStub(StubInfo var1) {
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
         md0 = new MethodDescriptor(m[0], class$weblogic$io$common$internal$OneWayInputClient == null ? (class$weblogic$io$common$internal$OneWayInputClient = class$("weblogic.io.common.internal.OneWayInputClient")) : class$weblogic$io$common$internal$OneWayInputClient, true, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$io$common$internal$OneWayInputClient == null ? (class$weblogic$io$common$internal$OneWayInputClient = class$("weblogic.io.common.internal.OneWayInputClient")) : class$weblogic$io$common$internal$OneWayInputClient, true, false, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$io$common$internal$OneWayInputClient == null ? (class$weblogic$io$common$internal$OneWayInputClient = class$("weblogic.io.common.internal.OneWayInputClient")) : class$weblogic$io$common$internal$OneWayInputClient, true, false, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         initialized = true;
      }
   }

   public final void error(Exception var1) {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var3 = new Object[]{var1};
         this.ror.invoke(this, md0, var3, m[0]);
      } catch (Error var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw var10;
      } catch (Throwable var11) {
         throw new RemoteRuntimeException("Unexpected Exception", var11);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final void readResult(int var1, byte[] var2) {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{new Integer(var1), var2};
         this.ror.invoke(this, md1, var4, m[1]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (Throwable var12) {
         throw new RemoteRuntimeException("Unexpected Exception", var12);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   public final void skipResult(long var1) {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{new Long(var1)};
         this.ror.invoke(this, md2, var4, m[2]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (Throwable var12) {
         throw new RemoteRuntimeException("Unexpected Exception", var12);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }
}
