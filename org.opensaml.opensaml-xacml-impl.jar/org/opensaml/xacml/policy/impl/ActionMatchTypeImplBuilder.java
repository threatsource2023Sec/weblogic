package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ActionMatchType;

public class ActionMatchTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public ActionMatchType buildObject() {
      return (ActionMatchType)this.buildObject(ActionMatchType.DEFAULT_ELEMENT_NAME);
   }

   public ActionMatchType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ActionMatchTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
