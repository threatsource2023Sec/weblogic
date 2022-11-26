package org.opensaml.xmlsec.signature.impl;

import java.math.BigInteger;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.xmlsec.signature.X509SerialNumber;

public class X509SerialNumberImpl extends AbstractXMLObject implements X509SerialNumber {
   private BigInteger value;

   protected X509SerialNumberImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public BigInteger getValue() {
      return this.value;
   }

   public void setValue(BigInteger newValue) {
      this.value = (BigInteger)this.prepareForAssignment(this.value, newValue);
   }

   public List getOrderedChildren() {
      return null;
   }
}
