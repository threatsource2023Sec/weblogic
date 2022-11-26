package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface ForeignServerOverrideMBean extends ConfigurationMBean {
   ForeignDestinationOverrideMBean[] getForeignDestinations();

   ForeignDestinationOverrideMBean createForeignDestination(String var1);

   void destroyForeignDestination(ForeignDestinationOverrideMBean var1);

   ForeignDestinationOverrideMBean lookupForeignDestination(String var1);

   ForeignConnectionFactoryOverrideMBean[] getForeignConnectionFactories();

   ForeignConnectionFactoryOverrideMBean createForeignConnectionFactory(String var1);

   void destroyForeignConnectionFactory(ForeignConnectionFactoryOverrideMBean var1);

   ForeignConnectionFactoryOverrideMBean lookupForeignConnectionFactory(String var1);

   String getInitialContextFactory();

   void setInitialContextFactory(String var1) throws InvalidAttributeValueException;

   String getConnectionURL();

   void setConnectionURL(String var1) throws InvalidAttributeValueException;

   byte[] getJNDIPropertiesCredentialEncrypted();

   void setJNDIPropertiesCredentialEncrypted(byte[] var1);

   String getJNDIPropertiesCredential();

   void setJNDIPropertiesCredential(String var1);

   PartitionPropertyMBean[] getJNDIProperties();

   PartitionPropertyMBean createJNDIProperty(String var1);

   void destroyJNDIProperty(PartitionPropertyMBean var1);

   PartitionPropertyMBean lookupJNDIProperty(String var1);
}
