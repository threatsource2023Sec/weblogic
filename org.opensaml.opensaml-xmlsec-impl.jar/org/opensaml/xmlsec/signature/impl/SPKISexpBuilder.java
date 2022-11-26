package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.SPKISexp;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class SPKISexpBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public SPKISexp buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SPKISexpImpl(namespaceURI, localName, namespacePrefix);
   }

   public SPKISexp buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "SPKISexp", "ds");
   }
}
