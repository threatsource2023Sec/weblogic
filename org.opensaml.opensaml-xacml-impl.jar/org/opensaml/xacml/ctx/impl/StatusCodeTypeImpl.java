package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.xacml.ctx.StatusCodeType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class StatusCodeTypeImpl extends AbstractXACMLObject implements StatusCodeType {
   private StatusCodeType statusCode;
   private String value;

   protected StatusCodeTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.statusCode != null) {
         children.add(this.statusCode);
      }

      return Collections.unmodifiableList(children);
   }

   public StatusCodeType getStatusCode() {
      return this.statusCode;
   }

   public String getValue() {
      return this.value;
   }

   public void setStatusCode(StatusCodeType code) {
      this.statusCode = (StatusCodeType)this.prepareForAssignment(this.statusCode, code);
   }

   public void setValue(String newValue) {
      this.value = this.prepareForAssignment(this.value, newValue);
   }
}
