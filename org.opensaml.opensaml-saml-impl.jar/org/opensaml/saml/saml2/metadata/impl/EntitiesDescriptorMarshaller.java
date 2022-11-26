package org.opensaml.saml.saml2.metadata.impl;

import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class EntitiesDescriptorMarshaller extends AbstractSAMLObjectMarshaller {
   private final Logger log = LoggerFactory.getLogger(EntitiesDescriptorMarshaller.class);

   protected void marshallAttributes(XMLObject samlElement, Element domElement) {
      EntitiesDescriptor entitiesDescriptor = (EntitiesDescriptor)samlElement;
      if (entitiesDescriptor.getID() != null) {
         this.log.debug("Writing ID attribute to EntitiesDescriptor DOM element.");
         domElement.setAttributeNS((String)null, "ID", entitiesDescriptor.getID());
         domElement.setIdAttributeNS((String)null, "ID", true);
      }

      String cacheDuration;
      if (entitiesDescriptor.getValidUntil() != null) {
         this.log.debug("Writting validUntil attribute to EntitiesDescriptor DOM element");
         cacheDuration = SAMLConfigurationSupport.getSAMLDateFormatter().print(entitiesDescriptor.getValidUntil());
         domElement.setAttributeNS((String)null, "validUntil", cacheDuration);
      }

      if (entitiesDescriptor.getCacheDuration() != null) {
         this.log.debug("Writting cacheDuration attribute to EntitiesDescriptor DOM element");
         cacheDuration = DOMTypeSupport.longToDuration(entitiesDescriptor.getCacheDuration());
         domElement.setAttributeNS((String)null, "cacheDuration", cacheDuration);
      }

      if (entitiesDescriptor.getName() != null) {
         this.log.debug("Writting Name attribute to EntitiesDescriptor DOM element");
         domElement.setAttributeNS((String)null, "Name", entitiesDescriptor.getName());
      }

   }
}
