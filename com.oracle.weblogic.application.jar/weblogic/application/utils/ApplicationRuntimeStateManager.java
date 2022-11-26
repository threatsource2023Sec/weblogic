package weblogic.application.utils;

import java.util.List;
import java.util.Map;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;

@Contract
public interface ApplicationRuntimeStateManager {
   void possiblyFixAppRuntimeState(AppDeploymentMBean var1, String var2, String[] var3) throws ManagementException;

   String[] getStoppedModuleIds(String var1, String var2);

   boolean isActiveVersion(String var1);

   boolean isActiveVersion(AppDeploymentMBean var1);

   boolean isAdminMode(String var1);

   String getCurrentStateFromLocalData(String var1, String var2);

   void updateApplicationChecker(long var1, String var3, Object var4);

   Map getCurrentStateOfAllVersionsFromLocalData(List var1, List var2, String var3);

   void updateApplicationCheckerWithUnresponsiveTarget(long var1, String var3);
}
