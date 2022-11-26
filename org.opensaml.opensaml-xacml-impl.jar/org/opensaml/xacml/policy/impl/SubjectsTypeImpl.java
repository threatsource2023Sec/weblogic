package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.SubjectsType;

public class SubjectsTypeImpl extends AbstractXACMLObject implements SubjectsType {
   private XMLObjectChildrenList subject = new XMLObjectChildrenList(this);

   protected SubjectsTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getSubjects() {
      return this.subject;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.subject);
      return children;
   }
}
