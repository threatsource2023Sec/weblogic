package org.apache.xml.security.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "ResolverType",
   namespace = "http://www.xmlsecurity.org/NS/configuration",
   propOrder = {"value"}
)
public class ResolverType {
   @XmlValue
   protected String value;
   @XmlAttribute(
      name = "JAVACLASS",
      required = true
   )
   protected String javaclass;
   @XmlAttribute(
      name = "DESCRIPTION",
      required = true
   )
   protected String description;

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getJAVACLASS() {
      return this.javaclass;
   }

   public void setJAVACLASS(String value) {
      this.javaclass = value;
   }

   public String getDESCRIPTION() {
      return this.description;
   }

   public void setDESCRIPTION(String value) {
      this.description = value;
   }
}
