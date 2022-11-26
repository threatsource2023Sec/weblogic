package org.apache.xml.security.configuration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "PropertiesType",
   namespace = "http://www.xmlsecurity.org/NS/configuration",
   propOrder = {"property"}
)
public class PropertiesType {
   @XmlElement(
      name = "Property",
      namespace = "http://www.xmlsecurity.org/NS/configuration"
   )
   protected List property;

   public List getProperty() {
      if (this.property == null) {
         this.property = new ArrayList();
      }

      return this.property;
   }
}
