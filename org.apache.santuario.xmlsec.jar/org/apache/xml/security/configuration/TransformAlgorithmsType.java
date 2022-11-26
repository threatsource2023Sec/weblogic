package org.apache.xml.security.configuration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "TransformAlgorithmsType",
   namespace = "http://www.xmlsecurity.org/NS/configuration",
   propOrder = {"transformAlgorithm"}
)
public class TransformAlgorithmsType {
   @XmlElement(
      name = "TransformAlgorithm",
      namespace = "http://www.xmlsecurity.org/NS/configuration"
   )
   protected List transformAlgorithm;

   public List getTransformAlgorithm() {
      if (this.transformAlgorithm == null) {
         this.transformAlgorithm = new ArrayList();
      }

      return this.transformAlgorithm;
   }
}
