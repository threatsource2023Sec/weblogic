package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wsaddressing.EndpointReferenceType;
import org.w3c.dom.Element;

public class EndpointReferenceTypeMarshaller extends AbstractWSAddressingObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      EndpointReferenceType eprType = (EndpointReferenceType)xmlObject;
      XMLObjectSupport.marshallAttributeMap(eprType.getUnknownAttributes(), domElement);
   }
}
