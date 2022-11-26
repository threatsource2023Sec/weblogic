package org.apache.xml.security.binding.xmldsig;

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
   name = "SignaturePropertiesType",
   namespace = "http://www.w3.org/2000/09/xmldsig#",
   propOrder = {"signatureProperty"}
)
public class SignaturePropertiesType {
   @XmlElement(
      name = "SignatureProperty",
      namespace = "http://www.w3.org/2000/09/xmldsig#",
      required = true
   )
   protected List signatureProperty;
   @XmlAttribute(
      name = "Id"
   )
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlID
   @XmlSchemaType(
      name = "ID"
   )
   protected String id;

   public List getSignatureProperty() {
      if (this.signatureProperty == null) {
         this.signatureProperty = new ArrayList();
      }

      return this.signatureProperty;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String value) {
      this.id = value;
   }
}
