package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.Status;
import org.opensaml.saml.saml2.core.StatusCode;
import org.opensaml.saml.saml2.core.StatusDetail;
import org.opensaml.saml.saml2.core.StatusMessage;

public class StatusImpl extends AbstractSAMLObject implements Status {
   private StatusCode statusCode;
   private StatusMessage statusMessage;
   private StatusDetail statusDetail;

   protected StatusImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public StatusCode getStatusCode() {
      return this.statusCode;
   }

   public void setStatusCode(StatusCode newStatusCode) {
      this.statusCode = (StatusCode)this.prepareForAssignment(this.statusCode, newStatusCode);
   }

   public StatusMessage getStatusMessage() {
      return this.statusMessage;
   }

   public void setStatusMessage(StatusMessage newStatusMessage) {
      this.statusMessage = (StatusMessage)this.prepareForAssignment(this.getStatusMessage(), newStatusMessage);
   }

   public StatusDetail getStatusDetail() {
      return this.statusDetail;
   }

   public void setStatusDetail(StatusDetail newStatusDetail) {
      this.statusDetail = (StatusDetail)this.prepareForAssignment(this.statusDetail, newStatusDetail);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.add(this.statusCode);
      if (this.statusMessage != null) {
         children.add(this.statusMessage);
      }

      if (this.statusDetail != null) {
         children.add(this.statusDetail);
      }

      return Collections.unmodifiableList(children);
   }
}
