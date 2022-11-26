package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.opensaml.saml.saml1.core.Subject;
import org.opensaml.saml.saml1.core.SubjectConfirmation;

public class SubjectUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      Subject subject = (Subject)parentSAMLObject;
      if (childSAMLObject instanceof NameIdentifier) {
         subject.setNameIdentifier((NameIdentifier)childSAMLObject);
      } else if (childSAMLObject instanceof SubjectConfirmation) {
         subject.setSubjectConfirmation((SubjectConfirmation)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}
