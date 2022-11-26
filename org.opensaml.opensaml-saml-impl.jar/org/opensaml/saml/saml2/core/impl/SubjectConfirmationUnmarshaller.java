package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.w3c.dom.Attr;

public class SubjectConfirmationUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      SubjectConfirmation subjectConfirmation = (SubjectConfirmation)parentObject;
      if (childObject instanceof BaseID) {
         subjectConfirmation.setBaseID((BaseID)childObject);
      } else if (childObject instanceof NameID) {
         subjectConfirmation.setNameID((NameID)childObject);
      } else if (childObject instanceof EncryptedID) {
         subjectConfirmation.setEncryptedID((EncryptedID)childObject);
      } else if (childObject instanceof SubjectConfirmationData) {
         subjectConfirmation.setSubjectConfirmationData((SubjectConfirmationData)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      SubjectConfirmation subjectConfirmation = (SubjectConfirmation)samlObject;
      if (attribute.getLocalName().equals("Method") && attribute.getNamespaceURI() == null) {
         subjectConfirmation.setMethod(attribute.getValue());
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
