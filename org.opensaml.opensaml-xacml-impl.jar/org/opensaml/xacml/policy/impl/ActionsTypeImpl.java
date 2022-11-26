package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ActionsType;

public class ActionsTypeImpl extends AbstractXACMLObject implements ActionsType {
   private XMLObjectChildrenList action = new XMLObjectChildrenList(this);

   protected ActionsTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getActions() {
      return this.action;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.action);
      return children;
   }
}
