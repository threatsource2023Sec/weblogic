package org.apache.xml.security.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "PropertyType",
   namespace = "http://www.xmlsecurity.org/NS/configuration",
   propOrder = {"value"}
)
public class PropertyType {
   @XmlValue
   protected String value;
   @XmlAttribute(
      name = "NAME"
   )
   protected String name;
   @XmlAttribute(
      name = "VAL"
   )
   protected String val;

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

   public String getVAL() {
      return this.val;
   }

   public void setVAL(String value) {
      this.val = value;
   }
}
