package org.apache.xml.security.binding.xmldsig11;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "CharTwoFieldParamsType",
   namespace = "http://www.w3.org/2009/xmldsig11#",
   propOrder = {"m"}
)
@XmlSeeAlso({TnBFieldParamsType.class, PnBFieldParamsType.class})
public class CharTwoFieldParamsType {
   @XmlElement(
      name = "M",
      namespace = "http://www.w3.org/2009/xmldsig11#",
      required = true
   )
   @XmlSchemaType(
      name = "positiveInteger"
   )
   protected BigInteger m;

   public BigInteger getM() {
      return this.m;
   }

   public void setM(BigInteger value) {
      this.m = value;
   }
}
