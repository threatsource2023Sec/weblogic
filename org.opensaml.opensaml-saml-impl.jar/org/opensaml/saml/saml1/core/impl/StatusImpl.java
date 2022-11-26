package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.Status;
import org.opensaml.saml.saml1.core.StatusCode;
import org.opensaml.saml.saml1.core.StatusDetail;
import org.opensaml.saml.saml1.core.StatusMessage;

public class StatusImpl extends AbstractSAMLObject implements Status {
   private StatusMessage statusMessage;
   private StatusCode statusCode;
   private StatusDetail statusDetail;

   protected StatusImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public StatusMessage getStatusMessage() {
      return this.statusMessage;
   }

   public void setStatusMessage(StatusMessage message) {
      this.statusMessage = (StatusMessage)this.prepareForAssignment(this.statusMessage, message);
   }

   public StatusCode getStatusCode() {
      return this.statusCode;
   }

   public void setStatusCode(StatusCode code) {
      this.statusCode = (StatusCode)this.prepareForAssignment(this.statusCode, code);
   }

   public StatusDetail getStatusDetail() {
      return this.statusDetail;
   }

   public void setStatusDetail(StatusDetail detail) {
      this.statusDetail = (StatusDetail)this.prepareForAssignment(this.statusDetail, detail);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList(3);
      if (this.statusCode != null) {
         children.add(this.statusCode);
      }

      if (this.statusMessage != null) {
         children.add(this.statusMessage);
      }

      if (this.statusDetail != null) {
         children.add(this.statusDetail);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
