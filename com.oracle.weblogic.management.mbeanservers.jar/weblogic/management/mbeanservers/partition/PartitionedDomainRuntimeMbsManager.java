package weblogic.management.mbeanservers.partition;

import org.jvnet.hk2.annotations.Service;

@Service
public class PartitionedDomainRuntimeMbsManager extends PartitionedMbsManager {
   protected String getJndiName() {
      return "weblogic/jmx/domainRuntime";
   }

   protected PartitionedMbsRefObjFactory getPartitionedMbsRefObjFactory() {
      return new PartitionedDomainRuntimeMbsRefObjFactory();
   }
}
