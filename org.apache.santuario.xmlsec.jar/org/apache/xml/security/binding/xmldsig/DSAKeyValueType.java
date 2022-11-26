package org.apache.xml.security.binding.xmldsig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "DSAKeyValueType",
   namespace = "http://www.w3.org/2000/09/xmldsig#",
   propOrder = {"p", "q", "g", "y", "j", "seed", "pgenCounter"}
)
public class DSAKeyValueType {
   @XmlElement(
      name = "P",
      namespace = "http://www.w3.org/2000/09/xmldsig#"
   )
   protected byte[] p;
   @XmlElement(
      name = "Q",
      namespace = "http://www.w3.org/2000/09/xmldsig#"
   )
   protected byte[] q;
   @XmlElement(
      name = "G",
      namespace = "http://www.w3.org/2000/09/xmldsig#"
   )
   protected byte[] g;
   @XmlElement(
      name = "Y",
      namespace = "http://www.w3.org/2000/09/xmldsig#",
      required = true
   )
   protected byte[] y;
   @XmlElement(
      name = "J",
      namespace = "http://www.w3.org/2000/09/xmldsig#"
   )
   protected byte[] j;
   @XmlElement(
      name = "Seed",
      namespace = "http://www.w3.org/2000/09/xmldsig#"
   )
   protected byte[] seed;
   @XmlElement(
      name = "PgenCounter",
      namespace = "http://www.w3.org/2000/09/xmldsig#"
   )
   protected byte[] pgenCounter;

   public byte[] getP() {
      return this.p;
   }

   public void setP(byte[] value) {
      this.p = value;
   }

   public byte[] getQ() {
      return this.q;
   }

   public void setQ(byte[] value) {
      this.q = value;
   }

   public byte[] getG() {
      return this.g;
   }

   public void setG(byte[] value) {
      this.g = value;
   }

   public byte[] getY() {
      return this.y;
   }

   public void setY(byte[] value) {
      this.y = value;
   }

   public byte[] getJ() {
      return this.j;
   }

   public void setJ(byte[] value) {
      this.j = value;
   }

   public byte[] getSeed() {
      return this.seed;
   }

   public void setSeed(byte[] value) {
      this.seed = value;
   }

   public byte[] getPgenCounter() {
      return this.pgenCounter;
   }

   public void setPgenCounter(byte[] value) {
      this.pgenCounter = value;
   }
}
