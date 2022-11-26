package weblogic.j2ee.descriptor;

public interface JmsConnectionFactoryBean extends PropertyBean {
   String getDescription();

   void setDescription(String var1);

   String getName();

   void setName(String var1);

   String getInterfaceName();

   void setInterfaceName(String var1);

   String getClassName();

   void setClassName(String var1);

   String getResourceAdapter();

   void setResourceAdapter(String var1);

   String getUser();

   void setUser(String var1);

   String getPassword();

   void setPassword(String var1);

   String getClientId();

   void setClientId(String var1);

   JavaEEPropertyBean[] getProperties();

   JavaEEPropertyBean lookupProperty(String var1);

   JavaEEPropertyBean createProperty();

   void destroyProperty(JavaEEPropertyBean var1);

   boolean isTransactional();

   void setTransactional(boolean var1);

   int getMaxPoolSize();

   void setMaxPoolSize(int var1);

   int getMinPoolSize();

   void setMinPoolSize(int var1);

   String getId();

   void setId(String var1);
}
