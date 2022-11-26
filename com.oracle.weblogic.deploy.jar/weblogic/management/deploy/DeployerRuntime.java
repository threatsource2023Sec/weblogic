package weblogic.management.deploy;

import java.io.IOException;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

/** @deprecated */
@Deprecated
public final class DeployerRuntime {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static DeployerRuntimeMBean getDeployerRuntime(String user, String pword, String adminURL) throws IllegalArgumentException, InstanceNotFoundException {
      try {
         MBeanServerConnection conn = getDomainMBSConnection(user, pword, adminURL);
         DomainRuntimeServiceMBean domainService = (DomainRuntimeServiceMBean)MBeanServerInvocationHandler.newProxyInstance(conn, new ObjectName(DomainRuntimeServiceMBean.OBJECT_NAME));
         DomainRuntimeMBean domainRuntime = domainService == null ? null : domainService.getDomainRuntime();
         if (domainRuntime != null) {
            return domainRuntime.getDeployerRuntime();
         }
      } catch (Exception var6) {
         throw new IllegalArgumentException(var6);
      }

      throw new InstanceNotFoundException("MBean not found: " + DomainRuntimeServiceMBean.OBJECT_NAME);
   }

   public static DeployerRuntimeMBean getDeployerRuntime() {
      return ManagementService.getDomainAccess(kernelId).getDeployerRuntime();
   }

   private static MBeanServerConnection getDomainMBSConnection(String username, String password, String serverUrl) throws IOException {
      JMXServiceURL serviceUrl = new JMXServiceURL("service:jmx:" + serverUrl + "/jndi/weblogic.management.mbeanservers.domainruntime");
      Map h = new HashMap();
      h.put("java.naming.security.principal", username);
      h.put("java.naming.security.credentials", password);
      h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
      JMXConnector connector = JMXConnectorFactory.connect(serviceUrl, h);
      return connector.getMBeanServerConnection();
   }
}
