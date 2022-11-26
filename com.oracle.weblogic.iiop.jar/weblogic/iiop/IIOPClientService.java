package weblogic.iiop;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.corba.j2ee.naming.EndPointSelector;
import weblogic.corba.orb.WlsIIOPInitialization;
import weblogic.corba.utils.ClassInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.ior.CodeSetsComponent;
import weblogic.iiop.protocol.CodeSet;
import weblogic.iiop.server.InitialReferences;
import weblogic.iiop.server.ior.ServerIORBuilder;
import weblogic.kernel.Kernel;
import weblogic.rmi.extensions.DisconnectMonitorListImpl;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

@Service
@Named
@RunLevel(10)
public final class IIOPClientService extends AbstractServerService {
   @Inject
   @Named("RMIServerService")
   private ServerService dependencyOnRMIServerService;
   private static boolean initialized = false;
   private static final DebugCategory debugStartup = Debug.getCategory("weblogic.iiop.startup");
   private static final DebugLogger debugIIOPStartup = DebugLogger.getDebugLogger("DebugIIOPStartup");
   static String locationForwardPolicy;
   public static boolean useSerialFormatVersion2 = false;
   static boolean useLocateRequest = false;

   private void initialize() throws ServiceFailureException {
      if (!initialized) {
         initialized = true;
         if (WlsIIOPInitialization.initialize()) {
            ProtocolHandlerIIOP.getProtocolHandler();
            ProtocolHandlerIIOPS.getProtocolHandler();
            MuxableSocketIIOP.initialize();

            try {
               InitialReferences.initializeClientInitialReferences();
            } catch (IOException var2) {
               throw new ServiceFailureException(var2);
            }
         }
      }
   }

   public void stop() {
      this.halt();
   }

   public void halt() {
      MuxableSocketIIOP.disable();
   }

   public static void resumeClient() {
      boolean debug = debugStartup.isEnabled() || debugIIOPStartup.isDebugEnabled();
      useLocateRequest = Kernel.getConfig().getIIOP().getUseLocateRequest();
      if (debug) {
         IIOPLogger.logLocateRequest(useLocateRequest ? "on" : "off");
      }

      setDefaultGIOPMinorVersion((byte)Kernel.getConfig().getIIOP().getDefaultMinorVersion());
      locationForwardPolicy = Kernel.getConfig().getIIOP().getLocationForwardPolicy();
      if (debug) {
         IIOPLogger.logLocationForwardPolicy(locationForwardPolicy);
      }

      useSerialFormatVersion2 = Kernel.getConfig().getIIOP().getUseSerialFormatVersion2();
      String defaultCodeset = Kernel.getConfig().getIIOP().getDefaultCharCodeset();
      String defaultWcodeset = Kernel.getConfig().getIIOP().getDefaultWideCharCodeset();
      CodeSet.setDefaults(CodeSet.getOSFCodeset(defaultCodeset), CodeSet.getOSFCodeset(defaultWcodeset));
      if (debug) {
         IIOPLogger.logCodeSet("char", defaultCodeset, "0x" + Integer.toHexString(CodeSet.getDefaultCharCodeSet()));
      }

      if (debug) {
         IIOPLogger.logCodeSet("wchar", defaultWcodeset, "0x" + Integer.toHexString(CodeSet.getDefaultWcharCodeSet()));
      }

      CodeSetsComponent.resetDefault();
      ClassInfo.initialize(Kernel.getConfig().getIIOP().getUseFullRepositoryIdList());
      DisconnectMonitorListImpl.getDisconnectMonitorList().addDisconnectMonitor(new DisconnectMonitorImpl());
   }

   private static void setDefaultGIOPMinorVersion(byte defaultGIOPMinorVersion) {
      if (debugStartup.isEnabled() || debugIIOPStartup.isDebugEnabled()) {
         IIOPLogger.logGIOPVersion(defaultGIOPMinorVersion);
      }

      ServerIORBuilder.setDefaultGIOPMinorVersion(defaultGIOPMinorVersion);
      EndPointSelector.setDefaultMinorVersion(defaultGIOPMinorVersion);
   }

   public void start() throws ServiceFailureException {
      this.initialize();
      resumeClient();
   }
}
