package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml1.core.Action;
import org.opensaml.saml.saml1.core.AuthorizationDecisionQuery;
import org.opensaml.saml.saml1.core.Evidence;
import org.w3c.dom.Attr;

public class AuthorizationDecisionQueryUnmarshaller extends SubjectQueryUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      AuthorizationDecisionQuery authorizationDecisionQuery = (AuthorizationDecisionQuery)parentSAMLObject;
      if (childSAMLObject instanceof Action) {
         authorizationDecisionQuery.getActions().add((Action)childSAMLObject);
      } else if (childSAMLObject instanceof Evidence) {
         authorizationDecisionQuery.setEvidence((Evidence)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      AuthorizationDecisionQuery authorizationDecisionQuery = (AuthorizationDecisionQuery)samlObject;
      if (attribute.getLocalName().equals("Resource") && attribute.getNamespaceURI() == null) {
         authorizationDecisionQuery.setResource(attribute.getValue());
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
