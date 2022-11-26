package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.saml2.metadata.OrganizationDisplayName;

public class OrganizationDisplayNameImpl extends LocalizedNameImpl implements OrganizationDisplayName {
   protected OrganizationDisplayNameImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
