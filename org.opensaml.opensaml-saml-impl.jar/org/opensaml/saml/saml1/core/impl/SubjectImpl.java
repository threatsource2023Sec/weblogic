package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.opensaml.saml.saml1.core.Subject;
import org.opensaml.saml.saml1.core.SubjectConfirmation;

public class SubjectImpl extends AbstractSAMLObject implements Subject {
   private NameIdentifier nameIdentifier;
   private SubjectConfirmation subjectConfirmation;

   protected SubjectImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public NameIdentifier getNameIdentifier() {
      return this.nameIdentifier;
   }

   public void setNameIdentifier(NameIdentifier name) {
      this.nameIdentifier = (NameIdentifier)this.prepareForAssignment(this.nameIdentifier, name);
   }

   public SubjectConfirmation getSubjectConfirmation() {
      return this.subjectConfirmation;
   }

   public void setSubjectConfirmation(SubjectConfirmation conf) {
      this.subjectConfirmation = (SubjectConfirmation)this.prepareForAssignment(this.subjectConfirmation, conf);
   }

   public List getOrderedChildren() {
      List list = new ArrayList(2);
      if (this.nameIdentifier != null) {
         list.add(this.nameIdentifier);
      }

      if (this.subjectConfirmation != null) {
         list.add(this.subjectConfirmation);
      }

      return list.size() == 0 ? null : Collections.unmodifiableList(list);
   }
}
