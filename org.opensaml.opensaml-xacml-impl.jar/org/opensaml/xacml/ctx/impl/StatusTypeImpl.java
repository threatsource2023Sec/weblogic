package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.xacml.ctx.StatusCodeType;
import org.opensaml.xacml.ctx.StatusDetailType;
import org.opensaml.xacml.ctx.StatusMessageType;
import org.opensaml.xacml.ctx.StatusType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class StatusTypeImpl extends AbstractXACMLObject implements StatusType {
   private StatusCodeType statusCode;
   private StatusMessageType statusMessage;
   private StatusDetailType statusDetail;

   protected StatusTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public StatusCodeType getStatusCode() {
      return this.statusCode;
   }

   public void setStatusCode(StatusCodeType newStatusCode) {
      this.statusCode = (StatusCodeType)this.prepareForAssignment(this.statusCode, newStatusCode);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.statusCode != null) {
         children.add(this.statusCode);
      }

      if (this.statusMessage != null) {
         children.add(this.statusMessage);
      }

      if (this.statusDetail != null) {
         children.add(this.statusDetail);
      }

      return Collections.unmodifiableList(children);
   }

   public StatusMessageType getStatusMessage() {
      return this.statusMessage;
   }

   public void setStatusMessage(StatusMessageType newStatusMessage) {
      this.statusMessage = (StatusMessageType)this.prepareForAssignment(this.statusMessage, newStatusMessage);
   }

   public StatusDetailType getStatusDetail() {
      return this.statusDetail;
   }

   public void setStatusDetail(StatusDetailType newStatusDetail) {
      this.statusDetail = (StatusDetailType)this.prepareForAssignment(this.statusDetail, newStatusDetail);
   }
}
