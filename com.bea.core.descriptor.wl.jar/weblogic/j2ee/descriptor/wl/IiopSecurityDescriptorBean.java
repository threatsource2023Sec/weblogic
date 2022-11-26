package weblogic.j2ee.descriptor.wl;

public interface IiopSecurityDescriptorBean {
   TransportRequirementsBean getTransportRequirements();

   boolean isTransportRequirementsSet();

   String getClientAuthentication();

   void setClientAuthentication(String var1);

   String getIdentityAssertion();

   void setIdentityAssertion(String var1);

   String getId();

   void setId(String var1);
}
