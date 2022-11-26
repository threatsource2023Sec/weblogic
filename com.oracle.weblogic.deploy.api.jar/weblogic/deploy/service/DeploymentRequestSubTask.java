package weblogic.deploy.service;

import java.util.Map;
import weblogic.management.ManagementException;
import weblogic.management.runtime.TaskRuntimeMBean;

public interface DeploymentRequestSubTask extends TaskRuntimeMBean {
   void prepareToStart() throws ManagementException;

   void prepareToCancel() throws Exception;

   void setMyParent(TaskRuntimeMBean var1);

   boolean isComplete();

   Map getFailedTargets();
}
