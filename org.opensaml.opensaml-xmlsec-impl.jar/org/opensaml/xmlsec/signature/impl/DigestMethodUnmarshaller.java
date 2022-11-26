package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.DigestMethod;
import org.w3c.dom.Attr;

public class DigestMethodUnmarshaller extends AbstractXMLSignatureUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      DigestMethod dm = (DigestMethod)xmlObject;
      if (attribute.getLocalName().equals("Algorithm")) {
         dm.setAlgorithm(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      DigestMethod dm = (DigestMethod)parentXMLObject;
      dm.getUnknownXMLObjects().add(childXMLObject);
   }
}
