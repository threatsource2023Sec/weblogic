package weblogic.management.provider.internal;

import javax.management.InvalidAttributeValueException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.ConfigurationMBean;
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

@Contract
public interface ComponentPartitionProcessor {
   boolean isTargetableVirtually(ConfigurationMBean var1);

   void processAppDeployment(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, AppDeploymentMBean var4, AppDeploymentMBean var5) throws InvalidAttributeValueException, ManagementException;

   void processLibrary(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, LibraryMBean var4, LibraryMBean var5) throws InvalidAttributeValueException, ManagementException;

   void processJMSServer(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, JMSServerMBean var4, JMSServerMBean var5) throws InvalidAttributeValueException, ManagementException;

   void processMessagingBridge(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, MessagingBridgeMBean var4, MessagingBridgeMBean var5) throws InvalidAttributeValueException, ManagementException;

   void processPathService(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, PathServiceMBean var4, PathServiceMBean var5) throws InvalidAttributeValueException, ManagementException;

   void processJMSBridgeDestination(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, JMSBridgeDestinationMBean var4, JMSBridgeDestinationMBean var5) throws InvalidAttributeValueException, ManagementException;

   void processMailSession(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, MailSessionMBean var4, MailSessionMBean var5) throws InvalidAttributeValueException, ManagementException;

   void processFileStore(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, FileStoreMBean var4, FileStoreMBean var5) throws InvalidAttributeValueException, ManagementException;

   void processJDBCStore(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, JDBCStoreMBean var4, JDBCStoreMBean var5) throws InvalidAttributeValueException, ManagementException;

   void processJMSSystemResource(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, JMSSystemResourceMBean var4, JMSSystemResourceMBean var5) throws InvalidAttributeValueException, ManagementException;

   void processJDBCSystemResource(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, JDBCSystemResourceMBean var4, JDBCSystemResourceMBean var5) throws InvalidAttributeValueException, ManagementException;

   void processCoherenceClusterSystemResource(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, CoherenceClusterSystemResourceMBean var4, CoherenceClusterSystemResourceMBean var5) throws InvalidAttributeValueException, ManagementException;

   void processWLDFSystemResource(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, WLDFSystemResourceMBean var4, WLDFSystemResourceMBean var5) throws InvalidAttributeValueException, ManagementException;

   void processSAFAgent(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, SAFAgentMBean var4, SAFAgentMBean var5) throws InvalidAttributeValueException, ManagementException;

   void processOsgiFramework(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, OsgiFrameworkMBean var4, OsgiFrameworkMBean var5) throws InvalidAttributeValueException, ManagementException;

   void processForeignJNDIProvider(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, ForeignJNDIProviderMBean var4, ForeignJNDIProviderMBean var5) throws InvalidAttributeValueException, ManagementException;
}
