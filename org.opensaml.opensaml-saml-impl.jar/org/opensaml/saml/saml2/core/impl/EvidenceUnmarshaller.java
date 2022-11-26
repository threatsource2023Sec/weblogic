package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AssertionIDRef;
import org.opensaml.saml.saml2.core.AssertionURIRef;
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.Evidence;

public class EvidenceUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      Evidence evidence = (Evidence)parentObject;
      if (childObject instanceof AssertionIDRef) {
         evidence.getAssertionIDReferences().add((AssertionIDRef)childObject);
      } else if (childObject instanceof AssertionURIRef) {
         evidence.getAssertionURIReferences().add((AssertionURIRef)childObject);
      } else if (childObject instanceof Assertion) {
         evidence.getAssertions().add((Assertion)childObject);
      } else if (childObject instanceof EncryptedAssertion) {
         evidence.getEncryptedAssertions().add((EncryptedAssertion)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }
}
