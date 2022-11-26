package weblogic.elasticity;

import com.oracle.core.interceptor.MethodInvocationContext;
import java.lang.annotation.Annotation;
import javax.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.watch.actions.ClusterInfo;
import weblogic.elasticity.i18n.ElasticityTextTextFormatter;
import weblogic.elasticity.util.ElasticityUtils;
import weblogic.management.workflow.FailurePlan;
import weblogic.management.workflow.command.CommandInterface;
import weblogic.management.workflow.command.WorkflowContext;
import weblogic.server.GlobalServiceLocator;

@Service(
   name = "ElasticServiceInterceptor"
)
@Interceptor
@ScalingOperation
@ContractsProvided({MethodInterceptor.class, ElasticServiceInterceptor.class})
@Rank(2147483646)
public class ElasticServiceInterceptor implements MethodInterceptor {
   @Inject
   MethodInvocationContext invCtx;
   @Inject
   private ElasticServiceManager esm;
   private static final ElasticityTextTextFormatter DTF = ElasticityTextTextFormatter.getInstance();
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugElasticServices");

   public Object invoke(MethodInvocation methodInvocation) throws Throwable {
      ElasticityUtils.checkForAdminServer();
      String operationName = methodInvocation.getMethod().getName();
      Object[] args;
      String clusterName;
      if (operationName.equals("scaleUp") || operationName.equals("scaleDown")) {
         args = methodInvocation.getArguments();
         clusterName = (String)args[0];
         int scaleFactor = Integer.valueOf((Integer)args[1]);
         this.validateClusterNameAndScaleFactor(clusterName, scaleFactor);
         String serviceIdentifier = this.esm.createWorkflowServiceIdentifier(clusterName);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("ElasticServiceInterceptor: creating workflow with service name " + serviceIdentifier);
         }

         this.invCtx.getWorkflowBuilder().name(serviceIdentifier).add(new InstanceChooser(clusterName, operationName, scaleFactor), new FailurePlan(false, false, false, 0, 0L));
      }

      args = null;
      boolean var10 = false;

      Object result;
      try {
         var10 = true;
         result = methodInvocation.proceed();
         var10 = false;
      } finally {
         if (var10) {
            if (operationName.equals("scaleUp") || operationName.equals("scaleDown")) {
               String clusterName = (String)methodInvocation.getArguments()[0];
               this.invCtx.getWorkflowBuilder().add(new ScalingOperationCompletionMarker(clusterName), new FailurePlan(false, false, false, 0, 0L));
            }

         }
      }

      if (operationName.equals("scaleUp") || operationName.equals("scaleDown")) {
         clusterName = (String)methodInvocation.getArguments()[0];
         this.invCtx.getWorkflowBuilder().add(new ScalingOperationCompletionMarker(clusterName), new FailurePlan(false, false, false, 0, 0L));
      }

      return result;
   }

   private void validateClusterNameAndScaleFactor(String clusterName, int scaleFactor) {
      if (scaleFactor <= 0) {
         throw new IllegalArgumentException(DTF.getInvalidScalingFactor(scaleFactor));
      } else if (ClusterInfo.getClusterInfo(clusterName) == null) {
         throw new IllegalArgumentException(DTF.getNonexistentOrConfiguredCluster(clusterName));
      }
   }

   public static final class ScalingOperationCompletionMarker implements CommandInterface {
      private String clusterName;

      public ScalingOperationCompletionMarker(String clusterName) {
         this.clusterName = clusterName;
      }

      public void initialize(WorkflowContext context) {
      }

      public boolean execute() throws Exception {
         ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
         ElasticServiceManager esm = (ElasticServiceManager)serviceLocator.getService(ElasticServiceManager.class, new Annotation[0]);
         ClusterScalingStatus clusterScalingStatus = esm.getClusterScalingStatus(this.clusterName);
         clusterScalingStatus.setLastScalingEndTime(System.currentTimeMillis());
         return true;
      }
   }
}
