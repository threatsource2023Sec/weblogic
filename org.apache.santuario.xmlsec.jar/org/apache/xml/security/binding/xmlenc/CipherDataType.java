package org.apache.xml.security.binding.xmlenc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "CipherDataType",
   namespace = "http://www.w3.org/2001/04/xmlenc#",
   propOrder = {"cipherValue", "cipherReference"}
)
public class CipherDataType {
   @XmlElement(
      name = "CipherValue",
      namespace = "http://www.w3.org/2001/04/xmlenc#"
   )
   protected CipherValueType cipherValue;
   @XmlElement(
      name = "CipherReference",
      namespace = "http://www.w3.org/2001/04/xmlenc#"
   )
   protected CipherReferenceType cipherReference;

   public CipherValueType getCipherValue() {
      return this.cipherValue;
   }

   public void setCipherValue(CipherValueType value) {
      this.cipherValue = value;
   }

   public CipherReferenceType getCipherReference() {
      return this.cipherReference;
   }

   public void setCipherReference(CipherReferenceType value) {
      this.cipherReference = value;
   }
}
