package weblogic.management;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.runtime.NodeManagerRuntimeMBean;
import weblogic.server.ServiceFailureException;

@Contract
public interface NodeManagerRuntimeService {
   NodeManagerRuntimeMBean[] getNodeManagerRuntimes();

   void hardShutdown() throws ServiceFailureException;
}
