package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.ext.saml2mdui.DisplayName;
import org.opensaml.saml.saml2.metadata.impl.LocalizedNameImpl;

public class DisplayNameImpl extends LocalizedNameImpl implements DisplayName {
   protected DisplayNameImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
