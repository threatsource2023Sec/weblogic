package weblogic.j2ee.descriptor.wl;

public interface ConnectionInstanceBean {
   String getDescription();

   void setDescription(String var1);

   String getJNDIName();

   void setJNDIName(String var1);

   ConnectionDefinitionPropertiesBean getConnectionProperties();

   boolean isConnectionPropertiesSet();

   String getId();

   void setId(String var1);
}
