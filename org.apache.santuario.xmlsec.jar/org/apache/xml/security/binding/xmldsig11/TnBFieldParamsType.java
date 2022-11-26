package org.apache.xml.security.binding.xmldsig11;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "TnBFieldParamsType",
   namespace = "http://www.w3.org/2009/xmldsig11#",
   propOrder = {"k"}
)
public class TnBFieldParamsType extends CharTwoFieldParamsType {
   @XmlElement(
      name = "K",
      namespace = "http://www.w3.org/2009/xmldsig11#",
      required = true
   )
   @XmlSchemaType(
      name = "positiveInteger"
   )
   protected BigInteger k;

   public BigInteger getK() {
      return this.k;
   }

   public void setK(BigInteger value) {
      this.k = value;
   }
}
