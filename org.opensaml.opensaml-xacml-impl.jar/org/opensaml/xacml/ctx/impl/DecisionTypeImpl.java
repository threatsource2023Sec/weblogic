package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.xacml.ctx.DecisionType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class DecisionTypeImpl extends AbstractXACMLObject implements DecisionType {
   private DecisionType.DECISION decision;

   protected DecisionTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getOrderedChildren() {
      return new ArrayList();
   }

   public DecisionType.DECISION getDecision() {
      return this.decision;
   }

   public void setDecision(DecisionType.DECISION dec) {
      this.decision = (DecisionType.DECISION)this.prepareForAssignment(this.decision, dec);
   }
}
