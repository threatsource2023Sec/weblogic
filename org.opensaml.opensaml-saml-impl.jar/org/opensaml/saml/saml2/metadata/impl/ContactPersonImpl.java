package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.Company;
import org.opensaml.saml.saml2.metadata.ContactPerson;
import org.opensaml.saml.saml2.metadata.ContactPersonTypeEnumeration;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.GivenName;
import org.opensaml.saml.saml2.metadata.SurName;

public class ContactPersonImpl extends AbstractSAMLObject implements ContactPerson {
   private ContactPersonTypeEnumeration type;
   private Extensions extensions;
   private Company company;
   private GivenName givenName;
   private SurName surName;
   private final AttributeMap unknownAttributes = new AttributeMap(this);
   private final XMLObjectChildrenList emailAddresses = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList telephoneNumbers = new XMLObjectChildrenList(this);

   protected ContactPersonImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public ContactPersonTypeEnumeration getType() {
      return this.type;
   }

   public void setType(ContactPersonTypeEnumeration theType) {
      this.type = (ContactPersonTypeEnumeration)this.prepareForAssignment(this.type, theType);
   }

   public Extensions getExtensions() {
      return this.extensions;
   }

   public void setExtensions(Extensions theExtensions) {
      this.extensions = (Extensions)this.prepareForAssignment(this.extensions, theExtensions);
   }

   public Company getCompany() {
      return this.company;
   }

   public void setCompany(Company theCompany) {
      this.company = (Company)this.prepareForAssignment(this.company, theCompany);
   }

   public GivenName getGivenName() {
      return this.givenName;
   }

   public void setGivenName(GivenName name) {
      this.givenName = (GivenName)this.prepareForAssignment(this.givenName, name);
   }

   public SurName getSurName() {
      return this.surName;
   }

   public void setSurName(SurName name) {
      this.surName = (SurName)this.prepareForAssignment(this.surName, name);
   }

   public List getEmailAddresses() {
      return this.emailAddresses;
   }

   public List getTelephoneNumbers() {
      return this.telephoneNumbers;
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.add(this.extensions);
      children.add(this.company);
      children.add(this.givenName);
      children.add(this.surName);
      children.addAll(this.emailAddresses);
      children.addAll(this.telephoneNumbers);
      return children;
   }
}
