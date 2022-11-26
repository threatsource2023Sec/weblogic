package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.Reference;
import org.w3c.dom.Attr;

public class ReferenceUnmarshaller extends AbstractWSSecurityObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      Reference reference = (Reference)xmlObject;
      String attrName = attribute.getLocalName();
      if ("URI".equals(attrName)) {
         reference.setURI(attribute.getValue());
      } else if ("ValueType".equals(attrName)) {
         reference.setValueType(attribute.getValue());
      } else {
         XMLObjectSupport.unmarshallToAttributeMap(reference.getUnknownAttributes(), attribute);
      }

   }
}
