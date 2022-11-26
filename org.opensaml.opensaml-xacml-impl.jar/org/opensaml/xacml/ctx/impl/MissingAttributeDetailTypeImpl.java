package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.ctx.MissingAttributeDetailType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class MissingAttributeDetailTypeImpl extends AbstractXACMLObject implements MissingAttributeDetailType {
   private XMLObjectChildrenList attributeValues = new XMLObjectChildrenList(this);
   private String attributeId;
   private String dataType;
   private String issuer;

   protected MissingAttributeDetailTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAttributeId() {
      return this.attributeId;
   }

   public List getAttributeValues() {
      return this.attributeValues;
   }

   public String getDataType() {
      return this.dataType;
   }

   public String getIssuer() {
      return this.issuer;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.attributeValues);
      return Collections.unmodifiableList(children);
   }

   public void setAttributeId(String id) {
      this.attributeId = this.prepareForAssignment(this.attributeId, id);
   }

   public void setDataType(String type) {
      this.dataType = this.prepareForAssignment(this.dataType, type);
   }

   public void setIssuer(String iss) {
      this.issuer = this.prepareForAssignment(this.issuer, iss);
   }
}
