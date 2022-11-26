package weblogic.elasticity;

import com.oracle.core.interceptor.MethodInvocationContext;
import com.oracle.core.interceptor.WorkflowProducer;
import com.oracle.core.interceptor.impl.MethodInvocationContextManager;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.extras.interception.Intercepted;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.elasticity.util.ElasticityUtils;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.ScalingTaskRuntimeMBean;
import weblogic.management.workflow.FailurePlan;
import weblogic.management.workflow.WorkflowLifecycleManager;
import weblogic.management.workflow.WorkflowProgress;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.collections.ConcurrentHashMap;

@Service(
   name = "ElasticServiceManager"
)
@Singleton
@Intercepted
public class ElasticServiceManager {
   private static final String ELASTIC_SCALING_WORKFLOW_PREFIX = "ElasticScalingWorkflow_";
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugElasticServices");
   private ElasticServiceManagerRuntimeMBeanImpl esmMBean;
   private ConcurrentHashMap cluster2Tasks = new ConcurrentHashMap();
   private ConcurrentHashMap clusterScalingStatus = new ConcurrentHashMap();
   @Inject
   private WorkflowLifecycleManager workFlowManager;
   @Inject
   private MethodInvocationContext invCtx;
   @Inject
   private DynamicClusterScalingService dynamicClusterScalingService;
   @Inject
   private MethodInvocationContextManager methodInvocationContextManager;
   private long serviceSequence = 0L;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   @PostConstruct
   public void initialize() {
      try {
         if (this.esmMBean == null && ManagementService.getDomainAccess(kernelId) != null) {
            DomainRuntimeMBean domainRuntimeMBean = ManagementService.getDomainAccess(kernelId).getDomainRuntime();
            if (domainRuntimeMBean != null) {
               this.esmMBean = new ElasticServiceManagerRuntimeMBeanImpl("ElasticServiceManager", this);
            }
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Domain access runtime not found, elastic service manager will be disabled");
         }

      } catch (ManagementException var2) {
         var2.printStackTrace();
         throw new RuntimeException(var2);
      }
   }

   public ScalingTaskRuntimeMBean[] getScalingTasks() {
      List tasks = new ArrayList();
      Iterator var2 = this.cluster2Tasks.values().iterator();

      while(var2.hasNext()) {
         List list = (List)var2.next();
         Iterator var4 = list.iterator();

         while(var4.hasNext()) {
            ScalingTaskRuntimeMBeanImpl task = (ScalingTaskRuntimeMBeanImpl)var4.next();
            tasks.add(task);
         }
      }

      return (ScalingTaskRuntimeMBean[])tasks.toArray(new ScalingTaskRuntimeMBean[tasks.size()]);
   }

   public ScalingTaskRuntimeMBean[] getScalingTasks(String clusterName, int size) {
      LinkedList list = new LinkedList(this.getOrCreateScalingTaskList(clusterName));
      int sz = size < list.size() ? size : list.size();
      return (ScalingTaskRuntimeMBean[])list.subList(0, sz).toArray(new ScalingTaskRuntimeMBean[sz]);
   }

   @ScalingOperation
   @WorkflowProducer
   public void scaleUp(String clusterName, int scaleSize, Map payload) throws Exception {
      ElasticityUtils.checkForAdminServer();
      this.invCtx.getWorkflowBuilder().add(new ScaleUpWork(), new FailurePlan(false, false, false, 0, 0L));
   }

   @ScalingOperation
   @WorkflowProducer
   public void scaleDown(String clusterName, int scaleSize, Map payload) {
      ElasticityUtils.checkForAdminServer();
      this.invCtx.getWorkflowBuilder().add(new ScaleDownWork(), new FailurePlan(false, false, false, 0, 0L));
   }

   public int purgeScalingTasks(String clusterName, int age) {
      int count = 0;
      List list = (List)this.cluster2Tasks.get(clusterName);
      List candidates = new ArrayList();
      long ageInMillis = (long)(age * 1000);
      long now = System.currentTimeMillis();
      if (list != null) {
         Iterator var10 = list.iterator();

         ScalingTaskRuntimeMBeanImpl task;
         while(var10.hasNext()) {
            task = (ScalingTaskRuntimeMBeanImpl)var10.next();
            if (!task.isRunning()) {
               long endTime = task.getEndTime();
               if (endTime > 0L) {
                  long timeSinceCompletion = now - endTime;
                  if (timeSinceCompletion >= ageInMillis) {
                     candidates.add(task);
                  }
               }
            }
         }

         var10 = candidates.iterator();

         while(var10.hasNext()) {
            task = (ScalingTaskRuntimeMBeanImpl)var10.next();
            if (list.remove(task)) {
               ++count;
            }
         }
      }

      return count;
   }

   private List getOrCreateScalingTaskList(String clusterName) {
      List list = (List)this.cluster2Tasks.get(clusterName);
      if (list == null) {
         list = new LinkedList();
         List list2 = (List)this.cluster2Tasks.putIfAbsent(clusterName, list);
         if (list2 != null) {
            list = list2;
         }
      }

      return (List)list;
   }

   public ScalingTaskRuntimeMBeanImpl lookupScalingTask(String name) {
      Iterator var2 = this.cluster2Tasks.values().iterator();

      while(var2.hasNext()) {
         List list = (List)var2.next();
         Iterator var4 = list.iterator();

         while(var4.hasNext()) {
            ScalingTaskRuntimeMBeanImpl task = (ScalingTaskRuntimeMBeanImpl)var4.next();
            if (name.equals(task.getName())) {
               return task;
            }
         }
      }

      return null;
   }

   public ScalingTaskRuntimeMBeanImpl createScalingTask(String clusterName, ScalingTaskRuntimeMBean.ScalingType type, int scaleFactor, MethodInvocationContext ctx) throws ManagementException {
      ElasticityUtils.checkForAdminServer();
      ScalingTaskRuntimeMBeanImpl task = new ScalingTaskRuntimeMBeanImpl(clusterName, type, ctx, this.esmMBean);
      List list = this.getOrCreateScalingTaskList(clusterName);
      synchronized(list) {
         list.add(0, task);
         return task;
      }
   }

   public ClusterScalingStatus getClusterScalingStatus(String clusterName) {
      ClusterScalingStatus status = (ClusterScalingStatus)this.clusterScalingStatus.get(clusterName);
      if (status == null) {
         status = new ClusterScalingStatus();
         ClusterScalingStatus status2 = (ClusterScalingStatus)this.clusterScalingStatus.putIfAbsent(clusterName, status);
         if (status2 != null) {
            status = status2;
         }
      }

      return status;
   }

   public MethodInvocationContextManager getMethodInvocationContextManager() {
      return this.methodInvocationContextManager;
   }

   public synchronized String createWorkflowServiceIdentifier(String clusterName) {
      String id = this.createWorkFlowPrefix(clusterName) + this.serviceSequence++;
      return id;
   }

   public synchronized boolean isWorkflowInProgressForCluster(String clusterName, String currentWorkflowName) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Checking for existing incomplete workflows for cluster " + clusterName + " prior to executing " + currentWorkflowName);
      }

      String workflowServicePrefix = this.createWorkFlowPrefix(clusterName);
      List allWorkflows = this.workFlowManager.getActiveWorkflows();
      Iterator var5 = allWorkflows.iterator();

      WorkflowProgress progress;
      String existingWorkflowName;
      do {
         if (!var5.hasNext()) {
            return false;
         }

         progress = (WorkflowProgress)var5.next();
         existingWorkflowName = progress.getName();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Found workflow " + existingWorkflowName + ", service name: " + progress.getServiceName() + "id: " + progress.getWorkflowId() + ", for cluster " + clusterName + ", status: " + progress.getState());
         }
      } while(existingWorkflowName == null || existingWorkflowName.equals(currentWorkflowName) || !existingWorkflowName.startsWith(workflowServicePrefix) || this.isWorkFlowComplete(progress));

      return true;
   }

   private String createWorkFlowPrefix(String clusterName) {
      return "ElasticScalingWorkflow_" + clusterName + "_";
   }

   private boolean isWorkFlowComplete(WorkflowProgress progress) {
      switch (progress.getState()) {
         case NONE:
         case INITIALIZED:
         case STARTED:
         case REVERTING:
         case RETRY:
            return false;
         case CANCELED:
         case CAUSE_FAILURE:
         case DELETED:
         case FAIL:
         case REVERTED:
         case REVERT_CANCELED:
         case REVERT_FAIL:
         case SUCCESS:
         default:
            return true;
      }
   }
}
