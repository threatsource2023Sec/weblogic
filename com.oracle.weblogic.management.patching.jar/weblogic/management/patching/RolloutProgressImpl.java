package weblogic.management.patching;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import weblogic.management.workflow.CorruptedStoreException;
import weblogic.management.workflow.WorkflowProgress;
import weblogic.management.workflow.internal.Workflow;
import weblogic.management.workflow.internal.WorkflowProgressImpl;

public class RolloutProgressImpl extends WorkflowProgressImpl implements RolloutProgress, Serializable {
   public RolloutProgressImpl() {
   }

   public RolloutProgressImpl(WorkflowProgress progress) {
      this(progress.getWorkflow(), progress.getServiceName(), progress.getMeta());
   }

   public RolloutProgressImpl(Workflow workflow, String serviceName, Map meta) throws CorruptedStoreException {
      super(workflow, serviceName, meta);
   }

   public List getTargetedNodes() {
      List targetedNodes = null;
      Workflow wf = this.getWorkflow();
      if (wf != null) {
         Map state = wf.getShareState();
         if (state != null) {
            List mis = (List)state.get("targetedNodes");
            if (mis != null) {
               targetedNodes = mis;
            }
         } else if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("getTargetedNodes failed to access the workflow state");
         }
      } else if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("getTargetedNodes failed to access the workflow");
      }

      return targetedNodes;
   }

   public List getUpdatedNodes() {
      List updatedNodes = null;
      Workflow wf = this.getWorkflow();
      if (wf != null) {
         Map state = wf.getShareState();
         if (state != null) {
            List mis = (List)state.get("updatedNodes");
            if (mis != null) {
               updatedNodes = mis;
            }
         } else if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("getUpdatedNodes failed to access the workflow state");
         }
      } else if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("getUpdatedNodes failed to access the workflow");
      }

      return updatedNodes;
   }
}
