package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.AuthzDecisionStatement;
import org.opensaml.saml.saml2.core.DecisionTypeEnumeration;
import org.opensaml.saml.saml2.core.Evidence;

public class AuthzDecisionStatementImpl extends AbstractSAMLObject implements AuthzDecisionStatement {
   private String resource;
   private DecisionTypeEnumeration decision;
   private final XMLObjectChildrenList actions = new XMLObjectChildrenList(this);
   private Evidence evidence;

   protected AuthzDecisionStatementImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getResource() {
      return this.resource;
   }

   public void setResource(String newResourceURI) {
      this.resource = this.prepareForAssignment(this.resource, newResourceURI, false);
   }

   public DecisionTypeEnumeration getDecision() {
      return this.decision;
   }

   public void setDecision(DecisionTypeEnumeration newDecision) {
      this.decision = (DecisionTypeEnumeration)this.prepareForAssignment(this.decision, newDecision);
   }

   public List getActions() {
      return this.actions;
   }

   public Evidence getEvidence() {
      return this.evidence;
   }

   public void setEvidence(Evidence newEvidence) {
      this.evidence = (Evidence)this.prepareForAssignment(this.evidence, newEvidence);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.actions);
      children.add(this.evidence);
      return Collections.unmodifiableList(children);
   }
}
