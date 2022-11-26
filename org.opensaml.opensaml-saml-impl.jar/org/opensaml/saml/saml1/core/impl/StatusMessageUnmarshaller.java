package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.StatusMessage;

public class StatusMessageUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      StatusMessage statusMessage = (StatusMessage)samlObject;
      statusMessage.setMessage(elementContent);
   }
}
