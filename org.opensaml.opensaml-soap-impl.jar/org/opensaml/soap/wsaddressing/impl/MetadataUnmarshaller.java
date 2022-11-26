package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wsaddressing.Metadata;
import org.w3c.dom.Attr;

public class MetadataUnmarshaller extends AbstractWSAddressingObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      Metadata metadata = (Metadata)xmlObject;
      XMLObjectSupport.unmarshallToAttributeMap(metadata.getUnknownAttributes(), attribute);
   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Metadata metadata = (Metadata)parentXMLObject;
      metadata.getUnknownXMLObjects().add(childXMLObject);
   }
}
