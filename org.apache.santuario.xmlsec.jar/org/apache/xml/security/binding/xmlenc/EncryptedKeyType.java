package org.apache.xml.security.binding.xmlenc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "EncryptedKeyType",
   namespace = "http://www.w3.org/2001/04/xmlenc#",
   propOrder = {"referenceList", "carriedKeyName"}
)
public class EncryptedKeyType extends EncryptedType {
   @XmlElement(
      name = "ReferenceList",
      namespace = "http://www.w3.org/2001/04/xmlenc#"
   )
   protected ReferenceList referenceList;
   @XmlElement(
      name = "CarriedKeyName",
      namespace = "http://www.w3.org/2001/04/xmlenc#"
   )
   protected String carriedKeyName;
   @XmlAttribute(
      name = "Recipient"
   )
   protected String recipient;

   public ReferenceList getReferenceList() {
      return this.referenceList;
   }

   public void setReferenceList(ReferenceList value) {
      this.referenceList = value;
   }

   public String getCarriedKeyName() {
      return this.carriedKeyName;
   }

   public void setCarriedKeyName(String value) {
      this.carriedKeyName = value;
   }

   public String getRecipient() {
      return this.recipient;
   }

   public void setRecipient(String value) {
      this.recipient = value;
   }
}
