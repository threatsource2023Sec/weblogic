package org.opensaml.soap.wsfed.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.wsfed.AppliesTo;
import org.opensaml.soap.wsfed.WSFedObjectBuilder;

public class AppliesToBuilder extends AbstractXMLObjectBuilder implements WSFedObjectBuilder {
   public AppliesTo buildObject() {
      return this.buildObject("http://schemas.xmlsoap.org/ws/2004/09/policy", "AppliesTo", "wsp");
   }

   public AppliesTo buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AppliesToImpl(namespaceURI, localName, namespacePrefix);
   }
}
