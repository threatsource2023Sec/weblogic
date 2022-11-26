package weblogic.iiop;

import java.io.IOException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.utils.LocatorUtilities;

@Contract
public abstract class IIOPLoggerFacade {
   static String logExceptionReceiving(Throwable t) {
      return IIOPLoggerFacade.Initializer.instance.doLogExceptionReceiving(t);
   }

   static String logExceptionSending(IOException e) {
      return IIOPLoggerFacade.Initializer.instance.doLogExceptionSending(e);
   }

   static String logOutOfMemory(Throwable t) {
      return IIOPLoggerFacade.Initializer.instance.doLogOutOfMemory(t);
   }

   static String logHeartbeatPeerClosed() {
      return IIOPLoggerFacade.Initializer.instance.doLogHeartbeatPeerClosed();
   }

   public static String logDebugTransport(String reason, Object... args) {
      return IIOPLoggerFacade.Initializer.instance.doLogDebugTransport(reason, args);
   }

   public static boolean isTransportDebugEnabled() {
      return IIOPLoggerFacade.Initializer.instance.getTransportDebugEnabled();
   }

   protected abstract String doLogExceptionReceiving(Throwable var1);

   protected abstract String doLogExceptionSending(IOException var1);

   protected abstract String doLogOutOfMemory(Throwable var1);

   protected abstract String doLogHeartbeatPeerClosed();

   protected abstract boolean getTransportDebugEnabled();

   protected abstract String doLogDebugTransport(String var1, Object[] var2);

   private static final class Initializer {
      private static final IIOPLoggerFacade instance = (IIOPLoggerFacade)LocatorUtilities.getService(IIOPLoggerFacade.class);
   }
}
