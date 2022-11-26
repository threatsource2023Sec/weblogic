package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml1.core.Attribute;
import org.opensaml.saml.saml1.core.AttributeStatement;

public class AttributeStatementUnmarshaller extends SubjectStatementUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      AttributeStatement attributeStatement = (AttributeStatement)parentSAMLObject;
      if (childSAMLObject instanceof Attribute) {
         attributeStatement.getAttributes().add((Attribute)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}
