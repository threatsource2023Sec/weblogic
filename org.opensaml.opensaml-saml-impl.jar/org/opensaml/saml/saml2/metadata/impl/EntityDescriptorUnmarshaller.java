package org.opensaml.saml.saml2.metadata.impl;

import com.google.common.base.Strings;
import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.metadata.AdditionalMetadataLocation;
import org.opensaml.saml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml.saml2.metadata.ContactPerson;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.Organization;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.xmlsec.signature.Signature;
import org.w3c.dom.Attr;

public class EntityDescriptorUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      EntityDescriptor entityDescriptor = (EntityDescriptor)parentSAMLObject;
      if (childSAMLObject instanceof Extensions) {
         entityDescriptor.setExtensions((Extensions)childSAMLObject);
      } else if (childSAMLObject instanceof Signature) {
         entityDescriptor.setSignature((Signature)childSAMLObject);
      } else if (childSAMLObject instanceof RoleDescriptor) {
         entityDescriptor.getRoleDescriptors().add((RoleDescriptor)childSAMLObject);
      } else if (childSAMLObject instanceof AffiliationDescriptor) {
         entityDescriptor.setAffiliationDescriptor((AffiliationDescriptor)childSAMLObject);
      } else if (childSAMLObject instanceof Organization) {
         entityDescriptor.setOrganization((Organization)childSAMLObject);
      } else if (childSAMLObject instanceof ContactPerson) {
         entityDescriptor.getContactPersons().add((ContactPerson)childSAMLObject);
      } else if (childSAMLObject instanceof AdditionalMetadataLocation) {
         entityDescriptor.getAdditionalMetadataLocations().add((AdditionalMetadataLocation)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      EntityDescriptor entityDescriptor = (EntityDescriptor)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("entityID")) {
            entityDescriptor.setEntityID(attribute.getValue());
         } else if (attribute.getLocalName().equals("ID")) {
            entityDescriptor.setID(attribute.getValue());
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
         } else if (attribute.getLocalName().equals("validUntil") && !Strings.isNullOrEmpty(attribute.getValue())) {
            entityDescriptor.setValidUntil(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else if (attribute.getLocalName().equals("cacheDuration")) {
            entityDescriptor.setCacheDuration(DOMTypeSupport.durationToLong(attribute.getValue()));
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         this.processUnknownAttribute(entityDescriptor, attribute);
      }

   }
}
