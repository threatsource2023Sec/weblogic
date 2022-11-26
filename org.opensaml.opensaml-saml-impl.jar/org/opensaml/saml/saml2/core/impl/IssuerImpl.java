package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.saml2.core.Issuer;

public class IssuerImpl extends AbstractNameIDType implements Issuer {
   protected IssuerImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
