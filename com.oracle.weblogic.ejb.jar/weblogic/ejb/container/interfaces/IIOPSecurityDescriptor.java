package weblogic.ejb.container.interfaces;

public interface IIOPSecurityDescriptor {
   String NONE_VAL = "none";
   String SUPPORTED_VAL = "supported";
   String REQUIRED_VAL = "required";

   String getTransport_integrity();

   String getTransport_confidentiality();

   String getTransport_client_cert_authentication();

   String getClient_authentication();

   String getIdentity_assertion();
}
