package org.apache.xml.security.binding.xmlenc11;

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
   name = "KeyDerivationMethodType",
   namespace = "http://www.w3.org/2009/xmlenc11#",
   propOrder = {"any"}
)
public class KeyDerivationMethodType {
   @XmlAnyElement(
      lax = true
   )
   protected List any;
   @XmlAttribute(
      name = "Algorithm",
      required = true
   )
   @XmlSchemaType(
      name = "anyURI"
   )
   protected String algorithm;

   public List getAny() {
      if (this.any == null) {
         this.any = new ArrayList();
      }

      return this.any;
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public void setAlgorithm(String value) {
      this.algorithm = value;
   }
}
