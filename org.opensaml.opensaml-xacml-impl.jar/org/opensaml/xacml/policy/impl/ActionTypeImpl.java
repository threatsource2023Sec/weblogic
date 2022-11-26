package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ActionType;

public class ActionTypeImpl extends AbstractXACMLObject implements ActionType {
   private XMLObjectChildrenList actionMatch = new XMLObjectChildrenList(this);

   protected ActionTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getActionMatches() {
      return this.actionMatch;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.actionMatch);
      return Collections.unmodifiableList(children);
   }
}
