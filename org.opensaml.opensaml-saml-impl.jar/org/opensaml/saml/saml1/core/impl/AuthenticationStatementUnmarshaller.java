package org.opensaml.saml.saml1.core.impl;

import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml1.core.AuthenticationStatement;
import org.opensaml.saml.saml1.core.AuthorityBinding;
import org.opensaml.saml.saml1.core.SubjectLocality;
import org.w3c.dom.Attr;

public class AuthenticationStatementUnmarshaller extends SubjectStatementUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      AuthenticationStatement authenticationStatement = (AuthenticationStatement)parentSAMLObject;
      if (childSAMLObject instanceof SubjectLocality) {
         authenticationStatement.setSubjectLocality((SubjectLocality)childSAMLObject);
      } else if (childSAMLObject instanceof AuthorityBinding) {
         authenticationStatement.getAuthorityBindings().add((AuthorityBinding)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      AuthenticationStatement authenticationStatement = (AuthenticationStatement)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if ("AuthenticationInstant".equals(attribute.getLocalName()) && !Strings.isNullOrEmpty(attribute.getValue())) {
            DateTime value = new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC());
            authenticationStatement.setAuthenticationInstant(value);
         } else if ("AuthenticationMethod".equals(attribute.getLocalName())) {
            authenticationStatement.setAuthenticationMethod(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
