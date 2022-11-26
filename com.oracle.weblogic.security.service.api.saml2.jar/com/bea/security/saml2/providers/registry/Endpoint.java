package com.bea.security.saml2.providers.registry;

import java.io.Serializable;

public interface Endpoint extends Serializable {
   String HTTP_ARTIFACT_BINDING = "HTTP/Artifact";
   String HTTP_POST_BINDING = "HTTP/POST";
   String HTTP_REDIRECT_BINDING = "HTTP/Redirect";
   String SOAP_BINDING = "SOAP";
   String HTTP_ARTIFACT_URN = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Artifact";
   String HTTP_POST_URN = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST";
   String HTTP_REDIRECT_URN = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect";
   String SOAP_HTTP_URN = "urn:oasis:names:tc:SAML:2.0:bindings:SOAP";

   String getBinding();

   void setBinding(String var1);

   String getLocation();

   void setLocation(String var1);
}
