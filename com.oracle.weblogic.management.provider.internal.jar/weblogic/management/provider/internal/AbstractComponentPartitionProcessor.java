package weblogic.management.provider.internal;

import javax.management.InvalidAttributeValueException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.FileStoreMBean;
import weblogic.management.configuration.ForeignJNDIProviderMBean;
import weblogic.management.configuration.JDBCStoreMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.JMSBridgeDestinationMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.MailSessionMBean;
import weblogic.management.configuration.MessagingBridgeMBean;
import weblogic.management.configuration.OsgiFrameworkMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PathServiceMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.WLDFSystemResourceMBean;

public abstract class AbstractComponentPartitionProcessor implements ComponentPartitionProcessor {
   private PartitionProcessor partitionProcessor;

   public void processAppDeployment(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, AppDeploymentMBean bean, AppDeploymentMBean clone) throws InvalidAttributeValueException, ManagementException {
   }

   public void processLibrary(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, LibraryMBean bean, LibraryMBean clone) throws InvalidAttributeValueException, ManagementException {
   }

   public void processJMSServer(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, JMSServerMBean bean, JMSServerMBean clone) throws InvalidAttributeValueException, ManagementException {
   }

   public void processMessagingBridge(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, MessagingBridgeMBean bean, MessagingBridgeMBean clone) throws InvalidAttributeValueException, ManagementException {
   }

   public void processPathService(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, PathServiceMBean bean, PathServiceMBean clone) throws InvalidAttributeValueException, ManagementException {
   }

   public void processJMSBridgeDestination(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, JMSBridgeDestinationMBean bean, JMSBridgeDestinationMBean clone) throws InvalidAttributeValueException, ManagementException {
   }

   public void processMailSession(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, MailSessionMBean bean, MailSessionMBean clone) throws InvalidAttributeValueException, ManagementException {
   }

   public void processFileStore(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, FileStoreMBean bean, FileStoreMBean clone) throws InvalidAttributeValueException, ManagementException {
   }

   public void processJDBCStore(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, JDBCStoreMBean bean, JDBCStoreMBean clone) throws InvalidAttributeValueException, ManagementException {
   }

   public void processJMSSystemResource(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, JMSSystemResourceMBean bean, JMSSystemResourceMBean clone) throws InvalidAttributeValueException, ManagementException {
   }

   public void processJDBCSystemResource(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, JDBCSystemResourceMBean bean, JDBCSystemResourceMBean clone) throws InvalidAttributeValueException, ManagementException {
   }

   public void processCoherenceClusterSystemResource(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, CoherenceClusterSystemResourceMBean bean, CoherenceClusterSystemResourceMBean clone) throws InvalidAttributeValueException, ManagementException {
   }

   public void processWLDFSystemResource(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, WLDFSystemResourceMBean bean, WLDFSystemResourceMBean clone) throws InvalidAttributeValueException, ManagementException {
   }

   public void processSAFAgent(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, SAFAgentMBean bean, SAFAgentMBean clone) throws InvalidAttributeValueException, ManagementException {
   }

   public void processOsgiFramework(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, OsgiFrameworkMBean bean, OsgiFrameworkMBean clone) throws InvalidAttributeValueException, ManagementException {
   }

   public void processForeignJNDIProvider(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, ForeignJNDIProviderMBean bean, ForeignJNDIProviderMBean clone) throws InvalidAttributeValueException, ManagementException {
   }
}
