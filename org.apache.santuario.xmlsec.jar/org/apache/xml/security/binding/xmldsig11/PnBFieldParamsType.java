package org.apache.xml.security.binding.xmldsig11;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "PnBFieldParamsType",
   namespace = "http://www.w3.org/2009/xmldsig11#",
   propOrder = {"k1", "k2", "k3"}
)
public class PnBFieldParamsType extends CharTwoFieldParamsType {
   @XmlElement(
      name = "K1",
      namespace = "http://www.w3.org/2009/xmldsig11#",
      required = true
   )
   @XmlSchemaType(
      name = "positiveInteger"
   )
   protected BigInteger k1;
   @XmlElement(
      name = "K2",
      namespace = "http://www.w3.org/2009/xmldsig11#",
      required = true
   )
   @XmlSchemaType(
      name = "positiveInteger"
   )
   protected BigInteger k2;
   @XmlElement(
      name = "K3",
      namespace = "http://www.w3.org/2009/xmldsig11#",
      required = true
   )
   @XmlSchemaType(
      name = "positiveInteger"
   )
   protected BigInteger k3;

   public BigInteger getK1() {
      return this.k1;
   }

   public void setK1(BigInteger value) {
      this.k1 = value;
   }

   public BigInteger getK2() {
      return this.k2;
   }

   public void setK2(BigInteger value) {
      this.k2 = value;
   }

   public BigInteger getK3() {
      return this.k3;
   }

   public void setK3(BigInteger value) {
      this.k3 = value;
   }
}
