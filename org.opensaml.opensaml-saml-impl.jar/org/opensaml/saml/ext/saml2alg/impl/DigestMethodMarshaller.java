package org.opensaml.saml.ext.saml2alg.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.ext.saml2alg.DigestMethod;
import org.w3c.dom.Element;

public class DigestMethodMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      DigestMethod digestMethod = (DigestMethod)xmlObject;
      if (digestMethod.getAlgorithm() != null) {
         domElement.setAttributeNS((String)null, "Algorithm", digestMethod.getAlgorithm());
      }

   }
}
