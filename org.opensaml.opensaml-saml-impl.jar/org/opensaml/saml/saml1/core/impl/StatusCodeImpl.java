package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.StatusCode;

public class StatusCodeImpl extends AbstractSAMLObject implements StatusCode {
   private QName value;
   private StatusCode childStatusCode;

   protected StatusCodeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public QName getValue() {
      return this.value;
   }

   public void setValue(QName newValue) {
      this.value = this.prepareAttributeValueForAssignment("Value", this.value, newValue);
   }

   public StatusCode getStatusCode() {
      return this.childStatusCode;
   }

   public void setStatusCode(StatusCode statusCode) {
      this.childStatusCode = (StatusCode)this.prepareForAssignment(this.childStatusCode, statusCode);
   }

   public List getOrderedChildren() {
      if (this.childStatusCode != null) {
         ArrayList contents = new ArrayList(1);
         contents.add(this.childStatusCode);
         return Collections.unmodifiableList(contents);
      } else {
         return null;
      }
   }
}
