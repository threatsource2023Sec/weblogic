package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wstrust.UseKey;
import org.w3c.dom.Attr;

public class UseKeyUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      UseKey uk = (UseKey)xmlObject;
      if ("Sig".equals(attribute.getLocalName())) {
         uk.setSig(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      UseKey uk = (UseKey)parentXMLObject;
      uk.setUnknownXMLObject(childXMLObject);
   }
}
