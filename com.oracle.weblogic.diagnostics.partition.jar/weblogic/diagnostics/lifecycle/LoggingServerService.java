package weblogic.diagnostics.lifecycle;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class LoggingServerService extends AbstractServerService {
   @Inject
   @Named("DomainAccessService")
   private ServerService depdendency;
   private static final DiagnosticComponentLifecycle[] COMPONENTS = new DiagnosticComponentLifecycle[]{new ServerLoggingLifecycleImpl(), new DebugLifecycleImpl()};

   public synchronized void start() throws ServiceFailureException {
      DiagnosticComponentLifecycle[] var1 = COMPONENTS;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         DiagnosticComponentLifecycle component = var1[var3];

         try {
            component.initialize();
         } catch (DiagnosticComponentLifecycleException var6) {
            throw new ServiceFailureException(var6);
         }
      }

   }

   public synchronized void stop() throws ServiceFailureException {
      DiagnosticComponentLifecycle[] var1 = COMPONENTS;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         DiagnosticComponentLifecycle component = var1[var3];

         try {
            component.disable();
         } catch (DiagnosticComponentLifecycleException var6) {
            throw new ServiceFailureException(var6);
         }
      }

   }
}
