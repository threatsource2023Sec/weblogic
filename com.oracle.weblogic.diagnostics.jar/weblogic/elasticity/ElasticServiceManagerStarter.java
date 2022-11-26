package weblogic.elasticity;

import com.oracle.core.interceptor.impl.InterceptorWorkflowLifecycleManagerAdapter;
import javax.inject.Inject;
import javax.inject.Provider;
import org.glassfish.hk2.api.PostConstruct;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.workflow.WorkflowLifecycleManager;

@Service
@RunLevel(20)
public class ElasticServiceManagerStarter implements PostConstruct {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugElasticServices");
   @Inject
   private Provider runtimeAccessProvider;
   @Inject
   private Provider elasticServiceManagerProvider;
   @Inject
   private Provider workflowLifecycleManagerProvider;
   @Inject
   private Provider interceptorWorkflowLifecycleManagerAdapterProvider;
   private ElasticServiceManager elasticServiceManager;
   private WorkflowLifecycleManager workflowLifecycleManager;
   private InterceptorWorkflowLifecycleManagerAdapter interceptorWorkflowLifecycleManagerAdapter;

   @javax.annotation.PostConstruct
   public void postConstruct() {
      RuntimeAccess runtimeAccess = (RuntimeAccess)this.runtimeAccessProvider.get();
      if (runtimeAccess != null && runtimeAccess.isAdminServer()) {
         this.elasticServiceManager = (ElasticServiceManager)this.elasticServiceManagerProvider.get();
         this.interceptorWorkflowLifecycleManagerAdapter = (InterceptorWorkflowLifecycleManagerAdapter)this.interceptorWorkflowLifecycleManagerAdapterProvider.get();
         this.workflowLifecycleManager = (WorkflowLifecycleManager)this.workflowLifecycleManagerProvider.get();
         this.interceptorWorkflowLifecycleManagerAdapter.setWorkflowLifecycleManager(this.workflowLifecycleManager);
         DomainMBean domainMBean = runtimeAccess.getDomain();
         if (domainMBean != null && domainMBean.getInterceptors() != null) {
            this.interceptorWorkflowLifecycleManagerAdapter.setInterceptorsMBean(domainMBean.getInterceptors());
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("** Initialized WLSInterceptorAdapter with interceptors: " + domainMBean.getInterceptors());
            }
         }
      }

   }
}
