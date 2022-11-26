package org.opensaml.core.xml.schema.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectMarshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.XSString;
import org.w3c.dom.Element;

public class XSStringMarshaller extends AbstractXMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
   }

   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      XSString xsiString = (XSString)xmlObject;
      ElementSupport.appendTextContent(domElement, xsiString.getValue());
   }
}
