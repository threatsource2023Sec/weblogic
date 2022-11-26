package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.KeyInfoReference;
import org.w3c.dom.Attr;

public class KeyInfoReferenceUnmarshaller extends AbstractXMLSignatureUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      KeyInfoReference ref = (KeyInfoReference)xmlObject;
      if (attribute.getLocalName().equals("Id")) {
         ref.setID(attribute.getValue());
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      } else if (attribute.getLocalName().equals("URI")) {
         ref.setURI(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
