package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectConfirmation;

public class SubjectUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      Subject subject = (Subject)parentObject;
      if (childObject instanceof BaseID) {
         subject.setBaseID((BaseID)childObject);
      } else if (childObject instanceof NameID) {
         subject.setNameID((NameID)childObject);
      } else if (childObject instanceof EncryptedID) {
         subject.setEncryptedID((EncryptedID)childObject);
      } else if (childObject instanceof SubjectConfirmation) {
         subject.getSubjectConfirmations().add((SubjectConfirmation)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }
}
