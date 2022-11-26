package org.opensaml.soap.wsfed.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectMarshaller;
import org.opensaml.soap.wsfed.Address;
import org.w3c.dom.Element;

public class AddressMarshaller extends AbstractXMLObjectMarshaller {
   protected void marshallAttributes(XMLObject fedObject, Element domElement) {
   }

   protected void marshallElementContent(XMLObject fedObject, Element domElement) {
      Address address = (Address)fedObject;
      if (address.getValue() != null) {
         ElementSupport.appendTextContent(domElement, address.getValue());
      }

   }
}
