package org.opensaml.saml.ext.saml2delrestrict.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2delrestrict.Delegate;

public class DelegateBuilder extends AbstractSAMLObjectBuilder {
   public Delegate buildObject() {
      return (Delegate)this.buildObject(Delegate.DEFAULT_ELEMENT_NAME);
   }

   public Delegate buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DelegateImpl(namespaceURI, localName, namespacePrefix);
   }
}
