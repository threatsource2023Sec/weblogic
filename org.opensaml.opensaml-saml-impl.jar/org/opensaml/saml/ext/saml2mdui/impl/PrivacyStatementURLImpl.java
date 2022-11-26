package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.ext.saml2mdui.PrivacyStatementURL;
import org.opensaml.saml.saml2.metadata.impl.LocalizedURIImpl;

public class PrivacyStatementURLImpl extends LocalizedURIImpl implements PrivacyStatementURL {
   protected PrivacyStatementURLImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
