package weblogic.j2ee.descriptor.wl;

public interface ForeignServerBean extends TargetableBean {
   ForeignDestinationBean[] getForeignDestinations();

   ForeignDestinationBean createForeignDestination(String var1);

   void destroyForeignDestination(ForeignDestinationBean var1);

   ForeignDestinationBean lookupForeignDestination(String var1);

   ForeignConnectionFactoryBean[] getForeignConnectionFactories();

   ForeignConnectionFactoryBean createForeignConnectionFactory(String var1);

   void destroyForeignConnectionFactory(ForeignConnectionFactoryBean var1);

   ForeignConnectionFactoryBean lookupForeignConnectionFactory(String var1);

   String getInitialContextFactory();

   void setInitialContextFactory(String var1) throws IllegalArgumentException;

   String getConnectionURL();

   void setConnectionURL(String var1) throws IllegalArgumentException;

   byte[] getJNDIPropertiesCredentialEncrypted();

   void setJNDIPropertiesCredentialEncrypted(byte[] var1);

   String getJNDIPropertiesCredential();

   void setJNDIPropertiesCredential(String var1);

   PropertyBean[] getJNDIProperties();

   PropertyBean createJNDIProperty(String var1);

   void destroyJNDIProperty(PropertyBean var1);

   PropertyBean lookupJNDIProperty(String var1);
}
