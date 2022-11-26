package org.opensaml.soap.wspolicy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wspolicy.AppliesTo;
import org.w3c.dom.Attr;

public class AppliesToUnmarshaller extends AbstractWSPolicyObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      AppliesTo at = (AppliesTo)xmlObject;
      XMLObjectSupport.unmarshallToAttributeMap(at.getUnknownAttributes(), attribute);
   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      AppliesTo at = (AppliesTo)parentXMLObject;
      at.getUnknownXMLObjects().add(childXMLObject);
   }
}
