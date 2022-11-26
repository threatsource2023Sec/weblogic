package weblogic.rmi.internal.dgc;

import java.lang.reflect.Method;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;

public final class DGCServerImpl_14110_WLStub extends Stub implements StubInfoIntf, DGCServer {
   // $FF: synthetic field
   private static Class class$weblogic$rmi$internal$dgc$DGCServer;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md2;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public DGCServerImpl_14110_WLStub(StubInfo var1) {
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

   public final void enroll(int[] var1) {
      try {
         Object[] var2 = new Object[]{var1};
         this.ror.invoke(this, md0, var2, m[0]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         md0 = new MethodDescriptor(m[0], class$weblogic$rmi$internal$dgc$DGCServer == null ? (class$weblogic$rmi$internal$dgc$DGCServer = class$("weblogic.rmi.internal.dgc.DGCServer")) : class$weblogic$rmi$internal$dgc$DGCServer, true, true, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$rmi$internal$dgc$DGCServer == null ? (class$weblogic$rmi$internal$dgc$DGCServer = class$("weblogic.rmi.internal.dgc.DGCServer")) : class$weblogic$rmi$internal$dgc$DGCServer, true, true, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$rmi$internal$dgc$DGCServer == null ? (class$weblogic$rmi$internal$dgc$DGCServer = class$("weblogic.rmi.internal.dgc.DGCServer")) : class$weblogic$rmi$internal$dgc$DGCServer, true, true, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         initialized = true;
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final void renewLease(int[] var1) {
      try {
         Object[] var2 = new Object[]{var1};
         this.ror.invoke(this, md1, var2, m[1]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }

   public final void unenroll(int[] var1) {
      try {
         Object[] var2 = new Object[]{var1};
         this.ror.invoke(this, md2, var2, m[2]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }
}
