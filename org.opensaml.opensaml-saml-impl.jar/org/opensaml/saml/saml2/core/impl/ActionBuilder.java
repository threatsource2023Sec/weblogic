package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Action;

public class ActionBuilder extends AbstractSAMLObjectBuilder {
   public Action buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Action", "saml2");
   }

   public Action buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ActionImpl(namespaceURI, localName, namespacePrefix);
   }
}
