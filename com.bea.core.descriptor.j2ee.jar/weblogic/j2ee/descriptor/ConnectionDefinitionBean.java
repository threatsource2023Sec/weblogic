package weblogic.j2ee.descriptor;

public interface ConnectionDefinitionBean {
   String getManagedConnectionFactoryClass();

   void setManagedConnectionFactoryClass(String var1);

   ConfigPropertyBean[] getConfigProperties();

   ConfigPropertyBean createConfigProperty();

   void destroyConfigProperty(ConfigPropertyBean var1);

   String getConnectionFactoryInterface();

   void setConnectionFactoryInterface(String var1);

   String getConnectionFactoryImplClass();

   void setConnectionFactoryImplClass(String var1);

   String getConnectionInterface();

   void setConnectionInterface(String var1);

   String getConnectionImplClass();

   void setConnectionImplClass(String var1);

   String getId();

   void setId(String var1);
}
