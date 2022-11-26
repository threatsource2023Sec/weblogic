package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ResourceType;

public class ResourceTypeImpl extends AbstractXACMLObject implements ResourceType {
   private XMLObjectChildrenList resourceMatch = new XMLObjectChildrenList(this);

   protected ResourceTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getResourceMatches() {
      return this.resourceMatch;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.resourceMatch);
      return Collections.unmodifiableList(children);
   }
}
