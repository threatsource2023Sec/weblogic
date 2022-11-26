package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.MissingAttributeDetailType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class MissingAttributeDetailTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public MissingAttributeDetailType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new MissingAttributeDetailTypeImpl(namespaceURI, localName, namespacePrefix);
   }

   public MissingAttributeDetailType buildObject() {
      return (MissingAttributeDetailType)this.buildObject(MissingAttributeDetailType.DEFAULT_ELEMENT_NAME);
   }
}
