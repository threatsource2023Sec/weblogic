package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.ManagementException;

public interface DiscreteResourceProcessor {
   void start();

   void end();

   void processAppDeployment(AppDeploymentMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processLibrary(LibraryMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processJMSServer(JMSServerMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processMessagingBridge(MessagingBridgeMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processPathService(PathServiceMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processMailSession(MailSessionMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processFileStore(FileStoreMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processJDBCStore(JDBCStoreMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processSAFAgent(SAFAgentMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processJMSBridgeDestination(JMSBridgeDestinationMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processJMSSystemResource(JMSSystemResourceMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processJDBCSystemResource(JDBCSystemResourceMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processOsgiFramework(OsgiFrameworkMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processWLDFSystemResource(WLDFSystemResourceMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processManagedExecutorService(ManagedExecutorServiceMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processManagedScheduledExecutorService(ManagedScheduledExecutorServiceMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processManagedThreadFactory(ManagedThreadFactoryMBean var1) throws InvalidAttributeValueException, ManagementException;

   void processForeignJNDIProvider(ForeignJNDIProviderMBean var1) throws InvalidAttributeValueException, ManagementException;
}
