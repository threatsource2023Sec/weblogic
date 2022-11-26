package org.apache.xml.security.binding.xmlenc11;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.xml.security.binding.xmlenc.ReferenceList;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "DerivedKeyType",
   namespace = "http://www.w3.org/2009/xmlenc11#",
   propOrder = {"keyDerivationMethod", "referenceList", "derivedKeyName", "masterKeyName"}
)
public class DerivedKeyType {
   @XmlElement(
      name = "KeyDerivationMethod",
      namespace = "http://www.w3.org/2009/xmlenc11#"
   )
   protected KeyDerivationMethodType keyDerivationMethod;
   @XmlElement(
      name = "ReferenceList",
      namespace = "http://www.w3.org/2001/04/xmlenc#"
   )
   protected ReferenceList referenceList;
   @XmlElement(
      name = "DerivedKeyName",
      namespace = "http://www.w3.org/2009/xmlenc11#"
   )
   protected String derivedKeyName;
   @XmlElement(
      name = "MasterKeyName",
      namespace = "http://www.w3.org/2009/xmlenc11#"
   )
   protected String masterKeyName;
   @XmlAttribute(
      name = "Recipient"
   )
   protected String recipient;
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

   public KeyDerivationMethodType getKeyDerivationMethod() {
      return this.keyDerivationMethod;
   }

   public void setKeyDerivationMethod(KeyDerivationMethodType value) {
      this.keyDerivationMethod = value;
   }

   public ReferenceList getReferenceList() {
      return this.referenceList;
   }

   public void setReferenceList(ReferenceList value) {
      this.referenceList = value;
   }

   public String getDerivedKeyName() {
      return this.derivedKeyName;
   }

   public void setDerivedKeyName(String value) {
      this.derivedKeyName = value;
   }

   public String getMasterKeyName() {
      return this.masterKeyName;
   }

   public void setMasterKeyName(String value) {
      this.masterKeyName = value;
   }

   public String getRecipient() {
      return this.recipient;
   }

   public void setRecipient(String value) {
      this.recipient = value;
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
}
