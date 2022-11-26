package org.opensaml.saml.metadata.resolver.filter.impl;

import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PredicateFilter implements MetadataFilter {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(PredicateFilter.class);
   @Nonnull
   private final Direction direction;
   @Nonnull
   private final Predicate condition;
   private boolean removeEmptyEntitiesDescriptors;

   public PredicateFilter(@Nonnull Direction dir, @Nonnull Predicate theCondition) {
      this.condition = (Predicate)Constraint.isNotNull(theCondition, "Matching condition cannot be null");
      this.direction = (Direction)Constraint.isNotNull(dir, "Direction cannot be null");
      this.removeEmptyEntitiesDescriptors = true;
   }

   @Nonnull
   public Direction getDirection() {
      return this.direction;
   }

   @Nonnull
   public Predicate getCondition() {
      return this.condition;
   }

   public boolean getRemoveEmptyEntitiesDescriptors() {
      return this.removeEmptyEntitiesDescriptors;
   }

   public void setRemoveEmptyEntitiesDescriptors(boolean remove) {
      this.removeEmptyEntitiesDescriptors = remove;
   }

   public XMLObject filter(@Nullable XMLObject metadata) throws FilterException {
      if (metadata == null) {
         return null;
      } else if (metadata instanceof EntitiesDescriptor) {
         this.filterEntitiesDescriptor((EntitiesDescriptor)metadata);
         return metadata;
      } else if (metadata instanceof EntityDescriptor) {
         EntityDescriptor entity = (EntityDescriptor)metadata;
         if (PredicateFilter.Direction.EXCLUDE.equals(this.direction) == this.condition.apply(entity)) {
            this.log.trace("Filtering out entity {} ", entity.getEntityID());
            return null;
         } else {
            return metadata;
         }
      } else {
         this.log.error("Unrecognised metadata type {}", metadata.getClass().getSimpleName());
         return null;
      }
   }

   protected void filterEntitiesDescriptor(@Nonnull EntitiesDescriptor descriptor) {
      List entityDescriptors = descriptor.getEntityDescriptors();
      if (!entityDescriptors.isEmpty()) {
         List emptyEntityDescriptors = new ArrayList();
         Iterator entityDescriptorsItr = entityDescriptors.iterator();

         while(entityDescriptorsItr.hasNext()) {
            EntityDescriptor entityDescriptor = (EntityDescriptor)entityDescriptorsItr.next();
            if (PredicateFilter.Direction.EXCLUDE.equals(this.direction) == this.condition.apply(entityDescriptor)) {
               this.log.trace("Filtering out entity {} from group {}", entityDescriptor.getEntityID(), descriptor.getName());
               emptyEntityDescriptors.add(entityDescriptor);
            }
         }

         entityDescriptors.removeAll(emptyEntityDescriptors);
      }

      List entitiesDescriptors = descriptor.getEntitiesDescriptors();
      if (!entitiesDescriptors.isEmpty()) {
         List emptyEntitiesDescriptors = new ArrayList();
         Iterator entitiesDescriptorsItr = entitiesDescriptors.iterator();

         while(entitiesDescriptorsItr.hasNext()) {
            EntitiesDescriptor entitiesDescriptor = (EntitiesDescriptor)entitiesDescriptorsItr.next();
            this.filterEntitiesDescriptor(entitiesDescriptor);
            if (this.getRemoveEmptyEntitiesDescriptors() && entitiesDescriptor.getEntityDescriptors().isEmpty() && entitiesDescriptor.getEntitiesDescriptors().isEmpty()) {
               this.log.trace("Filtering out empty group {} from group {}", entitiesDescriptor.getName(), descriptor.getName());
               emptyEntitiesDescriptors.add(entitiesDescriptor);
            }
         }

         entitiesDescriptors.removeAll(emptyEntitiesDescriptors);
      }

   }

   public static enum Direction {
      INCLUDE,
      EXCLUDE;
   }
}
