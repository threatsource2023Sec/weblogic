package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml2.core.Advice;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.Subject;

public class AssertionImpl extends AbstractSignableSAMLObject implements Assertion {
   private SAMLVersion version;
   private DateTime issueInstant;
   private String id;
   private Issuer issuer;
   private Subject subject;
   private Conditions conditions;
   private Advice advice;
   private final IndexedXMLObjectChildrenList statements;

   protected AssertionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
      this.version = SAMLVersion.VERSION_20;
      this.statements = new IndexedXMLObjectChildrenList(this);
   }

   public SAMLVersion getVersion() {
      return this.version;
   }

   public void setVersion(SAMLVersion newVersion) {
      this.version = (SAMLVersion)this.prepareForAssignment(this.version, newVersion);
   }

   public DateTime getIssueInstant() {
      return this.issueInstant;
   }

   public void setIssueInstant(DateTime newIssueInstance) {
      this.issueInstant = this.prepareForAssignment(this.issueInstant, newIssueInstance);
   }

   public String getID() {
      return this.id;
   }

   public void setID(String newID) {
      String oldID = this.id;
      this.id = this.prepareForAssignment(this.id, newID);
      this.registerOwnID(oldID, this.id);
   }

   public Issuer getIssuer() {
      return this.issuer;
   }

   public void setIssuer(Issuer newIssuer) {
      this.issuer = (Issuer)this.prepareForAssignment(this.issuer, newIssuer);
   }

   public Subject getSubject() {
      return this.subject;
   }

   public void setSubject(Subject newSubject) {
      this.subject = (Subject)this.prepareForAssignment(this.subject, newSubject);
   }

   public Conditions getConditions() {
      return this.conditions;
   }

   public void setConditions(Conditions newConditions) {
      this.conditions = (Conditions)this.prepareForAssignment(this.conditions, newConditions);
   }

   public Advice getAdvice() {
      return this.advice;
   }

   public void setAdvice(Advice newAdvice) {
      this.advice = (Advice)this.prepareForAssignment(this.advice, newAdvice);
   }

   public List getStatements() {
      return this.statements;
   }

   public List getStatements(QName typeOrName) {
      return this.statements.subList(typeOrName);
   }

   public List getAuthnStatements() {
      QName statementQName = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnStatement", "saml2");
      return this.statements.subList(statementQName);
   }

   public List getAuthzDecisionStatements() {
      QName statementQName = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthzDecisionStatement", "saml2");
      return this.statements.subList(statementQName);
   }

   public List getAttributeStatements() {
      QName statementQName = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeStatement", "saml2");
      return this.statements.subList(statementQName);
   }

   public String getSignatureReferenceID() {
      return this.id;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.add(this.issuer);
      if (this.getSignature() != null) {
         children.add(this.getSignature());
      }

      children.add(this.subject);
      children.add(this.conditions);
      children.add(this.advice);
      children.addAll(this.statements);
      return Collections.unmodifiableList(children);
   }
}
