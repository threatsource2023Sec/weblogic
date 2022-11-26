package org.apache.xml.security.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "HandlerType",
   namespace = "http://www.xmlsecurity.org/NS/configuration",
   propOrder = {"value"}
)
public class HandlerType {
   @XmlValue
   protected String value;
   @XmlAttribute(
      name = "NAME",
      required = true
   )
   protected String name;
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

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getNAME() {
      return this.name;
   }

   public void setNAME(String value) {
      this.name = value;
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
}
