package org.opensaml.saml.saml2.core.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.SessionIndex;
import org.w3c.dom.Element;

public class SessionIndexMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      SessionIndex si = (SessionIndex)samlObject;
      if (si.getSessionIndex() != null) {
         ElementSupport.appendTextContent(domElement, si.getSessionIndex());
      }

   }
}
