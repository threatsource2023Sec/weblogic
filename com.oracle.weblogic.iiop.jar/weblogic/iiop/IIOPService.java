package weblogic.iiop;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import org.omg.PortableServer.Servant;
import weblogic.corba.cos.naming.RootContextBindingInterceptor;
import weblogic.corba.cos.naming.RootNamingContextImpl;
import weblogic.corba.idl.CorbaStub;
import weblogic.corba.orb.WlsIIOPInitialization;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.security.SecurityServiceImpl;
import weblogic.iiop.server.InitialReferences;
import weblogic.jndi.ObjectCopier;
import weblogic.jndi.internal.JNDIEnvironment;
import weblogic.kernel.Kernel;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.io.Replacer;

@Service
@Named
@RunLevel(10)
public final class IIOPService implements ServerService {
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnRemoteNamingService;
   @Inject
   @Named("IIOPClientService")
   private ServerService dependencyOnIIOPClientService;
   @Inject
   @Named("ClusterServiceActivator")
   private ServerService dependencyOnClusterServiceActivator;
   private static final String FALSE_PROP = "false";
   public static final int TX_DISABLED_MECHANISM = 0;
   public static final int TX_OTS_MECHANISM = 1;
   public static final int TX_JTA_MECHANISM = 2;
   public static final int TX_OTS11_MECHANISM = 3;
   public static int txMechanism = 1;
   private static final DebugCategory debugStartup = Debug.getCategory("weblogic.iiop.startup");
   private static final DebugLogger debugIIOPStartup = DebugLogger.getDebugLogger("DebugIIOPStartup");
   private static volatile boolean enabled = false;

   public static boolean load() {
      return WlsIIOPInitialization.initialize();
   }

   public static void setTGIOPEnabled(boolean b) {
      enabled = b;
   }

   public static boolean isTGIOPEnabled() {
      return enabled;
   }

   @PreDestroy
   public void stop() throws ServiceFailureException {
      this.halt();
   }

   public void halt() throws ServiceFailureException {
      MuxableSocketIIOP.disable();
   }

   public String getName() {
      return "CorbaService";
   }

   public String getVersion() {
      return "CORBA 2.3, IIOP 1.2, RMI-IIOP SFV2, OTS 1.2, CSIv2 Level 0 + Stateful";
   }

   @PostConstruct
   public void start() throws ServiceFailureException {
      if ("false".equals(System.getProperty("weblogic.system.iiop.enableTxInterop"))) {
         txMechanism = 0;
      }

      boolean debug = debugStartup.isEnabled() || debugIIOPStartup.isDebugEnabled();

      try {
         InitialReferences.initializeServerInitialReferences();
      } catch (IOException var3) {
         throw new ServiceFailureException(var3);
      }

      IORManager.initialize();
      JNDIEnvironment.getJNDIEnvironment().addCopier(new IIOPObjectCopier());
      if (txMechanism > 0) {
         String txpolicy = Kernel.getConfig().getIIOP().getTxMechanism();
         if (txpolicy.equalsIgnoreCase("JTA")) {
            txMechanism = 2;
         } else if (txpolicy.equalsIgnoreCase("OTSv11")) {
            txMechanism = 3;
         } else if (txpolicy.equalsIgnoreCase("none")) {
            txMechanism = 0;
         }

         if (debug) {
            IIOPLogger.logJTAEnabled(txpolicy);
         }
      } else if (debug) {
         IIOPLogger.logJTAEnabled("none");
      }

      ClusterServices.initialize();
      this.bindGlobalServices(debug);
   }

   private void bindGlobalServices(boolean debug) {
      RootNamingContextImpl.getInitialReference().addBindInterceptor(new RootContextBindingInterceptor() {
         public String getBindingName() {
            return "weblogic/security/SecurityManager";
         }

         public Object getObjectToBind() {
            return new SecurityServiceImpl();
         }
      });
   }

   static class IIOPObjectCopier implements ObjectCopier {
      private final Replacer iiopReplacer = IIOPReplacer.getReplacer();

      public boolean mayCopy(Object objectToCopy) {
         return objectToCopy instanceof org.omg.CORBA.Object || objectToCopy instanceof Servant || objectToCopy instanceof CorbaStub;
      }

      public Object copyObject(Object objectToCopy) throws IOException {
         Object replaced = this.iiopReplacer.replaceObject(objectToCopy);
         return this.iiopReplacer.resolveObject(replaced);
      }

      public int getPriority() {
         return 80;
      }
   }
}
