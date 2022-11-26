package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationInfo;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationPolicy;
import org.w3c.dom.Attr;

public class RegistrationInfoUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      RegistrationInfo info = (RegistrationInfo)parentObject;
      if (childObject instanceof RegistrationPolicy) {
         info.getRegistrationPolicies().add((RegistrationPolicy)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      RegistrationInfo info = (RegistrationInfo)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if ("registrationAuthority".equals(attribute.getName())) {
            info.setRegistrationAuthority(attribute.getValue());
         } else if ("registrationInstant".equals(attribute.getName())) {
            info.setRegistrationInstant(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
