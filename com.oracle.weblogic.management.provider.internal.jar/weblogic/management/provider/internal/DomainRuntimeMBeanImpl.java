package weblogic.management.provider.internal;

import java.security.AccessController;
import java.util.Date;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.ManagementException;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;
import weblogic.management.runtime.BatchJobRepositoryRuntimeMBean;
import weblogic.management.runtime.CoherenceServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ConsoleRuntimeMBean;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DeploymentManagerMBean;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.EditSessionConfigurationManagerMBean;
import weblogic.management.runtime.ElasticServiceManagerRuntimeMBean;
import weblogic.management.runtime.LogRuntimeMBean;
import weblogic.management.runtime.MessageDrivenControlEJBRuntimeMBean;
import weblogic.management.runtime.MigratableServiceCoordinatorRuntimeMBean;
import weblogic.management.runtime.MigrationDataRuntimeMBean;
import weblogic.management.runtime.NodeManagerRuntimeMBean;
import weblogic.management.runtime.PartitionLifeCycleRuntimeMBean;
import weblogic.management.runtime.PartitionLifeCycleTaskRuntimeMBean;
import weblogic.management.runtime.ResourceGroupLifeCycleRuntimeMBean;
import weblogic.management.runtime.RolloutServiceRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.SNMPAgentRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServiceMigrationDataRuntimeMBean;
import weblogic.management.runtime.SystemComponentLifeCycleRuntimeMBean;
import weblogic.management.runtime.WseePolicySubjectManagerRuntimeMBean;
import weblogic.management.utils.AdminServerDeploymentManagerService;
import weblogic.management.utils.AdminServerDeploymentManagerServiceGenerator;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.LocatorUtilities;

public final class DomainRuntimeMBeanImpl extends DomainRuntimeMBeanDelegate implements DomainRuntimeMBean {
   private DomainAccess domainAccess;
   private LogRuntimeMBean logRuntime;
   private SNMPAgentRuntimeMBean snmpRuntime;
   private ConsoleRuntimeMBean consoleRuntime;
   private WseePolicySubjectManagerRuntimeMBean policyRuntime;
   private ElasticServiceManagerRuntimeMBean elasticServiceManagerRuntimeMBean;
   private RolloutServiceRuntimeMBean rolloutServiceRuntimeMBean;
   private BatchJobRepositoryRuntimeMBean batchJobRepositoryRuntimeMBean;
   private EditSessionConfigurationManagerMBean editSessionConfigurationManagerMBean;
   private NodeManagerRuntimeMBean[] nodemanagerRuntimeMBeans = null;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private MessageDrivenControlEJBRuntimeMBean messageDrivenControlEJBRuntime;

   DomainRuntimeMBeanImpl() throws ManagementException {
      super(ManagementService.getRuntimeAccess(kernelId).getDomainName(), (RuntimeMBean)null);
      this.domainAccess = ManagementService.getDomainAccess(kernelId);
   }

   public Date getActivationTime() {
      return new Date(this.domainAccess.getActivationTime());
   }

   public DeployerRuntimeMBean getDeployerRuntime() {
      return this.domainAccess.getDeployerRuntime();
   }

   public DeploymentManagerMBean getDeploymentManager() {
      return this.domainAccess.getDeploymentManager();
   }

   public ServerLifeCycleRuntimeMBean lookupServerLifeCycleRuntime(String name) {
      return this.domainAccess.lookupServerLifecycleRuntime(name);
   }

   public ServerLifeCycleRuntimeMBean[] getServerLifeCycleRuntimes() {
      return this.domainAccess.getServerLifecycleRuntimes();
   }

   /** @deprecated */
   @Deprecated
   public CoherenceServerLifeCycleRuntimeMBean lookupCoherenceServerLifeCycleRuntime(String name) {
      return this.domainAccess.lookupCoherenceServerLifecycleRuntime(name);
   }

   /** @deprecated */
   @Deprecated
   public CoherenceServerLifeCycleRuntimeMBean[] getCoherenceServerLifeCycleRuntimes() {
      return this.domainAccess.getCoherenceServerLifecycleRuntimes();
   }

   public SystemComponentLifeCycleRuntimeMBean lookupSystemComponentLifeCycleRuntime(String name) {
      return this.domainAccess.lookupSystemComponentLifecycleRuntime(name);
   }

   public SystemComponentLifeCycleRuntimeMBean[] getSystemComponentLifeCycleRuntimes() {
      return this.domainAccess.getSystemComponentLifecycleRuntimes();
   }

   public LogRuntimeMBean getLogRuntime() {
      return this.logRuntime;
   }

   public void setLogRuntime(LogRuntimeMBean bean) {
      this.logRuntime = bean;
   }

   public MigratableServiceCoordinatorRuntimeMBean getMigratableServiceCoordinatorRuntime() {
      return this.domainAccess.getMigratableServiceCoordinatorRuntime();
   }

   public MigrationDataRuntimeMBean[] getMigrationDataRuntimes() {
      return this.domainAccess.getMigrationDataRuntimes();
   }

   public AppRuntimeStateRuntimeMBean getAppRuntimeStateRuntime() {
      return this.domainAccess.getAppRuntimeStateRuntime();
   }

   public MessageDrivenControlEJBRuntimeMBean getMessageDrivenControlEJBRuntime() {
      return this.messageDrivenControlEJBRuntime;
   }

   public void setMessageDrivenControlEJBRuntime(MessageDrivenControlEJBRuntimeMBean b) {
      this.messageDrivenControlEJBRuntime = b;
   }

   public SNMPAgentRuntimeMBean getSNMPAgentRuntime() {
      return this.snmpRuntime;
   }

   public void setSNMPAgentRuntime(SNMPAgentRuntimeMBean bean) {
      this.snmpRuntime = bean;
   }

   public ConsoleRuntimeMBean getConsoleRuntime() {
      return this.consoleRuntime;
   }

   public void setConsoleRuntime(ConsoleRuntimeMBean bean) {
      this.consoleRuntime = bean;
   }

   public WseePolicySubjectManagerRuntimeMBean getPolicySubjectManagerRuntime() {
      return this.policyRuntime;
   }

   public void setPolicySubjectManagerRuntime(WseePolicySubjectManagerRuntimeMBean bean) {
      this.policyRuntime = bean;
   }

   public void restartSystemResource(SystemResourceMBean s) throws ManagementException {
      AdminServerDeploymentManagerServiceGenerator generator = (AdminServerDeploymentManagerServiceGenerator)LocatorUtilities.getService(AdminServerDeploymentManagerServiceGenerator.class);
      AdminServerDeploymentManagerService deploymentManager = generator.createAdminServerDeploymentManager(kernelId);
      deploymentManager.restartSystemResource(s);
   }

   public ServiceMigrationDataRuntimeMBean[] getServiceMigrationDataRuntimes() {
      return this.domainAccess.getServiceMigrationDataRuntimes();
   }

   public ResourceGroupLifeCycleRuntimeMBean lookupResourceGroupLifeCycleRuntime(String name) {
      return this.domainAccess.lookupResourceGroupLifeCycleRuntime(name);
   }

   public ResourceGroupLifeCycleRuntimeMBean[] getResourceGroupLifeCycleRuntimes() {
      return this.domainAccess.getResourceGroupLifeCycleRuntimes();
   }

   public ElasticServiceManagerRuntimeMBean getElasticServiceManagerRuntime() {
      return this.elasticServiceManagerRuntimeMBean;
   }

   public void setElasticServiceManagerRuntime(ElasticServiceManagerRuntimeMBean elasticServiceManagerRuntimeMBean) {
      this.elasticServiceManagerRuntimeMBean = elasticServiceManagerRuntimeMBean;
   }

   public RolloutServiceRuntimeMBean getRolloutService() {
      return this.rolloutServiceRuntimeMBean;
   }

   public void setRolloutService(RolloutServiceRuntimeMBean bean) {
      this.rolloutServiceRuntimeMBean = bean;
   }

   public BatchJobRepositoryRuntimeMBean getBatchJobRepositoryRuntime() {
      return ManagementService.getRuntimeAccess(kernelId) != null && ManagementService.getRuntimeAccess(kernelId).getServerRuntime() != null ? ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getBatchJobRepositoryRuntime() : null;
   }

   public void setBatchJobRepositoryRuntime(BatchJobRepositoryRuntimeMBean bean) {
      this.batchJobRepositoryRuntimeMBean = bean;
   }

   public void setEditSessionConfigurationManager(EditSessionConfigurationManagerMBean bean) {
      this.editSessionConfigurationManagerMBean = bean;
   }

   public EditSessionConfigurationManagerMBean getEditSessionConfigurationManager() {
      return this.editSessionConfigurationManagerMBean;
   }

   public NodeManagerRuntimeMBean[] getNodeManagerRuntimes() {
      return this.domainAccess.getNodeManagerRuntimes();
   }

   public PartitionLifeCycleTaskRuntimeMBean startPartitionWait(PartitionMBean partitionMBean, String initialState, int timeOut) throws ManagementException, PartitionLifeCycleException, InterruptedException, IllegalArgumentException {
      if (partitionMBean == null) {
         throw new IllegalArgumentException(String.format("PartitionMBean must not be null"));
      } else {
         DomainPartitionRuntimeMBean domainPartitionRuntimeMBean = this.domainAccess.lookupDomainPartitionRuntime(partitionMBean.getName());
         if (domainPartitionRuntimeMBean == null) {
            throw new ManagementException(String.format("could not find the specified partition %s. The problem might be that the partition was created \nin a deferred edit session that also contained a non-dynamic change that requires a server restart.", partitionMBean.getName()));
         } else {
            PartitionLifeCycleRuntimeMBean pLCRMBean = domainPartitionRuntimeMBean.getPartitionLifeCycleRuntime();
            if (pLCRMBean == null) {
               throw new ManagementException(String.format("could not find the PartitionLifeCycleRuntime for partition %s", partitionMBean.getName()));
            } else {
               PartitionLifeCycleTaskRuntimeMBean retTask = pLCRMBean.start(initialState, timeOut);
               long sleepInterval = 1000L;

               while(retTask.isRunning()) {
                  Thread.sleep(sleepInterval);
               }

               return retTask;
            }
         }
      }
   }

   public PartitionLifeCycleTaskRuntimeMBean forceShutdownPartitionWait(PartitionMBean partitionMBean, int timeout) throws ManagementException, PartitionLifeCycleException, InterruptedException, IllegalArgumentException {
      if (partitionMBean == null) {
         throw new IllegalArgumentException(String.format("PartitionMBean must not be null"));
      } else {
         DomainPartitionRuntimeMBean domainPartitionRuntimeMBean = this.domainAccess.lookupDomainPartitionRuntime(partitionMBean.getName());
         if (domainPartitionRuntimeMBean == null) {
            throw new ManagementException(String.format("could not find the specified partition %s. The problem might be that the partition was created \nin a deferred edit session that also contained a non-dynamic change that requires a server restart.", partitionMBean.getName()));
         } else {
            PartitionLifeCycleRuntimeMBean pLCRMBean = domainPartitionRuntimeMBean.getPartitionLifeCycleRuntime();
            if (pLCRMBean == null) {
               throw new ManagementException(String.format("could not find the PartitionLifeCycleRuntime for partition %s", partitionMBean.getName()));
            } else {
               PartitionLifeCycleTaskRuntimeMBean retTask = pLCRMBean.forceShutdown(timeout);
               long sleepInterval = 1000L;

               while(retTask.isRunning()) {
                  Thread.sleep(sleepInterval);
               }

               return retTask;
            }
         }
      }
   }

   public DomainPartitionRuntimeMBean[] getDomainPartitionRuntimes() {
      return this.domainAccess.getDomainPartitionRuntimes();
   }

   public DomainPartitionRuntimeMBean lookupDomainPartitionRuntime(String name) {
      return this.domainAccess.lookupDomainPartitionRuntime(name);
   }

   public DomainPartitionRuntimeMBean getCurrentDomainPartitionRuntime() {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(kernelId).getCurrentComponentInvocationContext();
      if (!cic.isGlobalRuntime()) {
         DomainPartitionRuntimeMBean dprm = this.lookupDomainPartitionRuntime(cic.getPartitionName());
         if (dprm != null) {
            return dprm;
         }
      }

      return null;
   }
}
