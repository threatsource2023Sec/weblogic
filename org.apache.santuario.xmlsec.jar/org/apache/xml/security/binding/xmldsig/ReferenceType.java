package org.apache.xml.security.binding.xmldsig;

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
   name = "ReferenceType",
   namespace = "http://www.w3.org/2000/09/xmldsig#",
   propOrder = {"transforms", "digestMethod", "digestValue"}
)
public class ReferenceType {
   @XmlElement(
      name = "Transforms",
      namespace = "http://www.w3.org/2000/09/xmldsig#"
   )
   protected TransformsType transforms;
   @XmlElement(
      name = "DigestMethod",
      namespace = "http://www.w3.org/2000/09/xmldsig#",
      required = true
   )
   protected DigestMethodType digestMethod;
   @XmlElement(
      name = "DigestValue",
      namespace = "http://www.w3.org/2000/09/xmldsig#",
      required = true
   )
   protected byte[] digestValue;
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

   public DigestMethodType getDigestMethod() {
      return this.digestMethod;
   }

   public void setDigestMethod(DigestMethodType value) {
      this.digestMethod = value;
   }

   public byte[] getDigestValue() {
      return this.digestValue;
   }

   public void setDigestValue(byte[] value) {
      this.digestValue = value;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String value) {
      this.id = value;
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
