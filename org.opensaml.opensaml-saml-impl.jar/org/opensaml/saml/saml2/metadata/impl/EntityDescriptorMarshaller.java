package org.opensaml.saml.saml2.metadata.impl;

import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class EntityDescriptorMarshaller extends AbstractSAMLObjectMarshaller {
   private final Logger log = LoggerFactory.getLogger(EntityDescriptorMarshaller.class);

   protected void marshallAttributes(XMLObject samlElement, Element domElement) {
      EntityDescriptor entityDescriptor = (EntityDescriptor)samlElement;
      if (entityDescriptor.getEntityID() != null) {
         domElement.setAttributeNS((String)null, "entityID", entityDescriptor.getEntityID());
      }

      if (entityDescriptor.getID() != null) {
         domElement.setAttributeNS((String)null, "ID", entityDescriptor.getID());
         domElement.setIdAttributeNS((String)null, "ID", true);
      }

      String cacheDuration;
      if (entityDescriptor.getValidUntil() != null) {
         this.log.debug("Writting validUntil attribute to EntityDescriptor DOM element");
         cacheDuration = SAMLConfigurationSupport.getSAMLDateFormatter().print(entityDescriptor.getValidUntil());
         domElement.setAttributeNS((String)null, "validUntil", cacheDuration);
      }

      if (entityDescriptor.getCacheDuration() != null) {
         this.log.debug("Writting cacheDuration attribute to EntityDescriptor DOM element");
         cacheDuration = DOMTypeSupport.longToDuration(entityDescriptor.getCacheDuration());
         domElement.setAttributeNS((String)null, "cacheDuration", cacheDuration);
      }

      this.marshallUnknownAttributes(entityDescriptor, domElement);
   }
}
