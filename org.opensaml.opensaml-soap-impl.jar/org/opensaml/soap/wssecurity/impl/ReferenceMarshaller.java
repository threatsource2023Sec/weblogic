package org.opensaml.soap.wssecurity.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.Reference;
import org.w3c.dom.Element;

public class ReferenceMarshaller extends AbstractWSSecurityObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Reference reference = (Reference)xmlObject;
      if (!Strings.isNullOrEmpty(reference.getURI())) {
         domElement.setAttributeNS((String)null, "URI", reference.getURI());
      }

      if (!Strings.isNullOrEmpty(reference.getValueType())) {
         domElement.setAttributeNS((String)null, "ValueType", reference.getValueType());
      }

      XMLObjectSupport.marshallAttributeMap(reference.getUnknownAttributes(), domElement);
   }
}
