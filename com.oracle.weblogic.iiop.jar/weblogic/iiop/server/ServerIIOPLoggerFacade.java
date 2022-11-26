package weblogic.iiop.server;

import java.io.IOException;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.IIOPLogger;
import weblogic.iiop.IIOPLoggerFacade;
import weblogic.rmi.RMILogger;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

@Service
public class ServerIIOPLoggerFacade extends IIOPLoggerFacade {
   private static final DebugCategory debugTransport = Debug.getCategory("weblogic.iiop.transport");
   private static final DebugLogger debugIIOPTransport = DebugLogger.getDebugLogger("DebugIIOPTransport");

   protected String doLogExceptionReceiving(Throwable t) {
      return IIOPLogger.logExceptionReceiving(t);
   }

   protected String doLogExceptionSending(IOException e) {
      return IIOPLogger.logExceptionSending(e);
   }

   protected String doLogOutOfMemory(Throwable t) {
      return IIOPLogger.logOutOfMemory(t);
   }

   protected String doLogHeartbeatPeerClosed() {
      return RMILogger.logHeartbeatPeerClosed();
   }

   protected boolean getTransportDebugEnabled() {
      return debugTransport.isEnabled() || debugIIOPTransport.isDebugEnabled();
   }

   protected String doLogDebugTransport(String reason, Object[] args) {
      return IIOPLogger.logDebugTransport(String.format(reason, args));
   }
}
