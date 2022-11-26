package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml1.core.Advice;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.Conditions;

public class AssertionImpl extends AbstractSignableSAMLObject implements Assertion {
   private String id;
   private SAMLVersion version;
   private String issuer;
   private DateTime issueInstant;
   private Conditions conditions;
   private Advice advice;
   private final IndexedXMLObjectChildrenList statements = new IndexedXMLObjectChildrenList(this);

   protected AssertionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
      this.version = SAMLVersion.VERSION_11;
   }

   public int getMajorVersion() {
      return this.version.getMajorVersion();
   }

   public int getMinorVersion() {
      return this.version.getMinorVersion();
   }

   public void setVersion(SAMLVersion newVersion) {
      this.version = (SAMLVersion)this.prepareForAssignment(this.version, newVersion);
   }

   public String getID() {
      return this.id;
   }

   public void setID(String newID) {
      String oldID = this.id;
      this.id = this.prepareForAssignment(this.id, newID);
      this.registerOwnID(oldID, this.id);
   }

   public String getIssuer() {
      return this.issuer;
   }

   public void setIssuer(String iss) {
      this.issuer = this.prepareForAssignment(this.issuer, iss);
   }

   public DateTime getIssueInstant() {
      return this.issueInstant;
   }

   public void setIssueInstant(DateTime instant) {
      this.issueInstant = this.prepareForAssignment(this.issueInstant, instant);
   }

   public Conditions getConditions() {
      return this.conditions;
   }

   public void setConditions(Conditions c) {
      this.conditions = (Conditions)this.prepareForAssignment(this.conditions, c);
   }

   public Advice getAdvice() {
      return this.advice;
   }

   public void setAdvice(Advice adv) {
      this.advice = (Advice)this.prepareForAssignment(this.advice, adv);
   }

   public List getStatements() {
      return this.statements;
   }

   public List getStatements(QName typeOrName) {
      return this.statements.subList(typeOrName);
   }

   public List getSubjectStatements() {
      QName statementQName = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectStatement");
      return this.statements.subList(statementQName);
   }

   public List getAuthenticationStatements() {
      QName statementQName = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthenticationStatement");
      return this.statements.subList(statementQName);
   }

   public List getAuthorizationDecisionStatements() {
      QName statementQName = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorizationDecisionStatement");
      return this.statements.subList(statementQName);
   }

   public List getAttributeStatements() {
      QName statementQName = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeStatement");
      return this.statements.subList(statementQName);
   }

   public String getSignatureReferenceID() {
      return this.id;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.conditions != null) {
         children.add(this.conditions);
      }

      if (this.advice != null) {
         children.add(this.advice);
      }

      children.addAll(this.statements);
      if (this.getSignature() != null) {
         children.add(this.getSignature());
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
