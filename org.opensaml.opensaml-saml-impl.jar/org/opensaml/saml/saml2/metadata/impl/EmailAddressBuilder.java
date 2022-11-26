package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.EmailAddress;

public class EmailAddressBuilder extends AbstractSAMLObjectBuilder {
   public EmailAddress buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "EmailAddress", "md");
   }

   public EmailAddress buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EmailAddressImpl(namespaceURI, localName, namespacePrefix);
   }
}
