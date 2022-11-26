package weblogic.diagnostics.lifecycle;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debugpatch.WLDFDebugPatchesRuntimeMBeanImpl;
import weblogic.management.ManagementException;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class DiagnosticFoundationService extends AbstractServerService {
   @Inject
   @Named("LoggingServerService")
   private ServerService depdendency;
   @Inject
   @Named("RMIServerService")
   private ServerService dependencyOnRMIServerService;
   private static final DiagnosticComponentLifecycle[] components = ComponentRegistry.getFoundationWLDFComponents();
   private static DiagnosticFoundationService diagnosticFoundationInstance;
   private boolean initialized;
   private WLDFRuntimeMBeanImpl wldfRuntime;

   public DiagnosticFoundationService() {
      Class var1 = DiagnosticFoundationService.class;
      synchronized(DiagnosticFoundationService.class) {
         if (diagnosticFoundationInstance == null) {
            diagnosticFoundationInstance = this;
         } else {
            throw new IllegalStateException("Attempt to instantiate multiple instances.");
         }
      }
   }

   public static synchronized DiagnosticFoundationService getInstance() {
      if (diagnosticFoundationInstance == null) {
         diagnosticFoundationInstance = new DiagnosticFoundationService();
      }

      return diagnosticFoundationInstance;
   }

   public DiagnosticComponentLifecycle[] getComponents() {
      return components;
   }

   public synchronized void start() throws ServiceFailureException {
      if (!this.initialized) {
         try {
            this.wldfRuntime = new WLDFRuntimeMBeanImpl();
            WLDFDebugPatchesRuntimeMBeanImpl debugPatchesRuntime = new WLDFDebugPatchesRuntimeMBeanImpl(this.wldfRuntime);
            this.wldfRuntime.setWLDFDebugPatchesRuntime(debugPatchesRuntime);
         } catch (ManagementException var4) {
            throw new ServiceFailureException(var4);
         }

         for(int i = 0; i < components.length; ++i) {
            try {
               components[i].initialize();
            } catch (DiagnosticComponentLifecycleException var3) {
               throw new ServiceFailureException(var3);
            }
         }

         this.initialized = true;
         DebugLifecycleUtility.debugHandlerStates(components);
      }
   }

   public void stop() throws ServiceFailureException {
      DebugLifecycleUtility.debugHandlerStates(components);
      if (this.initialized) {
         this.disableComponents();
         this.initialized = false;
      }
   }

   public void halt() throws ServiceFailureException {
      if (this.initialized) {
         this.disableComponents();
         this.initialized = false;
         DebugLifecycleUtility.debugHandlerStates(components);
      }
   }

   private void disableComponents() throws ServiceFailureException {
      for(int i = components.length - 1; i >= 0; --i) {
         try {
            components[i].disable();
         } catch (DiagnosticComponentLifecycleException var3) {
            throw new ServiceFailureException(var3);
         }
      }

   }
}
