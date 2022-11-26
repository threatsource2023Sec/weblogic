package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.xacml.ctx.DecisionType;
import org.opensaml.xacml.ctx.ResultType;
import org.opensaml.xacml.ctx.StatusType;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ObligationsType;

public class ResultTypeImpl extends AbstractXACMLObject implements ResultType {
   private String resourceId;
   private DecisionType decision;
   private StatusType status;
   private ObligationsType obligations;

   protected ResultTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public DecisionType getDecision() {
      return this.decision;
   }

   public ObligationsType getObligations() {
      return this.obligations;
   }

   public void setObligations(ObligationsType obligationsIn) {
      this.obligations = (ObligationsType)this.prepareForAssignment(this.obligations, obligationsIn);
   }

   public String getResourceId() {
      return this.resourceId;
   }

   public StatusType getStatus() {
      return this.status;
   }

   public void setStatus(StatusType statusIn) {
      this.status = (StatusType)this.prepareForAssignment(this.status, statusIn);
   }

   public void setDecision(DecisionType decisionIn) {
      this.decision = (DecisionType)this.prepareForAssignment(this.decision, decisionIn);
   }

   public void setResourceId(String newResourceId) {
      this.resourceId = this.prepareForAssignment(this.resourceId, newResourceId);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.decision != null) {
         children.add(this.decision);
      }

      if (this.status != null) {
         children.add(this.status);
      }

      if (this.obligations != null) {
         children.add(this.obligations);
      }

      return Collections.unmodifiableList(children);
   }
}
