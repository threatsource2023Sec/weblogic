package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.EnvironmentMatchType;

public class EnvironmentMatchTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public EnvironmentMatchType buildObject() {
      return (EnvironmentMatchType)this.buildObject(EnvironmentMatchType.DEFAULT_ELEMENT_QNAME);
   }

   public EnvironmentMatchType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EnvironmentMatchTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
