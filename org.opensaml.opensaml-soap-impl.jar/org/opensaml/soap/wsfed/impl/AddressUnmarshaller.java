package org.opensaml.soap.wsfed.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.soap.wsfed.Address;
import org.w3c.dom.Attr;

public class AddressUnmarshaller extends AbstractXMLObjectUnmarshaller {
   protected void processElementContent(XMLObject fedObject, String elementContent) {
      Address address = (Address)fedObject;
      address.setValue(elementContent);
   }

   protected void processAttribute(XMLObject fedObject, Attr attribute) {
   }

   protected void processChildElement(XMLObject fedObject, XMLObject child) {
   }
}
