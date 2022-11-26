package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.ctx.SubjectType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class SubjectTypeImpl extends AbstractXACMLObject implements SubjectType {
   private String subjectCategory = "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject";
   private XMLObjectChildrenList attributes = new XMLObjectChildrenList(this);

   protected SubjectTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getSubjectCategory() {
      return this.subjectCategory;
   }

   public void setSubjectCategory(String newSubjectCategory) {
      this.subjectCategory = this.prepareForAssignment(this.subjectCategory, newSubjectCategory);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.attributes);
      return Collections.unmodifiableList(children);
   }

   public List getAttributes() {
      return this.attributes;
   }
}
