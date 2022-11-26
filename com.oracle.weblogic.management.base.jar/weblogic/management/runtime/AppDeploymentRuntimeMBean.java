package weblogic.management.runtime;

import java.util.Properties;

public interface AppDeploymentRuntimeMBean extends RuntimeMBean {
   String getApplicationName();

   String getApplicationVersion();

   String getPartitionName();

   DeploymentProgressObjectMBean start() throws RuntimeException;

   DeploymentProgressObjectMBean start(String[] var1, Properties var2) throws RuntimeException;

   DeploymentProgressObjectMBean stop() throws RuntimeException;

   DeploymentProgressObjectMBean stop(String[] var1, Properties var2) throws RuntimeException;

   DeploymentProgressObjectMBean undeploy() throws RuntimeException;

   DeploymentProgressObjectMBean undeploy(String[] var1, Properties var2) throws RuntimeException;

   DeploymentProgressObjectMBean redeploy() throws RuntimeException;

   DeploymentProgressObjectMBean redeploy(String[] var1, String var2, Properties var3) throws RuntimeException;

   DeploymentProgressObjectMBean redeploy(String[] var1, String var2, String var3, Properties var4) throws RuntimeException;

   String[] getModules();

   String getState(String var1);

   DeploymentProgressObjectMBean update(String[] var1, String var2, Properties var3) throws RuntimeException;
}
