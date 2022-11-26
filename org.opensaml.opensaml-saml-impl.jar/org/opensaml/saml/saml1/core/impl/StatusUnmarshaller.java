package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.Status;
import org.opensaml.saml.saml1.core.StatusCode;
import org.opensaml.saml.saml1.core.StatusDetail;
import org.opensaml.saml.saml1.core.StatusMessage;

public class StatusUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      Status status = (Status)parentSAMLObject;
      if (childSAMLObject instanceof StatusCode) {
         status.setStatusCode((StatusCode)childSAMLObject);
      } else if (childSAMLObject instanceof StatusMessage) {
         status.setStatusMessage((StatusMessage)childSAMLObject);
      } else if (childSAMLObject instanceof StatusDetail) {
         status.setStatusDetail((StatusDetail)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}
