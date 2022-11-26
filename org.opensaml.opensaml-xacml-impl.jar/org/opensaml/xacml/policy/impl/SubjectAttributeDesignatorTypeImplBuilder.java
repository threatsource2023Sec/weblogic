package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.SubjectAttributeDesignatorType;

public class SubjectAttributeDesignatorTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public SubjectAttributeDesignatorType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SubjectAttributeDesignatorTypeImpl(namespaceURI, localName, namespacePrefix);
   }

   public SubjectAttributeDesignatorType buildObject() {
      return (SubjectAttributeDesignatorType)this.buildObject(SubjectAttributeDesignatorType.DEFAULT_ELEMENT_QNAME);
   }
}
