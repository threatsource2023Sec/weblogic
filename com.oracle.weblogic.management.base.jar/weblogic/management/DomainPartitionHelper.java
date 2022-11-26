package weblogic.management;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;

@Contract
public interface DomainPartitionHelper {
   void init(DomainMBean var1);

   DomainPartitionRuntimeMBean[] getDomainPartitionRuntimes();

   DomainPartitionRuntimeMBean lookupDomainPartitionRuntime(String var1);
}
