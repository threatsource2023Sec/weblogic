package weblogic.diagnostics.lifecycle;

import java.io.File;
import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.instrumentation.InstrumentationLibrary;
import weblogic.diagnostics.instrumentation.InstrumentationManager;
import weblogic.diagnostics.instrumentation.engine.InstrumentationEngineConfiguration;
import weblogic.management.DomainDir;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class DiagnosticInstrumentationService extends AbstractServerService {
   @Inject
   @Named("IIOPClientService")
   private ServerService depdendency;
   @Inject
   @Named("ValidationService")
   private ServerService validationService;
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void start() throws ServiceFailureException {
      try {
         InstrumentationManager.getInstrumentationManager().initialize();
         String serializedEngineConfigFilePath = null;
         String serverName = null;
         RuntimeAccess rtAccess = ManagementService.getRuntimeAccess(kernelID);
         if (rtAccess != null) {
            serverName = rtAccess.getServerName();
         }

         if (serverName == null || serverName.equals("")) {
            serverName = System.getProperty("weblogic.Name", "myserver");
         }

         if ("".equals(serverName)) {
            serverName = null;
         }

         if (serverName != null) {
            File f = new File(DomainDir.getPathRelativeServersCacheDir(serverName, "diagnostics"));
            f = new File(f, "InstrumentationEngineConfig.ser");
            serializedEngineConfigFilePath = f.getAbsolutePath();
         }

         InstrumentationEngineConfiguration engineConf = InstrumentationEngineConfiguration.getInstrumentationEngineConfiguration();
         if (engineConf != null && serializedEngineConfigFilePath != null) {
            InstrumentationEngineConfiguration.saveEngineConfiguration(engineConf, serializedEngineConfigFilePath);
         }

         InstrumentationLibrary.getInstrumentationLibrary().removeUnkeptPointcuts();
      } catch (Exception var5) {
         throw new ServiceFailureException(var5);
      }
   }
}
