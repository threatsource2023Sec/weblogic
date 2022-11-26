package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.SubjectType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class SubjectTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public SubjectType buildObject() {
      return (SubjectType)this.buildObject(SubjectType.DEFAULT_ELEMENT_NAME);
   }

   public SubjectType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SubjectTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
