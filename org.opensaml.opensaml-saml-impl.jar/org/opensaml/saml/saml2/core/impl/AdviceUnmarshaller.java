package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.Advice;

public class AdviceUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      Advice advice = (Advice)parentObject;
      advice.getChildren().add(childObject);
   }
}
