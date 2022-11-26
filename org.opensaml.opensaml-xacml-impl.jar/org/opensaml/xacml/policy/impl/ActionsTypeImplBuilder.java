package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ActionsType;

public class ActionsTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public ActionsType buildObject() {
      return (ActionsType)this.buildObject(ActionsType.DEFAULT_ELEMENT_NAME);
   }

   public ActionsType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ActionsTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
