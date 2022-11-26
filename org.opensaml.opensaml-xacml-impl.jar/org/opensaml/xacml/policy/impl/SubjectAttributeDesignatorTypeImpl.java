package org.opensaml.xacml.policy.impl;

import java.util.List;
import net.shibboleth.utilities.java.support.collection.LazyList;
import org.opensaml.xacml.policy.SubjectAttributeDesignatorType;

public class SubjectAttributeDesignatorTypeImpl extends AttributeDesignatorTypeImpl implements SubjectAttributeDesignatorType {
   private String subjectCategory;

   protected SubjectAttributeDesignatorTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getSubjectCategory() {
      return this.subjectCategory;
   }

   public void setSubjectCategory(String category) {
      this.subjectCategory = this.prepareForAssignment(this.subjectCategory, category);
   }

   public List getOrderedChildren() {
      return new LazyList();
   }
}
