package org.apache.xml.security.binding.xmlenc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "TransformsType",
   namespace = "http://www.w3.org/2001/04/xmlenc#",
   propOrder = {"transform"}
)
public class TransformsType {
   @XmlElement(
      name = "Transform",
      namespace = "http://www.w3.org/2000/09/xmldsig#",
      required = true
   )
   protected List transform;

   public List getTransform() {
      if (this.transform == null) {
         this.transform = new ArrayList();
      }

      return this.transform;
   }
}
