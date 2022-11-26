package org.opensaml.soap.wstrust.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.soap.wstrust.Code;
import org.opensaml.soap.wstrust.Reason;
import org.opensaml.soap.wstrust.Status;

public class StatusImpl extends AbstractWSTrustObject implements Status {
   private Code code;
   private Reason reason;

   public StatusImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Code getCode() {
      return this.code;
   }

   public Reason getReason() {
      return this.reason;
   }

   public void setCode(Code newCode) {
      this.code = (Code)this.prepareForAssignment(this.code, newCode);
   }

   public void setReason(Reason newReason) {
      this.reason = (Reason)this.prepareForAssignment(this.reason, newReason);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.code != null) {
         children.add(this.code);
      }

      if (this.reason != null) {
         children.add(this.reason);
      }

      return Collections.unmodifiableList(children);
   }
}
