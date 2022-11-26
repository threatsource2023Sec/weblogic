package org.apache.xml.security.binding.xmlenc11;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "PBKDF2ParameterType",
   namespace = "http://www.w3.org/2009/xmlenc11#",
   propOrder = {"salt", "iterationCount", "keyLength", "prf"}
)
public class PBKDF2ParameterType {
   @XmlElement(
      name = "Salt",
      namespace = "http://www.w3.org/2009/xmlenc11#",
      required = true
   )
   protected Salt salt;
   @XmlElement(
      name = "IterationCount",
      namespace = "http://www.w3.org/2009/xmlenc11#",
      required = true
   )
   @XmlSchemaType(
      name = "positiveInteger"
   )
   protected BigInteger iterationCount;
   @XmlElement(
      name = "KeyLength",
      namespace = "http://www.w3.org/2009/xmlenc11#",
      required = true
   )
   @XmlSchemaType(
      name = "positiveInteger"
   )
   protected BigInteger keyLength;
   @XmlElement(
      name = "PRF",
      namespace = "http://www.w3.org/2009/xmlenc11#",
      required = true
   )
   protected PRFAlgorithmIdentifierType prf;

   public Salt getSalt() {
      return this.salt;
   }

   public void setSalt(Salt value) {
      this.salt = value;
   }

   public BigInteger getIterationCount() {
      return this.iterationCount;
   }

   public void setIterationCount(BigInteger value) {
      this.iterationCount = value;
   }

   public BigInteger getKeyLength() {
      return this.keyLength;
   }

   public void setKeyLength(BigInteger value) {
      this.keyLength = value;
   }

   public PRFAlgorithmIdentifierType getPRF() {
      return this.prf;
   }

   public void setPRF(PRFAlgorithmIdentifierType value) {
      this.prf = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(
      name = "",
      propOrder = {"specified", "otherSource"}
   )
   public static class Salt {
      @XmlElement(
         name = "Specified",
         namespace = "http://www.w3.org/2009/xmlenc11#"
      )
      protected byte[] specified;
      @XmlElement(
         name = "OtherSource",
         namespace = "http://www.w3.org/2009/xmlenc11#"
      )
      protected AlgorithmIdentifierType otherSource;

      public byte[] getSpecified() {
         return this.specified;
      }

      public void setSpecified(byte[] value) {
         this.specified = value;
      }

      public AlgorithmIdentifierType getOtherSource() {
         return this.otherSource;
      }

      public void setOtherSource(AlgorithmIdentifierType value) {
         this.otherSource = value;
      }
   }
}
