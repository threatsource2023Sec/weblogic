package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.ConfirmationMethod;

public class ConfirmationMethodUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      ConfirmationMethod confirmationMethod = (ConfirmationMethod)samlObject;
      confirmationMethod.setConfirmationMethod(elementContent);
   }
}
