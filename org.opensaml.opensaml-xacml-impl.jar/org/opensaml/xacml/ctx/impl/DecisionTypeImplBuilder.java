package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.DecisionType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class DecisionTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public DecisionType buildObject() {
      return (DecisionType)this.buildObject(DecisionType.DEFAULT_ELEMENT_NAME);
   }

   public DecisionType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DecisionTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
