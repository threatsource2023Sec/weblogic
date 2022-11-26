package org.opensaml.saml.saml1.core.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml1.core.ConfirmationMethod;
import org.w3c.dom.Element;

public class ConfirmationMethodMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      ConfirmationMethod confirmationMethod = (ConfirmationMethod)samlObject;
      ElementSupport.appendTextContent(domElement, confirmationMethod.getConfirmationMethod());
   }
}
