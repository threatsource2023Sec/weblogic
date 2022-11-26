package weblogic.elasticity;

import com.oracle.core.interceptor.MethodInvocationContext;
import javax.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.Rank;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.workflow.FailurePlan;

@Service(
   name = "MetadataPopulatorCommandCreator"
)
@Rank(1073741823)
@ContractsProvided({MethodInterceptor.class, MetadataPopulatorCommandCreator.class})
public class MetadataPopulatorCommandCreator implements MethodInterceptor {
   @Inject
   MethodInvocationContext invCtx;

   public Object invoke(MethodInvocation methodInvocation) throws Throwable {
      String operationName = methodInvocation.getMethod().getName();
      if (operationName.equals("scaleDown")) {
         Object[] args = methodInvocation.getArguments();
         String clusterName = (String)args[0];
         int scaleFactor = Integer.valueOf((Integer)args[1]);
         this.invCtx.getWorkflowBuilder().add(new MetadataPopulatorCommand(clusterName, operationName, scaleFactor), new FailurePlan(false, false, false, 0, 0L));
      }

      Object result = methodInvocation.proceed();
      if (operationName.equals("scaleUp")) {
         Object[] args = methodInvocation.getArguments();
         String clusterName = (String)args[0];
         int scaleFactor = Integer.valueOf((Integer)args[1]);
         this.invCtx.getWorkflowBuilder().add(new MetadataPopulatorCommand(clusterName, operationName, scaleFactor), new FailurePlan(false, false, false, 0, 0L));
      }

      return result;
   }
}
