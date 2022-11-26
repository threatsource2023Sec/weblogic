package org.opensaml.soap.wstrust.impl;

import java.util.List;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.soap.wstrust.Renewing;

public class RenewingImpl extends AbstractWSTrustObject implements Renewing {
   private XSBooleanValue allow;
   private XSBooleanValue ok;

   public RenewingImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Boolean isAllow() {
      return this.allow != null ? this.allow.getValue() : Boolean.TRUE;
   }

   public XSBooleanValue isAllowXSBoolean() {
      return this.allow;
   }

   public void setAllow(Boolean newAllow) {
      if (newAllow != null) {
         this.allow = (XSBooleanValue)this.prepareForAssignment(this.allow, new XSBooleanValue(newAllow, false));
      } else {
         this.allow = (XSBooleanValue)this.prepareForAssignment(this.allow, (Object)null);
      }

   }

   public void setAllow(XSBooleanValue newAllow) {
      this.allow = (XSBooleanValue)this.prepareForAssignment(this.allow, newAllow);
   }

   public Boolean isOK() {
      return this.ok != null ? this.ok.getValue() : Boolean.FALSE;
   }

   public XSBooleanValue isOKXSBoolean() {
      return this.ok;
   }

   public void setOK(Boolean newOK) {
      if (newOK != null) {
         this.ok = (XSBooleanValue)this.prepareForAssignment(this.ok, new XSBooleanValue(newOK, false));
      } else {
         this.ok = (XSBooleanValue)this.prepareForAssignment(this.ok, (Object)null);
      }

   }

   public void setOK(XSBooleanValue newOK) {
      this.ok = (XSBooleanValue)this.prepareForAssignment(this.ok, newOK);
   }

   public List getOrderedChildren() {
      return null;
   }
}
