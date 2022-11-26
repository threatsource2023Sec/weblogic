package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.RetrievalMethod;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class RetrievalMethodBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public RetrievalMethod buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RetrievalMethodImpl(namespaceURI, localName, namespacePrefix);
   }

   public RetrievalMethod buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "RetrievalMethod", "ds");
   }
}
