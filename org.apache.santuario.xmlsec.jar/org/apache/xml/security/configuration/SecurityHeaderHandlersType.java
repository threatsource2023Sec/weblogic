package org.apache.xml.security.configuration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "SecurityHeaderHandlersType",
   namespace = "http://www.xmlsecurity.org/NS/configuration",
   propOrder = {"handler"}
)
public class SecurityHeaderHandlersType {
   @XmlElement(
      name = "Handler",
      namespace = "http://www.xmlsecurity.org/NS/configuration"
   )
   protected List handler;

   public List getHandler() {
      if (this.handler == null) {
         this.handler = new ArrayList();
      }

      return this.handler;
   }
}
