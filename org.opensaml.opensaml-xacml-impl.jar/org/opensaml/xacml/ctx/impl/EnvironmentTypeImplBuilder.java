package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.EnvironmentType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class EnvironmentTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public EnvironmentType buildObject() {
      return (EnvironmentType)this.buildObject(EnvironmentType.DEFAULT_ELEMENT_NAME);
   }

   public EnvironmentType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EnvironmentTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
