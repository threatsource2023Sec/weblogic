package org.opensaml;

import java.io.IOException;

public interface SAMLBinding {
   String SAML_SOAP_HTTPS = "urn:oasis:names:tc:SAML:1.0:bindings:SOAP-binding";

   SAMLResponse send(SAMLAuthorityBinding var1, SAMLRequest var2) throws SAMLException;

   /** @deprecated */
   SAMLRequest receive(Object var1, StringBuffer var2) throws SAMLException;

   SAMLRequest receive(Object var1) throws SAMLException;

   void respond(Object var1, SAMLResponse var2, SAMLException var3) throws IOException;
}
