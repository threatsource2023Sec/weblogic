package org.apache.xml.security.binding.xmlenc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.xml.security.binding.xmldsig.KeyInfoType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "EncryptedType",
   namespace = "http://www.w3.org/2001/04/xmlenc#",
   propOrder = {"encryptionMethod", "keyInfo", "cipherData", "encryptionProperties"}
)
@XmlSeeAlso({EncryptedDataType.class, EncryptedKeyType.class})
public abstract class EncryptedType {
   @XmlElement(
      name = "EncryptionMethod",
      namespace = "http://www.w3.org/2001/04/xmlenc#"
   )
   protected EncryptionMethodType encryptionMethod;
   @XmlElement(
      name = "KeyInfo",
      namespace = "http://www.w3.org/2000/09/xmldsig#"
   )
   protected KeyInfoType keyInfo;
   @XmlElement(
      name = "CipherData",
      namespace = "http://www.w3.org/2001/04/xmlenc#",
      required = true
   )
   protected CipherDataType cipherData;
   @XmlElement(
      name = "EncryptionProperties",
      namespace = "http://www.w3.org/2001/04/xmlenc#"
   )
   protected EncryptionPropertiesType encryptionProperties;
   @XmlAttribute(
      name = "Id"
   )
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlID
   @XmlSchemaType(
      name = "ID"
   )
   protected String id;
   @XmlAttribute(
      name = "Type"
   )
   @XmlSchemaType(
      name = "anyURI"
   )
   protected String type;
   @XmlAttribute(
      name = "MimeType"
   )
   protected String mimeType;
   @XmlAttribute(
      name = "Encoding"
   )
   @XmlSchemaType(
      name = "anyURI"
   )
   protected String encoding;

   public EncryptionMethodType getEncryptionMethod() {
      return this.encryptionMethod;
   }

   public void setEncryptionMethod(EncryptionMethodType value) {
      this.encryptionMethod = value;
   }

   public KeyInfoType getKeyInfo() {
      return this.keyInfo;
   }

   public void setKeyInfo(KeyInfoType value) {
      this.keyInfo = value;
   }

   public CipherDataType getCipherData() {
      return this.cipherData;
   }

   public void setCipherData(CipherDataType value) {
      this.cipherData = value;
   }

   public EncryptionPropertiesType getEncryptionProperties() {
      return this.encryptionProperties;
   }

   public void setEncryptionProperties(EncryptionPropertiesType value) {
      this.encryptionProperties = value;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String value) {
      this.id = value;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
   }

   public String getMimeType() {
      return this.mimeType;
   }

   public void setMimeType(String value) {
      this.mimeType = value;
   }

   public String getEncoding() {
      return this.encoding;
   }

   public void setEncoding(String value) {
      this.encoding = value;
   }
}
