package org.opensaml.core.xml.schema.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSURI;
import org.w3c.dom.Attr;

public class XSURIUnmarshaller extends AbstractXMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      XSURI uri = (XSURI)xmlObject;
      uri.setValue(elementContent);
   }
}
