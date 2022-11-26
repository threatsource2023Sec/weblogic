package weblogic.management.internal;

import java.lang.reflect.Method;
import javax.management.Notification;
import javax.management.NotificationFilter;
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

public final class RelayNotificationListenerImpl_14110_WLStub extends Stub implements StubInfoIntf, RelayNotificationListener {
   // $FF: synthetic field
   private static Class class$weblogic$management$internal$RelayNotificationListener;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md2;
   private static RuntimeMethodDescriptor md3;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public RelayNotificationListenerImpl_14110_WLStub(StubInfo var1) {
      super(var1);
      this.stubinfo = var1;
      this.ror = this.stubinfo.getRemoteRef();
      ensureInitialized(this.stubinfo);
   }

   public final void addFilterAndHandback(NotificationFilter var1, Object var2) {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md0, var4, m[0]);
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
         md0 = new MethodDescriptor(m[0], class$weblogic$management$internal$RelayNotificationListener == null ? (class$weblogic$management$internal$RelayNotificationListener = class$("weblogic.management.internal.RelayNotificationListener")) : class$weblogic$management$internal$RelayNotificationListener, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$management$internal$RelayNotificationListener == null ? (class$weblogic$management$internal$RelayNotificationListener = class$("weblogic.management.internal.RelayNotificationListener")) : class$weblogic$management$internal$RelayNotificationListener, false, false, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$management$internal$RelayNotificationListener == null ? (class$weblogic$management$internal$RelayNotificationListener = class$("weblogic.management.internal.RelayNotificationListener")) : class$weblogic$management$internal$RelayNotificationListener, false, false, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$management$internal$RelayNotificationListener == null ? (class$weblogic$management$internal$RelayNotificationListener = class$("weblogic.management.internal.RelayNotificationListener")) : class$weblogic$management$internal$RelayNotificationListener, false, false, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         initialized = true;
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final void handleNotification(Notification var1, Object var2) {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
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

   public final void remove() {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var2 = new Object[0];
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

   public final void unregister() {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var2 = new Object[0];
         this.ror.invoke(this, md3, var2, m[3]);
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
}
