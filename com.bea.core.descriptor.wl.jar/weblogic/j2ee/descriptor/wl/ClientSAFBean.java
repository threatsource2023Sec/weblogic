package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface ClientSAFBean extends SettableBean {
   DefaultPersistentStoreBean getPersistentStore();

   DefaultSAFAgentBean getSAFAgent();

   JMSConnectionFactoryBean[] getConnectionFactories();

   JMSConnectionFactoryBean createConnectionFactory(String var1);

   void destroyConnectionFactory(JMSConnectionFactoryBean var1);

   JMSConnectionFactoryBean lookupConnectionFactory(String var1);

   SAFImportedDestinationsBean[] getSAFImportedDestinations();

   SAFImportedDestinationsBean createSAFImportedDestinations(String var1);

   void destroySAFImportedDestinations(SAFImportedDestinationsBean var1);

   SAFImportedDestinationsBean lookupSAFImportedDestinations(String var1);

   SAFRemoteContextBean[] getSAFRemoteContexts();

   SAFRemoteContextBean createSAFRemoteContext(String var1);

   void destroySAFRemoteContext(SAFRemoteContextBean var1);

   SAFRemoteContextBean lookupSAFRemoteContext(String var1);

   SAFErrorHandlingBean[] getSAFErrorHandlings();

   SAFErrorHandlingBean createSAFErrorHandling(String var1);

   void destroySAFErrorHandling(SAFErrorHandlingBean var1);

   SAFErrorHandlingBean lookupSAFErrorHandling(String var1);
}
