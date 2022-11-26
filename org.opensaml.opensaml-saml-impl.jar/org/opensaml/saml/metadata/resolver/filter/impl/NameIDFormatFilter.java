package org.opensaml.saml.metadata.resolver.filter.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.saml2.metadata.AttributeAuthorityDescriptor;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.PDPDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameIDFormatFilter extends AbstractInitializableComponent implements MetadataFilter {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(NameIDFormatFilter.class);
   @Nonnull
   @NonnullElements
   private Multimap applyMap;
   @Nonnull
   private final SAMLObjectBuilder formatBuilder;

   public NameIDFormatFilter() {
      this.formatBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(NameIDFormat.DEFAULT_ELEMENT_NAME);
   }

   public void setRules(@Nonnull @NonnullElements Map rules) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      Constraint.isNotNull(rules, "Rules map cannot be null");
      this.applyMap = ArrayListMultimap.create(rules.size(), 1);
      Iterator var2 = rules.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         if (entry.getKey() != null && entry.getValue() != null) {
            this.applyMap.putAll(entry.getKey(), StringSupport.normalizeStringCollection((Collection)entry.getValue()));
         }
      }

   }

   @Nullable
   public XMLObject filter(@Nullable XMLObject metadata) throws FilterException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      if (metadata == null) {
         return null;
      } else {
         if (metadata instanceof EntitiesDescriptor) {
            this.filterEntitiesDescriptor((EntitiesDescriptor)metadata);
         } else {
            this.filterEntityDescriptor((EntityDescriptor)metadata);
         }

         return metadata;
      }
   }

   protected void filterEntityDescriptor(@Nonnull EntityDescriptor descriptor) {
      Iterator var2 = this.applyMap.asMap().entrySet().iterator();

      while(true) {
         Map.Entry entry;
         do {
            do {
               if (!var2.hasNext()) {
                  return;
               }

               entry = (Map.Entry)var2.next();
            } while(((Collection)entry.getValue()).isEmpty());
         } while(!((Predicate)entry.getKey()).apply(descriptor));

         Iterator var4 = ((Collection)entry.getValue()).iterator();

         while(var4.hasNext()) {
            String format = (String)var4.next();
            this.log.info("Adding NameIDFormat '{}' to EntityDescriptor '{}'", format, descriptor.getEntityID());
            Iterator var6 = descriptor.getRoleDescriptors().iterator();

            while(var6.hasNext()) {
               RoleDescriptor role = (RoleDescriptor)var6.next();
               NameIDFormat nif;
               if (role instanceof SPSSODescriptor) {
                  nif = (NameIDFormat)this.formatBuilder.buildObject();
                  nif.setFormat(format);
                  ((SPSSODescriptor)role).getNameIDFormats().add(nif);
               } else if (role instanceof AttributeAuthorityDescriptor) {
                  nif = (NameIDFormat)this.formatBuilder.buildObject();
                  nif.setFormat(format);
                  ((AttributeAuthorityDescriptor)role).getNameIDFormats().add(nif);
               } else if (role instanceof PDPDescriptor) {
                  nif = (NameIDFormat)this.formatBuilder.buildObject();
                  nif.setFormat(format);
                  ((PDPDescriptor)role).getNameIDFormats().add(nif);
               }
            }
         }
      }
   }

   protected void filterEntitiesDescriptor(@Nonnull EntitiesDescriptor descriptor) {
      Iterator var2 = descriptor.getEntitiesDescriptors().iterator();

      while(var2.hasNext()) {
         EntitiesDescriptor group = (EntitiesDescriptor)var2.next();
         this.filterEntitiesDescriptor(group);
      }

      var2 = descriptor.getEntityDescriptors().iterator();

      while(var2.hasNext()) {
         EntityDescriptor entity = (EntityDescriptor)var2.next();
         this.filterEntityDescriptor(entity);
      }

   }
}
