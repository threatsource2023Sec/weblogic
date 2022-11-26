package com.oracle.injection.integration;

import java.util.logging.Logger;
import javax.enterprise.deploy.shared.ModuleType;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.logging.LoggingHelper;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class CDIIntegrationService extends AbstractServerService {
   @Inject
   @Named("DiagnosticFoundationService")
   private ServerService depdendency;
   private Logger weldLogger;

   public void start() throws ServiceFailureException {
      this.weldLogger = Logger.getLogger("org.jboss.weld");
      LoggingHelper.addServerLoggingHandler(this.weldLogger, false);
      ApplicationFactoryManager applicationFactoryManager = ApplicationFactoryManager.getApplicationFactoryManager();
      applicationFactoryManager.addModuleExtensionFactory(ModuleType.WAR.toString(), new CDIModuleExtensionFactory());
      applicationFactoryManager.addModuleExtensionFactory(ModuleType.EJB.toString(), new CDIModuleExtensionFactory());
      applicationFactoryManager.addModuleExtensionFactory(ModuleType.RAR.toString(), new CDIModuleExtensionFactory());
      applicationFactoryManager.addAppDeploymentExtensionFactory(new CDIAppDeploymentExtensionFactory());
   }
}
