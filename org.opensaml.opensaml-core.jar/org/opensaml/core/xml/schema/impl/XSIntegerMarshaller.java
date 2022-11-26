package org.opensaml.core.xml.schema.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectMarshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.XSInteger;
import org.w3c.dom.Element;

public class XSIntegerMarshaller extends AbstractXMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
   }

   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      XSInteger xsiInteger = (XSInteger)xmlObject;
      if (xsiInteger.getValue() != null) {
         ElementSupport.appendTextContent(domElement, xsiInteger.getValue().toString());
      }

   }
}
