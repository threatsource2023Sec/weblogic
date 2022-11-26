package org.opensaml.saml.metadata.resolver.index.impl;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.saml.metadata.resolver.index.MetadataIndex;
import org.opensaml.saml.metadata.resolver.index.MetadataIndexKey;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataIndexManager {
   private Logger log = LoggerFactory.getLogger(MetadataIndexManager.class);
   private Map indexes;
   private Function entityDescriptorFunction;

   public MetadataIndexManager(@Nullable @NonnullElements @Unmodifiable @NotLive Set initIndexes, @Nonnull Function extractionFunction) {
      this.entityDescriptorFunction = (Function)Constraint.isNotNull(extractionFunction, "EntityDescriptor extraction function was null");
      this.indexes = new ConcurrentHashMap();
      if (initIndexes != null) {
         Iterator var3 = initIndexes.iterator();

         while(var3.hasNext()) {
            MetadataIndex index = (MetadataIndex)var3.next();
            this.log.trace("Initializing manager for index: {}", index);
            this.indexes.put(index, new MetadataIndexStore());
         }
      }

   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public Set getIndexes() {
      return ImmutableSet.copyOf(this.indexes.keySet());
   }

   @Nullable
   protected MetadataIndexStore getStore(@Nonnull MetadataIndex index) {
      Constraint.isNotNull(index, "MetadataIndex was null");
      return (MetadataIndexStore)this.indexes.get(index);
   }

   @Nonnull
   @NonnullElements
   public Optional lookupIndexedItems(@Nonnull CriteriaSet criteria) {
      Set items = new HashSet();
      Iterator var3 = this.indexes.keySet().iterator();

      MetadataIndex index;
      do {
         Set keys;
         do {
            do {
               if (!var3.hasNext()) {
                  if (items.isEmpty()) {
                     return Optional.absent();
                  }

                  return Optional.of(items);
               }

               index = (MetadataIndex)var3.next();
               keys = index.generateKeys(criteria);
            } while(keys == null);
         } while(keys.isEmpty());

         LazySet indexResult = new LazySet();
         MetadataIndexStore indexStore = this.getStore(index);
         Iterator var8 = keys.iterator();

         while(var8.hasNext()) {
            MetadataIndexKey key = (MetadataIndexKey)var8.next();
            indexResult.addAll(indexStore.lookup(key));
         }

         this.log.trace("MetadataIndex '{}' produced results: {}", index, indexResult);
         if (items.isEmpty()) {
            items.addAll(indexResult);
         } else {
            items.retainAll(indexResult);
         }
      } while(!items.isEmpty());

      this.log.trace("Accumulator intersected with MetadataIndex '{}' result produced empty result, terminating early and returning empty result set", index);
      return Optional.of(Collections.emptySet());
   }

   public void indexEntityDescriptor(@Nonnull EntityDescriptor descriptor) {
      Object item = this.entityDescriptorFunction.apply(descriptor);
      if (item != null) {
         Iterator var3 = this.indexes.keySet().iterator();

         while(true) {
            MetadataIndex index;
            Set keys;
            do {
               do {
                  if (!var3.hasNext()) {
                     return;
                  }

                  index = (MetadataIndex)var3.next();
                  keys = index.generateKeys(descriptor);
               } while(keys == null);
            } while(keys.isEmpty());

            MetadataIndexStore store = this.getStore(index);
            Iterator var7 = keys.iterator();

            while(var7.hasNext()) {
               MetadataIndexKey key = (MetadataIndexKey)var7.next();
               this.log.trace("Indexing metadata: index '{}', key '{}', data item '{}'", new Object[]{index, key, item});
               store.add(key, item);
            }
         }
      } else {
         this.log.trace("Unable to extract indexed data item from EntityDescriptor");
      }
   }

   public static class EntityIDExtractionFunction implements Function {
      public String apply(EntityDescriptor input) {
         return input == null ? null : StringSupport.trimOrNull(input.getEntityID());
      }
   }

   public static class IdentityExtractionFunction implements Function {
      public EntityDescriptor apply(EntityDescriptor input) {
         return input;
      }
   }
}
