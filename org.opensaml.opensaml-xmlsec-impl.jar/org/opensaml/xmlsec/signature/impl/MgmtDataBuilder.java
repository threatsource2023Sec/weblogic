package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.MgmtData;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class MgmtDataBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public MgmtData buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new MgmtDataImpl(namespaceURI, localName, namespacePrefix);
   }

   public MgmtData buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "MgmtData", "ds");
   }
}
