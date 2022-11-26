package weblogic.j2ee.descriptor.wl;

public interface AdminObjectGroupBean {
   String getAdminObjectInterface();

   void setAdminObjectInterface(String var1);

   String getAdminObjectClass();

   void setAdminObjectClass(String var1);

   AdminObjectInstanceBean[] getAdminObjectInstances();

   AdminObjectInstanceBean createAdminObjectInstance();

   void destroyAdminObjectInstance(AdminObjectInstanceBean var1);

   ConfigPropertiesBean getDefaultProperties();

   boolean isDefaultPropertiesSet();

   String getId();

   void setId(String var1);
}
