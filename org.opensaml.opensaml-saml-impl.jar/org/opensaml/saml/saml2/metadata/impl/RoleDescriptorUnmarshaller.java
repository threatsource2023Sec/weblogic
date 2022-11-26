package org.opensaml.saml.saml2.metadata.impl;

import com.google.common.base.Strings;
import java.util.StringTokenizer;
import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.metadata.ContactPerson;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml.saml2.metadata.Organization;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.xmlsec.signature.Signature;
import org.w3c.dom.Attr;

public abstract class RoleDescriptorUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      RoleDescriptor roleDescriptor = (RoleDescriptor)parentSAMLObject;
      if (childSAMLObject instanceof Extensions) {
         roleDescriptor.setExtensions((Extensions)childSAMLObject);
      } else if (childSAMLObject instanceof Signature) {
         roleDescriptor.setSignature((Signature)childSAMLObject);
      } else if (childSAMLObject instanceof KeyDescriptor) {
         roleDescriptor.getKeyDescriptors().add((KeyDescriptor)childSAMLObject);
      } else if (childSAMLObject instanceof Organization) {
         roleDescriptor.setOrganization((Organization)childSAMLObject);
      } else if (childSAMLObject instanceof ContactPerson) {
         roleDescriptor.getContactPersons().add((ContactPerson)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      RoleDescriptor roleDescriptor = (RoleDescriptor)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("ID")) {
            roleDescriptor.setID(attribute.getValue());
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
         } else if (attribute.getLocalName().equals("validUntil") && !Strings.isNullOrEmpty(attribute.getValue())) {
            roleDescriptor.setValidUntil(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else if (attribute.getLocalName().equals("cacheDuration")) {
            roleDescriptor.setCacheDuration(DOMTypeSupport.durationToLong(attribute.getValue()));
         } else if (attribute.getLocalName().equals("protocolSupportEnumeration")) {
            StringTokenizer protocolTokenizer = new StringTokenizer(attribute.getValue(), " ");

            while(protocolTokenizer.hasMoreTokens()) {
               roleDescriptor.addSupportedProtocol(protocolTokenizer.nextToken());
            }
         } else if (attribute.getLocalName().equals("errorURL")) {
            roleDescriptor.setErrorURL(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         this.processUnknownAttribute(roleDescriptor, attribute);
      }

   }
}
