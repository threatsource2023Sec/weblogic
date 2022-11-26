package org.apache.xml.security.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "TransformAlgorithmType",
   namespace = "http://www.xmlsecurity.org/NS/configuration",
   propOrder = {"value"}
)
public class TransformAlgorithmType {
   @XmlValue
   protected String value;
   @XmlAttribute(
      name = "URI",
      required = true
   )
   protected String uri;
   @XmlAttribute(
      name = "JAVACLASS",
      required = true
   )
   protected String javaclass;
   @XmlAttribute(
      name = "INOUT"
   )
   protected InOutAttrType inout;

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getURI() {
      return this.uri;
   }

   public void setURI(String value) {
      this.uri = value;
   }

   public String getJAVACLASS() {
      return this.javaclass;
   }

   public void setJAVACLASS(String value) {
      this.javaclass = value;
   }

   public InOutAttrType getINOUT() {
      return this.inout;
   }

   public void setINOUT(InOutAttrType value) {
      this.inout = value;
   }
}
