package org.opensaml.saml.metadata.support;

import java.util.Iterator;
import java.util.List;
import org.opensaml.saml.ext.saml2mdquery.AttributeQueryDescriptorType;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttributeConsumingServiceSelector {
   private Logger log = LoggerFactory.getLogger(AttributeConsumingServiceSelector.class);
   private Integer index;
   private RoleDescriptor roleDescriptor;
   private boolean onBadIndexUseDefault;

   public Integer getIndex() {
      return this.index;
   }

   public void setIndex(Integer requestedIndex) {
      this.index = requestedIndex;
   }

   public RoleDescriptor getRoleDescriptor() {
      return this.roleDescriptor;
   }

   public void setRoleDescriptor(RoleDescriptor descriptor) {
      this.roleDescriptor = descriptor;
   }

   public void setOnBadIndexUseDefault(boolean flag) {
      this.onBadIndexUseDefault = flag;
   }

   public boolean isOnBadIndexUseDefault() {
      return this.onBadIndexUseDefault;
   }

   public AttributeConsumingService selectService() {
      List candidates = this.getCandidates();
      if (candidates != null && !candidates.isEmpty()) {
         this.log.debug("AttributeConsumingService index was specified: {}", this.index != null);
         AttributeConsumingService acs = null;
         if (this.index != null) {
            acs = this.selectByIndex(candidates);
            if (acs == null && this.isOnBadIndexUseDefault()) {
               acs = this.selectDefault(candidates);
            }

            return acs;
         } else {
            return this.selectDefault(candidates);
         }
      } else {
         this.log.debug("AttributeConsumingService candidate list was empty, can not select service");
         return null;
      }
   }

   protected List getCandidates() {
      if (this.roleDescriptor == null) {
         this.log.debug("RoleDescriptor was not supplied, unable to select AttributeConsumingService");
         return null;
      } else if (this.roleDescriptor instanceof SPSSODescriptor) {
         this.log.debug("Resolving AttributeConsumingService candidates from SPSSODescriptor");
         return ((SPSSODescriptor)this.roleDescriptor).getAttributeConsumingServices();
      } else if (this.roleDescriptor instanceof AttributeQueryDescriptorType) {
         this.log.debug("Resolving AttributeConsumingService candidates from AttributeQueryDescriptorType");
         return ((AttributeQueryDescriptorType)this.roleDescriptor).getAttributeConsumingServices();
      } else {
         this.log.debug("Unable to resolve service candidates, role descriptor was of an unsupported type: {}", this.roleDescriptor.getClass().getName());
         return null;
      }
   }

   private AttributeConsumingService selectByIndex(List candidates) {
      this.log.debug("Selecting AttributeConsumingService by index");
      Iterator var2 = candidates.iterator();

      AttributeConsumingService attribCS;
      do {
         if (!var2.hasNext()) {
            this.log.debug("A service index of '{}' was specified, but was not found in metadata", this.index);
            return null;
         }

         attribCS = (AttributeConsumingService)var2.next();
      } while(this.index == null || this.index != attribCS.getIndex());

      this.log.debug("Selected AttributeConsumingService with index: {}", this.index);
      return attribCS;
   }

   private AttributeConsumingService selectDefault(List candidates) {
      this.log.debug("Selecting default AttributeConsumingService");
      AttributeConsumingService firstNoDefault = null;
      Iterator var3 = candidates.iterator();

      while(var3.hasNext()) {
         AttributeConsumingService attribCS = (AttributeConsumingService)var3.next();
         if (attribCS.isDefault()) {
            this.log.debug("Selected AttributeConsumingService with explicit isDefault of true");
            return attribCS;
         }

         if (firstNoDefault == null && attribCS.isDefaultXSBoolean() == null) {
            firstNoDefault = attribCS;
         }
      }

      if (firstNoDefault != null) {
         this.log.debug("Selected first AttributeConsumingService with no explicit isDefault");
         return firstNoDefault;
      } else {
         this.log.debug("Selected first AttributeConsumingService with explicit isDefault of false");
         return (AttributeConsumingService)candidates.get(0);
      }
   }
}
