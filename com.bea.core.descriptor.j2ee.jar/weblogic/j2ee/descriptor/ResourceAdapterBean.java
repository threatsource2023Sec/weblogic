package weblogic.j2ee.descriptor;

public interface ResourceAdapterBean {
   String getResourceAdapterClass();

   void setResourceAdapterClass(String var1);

   ConfigPropertyBean[] getConfigProperties();

   ConfigPropertyBean createConfigProperty();

   void destroyConfigProperty(ConfigPropertyBean var1);

   OutboundResourceAdapterBean getOutboundResourceAdapter();

   OutboundResourceAdapterBean createOutboundResourceAdapter();

   void destroyOutboundResourceAdapter(OutboundResourceAdapterBean var1);

   InboundResourceAdapterBean getInboundResourceAdapter();

   InboundResourceAdapterBean createInboundResourceAdapter();

   void destroyInboundResourceAdapter(InboundResourceAdapterBean var1);

   AdminObjectBean[] getAdminObjects();

   AdminObjectBean createAdminObject();

   void destroyAdminObject(AdminObjectBean var1);

   SecurityPermissionBean[] getSecurityPermissions();

   SecurityPermissionBean createSecurityPermission();

   void destroySecurityPermission(SecurityPermissionBean var1);

   String getId();

   void setId(String var1);
}
