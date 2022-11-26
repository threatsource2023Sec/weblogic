package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.ContactPerson;

public class ContactPersonBuilder extends AbstractSAMLObjectBuilder {
   public ContactPerson buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "ContactPerson", "md");
   }

   public ContactPerson buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ContactPersonImpl(namespaceURI, localName, namespacePrefix);
   }
}
