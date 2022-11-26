package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.joda.time.format.ISODateTimeFormat;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationInfo;
import org.w3c.dom.Element;

public class RegistrationInfoMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      RegistrationInfo info = (RegistrationInfo)samlObject;
      if (info.getRegistrationAuthority() != null) {
         domElement.setAttributeNS((String)null, "registrationAuthority", info.getRegistrationAuthority());
      }

      if (info.getRegistrationInstant() != null) {
         String registrationInstant = ISODateTimeFormat.dateTime().print(info.getRegistrationInstant());
         domElement.setAttributeNS((String)null, "registrationInstant", registrationInstant);
      }

   }
}
