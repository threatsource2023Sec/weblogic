package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.Transform;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class TransformBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public Transform buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new TransformImpl(namespaceURI, localName, namespacePrefix);
   }

   public Transform buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Transform", "ds");
   }
}
