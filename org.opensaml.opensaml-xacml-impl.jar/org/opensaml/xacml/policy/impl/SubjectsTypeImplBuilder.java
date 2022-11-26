package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.SubjectsType;

public class SubjectsTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public SubjectsType buildObject() {
      return (SubjectsType)this.buildObject(SubjectsType.DEFAULT_ELEMENT_NAME);
   }

   public SubjectsType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SubjectsTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
