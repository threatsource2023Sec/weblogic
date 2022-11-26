package org.apache.xml.security.binding.xmldsig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "RetrievalMethodType",
   namespace = "http://www.w3.org/2000/09/xmldsig#",
   propOrder = {"transforms"}
)
public class RetrievalMethodType {
   @XmlElement(
      name = "Transforms",
      namespace = "http://www.w3.org/2000/09/xmldsig#"
   )
   protected TransformsType transforms;
   @XmlAttribute(
      name = "URI"
   )
   @XmlSchemaType(
      name = "anyURI"
   )
   protected String uri;
   @XmlAttribute(
      name = "Type"
   )
   @XmlSchemaType(
      name = "anyURI"
   )
   protected String type;

   public TransformsType getTransforms() {
      return this.transforms;
   }

   public void setTransforms(TransformsType value) {
      this.transforms = value;
   }

   public String getURI() {
      return this.uri;
   }

   public void setURI(String value) {
      this.uri = value;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
   }
}
