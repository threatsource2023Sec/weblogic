package weblogic.diagnostics.lifecycle;

import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.harvester.WLDFHarvester;
import weblogic.diagnostics.harvester.WLDFHarvesterManager;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(15)
public final class DiagnosticSystemService extends AbstractServerService {
   @Inject
   @Named("DeploymentServerService")
   private ServerService depdendency;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DiagnosticComponentLifecycle[] components = ComponentRegistry.getNonFoundationWLDFComponents();
   private static DiagnosticSystemService diagnosticSystemInstance;
   private boolean initialized;
   private static WLDFHarvester harvesterSingleton;

   public DiagnosticSystemService() {
      Class var1 = DiagnosticSystemService.class;
      synchronized(DiagnosticSystemService.class) {
         if (diagnosticSystemInstance == null) {
            diagnosticSystemInstance = this;
         } else {
            throw new IllegalStateException("Attempt to instantiate multiple instances.");
         }
      }
   }

   public static synchronized DiagnosticSystemService getInstance() {
      if (diagnosticSystemInstance == null) {
         diagnosticSystemInstance = new DiagnosticSystemService();
      }

      return diagnosticSystemInstance;
   }

   public DiagnosticComponentLifecycle[] getComponents() {
      return components;
   }

   public void start() throws ServiceFailureException {
      if (!this.initialized) {
         ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getServer();
         DiagnosticsLogger.logDiagnosticsInitializing(server.getName());
         harvesterSingleton = WLDFHarvesterManager.getInstance().getHarvesterSingleton();

         for(int i = 0; i < components.length; ++i) {
            try {
               components[i].initialize();
            } catch (DiagnosticComponentLifecycleException var4) {
               throw new ServiceFailureException(var4);
            }
         }

         this.initialized = true;
         DebugLifecycleUtility.debugHandlerStates(components);
      }
   }

   public void stop() throws ServiceFailureException {
      if (this.initialized) {
         DiagnosticsLogger.logDiagnosticsStopping(ManagementService.getRuntimeAccess(kernelId).getServer().getName());

         for(int i = components.length - 1; i >= 0; --i) {
            try {
               components[i].disable();
            } catch (DiagnosticComponentLifecycleException var3) {
               throw new ServiceFailureException(var3);
            }
         }

         this.initialized = false;
         DebugLifecycleUtility.debugHandlerStates(components);
         if (harvesterSingleton != null) {
            harvesterSingleton.deallocate();
         }

      }
   }

   public void halt() throws ServiceFailureException {
      if (this.initialized) {
         DiagnosticsLogger.logDiagnosticsStopping(ManagementService.getRuntimeAccess(kernelId).getServer().getName());

         for(int i = components.length - 1; i >= 0; --i) {
            try {
               components[i].disable();
            } catch (DiagnosticComponentLifecycleException var3) {
               throw new ServiceFailureException(var3);
            }
         }

         this.initialized = false;
         if (harvesterSingleton != null) {
            harvesterSingleton.deallocate();
         }

         DebugLifecycleUtility.debugHandlerStates(components);
      }
   }

   public static boolean isInitialized() {
      return diagnosticSystemInstance != null && diagnosticSystemInstance.initialized;
   }
}
