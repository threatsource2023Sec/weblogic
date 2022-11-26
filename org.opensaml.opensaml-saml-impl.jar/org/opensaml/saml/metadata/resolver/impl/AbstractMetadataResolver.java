package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiableInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.CriterionPredicateRegistry;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import net.shibboleth.utilities.java.support.resolver.ResolverSupport;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.criterion.SatisfyAnyCriterion;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallerFactory;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.metadata.criteria.entity.EvaluableEntityDescriptorCriterion;
import org.opensaml.saml.metadata.criteria.entity.impl.EntityDescriptorCriterionPredicateRegistry;
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.saml2.common.SAML2Support;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public abstract class AbstractMetadataResolver extends AbstractIdentifiableInitializableComponent implements MetadataResolver {
   private final Logger log = LoggerFactory.getLogger(AbstractMetadataResolver.class);
   private UnmarshallerFactory unmarshallerFactory = XMLObjectProviderRegistrySupport.getUnmarshallerFactory();
   private boolean requireValidMetadata = true;
   private MetadataFilter mdFilter;
   private String logPrefix;
   private boolean failFastInitialization = true;
   private EntityBackingStore entityBackingStore;
   private ParserPool parser;
   private boolean satisfyAnyPredicates;
   private CriterionPredicateRegistry criterionPredicateRegistry;
   private boolean useDefaultPredicateRegistry = true;

   public boolean isRequireValidMetadata() {
      return this.requireValidMetadata;
   }

   public void setRequireValidMetadata(boolean require) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.requireValidMetadata = require;
   }

   @Nullable
   public MetadataFilter getMetadataFilter() {
      return this.mdFilter;
   }

   public void setMetadataFilter(@Nullable MetadataFilter newFilter) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.mdFilter = newFilter;
   }

   public boolean isFailFastInitialization() {
      return this.failFastInitialization;
   }

   public void setFailFastInitialization(boolean failFast) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.failFastInitialization = failFast;
   }

   @Nonnull
   public ParserPool getParserPool() {
      return this.parser;
   }

   public void setParserPool(@Nonnull ParserPool pool) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.parser = (ParserPool)Constraint.isNotNull(pool, "ParserPool may not be null");
   }

   public boolean isSatisfyAnyPredicates() {
      return this.satisfyAnyPredicates;
   }

   public void setSatisfyAnyPredicates(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.satisfyAnyPredicates = flag;
   }

   @NonnullAfterInit
   public CriterionPredicateRegistry getCriterionPredicateRegistry() {
      return this.criterionPredicateRegistry;
   }

   public void setCriterionPredicateRegistry(@Nullable CriterionPredicateRegistry registry) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.criterionPredicateRegistry = registry;
   }

   public boolean isUseDefaultPredicateRegistry() {
      return this.useDefaultPredicateRegistry;
   }

   public void setUseDefaultPredicateRegistry(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.useDefaultPredicateRegistry = flag;
   }

   @Nullable
   public EntityDescriptor resolveSingle(CriteriaSet criteria) throws ResolverException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      Iterable iterable = this.resolve(criteria);
      if (iterable != null) {
         Iterator iterator = iterable.iterator();
         if (iterator != null && iterator.hasNext()) {
            return (EntityDescriptor)iterator.next();
         }
      }

      return null;
   }

   protected UnmarshallerFactory getUnmarshallerFactory() {
      return this.unmarshallerFactory;
   }

   protected final void doInitialize() throws ComponentInitializationException {
      super.doInitialize();

      try {
         this.initMetadataResolver();
      } catch (ComponentInitializationException var2) {
         if (this.failFastInitialization) {
            this.log.error("{} Metadata provider failed to properly initialize, fail-fast=true, halting", this.getLogPrefix(), var2);
            throw var2;
         }

         this.log.error("{} Metadata provider failed to properly initialize, fail-fast=false, continuing on in a degraded state", this.getLogPrefix(), var2);
      }

   }

   protected void doDestroy() {
      this.unmarshallerFactory = null;
      this.mdFilter = null;
      this.entityBackingStore = null;
      this.parser = null;
      this.criterionPredicateRegistry = null;
      super.doDestroy();
   }

   protected void initMetadataResolver() throws ComponentInitializationException {
      if (this.getCriterionPredicateRegistry() == null && this.isUseDefaultPredicateRegistry()) {
         this.setCriterionPredicateRegistry(new EntityDescriptorCriterionPredicateRegistry());
      }

   }

   @Nonnull
   protected XMLObject unmarshallMetadata(@Nonnull InputStream metadataInput) throws UnmarshallingException {
      XMLObject var5;
      try {
         if (this.parser == null) {
            throw new UnmarshallingException("ParserPool is null, can't parse input stream");
         }

         this.log.trace("{} Parsing retrieved metadata into a DOM object", this.getLogPrefix());
         Document mdDocument = this.parser.parse(metadataInput);
         this.log.trace("{} Unmarshalling and caching metadata DOM", this.getLogPrefix());
         Unmarshaller unmarshaller = this.getUnmarshallerFactory().getUnmarshaller(mdDocument.getDocumentElement());
         if (unmarshaller == null) {
            String msg = "No unmarshaller registered for document element " + QNameSupport.getNodeQName(mdDocument.getDocumentElement());
            this.log.error("{} " + msg, this.getLogPrefix());
            throw new UnmarshallingException(msg);
         }

         XMLObject metadata = unmarshaller.unmarshall(mdDocument.getDocumentElement());
         var5 = metadata;
      } catch (Exception var14) {
         throw new UnmarshallingException(var14);
      } finally {
         try {
            metadataInput.close();
         } catch (IOException var13) {
            this.log.debug("{} Failed to close input: {}", this.getLogPrefix(), var13);
         }

      }

      return var5;
   }

   @Nullable
   protected XMLObject filterMetadata(@Nullable XMLObject metadata) throws FilterException {
      if (this.getMetadataFilter() != null) {
         this.log.debug("{} Applying metadata filter", this.getLogPrefix());
         return this.getMetadataFilter().filter(metadata);
      } else {
         return metadata;
      }
   }

   protected void releaseMetadataDOM(@Nullable XMLObject metadata) {
      if (metadata != null) {
         metadata.releaseDOM();
         metadata.releaseChildrenDOM(true);
      }

   }

   protected boolean isValid(@Nullable XMLObject descriptor) {
      if (descriptor == null) {
         return false;
      } else {
         return !this.isRequireValidMetadata() ? true : SAML2Support.isValid(descriptor);
      }
   }

   @Nonnull
   @NonnullElements
   protected List lookupEntityID(@Nonnull @NotEmpty String entityID) throws ResolverException {
      if (!this.isInitialized()) {
         throw new ResolverException("Metadata resolver has not been initialized");
      } else if (Strings.isNullOrEmpty(entityID)) {
         this.log.debug("{} EntityDescriptor entityID was null or empty, skipping search for it", this.getLogPrefix());
         return Collections.emptyList();
      } else {
         List descriptors = this.lookupIndexedEntityID(entityID);
         if (descriptors.isEmpty()) {
            this.log.debug("{} Metadata backing store does not contain any EntityDescriptors with the ID: {}", this.getLogPrefix(), entityID);
            return descriptors;
         } else {
            Iterator entitiesIter = descriptors.iterator();

            while(entitiesIter.hasNext()) {
               EntityDescriptor descriptor = (EntityDescriptor)entitiesIter.next();
               if (!this.isValid(descriptor)) {
                  this.log.warn("{} Metadata backing store contained an EntityDescriptor with the ID: {},  but it was no longer valid", this.getLogPrefix(), entityID);
                  entitiesIter.remove();
               }
            }

            return descriptors;
         }
      }
   }

   @Nonnull
   @NonnullElements
   protected List lookupIndexedEntityID(@Nonnull @NotEmpty String entityID) {
      List descriptors = (List)this.getBackingStore().getIndexedDescriptors().get(entityID);
      return (List)(descriptors != null ? new ArrayList(descriptors) : Collections.emptyList());
   }

   @Nonnull
   protected EntityBackingStore createNewBackingStore() {
      return new EntityBackingStore();
   }

   @Nonnull
   protected EntityBackingStore getBackingStore() {
      return this.entityBackingStore;
   }

   protected void setBackingStore(@Nonnull EntityBackingStore newBackingStore) {
      this.entityBackingStore = (EntityBackingStore)Constraint.isNotNull(newBackingStore, "EntityBackingStore may not be null");
   }

   protected void preProcessEntityDescriptor(@Nonnull EntityDescriptor entityDescriptor, @Nonnull EntityBackingStore backingStore) {
      backingStore.getOrderedDescriptors().add(entityDescriptor);
      this.indexEntityDescriptor(entityDescriptor, backingStore);
   }

   protected void removeByEntityID(@Nonnull String entityID, @Nonnull EntityBackingStore backingStore) {
      Map indexedDescriptors = backingStore.getIndexedDescriptors();
      List descriptors = (List)indexedDescriptors.get(entityID);
      if (descriptors != null) {
         backingStore.getOrderedDescriptors().removeAll(descriptors);
      }

      indexedDescriptors.remove(entityID);
   }

   protected void indexEntityDescriptor(@Nonnull EntityDescriptor entityDescriptor, @Nonnull EntityBackingStore backingStore) {
      String entityID = StringSupport.trimOrNull(entityDescriptor.getEntityID());
      if (entityID != null) {
         List entities = (List)backingStore.getIndexedDescriptors().get(entityID);
         if (entities == null) {
            entities = new ArrayList();
            backingStore.getIndexedDescriptors().put(entityID, entities);
         } else if (!((List)entities).isEmpty()) {
            this.log.warn("{} Detected duplicate EntityDescriptor for entityID: {}", this.getLogPrefix(), entityID);
         }

         ((List)entities).add(entityDescriptor);
      }

   }

   protected void preProcessEntitiesDescriptor(@Nonnull EntitiesDescriptor entitiesDescriptor, EntityBackingStore backingStore) {
      Iterator var3 = entitiesDescriptor.getOrderedChildren().iterator();

      while(var3.hasNext()) {
         XMLObject child = (XMLObject)var3.next();
         if (child instanceof EntityDescriptor) {
            this.preProcessEntityDescriptor((EntityDescriptor)child, backingStore);
         } else if (child instanceof EntitiesDescriptor) {
            this.preProcessEntitiesDescriptor((EntitiesDescriptor)child, backingStore);
         }
      }

   }

   protected Iterable predicateFilterCandidates(@Nonnull Iterable candidates, @Nonnull CriteriaSet criteria, boolean onEmptyPredicatesReturnEmpty) throws ResolverException {
      if (!candidates.iterator().hasNext()) {
         this.log.debug("{} Candidates iteration was empty, nothing to filter via predicates", this.getLogPrefix());
         return Collections.emptySet();
      } else {
         this.log.debug("{} Attempting to filter candidate EntityDescriptors via resolved Predicates", this.getLogPrefix());
         Set predicates = ResolverSupport.getPredicates(criteria, EvaluableEntityDescriptorCriterion.class, this.getCriterionPredicateRegistry());
         this.log.trace("{} Resolved {} Predicates: {}", new Object[]{this.getLogPrefix(), predicates.size(), predicates});
         SatisfyAnyCriterion satisfyAnyCriterion = (SatisfyAnyCriterion)criteria.get(SatisfyAnyCriterion.class);
         boolean satisfyAny;
         if (satisfyAnyCriterion != null) {
            this.log.trace("{} CriteriaSet contained SatisfyAnyCriterion", this.getLogPrefix());
            satisfyAny = satisfyAnyCriterion.isSatisfyAny();
         } else {
            this.log.trace("{} CriteriaSet did NOT contain SatisfyAnyCriterion", this.getLogPrefix());
            satisfyAny = this.isSatisfyAnyPredicates();
         }

         this.log.trace("{} Effective satisyAny value: {}", this.getLogPrefix(), satisfyAny);
         Iterable result = ResolverSupport.getFilteredIterable(candidates, predicates, satisfyAny, onEmptyPredicatesReturnEmpty);
         if (this.log.isDebugEnabled()) {
            this.log.debug("{} After predicate filtering {} EntityDescriptors remain", this.getLogPrefix(), Iterables.size(result));
         }

         return result;
      }
   }

   @Nonnull
   @NotEmpty
   protected String getLogPrefix() {
      if (this.logPrefix == null) {
         this.logPrefix = String.format("Metadata Resolver %s %s:", this.getClass().getSimpleName(), this.getId());
      }

      return this.logPrefix;
   }

   protected class EntityBackingStore {
      private Map indexedDescriptors = new ConcurrentHashMap();
      private List orderedDescriptors = new ArrayList();

      @Nonnull
      public Map getIndexedDescriptors() {
         return this.indexedDescriptors;
      }

      @Nonnull
      public List getOrderedDescriptors() {
         return this.orderedDescriptors;
      }
   }
}
