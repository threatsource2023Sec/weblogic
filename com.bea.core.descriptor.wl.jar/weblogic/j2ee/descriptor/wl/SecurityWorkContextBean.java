package weblogic.j2ee.descriptor.wl;

public interface SecurityWorkContextBean {
   boolean isInboundMappingRequired();

   void setInboundMappingRequired(boolean var1);

   AnonPrincipalBean getCallerPrincipalDefaultMapped();

   boolean isCallerPrincipalDefaultMappedSet();

   InboundCallerPrincipalMappingBean[] getCallerPrincipalMappings();

   InboundCallerPrincipalMappingBean createCallerPrincipalMapping(String var1);

   InboundCallerPrincipalMappingBean lookupCallerPrincipalMapping(String var1);

   void destroyCallerPrincipalMapping(InboundCallerPrincipalMappingBean var1);

   String getGroupPrincipalDefaultMapped();

   void setGroupPrincipalDefaultMapped(String var1);

   InboundGroupPrincipalMappingBean[] getGroupPrincipalMappings();

   InboundGroupPrincipalMappingBean createGroupPrincipalMapping(String var1);

   InboundGroupPrincipalMappingBean lookupGroupPrincipalMapping(String var1);

   void destroyGroupPrincipalMapping(InboundGroupPrincipalMappingBean var1);

   String getId();

   void setId(String var1);
}
