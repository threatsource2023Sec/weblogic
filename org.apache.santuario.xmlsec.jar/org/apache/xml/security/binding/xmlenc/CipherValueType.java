package org.apache.xml.security.binding.xmlenc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "CipherValueType",
   namespace = "http://www.w3.org/2001/04/xmlenc#",
   propOrder = {"content"}
)
public class CipherValueType {
   @XmlElementRef(
      name = "Include",
      namespace = "http://www.w3.org/2004/08/xop/include",
      type = JAXBElement.class
   )
   @XmlMixed
   protected List content;

   public List getContent() {
      if (this.content == null) {
         this.content = new ArrayList();
      }

      return this.content;
   }
}
