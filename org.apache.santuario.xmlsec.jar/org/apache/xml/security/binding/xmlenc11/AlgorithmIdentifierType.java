package org.apache.xml.security.binding.xmlenc11;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "AlgorithmIdentifierType",
   namespace = "http://www.w3.org/2009/xmlenc11#",
   propOrder = {"parameters"}
)
@XmlSeeAlso({MGFType.class, PRFAlgorithmIdentifierType.class})
public class AlgorithmIdentifierType {
   @XmlElement(
      name = "Parameters",
      namespace = "http://www.w3.org/2009/xmlenc11#"
   )
   protected Object parameters;
   @XmlAttribute(
      name = "Algorithm",
      required = true
   )
   @XmlSchemaType(
      name = "anyURI"
   )
   protected String algorithm;

   public Object getParameters() {
      return this.parameters;
   }

   public void setParameters(Object value) {
      this.parameters = value;
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public void setAlgorithm(String value) {
      this.algorithm = value;
   }
}
