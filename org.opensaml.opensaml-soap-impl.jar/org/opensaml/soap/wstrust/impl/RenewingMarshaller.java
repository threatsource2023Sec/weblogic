package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.soap.wstrust.Renewing;
import org.w3c.dom.Element;

public class RenewingMarshaller extends AbstractWSTrustObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Renewing renewing = (Renewing)xmlObject;
      if (renewing.isAllowXSBoolean() != null) {
         domElement.setAttributeNS((String)null, "Allow", renewing.isAllowXSBoolean().toString());
      }

      if (renewing.isOKXSBoolean() != null) {
         domElement.setAttributeNS((String)null, "OK", renewing.isOKXSBoolean().toString());
      }

   }
}
