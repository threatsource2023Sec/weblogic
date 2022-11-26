package org.apache.xml.security.binding.xmldsig11;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "ECValidationDataType",
   namespace = "http://www.w3.org/2009/xmldsig11#",
   propOrder = {"seed"}
)
public class ECValidationDataType {
   @XmlElement(
      namespace = "http://www.w3.org/2009/xmldsig11#",
      required = true
   )
   protected byte[] seed;
   @XmlAttribute(
      name = "hashAlgorithm",
      required = true
   )
   @XmlSchemaType(
      name = "anyURI"
   )
   protected String hashAlgorithm;

   public byte[] getSeed() {
      return this.seed;
   }

   public void setSeed(byte[] value) {
      this.seed = value;
   }

   public String getHashAlgorithm() {
      return this.hashAlgorithm;
   }

   public void setHashAlgorithm(String value) {
      this.hashAlgorithm = value;
   }
}
