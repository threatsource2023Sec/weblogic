package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.ActionType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class ActionTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public ActionType buildObject() {
      return (ActionType)this.buildObject(ActionType.DEFAULT_ELEMENT_NAME);
   }

   public ActionType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ActionTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
