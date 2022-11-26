package weblogic.elasticity.interceptor;

import com.oracle.core.interceptor.MethodInvocationContext;
import javax.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.elasticity.ScalingOperation;
import weblogic.management.workflow.WorkflowBuilder;

@Service(
   name = "DatasourceInterceptor"
)
@Interceptor
@ScalingOperation
@ContractsProvided({MethodInterceptor.class, DataSourceInterceptor.class})
@Rank(1024)
public class DataSourceInterceptor implements MethodInterceptor {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDataSourceInterceptor");
   @Inject
   MethodInvocationContext invCtx;

   public Object invoke(MethodInvocation methodInvocation) throws Throwable {
      boolean debugEnabled = DEBUG.isDebugEnabled();
      String operationName = methodInvocation.getMethod().getName();
      if (debugEnabled) {
         DEBUG.debug("**DataSourceInterceptor[" + methodInvocation.getMethod().getName() + "]: " + this.invCtx);
      }

      if (operationName.equals("scaleUp")) {
         Object[] args = methodInvocation.getArguments();
         String clusterName = (String)args[0];
         int scaleFactor = Integer.valueOf((Integer)args[1]);
         if (debugEnabled) {
            DEBUG.debug("DataSourceInterceptor: clusterName=" + clusterName + ", scaleFactor=" + scaleFactor);
         }

         if (scaleFactor > 0) {
            DatasourceConstraintValidator validator = new DatasourceConstraintValidator(clusterName, scaleFactor);
            if (debugEnabled) {
               DEBUG.debug("**DataSourceInterceptor[invoke]: " + this.invCtx);
            }

            WorkflowBuilder builder = this.invCtx.getWorkflowBuilder();
            if (debugEnabled) {
               DEBUG.debug("**DataSourceInterceptor[invoke]: builder = " + builder);
            }

            builder.add(validator);
         }
      }

      return methodInvocation.proceed();
   }
}
