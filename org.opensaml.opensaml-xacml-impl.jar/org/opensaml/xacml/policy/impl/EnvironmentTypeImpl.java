package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.EnvironmentType;

public class EnvironmentTypeImpl extends AbstractXACMLObject implements EnvironmentType {
   private XMLObjectChildrenList environmentMatches = new XMLObjectChildrenList(this);

   protected EnvironmentTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getEnvrionmentMatches() {
      return this.environmentMatches;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.environmentMatches);
      return Collections.unmodifiableList(children);
   }
}
