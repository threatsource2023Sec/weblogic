package weblogic.rjvm;

import java.lang.annotation.Annotation;
import java.net.InetAddress;
import javax.servlet.http.HttpServletRequest;
import org.jvnet.hk2.annotations.Contract;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.runtime.SocketRuntime;
import weblogic.protocol.ProtocolHandler;
import weblogic.protocol.ServerChannel;
import weblogic.security.service.ContextHandler;
import weblogic.server.GlobalServiceLocator;

@Contract
public abstract class WebRjvmSupport {
   private static WebRjvmSupport webRjvmSupport;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("WebRjvmSupport");

   public static WebRjvmSupport getWebRjvmSupport() {
      if (webRjvmSupport == null) {
         loadWebRjvmSupportImpl();
      }

      return webRjvmSupport;
   }

   private static synchronized void loadWebRjvmSupportImpl() {
      if (webRjvmSupport == null) {
         try {
            webRjvmSupport = (WebRjvmSupport)GlobalServiceLocator.getServiceLocator().getService(WebRjvmSupport.class, new Annotation[0]);
            if (webRjvmSupport == null) {
               doHk2Fallback();
            }
         } catch (Throwable var1) {
            RJVMLogger.logUnableGetWebRjvmSupportHK2(var1);
            doHk2Fallback();
         }

      }
   }

   private static void doHk2Fallback() {
      try {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("!!! HK2 Did not fill in weblogic.rjvm.WebRjvmSupport.webRjvmSupport; using reflection instead");
         }

         webRjvmSupport = (WebRjvmSupport)Class.forName("weblogic.servlet.internal.utils.WebRjvmSupportImpl").newInstance();
      } catch (Throwable var1) {
         RJVMLogger.logUnableGetWebRjvmSupportReflect(var1);
      }

   }

   public abstract ProtocolHandler getHttpProtocolHandler();

   public abstract ProtocolHandler getHttpsProtocolHandler();

   public abstract ServerChannel getChannel(HttpServletRequest var1);

   public abstract SocketRuntime getSocketRuntime(HttpServletRequest var1);

   public abstract ContextHandler getContextHandler(HttpServletRequest var1);

   public abstract InetAddress getSocketLocalAddress(HttpServletRequest var1);
}
