package weblogic.iiop;

import java.io.IOException;
import weblogic.corba.orb.WlsIIOPInitialization;
import weblogic.iiop.server.InitialReferences;
import weblogic.kernel.Kernel;
import weblogic.protocol.ServerChannel;
import weblogic.security.service.SubjectManagerImpl;
import weblogic.server.channels.ServerSocketManager;

public final class IIOPClient {
   private static boolean enabled = false;

   public static boolean isEnabled() {
      return enabled || Kernel.isServer();
   }

   public static synchronized void initialize() {
      if (!isEnabled()) {
         if (WlsIIOPInitialization.initialize()) {
            SubjectManagerImpl.ensureInitialized();
            Kernel.ensureInitialized();
            IIOPClientService.resumeClient();

            try {
               InitialReferences.initializeClientInitialReferences();
            } catch (IOException var3) {
            }

            MuxableSocketIIOP.initialize();
            IIOPService.txMechanism = 0;
            ServerChannel sc = ProtocolHandlerIIOP.getProtocolHandler().getDefaultServerChannel();
            if (sc.getPort() != -1) {
               try {
                  ServerSocketManager.getInstance().createClientListener(sc);
               } catch (SecurityException var2) {
               }
            }

            enabled = true;
         }
      }
   }

   public static void shutdown() {
      MuxableSocketIIOP.disable();
      enabled = false;
   }
}
