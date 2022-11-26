package org.apache.xml.security.binding.xmldsig11;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "FieldIDType",
   namespace = "http://www.w3.org/2009/xmldsig11#",
   propOrder = {"prime", "tnB", "pnB", "gnB", "any"}
)
public class FieldIDType {
   @XmlElement(
      name = "Prime",
      namespace = "http://www.w3.org/2009/xmldsig11#"
   )
   protected PrimeFieldParamsType prime;
   @XmlElement(
      name = "TnB",
      namespace = "http://www.w3.org/2009/xmldsig11#"
   )
   protected TnBFieldParamsType tnB;
   @XmlElement(
      name = "PnB",
      namespace = "http://www.w3.org/2009/xmldsig11#"
   )
   protected PnBFieldParamsType pnB;
   @XmlElement(
      name = "GnB",
      namespace = "http://www.w3.org/2009/xmldsig11#"
   )
   protected CharTwoFieldParamsType gnB;
   @XmlAnyElement(
      lax = true
   )
   protected Object any;

   public PrimeFieldParamsType getPrime() {
      return this.prime;
   }

   public void setPrime(PrimeFieldParamsType value) {
      this.prime = value;
   }

   public TnBFieldParamsType getTnB() {
      return this.tnB;
   }

   public void setTnB(TnBFieldParamsType value) {
      this.tnB = value;
   }

   public PnBFieldParamsType getPnB() {
      return this.pnB;
   }

   public void setPnB(PnBFieldParamsType value) {
      this.pnB = value;
   }

   public CharTwoFieldParamsType getGnB() {
      return this.gnB;
   }

   public void setGnB(CharTwoFieldParamsType value) {
      this.gnB = value;
   }

   public Object getAny() {
      return this.any;
   }

   public void setAny(Object value) {
      this.any = value;
   }
}
