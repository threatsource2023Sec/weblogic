package weblogic.elasticity;

import com.oracle.core.interceptor.MethodInvocationContext;
import java.security.AccessController;
import java.util.HashMap;
import weblogic.elasticity.i18n.ElasticityTextTextFormatter;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.ElasticServiceManagerRuntimeMBean;
import weblogic.management.runtime.ScalingTaskRuntimeMBean;
import weblogic.management.runtime.ScalingTaskRuntimeMBean.ScalingType;
import weblogic.management.workflow.WorkflowProgress;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ElasticServiceManagerRuntimeMBeanImpl extends DomainRuntimeMBeanDelegate implements ElasticServiceManagerRuntimeMBean {
   private static final ElasticityTextTextFormatter txtFmt = ElasticityTextTextFormatter.getInstance();
   private final ElasticServiceManager esm;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   ElasticServiceManagerRuntimeMBeanImpl(String nameArg, ElasticServiceManager esm) throws ManagementException {
      super(nameArg);
      ManagementService.getDomainAccess(kernelId).getDomainRuntime().setElasticServiceManagerRuntime(this);
      this.esm = esm;
   }

   public ScalingTaskRuntimeMBean[] getScalingTasks() {
      return this.esm.getScalingTasks();
   }

   public ScalingTaskRuntimeMBean[] getScalingTasks(String clusterName, int size) {
      return this.esm.getScalingTasks(clusterName, size);
   }

   public ScalingTaskRuntimeMBean lookupScalingTask(String name) {
      return this.esm.lookupScalingTask(name);
   }

   public ScalingTaskRuntimeMBean scaleUp(String clusterName, int scaleFactor) {
      try {
         this.esm.scaleUp(clusterName, scaleFactor, new HashMap());
         MethodInvocationContext ctx = this.esm.getMethodInvocationContextManager().getMostRecentCompletedMethodInvocationContext();
         WorkflowProgress workflowProgress = ctx.getWorkflowProgress();
         if (workflowProgress == null) {
            throw new IllegalStateException(txtFmt.getWorkFlowProgressUnavailableText(ScalingType.ScaleUp.toString()));
         } else {
            ScalingTaskRuntimeMBeanImpl scalingTaskRuntimeMBean = this.esm.createScalingTask(clusterName, ScalingType.ScaleUp, scaleFactor, ctx);
            return scalingTaskRuntimeMBean;
         }
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public ScalingTaskRuntimeMBean scaleDown(String clusterName, int scaleFactor) {
      try {
         this.esm.scaleDown(clusterName, scaleFactor, new HashMap());
         MethodInvocationContext ctx = this.esm.getMethodInvocationContextManager().getMostRecentCompletedMethodInvocationContext();
         WorkflowProgress workflowProgress = ctx.getWorkflowProgress();
         if (workflowProgress == null) {
            throw new IllegalStateException(txtFmt.getWorkFlowProgressUnavailableText(ScalingType.ScaleDown.toString()));
         } else {
            ScalingTaskRuntimeMBeanImpl scalingTaskRuntimeMBean = this.esm.createScalingTask(clusterName, ScalingType.ScaleDown, scaleFactor, ctx);
            return scalingTaskRuntimeMBean;
         }
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public int purgeScalingTasks(String clusterName, int age) {
      return this.esm.purgeScalingTasks(clusterName, age);
   }
}
