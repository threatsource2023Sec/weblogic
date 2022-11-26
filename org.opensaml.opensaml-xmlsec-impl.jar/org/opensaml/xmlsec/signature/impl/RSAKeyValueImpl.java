package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.xmlsec.signature.Exponent;
import org.opensaml.xmlsec.signature.Modulus;
import org.opensaml.xmlsec.signature.RSAKeyValue;

public class RSAKeyValueImpl extends AbstractXMLObject implements RSAKeyValue {
   private Modulus modulus;
   private Exponent exponent;

   protected RSAKeyValueImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Modulus getModulus() {
      return this.modulus;
   }

   public void setModulus(Modulus newModulus) {
      this.modulus = (Modulus)this.prepareForAssignment(this.modulus, newModulus);
   }

   public Exponent getExponent() {
      return this.exponent;
   }

   public void setExponent(Exponent newExponent) {
      this.exponent = (Exponent)this.prepareForAssignment(this.exponent, newExponent);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.modulus != null) {
         children.add(this.modulus);
      }

      if (this.exponent != null) {
         children.add(this.exponent);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
