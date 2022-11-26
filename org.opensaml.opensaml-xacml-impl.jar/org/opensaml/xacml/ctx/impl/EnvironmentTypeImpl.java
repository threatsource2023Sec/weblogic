package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.ctx.EnvironmentType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class EnvironmentTypeImpl extends AbstractXACMLObject implements EnvironmentType {
   private XMLObjectChildrenList attributes = new XMLObjectChildrenList(this);

   protected EnvironmentTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAttributes() {
      return this.attributes;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.attributes);
      return Collections.unmodifiableList(children);
   }
}
