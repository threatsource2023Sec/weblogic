package weblogic.j2ee.descriptor.wl;

public interface ConnectionFactoryBean {
   String getFactoryName();

   void setFactoryName(String var1);

   ConnectionPropertiesBean getConnectionProperties();

   ConnectionPropertiesBean createConnectionProperties();

   void destroyConnectionProperties(ConnectionPropertiesBean var1);
}
