package weblogic.management.mbeanservers.partition;

import org.jvnet.hk2.annotations.Service;

@Service
public class PartitionedEditMbsManager extends PartitionedMbsManager {
   protected String getJndiName() {
      return "weblogic/jmx/edit";
   }

   protected PartitionedMbsRefObjFactory getPartitionedMbsRefObjFactory() {
      return new PartitionedEditMbsRefObjFactory();
   }
}
