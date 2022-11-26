package org.apache.xml.security.binding.xmldsig11;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "PrimeFieldParamsType",
   namespace = "http://www.w3.org/2009/xmldsig11#",
   propOrder = {"p"}
)
public class PrimeFieldParamsType {
   @XmlElement(
      name = "P",
      namespace = "http://www.w3.org/2009/xmldsig11#",
      required = true
   )
   protected byte[] p;

   public byte[] getP() {
      return this.p;
   }

   public void setP(byte[] value) {
      this.p = value;
   }
}
