package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.EffectType;
import org.opensaml.xacml.policy.ObligationType;

public class ObligationTypeImpl extends AbstractXACMLObject implements ObligationType {
   private XMLObjectChildrenList attributeAssignments = new XMLObjectChildrenList(this);
   private EffectType fulFillOn;
   private String obligationId;

   protected ObligationTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAttributeAssignments() {
      return this.attributeAssignments;
   }

   public EffectType getFulfillOn() {
      return this.fulFillOn;
   }

   public String getObligationId() {
      return this.obligationId;
   }

   public void setFulfillOn(EffectType newFulfillOn) {
      this.fulFillOn = (EffectType)this.prepareForAssignment(this.fulFillOn, newFulfillOn);
   }

   public void setObligationId(String newObligationId) {
      this.obligationId = this.prepareForAssignment(this.obligationId, newObligationId);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (!this.attributeAssignments.isEmpty()) {
         children.addAll(this.attributeAssignments);
      }

      return Collections.unmodifiableList(children);
   }
}
