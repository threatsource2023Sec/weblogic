package weblogic.j2ee;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WorkManagerRuntimeMBean;

public class ComponentRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ComponentRuntimeMBean {
   private final String moduleId;
   protected int state = 3;
   private Set workManagerRuntimes = Collections.synchronizedSet(new HashSet());

   public ComponentRuntimeMBeanImpl(String nameArg, String moduleId, boolean registerNow) throws ManagementException {
      super(nameArg, registerNow, "ComponentRuntimes");
      this.moduleId = moduleId;
   }

   public ComponentRuntimeMBeanImpl(String nameArg, String moduleId, RuntimeMBean parentArg, boolean registerNow) throws ManagementException {
      super(nameArg, parentArg, registerNow, "ComponentRuntimes");
      this.moduleId = moduleId;
   }

   public ComponentRuntimeMBeanImpl(String nameArg, String moduleId, RuntimeMBean parentArg, boolean registerNow, DescriptorBean descriptor) throws ManagementException {
      super(nameArg, parentArg, registerNow, descriptor, "ComponentRuntimes");
      this.moduleId = moduleId;
   }

   public ComponentRuntimeMBeanImpl(String nameArg, String moduleId, RuntimeMBean parentArg) throws ManagementException {
      super(nameArg, parentArg);
      this.moduleId = moduleId;
   }

   public String getModuleId() {
      return this.moduleId;
   }

   public int getDeploymentState() {
      return this.state;
   }

   public void setDeploymentState(int state) {
      this.state = state;
   }

   public boolean addWorkManagerRuntime(WorkManagerRuntimeMBean wmRuntime) {
      return this.workManagerRuntimes.add(wmRuntime);
   }

   public WorkManagerRuntimeMBean[] getWorkManagerRuntimes() {
      int len = this.workManagerRuntimes.size();
      return (WorkManagerRuntimeMBean[])((WorkManagerRuntimeMBean[])this.workManagerRuntimes.toArray(new WorkManagerRuntimeMBean[len]));
   }

   public void unregister() throws ManagementException {
      super.unregister();
   }
}
