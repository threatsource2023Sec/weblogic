package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Optional;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.metadata.IterableMetadataSource;
import org.opensaml.saml.metadata.resolver.BatchMetadataResolver;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.index.impl.MetadataIndexManager;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractBatchMetadataResolver extends AbstractMetadataResolver implements BatchMetadataResolver, IterableMetadataSource {
   private final Logger log = LoggerFactory.getLogger(AbstractBatchMetadataResolver.class);
   private boolean cacheSourceMetadata;
   private Set indexes = Collections.emptySet();
   private boolean resolveViaPredicatesOnly;

   public AbstractBatchMetadataResolver() {
      this.setCacheSourceMetadata(true);
   }

   public Iterator iterator() {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      return Collections.unmodifiableList(this.getBackingStore().getOrderedDescriptors()).iterator();
   }

   protected boolean isCacheSourceMetadata() {
      return this.cacheSourceMetadata;
   }

   protected void setCacheSourceMetadata(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.cacheSourceMetadata = flag;
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public Set getIndexes() {
      return ImmutableSet.copyOf(this.indexes);
   }

   public void setIndexes(@Nullable Set newIndexes) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      if (newIndexes == null) {
         this.indexes = Collections.emptySet();
      } else {
         this.indexes = new HashSet();
         this.indexes.addAll(Collections2.filter(newIndexes, Predicates.notNull()));
      }

   }

   public boolean isResolveViaPredicatesOnly() {
      return this.resolveViaPredicatesOnly;
   }

   public void setResolveViaPredicatesOnly(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.resolveViaPredicatesOnly = flag;
   }

   @Nonnull
   public Iterable resolve(CriteriaSet criteria) throws ResolverException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      EntityIdCriterion entityIdCriterion = (EntityIdCriterion)criteria.get(EntityIdCriterion.class);
      if (entityIdCriterion != null) {
         Iterable entityIdcandidates = this.lookupEntityID(entityIdCriterion.getEntityId());
         if (this.log.isDebugEnabled()) {
            this.log.debug("{} Resolved {} candidates via EntityIdCriterion: {}", new Object[]{this.getLogPrefix(), Iterables.size(entityIdcandidates), entityIdCriterion});
         }

         return this.predicateFilterCandidates(entityIdcandidates, criteria, false);
      } else {
         Optional indexedCandidates = this.lookupByIndexes(criteria);
         if (this.log.isDebugEnabled()) {
            if (indexedCandidates.isPresent()) {
               this.log.debug("{} Resolved {} candidates via secondary index lookup", this.getLogPrefix(), Iterables.size((Iterable)indexedCandidates.get()));
            } else {
               this.log.debug("{} Resolved no candidates via secondary index lookup (Optional indicated result was absent)", this.getLogPrefix());
            }
         }

         if (indexedCandidates.isPresent()) {
            this.log.debug("{} Performing predicate filtering of resolved secondary indexed candidates", this.getLogPrefix());
            return this.predicateFilterCandidates((Iterable)indexedCandidates.get(), criteria, false);
         } else if (this.isResolveViaPredicatesOnly()) {
            this.log.debug("{} Performing predicate filtering of entire metadata collection", this.getLogPrefix());
            return this.predicateFilterCandidates(this, criteria, true);
         } else {
            this.log.debug("{} Resolved no secondary indexed candidates, returning empty result", this.getLogPrefix());
            return Collections.emptySet();
         }
      }
   }

   @Nonnull
   @NonnullElements
   protected Optional lookupByIndexes(@Nonnull CriteriaSet criteria) {
      return this.getBackingStore().getSecondaryIndexManager().lookupIndexedItems(criteria);
   }

   protected void indexEntityDescriptor(@Nonnull EntityDescriptor entityDescriptor, @Nonnull AbstractMetadataResolver.EntityBackingStore backingStore) {
      super.indexEntityDescriptor(entityDescriptor, backingStore);
      ((BatchEntityBackingStore)backingStore).getSecondaryIndexManager().indexEntityDescriptor(entityDescriptor);
   }

   @Nonnull
   protected BatchEntityBackingStore createNewBackingStore() {
      return new BatchEntityBackingStore(this.getIndexes());
   }

   @Nonnull
   protected BatchEntityBackingStore getBackingStore() {
      return (BatchEntityBackingStore)super.getBackingStore();
   }

   protected void initMetadataResolver() throws ComponentInitializationException {
      super.initMetadataResolver();
      this.setBackingStore(this.createNewBackingStore());
   }

   @Nullable
   protected XMLObject getCachedOriginalMetadata() {
      return this.getBackingStore().getCachedOriginalMetadata();
   }

   @Nullable
   protected XMLObject getCachedFilteredMetadata() {
      return this.getBackingStore().getCachedFilteredMetadata();
   }

   @Nonnull
   protected BatchEntityBackingStore preProcessNewMetadata(@Nonnull XMLObject root) throws FilterException {
      BatchEntityBackingStore newBackingStore = this.createNewBackingStore();
      XMLObject filteredMetadata = this.filterMetadata(root);
      if (this.isCacheSourceMetadata()) {
         newBackingStore.setCachedOriginalMetadata(root);
         newBackingStore.setCachedFilteredMetadata(filteredMetadata);
      }

      if (filteredMetadata == null) {
         this.log.info("{} Metadata filtering process produced a null document, resulting in an empty data set", this.getLogPrefix());
         return newBackingStore;
      } else {
         if (filteredMetadata instanceof EntityDescriptor) {
            this.preProcessEntityDescriptor((EntityDescriptor)filteredMetadata, newBackingStore);
         } else if (filteredMetadata instanceof EntitiesDescriptor) {
            this.preProcessEntitiesDescriptor((EntitiesDescriptor)filteredMetadata, newBackingStore);
         } else {
            this.log.warn("{} Document root was neither an EntityDescriptor nor an EntitiesDescriptor: {}", this.getLogPrefix(), root.getClass().getName());
         }

         return newBackingStore;
      }
   }

   protected class BatchEntityBackingStore extends AbstractMetadataResolver.EntityBackingStore {
      private XMLObject cachedOriginalMetadata;
      private XMLObject cachedFilteredMetadata;
      private MetadataIndexManager secondaryIndexManager;

      protected BatchEntityBackingStore(@Nullable @NonnullElements @Unmodifiable @NotLive Set initIndexes) {
         super();
         this.secondaryIndexManager = new MetadataIndexManager(initIndexes, new MetadataIndexManager.IdentityExtractionFunction());
      }

      public XMLObject getCachedOriginalMetadata() {
         return this.cachedOriginalMetadata;
      }

      public void setCachedOriginalMetadata(XMLObject metadata) {
         this.cachedOriginalMetadata = metadata;
      }

      public XMLObject getCachedFilteredMetadata() {
         return this.cachedFilteredMetadata;
      }

      public void setCachedFilteredMetadata(XMLObject metadata) {
         this.cachedFilteredMetadata = metadata;
      }

      public MetadataIndexManager getSecondaryIndexManager() {
         return this.secondaryIndexManager;
      }
   }
}
