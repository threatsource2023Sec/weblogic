package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ApplyType;

public class ApplyTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public ApplyType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ApplyTypeImpl(namespaceURI, localName, namespacePrefix);
   }

   public ApplyType buildObject() {
      return (ApplyType)this.buildObject(ApplyType.DEFAULT_ELEMENT_NAME);
   }
}
