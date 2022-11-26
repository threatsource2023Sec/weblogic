package org.opensaml.xacml.policy.impl;

import java.util.List;
import net.shibboleth.utilities.java.support.collection.LazyList;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.AttributeSelectorType;

public class AttributeSelectorTypeImpl extends AbstractXACMLObject implements AttributeSelectorType {
   private String dataType;
   private String requestContextPath;
   private XSBooleanValue mustBePresentXS = XSBooleanValue.valueOf("false");

   protected AttributeSelectorTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getDataType() {
      return this.dataType;
   }

   public Boolean getMustBePresent() {
      return this.mustBePresentXS != null ? this.mustBePresentXS.getValue() : Boolean.FALSE;
   }

   public XSBooleanValue getMustBePresentXSBoolean() {
      return this.mustBePresentXS;
   }

   public String getRequestContextPath() {
      return this.requestContextPath;
   }

   public void setDataType(String type) {
      this.dataType = this.prepareForAssignment(this.dataType, type);
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

   public void setRequestContextPath(String path) {
      this.requestContextPath = this.prepareForAssignment(this.requestContextPath, path);
   }

   public List getOrderedChildren() {
      return new LazyList();
   }
}
