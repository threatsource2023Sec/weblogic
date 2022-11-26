package org.apache.xml.security.configuration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(
   name = "inOutAttrType",
   namespace = "http://www.xmlsecurity.org/NS/configuration"
)
@XmlEnum
public enum InOutAttrType {
   IN,
   OUT;

   public String value() {
      return this.name();
   }

   public static InOutAttrType fromValue(String v) {
      return valueOf(v);
   }
}
