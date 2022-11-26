package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.NameIDPolicy;
import org.w3c.dom.Element;

public class NameIDPolicyMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      NameIDPolicy policy = (NameIDPolicy)samlObject;
      if (policy.getFormat() != null) {
         domElement.setAttributeNS((String)null, "Format", policy.getFormat());
      }

      if (policy.getSPNameQualifier() != null) {
         domElement.setAttributeNS((String)null, "SPNameQualifier", policy.getSPNameQualifier());
      }

      if (policy.getAllowCreateXSBoolean() != null) {
         domElement.setAttributeNS((String)null, "AllowCreate", policy.getAllowCreateXSBoolean().toString());
      }

   }
}
