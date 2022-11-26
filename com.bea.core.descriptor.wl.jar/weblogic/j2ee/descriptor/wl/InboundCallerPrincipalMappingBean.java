package weblogic.j2ee.descriptor.wl;

public interface InboundCallerPrincipalMappingBean {
   String getEisCallerPrincipal();

   void setEisCallerPrincipal(String var1);

   AnonPrincipalBean getMappedCallerPrincipal();

   AnonPrincipalBean createMappedCallerPrincipal();

   void destroyMappedCallerPrincipal(AnonPrincipalBean var1);

   String getId();

   void setId(String var1);
}
