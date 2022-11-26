package weblogic.j2ee;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ComponentConcurrentRuntimeMBean;
import weblogic.management.runtime.ManagedExecutorServiceRuntimeMBean;
import weblogic.management.runtime.ManagedScheduledExecutorServiceRuntimeMBean;
import weblogic.management.runtime.ManagedThreadFactoryRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;

public class ComponentConcurrentRuntimeMBeanImpl extends ComponentRuntimeMBeanImpl implements ComponentConcurrentRuntimeMBean {
   private Set managedThreadFactoryRuntimes = Collections.synchronizedSet(new HashSet());
   private Set managedExecutorRuntimes = Collections.synchronizedSet(new HashSet());
   private Set managedScheduledExecutorRuntimes = Collections.synchronizedSet(new HashSet());

   public ComponentConcurrentRuntimeMBeanImpl(String nameArg, String moduleId, boolean registerNow) throws ManagementException {
      super(nameArg, moduleId, registerNow);
   }

   public ComponentConcurrentRuntimeMBeanImpl(String nameArg, String moduleId, RuntimeMBean parentArg, boolean registerNow) throws ManagementException {
      super(nameArg, moduleId, parentArg, registerNow);
   }

   public ComponentConcurrentRuntimeMBeanImpl(String nameArg, String moduleId, RuntimeMBean parentArg, boolean registerNow, DescriptorBean descriptor) throws ManagementException {
      super(nameArg, moduleId, parentArg, registerNow, descriptor);
   }

   public ComponentConcurrentRuntimeMBeanImpl(String nameArg, String moduleId, RuntimeMBean parentArg) throws ManagementException {
      super(nameArg, moduleId, parentArg);
   }

   public boolean addManagedThreadFactoryRuntime(ManagedThreadFactoryRuntimeMBean mtfRuntime) {
      return this.managedThreadFactoryRuntimes.add(mtfRuntime);
   }

   public ManagedThreadFactoryRuntimeMBean[] getManagedThreadFactoryRuntimes() {
      int len = this.managedThreadFactoryRuntimes.size();
      return (ManagedThreadFactoryRuntimeMBean[])((ManagedThreadFactoryRuntimeMBean[])this.managedThreadFactoryRuntimes.toArray(new ManagedThreadFactoryRuntimeMBean[len]));
   }

   public boolean addManagedExecutorServiceRuntime(ManagedExecutorServiceRuntimeMBean mbean) {
      return this.managedExecutorRuntimes.add(mbean);
   }

   public ManagedExecutorServiceRuntimeMBean[] getManagedExecutorServiceRuntimes() {
      int len = this.managedExecutorRuntimes.size();
      return (ManagedExecutorServiceRuntimeMBean[])((ManagedExecutorServiceRuntimeMBean[])this.managedExecutorRuntimes.toArray(new ManagedExecutorServiceRuntimeMBean[len]));
   }

   public boolean addManagedScheduledExecutorServiceRuntime(ManagedScheduledExecutorServiceRuntimeMBean mbean) {
      return this.managedScheduledExecutorRuntimes.add(mbean);
   }

   public ManagedScheduledExecutorServiceRuntimeMBean[] getManagedScheduledExecutorServiceRuntimes() {
      int len = this.managedScheduledExecutorRuntimes.size();
      return (ManagedScheduledExecutorServiceRuntimeMBean[])((ManagedScheduledExecutorServiceRuntimeMBean[])this.managedScheduledExecutorRuntimes.toArray(new ManagedScheduledExecutorServiceRuntimeMBean[len]));
   }
}
