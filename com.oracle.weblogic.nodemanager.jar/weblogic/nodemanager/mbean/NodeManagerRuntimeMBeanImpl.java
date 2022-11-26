package weblogic.nodemanager.mbean;

import weblogic.management.ManagementException;
import weblogic.management.configuration.NodeManagerMBean;
import weblogic.management.runtime.NodeManagerRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class NodeManagerRuntimeMBeanImpl extends RuntimeMBeanDelegate implements NodeManagerRuntimeMBean {
   private NodeManagerMBean nmMBean;

   public NodeManagerRuntimeMBeanImpl(NodeManagerMBean nmMBean) throws ManagementException {
      super(nmMBean.getName(), (RuntimeMBean)null);
      this.nmMBean = nmMBean;
   }

   public boolean isReachable() {
      return NodeManagerRuntime.isReachable(this.nmMBean);
   }
}
