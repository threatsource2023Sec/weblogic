package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml.saml1.core.DecisionTypeEnumeration;
import org.opensaml.saml.saml1.core.Evidence;

public class AuthorizationDecisionStatementImpl extends SubjectStatementImpl implements AuthorizationDecisionStatement {
   private String resource;
   private DecisionTypeEnumeration decision;
   private final XMLObjectChildrenList actions = new XMLObjectChildrenList(this);
   private Evidence evidence;

   protected AuthorizationDecisionStatementImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getResource() {
      return this.resource;
   }

   public void setResource(String res) {
      this.resource = this.prepareForAssignment(this.resource, res);
   }

   public DecisionTypeEnumeration getDecision() {
      return this.decision;
   }

   public void setDecision(DecisionTypeEnumeration dec) {
      this.decision = (DecisionTypeEnumeration)this.prepareForAssignment(this.decision, dec);
   }

   public List getActions() {
      return this.actions;
   }

   public Evidence getEvidence() {
      return this.evidence;
   }

   public void setEvidence(Evidence ev) {
      this.evidence = (Evidence)this.prepareForAssignment(this.evidence, ev);
   }

   public List getOrderedChildren() {
      List list = new ArrayList(this.actions.size() + 2);
      if (super.getOrderedChildren() != null) {
         list.addAll(super.getOrderedChildren());
      }

      list.addAll(this.actions);
      if (this.evidence != null) {
         list.add(this.evidence);
      }

      return list.size() == 0 ? null : Collections.unmodifiableList(list);
   }
}
