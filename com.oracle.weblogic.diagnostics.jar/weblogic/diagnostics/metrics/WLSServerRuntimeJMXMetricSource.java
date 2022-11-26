package weblogic.diagnostics.metrics;

import com.oracle.weblogic.diagnostics.expressions.AdminServer;
import com.oracle.weblogic.diagnostics.expressions.ExpressionBean;
import com.oracle.weblogic.diagnostics.expressions.ManagedServer;
import com.oracle.weblogic.diagnostics.expressions.WLDFI18n;
import com.oracle.weblogic.diagnostics.watch.MetricRuleType;
import com.oracle.weblogic.diagnostics.watch.beans.jmx.AbstractJMXMetricSource;
import java.security.AccessController;
import javax.inject.Singleton;
import javax.management.MBeanServerConnection;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@ExpressionBean(
   name = "runtime",
   prefix = "wls"
)
@Service
@Singleton
@WLDFI18n(
   resourceBundle = "weblogic.diagnostics.metrics.MetricsSourcesBundle",
   value = "server.runtime.desc",
   displayName = "server.runtime.displayname"
)
@ManagedServer
@AdminServer
@MetricRuleType
public class WLSServerRuntimeJMXMetricSource extends AbstractJMXMetricSource {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionMetrics");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private RuntimeAccess runtimeAccess;

   public WLSServerRuntimeJMXMetricSource() {
      this.runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
   }

   @WLDFI18n("server.runtime.namedesc")
   public String getName() {
      return "serverRuntime";
   }

   @WLDFI18n("server.runtime.serverruntime.desc")
   public ServerRuntimeMBean getServerRuntime() {
      return (ServerRuntimeMBean)BeantreeInterceptor.createProxy(this.runtimeAccess.getServerRuntime(), ServerRuntimeMBean.class);
   }

   @WLDFI18n("server.runtime.domain.desc")
   public DomainMBean getDomain() {
      return (DomainMBean)BeantreeInterceptor.createProxy(this.runtimeAccess.getDomain(), DomainMBean.class);
   }

   protected MBeanServerConnection lookupMBeanServerReference() {
      return ManagementService.getRuntimeMBeanServer(KERNEL_ID);
   }
}
