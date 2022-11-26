package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ActionMatchType;
import org.opensaml.xacml.policy.AttributeDesignatorType;
import org.opensaml.xacml.policy.AttributeSelectorType;
import org.opensaml.xacml.policy.AttributeValueType;

public class ActionMatchTypeImpl extends AbstractXACMLObject implements ActionMatchType {
   private AttributeValueType attributeValue;
   private IndexedXMLObjectChildrenList attributeChoice = new IndexedXMLObjectChildrenList(this);
   private String matchId;

   public ActionMatchTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public AttributeSelectorType getAttributeSelector() {
      List selectors = this.attributeChoice.subList(AttributeSelectorType.DEFAULT_ELEMENT_NAME);
      return selectors != null && !selectors.isEmpty() ? (AttributeSelectorType)selectors.get(0) : null;
   }

   public AttributeValueType getAttributeValue() {
      return this.attributeValue;
   }

   public AttributeDesignatorType getActionAttributeDesignator() {
      List selectors = this.attributeChoice.subList(AttributeDesignatorType.ACTION_ATTRIBUTE_DESIGNATOR_ELEMENT_NAME);
      return selectors != null && !selectors.isEmpty() ? (AttributeDesignatorType)selectors.get(0) : null;
   }

   public String getMatchId() {
      return this.matchId;
   }

   public void setAttributeSelector(AttributeSelectorType selector) {
      AttributeSelectorType currentSelector = this.getAttributeSelector();
      if (currentSelector != null) {
         this.attributeChoice.remove(currentSelector);
      }

      this.attributeChoice.add(selector);
   }

   public void setAttributeValue(AttributeValueType value) {
      this.attributeValue = (AttributeValueType)this.prepareForAssignment(this.attributeValue, value);
   }

   public void setActionAttributeDesignator(AttributeDesignatorType attribute) {
      AttributeDesignatorType currentDesignator = this.getActionAttributeDesignator();
      if (currentDesignator != null) {
         this.attributeChoice.remove(currentDesignator);
      }

      this.attributeChoice.add(attribute);
   }

   public void setMatchId(String id) {
      this.matchId = this.prepareForAssignment(this.matchId, id);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.add(this.attributeValue);
      if (!this.attributeChoice.isEmpty()) {
         children.addAll(this.attributeChoice);
      }

      return children;
   }
}
