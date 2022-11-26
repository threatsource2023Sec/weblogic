package weblogic.management.scripting;

import java.lang.reflect.Method;
import javax.management.Notification;
import weblogic.corba.rmi.Stub;
import weblogic.management.RemoteNotificationListener;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;

public final class ChangeListener_IIOP_WLStub extends Stub implements StubInfoIntf, RemoteNotificationListener {
   // $FF: synthetic field
   private static Class class$weblogic$management$RemoteNotificationListener;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public ChangeListener_IIOP_WLStub(StubInfo var1) {
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
         md0 = new MethodDescriptor(m[0], class$weblogic$management$RemoteNotificationListener == null ? (class$weblogic$management$RemoteNotificationListener = class$("weblogic.management.RemoteNotificationListener")) : class$weblogic$management$RemoteNotificationListener, false, true, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         initialized = true;
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final void handleNotification(Notification var1, Object var2) {
      try {
         Object[] var3 = new Object[]{var1, var2};
         this.ror.invoke(this, md0, var3, m[0]);
      } catch (Error var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw var5;
      } catch (Throwable var6) {
         throw new RemoteRuntimeException("Unexpected Exception", var6);
      }
   }
}
