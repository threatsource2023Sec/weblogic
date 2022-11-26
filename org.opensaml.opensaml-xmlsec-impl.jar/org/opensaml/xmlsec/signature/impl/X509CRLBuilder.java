package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.X509CRL;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class X509CRLBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public X509CRL buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new X509CRLImpl(namespaceURI, localName, namespacePrefix);
   }

   public X509CRL buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509CRL", "ds");
   }
}
