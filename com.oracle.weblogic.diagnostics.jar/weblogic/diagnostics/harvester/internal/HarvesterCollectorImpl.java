package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.harvester.HarvestCallback;
import com.bea.adaptive.harvester.WatchedValues;
import com.oracle.weblogic.diagnostics.timerservice.TimerListener;
import com.oracle.weblogic.diagnostics.timerservice.TimerService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.JMException;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFHarvesterBean;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.harvester.HarvesterCollector;
import weblogic.diagnostics.harvester.HarvesterCollectorStatistics;
import weblogic.diagnostics.harvester.HarvesterDataSample;
import weblogic.diagnostics.harvester.HarvesterRuntimeException;
import weblogic.diagnostics.harvester.I18NConstants;
import weblogic.diagnostics.harvester.InstanceNameNormalizer;
import weblogic.diagnostics.harvester.WLDFHarvester;
import weblogic.diagnostics.harvester.WLDFHarvesterManager;
import weblogic.diagnostics.i18n.DiagnosticsHarvesterLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextHarvesterTextFormatter;
import weblogic.diagnostics.timerservice.WLDFTimerServiceFactory;
import weblogic.diagnostics.utils.DateUtils;
import weblogic.diagnostics.utils.PartitionHelper;
import weblogic.t3.srvr.T3Srvr;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.PlatformConstants;

public final class HarvesterCollectorImpl implements HarvesterInternalConstants, HarvesterCollector, I18NConstants, TimerListener {
   private static final DiagnosticsTextHarvesterTextFormatter txtFormatter = DiagnosticsTextHarvesterTextFormatter.getInstance();
   private static final int DEFAULT_SAMPLE_PERIOD_MILLS = 300000;
   private HarvesterSnapshot inProgressSnapshot;
   private HarvesterSnapshot currentSnapshot;
   private long startTimeMillis = 0L;
   private static final DebugLogger debugLogger = DebugSupport.getDebugLogger();
   private static final DebugLogger debugDataLogger = DebugSupport.getLowLevelDebugLogger();
   private WLDFHarvester harvester;
   private HarvesterConfiguration currentConfiguration;
   private WatchedValues watchedValues = null;
   private int wvid = -1;
   private long minimumSampleInterval;
   private boolean enabled;
   private HarvesterCollectorStatisticsImpl collectorStatistics = new HarvesterCollectorStatisticsImpl();
   private boolean initialized;
   private String name;
   private long configuredSamplePeriod;
   private String moduleName;
   private AtomicBoolean timerCallbackInProgress = new AtomicBoolean(false);
   private String partitionId = "";
   private String partitionName = "";
   private T3Srvr service = (T3Srvr)LocatorUtilities.getService(T3Srvr.class);

   public String getName() {
      return this.name;
   }

   public synchronized void initialize() {
      if (!this.initialized) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Collector " + this.getName() + " initializing");
         }

         this.initialized = true;
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Collector " + this.getName() + " already initialized");
      }

   }

   public synchronized void enable() {
      if (this.enabled) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Collector " + this.getName() + " already enabled");
         }

      } else if (!this.initialized) {
         throw new HarvesterRuntimeException(txtFormatter.getCollectorNotInitializedText());
      } else {
         this.getTimerService().unregisterListener(this);
         this.minimumSampleInterval = (long)Math.round((float)this.currentConfiguration.getSamplePeriodMillis() / 100.0F * 50.0F);
         DiagnosticsLogger.logHarvestTimerInitiated(this.currentConfiguration.getSamplePeriodMillis());
         this.enabled = true;
         if (this.currentConfiguration.isEnabled() && this.setupWatchedValues()) {
            this.getTimerService().registerListener(this);
         }

         if (debugLogger.isDebugEnabled()) {
            String s = this.dumpAsString("  ");
            debugLogger.debug(PlatformConstants.EOL + s);
         }

         this.postEnabledStatusMessage();
      }
   }

   public synchronized void disable() {
      this.getTimerService().unregisterListener(this);
      this.enabled = false;
      this.postEnabledStatusMessage();
   }

   synchronized void destroy() {
      this.disable();
      this.deleteHarvesterWatchedValues();
      this.currentConfiguration = null;
      this.currentSnapshot = null;
      this.initialized = false;
   }

   public void timerExpired() {
      if (this.timerCallbackInProgress.compareAndSet(false, true)) {
         try {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Timer expired for harvester " + this.name + "with period = " + this.getSamplePeriodSeconds() + " at " + DateUtils.nanoDateToString(System.currentTimeMillis() * 1000000L, true));
            }

            this.execute();
         } catch (Exception var5) {
            throw new HarvesterRuntimeException(var5);
         } finally {
            this.timerCallbackInProgress.set(false);
         }
      }

   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public Map retrieveSnapshot() throws Exception {
      HashMap snapshot = new HashMap();
      if (this.setupWatchedValues()) {
         HarvesterSnapshot hvstSnapshot = new HarvesterSnapshot(System.currentTimeMillis(), this.getName(), this.partitionId, this.partitionName);
         synchronized(this) {
            if (this.wvid >= 0) {
               this.harvester.harvest(this.wvid);
               List valuesSnapshot = this.watchedValues.getAllMetricValues();
               hvstSnapshot.setDataSamples(valuesSnapshot);
               this.watchedValues.resetRawValues();
            }
         }

         Collection samples = hvstSnapshot.getHarvesterDataSamples();
         if (samples != null) {
            Iterator var10 = samples.iterator();

            while(var10.hasNext()) {
               HarvesterDataSample sample = (HarvesterDataSample)var10.next();
               Attribute attr = new Attribute(sample.getAttributeName(), sample.getAttributeValue());
               ObjectName sampleObjectName = new ObjectName(sample.getInstanceName());
               AttributeList attributeList = (AttributeList)snapshot.get(sampleObjectName);
               if (attributeList != null) {
                  attributeList.add(attr);
               } else {
                  attributeList = new AttributeList();
                  attributeList.add(attr);
                  snapshot.put(sampleObjectName, attributeList);
               }
            }
         }
      }

      return snapshot;
   }

   public HarvesterCollectorStatistics getStatistics() {
      return this.collectorStatistics;
   }

   public int getFrequency() {
      return this.getSamplePeriodSeconds();
   }

   synchronized HarvesterSnapshot getCurrentSnapshot() {
      return this.currentSnapshot;
   }

   public List getCurrentlyHarvestedAttributes(String typeName) {
      return this.isEnabled() ? this.harvester.getHarvestedAttributes(this.wvid, typeName, (String)null) : null;
   }

   public List getCurrentlyHarvestedInstances(String typeName) {
      return this.isEnabled() ? this.harvester.getHarvestedInstances(this.wvid, typeName, (String)null) : null;
   }

   public long getSamplePeriod() {
      return this.configuredSamplePeriod;
   }

   HarvesterCollectorImpl(String pName, WLDFResourceBean bean) {
      this.partitionName = pName;
      this.partitionId = PartitionHelper.getPartitionId(this.partitionName);
      WLDFHarvesterBean harvesterBean = bean.getHarvester();
      this.name = harvesterBean.getName();
      this.moduleName = bean.getName();
      this.configuredSamplePeriod = harvesterBean.getSamplePeriod();
      this.currentConfiguration = new HarvesterConfiguration(bean.getHarvester());
      this.harvester = WLDFHarvesterManager.getInstance().getHarvesterSingleton();
   }

   private synchronized boolean setupWatchedValues() {
      if (this.wvid < 0) {
         try {
            MetricSpecification[] enabledMetricSpecifications = this.currentConfiguration.getEnabledMetricSpecifications();
            if (this.currentConfiguration.getNumEnabledTypes() > 0) {
               if (this.watchedValues == null) {
                  this.watchedValues = this.harvester.createWatchedValues(this.getName(), this.partitionId, this.partitionName);
                  this.watchedValues.setDefaultSamplePeriod(this.getSamplePeriodSeconds());
               }

               MetricSpecification[] var2 = enabledMetricSpecifications;
               int var3 = enabledMetricSpecifications.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  MetricSpecification spec = var2[var4];
                  this.registerWatchedValues(spec);
               }

               this.wvid = this.harvester.addWatchedValues(this.currentConfiguration.getName(), this.watchedValues, (HarvestCallback)null);
            }
         } catch (Exception var6) {
            throw new RuntimeException(var6);
         }
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Harvester [" + this.partitionName + "]" + this.currentConfiguration.getName() + " already has a valid WV ID (" + this.wvid + "), skipping registration of watched values.");
      }

      return this.wvid >= 0;
   }

   int getSamplePeriodSeconds() {
      long samplePeriodMillis = this.currentConfiguration != null ? this.currentConfiguration.getSamplePeriodMillis() : 300000L;
      return (int)(samplePeriodMillis / 1000L);
   }

   private void registerWatchedValues(MetricSpecification spec) {
      this.registerWatchedValues(spec.getNamespace(), spec.getTypeName(), spec.getRequestedHarvestableInstances(), spec.getRequestedHarvestableAttributes(), spec.isEnabled());
   }

   private void deleteHarvesterWatchedValues() {
      if (this.wvid > -1) {
         try {
            this.harvester.deleteWatchedValues(this.watchedValues);
         } catch (Exception var2) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Caught exception unregistering WatchedValues", var2);
            }
         }

         this.watchedValues = null;
         this.wvid = -1;
      }

   }

   private synchronized void execute() throws IOException, JMException {
      if (this.enabled && this.currentConfiguration.isEnabled()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("HarvesterCollector " + this.name + " entered sync block at " + DateUtils.nanoDateToString(System.currentTimeMillis() * 1000000L, true));
         }

         long startTimeNanos = System.nanoTime();
         long currentTimeMillis = System.currentTimeMillis();
         long elapsedTime = currentTimeMillis - this.startTimeMillis;
         if (elapsedTime < this.minimumSampleInterval) {
            DiagnosticsLogger.logInsufficientTimeBetweenHarvesterCycles(elapsedTime);
         } else {
            this.startTimeMillis = currentTimeMillis;
            if (this.isServerRunning()) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("HarvesterCollector" + this.name + " executing harvesting process.");
               }

               if (this.watchedValues == null) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Watched values for not set, skipping harvest cycle");
                  }

                  DiagnosticsLogger.logHarvesterIsDisabled();
               } else {
                  this.performHarvestCycle(startTimeNanos);
               }
            } else {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Scheduled harvest cycle for HarvesterCollector" + this.name + " postponed util server is started.");
               }

            }
         }
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("A pending harvester cycle is being skipped because the harvester is not enabled.");
         }

      }
   }

   private boolean isServerRunning() {
      return this.service.getRunState() == 2;
   }

   private void performHarvestCycle(long startTimeNanos) {
      long elapsedTotalTimeNanos = 0L;
      long snapshotTimeMillis = System.currentTimeMillis();
      this.inProgressSnapshot = new HarvesterSnapshot(snapshotTimeMillis, this.moduleName, this.partitionId, this.partitionName);
      this.harvester.harvest(this.wvid);
      List valuesSnapshot = this.watchedValues.getAllMetricValues();
      this.inProgressSnapshot.setDataSamples(valuesSnapshot);
      int configuredDataCount = this.inProgressSnapshot.getDataSampleCount();
      if (configuredDataCount == 0) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("No data was harvested");
         }

      } else {
         debugLogger.debug("HarvesterCollector " + this.name + " harvested " + configuredDataCount + " data samples from configured specifications.");
         if (debugDataLogger.isDebugEnabled() && configuredDataCount > 0) {
            String configuredData = PlatformConstants.EOL + PlatformConstants.EOL + "*************************************************" + PlatformConstants.EOL + "CONFIGURED DATA SAMPLES (set in the last cycle):" + PlatformConstants.EOL + "*************************************************" + PlatformConstants.EOL + PlatformConstants.EOL;
            long samplesTimeMillis = this.inProgressSnapshot.getSnapshotStartTimeMillis();
            configuredData = configuredData + "Timestamp of the most recent collection: " + DateUtils.nanoDateToString(samplesTimeMillis * 1000000L, true) + PlatformConstants.EOL + PlatformConstants.EOL;

            String sampleStr;
            for(Iterator it = this.inProgressSnapshot.getHarvesterDataSamples().iterator(); it.hasNext(); configuredData = configuredData + sampleStr + PlatformConstants.EOL) {
               HarvesterDataSample sample = (HarvesterDataSample)it.next();
               sampleStr = DBG_USE_LONG_DATA_FORM ? sample.toStringLong() : sample.toStringShort();
            }

            debugDataLogger.debug(configuredData);
         }

         elapsedTotalTimeNanos = System.nanoTime() - startTimeNanos;
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Nanosecond timings for this harvest cycle:" + elapsedTotalTimeNanos);
         }

         this.inProgressSnapshot.setSnapshotElapsedTimeNanos(elapsedTotalTimeNanos);
         this.currentSnapshot = this.inProgressSnapshot;
         this.inProgressSnapshot = null;
         this.collectorStatistics.updateStatistics(this.startTimeMillis, configuredDataCount, elapsedTotalTimeNanos);
         this.watchedValues.resetRawValues();
         HarvesterSamplesQueue.enqueue(this.partitionName, this.currentSnapshot, this.collectorStatistics);
      }
   }

   private void registerWatchedValues(String namespace, String typeName, String[] instances, String[] attributes, boolean isEnabled) {
      if (instances != null && instances.length != 0) {
         String[] var6 = instances;
         int var7 = instances.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String instanceName = var6[var8];
            InstanceNameNormalizer n = new InstanceNameNormalizer(instanceName);

            try {
               this.registerWatchedValues(namespace, typeName, n.translateHarvesterSpec(), n.isRegexPattern(), attributes, isEnabled);
            } catch (Exception var12) {
               DiagnosticsHarvesterLogger.logInstanceNameInvalid(instanceName);
            }
         }
      } else {
         this.registerWatchedValues(namespace, typeName, (String)null, false, attributes, isEnabled);
      }

   }

   private void registerWatchedValues(String namespace, String typeName, String instanceSpec, boolean instanceIsRegExPattern, String[] attributes, boolean isEnabled) {
      if (attributes != null && attributes.length != 0) {
         String[] var7 = attributes;
         int var8 = attributes.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String attributeName = var7[var9];
            this.watchedValues.addMetric(namespace, typeName, instanceSpec, attributeName, false, instanceIsRegExPattern, false, isEnabled);
         }
      } else {
         this.watchedValues.addMetric(namespace, typeName, instanceSpec, (String)null, false, instanceIsRegExPattern, false, isEnabled);
      }

   }

   private void postEnabledStatusMessage() {
      String I18NState = this.enabled ? ACTIVE_I18N : INACTIVE_I18N;
      DiagnosticsLogger.logHarvestState(I18NState);
   }

   private TimerService getTimerService() {
      return WLDFTimerServiceFactory.getTimerService();
   }

   String dumpAsString(String indent) {
      ByteArrayOutputStream bs = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(bs);
      this.dumpWatchedValues(ps, indent);
      String s = bs.toString();
      return s;
   }

   private void dumpWatchedValues(PrintStream str, String indent) {
      str.println("");
      str.println(indent + "Active Harvester Configuration: " + this.currentConfiguration.getName());
      str.println(indent + "  enabled: " + this.enabled);
      str.println(indent + "  samplePeriod: " + this.currentConfiguration.getSamplePeriodMillis());
      str.println(indent + "  Current Configured Harvester Specifications:");
      if (this.watchedValues != null) {
         this.watchedValues.dump("\t", true, true, false);
      }

   }
}
