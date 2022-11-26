package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ObligationsType;

public class ObligationsTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public ObligationsType buildObject() {
      return (ObligationsType)this.buildObject(ObligationsType.DEFAULT_ELEMENT_QNAME);
   }

   public ObligationsType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ObligationsTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
