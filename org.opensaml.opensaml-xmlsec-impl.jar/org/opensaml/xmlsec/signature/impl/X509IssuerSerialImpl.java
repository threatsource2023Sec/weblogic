package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.xmlsec.signature.X509IssuerName;
import org.opensaml.xmlsec.signature.X509IssuerSerial;
import org.opensaml.xmlsec.signature.X509SerialNumber;

public class X509IssuerSerialImpl extends AbstractXMLObject implements X509IssuerSerial {
   private X509IssuerName issuerName;
   private X509SerialNumber serialNumber;

   protected X509IssuerSerialImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public X509IssuerName getX509IssuerName() {
      return this.issuerName;
   }

   public void setX509IssuerName(X509IssuerName newX509IssuerName) {
      this.issuerName = (X509IssuerName)this.prepareForAssignment(this.issuerName, newX509IssuerName);
   }

   public X509SerialNumber getX509SerialNumber() {
      return this.serialNumber;
   }

   public void setX509SerialNumber(X509SerialNumber newX509SerialNumber) {
      this.serialNumber = (X509SerialNumber)this.prepareForAssignment(this.serialNumber, newX509SerialNumber);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.issuerName != null) {
         children.add(this.issuerName);
      }

      if (this.serialNumber != null) {
         children.add(this.serialNumber);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
