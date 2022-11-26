package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.DigestMethod;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class DigestMethodBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public DigestMethod buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DigestMethodImpl(namespaceURI, localName, namespacePrefix);
   }

   public DigestMethod buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "DigestMethod", "ds");
   }
}
