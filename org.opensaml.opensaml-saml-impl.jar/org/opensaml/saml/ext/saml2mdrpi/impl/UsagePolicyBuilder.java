package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdrpi.UsagePolicy;

public class UsagePolicyBuilder extends AbstractSAMLObjectBuilder {
   public UsagePolicy buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:rpi", "UsagePolicy", "mdrpi");
   }

   public UsagePolicy buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new UsagePolicyImpl(namespaceURI, localName, namespacePrefix);
   }
}
