package weblogic.j2ee.descriptor.wl;

public interface AdminObjectsBean {
   ConfigPropertiesBean getDefaultProperties();

   boolean isDefaultPropertiesSet();

   AdminObjectGroupBean[] getAdminObjectGroups();

   AdminObjectGroupBean createAdminObjectGroup();

   void destroyAdminObjectGroup(AdminObjectGroupBean var1);

   String getId();

   void setId(String var1);
}
