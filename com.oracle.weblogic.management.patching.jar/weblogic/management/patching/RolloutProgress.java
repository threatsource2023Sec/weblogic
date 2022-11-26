package weblogic.management.patching;

import java.util.List;
import weblogic.management.workflow.WorkflowProgress;

public interface RolloutProgress extends WorkflowProgress {
   String TARGETED_NODES_PARAM = "targetedNodes";
   String UPDATED_NODES_PARAM = "updatedNodes";

   List getTargetedNodes();

   List getUpdatedNodes();
}
