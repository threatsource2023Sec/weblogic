package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ActionType;

public class ActionTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public ActionType buildObject() {
      return (ActionType)this.buildObject(ActionType.DEFAULT_ELEMENT_NAME);
   }

   public ActionType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ActionTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
