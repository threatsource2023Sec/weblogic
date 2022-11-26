package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.impl.XSQNameMarshaller;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wsaddressing.AttributedQName;
import org.w3c.dom.Element;

public class AttributedQNameMarshaller extends XSQNameMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AttributedQName attributedQName = (AttributedQName)xmlObject;
      XMLObjectSupport.marshallAttributeMap(attributedQName.getUnknownAttributes(), domElement);
   }
}
