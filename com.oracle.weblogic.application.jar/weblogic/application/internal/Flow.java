package weblogic.application.internal;

import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.DeploymentContext;
import weblogic.management.DeploymentException;

public interface Flow {
   void prepare() throws DeploymentException;

   void activate() throws DeploymentException;

   void assertUndeployable() throws DeploymentException;

   void deactivate() throws DeploymentException;

   void unprepare() throws DeploymentException;

   void remove() throws DeploymentException;

   void start(String[] var1) throws DeploymentException;

   void stop(String[] var1) throws DeploymentException;

   void prepareUpdate(String[] var1) throws DeploymentException;

   void activateUpdate(String[] var1) throws DeploymentException;

   void rollbackUpdate(String[] var1) throws DeploymentException;

   void adminToProduction() throws DeploymentException;

   void forceProductionToAdmin(AdminModeCompletionBarrier var1) throws DeploymentException;

   void gracefulProductionToAdmin(AdminModeCompletionBarrier var1) throws DeploymentException;

   void validateRedeploy(DeploymentContext var1) throws DeploymentException;
}
