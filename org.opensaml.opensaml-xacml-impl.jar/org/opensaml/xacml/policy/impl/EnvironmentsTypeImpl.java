package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.EnvironmentsType;

public class EnvironmentsTypeImpl extends AbstractXACMLObject implements EnvironmentsType {
   private XMLObjectChildrenList environments = new XMLObjectChildrenList(this);

   protected EnvironmentsTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getEnvironments() {
      return this.environments;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.environments);
      return Collections.unmodifiableList(children);
   }
}
