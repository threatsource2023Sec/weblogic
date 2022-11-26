package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.IdReferenceType;

public class IdReferenceTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public IdReferenceType buildObject() {
      return (IdReferenceType)this.buildObject(IdReferenceType.TYPE_NAME);
   }

   public IdReferenceType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new IdReferenceTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
