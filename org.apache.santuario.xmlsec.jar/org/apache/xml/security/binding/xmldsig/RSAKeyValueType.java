package org.apache.xml.security.binding.xmldsig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "RSAKeyValueType",
   namespace = "http://www.w3.org/2000/09/xmldsig#",
   propOrder = {"modulus", "exponent"}
)
public class RSAKeyValueType {
   @XmlElement(
      name = "Modulus",
      namespace = "http://www.w3.org/2000/09/xmldsig#",
      required = true
   )
   protected byte[] modulus;
   @XmlElement(
      name = "Exponent",
      namespace = "http://www.w3.org/2000/09/xmldsig#",
      required = true
   )
   protected byte[] exponent;

   public byte[] getModulus() {
      return this.modulus;
   }

   public void setModulus(byte[] value) {
      this.modulus = value;
   }

   public byte[] getExponent() {
      return this.exponent;
   }

   public void setExponent(byte[] value) {
      this.exponent = value;
   }
}
