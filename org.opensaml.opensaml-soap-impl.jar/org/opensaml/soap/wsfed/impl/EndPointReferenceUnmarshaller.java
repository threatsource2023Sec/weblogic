package org.opensaml.soap.wsfed.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.soap.wsfed.Address;
import org.opensaml.soap.wsfed.EndPointReference;
import org.w3c.dom.Attr;

public class EndPointReferenceUnmarshaller extends AbstractXMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) {
      EndPointReference endPointReference = (EndPointReference)parentSAMLObject;
      if (childSAMLObject instanceof Address) {
         endPointReference.setAddress((Address)childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject fedObject, Attr attribute) {
   }

   protected void processElementContent(XMLObject fedObject, String content) {
   }
}
