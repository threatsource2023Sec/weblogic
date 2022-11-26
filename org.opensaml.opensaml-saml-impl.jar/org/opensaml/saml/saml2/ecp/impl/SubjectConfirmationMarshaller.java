package org.opensaml.saml.saml2.ecp.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.ecp.SubjectConfirmation;
import org.w3c.dom.Element;

public class SubjectConfirmationMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      SubjectConfirmation sc = (SubjectConfirmation)samlObject;
      if (sc.isSOAP11MustUnderstandXSBoolean() != null) {
         XMLObjectSupport.marshallAttribute(SubjectConfirmation.SOAP11_MUST_UNDERSTAND_ATTR_NAME, sc.isSOAP11MustUnderstandXSBoolean().toString(), domElement, false);
      }

      if (sc.getSOAP11Actor() != null) {
         XMLObjectSupport.marshallAttribute(SubjectConfirmation.SOAP11_ACTOR_ATTR_NAME, sc.getSOAP11Actor(), domElement, false);
      }

      if (sc.getMethod() != null) {
         domElement.setAttributeNS((String)null, "Method", sc.getMethod());
      }

   }
}
