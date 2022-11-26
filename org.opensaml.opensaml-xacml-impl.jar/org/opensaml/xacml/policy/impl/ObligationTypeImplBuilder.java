package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ObligationType;

public class ObligationTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public ObligationType buildObject() {
      return (ObligationType)this.buildObject(ObligationType.DEFAULT_ELEMENT_QNAME);
   }

   public ObligationType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ObligationTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
