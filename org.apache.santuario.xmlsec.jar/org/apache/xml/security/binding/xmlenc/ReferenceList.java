package org.apache.xml.security.binding.xmlenc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "",
   propOrder = {"dataReferenceOrKeyReference"}
)
@XmlRootElement(
   name = "ReferenceList",
   namespace = "http://www.w3.org/2001/04/xmlenc#"
)
public class ReferenceList {
   @XmlElementRefs({@XmlElementRef(
   name = "DataReference",
   namespace = "http://www.w3.org/2001/04/xmlenc#",
   type = JAXBElement.class
), @XmlElementRef(
   name = "KeyReference",
   namespace = "http://www.w3.org/2001/04/xmlenc#",
   type = JAXBElement.class
)})
   protected List dataReferenceOrKeyReference;

   public List getDataReferenceOrKeyReference() {
      if (this.dataReferenceOrKeyReference == null) {
         this.dataReferenceOrKeyReference = new ArrayList();
      }

      return this.dataReferenceOrKeyReference;
   }
}
