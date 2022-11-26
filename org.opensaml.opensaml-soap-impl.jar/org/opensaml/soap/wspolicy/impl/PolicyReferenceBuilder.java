package org.opensaml.soap.wspolicy.impl;

import org.opensaml.soap.wspolicy.PolicyReference;

public class PolicyReferenceBuilder extends AbstractWSPolicyObjectBuilder {
   public PolicyReference buildObject() {
      return (PolicyReference)this.buildObject(PolicyReference.ELEMENT_NAME);
   }

   public PolicyReference buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PolicyReferenceImpl(namespaceURI, localName, namespacePrefix);
   }
}
