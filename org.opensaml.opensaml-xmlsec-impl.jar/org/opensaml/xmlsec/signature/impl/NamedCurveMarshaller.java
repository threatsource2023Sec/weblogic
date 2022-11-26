package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.signature.NamedCurve;
import org.w3c.dom.Element;

public class NamedCurveMarshaller extends AbstractXMLSignatureMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      NamedCurve nc = (NamedCurve)xmlObject;
      if (nc.getURI() != null) {
         domElement.setAttributeNS((String)null, "URI", nc.getURI());
      }

   }
}
