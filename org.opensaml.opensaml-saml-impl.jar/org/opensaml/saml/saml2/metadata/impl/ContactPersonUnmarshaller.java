package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.metadata.Company;
import org.opensaml.saml.saml2.metadata.ContactPerson;
import org.opensaml.saml.saml2.metadata.ContactPersonTypeEnumeration;
import org.opensaml.saml.saml2.metadata.EmailAddress;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.GivenName;
import org.opensaml.saml.saml2.metadata.SurName;
import org.opensaml.saml.saml2.metadata.TelephoneNumber;
import org.w3c.dom.Attr;

public class ContactPersonUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      ContactPerson person = (ContactPerson)parentSAMLObject;
      if (childSAMLObject instanceof Extensions) {
         person.setExtensions((Extensions)childSAMLObject);
      } else if (childSAMLObject instanceof Company) {
         person.setCompany((Company)childSAMLObject);
      } else if (childSAMLObject instanceof GivenName) {
         person.setGivenName((GivenName)childSAMLObject);
      } else if (childSAMLObject instanceof SurName) {
         person.setSurName((SurName)childSAMLObject);
      } else if (childSAMLObject instanceof EmailAddress) {
         person.getEmailAddresses().add((EmailAddress)childSAMLObject);
      } else if (childSAMLObject instanceof TelephoneNumber) {
         person.getTelephoneNumbers().add((TelephoneNumber)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      ContactPerson person = (ContactPerson)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("contactType")) {
            if (ContactPersonTypeEnumeration.TECHNICAL.toString().equals(attribute.getValue())) {
               person.setType(ContactPersonTypeEnumeration.TECHNICAL);
            } else if (ContactPersonTypeEnumeration.SUPPORT.toString().equals(attribute.getValue())) {
               person.setType(ContactPersonTypeEnumeration.SUPPORT);
            } else if (ContactPersonTypeEnumeration.ADMINISTRATIVE.toString().equals(attribute.getValue())) {
               person.setType(ContactPersonTypeEnumeration.ADMINISTRATIVE);
            } else if (ContactPersonTypeEnumeration.BILLING.toString().equals(attribute.getValue())) {
               person.setType(ContactPersonTypeEnumeration.BILLING);
            } else if (ContactPersonTypeEnumeration.OTHER.toString().equals(attribute.getValue())) {
               person.setType(ContactPersonTypeEnumeration.OTHER);
            } else {
               super.processAttribute(samlObject, attribute);
            }
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         this.processUnknownAttribute(person, attribute);
      }

   }
}
