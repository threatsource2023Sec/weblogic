package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.opensaml.saml.ext.saml2mdrpi.UsagePolicy;
import org.opensaml.saml.saml2.metadata.impl.LocalizedURIImpl;

public class UsagePolicyImpl extends LocalizedURIImpl implements UsagePolicy {
   protected UsagePolicyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
