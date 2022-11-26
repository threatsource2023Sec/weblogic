package weblogic.j2ee.descriptor.wl;

public interface ConfigPropertiesBean {
   ConfigPropertyBean[] getProperties();

   ConfigPropertyBean createProperty();

   void destroyProperty(ConfigPropertyBean var1);

   String getId();

   void setId(String var1);
}
