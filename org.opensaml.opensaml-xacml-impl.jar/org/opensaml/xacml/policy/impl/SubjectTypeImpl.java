package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.SubjectType;

public class SubjectTypeImpl extends AbstractXACMLObject implements SubjectType {
   private XMLObjectChildrenList subjectMatch = new XMLObjectChildrenList(this);

   protected SubjectTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getSubjectMatches() {
      return this.subjectMatch;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.subjectMatch);
      return Collections.unmodifiableList(children);
   }
}
