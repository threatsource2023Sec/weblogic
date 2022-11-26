package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.AuthnContext;
import org.opensaml.saml.saml2.core.AuthnStatement;
import org.opensaml.saml.saml2.core.SubjectLocality;

public class AuthnStatementImpl extends AbstractSAMLObject implements AuthnStatement {
   private SubjectLocality subjectLocality;
   private AuthnContext authnContext;
   private DateTime authnInstant;
   private String sessionIndex;
   private DateTime sessionNotOnOrAfter;

   protected AuthnStatementImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public SubjectLocality getSubjectLocality() {
      return this.subjectLocality;
   }

   public void setSubjectLocality(SubjectLocality newSubjectLocality) {
      this.subjectLocality = (SubjectLocality)this.prepareForAssignment(this.subjectLocality, newSubjectLocality);
   }

   public AuthnContext getAuthnContext() {
      return this.authnContext;
   }

   public void setAuthnContext(AuthnContext newAuthnContext) {
      this.authnContext = (AuthnContext)this.prepareForAssignment(this.authnContext, newAuthnContext);
   }

   public DateTime getAuthnInstant() {
      return this.authnInstant;
   }

   public void setAuthnInstant(DateTime newAuthnInstant) {
      this.authnInstant = this.prepareForAssignment(this.authnInstant, newAuthnInstant);
   }

   public String getSessionIndex() {
      return this.sessionIndex;
   }

   public void setSessionIndex(String newSessionIndex) {
      this.sessionIndex = this.prepareForAssignment(this.sessionIndex, newSessionIndex);
   }

   public DateTime getSessionNotOnOrAfter() {
      return this.sessionNotOnOrAfter;
   }

   public void setSessionNotOnOrAfter(DateTime newSessionNotOnOrAfter) {
      this.sessionNotOnOrAfter = this.prepareForAssignment(this.sessionNotOnOrAfter, newSessionNotOnOrAfter);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.add(this.subjectLocality);
      children.add(this.authnContext);
      return Collections.unmodifiableList(children);
   }
}
