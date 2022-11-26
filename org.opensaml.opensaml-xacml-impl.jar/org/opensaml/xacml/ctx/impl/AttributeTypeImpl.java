package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class AttributeTypeImpl extends AbstractXACMLObject implements AttributeType {
   private String issuer;
   private String attributeID;
   private String datatype;
   private final XMLObjectChildrenList attributeValues = new XMLObjectChildrenList(this);

   protected AttributeTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAttributeId() {
      return this.attributeID;
   }

   public String getDataType() {
      return this.datatype;
   }

   public String getIssuer() {
      return this.issuer;
   }

   public void setAttributeID(String attributeId) {
      this.attributeID = this.prepareForAssignment(this.attributeID, attributeId);
   }

   public void setDataType(String type) {
      this.datatype = this.prepareForAssignment(this.datatype, type);
   }

   public void setIssuer(String iss) {
      this.issuer = this.prepareForAssignment(this.issuer, iss);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.attributeValues);
      return Collections.unmodifiableList(children);
   }

   public List getAttributeValues() {
      return this.attributeValues;
   }
}
