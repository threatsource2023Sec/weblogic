package org.opensaml.soap.wsfed.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.soap.wsfed.AppliesTo;
import org.opensaml.soap.wsfed.EndPointReference;
import org.w3c.dom.Attr;

public class AppliesToUnmarshaller extends AbstractXMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentFedObject, XMLObject childFedObject) {
      AppliesTo appliesTo = (AppliesTo)parentFedObject;
      if (childFedObject instanceof EndPointReference) {
         appliesTo.setEndPointReference((EndPointReference)childFedObject);
      }

   }

   protected void processAttribute(XMLObject fedObject, Attr attribute) {
   }

   protected void processElementContent(XMLObject fedObject, String content) {
   }
}
