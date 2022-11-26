package org.apache.xml.security.stax.securityEvent;

import java.util.ArrayList;
import java.util.List;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;

public abstract class AbstractElementSecurityEvent extends SecurityEvent {
   private List elementPath;
   private XMLSecEvent xmlSecEvent;

   public AbstractElementSecurityEvent(SecurityEventConstants.Event securityEventType) {
      super(securityEventType);
   }

   public List getElementPath() {
      return this.elementPath;
   }

   public void setElementPath(List elementPath) {
      this.elementPath = new ArrayList(elementPath);
   }

   public XMLSecEvent getXmlSecEvent() {
      return this.xmlSecEvent;
   }

   public void setXmlSecEvent(XMLSecEvent xmlSecEvent) {
      this.xmlSecEvent = xmlSecEvent;
   }
}
