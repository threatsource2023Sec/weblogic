package weblogic.jdbc.common.internal;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JDBCMultiDataSourceRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WorkManagerRuntimeMBean;

public final class MultiDataSourceRuntimeMBeanImpl extends RuntimeMBeanDelegate implements JDBCMultiDataSourceRuntimeMBean {
   private MultiPool multipool;
   private int state = 1;

   public MultiDataSourceRuntimeMBeanImpl(MultiPool multipool, String beanName, RuntimeMBean parent, RuntimeMBean restParent, DescriptorBean descriptor) throws ManagementException {
      super(beanName, parent, true, descriptor);
      if (restParent != null) {
         this.setRestParent(restParent);
      }

      this.multipool = multipool;
   }

   public void setDeploymentState(int state) {
      this.state = state;
   }

   public int getDeploymentState() {
      return this.state;
   }

   public boolean addWorkManagerRuntime(WorkManagerRuntimeMBean wmRuntime) {
      return true;
   }

   public WorkManagerRuntimeMBean[] getWorkManagerRuntimes() {
      return null;
   }

   public String getModuleId() {
      return this.getName();
   }

   public int getMaxCapacity() {
      return this.multipool.getMaxCapacity();
   }
}
