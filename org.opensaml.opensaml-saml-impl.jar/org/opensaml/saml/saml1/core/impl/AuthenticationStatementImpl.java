package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml1.core.AuthenticationStatement;
import org.opensaml.saml.saml1.core.SubjectLocality;

public class AuthenticationStatementImpl extends SubjectStatementImpl implements AuthenticationStatement {
   private String authenticationMethod;
   private DateTime authenticationInstant;
   private SubjectLocality subjectLocality;
   private final XMLObjectChildrenList authorityBindings = new XMLObjectChildrenList(this);

   protected AuthenticationStatementImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAuthenticationMethod() {
      return this.authenticationMethod;
   }

   public void setAuthenticationMethod(String method) {
      this.authenticationMethod = this.prepareForAssignment(this.authenticationMethod, method);
   }

   public DateTime getAuthenticationInstant() {
      return this.authenticationInstant;
   }

   public void setAuthenticationInstant(DateTime instant) {
      this.authenticationInstant = this.prepareForAssignment(this.authenticationInstant, instant);
   }

   public SubjectLocality getSubjectLocality() {
      return this.subjectLocality;
   }

   public void setSubjectLocality(SubjectLocality locality) {
      this.subjectLocality = (SubjectLocality)this.prepareForAssignment(this.subjectLocality, locality);
   }

   public List getAuthorityBindings() {
      return this.authorityBindings;
   }

   public List getOrderedChildren() {
      List list = new ArrayList(this.authorityBindings.size() + 2);
      if (super.getOrderedChildren() != null) {
         list.addAll(super.getOrderedChildren());
      }

      if (this.subjectLocality != null) {
         list.add(this.subjectLocality);
      }

      list.addAll(this.authorityBindings);
      return list.size() == 0 ? null : Collections.unmodifiableList(list);
   }
}
