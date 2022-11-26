package org.opensaml.saml.saml2.metadata.impl;

import java.util.Iterator;
import java.util.List;
import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public abstract class RoleDescriptorMarshaller extends AbstractSAMLObjectMarshaller {
   private final Logger log = LoggerFactory.getLogger(RoleDescriptorMarshaller.class);

   protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      RoleDescriptor roleDescriptor = (RoleDescriptor)samlElement;
      if (roleDescriptor.getID() != null) {
         this.log.trace("Writing ID attribute to RoleDescriptor DOM element");
         domElement.setAttributeNS((String)null, "ID", roleDescriptor.getID());
         domElement.setIdAttributeNS((String)null, "ID", true);
      }

      String cacheDuration;
      if (roleDescriptor.getValidUntil() != null) {
         this.log.trace("Writting validUntil attribute to RoleDescriptor DOM element");
         cacheDuration = SAMLConfigurationSupport.getSAMLDateFormatter().print(roleDescriptor.getValidUntil());
         domElement.setAttributeNS((String)null, "validUntil", cacheDuration);
      }

      if (roleDescriptor.getCacheDuration() != null) {
         this.log.trace("Writting cacheDuration attribute to EntitiesDescriptor DOM element");
         cacheDuration = DOMTypeSupport.longToDuration(roleDescriptor.getCacheDuration());
         domElement.setAttributeNS((String)null, "cacheDuration", cacheDuration);
      }

      List supportedProtocols = roleDescriptor.getSupportedProtocols();
      if (supportedProtocols != null && supportedProtocols.size() > 0) {
         this.log.trace("Writting protocolSupportEnumberation attribute to RoleDescriptor DOM element");
         StringBuilder builder = new StringBuilder();
         Iterator var6 = supportedProtocols.iterator();

         while(var6.hasNext()) {
            String protocol = (String)var6.next();
            builder.append(protocol);
            builder.append(" ");
         }

         domElement.setAttributeNS((String)null, "protocolSupportEnumeration", builder.toString().trim());
      }

      if (roleDescriptor.getErrorURL() != null) {
         this.log.trace("Writting errorURL attribute to RoleDescriptor DOM element");
         domElement.setAttributeNS((String)null, "errorURL", roleDescriptor.getErrorURL());
      }

      this.marshallUnknownAttributes(roleDescriptor, domElement);
   }
}
