package weblogic.diagnostics.harvester.internal;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.diagnostics.archive.ArchiveException;
import weblogic.diagnostics.archive.DataWriter;
import weblogic.diagnostics.archive.HarvestedDataArchive;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager.Factory;
import weblogic.diagnostics.flightrecorder.event.HarvesterDataSampleEvent;
import weblogic.diagnostics.harvester.HarvesterCollector;
import weblogic.diagnostics.harvester.HarvesterCollectorFactory;
import weblogic.diagnostics.harvester.HarvesterCollectorStatistics;
import weblogic.diagnostics.harvester.HarvesterDataSample;
import weblogic.diagnostics.harvester.HarvesterException;
import weblogic.diagnostics.harvester.I18NConstants;
import weblogic.diagnostics.harvester.I18NSupport;
import weblogic.diagnostics.harvester.LogSupport;
import weblogic.diagnostics.harvester.WLDFHarvester;
import weblogic.diagnostics.harvester.WLDFHarvesterManager;
import weblogic.diagnostics.harvester.WLDFHarvesterUtils;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.utils.ArchiveHelper;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.PropertyHelper;

public final class MetricArchiver implements HarvesterInternalConstants, I18NConstants {
   private static final String ARCHIVEJFRNAME = PropertyHelper.getProperty("weblogic.diagnostics.harvester.ArchiveToJFR", (String)null);
   private static final boolean ARCHIVEONLYTOJFR = PropertyHelper.getBoolean("weblogic.diagnostics.harvester.ArchiveToJFROnly");
   private static final FlightRecorderManager flightRecorderMgr = Factory.getInstance();
   private static final Map metricArchivers = new HashMap();
   private HarvesterCycleListener harvesterCycleListener;
   private WLDFHarvester harvester;
   private AggregatedHarvesterStatistics aggregatedStatistics = new AggregatedHarvesterStatistics();
   private DataWriter archive = null;
   private String name;
   private static final DebugLogger debugLogger = DebugSupport.getDebugLogger();

   private MetricArchiver(String name) {
      if (name == null) {
         throw new IllegalArgumentException();
      } else {
         this.name = name;
         this.harvester = WLDFHarvesterManager.getInstance().getHarvesterSingleton();
      }
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (obj == this) {
         return true;
      } else {
         return obj instanceof MetricArchiver ? ((MetricArchiver)obj).name.equals(this.name) : false;
      }
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   int getStatus() {
      return !this.isEnabled() ? 2 : 1;
   }

   public boolean isEnabled() {
      return true;
   }

   public WLDFHarvester getHarvester() {
      return this.harvester;
   }

   public void setHarvesterCycleListener(HarvesterCycleListener listener) {
      this.harvesterCycleListener = listener;
   }

   /** @deprecated */
   @Deprecated
   public static MetricArchiver getInstance() {
      return findOrCreateMetricArchiver("");
   }

   public static synchronized MetricArchiver findOrCreateMetricArchiver(String name) {
      if (name == null) {
         name = "";
      }

      if (!metricArchivers.containsKey(name)) {
         metricArchivers.put(name, new MetricArchiver(name));
      }

      return (MetricArchiver)metricArchivers.get(name);
   }

   public static synchronized MetricArchiver removeMetricArchiver(String name) {
      if (name != null && !name.equals("")) {
         return (MetricArchiver)metricArchivers.remove(name);
      } else {
         throw new IllegalArgumentException();
      }
   }

   long getSamplePeriod() {
      HarvesterCollector[] collectors = HarvesterCollectorFactory.getFactoryInstance(this.name).listHarvesterCollectors();
      return collectors.length > 0 ? collectors[0].getSamplePeriod() : 0L;
   }

   int getTotalSamplingCycles() {
      return this.aggregatedStatistics.getTotalSamplingCycles();
   }

   long getTotalConfiguredDataSampleCount() {
      return this.aggregatedStatistics.getTotalConfiguredDataSampleCount();
   }

   int getCurrentConfiguredDataSampleCount() {
      int count = 0;
      HarvesterCollector[] collectors = HarvesterCollectorFactory.getFactoryInstance(this.name).listHarvesterCollectors();
      HarvesterCollector[] var3 = collectors;
      int var4 = collectors.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         HarvesterCollector collector = var3[var5];
         count += collector.getStatistics().getCurrentDataSampleCount();
      }

      return count;
   }

   long getTotalImplicitDataSampleCount() {
      return 0L;
   }

   long getCurrentImplicitDataSampleCount() {
      return 0L;
   }

   public long getTotalSamplingTimeOutlierCount() {
      return 0L;
   }

   public boolean isCurrentSampleTimeAnOutlier() {
      return false;
   }

   public float getOutlierDetectionFactor() {
      return 0.0F;
   }

   long getCurrentSnapshotStartTime() {
      long minStartTimeNanos = -1L;
      HarvesterCollector[] collectors = HarvesterCollectorFactory.getFactoryInstance(this.name).listHarvesterCollectors();
      HarvesterCollector[] var4 = collectors;
      int var5 = collectors.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         HarvesterCollector collector = var4[var6];
         long collectorTimeNanos = collector.getStatistics().getCurrentSnapshotStartTimeNanos();
         if (minStartTimeNanos <= 0L) {
            minStartTimeNanos = collectorTimeNanos;
         } else {
            minStartTimeNanos = collectorTimeNanos;
         }
      }

      return minStartTimeNanos;
   }

   long getCurrentSnapshotElapsedTime() {
      long aggregateElapsedTime = 0L;
      HarvesterCollector[] collectors = HarvesterCollectorFactory.getFactoryInstance(this.name).listHarvesterCollectors();
      HarvesterCollector[] var4 = collectors;
      int var5 = collectors.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         HarvesterCollector collector = var4[var6];
         aggregateElapsedTime += collector.getStatistics().getCurrentSnapshotElapsedTimeNanos();
      }

      return aggregateElapsedTime;
   }

   long getMinimumSamplingTime() {
      return this.aggregatedStatistics.getMinimumSamplingTimeNanos();
   }

   long getMaximumSamplingTime() {
      return this.aggregatedStatistics.getMaximumSamplingTimeNanos();
   }

   long getTotalSamplingTime() {
      return this.aggregatedStatistics.getTotalSamplingTimeNanos();
   }

   long getAverageSamplingTime() {
      return this.aggregatedStatistics.getAverageSamplingTimeNanos();
   }

   synchronized String[] getCurrentlyHarvestedAttributes(String typeName) {
      String[] ret = new String[0];
      if (this.harvester != null) {
         HashSet attributeSet = new HashSet();
         HarvesterCollectorFactory collectorFactory = HarvesterCollectorFactory.getFactoryInstance(this.name);
         HarvesterCollector[] collectors = collectorFactory.listHarvesterCollectors();
         HarvesterCollector[] var6 = collectors;
         int var7 = collectors.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            HarvesterCollector collector = var6[var8];
            List harvestedAttributes = collector.getCurrentlyHarvestedAttributes(typeName);
            if (harvestedAttributes != null && harvestedAttributes.size() > 0) {
               attributeSet.addAll(harvestedAttributes);
            }
         }

         if (attributeSet.size() > 0) {
            ret = (String[])attributeSet.toArray(new String[attributeSet.size()]);
         }
      }

      return ret;
   }

   public String[] getConfiguredNamespaces() {
      String[] ret = new String[0];
      if (this.harvester != null) {
         ret = this.harvester.getSupportedNamespaces();
      }

      return ret;
   }

   public String getDefaultNamespace() {
      String ret = null;
      if (this.harvester != null) {
         ret = this.harvester.getDefaultNamespace();
      }

      return ret;
   }

   String[] getKnownHarvestableTypes() {
      return this.getKnownHarvestableTypes((String)null);
   }

   String[] getKnownHarvestableTypes(String namespace) {
      String[] result = new String[0];
      if (this.harvester != null) {
         try {
            String[][] knownHarvestableTypes = this.harvester.getKnownHarvestableTypes(namespace, (String)null);
            if (knownHarvestableTypes != null) {
               result = new String[knownHarvestableTypes.length];

               for(int i = 0; i < knownHarvestableTypes.length; ++i) {
                  result[i] = knownHarvestableTypes[i][0];
               }
            }
         } catch (IOException var5) {
            throw new RuntimeException(var5);
         }
      }

      return result;
   }

   String[] getKnownHarvestableInstances(String typeName) throws HarvesterException.AmbiguousTypeName, HarvesterException.HarvestableTypesNotFoundException, HarvesterException.TypeNotHarvestable {
      return this.getKnownHarvestableInstances((String)null, typeName);
   }

   synchronized String[] getKnownHarvestableInstances(String namespace, String typeName) throws HarvesterException.AmbiguousTypeName, HarvesterException.HarvestableTypesNotFoundException, HarvesterException.TypeNotHarvestable {
      String[] ret = new String[0];
      if (this.harvester != null) {
         try {
            List harvestableInstances = this.harvester.getKnownHarvestableInstances(namespace, typeName, (String)null);
            if (harvestableInstances != null) {
               ret = (String[])harvestableInstances.toArray(new String[harvestableInstances.size()]);
            }
         } catch (IOException var5) {
            throw new RuntimeException(var5);
         } catch (RuntimeException var6) {
            throw new HarvesterException.HarvestableTypesNotFoundException(getTypeNotFoundExplanation(new String[]{typeName}), var6);
         }

         if (ret == null) {
            throw new HarvesterException.HarvestableTypesNotFoundException(getTypeNotFoundExplanation(new String[]{typeName}));
         }
      }

      return ret;
   }

   synchronized String[] getCurrentlyHarvestedInstances(String typeName) {
      String[] instances = new String[0];
      if (this.harvester != null) {
         HashSet instanceSet = new HashSet();
         HarvesterCollectorFactory collectorFactory = HarvesterCollectorFactory.getFactoryInstance(this.name);
         HarvesterCollector[] collectors = collectorFactory.listHarvesterCollectors();
         HarvesterCollector[] var6 = collectors;
         int var7 = collectors.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            HarvesterCollector collector = var6[var8];
            List harvestedInstances = collector.getCurrentlyHarvestedInstances(typeName);
            if (harvestedInstances != null && harvestedInstances.size() > 0) {
               instanceSet.addAll(harvestedInstances);
            }
         }

         if (instanceSet.size() > 0) {
            instances = (String[])instanceSet.toArray(new String[instanceSet.size()]);
         }
      }

      return instances;
   }

   synchronized String[][] getHarvestableAttributes(String typeName) throws IOException {
      String[][] archivableAttributes = new String[0][];
      if (this.harvester != null) {
         String[][] totalAttributes = this.harvester.getHarvestableAttributes(typeName, (String)null);
         if (totalAttributes != null) {
            ArrayList filteredMetaData = new ArrayList();
            String[][] var5 = totalAttributes;
            int var6 = totalAttributes.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               String[] attributeMetaData = var5[var7];
               String attrType = attributeMetaData[1];
               if (HarvesterSnapshot.isSupportedValueType(attrType)) {
                  filteredMetaData.add(attributeMetaData);
               }
            }

            if (filteredMetaData.size() > 0) {
               archivableAttributes = (String[][])filteredMetaData.toArray(archivableAttributes);
            }
         }
      }

      return archivableAttributes;
   }

   public synchronized String getHarvestableType(String instanceName) {
      String type = null;
      if (this.harvester != null) {
         String canonicalName = instanceName;

         try {
            canonicalName = (new ObjectName(instanceName)).getCanonicalName();
         } catch (MalformedObjectNameException var5) {
         } catch (NullPointerException var6) {
         }

         type = WLDFHarvesterUtils.getTypeForInstance(canonicalName);
         if (type == null) {
            type = this.harvester.getTypeForInstance(canonicalName);
         }
      }

      return type;
   }

   @Inject
   public void setHarvestedDataArchive(HarvestedDataArchive archiveService) {
      try {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Injecting HarvestedDataArchive through setter:" + archiveService);
         }

         this.archive = archiveService.getDataWriter();
      } catch (ArchiveException var3) {
         LogSupport.logUnexpectedException("Could not find harvester archive", var3);
      }

   }

   private DataWriter getArchive() {
      if (this.archive == null) {
         HarvestedDataArchive archiveService = (HarvestedDataArchive)GlobalServiceLocator.getServiceLocator().getService(HarvestedDataArchive.class, new Annotation[0]);
         if (archiveService != null) {
            try {
               this.archive = archiveService.getDataWriter();
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Iniialized MetricArchive.archive from GlobalServiceLocator:" + this.archive);
               }
            } catch (ArchiveException var3) {
               LogSupport.logUnexpectedException("Could not find harvester archive", var3);
            }
         }
      }

      return this.archive;
   }

   private boolean shouldGenerateJFREvents(HarvesterSnapshot snapshot) {
      if (snapshot != null && flightRecorderMgr.isRecordingPossible()) {
         return ARCHIVEJFRNAME != null && ARCHIVEJFRNAME.equals(snapshot.getName());
      } else {
         return false;
      }
   }

   void archive(HarvesterCycleData cycleData) {
      if (ArchiveHelper.isFilestoreNeededAndNotAvailable()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("MetricArchiver " + this.name + " not archiving data since file-store archive is not available");
         }

      } else {
         HarvesterSnapshot snapshot = cycleData.getCycleSnapshot();
         if (snapshot != null) {
            DataWriter dataArchive = this.getArchive();
            boolean generateJFR = this.shouldGenerateJFREvents(snapshot);
            if (dataArchive != null || generateJFR) {
               Collection data = snapshot.getHarvesterDataSamples();
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("MetricArchiver " + this.name + " archiving " + data.size() + " samples...");
               }

               if (data.size() != 0) {
                  try {
                     if (dataArchive != null && (!ARCHIVEONLYTOJFR || !generateJFR)) {
                        dataArchive.writeData((Collection)(generateJFR ? new DelegatingJFREventCollection(data) : data));
                     } else if (generateJFR) {
                        Iterator iter = new DelegatingJFREventIterator(data.iterator());

                        while(iter.hasNext()) {
                           iter.next();
                        }
                     }
                  } catch (IOException var7) {
                     DiagnosticsLogger.logErrorHarvesting(var7);
                  }

                  this.mergeHarvesterCycleStats(cycleData.getCycleStatistics());
                  if (this.harvesterCycleListener != null) {
                     this.harvesterCycleListener.harvestCycleOccurred(snapshot.getSnapshotStartTimeMillis(), snapshot.getName());
                  }

               }
            }
         }
      }
   }

   private void mergeHarvesterCycleStats(HarvesterCollectorStatistics statistics) {
      this.aggregatedStatistics.merge(statistics);
   }

   private static String getTypeNotFoundExplanation(String[] types) {
      if (types != null && types.length != 0) {
         String typesListStr = Arrays.toString(types);
         return I18NSupport.formatter().getTypesDoNotExistMessage(typesListStr);
      } else {
         return I18NSupport.formatter().getTypeNotDefinedMessage();
      }
   }

   private static class DelegatingJFREventIterator implements Iterator {
      private Iterator delegate;
      private HarvesterDataSampleEvent event;

      public DelegatingJFREventIterator(Iterator delegate) {
         this.delegate = delegate;
         this.event = new HarvesterDataSampleEvent();
      }

      public void remove() {
         this.delegate.remove();
      }

      public boolean hasNext() {
         return this.delegate.hasNext();
      }

      public Object next() {
         HarvesterDataSample sample = (HarvesterDataSample)this.delegate.next();
         if (sample != null) {
            this.event.setRecordID(sample.getRecordID());
            this.event.setTimestamp(sample.getTimestamp());
            this.event.setInstanceName(sample.getInstanceName());
            this.event.setTypeName(sample.getTypeName());
            this.event.setAttributeName(sample.getAttributeName());
            Object val = sample.getAttributeValue();
            if (val instanceof Number) {
               this.event.setAttributeValueDouble(((Number)((Number)val)).doubleValue());
            } else {
               this.event.setAttributeValueString(val.toString());
            }

            try {
               this.event.commit();
            } catch (Throwable var4) {
               if (MetricArchiver.debugLogger.isDebugEnabled()) {
                  MetricArchiver.debugLogger.debug("JFR harvester event commit failed", var4);
               }
            }

            this.event.reset();
         }

         return sample;
      }
   }

   private static class DelegatingJFREventCollection implements Collection {
      private boolean iteratorReturnedOnce = false;
      private Collection delegate;

      public DelegatingJFREventCollection(Collection delegate) {
         this.delegate = delegate;
      }

      public boolean add(Object arg0) {
         return this.delegate.add(arg0);
      }

      public boolean addAll(Collection arg0) {
         return this.delegate.addAll(arg0);
      }

      public void clear() {
         this.delegate.clear();
      }

      public boolean contains(Object arg0) {
         return this.delegate.contains(arg0);
      }

      public boolean containsAll(Collection arg0) {
         return this.delegate.containsAll(arg0);
      }

      public boolean isEmpty() {
         return this.delegate.isEmpty();
      }

      public boolean remove(Object arg0) {
         return this.delegate.remove(arg0);
      }

      public boolean removeAll(Collection arg0) {
         return this.delegate.removeAll(arg0);
      }

      public boolean retainAll(Collection arg0) {
         return this.delegate.retainAll(arg0);
      }

      public int size() {
         return this.delegate.size();
      }

      public Object[] toArray() {
         return this.delegate.toArray();
      }

      public Object[] toArray(Object[] arg0) {
         return this.delegate.toArray(arg0);
      }

      public Iterator iterator() {
         Iterator iter = this.delegate.iterator();
         if (this.iteratorReturnedOnce) {
            return iter;
         } else {
            this.iteratorReturnedOnce = true;
            return new DelegatingJFREventIterator(iter);
         }
      }
   }
}
