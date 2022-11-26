package org.opensaml.saml.ext.samlec.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.samlec.SessionKey;

public class SessionKeyBuilder extends AbstractSAMLObjectBuilder {
   public SessionKey buildObject() {
      return this.buildObject("urn:ietf:params:xml:ns:samlec", "SessionKey", "samlec");
   }

   public SessionKey buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SessionKeyImpl(namespaceURI, localName, namespacePrefix);
   }
}
