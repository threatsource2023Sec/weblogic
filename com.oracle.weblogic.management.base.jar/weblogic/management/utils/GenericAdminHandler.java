package weblogic.management.utils;

import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;

public interface GenericAdminHandler {
   void prepare(GenericManagedDeployment var1) throws DeploymentException;

   void activate(GenericManagedDeployment var1) throws DeploymentException;

   void deactivate(GenericManagedDeployment var1) throws UndeploymentException;

   void unprepare(GenericManagedDeployment var1) throws UndeploymentException;
}
