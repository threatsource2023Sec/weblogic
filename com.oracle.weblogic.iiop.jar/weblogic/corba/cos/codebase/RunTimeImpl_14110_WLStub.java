package weblogic.corba.cos.codebase;

import java.lang.reflect.Method;
import org.omg.CORBA.Object;
import org.omg.SendingContext.RunTime;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;

public final class RunTimeImpl_14110_WLStub extends Stub implements StubInfoIntf, RunTime, Object {
   private static boolean initialized;
   private static Method[] m;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public RunTimeImpl_14110_WLStub(StubInfo var1) {
      super(var1);
      this.stubinfo = var1;
      this.ror = this.stubinfo.getRemoteRef();
      ensureInitialized(this.stubinfo);
   }

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         initialized = true;
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }
}
