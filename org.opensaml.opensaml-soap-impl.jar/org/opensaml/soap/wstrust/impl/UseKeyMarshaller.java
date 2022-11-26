package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.soap.wstrust.UseKey;
import org.w3c.dom.Element;

public class UseKeyMarshaller extends AbstractWSTrustObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      UseKey useKey = (UseKey)xmlObject;
      if (useKey.getSig() != null) {
         domElement.setAttributeNS((String)null, "Sig", useKey.getSig());
      }

      super.marshallAttributes(xmlObject, domElement);
   }
}
