package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectQuery;

public abstract class SubjectQueryImpl extends RequestAbstractTypeImpl implements SubjectQuery {
   private Subject subject;

   protected SubjectQueryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Subject getSubject() {
      return this.subject;
   }

   public void setSubject(Subject newSubject) {
      this.subject = (Subject)this.prepareForAssignment(this.subject, newSubject);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (super.getOrderedChildren() != null) {
         children.addAll(super.getOrderedChildren());
      }

      if (this.subject != null) {
         children.add(this.subject);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
