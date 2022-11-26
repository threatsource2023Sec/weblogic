package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.Embedded;
import org.w3c.dom.Attr;

public class EmbeddedUnmarshaller extends AbstractWSSecurityObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      Embedded embedded = (Embedded)xmlObject;
      String attrName = attribute.getLocalName();
      if ("ValueType".equals(attrName)) {
         embedded.setValueType(attribute.getValue());
      } else {
         XMLObjectSupport.unmarshallToAttributeMap(embedded.getUnknownAttributes(), attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Embedded embedded = (Embedded)parentXMLObject;
      embedded.getUnknownXMLObjects().add(childXMLObject);
   }
}
