package weblogic.management.mbeanservers.partition;

import org.jvnet.hk2.annotations.Service;

@Service
public class PartitionedRuntimeMbsManager extends PartitionedMbsManager {
   protected String getJndiName() {
      return "weblogic/jmx/runtime";
   }

   protected PartitionedMbsRefObjFactory getPartitionedMbsRefObjFactory() {
      return new PartitionedRuntimeMbsRefObjFactory();
   }
}
