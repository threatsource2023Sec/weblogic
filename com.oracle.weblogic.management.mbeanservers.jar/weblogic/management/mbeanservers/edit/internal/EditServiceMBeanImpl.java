package weblogic.management.mbeanservers.edit.internal;

import java.security.AccessController;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.jmx.modelmbean.WLSModelMBeanFactory;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.AppDeploymentConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.mbeanservers.edit.PortablePartitionManagerMBean;
import weblogic.management.mbeanservers.edit.RecordingManagerMBean;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.EditFailedException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class EditServiceMBeanImpl extends ServiceImpl implements EditServiceMBean, EditAccess.EventListener {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugJMXEdit");
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private EditAccess edit;
   private ConfigurationManagerMBean configurationManager;
   private PortablePartitionManagerMBean portablePartitionManager;
   private AppDeploymentConfigurationManagerMBean appDeploymentConfigurationManager;
   private RecordingManagerMBean recordingManager;
   private WLSModelMBeanContext context;
   private volatile boolean domainConfigurationWasReturned;

   EditServiceMBeanImpl(EditAccess edit, WLSModelMBeanContext context) {
      this("EditService", EditServiceMBean.class.getName(), edit, context);
   }

   EditServiceMBeanImpl(String name, String type, EditAccess edit, WLSModelMBeanContext context) {
      super(name, type, (Service)null);
      this.domainConfigurationWasReturned = false;
      this.edit = edit;
      edit.register(this);
      this.context = context;
      this.configurationManager = new ConfigurationManagerMBeanImpl(edit, context);
      this.recordingManager = new RecordingManagerMBeanImpl(edit);
      this.portablePartitionManager = new PortablePartitionManagerMBeanImpl(this.configurationManager);

      try {
         this.appDeploymentConfigurationManager = new AppDeploymentConfigurationManagerMBeanImpl(edit, this.configurationManager, context);
         WLSModelMBeanFactory.registerWLSModelMBean(this.appDeploymentConfigurationManager, new ObjectName(AppDeploymentConfigurationManagerMBean.OBJECT_NAME), context);
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public DomainMBean getDomainConfiguration() {
      try {
         DomainMBean bean = this.edit.getDomainBeanWithoutLock();
         this.domainConfigurationWasReturned = true;
         return bean;
      } catch (EditFailedException var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Exception getting domain configuration ", var2);
         }

         throw new RuntimeException(var2);
      }
   }

   public ConfigurationManagerMBean getConfigurationManager() {
      return this.configurationManager;
   }

   public AppDeploymentConfigurationManagerMBean getAppDeploymentConfigurationManager() {
      return this.appDeploymentConfigurationManager;
   }

   public PortablePartitionManagerMBean getPortablePartitionManager() {
      return this.portablePartitionManager;
   }

   public RecordingManagerMBean getRecordingManager() {
      return this.recordingManager;
   }

   protected EditAccess getEditAccess() {
      return this.edit;
   }

   public void onDestroy(EditAccess editAccess, DomainMBean editDomainMBean) {
      this.context.setRecurse(true);
      if (editDomainMBean != null && this.domainConfigurationWasReturned) {
         WLSModelMBeanFactory.unregisterWLSModelMBean(editDomainMBean, this.context);
      }

      WLSModelMBeanFactory.unregisterWLSModelMBean(this.appDeploymentConfigurationManager, this.context);
   }
}
