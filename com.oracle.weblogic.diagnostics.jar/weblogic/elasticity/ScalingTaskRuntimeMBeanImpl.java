package weblogic.elasticity;

import com.oracle.core.interceptor.MethodInvocationContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ElasticServiceManagerRuntimeMBean;
import weblogic.management.runtime.ScalingTaskRuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBeanImpl;
import weblogic.management.workflow.WorkflowProgress;
import weblogic.management.workflow.WorkflowProgress.State;

public class ScalingTaskRuntimeMBeanImpl extends TaskRuntimeMBeanImpl implements ScalingTaskRuntimeMBean {
   private static AtomicLong _taskIdCounter = new AtomicLong(0L);
   private String clusterName;
   private ScalingTaskRuntimeMBean.ScalingType scalingType;
   private MethodInvocationContext invocationContext;
   private WorkflowProgress workflowProgress;

   public ScalingTaskRuntimeMBeanImpl(String clusterName, ScalingTaskRuntimeMBean.ScalingType scalingType, MethodInvocationContext ctx, ElasticServiceManagerRuntimeMBean parent) throws ManagementException {
      super(scalingType.toString() + "_" + _taskIdCounter.incrementAndGet(), parent, true);
      this.clusterName = clusterName;
      this.scalingType = scalingType;
      this.invocationContext = ctx;
      this.workflowProgress = ctx.getWorkflowProgress();
   }

   public String[] getSelectedInstanceNames() {
      ArrayList selectedNames = new ArrayList();
      Collection coll = this.workflowProgress.getSharedState("InterceptorSharedDataConstants_workflow_shared_data_map_key");
      if (coll != null) {
         Set names = new HashSet();
         Iterator var4 = coll.iterator();

         while(true) {
            ScalingOperationStatus operationStatus;
            do {
               if (!var4.hasNext()) {
                  return (String[])selectedNames.toArray(new String[selectedNames.size()]);
               }

               Serializable ser = (Serializable)var4.next();
               Map sharedMap = (Map)ser;
               operationStatus = (ScalingOperationStatus)sharedMap.get("InterceptorSharedDataConstants_elasticity_scaling_operation_status_key");
            } while(operationStatus == null);

            Iterator var8 = operationStatus.getCandidateMemberNames().iterator();

            while(var8.hasNext()) {
               String name = (String)var8.next();
               if (!names.contains(name)) {
                  names.add(name);
                  selectedNames.add(name);
               }
            }
         }
      } else {
         return (String[])selectedNames.toArray(new String[selectedNames.size()]);
      }
   }

   public String[] getScaledInstanceNames() {
      ArrayList scaledInstancesNames = new ArrayList();
      Collection coll = this.workflowProgress.getSharedState("InterceptorSharedDataConstants_workflow_shared_data_map_key");
      if (coll != null) {
         Set names = new HashSet();
         Iterator var4 = coll.iterator();

         while(true) {
            ScalingOperationStatus operationStatus;
            do {
               if (!var4.hasNext()) {
                  return (String[])scaledInstancesNames.toArray(new String[scaledInstancesNames.size()]);
               }

               Serializable ser = (Serializable)var4.next();
               Map sharedMap = (Map)ser;
               operationStatus = (ScalingOperationStatus)sharedMap.get("InterceptorSharedDataConstants_elasticity_scaling_operation_status_key");
            } while(operationStatus == null);

            Iterator var8 = operationStatus.getScaledMemberNames().iterator();

            while(var8.hasNext()) {
               String name = (String)var8.next();
               if (!names.contains(name)) {
                  names.add(name);
                  scaledInstancesNames.add(name);
               }
            }
         }
      } else {
         return (String[])scaledInstancesNames.toArray(new String[scaledInstancesNames.size()]);
      }
   }

   public void cancel() throws Exception {
      if (this.workflowProgress != null && !this.workflowProgress.isComplete()) {
         this.workflowProgress.cancel();
      }

   }

   public boolean isRunning() {
      return this.workflowProgress.isActive();
   }

   public String getProgress() {
      if (this.isRunning()) {
         return "processing";
      } else {
         return this.isSuccess() ? "success" : "failed";
      }
   }

   public boolean isSuccess() {
      return this.workflowProgress.getState() == State.SUCCESS;
   }

   public String getStatus() {
      return this.workflowProgress.getState().toString();
   }

   public String getClusterName() {
      return this.clusterName;
   }

   public TaskRuntimeMBean[] getSubTasks() {
      return new TaskRuntimeMBean[0];
   }

   public TaskRuntimeMBean getParentTask() {
      return null;
   }

   public String getScalingType() {
      return this.scalingType.toString();
   }

   public Exception getError() {
      Exception ex = this.workflowProgress.getFailureCause();
      if (ex == null) {
         return null;
      } else {
         Exception ex1 = new Exception(ex.getMessage());
         ex1.setStackTrace(ex.getStackTrace());
         return ex1;
      }
   }

   public Properties getSelectedInstanceMetadata() {
      Properties scaledInstancesMetadata = new Properties();
      Collection coll = this.workflowProgress.getSharedState("InterceptorSharedDataConstants_workflow_shared_data_map_key");
      if (coll != null) {
         Set instanceNames = new HashSet();
         Iterator var4 = coll.iterator();

         while(true) {
            ScalingOperationStatus operationStatus;
            do {
               if (!var4.hasNext()) {
                  return scaledInstancesMetadata;
               }

               Serializable ser = (Serializable)var4.next();
               Map sharedMap = (Map)ser;
               operationStatus = (ScalingOperationStatus)sharedMap.get("InterceptorSharedDataConstants_elasticity_scaling_operation_status_key");
            } while(operationStatus == null);

            Iterator var8 = operationStatus.getInstanceMetadata().entrySet().iterator();

            while(var8.hasNext()) {
               Map.Entry metaEntry = (Map.Entry)var8.next();
               if (!instanceNames.contains(metaEntry.getKey().toString())) {
                  scaledInstancesMetadata.put(metaEntry.getKey(), metaEntry.getValue());
               }
            }
         }
      } else {
         return scaledInstancesMetadata;
      }
   }

   public long getBeginTime() {
      Date time = this.workflowProgress.getStartTime();
      return time != null ? time.getTime() : -1L;
   }

   public long getEndTime() {
      Date time = this.workflowProgress.getEndTime();
      return time != null ? time.getTime() : -1L;
   }
}
