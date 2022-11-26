package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.harvester.WatchedValues;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.harvester.HarvesterDataSample;
import weblogic.diagnostics.harvester.WLDFHarvesterUtils;

final class HarvesterSnapshot {
   private static final DebugLogger DBG_DATA = DebugSupport.getLowLevelDebugLogger();
   private Collection harvestedSamples;
   private long snapshotTimeMillis;
   private long snapshotElapsedTimeNanos;
   private String name;
   private String partitionId = "";
   private String partitionName = "";

   static boolean isSupportedValueType(String className) {
      if (DBG_DATA.isDebugEnabled()) {
         DBG_DATA.debug("checking if value of type " + className + " is supported");
      }

      return HarvesterSnapshot.SupportedTypesHolder.SUPPORTED_TYPE_NAMES.contains(className);
   }

   static boolean isSupportedValueType(Class attrType) {
      if (DBG_DATA.isDebugEnabled()) {
         DBG_DATA.debug("checking if value of type " + attrType.getName() + " is supported");
      }

      return HarvesterSnapshot.SupportedTypesHolder.SUPPORTED_TYPES.contains(attrType);
   }

   private static Set createSupportedTypeNameSet() {
      Set result = new HashSet();
      Class[] var1 = HarvesterSnapshot.SupportedTypesHolder.supportedClasses;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Class clazz = var1[var3];
         result.add(clazz.getName());
      }

      return result;
   }

   private static Set createSupportedTypesSet() {
      Set result = new HashSet();
      Class[] var1 = HarvesterSnapshot.SupportedTypesHolder.supportedClasses;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Class clazz = var1[var3];
         result.add(clazz);
      }

      return result;
   }

   HarvesterSnapshot(long snapshotTimeMillis, String name, String partitionId, String partitionName) {
      this.snapshotTimeMillis = snapshotTimeMillis;
      this.name = name;
      this.partitionId = partitionId;
      this.partitionName = partitionName;
   }

   String getName() {
      return this.name;
   }

   int getDataSampleCount() {
      return this.harvestedSamples.size();
   }

   long getSnapshotStartTimeMillis() {
      return this.snapshotTimeMillis;
   }

   long getSnapshotElapsedTimeNanos() {
      return this.snapshotElapsedTimeNanos;
   }

   void setSnapshotElapsedTimeNanos(long time) {
      this.snapshotElapsedTimeNanos = time;
   }

   Collection getHarvesterDataSamples() {
      return this.harvestedSamples;
   }

   synchronized void setDataSamples(Collection dataSamples) {
      this.harvestedSamples = this.buildSamplesSet(dataSamples);
   }

   private List buildSamplesSet(Collection wvValues) {
      List sampleSet = new ArrayList();
      Iterator valueIt = wvValues.iterator();

      while(valueIt.hasNext()) {
         WatchedValues.Values value = (WatchedValues.Values)valueIt.next();
         List rawValues = value.getValues().getRawValues();
         Iterator it = rawValues.iterator();

         while(it.hasNext()) {
            WatchedValues.Values.RawValueData rawValue = (WatchedValues.Values.RawValueData)it.next();
            Object data = rawValue.getValue();
            if (data != null) {
               Class dataClass = data.getClass();
               if (dataClass.isArray()) {
                  this.addSamples(sampleSet, rawValue, (Object[])((Object[])data));
               } else {
                  this.addSample(sampleSet, rawValue, data);
               }
            }
         }
      }

      return sampleSet;
   }

   private void addSamples(List sampleSet, WatchedValues.Values.RawValueData rawValue, Object[] dataItems) {
      Object[] var4 = dataItems;
      int var5 = dataItems.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Object data = var4[var6];
         if (data != null) {
            if (data.getClass().isArray()) {
               this.addSamples(sampleSet, rawValue, (Object[])((Object[])data));
            } else {
               this.addSample(sampleSet, rawValue, data);
            }
         }
      }

   }

   private void addSample(List sampleSet, WatchedValues.Values.RawValueData rawValue, Object data) {
      Class dataClass = data.getClass();
      if (isSupportedValueType(dataClass)) {
         sampleSet.add(new HarvesterDataSample(this.getName(), this.snapshotTimeMillis, rawValue.getTypeName(), rawValue.getInstanceName(), rawValue.getAttributeName(), rawValue.getValue(), this.partitionId, this.partitionName));
      } else if (data instanceof WatchedValues.AttributeTrackedDataItem) {
         WatchedValues.AttributeTrackedDataItem trackedDataItem = (WatchedValues.AttributeTrackedDataItem)data;
         String attrName = WLDFHarvesterUtils.buildDataContextString(trackedDataItem.getDataContext());
         Object rawData = trackedDataItem.getData();
         if (rawData != null && isSupportedValueType(rawData.getClass())) {
            sampleSet.add(new HarvesterDataSample(this.getName(), this.snapshotTimeMillis, rawValue.getTypeName(), rawValue.getInstanceName(), attrName, rawData, this.partitionId, this.partitionName));
         } else if (DBG_DATA.isDebugEnabled()) {
            this.debugInvalidDataType(attrName, rawValue.getInstanceName(), rawValue.getTypeName());
         }
      } else if (DBG_DATA.isDebugEnabled()) {
         this.debugInvalidDataType(rawValue.getAttributeName(), rawValue.getInstanceName(), rawValue.getTypeName());
      }

   }

   private void debugInvalidDataType(String attributeName, String instanceName, String typeName) {
      DBG_DATA.debug("Data value for attribute " + attributeName + " of instance " + instanceName + " is of type " + typeName + ", which is not supported for harvesting");
   }

   private static class SupportedTypesHolder {
      private static final Class[] supportedClasses;
      private static final Set SUPPORTED_TYPES;
      private static final Set SUPPORTED_TYPE_NAMES;

      static {
         supportedClasses = new Class[]{String.class, Integer.class, Integer.TYPE, Boolean.class, Boolean.TYPE, Long.class, Long.TYPE, Double.class, Double.TYPE, Character.class, Character.TYPE, Float.TYPE, Float.class, Byte.TYPE, Byte.class};
         SUPPORTED_TYPES = HarvesterSnapshot.createSupportedTypesSet();
         SUPPORTED_TYPE_NAMES = HarvesterSnapshot.createSupportedTypeNameSet();
      }
   }
}
