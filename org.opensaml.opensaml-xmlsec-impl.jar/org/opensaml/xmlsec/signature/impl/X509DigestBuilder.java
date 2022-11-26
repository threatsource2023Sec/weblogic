package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.X509Digest;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class X509DigestBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public X509Digest buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new X509DigestImpl(namespaceURI, localName, namespacePrefix);
   }

   public X509Digest buildObject() {
      return this.buildObject("http://www.w3.org/2009/xmldsig11#", "X509Digest", "ds11");
   }
}
