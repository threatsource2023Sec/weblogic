package org.opensaml.saml.saml1.core.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml1.core.StatusMessage;
import org.w3c.dom.Element;

public class StatusMessageMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      StatusMessage statusMessage = (StatusMessage)samlObject;
      if (statusMessage.getMessage() != null) {
         ElementSupport.appendTextContent(domElement, statusMessage.getMessage());
      }

   }
}
