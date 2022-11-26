package org.opensaml.saml.ext.samlpthrpty.impl;

import org.opensaml.saml.ext.samlpthrpty.RespondTo;
import org.opensaml.saml.saml2.core.impl.AbstractNameIDType;

public class RespondToImpl extends AbstractNameIDType implements RespondTo {
   protected RespondToImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
