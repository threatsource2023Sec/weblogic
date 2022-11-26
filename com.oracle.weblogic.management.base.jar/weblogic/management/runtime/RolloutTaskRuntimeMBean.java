package weblogic.management.runtime;

import javax.management.openmbean.CompositeData;

public interface RolloutTaskRuntimeMBean extends WorkflowTaskRuntimeMBean {
   CompositeData[] getTargetedNodes();

   CompositeData[] getUpdatedNodes();
}
