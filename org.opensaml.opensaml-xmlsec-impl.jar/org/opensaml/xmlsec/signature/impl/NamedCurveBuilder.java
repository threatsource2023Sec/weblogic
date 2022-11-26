package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.NamedCurve;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class NamedCurveBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public NamedCurve buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new NamedCurveImpl(namespaceURI, localName, namespacePrefix);
   }

   public NamedCurve buildObject() {
      return this.buildObject("http://www.w3.org/2009/xmldsig11#", "NamedCurve", "ds11");
   }
}
