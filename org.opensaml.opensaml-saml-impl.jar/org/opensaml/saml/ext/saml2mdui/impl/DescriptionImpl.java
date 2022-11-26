package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.ext.saml2mdui.Description;
import org.opensaml.saml.saml2.metadata.impl.LocalizedNameImpl;

public class DescriptionImpl extends LocalizedNameImpl implements Description {
   protected DescriptionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
