package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml2.core.AuthzDecisionQuery;
import org.w3c.dom.Element;

public class AuthzDecisionQueryMarshaller extends SubjectQueryMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      AuthzDecisionQuery query = (AuthzDecisionQuery)samlObject;
      if (query.getResource() != null) {
         domElement.setAttributeNS((String)null, "Resource", query.getResource());
      }

      super.marshallAttributes(samlObject, domElement);
   }
}
