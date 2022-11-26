package org.opensaml.soap.wspolicy.impl;

import org.opensaml.soap.wspolicy.Policy;

public class PolicyBuilder extends AbstractWSPolicyObjectBuilder {
   public Policy buildObject() {
      return (Policy)this.buildObject(Policy.ELEMENT_NAME);
   }

   public Policy buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PolicyImpl(namespaceURI, localName, namespacePrefix);
   }
}
