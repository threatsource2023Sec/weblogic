package weblogic.management;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.ResourceGroupLifeCycleRuntimeMBean;

@Contract
public interface PartitionLifeCycleHelper {
   void init(DomainMBean var1, DomainRuntimeMBean var2);

   ResourceGroupLifeCycleRuntimeMBean[] getResourceGroupLifeCycleRuntimes();

   ResourceGroupLifeCycleRuntimeMBean lookupResourceGroupLifeCycleRuntime(String var1);
}
