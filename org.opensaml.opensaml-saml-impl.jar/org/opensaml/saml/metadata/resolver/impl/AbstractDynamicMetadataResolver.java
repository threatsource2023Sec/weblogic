package org.opensaml.saml.metadata.resolver.impl;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.RatioGauge;
import com.codahale.metrics.Timer;
import com.codahale.metrics.RatioGauge.Ratio;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.Positive;
import net.shibboleth.utilities.java.support.codec.StringDigester;
import net.shibboleth.utilities.java.support.codec.StringDigester.OutputFormat;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.core.metrics.MetricsSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.persist.XMLObjectLoadSaveManager;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.core.xml.util.XMLObjectSupport.CloneOutputOption;
import org.opensaml.saml.metadata.resolver.DynamicMetadataResolver;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.saml2.common.SAML2Support;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDynamicMetadataResolver extends AbstractMetadataResolver implements DynamicMetadataResolver {
   public static final String METRIC_TIMER_FETCH_FROM_ORIGIN_SOURCE = "timer.fetchFromOriginSource";
   public static final String METRIC_TIMER_RESOLVE = "timer.resolve";
   public static final String METRIC_RATIOGAUGE_FETCH_TO_RESOLVE = "ratioGauge.fetchToResolve";
   public static final String METRIC_GAUGE_NUM_LIVE_ENTITYIDS = "gauge.numLiveEntityIDs";
   public static final String METRIC_GAUGE_PERSISTENT_CACHE_INIT = "gauge.persistentCacheInitialization";
   private final Logger log = LoggerFactory.getLogger(AbstractDynamicMetadataResolver.class);
   @NonnullAfterInit
   private String metricsBaseName;
   @Nullable
   private Timer timerResolve;
   @Nullable
   private Timer timerFetchFromOriginSource;
   @Nullable
   private RatioGauge ratioGaugeFetchToResolve;
   @Nullable
   private Gauge gaugeNumLiveEntityIDs;
   @Nullable
   private Gauge gaugePersistentCacheInit;
   private java.util.Timer taskTimer;
   private boolean createdOwnTaskTimer;
   @Duration
   @Positive
   private Long minCacheDuration;
   @Duration
   @Positive
   private Long maxCacheDuration;
   @Positive
   private Float refreshDelayFactor;
   @Duration
   @Positive
   private Long maxIdleEntityData;
   private boolean removeIdleEntityData;
   @Duration
   @Positive
   private Long cleanupTaskInterval;
   private BackingStoreCleanupSweeper cleanupTask;
   private XMLObjectLoadSaveManager persistentCacheManager;
   private Function persistentCacheKeyGenerator;
   private boolean initializeFromPersistentCacheInBackground;
   @Duration
   @Positive
   private Long backgroundInitializationFromCacheDelay;
   private Predicate initializationFromCachePredicate;
   @NonnullAfterInit
   private PersistentCacheInitializationMetrics persistentCacheInitMetrics;
   private boolean initializing;

   public AbstractDynamicMetadataResolver(@Nullable java.util.Timer backgroundTaskTimer) {
      if (backgroundTaskTimer == null) {
         this.taskTimer = new java.util.Timer(true);
         this.createdOwnTaskTimer = true;
      } else {
         this.taskTimer = backgroundTaskTimer;
      }

      this.minCacheDuration = 600000L;
      this.maxCacheDuration = 28800000L;
      this.refreshDelayFactor = 0.75F;
      this.cleanupTaskInterval = 1800000L;
      this.maxIdleEntityData = 28800000L;
      this.removeIdleEntityData = true;
      this.initializeFromPersistentCacheInBackground = true;
      this.backgroundInitializationFromCacheDelay = 2000L;
   }

   public boolean isInitializeFromPersistentCacheInBackground() {
      return this.initializeFromPersistentCacheInBackground;
   }

   public void setInitializeFromPersistentCacheInBackground(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.initializeFromPersistentCacheInBackground = flag;
   }

   @Nonnull
   public Long getBackgroundInitializationFromCacheDelay() {
      return this.backgroundInitializationFromCacheDelay;
   }

   public void setBackgroundInitializationFromCacheDelay(@Nonnull Long delay) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.backgroundInitializationFromCacheDelay = delay;
   }

   @Nullable
   public XMLObjectLoadSaveManager getPersistentCacheManager() {
      return this.persistentCacheManager;
   }

   public void setPersistentCacheManager(@Nullable XMLObjectLoadSaveManager manager) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.persistentCacheManager = manager;
   }

   public boolean isPersistentCachingEnabled() {
      return this.getPersistentCacheManager() != null;
   }

   @NonnullAfterInit
   public Function getPersistentCacheKeyGenerator() {
      return this.persistentCacheKeyGenerator;
   }

   public void setPersistentCacheKeyGenerator(@Nullable Function generator) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.persistentCacheKeyGenerator = generator;
   }

   @NonnullAfterInit
   public Predicate getInitializationFromCachePredicate() {
      return this.initializationFromCachePredicate;
   }

   public void setInitializationFromCachePredicate(@Nullable Predicate predicate) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.initializationFromCachePredicate = predicate;
   }

   @Nonnull
   public Long getMinCacheDuration() {
      return this.minCacheDuration;
   }

   public void setMinCacheDuration(@Nonnull Long duration) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.minCacheDuration = (Long)Constraint.isNotNull(duration, "Minimum cache duration may not be null");
   }

   @Nonnull
   public Long getMaxCacheDuration() {
      return this.maxCacheDuration;
   }

   public void setMaxCacheDuration(@Nonnull Long duration) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.maxCacheDuration = (Long)Constraint.isNotNull(duration, "Maximum cache duration may not be null");
   }

   public Float getRefreshDelayFactor() {
      return this.refreshDelayFactor;
   }

   public void setRefreshDelayFactor(Float factor) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      if (!(factor <= 0.0F) && !(factor >= 1.0F)) {
         this.refreshDelayFactor = factor;
      } else {
         throw new IllegalArgumentException("Refresh delay factor must be a number between 0.0 and 1.0, exclusive");
      }
   }

   public boolean isRemoveIdleEntityData() {
      return this.removeIdleEntityData;
   }

   public void setRemoveIdleEntityData(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.removeIdleEntityData = flag;
   }

   @Nonnull
   public Long getMaxIdleEntityData() {
      return this.maxIdleEntityData;
   }

   public void setMaxIdleEntityData(@Nonnull Long max) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.maxIdleEntityData = (Long)Constraint.isNotNull(max, "Max idle entity data may not be null");
   }

   @Nonnull
   public Long getCleanupTaskInterval() {
      return this.cleanupTaskInterval;
   }

   public void setCleanupTaskInterval(@Nonnull Long interval) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.cleanupTaskInterval = (Long)Constraint.isNotNull(interval, "Cleanup task interval may not be null");
   }

   @NonnullAfterInit
   public String getMetricsBaseName() {
      return this.metricsBaseName;
   }

   public void setMetricsBaseName(@Nullable String baseName) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.metricsBaseName = StringSupport.trimOrNull(baseName);
   }

   @Nonnull
   public Iterable resolve(@Nonnull CriteriaSet criteria) throws ResolverException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      Timer.Context contextResolve = MetricsSupport.startTimer(this.timerResolve);

      Set var4;
      try {
         EntityIdCriterion entityIdCriterion = (EntityIdCriterion)criteria.get(EntityIdCriterion.class);
         if (entityIdCriterion != null && !Strings.isNullOrEmpty(entityIdCriterion.getEntityId())) {
            String entityID = StringSupport.trimOrNull(((EntityIdCriterion)criteria.get(EntityIdCriterion.class)).getEntityId());
            this.log.debug("{} Attempting to resolve metadata for entityID: {}", this.getLogPrefix(), entityID);
            EntityManagementData mgmtData = this.getBackingStore().getManagementData(entityID);
            Lock readLock = mgmtData.getReadWriteLock().readLock();
            Iterable candidates = null;

            try {
               readLock.lock();
               List descriptors = this.lookupEntityID(entityID);
               if (descriptors.isEmpty()) {
                  this.log.debug("{} Did not find requested metadata in backing store, attempting to resolve dynamically", this.getLogPrefix());
               } else if (this.shouldAttemptRefresh(mgmtData)) {
                  this.log.debug("{} Metadata was indicated to be refreshed based on refresh trigger time", this.getLogPrefix());
               } else {
                  this.log.debug("{} Found requested metadata in backing store", this.getLogPrefix());
                  candidates = descriptors;
               }
            } finally {
               readLock.unlock();
            }

            if (candidates == null) {
               candidates = this.resolveFromOriginSource(criteria);
            }

            Iterable var18 = this.predicateFilterCandidates((Iterable)candidates, criteria, false);
            return var18;
         }

         this.log.info("{} Entity Id was not supplied in criteria set, skipping resolution", this.getLogPrefix());
         var4 = Collections.emptySet();
      } finally {
         MetricsSupport.stopTimer(contextResolve);
      }

      return var4;
   }

   @Nonnull
   @NonnullElements
   protected Iterable resolveFromOriginSource(@Nonnull CriteriaSet criteria) throws ResolverException {
      String entityID = StringSupport.trimOrNull(((EntityIdCriterion)criteria.get(EntityIdCriterion.class)).getEntityId());
      EntityManagementData mgmtData = this.getBackingStore().getManagementData(entityID);
      Lock writeLock = mgmtData.getReadWriteLock().writeLock();

      List var6;
      try {
         writeLock.lock();
         List descriptors = this.lookupEntityID(entityID);
         if (descriptors.isEmpty() || this.shouldAttemptRefresh(mgmtData)) {
            this.log.debug("{} Resolving metadata dynamically for entity ID: {}", this.getLogPrefix(), entityID);
            Timer.Context contextFetchFromOriginSource = MetricsSupport.startTimer(this.timerFetchFromOriginSource);
            XMLObject root = null;

            try {
               root = this.fetchFromOriginSource(criteria);
            } finally {
               MetricsSupport.stopTimer(contextFetchFromOriginSource);
            }

            if (root == null) {
               this.log.debug("{} No metadata was fetched from the origin source", this.getLogPrefix());
            } else {
               try {
                  this.processNewMetadata(root, entityID);
               } catch (FilterException var18) {
                  this.log.error("{} Metadata filtering problem processing new metadata", this.getLogPrefix(), var18);
               }
            }

            List var8 = this.lookupEntityID(entityID);
            return var8;
         }

         this.log.debug("{} Metadata was resolved and stored by another thread while this thread was waiting on the write lock", this.getLogPrefix());
         var6 = descriptors;
         return var6;
      } catch (IOException var20) {
         this.log.error("{} Error fetching metadata from origin source", this.getLogPrefix(), var20);
         var6 = this.lookupEntityID(entityID);
      } finally {
         writeLock.unlock();
      }

      return var6;
   }

   @Nullable
   protected abstract XMLObject fetchFromOriginSource(@Nonnull CriteriaSet var1) throws IOException;

   @Nonnull
   @NonnullElements
   protected List lookupEntityID(@Nonnull String entityID) throws ResolverException {
      this.getBackingStore().getManagementData(entityID).recordEntityAccess();
      return super.lookupEntityID(entityID);
   }

   @Nonnull
   protected void processNewMetadata(@Nonnull XMLObject root, @Nonnull String expectedEntityID) throws FilterException {
      try {
         this.processNewMetadata(root, expectedEntityID, false);
      } catch (ResolverException var4) {
         throw new FilterException(var4);
      }
   }

   @Nonnull
   protected void processNewMetadata(@Nonnull XMLObject root, @Nonnull String expectedEntityID, boolean fromPersistentCache) throws FilterException, ResolverException {
      XMLObject filteredMetadata = this.filterMetadata(this.prepareForFiltering(root));
      if (filteredMetadata == null) {
         this.log.info("{} Metadata filtering process produced a null document, resulting in an empty data set", this.getLogPrefix());
         this.releaseMetadataDOM(root);
         if (fromPersistentCache) {
            throw new FilterException("Metadata filtering process produced a null XMLObject");
         }
      } else {
         if (filteredMetadata instanceof EntityDescriptor) {
            EntityDescriptor entityDescriptor = (EntityDescriptor)filteredMetadata;
            if (!Objects.equals(entityDescriptor.getEntityID(), expectedEntityID)) {
               this.log.warn("{} New metadata's entityID '{}' does not match expected entityID '{}', will not process", new Object[]{this.getLogPrefix(), entityDescriptor.getEntityID(), expectedEntityID});
               if (fromPersistentCache) {
                  throw new ResolverException("New metadata's entityID does not match expected entityID");
               }

               return;
            }

            this.preProcessEntityDescriptor(entityDescriptor, this.getBackingStore());
            this.log.info("{} Successfully loaded new EntityDescriptor with entityID '{}' from {}", new Object[]{this.getLogPrefix(), entityDescriptor.getEntityID(), fromPersistentCache ? "persistent cache" : "origin source"});
            if (this.isPersistentCachingEnabled() && !fromPersistentCache && root instanceof EntityDescriptor) {
               EntityDescriptor origDescriptor = (EntityDescriptor)root;
               String key = (String)this.getPersistentCacheKeyGenerator().apply(origDescriptor);
               this.log.trace("{} Storing resolved EntityDescriptor '{}' in persistent cache with key '{}'", new Object[]{this.getLogPrefix(), origDescriptor.getEntityID(), key});
               if (key == null) {
                  this.log.warn("{} Could not generate cache storage key for EntityDescriptor '{}', skipping caching", this.getLogPrefix(), origDescriptor.getEntityID());
               } else {
                  try {
                     this.getPersistentCacheManager().save(key, origDescriptor, true);
                  } catch (IOException var9) {
                     this.log.warn("{} Error saving EntityDescriptor '{}' to cache store with key {}'", new Object[]{this.getLogPrefix(), origDescriptor.getEntityID(), key});
                  }
               }
            }
         } else {
            this.log.warn("{} Document root was not an EntityDescriptor: {}", this.getLogPrefix(), root.getClass().getName());
         }

         this.releaseMetadataDOM(filteredMetadata);
         this.releaseMetadataDOM(root);
      }
   }

   @Nonnull
   protected XMLObject prepareForFiltering(@Nonnull XMLObject input) {
      if (this.getMetadataFilter() != null && this.isPersistentCachingEnabled()) {
         try {
            return XMLObjectSupport.cloneXMLObject(input, CloneOutputOption.RootDOMInNewDocument);
         } catch (UnmarshallingException | MarshallingException var3) {
            this.log.warn("{} Error cloning XMLObject, will use input root object as filter target", this.getLogPrefix(), var3);
            return input;
         }
      } else {
         return input;
      }
   }

   protected void preProcessEntityDescriptor(@Nonnull EntityDescriptor entityDescriptor, @Nonnull AbstractMetadataResolver.EntityBackingStore backingStore) {
      String entityID = StringSupport.trimOrNull(entityDescriptor.getEntityID());
      this.removeByEntityID(entityID, backingStore);
      super.preProcessEntityDescriptor(entityDescriptor, backingStore);
      DynamicEntityBackingStore dynamicBackingStore = (DynamicEntityBackingStore)backingStore;
      EntityManagementData mgmtData = dynamicBackingStore.getManagementData(entityID);
      DateTime now = new DateTime(ISOChronology.getInstanceUTC());
      this.log.debug("{} For metadata expiration and refresh computation, 'now' is : {}", this.getLogPrefix(), now);
      mgmtData.setLastUpdateTime(now);
      mgmtData.setExpirationTime(this.computeExpirationTime(entityDescriptor, now));
      this.log.debug("{} Computed metadata expiration time: {}", this.getLogPrefix(), mgmtData.getExpirationTime());
      mgmtData.setRefreshTriggerTime(this.computeRefreshTriggerTime(mgmtData.getExpirationTime(), now));
      this.log.debug("{} Computed refresh trigger time: {}", this.getLogPrefix(), mgmtData.getRefreshTriggerTime());
   }

   @Nonnull
   protected DateTime computeExpirationTime(@Nonnull EntityDescriptor entityDescriptor, @Nonnull DateTime now) {
      DateTime lowerBound = now.toDateTime(ISOChronology.getInstanceUTC()).plus(this.getMinCacheDuration());
      DateTime expiration = SAML2Support.getEarliestExpiration(entityDescriptor, now.plus(this.getMaxCacheDuration()), now);
      if (expiration.isBefore(lowerBound)) {
         expiration = lowerBound;
      }

      return expiration;
   }

   @Nonnull
   protected DateTime computeRefreshTriggerTime(@Nullable DateTime expirationTime, @Nonnull DateTime nowDateTime) {
      DateTime nowDateTimeUTC = nowDateTime.toDateTime(ISOChronology.getInstanceUTC());
      long now = nowDateTimeUTC.getMillis();
      long expireInstant = 0L;
      if (expirationTime != null) {
         expireInstant = expirationTime.toDateTime(ISOChronology.getInstanceUTC()).getMillis();
      }

      long refreshDelay = (long)((float)(expireInstant - now) * this.getRefreshDelayFactor());
      if (refreshDelay < this.getMinCacheDuration()) {
         refreshDelay = this.getMinCacheDuration();
      }

      return nowDateTimeUTC.plus(refreshDelay);
   }

   protected boolean shouldAttemptRefresh(@Nonnull EntityManagementData mgmtData) {
      DateTime now = new DateTime(ISOChronology.getInstanceUTC());
      return now.isAfter(mgmtData.getRefreshTriggerTime());
   }

   @Nonnull
   protected DynamicEntityBackingStore createNewBackingStore() {
      return new DynamicEntityBackingStore();
   }

   @NonnullAfterInit
   protected DynamicEntityBackingStore getBackingStore() {
      return (DynamicEntityBackingStore)super.getBackingStore();
   }

   protected void initMetadataResolver() throws ComponentInitializationException {
      try {
         this.initializing = true;
         super.initMetadataResolver();
         this.initializeMetricsInstrumentation();
         this.setBackingStore(this.createNewBackingStore());
         if (this.getPersistentCacheKeyGenerator() == null) {
            this.setPersistentCacheKeyGenerator(new DefaultCacheKeyGenerator());
         }

         if (this.getInitializationFromCachePredicate() == null) {
            this.setInitializationFromCachePredicate(Predicates.alwaysTrue());
         }

         this.persistentCacheInitMetrics = new PersistentCacheInitializationMetrics();
         if (this.isPersistentCachingEnabled()) {
            this.persistentCacheInitMetrics.enabled = true;
            if (this.isInitializeFromPersistentCacheInBackground()) {
               this.log.debug("{} Initializing from the persistent cache in the background in {} ms", this.getLogPrefix(), this.getBackgroundInitializationFromCacheDelay());
               TimerTask initTask = new TimerTask() {
                  public void run() {
                     AbstractDynamicMetadataResolver.this.initializeFromPersistentCache();
                  }
               };
               this.taskTimer.schedule(initTask, this.getBackgroundInitializationFromCacheDelay());
            } else {
               this.log.debug("{} Initializing from the persistent cache in the foreground", this.getLogPrefix());
               this.initializeFromPersistentCache();
            }
         }

         this.cleanupTask = new BackingStoreCleanupSweeper();
         this.taskTimer.schedule(this.cleanupTask, 60000L, this.getCleanupTaskInterval());
      } finally {
         this.initializing = false;
      }

   }

   private void initializeMetricsInstrumentation() {
      if (this.getMetricsBaseName() == null) {
         this.setMetricsBaseName(MetricRegistry.name(this.getClass(), new String[]{this.getId()}));
      }

      MetricRegistry metricRegistry = MetricsSupport.getMetricRegistry();
      if (metricRegistry != null) {
         this.timerResolve = metricRegistry.timer(MetricRegistry.name(this.getMetricsBaseName(), new String[]{"timer.resolve"}));
         this.timerFetchFromOriginSource = metricRegistry.timer(MetricRegistry.name(this.getMetricsBaseName(), new String[]{"timer.fetchFromOriginSource"}));
         this.ratioGaugeFetchToResolve = (RatioGauge)MetricsSupport.register(MetricRegistry.name(this.getMetricsBaseName(), new String[]{"ratioGauge.fetchToResolve"}), new RatioGauge() {
            protected RatioGauge.Ratio getRatio() {
               return Ratio.of((double)AbstractDynamicMetadataResolver.this.timerFetchFromOriginSource.getCount(), (double)AbstractDynamicMetadataResolver.this.timerResolve.getCount());
            }
         }, true);
         this.gaugeNumLiveEntityIDs = (Gauge)MetricsSupport.register(MetricRegistry.name(this.getMetricsBaseName(), new String[]{"gauge.numLiveEntityIDs"}), new Gauge() {
            public Integer getValue() {
               return AbstractDynamicMetadataResolver.this.getBackingStore().getIndexedDescriptors().keySet().size();
            }
         }, true);
         this.gaugePersistentCacheInit = (Gauge)MetricsSupport.register(MetricRegistry.name(this.getMetricsBaseName(), new String[]{"gauge.persistentCacheInitialization"}), new Gauge() {
            public PersistentCacheInitializationMetrics getValue() {
               return AbstractDynamicMetadataResolver.this.persistentCacheInitMetrics;
            }
         }, true);
      }

   }

   protected void initializeFromPersistentCache() {
      if (!this.isPersistentCachingEnabled()) {
         this.log.trace("{} Persistent caching is not enabled, skipping init from cache", this.getLogPrefix());
      } else {
         this.log.trace("{} Attempting to load and process entities from the persistent cache", this.getLogPrefix());
         long start = System.nanoTime();

         try {
            Iterator var3 = this.getPersistentCacheManager().listAll().iterator();

            while(var3.hasNext()) {
               Pair cacheEntry = (Pair)var3.next();
               this.persistentCacheInitMetrics.entriesTotal++;
               EntityDescriptor descriptor = (EntityDescriptor)cacheEntry.getSecond();
               String currentKey = (String)cacheEntry.getFirst();
               this.log.trace("{} Loaded EntityDescriptor from cache store with entityID '{}' and storage key '{}'", new Object[]{this.getLogPrefix(), descriptor.getEntityID(), currentKey});
               String entityID = StringSupport.trimOrNull(descriptor.getEntityID());
               EntityManagementData mgmtData = this.getBackingStore().getManagementData(entityID);
               Lock writeLock = mgmtData.getReadWriteLock().writeLock();

               try {
                  writeLock.lock();
                  if (!this.lookupIndexedEntityID(entityID).isEmpty()) {
                     this.log.trace("{} Metadata for entityID '{}' found in persistent cache was already live, ignoring cached entry", this.getLogPrefix(), entityID);
                     this.persistentCacheInitMetrics.entriesSkippedAlreadyLive++;
                  } else {
                     this.processPersistentCacheEntry(currentKey, descriptor);
                  }
               } finally {
                  writeLock.unlock();
               }
            }
         } catch (IOException var19) {
            this.log.warn("{} Error loading EntityDescriptors from cache", this.getLogPrefix(), var19);
         } finally {
            this.persistentCacheInitMetrics.processingTime = System.nanoTime() - start;
            this.log.debug("{} Persistent cache initialization metrics: {}", this.getLogPrefix(), this.persistentCacheInitMetrics);
         }

      }
   }

   protected void processPersistentCacheEntry(@Nonnull String currentKey, @Nonnull EntityDescriptor descriptor) {
      if (this.isValid(descriptor)) {
         if (this.getInitializationFromCachePredicate().apply(descriptor)) {
            try {
               this.processNewMetadata(descriptor, descriptor.getEntityID(), true);
               this.log.trace("{} Successfully processed EntityDescriptor with entityID '{}' from cache", this.getLogPrefix(), descriptor.getEntityID());
               this.persistentCacheInitMetrics.entriesLoaded++;
            } catch (ResolverException | FilterException var7) {
               this.log.warn("{} Error processing EntityDescriptor '{}' from cache with storage key '{}'", new Object[]{this.getLogPrefix(), descriptor.getEntityID(), currentKey, var7});
               this.persistentCacheInitMetrics.entriesSkippedProcessingException++;
            }
         } else {
            this.log.trace("{} Cache initialization predicate indicated to not process EntityDescriptor with entityID '{}' and cache storage key '{}'", new Object[]{this.getLogPrefix(), descriptor.getEntityID(), currentKey});
            this.persistentCacheInitMetrics.entriesSkippedFailedPredicate++;
         }

         String expectedKey = (String)this.getPersistentCacheKeyGenerator().apply(descriptor);

         try {
            if (!Objects.equals(currentKey, expectedKey)) {
               this.log.trace("{} Current cache storage key '{}' differs from expected key '{}', updating", new Object[]{this.getLogPrefix(), currentKey, expectedKey});
               this.getPersistentCacheManager().updateKey(currentKey, expectedKey);
               this.log.trace("{} Successfully updated cache storage key '{}' to '{}'", new Object[]{this.getLogPrefix(), currentKey, expectedKey});
            }
         } catch (IOException var6) {
            this.log.warn("{} Error updating cache storage key '{}' to '{}'", new Object[]{this.getLogPrefix(), currentKey, expectedKey, var6});
         }
      } else {
         this.log.trace("{} EntityDescriptor with entityID '{}' and storaage key '{}' in cache was not valid, skipping and removing", new Object[]{this.getLogPrefix(), descriptor.getEntityID(), currentKey});
         this.persistentCacheInitMetrics.entriesSkippedInvalid++;

         try {
            this.getPersistentCacheManager().remove(currentKey);
         } catch (IOException var5) {
            this.log.warn("{} Error removing invalid EntityDescriptor '{}' from persistent cache with key '{}'", new Object[]{this.getLogPrefix(), descriptor.getEntityID(), currentKey});
         }
      }

   }

   protected void removeByEntityID(String entityID, AbstractMetadataResolver.EntityBackingStore backingStore) {
      if (this.isPersistentCachingEnabled()) {
         List descriptors = (List)backingStore.getIndexedDescriptors().get(entityID);
         if (descriptors != null) {
            Iterator var4 = descriptors.iterator();

            while(var4.hasNext()) {
               EntityDescriptor descriptor = (EntityDescriptor)var4.next();
               String key = (String)this.getPersistentCacheKeyGenerator().apply(descriptor);

               try {
                  this.getPersistentCacheManager().remove(key);
               } catch (IOException var8) {
                  this.log.warn("{} Error removing EntityDescriptor '{}' from cache store with key '{}'", new Object[]{this.getLogPrefix(), descriptor.getEntityID(), key});
               }
            }
         }
      }

      super.removeByEntityID(entityID, backingStore);
   }

   protected void doDestroy() {
      if (this.cleanupTask != null) {
         this.cleanupTask.cancel();
      }

      if (this.createdOwnTaskTimer) {
         this.taskTimer.cancel();
      }

      this.cleanupTask = null;
      this.taskTimer = null;
      if (this.ratioGaugeFetchToResolve != null) {
         MetricsSupport.remove(MetricRegistry.name(this.getMetricsBaseName(), new String[]{"ratioGauge.fetchToResolve"}), this.ratioGaugeFetchToResolve);
      }

      if (this.gaugeNumLiveEntityIDs != null) {
         MetricsSupport.remove(MetricRegistry.name(this.getMetricsBaseName(), new String[]{"gauge.numLiveEntityIDs"}), this.gaugeNumLiveEntityIDs);
      }

      if (this.gaugePersistentCacheInit != null) {
         MetricsSupport.remove(MetricRegistry.name(this.getMetricsBaseName(), new String[]{"gauge.persistentCacheInitialization"}), this.gaugePersistentCacheInit);
      }

      this.ratioGaugeFetchToResolve = null;
      this.gaugeNumLiveEntityIDs = null;
      this.gaugePersistentCacheInit = null;
      this.timerFetchFromOriginSource = null;
      this.timerResolve = null;
      super.doDestroy();
   }

   public static class PersistentCacheInitializationMetrics {
      private boolean enabled;
      private long processingTime;
      private int entriesTotal;
      private int entriesLoaded;
      private int entriesSkippedAlreadyLive;
      private int entriesSkippedInvalid;
      private int entriesSkippedFailedPredicate;
      private int entriesSkippedProcessingException;

      public boolean isEnabled() {
         return this.enabled;
      }

      public long getProcessingTime() {
         return this.processingTime;
      }

      public int getEntriesTotal() {
         return this.entriesTotal;
      }

      public int getEntriesLoaded() {
         return this.entriesLoaded;
      }

      public int getEntriesSkippedAlreadyLive() {
         return this.entriesSkippedAlreadyLive;
      }

      public int getEntriesSkippedInvalid() {
         return this.entriesSkippedInvalid;
      }

      public int getEntriesSkippedFailedPredicate() {
         return this.entriesSkippedFailedPredicate;
      }

      public int getEntriesSkippedProcessingException() {
         return this.entriesSkippedProcessingException;
      }

      public String toString() {
         return MoreObjects.toStringHelper(this).add("enabled", this.enabled).add("processingTime", this.processingTime).add("entriesTotal", this.entriesTotal).add("entriesLoaded", this.entriesLoaded).add("entriesSkippedAlreadyLive", this.entriesSkippedAlreadyLive).add("entriesSkippedInvalid", this.entriesSkippedInvalid).add("entriesSkippedFailedPredicate", this.entriesSkippedFailedPredicate).add("entriesSkippedProcessingException", this.entriesSkippedProcessingException).toString();
      }
   }

   public static class DefaultCacheKeyGenerator implements Function {
      private StringDigester digester;

      public DefaultCacheKeyGenerator() {
         try {
            this.digester = new StringDigester("SHA-1", OutputFormat.HEX_LOWER);
         } catch (NoSuchAlgorithmException var2) {
         }

      }

      public String apply(EntityDescriptor input) {
         if (input == null) {
            return null;
         } else {
            String entityID = StringSupport.trimOrNull(input.getEntityID());
            return entityID == null ? null : this.digester.apply(entityID);
         }
      }
   }

   protected class BackingStoreCleanupSweeper extends TimerTask {
      private final Logger log = LoggerFactory.getLogger(BackingStoreCleanupSweeper.class);

      public void run() {
         if (!AbstractDynamicMetadataResolver.this.isDestroyed() && AbstractDynamicMetadataResolver.this.isInitialized()) {
            this.removeExpiredAndIdleMetadata();
         } else {
            this.log.debug("{} BackingStoreCleanupSweeper will not run because: inited: {}, destroyed: {}", new Object[]{AbstractDynamicMetadataResolver.this.getLogPrefix(), AbstractDynamicMetadataResolver.this.isInitialized(), AbstractDynamicMetadataResolver.this.isDestroyed()});
         }
      }

      private void removeExpiredAndIdleMetadata() {
         DateTime now = new DateTime(ISOChronology.getInstanceUTC());
         DateTime earliestValidLastAccessed = now.minus(AbstractDynamicMetadataResolver.this.getMaxIdleEntityData());
         DynamicEntityBackingStore backingStore = AbstractDynamicMetadataResolver.this.getBackingStore();
         Map indexedDescriptors = backingStore.getIndexedDescriptors();
         Iterator var5 = indexedDescriptors.keySet().iterator();

         while(var5.hasNext()) {
            String entityID = (String)var5.next();
            EntityManagementData mgmtData = backingStore.getManagementData(entityID);
            Lock writeLock = mgmtData.getReadWriteLock().writeLock();

            try {
               writeLock.lock();
               if (this.isRemoveData(mgmtData, now, earliestValidLastAccessed)) {
                  AbstractDynamicMetadataResolver.this.removeByEntityID(entityID, backingStore);
                  backingStore.removeManagementData(entityID);
               }
            } finally {
               writeLock.unlock();
            }
         }

      }

      private boolean isRemoveData(@Nonnull EntityManagementData mgmtData, @Nonnull DateTime now, @Nonnull DateTime earliestValidLastAccessed) {
         if (AbstractDynamicMetadataResolver.this.isRemoveIdleEntityData() && mgmtData.getLastAccessedTime().isBefore(earliestValidLastAccessed)) {
            this.log.debug("{} Entity metadata exceeds maximum idle time, removing: {}", AbstractDynamicMetadataResolver.this.getLogPrefix(), mgmtData.getEntityID());
            return true;
         } else if (now.isAfter(mgmtData.getExpirationTime())) {
            this.log.debug("{} Entity metadata is expired, removing: {}", AbstractDynamicMetadataResolver.this.getLogPrefix(), mgmtData.getEntityID());
            return true;
         } else {
            return false;
         }
      }
   }

   protected class EntityManagementData {
      private String entityID;
      private DateTime lastUpdateTime;
      private DateTime expirationTime;
      private DateTime refreshTriggerTime;
      private DateTime lastAccessedTime;
      private ReadWriteLock readWriteLock;

      protected EntityManagementData(@Nonnull String id) {
         this.entityID = (String)Constraint.isNotNull(id, "Entity ID was null");
         DateTime now = new DateTime(ISOChronology.getInstanceUTC());
         this.expirationTime = now.plus(AbstractDynamicMetadataResolver.this.getMaxCacheDuration());
         this.refreshTriggerTime = now.plus(AbstractDynamicMetadataResolver.this.getMaxCacheDuration());
         this.lastAccessedTime = now;
         this.readWriteLock = new ReentrantReadWriteLock(true);
      }

      @Nonnull
      public String getEntityID() {
         return this.entityID;
      }

      @Nullable
      public DateTime getLastUpdateTime() {
         return this.lastUpdateTime;
      }

      public void setLastUpdateTime(@Nonnull DateTime dateTime) {
         this.lastUpdateTime = dateTime;
      }

      @Nonnull
      public DateTime getExpirationTime() {
         return this.expirationTime;
      }

      public void setExpirationTime(@Nonnull DateTime dateTime) {
         this.expirationTime = (DateTime)Constraint.isNotNull(dateTime, "Expiration time may not be null");
      }

      @Nonnull
      public DateTime getRefreshTriggerTime() {
         return this.refreshTriggerTime;
      }

      public void setRefreshTriggerTime(@Nonnull DateTime dateTime) {
         this.refreshTriggerTime = (DateTime)Constraint.isNotNull(dateTime, "Refresh trigger time may not be null");
      }

      @Nonnull
      public DateTime getLastAccessedTime() {
         return this.lastAccessedTime;
      }

      public void recordEntityAccess() {
         this.lastAccessedTime = new DateTime(ISOChronology.getInstanceUTC());
      }

      @Nonnull
      public ReadWriteLock getReadWriteLock() {
         return this.readWriteLock;
      }
   }

   protected class DynamicEntityBackingStore extends AbstractMetadataResolver.EntityBackingStore {
      private Map mgmtDataMap = new ConcurrentHashMap();

      protected DynamicEntityBackingStore() {
         super();
      }

      @Nonnull
      public EntityManagementData getManagementData(@Nonnull String entityID) {
         Constraint.isNotNull(entityID, "EntityID may not be null");
         EntityManagementData entityData = (EntityManagementData)this.mgmtDataMap.get(entityID);
         if (entityData != null) {
            return entityData;
         } else {
            synchronized(this) {
               entityData = (EntityManagementData)this.mgmtDataMap.get(entityID);
               if (entityData != null) {
                  return entityData;
               } else {
                  entityData = AbstractDynamicMetadataResolver.this.new EntityManagementData(entityID);
                  this.mgmtDataMap.put(entityID, entityData);
                  return entityData;
               }
            }
         }
      }

      public void removeManagementData(@Nonnull String entityID) {
         Constraint.isNotNull(entityID, "EntityID may not be null");
         synchronized(this) {
            this.mgmtDataMap.remove(entityID);
         }
      }
   }
}
