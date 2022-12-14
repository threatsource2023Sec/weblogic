package org.apache.xml.security.binding.xmlenc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "EncryptionMethodType",
   namespace = "http://www.w3.org/2001/04/xmlenc#",
   propOrder = {"content"}
)
public class EncryptionMethodType {
   @XmlElementRefs({@XmlElementRef(
   name = "KeySize",
   namespace = "http://www.w3.org/2001/04/xmlenc#",
   type = JAXBElement.class
), @XmlElementRef(
   name = "OAEPparams",
   namespace = "http://www.w3.org/2001/04/xmlenc#",
   type = JAXBElement.class
)})
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
