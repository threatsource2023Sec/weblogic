package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.saml2.metadata.OrganizationURL;

public class OrganizationURLImpl extends LocalizedURIImpl implements OrganizationURL {
   protected OrganizationURLImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
