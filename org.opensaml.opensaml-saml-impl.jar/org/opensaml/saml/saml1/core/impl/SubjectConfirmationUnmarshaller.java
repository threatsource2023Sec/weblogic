package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.ConfirmationMethod;
import org.opensaml.saml.saml1.core.SubjectConfirmation;
import org.opensaml.xmlsec.signature.KeyInfo;

public class SubjectConfirmationUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      SubjectConfirmation subjectConfirmation = (SubjectConfirmation)parentSAMLObject;
      if (childSAMLObject instanceof ConfirmationMethod) {
         subjectConfirmation.getConfirmationMethods().add((ConfirmationMethod)childSAMLObject);
      } else if (childSAMLObject instanceof KeyInfo) {
         subjectConfirmation.setKeyInfo((KeyInfo)childSAMLObject);
      } else {
         subjectConfirmation.setSubjectConfirmationData(childSAMLObject);
      }

   }
}
