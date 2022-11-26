package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.Scoping;
import org.w3c.dom.Element;

public class ScopingMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      Scoping scoping = (Scoping)samlObject;
      if (scoping.getProxyCount() != null) {
         domElement.setAttributeNS((String)null, "ProxyCount", scoping.getProxyCount().toString());
      }

   }
}
