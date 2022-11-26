package org.apache.xml.security.binding.xmlenc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "EncryptionPropertiesType",
   namespace = "http://www.w3.org/2001/04/xmlenc#",
   propOrder = {"encryptionProperty"}
)
public class EncryptionPropertiesType {
   @XmlElement(
      name = "EncryptionProperty",
      namespace = "http://www.w3.org/2001/04/xmlenc#",
      required = true
   )
   protected List encryptionProperty;
   @XmlAttribute(
      name = "Id"
   )
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlID
   @XmlSchemaType(
      name = "ID"
   )
   protected String id;

   public List getEncryptionProperty() {
      if (this.encryptionProperty == null) {
         this.encryptionProperty = new ArrayList();
      }

      return this.encryptionProperty;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String value) {
      this.id = value;
   }
}
