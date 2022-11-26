package org.glassfish.tyrus.core.wsadl.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "",
   propOrder = {"endpoint", "any"}
)
@XmlRootElement(
   name = "application"
)
public class Application {
   protected List endpoint;
   @XmlAnyElement(
      lax = true
   )
   protected List any;

   public List getEndpoint() {
      if (this.endpoint == null) {
         this.endpoint = new ArrayList();
      }

      return this.endpoint;
   }

   public List getAny() {
      if (this.any == null) {
         this.any = new ArrayList();
      }

      return this.any;
   }
}
