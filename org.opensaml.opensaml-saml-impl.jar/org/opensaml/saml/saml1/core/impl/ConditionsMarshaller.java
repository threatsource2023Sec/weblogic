package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml1.core.Conditions;
import org.w3c.dom.Element;

public class ConditionsMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      Conditions conditions = (Conditions)samlElement;
      String date;
      if (conditions.getNotBefore() != null) {
         date = SAMLConfigurationSupport.getSAMLDateFormatter().print(conditions.getNotBefore());
         domElement.setAttributeNS((String)null, "NotBefore", date);
      }

      if (conditions.getNotOnOrAfter() != null) {
         date = SAMLConfigurationSupport.getSAMLDateFormatter().print(conditions.getNotOnOrAfter());
         domElement.setAttributeNS((String)null, "NotOnOrAfter", date);
      }

   }
}
