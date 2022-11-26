package org.opensaml.xacml.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

public abstract class AbstractXACMLObjectUnmarshaller extends AbstractXMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
   }
}
