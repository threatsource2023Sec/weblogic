package weblogic.ejb.container.deployer;

import weblogic.ejb.container.interfaces.IIOPSecurityDescriptor;

class IIOPSecurityDescriptorImpl implements IIOPSecurityDescriptor {
   private String transportIntegrity = "supported";
   private String transportConfidentiality = "supported";
   private String transportClientCertAuthentication = "supported";
   private String clientAuthentication = "supported";
   private String identityAssertion = "supported";

   void setTransport_integrity(String val) {
      this.transportIntegrity = val;
   }

   public String getTransport_integrity() {
      return this.transportIntegrity;
   }

   void setTransport_confidentiality(String val) {
      this.transportConfidentiality = val;
   }

   public String getTransport_confidentiality() {
      return this.transportConfidentiality;
   }

   void setTransport_client_cert_authentication(String val) {
      this.transportClientCertAuthentication = val;
   }

   public String getTransport_client_cert_authentication() {
      return this.transportClientCertAuthentication;
   }

   void setClient_authentication(String val) {
      this.clientAuthentication = val;
   }

   public String getClient_authentication() {
      return this.clientAuthentication;
   }

   void setIdentity_assertion(String val) {
      this.identityAssertion = val;
   }

   public String getIdentity_assertion() {
      return this.identityAssertion;
   }
}
