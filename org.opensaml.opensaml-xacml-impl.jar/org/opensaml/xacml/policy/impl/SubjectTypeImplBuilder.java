package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.SubjectType;

public class SubjectTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public SubjectType buildObject() {
      return (SubjectType)this.buildObject(SubjectType.DEFAULT_ELEMENT_NAME);
   }

   public SubjectType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SubjectTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
