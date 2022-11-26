package org.opensaml.saml.ext.saml2alg.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.ext.saml2alg.SigningMethod;
import org.w3c.dom.Element;

public class SigningMethodMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      SigningMethod signingMethod = (SigningMethod)xmlObject;
      if (signingMethod.getAlgorithm() != null) {
         domElement.setAttributeNS((String)null, "Algorithm", signingMethod.getAlgorithm());
      }

      if (signingMethod.getMinKeySize() != null) {
         domElement.setAttributeNS((String)null, "MinKeySize", signingMethod.getMinKeySize().toString());
      }

      if (signingMethod.getMaxKeySize() != null) {
         domElement.setAttributeNS((String)null, "MaxKeySize", signingMethod.getMaxKeySize().toString());
      }

   }
}
