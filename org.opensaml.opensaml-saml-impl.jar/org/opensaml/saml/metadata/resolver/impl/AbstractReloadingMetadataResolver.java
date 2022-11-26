package org.opensaml.saml.metadata.resolver.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.annotation.constraint.Positive;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.metadata.resolver.RefreshableMetadataResolver;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.saml2.common.SAML2Support;
import org.opensaml.saml.saml2.common.TimeBoundSAMLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public abstract class AbstractReloadingMetadataResolver extends AbstractBatchMetadataResolver implements RefreshableMetadataResolver {
   private final Logger log;
   private Timer taskTimer;
   private boolean createdOwnTaskTimer;
   private RefreshMetadataTask refreshMetadataTask;
   private float refreshDelayFactor;
   @Duration
   @Positive
   private long maxRefreshDelay;
   @Duration
   @Positive
   private long minRefreshDelay;
   private DateTime expirationTime;
   private DateTime lastUpdate;
   private DateTime lastRefresh;
   private DateTime nextRefresh;

   protected AbstractReloadingMetadataResolver() {
      this((Timer)null);
   }

   protected AbstractReloadingMetadataResolver(@Nullable Timer backgroundTaskTimer) {
      this.log = LoggerFactory.getLogger(AbstractReloadingMetadataResolver.class);
      this.refreshDelayFactor = 0.75F;
      this.maxRefreshDelay = 14400000L;
      this.minRefreshDelay = 300000L;
      this.setCacheSourceMetadata(true);
      if (backgroundTaskTimer == null) {
         this.taskTimer = new Timer(true);
         this.createdOwnTaskTimer = true;
      } else {
         this.taskTimer = backgroundTaskTimer;
      }

   }

   protected void setCacheSourceMetadata(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      if (!flag) {
         this.log.warn("{} Caching of source metadata may not be disabled for reloading metadata resolvers", this.getLogPrefix());
      } else {
         super.setCacheSourceMetadata(flag);
      }

   }

   public DateTime getExpirationTime() {
      return this.expirationTime;
   }

   @Nullable
   public DateTime getLastUpdate() {
      return this.lastUpdate;
   }

   @Nullable
   public DateTime getLastRefresh() {
      return this.lastRefresh;
   }

   public DateTime getNextRefresh() {
      return this.nextRefresh;
   }

   @Duration
   public long getMaxRefreshDelay() {
      return this.maxRefreshDelay;
   }

   @Duration
   public void setMaxRefreshDelay(@Duration @Positive long delay) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      if (delay < 0L) {
         throw new IllegalArgumentException("Maximum refresh delay must be greater than 0");
      } else {
         this.maxRefreshDelay = delay;
      }
   }

   public float getRefreshDelayFactor() {
      return this.refreshDelayFactor;
   }

   public void setRefreshDelayFactor(float factor) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      if (!(factor <= 0.0F) && !(factor >= 1.0F)) {
         this.refreshDelayFactor = factor;
      } else {
         throw new IllegalArgumentException("Refresh delay factor must be a number between 0.0 and 1.0, exclusive");
      }
   }

   @Duration
   public long getMinRefreshDelay() {
      return this.minRefreshDelay;
   }

   @Duration
   public void setMinRefreshDelay(@Duration @Positive long delay) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      if (delay < 0L) {
         throw new IllegalArgumentException("Minimum refresh delay must be greater than 0");
      } else {
         this.minRefreshDelay = delay;
      }
   }

   protected void doDestroy() {
      this.refreshMetadataTask.cancel();
      if (this.createdOwnTaskTimer) {
         this.taskTimer.cancel();
      }

      this.expirationTime = null;
      this.lastRefresh = null;
      this.lastUpdate = null;
      this.nextRefresh = null;
      super.doDestroy();
   }

   protected void initMetadataResolver() throws ComponentInitializationException {
      super.initMetadataResolver();

      try {
         this.refresh();
      } catch (ResolverException var2) {
         throw new ComponentInitializationException("Error refreshing metadata during init", var2);
      }

      if (this.minRefreshDelay > this.maxRefreshDelay) {
         throw new ComponentInitializationException("Minimum refresh delay " + this.minRefreshDelay + " is greater than maximum refresh delay " + this.maxRefreshDelay);
      }
   }

   public synchronized void refresh() throws ResolverException {
      DateTime now = null;
      String mdId = null;
      boolean var10 = false;

      long nextRefreshDelay;
      label75: {
         try {
            var10 = true;
            if (this.isDestroyed()) {
               var10 = false;
               break label75;
            }

            if (this.refreshMetadataTask != null) {
               this.refreshMetadataTask.cancel();
            }

            now = new DateTime(ISOChronology.getInstanceUTC());
            mdId = this.getMetadataIdentifier();
            this.log.debug("{} Beginning refresh of metadata from '{}'", this.getLogPrefix(), mdId);
            byte[] mdBytes = this.fetchMetadata();
            if (mdBytes == null) {
               this.log.info("{} Metadata from '{}' has not changed since last refresh", this.getLogPrefix(), mdId);
               this.processCachedMetadata(mdId, now);
               var10 = false;
            } else {
               this.log.debug("{} Processing new metadata from '{}'", this.getLogPrefix(), mdId);
               this.processNewMetadata(mdId, now, mdBytes);
               var10 = false;
            }
         } catch (Throwable var11) {
            this.log.error("{} Error occurred while attempting to refresh metadata from '{}'", new Object[]{this.getLogPrefix(), mdId, var11});
            this.nextRefresh = (new DateTime(ISOChronology.getInstanceUTC())).plus(this.minRefreshDelay);
            if (var11 instanceof Exception) {
               throw new ResolverException((Exception)var11);
            }

            throw new ResolverException(String.format("Saw an error of type '%s' with message '%s'", var11.getClass().getName(), var11.getMessage()));
         } finally {
            if (var10) {
               this.logCachedMetadataExpiration();
               this.refreshMetadataTask = new RefreshMetadataTask();
               long nextRefreshDelay = this.nextRefresh.getMillis() - System.currentTimeMillis();
               this.taskTimer.schedule(this.refreshMetadataTask, nextRefreshDelay);
               this.log.info("{} Next refresh cycle for metadata provider '{}' will occur on '{}' ('{}' local time)", new Object[]{this.getLogPrefix(), mdId, this.nextRefresh, this.nextRefresh.toDateTime(DateTimeZone.getDefault())});
               this.lastRefresh = now;
            }
         }

         this.logCachedMetadataExpiration();
         this.refreshMetadataTask = new RefreshMetadataTask();
         nextRefreshDelay = this.nextRefresh.getMillis() - System.currentTimeMillis();
         this.taskTimer.schedule(this.refreshMetadataTask, nextRefreshDelay);
         this.log.info("{} Next refresh cycle for metadata provider '{}' will occur on '{}' ('{}' local time)", new Object[]{this.getLogPrefix(), mdId, this.nextRefresh, this.nextRefresh.toDateTime(DateTimeZone.getDefault())});
         this.lastRefresh = now;
         return;
      }

      this.logCachedMetadataExpiration();
      this.refreshMetadataTask = new RefreshMetadataTask();
      nextRefreshDelay = this.nextRefresh.getMillis() - System.currentTimeMillis();
      this.taskTimer.schedule(this.refreshMetadataTask, nextRefreshDelay);
      this.log.info("{} Next refresh cycle for metadata provider '{}' will occur on '{}' ('{}' local time)", new Object[]{this.getLogPrefix(), mdId, this.nextRefresh, this.nextRefresh.toDateTime(DateTimeZone.getDefault())});
      this.lastRefresh = now;
   }

   private void logCachedMetadataExpiration() {
      String mdId = this.getMetadataIdentifier();
      XMLObject cached = this.getBackingStore().getCachedOriginalMetadata();
      if (cached != null && !this.isValid(cached)) {
         this.log.warn("{} Metadata root from '{}' currently live (post-refresh) is expired or otherwise invalid", this.getLogPrefix(), mdId);
      } else if (cached instanceof TimeBoundSAMLObject) {
         TimeBoundSAMLObject timebound = (TimeBoundSAMLObject)cached;
         if (this.isRequireValidMetadata() && timebound.getValidUntil() != null && timebound.getValidUntil().isBefore(this.nextRefresh)) {
            this.log.warn("{} Metadata root from '{}' currently live (post-refresh) will expire at '{}' before the next refresh scheduled for {}'", new Object[]{this.getLogPrefix(), mdId, timebound.getValidUntil(), this.nextRefresh});
         }
      }

   }

   protected abstract String getMetadataIdentifier();

   protected abstract byte[] fetchMetadata() throws ResolverException;

   protected XMLObject unmarshallMetadata(byte[] metadataBytes) throws ResolverException {
      try {
         return this.unmarshallMetadata(new ByteArrayInputStream(metadataBytes));
      } catch (UnmarshallingException var4) {
         String errorMsg = "Unable to unmarshall metadata";
         this.log.error("{} Unable to unmarshall metadata", this.getLogPrefix(), var4);
         throw new ResolverException("Unable to unmarshall metadata", var4);
      }
   }

   protected void processCachedMetadata(String metadataIdentifier, DateTime refreshStart) throws ResolverException {
      this.log.debug("{} Computing new expiration time for cached metadata from '{}'", this.getLogPrefix(), metadataIdentifier);
      DateTime metadataExpirationTime = SAML2Support.getEarliestExpiration(this.getBackingStore().getCachedOriginalMetadata(), refreshStart.plus(this.getMaxRefreshDelay()), refreshStart);
      this.expirationTime = metadataExpirationTime;
      long nextRefreshDelay = this.computeNextRefreshDelay(this.expirationTime);
      this.nextRefresh = (new DateTime(ISOChronology.getInstanceUTC())).plus(nextRefreshDelay);
   }

   protected void processNewMetadata(String metadataIdentifier, DateTime refreshStart, byte[] metadataBytes) throws ResolverException {
      this.log.debug("{} Unmarshalling metadata from '{}'", this.getLogPrefix(), metadataIdentifier);
      XMLObject metadata = this.unmarshallMetadata(metadataBytes);
      if (!this.isValid(metadata)) {
         this.processPreExpiredMetadata(metadataIdentifier, refreshStart, metadataBytes, metadata);
      } else {
         this.processNonExpiredMetadata(metadataIdentifier, refreshStart, metadataBytes, metadata);
      }

   }

   protected void processPreExpiredMetadata(String metadataIdentifier, DateTime refreshStart, byte[] metadataBytes, XMLObject metadata) {
      this.log.warn("{} Entire metadata document from '{}' was expired at time of loading, existing metadata retained", this.getLogPrefix(), metadataIdentifier);
      this.nextRefresh = (new DateTime(ISOChronology.getInstanceUTC())).plus(this.getMinRefreshDelay());
   }

   protected void processNonExpiredMetadata(String metadataIdentifier, DateTime refreshStart, byte[] metadataBytes, XMLObject metadata) throws ResolverException {
      Document metadataDom = metadata.getDOM().getOwnerDocument();
      this.log.debug("{} Preprocessing metadata from '{}'", this.getLogPrefix(), metadataIdentifier);
      AbstractBatchMetadataResolver.BatchEntityBackingStore newBackingStore = null;

      try {
         newBackingStore = this.preProcessNewMetadata(metadata);
      } catch (FilterException var10) {
         String errMsg = "Error filtering metadata from " + metadataIdentifier;
         this.log.error("{} " + errMsg, this.getLogPrefix(), var10);
         throw new ResolverException(errMsg, var10);
      }

      this.log.debug("{} Releasing cached DOM for metadata from '{}'", this.getLogPrefix(), metadataIdentifier);
      this.releaseMetadataDOM(newBackingStore.getCachedOriginalMetadata());
      this.releaseMetadataDOM(newBackingStore.getCachedFilteredMetadata());
      this.log.debug("{} Post-processing metadata from '{}'", this.getLogPrefix(), metadataIdentifier);
      this.postProcessMetadata(metadataBytes, metadataDom, newBackingStore.getCachedOriginalMetadata(), newBackingStore.getCachedFilteredMetadata());
      this.log.debug("{} Computing expiration time for metadata from '{}'", this.getLogPrefix(), metadataIdentifier);
      DateTime metadataExpirationTime = SAML2Support.getEarliestExpiration(newBackingStore.getCachedOriginalMetadata(), refreshStart.plus(this.getMaxRefreshDelay()), refreshStart);
      this.log.debug("{} Expiration of metadata from '{}' will occur at {}", new Object[]{this.getLogPrefix(), metadataIdentifier, metadataExpirationTime.toString()});
      this.setBackingStore(newBackingStore);
      this.lastUpdate = refreshStart;
      long nextRefreshDelay;
      if (metadataExpirationTime.isBeforeNow()) {
         this.expirationTime = (new DateTime(ISOChronology.getInstanceUTC())).plus(this.getMinRefreshDelay());
         nextRefreshDelay = this.getMaxRefreshDelay();
      } else {
         this.expirationTime = metadataExpirationTime;
         nextRefreshDelay = this.computeNextRefreshDelay(this.expirationTime);
      }

      this.nextRefresh = (new DateTime(ISOChronology.getInstanceUTC())).plus(nextRefreshDelay);
      this.log.info("{} New metadata successfully loaded for '{}'", this.getLogPrefix(), this.getMetadataIdentifier());
   }

   protected void postProcessMetadata(byte[] metadataBytes, Document metadataDom, XMLObject originalMetadata, XMLObject filteredMetadata) throws ResolverException {
   }

   protected long computeNextRefreshDelay(DateTime expectedExpiration) {
      long now = (new DateTime(ISOChronology.getInstanceUTC())).getMillis();
      long expireInstant = 0L;
      if (expectedExpiration != null) {
         expireInstant = expectedExpiration.toDateTime(ISOChronology.getInstanceUTC()).getMillis();
      }

      long refreshDelay = (long)((float)(expireInstant - now) * this.getRefreshDelayFactor());
      if (refreshDelay < this.getMinRefreshDelay()) {
         refreshDelay = this.getMinRefreshDelay();
      }

      return refreshDelay;
   }

   protected byte[] inputstreamToByteArray(InputStream ins) throws ResolverException {
      try {
         byte[] buffer = new byte[1048576];
         ByteArrayOutputStream output = new ByteArrayOutputStream();
         int n = false;

         int n;
         while(-1 != (n = ins.read(buffer))) {
            output.write(buffer, 0, n);
         }

         ins.close();
         return output.toByteArray();
      } catch (IOException var5) {
         throw new ResolverException(var5);
      }
   }

   private class RefreshMetadataTask extends TimerTask {
      private RefreshMetadataTask() {
      }

      public void run() {
         try {
            if (!AbstractReloadingMetadataResolver.this.isDestroyed()) {
               AbstractReloadingMetadataResolver.this.refresh();
            }
         } catch (ResolverException var2) {
         }
      }

      // $FF: synthetic method
      RefreshMetadataTask(Object x1) {
         this();
      }
   }
}
