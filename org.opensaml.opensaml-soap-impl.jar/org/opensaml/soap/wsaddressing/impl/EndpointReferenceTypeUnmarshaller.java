package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wsaddressing.Address;
import org.opensaml.soap.wsaddressing.EndpointReferenceType;
import org.opensaml.soap.wsaddressing.Metadata;
import org.opensaml.soap.wsaddressing.ReferenceParameters;
import org.w3c.dom.Attr;

public class EndpointReferenceTypeUnmarshaller extends AbstractWSAddressingObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      EndpointReferenceType epr = (EndpointReferenceType)parentXMLObject;
      if (childXMLObject instanceof Address) {
         epr.setAddress((Address)childXMLObject);
      } else if (childXMLObject instanceof Metadata) {
         epr.setMetadata((Metadata)childXMLObject);
      } else if (childXMLObject instanceof ReferenceParameters) {
         epr.setReferenceParameters((ReferenceParameters)childXMLObject);
      } else {
         epr.getUnknownXMLObjects().add(childXMLObject);
      }

   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      EndpointReferenceType epr = (EndpointReferenceType)xmlObject;
      XMLObjectSupport.unmarshallToAttributeMap(epr.getUnknownAttributes(), attribute);
   }
}
