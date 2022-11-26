package weblogic.rmi.internal;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.HeartbeatHelper;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.utils.Utilities;

public final class HeartbeatHelperImpl_14110_WLStub extends Stub implements StubInfoIntf, HeartbeatHelper {
   // $FF: synthetic field
   private static Class class$weblogic$rmi$extensions$server$HeartbeatHelper;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public HeartbeatHelperImpl_14110_WLStub(StubInfo var1) {
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
         md0 = new MethodDescriptor(m[0], class$weblogic$rmi$extensions$server$HeartbeatHelper == null ? (class$weblogic$rmi$extensions$server$HeartbeatHelper = class$("weblogic.rmi.extensions.server.HeartbeatHelper")) : class$weblogic$rmi$extensions$server$HeartbeatHelper, false, true, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         initialized = true;
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final void ping() throws RemoteException {
      try {
         Object[] var1 = new Object[0];
         this.ror.invoke(this, md0, var1, m[0]);
      } catch (Error var2) {
         throw var2;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (RemoteException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }
}
