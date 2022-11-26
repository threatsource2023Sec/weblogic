package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.StatusCode;

public class StatusCodeImpl extends AbstractSAMLObject implements StatusCode {
   private String value;
   private StatusCode childStatusCode;

   protected StatusCodeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public StatusCode getStatusCode() {
      return this.childStatusCode;
   }

   public void setStatusCode(StatusCode newStatusCode) {
      this.childStatusCode = (StatusCode)this.prepareForAssignment(this.childStatusCode, newStatusCode);
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String newValue) {
      this.value = this.prepareForAssignment(this.value, newValue);
   }

   public List getOrderedChildren() {
      if (this.childStatusCode != null) {
         ArrayList children = new ArrayList();
         children.add(this.childStatusCode);
         return Collections.unmodifiableList(children);
      } else {
         return null;
      }
   }
}
