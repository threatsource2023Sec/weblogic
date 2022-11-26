package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.X509SKI;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class X509SKIBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public X509SKI buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new X509SKIImpl(namespaceURI, localName, namespacePrefix);
   }

   public X509SKI buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509SKI", "ds");
   }
}
