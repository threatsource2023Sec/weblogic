package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.DefaultsType;

public class DefaultsTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public DefaultsType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DefaultsTypeImpl(namespaceURI, localName, namespacePrefix);
   }

   public DefaultsType buildObject() {
      return null;
   }
}
