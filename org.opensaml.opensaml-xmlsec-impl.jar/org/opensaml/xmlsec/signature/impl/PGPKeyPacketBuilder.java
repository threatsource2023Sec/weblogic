package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.PGPKeyPacket;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class PGPKeyPacketBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public PGPKeyPacket buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PGPKeyPacketImpl(namespaceURI, localName, namespacePrefix);
   }

   public PGPKeyPacket buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "PGPKeyPacket", "ds");
   }
}
