package weblogic.coherence.api.internal;

import org.jvnet.hk2.annotations.Contract;
import weblogic.j2ee.descriptor.wl.CoherenceClusterRefBean;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ComponentRuntimeMBean;

@Contract
public interface CoherenceService {
   void setupCoherenceCaches(ClassLoader var1, ComponentRuntimeMBean var2, CoherenceClusterRefBean var3) throws DeploymentException;

   void releaseCoherenceCaches(ClassLoader var1, ComponentRuntimeMBean var2, String var3, CoherenceClusterRefBean var4) throws ManagementException, CoherenceException;
}
