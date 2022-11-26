package org.opensaml.saml.saml2.metadata.impl;

import com.google.common.base.Strings;
import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.xmlsec.signature.Signature;
import org.w3c.dom.Attr;

public class EntitiesDescriptorUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      EntitiesDescriptor entitiesDescriptor = (EntitiesDescriptor)parentSAMLObject;
      if (childSAMLObject instanceof Extensions) {
         entitiesDescriptor.setExtensions((Extensions)childSAMLObject);
      } else if (childSAMLObject instanceof EntitiesDescriptor) {
         entitiesDescriptor.getEntitiesDescriptors().add((EntitiesDescriptor)childSAMLObject);
      } else if (childSAMLObject instanceof EntityDescriptor) {
         entitiesDescriptor.getEntityDescriptors().add((EntityDescriptor)childSAMLObject);
      } else if (childSAMLObject instanceof Signature) {
         entitiesDescriptor.setSignature((Signature)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      EntitiesDescriptor entitiesDescriptor = (EntitiesDescriptor)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("ID")) {
            entitiesDescriptor.setID(attribute.getValue());
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
         } else if (attribute.getLocalName().equals("validUntil") && !Strings.isNullOrEmpty(attribute.getValue())) {
            entitiesDescriptor.setValidUntil(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else if (attribute.getLocalName().equals("cacheDuration")) {
            entitiesDescriptor.setCacheDuration(new Long(DOMTypeSupport.durationToLong(attribute.getValue())));
         } else if (attribute.getLocalName().equals("Name")) {
            entitiesDescriptor.setName(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
