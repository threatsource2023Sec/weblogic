package weblogic.management.patching;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import weblogic.management.ManagementException;
import weblogic.management.patching.model.MachineInfo;
import weblogic.management.runtime.RolloutTaskRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.workflow.mbean.WorkflowProgressMBeanDelegate;

public class RolloutProgressMBeanDelegate extends WorkflowProgressMBeanDelegate implements RolloutTaskRuntimeMBean {
   public RolloutProgressMBeanDelegate(RolloutProgress progress, RuntimeMBean parentArg) throws ManagementException {
      super(progress, parentArg);
   }

   public RolloutProgressMBeanDelegate(RolloutProgress progress, String description, RuntimeMBean parentArg) throws ManagementException {
      super(progress, description, parentArg);
   }

   public CompositeData[] getTargetedNodes() {
      ArrayList targetedNodes = null;
      List nodes = ((RolloutProgress)this.progress).getTargetedNodes();
      if (nodes != null && !nodes.isEmpty()) {
         targetedNodes = new ArrayList();
         Iterator var3 = nodes.iterator();

         while(var3.hasNext()) {
            MachineInfo mi = (MachineInfo)var3.next();

            try {
               CompositeData cd = mi.toCompositeData();
               targetedNodes.add(cd);
            } catch (OpenDataException var6) {
               PatchingDebugLogger.debug("getTargetedNodes failed to get composite data for MachineInfo: " + mi.getMachineName(), var6);
            }
         }
      }

      CompositeData[] data = null;
      if (targetedNodes != null && targetedNodes.size() > 0) {
         data = new CompositeData[targetedNodes.size()];
         targetedNodes.toArray(data);
      }

      return data;
   }

   public CompositeData[] getUpdatedNodes() {
      ArrayList updatedNodes = null;
      List nodes = ((RolloutProgress)this.progress).getUpdatedNodes();
      if (nodes != null && !nodes.isEmpty()) {
         updatedNodes = new ArrayList();
         Iterator var3 = nodes.iterator();

         while(var3.hasNext()) {
            MachineInfo mi = (MachineInfo)var3.next();

            try {
               CompositeData cd = mi.toCompositeData();
               updatedNodes.add(cd);
            } catch (OpenDataException var6) {
               PatchingDebugLogger.debug("getUpdatedNodes failed to get composite data for MachineInfo: " + mi.getMachineName(), var6);
            }
         }
      }

      CompositeData[] data = null;
      if (updatedNodes != null && updatedNodes.size() > 0) {
         data = new CompositeData[updatedNodes.size()];
         updatedNodes.toArray(data);
      }

      return data;
   }
}
