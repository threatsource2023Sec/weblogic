package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.TransformationParameters;
import org.w3c.dom.Element;

public class TransformationParametersMarshaller extends AbstractWSSecurityObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      TransformationParameters tp = (TransformationParameters)xmlObject;
      XMLObjectSupport.marshallAttributeMap(tp.getUnknownAttributes(), domElement);
   }
}
