package org.apache.xml.security.binding.xmldsig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "PGPDataType",
   namespace = "http://www.w3.org/2000/09/xmldsig#",
   propOrder = {"content"}
)
public class PGPDataType {
   @XmlElementRefs({@XmlElementRef(
   name = "PGPKeyID",
   namespace = "http://www.w3.org/2000/09/xmldsig#",
   type = JAXBElement.class
), @XmlElementRef(
   name = "PGPKeyPacket",
   namespace = "http://www.w3.org/2000/09/xmldsig#",
   type = JAXBElement.class
)})
   @XmlAnyElement(
      lax = true
   )
   protected List content;

   public List getContent() {
      if (this.content == null) {
         this.content = new ArrayList();
      }

      return this.content;
   }
}
