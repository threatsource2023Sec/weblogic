package weblogic.j2ee.descriptor.wl;

public interface ConnectionDefinitionBean {
   String getConnectionFactoryInterface();

   void setConnectionFactoryInterface(String var1);

   ConnectionDefinitionPropertiesBean getDefaultConnectionProperties();

   boolean isDefaultConnectionPropertiesSet();

   ConnectionInstanceBean[] getConnectionInstances();

   ConnectionInstanceBean createConnectionInstance();

   void destroyConnectionInstance(ConnectionInstanceBean var1);

   String getId();

   void setId(String var1);
}
