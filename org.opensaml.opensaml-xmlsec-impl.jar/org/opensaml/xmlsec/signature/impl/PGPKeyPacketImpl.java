package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.schema.impl.XSBase64BinaryImpl;
import org.opensaml.xmlsec.signature.PGPKeyPacket;

public class PGPKeyPacketImpl extends XSBase64BinaryImpl implements PGPKeyPacket {
   protected PGPKeyPacketImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
