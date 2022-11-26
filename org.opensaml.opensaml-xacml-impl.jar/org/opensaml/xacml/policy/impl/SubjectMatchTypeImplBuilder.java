package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.SubjectMatchType;

public class SubjectMatchTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public SubjectMatchType buildObject() {
      return (SubjectMatchType)this.buildObject(SubjectMatchType.DEFAULT_ELEMENT_NAME);
   }

   public SubjectMatchType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SubjectMatchTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
