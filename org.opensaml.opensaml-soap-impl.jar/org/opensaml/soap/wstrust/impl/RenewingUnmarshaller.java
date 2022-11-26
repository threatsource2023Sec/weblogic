package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.soap.wstrust.Renewing;
import org.w3c.dom.Attr;

public class RenewingUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      Renewing renewing = (Renewing)xmlObject;
      if (attribute.getLocalName().equals("Allow")) {
         renewing.setAllow(XSBooleanValue.valueOf(attribute.getValue()));
      } else if (attribute.getLocalName().equals("OK")) {
         renewing.setOK(XSBooleanValue.valueOf(attribute.getValue()));
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
