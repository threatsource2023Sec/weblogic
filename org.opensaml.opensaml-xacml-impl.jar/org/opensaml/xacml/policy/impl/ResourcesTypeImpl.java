package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ResourcesType;

public class ResourcesTypeImpl extends AbstractXACMLObject implements ResourcesType {
   private XMLObjectChildrenList resource = new XMLObjectChildrenList(this);

   public ResourcesTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getResources() {
      return this.resource;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.resource);
      return Collections.unmodifiableList(children);
   }
}
