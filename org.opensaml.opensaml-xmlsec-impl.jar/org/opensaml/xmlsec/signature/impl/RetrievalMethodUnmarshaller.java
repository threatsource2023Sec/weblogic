package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.RetrievalMethod;
import org.opensaml.xmlsec.signature.Transforms;
import org.w3c.dom.Attr;

public class RetrievalMethodUnmarshaller extends AbstractXMLSignatureUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      RetrievalMethod rm = (RetrievalMethod)xmlObject;
      if (attribute.getLocalName().equals("URI")) {
         rm.setURI(attribute.getValue());
      } else if (attribute.getLocalName().equals("Type")) {
         rm.setType(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      RetrievalMethod rm = (RetrievalMethod)parentXMLObject;
      if (childXMLObject instanceof Transforms) {
         rm.setTransforms((Transforms)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
