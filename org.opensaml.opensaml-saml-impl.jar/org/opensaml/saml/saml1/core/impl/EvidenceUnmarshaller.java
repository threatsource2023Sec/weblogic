package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.AssertionIDReference;
import org.opensaml.saml.saml1.core.Evidence;

public class EvidenceUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      Evidence evidence = (Evidence)parentSAMLObject;
      if (childSAMLObject instanceof AssertionIDReference) {
         evidence.getAssertionIDReferences().add((AssertionIDReference)childSAMLObject);
      } else if (childSAMLObject instanceof Assertion) {
         evidence.getAssertions().add((Assertion)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}
