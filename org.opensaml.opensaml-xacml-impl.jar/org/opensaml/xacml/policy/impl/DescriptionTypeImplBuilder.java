package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.DescriptionType;

public class DescriptionTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public DescriptionType buildObject() {
      return this.buildObject("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Description", "xacml");
   }

   public DescriptionType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DescriptionTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
