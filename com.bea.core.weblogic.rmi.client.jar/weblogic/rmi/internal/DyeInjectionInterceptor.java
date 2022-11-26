package weblogic.rmi.internal;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.spi.InboundRequest;

public class DyeInjectionInterceptor extends ServerReferenceInterceptor {
   private static Method wldfDyeInjectionMethod = null;
   private static ServerReferenceInterceptor instance = null;

   static ServerReferenceInterceptor getInstance() throws NoSuchMethodException, ClassNotFoundException {
      if (instance == null) {
         instance = new DyeInjectionInterceptor();
      }

      return instance;
   }

   void preInvoke(ServerReference serverRef, InboundRequest request, RuntimeMethodDescriptor descriptor) throws RemoteException {
      try {
         Object[] args = new Object[]{request};
         wldfDyeInjectionMethod.invoke((Object)null, args);
      } catch (Throwable var5) {
      }

   }

   private DyeInjectionInterceptor() throws ClassNotFoundException, NoSuchMethodException {
      Class clz = Class.forName("weblogic.diagnostics.instrumentation.support.DyeInjectionMonitorSupport");
      wldfDyeInjectionMethod = clz.getMethod("dyeRMIRequest", Object.class);
   }
}
