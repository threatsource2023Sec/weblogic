package org.opensaml.saml.saml2.metadata.impl;

import com.google.common.base.Strings;
import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.metadata.AffiliateMember;
import org.opensaml.saml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.xmlsec.signature.Signature;
import org.w3c.dom.Attr;

public class AffiliationDescriptorUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      AffiliationDescriptor descriptor = (AffiliationDescriptor)parentSAMLObject;
      if (childSAMLObject instanceof Extensions) {
         descriptor.setExtensions((Extensions)childSAMLObject);
      } else if (childSAMLObject instanceof Signature) {
         descriptor.setSignature((Signature)childSAMLObject);
      } else if (childSAMLObject instanceof AffiliateMember) {
         descriptor.getMembers().add((AffiliateMember)childSAMLObject);
      } else if (childSAMLObject instanceof KeyDescriptor) {
         descriptor.getKeyDescriptors().add((KeyDescriptor)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      AffiliationDescriptor descriptor = (AffiliationDescriptor)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("affiliationOwnerID")) {
            descriptor.setOwnerID(attribute.getValue());
         } else if (attribute.getLocalName().equals("ID")) {
            descriptor.setID(attribute.getValue());
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
         } else if (attribute.getLocalName().equals("validUntil") && !Strings.isNullOrEmpty(attribute.getValue())) {
            descriptor.setValidUntil(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else if (attribute.getLocalName().equals("cacheDuration")) {
            descriptor.setCacheDuration(DOMTypeSupport.durationToLong(attribute.getValue()));
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         this.processUnknownAttribute(descriptor, attribute);
      }

   }
}
