package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.core.Conditions;
import org.w3c.dom.Element;

public class ConditionsMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      Conditions conditions = (Conditions)samlObject;
      String notOnOrAfterStr;
      if (conditions.getNotBefore() != null) {
         notOnOrAfterStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(conditions.getNotBefore());
         domElement.setAttributeNS((String)null, "NotBefore", notOnOrAfterStr);
      }

      if (conditions.getNotOnOrAfter() != null) {
         notOnOrAfterStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(conditions.getNotOnOrAfter());
         domElement.setAttributeNS((String)null, "NotOnOrAfter", notOnOrAfterStr);
      }

   }
}
