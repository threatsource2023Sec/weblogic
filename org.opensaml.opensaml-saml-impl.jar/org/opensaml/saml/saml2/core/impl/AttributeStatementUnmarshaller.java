package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.EncryptedAttribute;

public class AttributeStatementUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      AttributeStatement attributeStatement = (AttributeStatement)parentObject;
      if (childObject instanceof Attribute) {
         attributeStatement.getAttributes().add((Attribute)childObject);
      } else if (childObject instanceof EncryptedAttribute) {
         attributeStatement.getEncryptedAttributes().add((EncryptedAttribute)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }
}
