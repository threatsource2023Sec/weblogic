package org.apache.xml.security.binding.xmlenc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "ReferenceType",
   namespace = "http://www.w3.org/2001/04/xmlenc#",
   propOrder = {"any"}
)
public class ReferenceType {
   @XmlAnyElement(
      lax = true
   )
   protected List any;
   @XmlAttribute(
      name = "URI",
      required = true
   )
   @XmlSchemaType(
      name = "anyURI"
   )
   protected String uri;

   public List getAny() {
      if (this.any == null) {
         this.any = new ArrayList();
      }

      return this.any;
   }

   public String getURI() {
      return this.uri;
   }

   public void setURI(String value) {
      this.uri = value;
   }
}
