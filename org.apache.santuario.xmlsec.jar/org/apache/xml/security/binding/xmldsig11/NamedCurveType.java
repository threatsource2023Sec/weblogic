package org.apache.xml.security.binding.xmldsig11;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "NamedCurveType",
   namespace = "http://www.w3.org/2009/xmldsig11#"
)
public class NamedCurveType {
   @XmlAttribute(
      name = "URI",
      required = true
   )
   @XmlSchemaType(
      name = "anyURI"
   )
   protected String uri;

   public String getURI() {
      return this.uri;
   }

   public void setURI(String value) {
      this.uri = value;
   }
}
