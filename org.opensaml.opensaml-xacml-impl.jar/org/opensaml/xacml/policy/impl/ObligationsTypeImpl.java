package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ObligationsType;

public class ObligationsTypeImpl extends AbstractXACMLObject implements ObligationsType {
   private XMLObjectChildrenList obligations = new XMLObjectChildrenList(this);

   protected ObligationsTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getObligations() {
      return this.obligations;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.obligations);
      return Collections.unmodifiableList(children);
   }
}
