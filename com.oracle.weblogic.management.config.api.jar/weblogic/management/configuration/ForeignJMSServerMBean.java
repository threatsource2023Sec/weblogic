package weblogic.management.configuration;

import java.util.Properties;
import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.ForeignServerBean;

/** @deprecated */
@Deprecated
public interface ForeignJMSServerMBean extends DeploymentMBean {
   String WLS_CONTEXT_FACTORY = "weblogic.jndi.WLInitialContextFactory";

   /** @deprecated */
   @Deprecated
   ForeignJMSDestinationMBean[] getDestinations();

   /** @deprecated */
   @Deprecated
   void setDestinations(ForeignJMSDestinationMBean[] var1);

   /** @deprecated */
   @Deprecated
   boolean addDestination(ForeignJMSDestinationMBean var1);

   /** @deprecated */
   @Deprecated
   boolean removeDestination(ForeignJMSDestinationMBean var1);

   ForeignJMSConnectionFactoryMBean[] getConnectionFactories();

   void setConnectionFactories(ForeignJMSConnectionFactoryMBean[] var1);

   /** @deprecated */
   @Deprecated
   boolean addConnectionFactory(ForeignJMSConnectionFactoryMBean var1);

   /** @deprecated */
   @Deprecated
   boolean removeConnectionFactory(ForeignJMSConnectionFactoryMBean var1);

   void setInitialContextFactory(String var1) throws InvalidAttributeValueException;

   String getInitialContextFactory();

   void setConnectionURL(String var1) throws InvalidAttributeValueException;

   String getConnectionURL();

   void setJNDIProperties(Properties var1) throws InvalidAttributeValueException;

   Properties getJNDIProperties();

   byte[] getJNDIPropertiesCredentialEncrypted();

   void setJNDIPropertiesCredentialEncrypted(byte[] var1) throws InvalidAttributeValueException;

   String getJNDIPropertiesCredential();

   void setJNDIPropertiesCredential(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   ForeignJMSConnectionFactoryMBean[] getForeignJMSConnectionFactories();

   ForeignJMSConnectionFactoryMBean createForeignJMSConnectionFactory(String var1);

   ForeignJMSConnectionFactoryMBean lookupForeignJMSConnectionFactory(String var1);

   void destroyForeignJMSConnectionFactory(ForeignJMSConnectionFactoryMBean var1);

   ForeignJMSConnectionFactoryMBean createForeignJMSConnectionFactory(String var1, ForeignJMSConnectionFactoryMBean var2);

   /** @deprecated */
   @Deprecated
   ForeignJMSDestinationMBean[] getForeignJMSDestinations();

   ForeignJMSDestinationMBean createForeignJMSDestination(String var1);

   ForeignJMSDestinationMBean lookupForeignJMSDestination(String var1);

   ForeignJMSDestinationMBean createForeignJMSDestination(String var1, ForeignJMSDestinationMBean var2);

   void destroyForeignJMSDestination(ForeignJMSDestinationMBean var1);

   void useDelegates(ForeignServerBean var1, SubDeploymentMBean var2);
}
