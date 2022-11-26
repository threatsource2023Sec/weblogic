package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.ext.saml2mdui.InformationURL;
import org.opensaml.saml.saml2.metadata.impl.LocalizedURIImpl;

public class InformationURLImpl extends LocalizedURIImpl implements InformationURL {
   protected InformationURLImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
