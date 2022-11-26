package org.opensaml.core.xml.schema.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.BaseXMLObjectMarshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.XSDateTime;
import org.w3c.dom.Element;

public class XSDateTimeMarshaller extends BaseXMLObjectMarshaller {
   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      XSDateTime xsDateTime = (XSDateTime)xmlObject;
      ElementSupport.appendTextContent(domElement, xsDateTime.getDateTimeFormatter().print(xsDateTime.getValue()));
   }
}
