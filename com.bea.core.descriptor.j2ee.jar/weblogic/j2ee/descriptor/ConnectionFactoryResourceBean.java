package weblogic.j2ee.descriptor;

public interface ConnectionFactoryResourceBean extends PropertyBean {
   String getDescription();

   void setDescription(String var1);

   String getName();

   void setName(String var1);

   String getInterfaceName();

   void setInterfaceName(String var1);

   String getResourceAdapter();

   void setResourceAdapter(String var1);

   int getMaxPoolSize();

   void setMaxPoolSize(int var1);

   int getMinPoolSize();

   void setMinPoolSize(int var1);

   String getTransactionSupport();

   void setTransactionSupport(String var1);

   JavaEEPropertyBean[] getProperties();

   JavaEEPropertyBean lookupProperty(String var1);

   JavaEEPropertyBean createProperty();

   void destroyProperty(JavaEEPropertyBean var1);

   String getId();

   void setId(String var1);
}
