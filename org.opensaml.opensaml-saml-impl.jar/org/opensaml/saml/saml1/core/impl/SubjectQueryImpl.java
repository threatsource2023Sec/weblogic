package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.Subject;
import org.opensaml.saml.saml1.core.SubjectQuery;

public abstract class SubjectQueryImpl extends AbstractSAMLObject implements SubjectQuery {
   private Subject subject;

   protected SubjectQueryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Subject getSubject() {
      return this.subject;
   }

   public void setSubject(Subject sub) {
      this.subject = (Subject)this.prepareForAssignment(this.subject, sub);
   }

   public List getOrderedChildren() {
      if (this.subject == null) {
         return null;
      } else {
         List children = new ArrayList();
         children.add(this.subject);
         return Collections.unmodifiableList(children);
      }
   }
}
