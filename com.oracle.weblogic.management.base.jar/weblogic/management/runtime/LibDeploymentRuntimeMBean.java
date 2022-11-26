package weblogic.management.runtime;

import java.util.Properties;

public interface LibDeploymentRuntimeMBean extends RuntimeMBean {
   String getLibraryName();

   String getLibraryIdentifier();

   String getSpecificationVersion();

   String getImplementationVersion();

   String getPartitionName();

   DeploymentProgressObjectMBean undeploy() throws RuntimeException;

   DeploymentProgressObjectMBean undeploy(String[] var1, Properties var2) throws RuntimeException;

   DeploymentProgressObjectMBean redeploy() throws RuntimeException;

   DeploymentProgressObjectMBean redeploy(String[] var1, Properties var2) throws RuntimeException;
}
