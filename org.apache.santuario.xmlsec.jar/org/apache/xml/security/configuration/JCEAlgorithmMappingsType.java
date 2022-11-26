package org.apache.xml.security.configuration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "JCEAlgorithmMappingsType",
   namespace = "http://www.xmlsecurity.org/NS/configuration",
   propOrder = {"algorithm"}
)
public class JCEAlgorithmMappingsType {
   @XmlElement(
      name = "Algorithm",
      namespace = "http://www.xmlsecurity.org/NS/configuration"
   )
   protected List algorithm;

   public List getAlgorithm() {
      if (this.algorithm == null) {
         this.algorithm = new ArrayList();
      }

      return this.algorithm;
   }
}
