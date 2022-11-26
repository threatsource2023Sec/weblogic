package weblogic.j2ee.descriptor;

public interface AdminObjectBean {
   String getAdminObjectInterface();

   void setAdminObjectInterface(String var1);

   String getAdminObjectClass();

   void setAdminObjectClass(String var1);

   ConfigPropertyBean[] getConfigProperties();

   ConfigPropertyBean createConfigProperty();

   void destroyConfigProperty(ConfigPropertyBean var1);

   String getId();

   void setId(String var1);
}
