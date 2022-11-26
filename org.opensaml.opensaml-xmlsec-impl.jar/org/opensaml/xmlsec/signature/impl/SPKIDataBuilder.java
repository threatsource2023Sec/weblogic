package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.SPKIData;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class SPKIDataBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public SPKIData buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SPKIDataImpl(namespaceURI, localName, namespacePrefix);
   }

   public SPKIData buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "SPKIData", "ds");
   }
}
