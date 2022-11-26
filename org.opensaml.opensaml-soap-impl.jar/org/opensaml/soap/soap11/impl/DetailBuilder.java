package org.opensaml.soap.soap11.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.soap.common.SOAPObjectBuilder;
import org.opensaml.soap.soap11.Detail;

public class DetailBuilder extends AbstractXMLObjectBuilder implements SOAPObjectBuilder {
   public Detail buildObject() {
      return this.buildObject((String)null, "detail", (String)null);
   }

   public Detail buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DetailImpl(namespaceURI, localName, namespacePrefix);
   }
}
