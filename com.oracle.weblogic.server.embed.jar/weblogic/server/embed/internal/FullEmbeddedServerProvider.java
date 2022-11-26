package weblogic.server.embed.internal;

import java.io.IOException;
import java.security.AccessController;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.naming.NamingException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.internal.CIEDomainGenerator;
import weblogic.management.internal.DomainGenerator;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.mbeanservers.edit.ActivationTaskMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceClient;
import weblogic.management.runtime.DeploymentManagerMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class FullEmbeddedServerProvider extends EmbeddedServerProvider {
   private static ConfigurationManagerMBean configMgr = null;

   private ConfigurationManagerMBean getConfigMgr(String u, String p) throws MalformedObjectNameException, NamingException, IOException {
      if (configMgr == null) {
         MBeanServerConnection con = getMBeanServerConnection("edit", u, p);
         EditServiceMBean edit = (EditServiceMBean)MBeanServerInvocationHandler.newProxyInstance(con, new ObjectName(EditServiceMBean.OBJECT_NAME), EditServiceMBean.class, false);
         configMgr = edit.getConfigurationManager();
      }

      return configMgr;
   }

   public DomainMBean beginEdit(String u, String p) throws Exception {
      return this.getConfigMgr(u, p).startEdit(-1, -1);
   }

   public void cancelEdit() {
      if (configMgr == null) {
         throw new AssertionError("Config Manager not initialized");
      } else {
         configMgr.cancelEdit();
      }
   }

   public void saveEdit() throws Exception {
      if (configMgr == null) {
         throw new AssertionError("Config Manager not initialized");
      } else {
         ActivationTaskMBean a = null;

         try {
            a = configMgr.activate(-1L);
            a.waitForTaskCompletion();
         } catch (Exception var3) {
            configMgr.cancelEdit();
            throw var3;
         }

         Exception gotErr = a.getError();
         if (gotErr != null) {
            throw gotErr;
         }
      }
   }

   public DeploymentManagerMBean getDeploymentManager() {
      return ManagementService.getDomainAccess(this.getKernelId()).getDeploymentManager();
   }

   protected DomainGenerator lookupDomainGenerator() throws ManagementException {
      CIEDomainGenerator domGen = new CIEDomainGenerator();
      domGen.validateConfigFramework();
      return domGen;
   }

   public void initializeManagementServiceClientBeanInfoAccess() {
      ManagementServiceClient.getBeanInfoAccess();
   }

   private AuthenticatedSubject getKernelId() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
