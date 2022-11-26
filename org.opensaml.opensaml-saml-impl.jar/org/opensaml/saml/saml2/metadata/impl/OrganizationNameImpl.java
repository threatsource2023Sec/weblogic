package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.saml2.metadata.OrganizationName;

public class OrganizationNameImpl extends LocalizedNameImpl implements OrganizationName {
   protected OrganizationNameImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
