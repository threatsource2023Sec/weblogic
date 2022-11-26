package org.opensaml.saml.metadata.resolver.filter.impl;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdattr.EntityAttributes;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityAttributesFilter implements MetadataFilter {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(EntityAttributesFilter.class);
   @Nonnull
   @NonnullElements
   private Multimap applyMap;
   @Nonnull
   private final SAMLObjectBuilder extBuilder;
   @Nonnull
   private final SAMLObjectBuilder entityAttributesBuilder;

   public EntityAttributesFilter() {
      this.extBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Extensions.DEFAULT_ELEMENT_NAME);
      this.entityAttributesBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(EntityAttributes.DEFAULT_ELEMENT_NAME);
   }

   public void setRules(@Nonnull @NonnullElements Map rules) {
      Constraint.isNotNull(rules, "Rules map cannot be null");
      this.applyMap = ArrayListMultimap.create(rules.size(), 1);
      Iterator var2 = rules.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         if (entry.getKey() != null && entry.getValue() != null) {
            this.applyMap.putAll(entry.getKey(), Collections2.filter((Collection)entry.getValue(), Predicates.notNull()));
         }
      }

   }

   @Nullable
   public XMLObject filter(@Nullable XMLObject metadata) throws FilterException {
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

         Extensions extensions = descriptor.getExtensions();
         if (extensions == null) {
            extensions = (Extensions)this.extBuilder.buildObject();
            descriptor.setExtensions(extensions);
         }

         Collection entityAttributesCollection = extensions.getUnknownXMLObjects(EntityAttributes.DEFAULT_ELEMENT_NAME);
         if (entityAttributesCollection.isEmpty()) {
            entityAttributesCollection.add(this.entityAttributesBuilder.buildObject());
         }

         EntityAttributes entityAttributes = (EntityAttributes)entityAttributesCollection.iterator().next();
         Iterator var7 = ((Collection)entry.getValue()).iterator();

         while(var7.hasNext()) {
            Attribute attribute = (Attribute)var7.next();

            try {
               this.log.info("Adding EntityAttribute ({}) to EntityDescriptor ({})", attribute.getName(), descriptor.getEntityID());
               Attribute copy = (Attribute)XMLObjectSupport.cloneXMLObject(attribute);
               entityAttributes.getAttributes().add(copy);
            } catch (UnmarshallingException | MarshallingException var10) {
               this.log.error("Error cloning Attribute", var10);
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
