package org.opensaml.xacml.policy.impl;

import java.util.List;
import net.shibboleth.utilities.java.support.collection.LazyList;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.AttributeDesignatorType;

public class AttributeDesignatorTypeImpl extends AbstractXACMLObject implements AttributeDesignatorType {
   private String attributeId;
   private String dataType;
   private String issuer;
   private XSBooleanValue mustBePresentXS = XSBooleanValue.valueOf("false");

   protected AttributeDesignatorTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAttributeId() {
      return this.attributeId;
   }

   public String getDataType() {
      return this.dataType;
   }

   public String getIssuer() {
      return this.issuer;
   }

   public XSBooleanValue getMustBePresentXSBoolean() {
      return this.mustBePresentXS;
   }

   public Boolean getMustBePresent() {
      return this.mustBePresentXS != null ? this.mustBePresentXS.getValue() : Boolean.FALSE;
   }

   public void setAttributeId(String id) {
      this.attributeId = this.prepareForAssignment(this.attributeId, id);
   }

   public void setDataType(String type) {
      this.dataType = this.prepareForAssignment(this.dataType, type);
   }

   public void setIssuer(String newIssuer) {
      this.issuer = this.prepareForAssignment(this.issuer, newIssuer);
   }

   public void setMustBePresentXSBoolean(XSBooleanValue present) {
      this.mustBePresentXS = (XSBooleanValue)this.prepareForAssignment(this.mustBePresentXS, present);
   }

   public void setMustBePresent(Boolean present) {
      if (present != null) {
         this.mustBePresentXS = (XSBooleanValue)this.prepareForAssignment(this.mustBePresentXS, new XSBooleanValue(present, false));
      } else {
         this.mustBePresentXS = (XSBooleanValue)this.prepareForAssignment(this.mustBePresentXS, (Object)null);
      }

   }

   public List getOrderedChildren() {
      return new LazyList();
   }
}
