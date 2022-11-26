package weblogic.application;

import java.io.File;
import weblogic.application.archive.ApplicationArchive;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;

public interface DeploymentFactory {
   boolean isSupportedBasic(BasicDeploymentMBean var1, File var2) throws DeploymentException;

   boolean isSupportedAdvanced(BasicDeploymentMBean var1, File var2) throws DeploymentException;

   Deployment createDeployment(AppDeploymentMBean var1, File var2) throws DeploymentException;

   /** @deprecated */
   @Deprecated
   Deployment createDeployment(AppDeploymentMBean var1, ApplicationArchive var2) throws DeploymentException;

   Deployment createDeployment(SystemResourceMBean var1, File var2) throws DeploymentException;

   /** @deprecated */
   @Deprecated
   Deployment createDeployment(SystemResourceMBean var1, ApplicationArchive var2) throws DeploymentException;
}
