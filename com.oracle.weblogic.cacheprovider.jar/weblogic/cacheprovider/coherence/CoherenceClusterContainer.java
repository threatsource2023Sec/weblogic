package weblogic.cacheprovider.coherence;

import weblogic.j2ee.descriptor.wl.CoherenceClusterRefBean;
import weblogic.management.ManagementException;

public interface CoherenceClusterContainer {
   CoherenceClusterRefBean getCoherenceClusterRefBean();

   void registerRuntimeMBeans(CoherenceClusterManager var1, ClassLoader var2) throws ManagementException;

   void unRegisterRuntimeMBeans(CoherenceClusterManager var1, ClassLoader var2) throws ManagementException;
}
