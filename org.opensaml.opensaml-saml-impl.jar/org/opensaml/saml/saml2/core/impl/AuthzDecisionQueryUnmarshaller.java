package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.core.Action;
import org.opensaml.saml.saml2.core.AuthzDecisionQuery;
import org.opensaml.saml.saml2.core.Evidence;
import org.w3c.dom.Attr;

public class AuthzDecisionQueryUnmarshaller extends SubjectQueryUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      AuthzDecisionQuery query = (AuthzDecisionQuery)parentSAMLObject;
      if (childSAMLObject instanceof Action) {
         query.getActions().add((Action)childSAMLObject);
      } else if (childSAMLObject instanceof Evidence) {
         query.setEvidence((Evidence)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      AuthzDecisionQuery query = (AuthzDecisionQuery)samlObject;
      if (attribute.getLocalName().equals("Resource") && attribute.getNamespaceURI() == null) {
         query.setResource(attribute.getValue());
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
