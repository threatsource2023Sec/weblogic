package org.apache.xml.security.binding.xmldsig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "CanonicalizationMethodType",
   namespace = "http://www.w3.org/2000/09/xmldsig#",
   propOrder = {"content"}
)
public class CanonicalizationMethodType {
   @XmlMixed
   @XmlAnyElement(
      lax = true
   )
   protected List content;
   @XmlAttribute(
      name = "Algorithm",
      required = true
   )
   @XmlSchemaType(
      name = "anyURI"
   )
   protected String algorithm;

   public List getContent() {
      if (this.content == null) {
         this.content = new ArrayList();
      }

      return this.content;
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public void setAlgorithm(String value) {
      this.algorithm = value;
   }
}
