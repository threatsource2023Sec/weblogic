package com.bea.adaptive.harvester;

import com.bea.adaptive.harvester.utils.collections.ExtensibleList;
import com.bea.adaptive.harvester.utils.date.DateUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.management.ObjectName;

public class WatchedValuesImpl implements WatchedValues {
   private Set instanceFilters;
   private String partitionId;
   private String partitionName;
   private boolean partitionScoped;
   private static final int DEF_SAMPLE_PERIOD = 5;
   private String name;
   private long previousSamplePeriod;
   ArrayList mostRecentSamplePeriods;
   private ExtensibleList valuesAll;
   private long mostRecentValuesTimestamp;
   private long mostRecentValuesCount;
   private long mostRecentAttrsWithErrorsCount;
   private int defaultSamplePeriod;
   private int valuesIndex;
   private int id;
   private boolean shared;
   private boolean attributeNameTrackingEnabled;

   public WatchedValuesImpl(String name, String partitionId, String partitionName, Set filterSet) {
      this.previousSamplePeriod = -1L;
      this.mostRecentSamplePeriods = new ArrayList();
      this.defaultSamplePeriod = 5;
      this.valuesIndex = 0;
      this.id = -1;
      this.attributeNameTrackingEnabled = false;
      this.name = name;
      this.partitionId = partitionId;
      this.partitionName = partitionName;
      this.instanceFilters = filterSet;
      this.partitionScoped = partitionId != null && !partitionId.isEmpty() && partitionName != null && !partitionName.isEmpty();
      this.initialize();
   }

   public WatchedValuesImpl(String name) {
      this(name, (String)null, (String)null, new HashSet());
   }

   public WatchedValuesImpl(String name, int defaultSamplePeriod) {
      this(name);
      this.defaultSamplePeriod = defaultSamplePeriod;
   }

   private void initialize() {
      this.valuesAll = new ExtensibleList();
   }

   public void setTimeStamp(long timestamp) {
      this.mostRecentValuesTimestamp = timestamp;
      this.mostRecentValuesCount = 0L;
      this.mostRecentAttrsWithErrorsCount = 0L;
      this.mostRecentSamplePeriods.clear();
      this.previousSamplePeriod = -1L;
      this.resetRawValues();
   }

   public synchronized void resetRawValues() {
      Iterator valuesIt = this.valuesAll.iterator();

      while(valuesIt.hasNext()) {
         WatchedValues.Values v = (WatchedValues.Values)valuesIt.next();
         v.resetRawValues();
      }

   }

   public long getMostRecentValuesTimestamp() {
      return this.mostRecentValuesTimestamp;
   }

   public List getMostRecentSamplePeriods() {
      return this.mostRecentSamplePeriods;
   }

   private void addSamplePeriod(long samplePeriod) {
      Iterator it = this.mostRecentSamplePeriods.iterator();

      long knownSamplePeriod;
      do {
         if (!it.hasNext()) {
            this.mostRecentSamplePeriods.add(samplePeriod);
            return;
         }

         knownSamplePeriod = (Long)it.next();
      } while(knownSamplePeriod != samplePeriod);

   }

   public Iterator getWatchedMetrics() {
      return this.valuesAll != null ? this.valuesAll.iterator() : Collections.EMPTY_LIST.iterator();
   }

   public WatchedValues.Values getMetric(int vid) {
      return (WatchedValues.Values)this.valuesAll.get(vid);
   }

   public Iterator getMostRecentValues() {
      return new CycleBasedValuesIterator(this.getWatchedMetrics(), this.mostRecentValuesTimestamp);
   }

   public long getMostRecentValuesCount() {
      return this.mostRecentValuesCount;
   }

   public long getMostRecentAttrsWithErrorsCount() {
      return this.mostRecentAttrsWithErrorsCount;
   }

   public synchronized List getAllMetricValues() {
      return this.valuesAll;
   }

   public synchronized Iterator getAllMetricValuesFor(String instName, String typeName) {
      ArrayList l = new ArrayList();
      Iterator it = this.valuesAll.iterator();

      while(it.hasNext()) {
         WatchedValues.Values metric = (WatchedValues.Values)it.next();
         if (metric.matches(metric.getNamespace(), typeName, instName)) {
            l.add(metric);
         }
      }

      return l.iterator();
   }

   public WatchedValues.Values addMetric(String typeName, String instanceName, String attributeName, boolean typeIsPattern, boolean instIsPattern, boolean attrIsPattern, int vid) {
      return this.addMetric(typeName, instanceName, attributeName, this.getDefaultSamplePeriod(), typeIsPattern, instIsPattern, attrIsPattern, true, vid);
   }

   public WatchedValues.Values addMetric(String typeName, String instanceName, String attributeName, int samplePeriod, boolean typeIsPattern, boolean instIsPattern, boolean attrIsPattern, boolean isEnabled) {
      return this.addMetric(typeName, instanceName, attributeName, samplePeriod, typeIsPattern, instIsPattern, attrIsPattern, isEnabled, this.computeNextAvailableVID());
   }

   public WatchedValues.Values addMetric(String typeName, String instanceName, String attributeName, int samplePeriod, boolean typeIsPattern, boolean instIsPattern, boolean attrIsPattern, boolean isEnabled, int vid) {
      return this.addMetric((String)null, typeName, instanceName, attributeName, samplePeriod, typeIsPattern, instIsPattern, attrIsPattern, isEnabled, vid);
   }

   public WatchedValues.Values addMetric(String namespace, String typeName, String instanceName, String attributeName, boolean typeIsPattern, boolean instIsPattern, boolean attrIsPattern, boolean isEnabled) {
      return this.addMetric(namespace, typeName, instanceName, attributeName, this.getDefaultSamplePeriod(), typeIsPattern, instIsPattern, attrIsPattern, isEnabled, this.computeNextAvailableVID());
   }

   public WatchedValues.Values addMetric(String namespace, String typeName, String instanceName, String attributeName, boolean typeIsPattern, boolean instIsPattern, boolean attrIsPattern, boolean isEnabled, int vid) {
      return this.addMetric(namespace, typeName, instanceName, attributeName, this.getDefaultSamplePeriod(), typeIsPattern, instIsPattern, attrIsPattern, isEnabled, vid);
   }

   public WatchedValues.Values addMetric(String namespace, String typeName, String instanceName, String attributeName, int samplePeriod, boolean typeIsPattern, boolean instIsPattern, boolean attrIsPattern, boolean isEnabled, int vid) {
      WatchedValues.Values value = new ValuesImpl(vid, namespace, typeName, instanceName, attributeName, samplePeriod, isEnabled, typeIsPattern, instIsPattern, attrIsPattern);
      this.addMetric(value);
      return value;
   }

   private WatchedValues.Values addMetric(WatchedValues.Values candidate) {
      if (candidate.attributeIsPattern()) {
         throw new UnsupportedOperationException("Pattern not currently supported for attribute names.");
      } else {
         Iterator var2;
         WatchedValues.Values value;
         if (candidate.getVID() < 0) {
            var2 = this.getAllMetricValues().iterator();

            while(var2.hasNext()) {
               value = (WatchedValues.Values)var2.next();
               if (value.compareAttributes(candidate)) {
                  return value;
               }
            }
         } else {
            var2 = this.getAllMetricValues().iterator();

            while(var2.hasNext()) {
               value = (WatchedValues.Values)var2.next();
               if (value.getVID() == candidate.getVID()) {
                  throw new RuntimeException("VID conflict: " + candidate.getVID());
               }
            }
         }

         this.valuesAll.set(candidate.getVID(), candidate);
         return candidate;
      }
   }

   public boolean isAttributeNameTrackingEnabled() {
      return this.attributeNameTrackingEnabled;
   }

   public void setAttributeNameTrackingEnabled(boolean trackActualAttributeNames) {
      this.attributeNameTrackingEnabled = trackActualAttributeNames;
   }

   static boolean areEqual(Object s1, Object s2) {
      if (s1 != null && s2 != null) {
         return s1.equals(s2);
      } else {
         return s1 == s2;
      }
   }

   public int computeNextAvailableVID() {
      return this.valuesIndex++;
   }

   public String dump(String indent, boolean currentOnly, boolean internal, boolean shortForm) {
      StringBuffer s = new StringBuffer(512);
      s.append("\n\n").append(indent);
      s.append("*************************************************\n");
      s.append(indent).append("WATCHED VALUES ").append(currentOnly ? "(set in the last cycle)" : "(all)");
      s.append(" for set ").append(this.name).append(":").append("\n");
      s.append(indent).append("*************************************************\n\n");
      s.append(indent).append("Timestamp of the most recent collection: ").append(DateUtils.nanoDateToString(this.mostRecentValuesTimestamp, true)).append("\n");
      s.append(indent).append("Count of values collected in the most recent collection: ").append(this.mostRecentValuesCount).append("\n\n");
      s.append(indent).append("Count of errors ocurring in the most recent collection: ").append(this.mostRecentAttrsWithErrorsCount).append("\n\n");
      Iterator valuesSets = null;
      if (currentOnly) {
         valuesSets = this.getMostRecentValues();
      } else {
         valuesSets = this.getWatchedMetrics();
      }

      Iterator it = valuesSets;

      while(it.hasNext()) {
         ValuesImpl v = (ValuesImpl)it.next();
         s.append(v.dump(indent + "  ", internal, shortForm));
      }

      return s.toString();
   }

   public List extendValues(WatchedValues newWV, List newVids) {
      ArrayList newMetrics = new ArrayList();
      Iterator var4 = newVids.iterator();

      while(var4.hasNext()) {
         Integer vid = (Integer)var4.next();
         WatchedValues.Values newValue = newWV.getMetric(vid);
         WatchedValues.Values clonedValue = this.addMetric(newValue.getNamespace(), newValue.getTypeName(), newValue.getInstanceName(), newValue.getAttributeName(), newValue.getSamplePeriod(), newValue.typeIsPattern(), newValue.instanceIsPattern(), newValue.attributeIsPattern(), newValue.isEnabled(), -1);
         newMetrics.add(clonedValue);
      }

      return newMetrics;
   }

   public ArrayList extendValues(WatchedValues newWV) {
      ArrayList newMetrics = new ArrayList();
      Iterator var3 = newWV.getAllMetricValues().iterator();

      while(var3.hasNext()) {
         WatchedValues.Values newValue = (WatchedValues.Values)var3.next();
         WatchedValues.Values clonedValue = this.addMetric(newValue.getNamespace(), newValue.getTypeName(), newValue.getInstanceName(), newValue.getAttributeName(), newValue.getSamplePeriod(), newValue.typeIsPattern(), newValue.instanceIsPattern(), newValue.attributeIsPattern(), newValue.isEnabled(), newValue.getVID());
         newMetrics.add(clonedValue);
      }

      return newMetrics;
   }

   public int setEnabledStatus(Integer[] mids, boolean status) {
      Arrays.sort(mids);
      int count = 0;

      for(int i = 0; i < mids.length; ++i) {
         int mid = mids[i];
         Iterator it = this.valuesAll.iterator();

         while(it.hasNext()) {
            WatchedValues.Values metric = (WatchedValues.Values)it.next();
            int otherMid = metric.getVID();
            if (mid == otherMid) {
               if (metric.setEnabled(status)) {
                  ++count;
               }
               break;
            }
         }
      }

      return count;
   }

   public List instanceRemoved(String type, String instance) {
      ArrayList matchingValues = new ArrayList();
      Iterator var4 = this.valuesAll.iterator();

      while(var4.hasNext()) {
         WatchedValues.Values v = (WatchedValues.Values)var4.next();
         if (v.matches(type, instance)) {
            matchingValues.add(v);
         }
      }

      return matchingValues;
   }

   public int getDefaultSamplePeriod() {
      return this.defaultSamplePeriod;
   }

   public void setDefaultSamplePeriod(int period) {
      this.defaultSamplePeriod = period;
   }

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public boolean isShared() {
      return this.shared;
   }

   public void setShared(boolean shared) {
      this.shared = shared;
   }

   public String getPartitionId() {
      return this.partitionId;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public boolean isPartitionScoped() {
      return this.partitionScoped;
   }

   public class ValuesImpl implements WatchedValues.Values {
      private String typeName;
      private String instName;
      private String attributeName;
      private String namespace;
      private long timestamp;
      private ArrayList values;
      private ArrayList errors;
      private int vid;
      private int hashCode;
      private int samplePeriod;
      private boolean isEnabled;
      private boolean typeIsPattern;
      private boolean instIsPattern;
      private boolean attrIsPattern;
      private ObjectName cachedObjectName;
      private Pattern typePat;
      private Pattern instPat;

      public WatchedValuesImpl getWatchedValues() {
         return WatchedValuesImpl.this;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof WatchedValues.Values)) {
            return false;
         } else {
            WatchedValues.Values other = (WatchedValues.Values)o;
            if ((this.vid >= 0 || other.getVID() >= 0) && this.vid != other.getVID()) {
               return false;
            } else {
               return this.compareAttributes(other) && WatchedValuesImpl.this == ((ValuesImpl)other).getWatchedValues();
            }
         }
      }

      public boolean compareAttributes(WatchedValues.Values candidate) {
         return WatchedValuesImpl.areEqual(this.namespace, candidate.getNamespace()) && WatchedValuesImpl.areEqual(this.attributeName, candidate.getAttributeName()) && WatchedValuesImpl.areEqual(this.instName, candidate.getInstanceName()) && WatchedValuesImpl.areEqual(this.typeName, candidate.getTypeName()) && this.samplePeriod == candidate.getSamplePeriod();
      }

      public int hashCode() {
         return this.hashCode;
      }

      public ValuesImpl(int vid, String typeName, String instName, String attributeName, int samplePeriod, boolean isEnabled, boolean typeIsPattern, boolean instIsPattern, boolean attrIsPattern) {
         this(vid, (String)null, typeName, instName, attributeName, samplePeriod, isEnabled, typeIsPattern, instIsPattern, attrIsPattern);
      }

      protected ValuesImpl(int vid, String namespace, String typeName, String instName, String attributeName, int samplePeriod, boolean isEnabled, boolean typeIsPattern, boolean instIsPattern, boolean attrIsPattern) {
         this.timestamp = -1L;
         this.values = null;
         this.errors = null;
         this.isEnabled = false;
         this.typePat = null;
         this.instPat = null;
         this.namespace = namespace;
         this.typeName = typeName;
         this.instName = instName;
         this.attributeName = attributeName;
         this.vid = vid;
         if (this.vid < 0) {
            this.vid = WatchedValuesImpl.this.computeNextAvailableVID();
         }

         this.samplePeriod = samplePeriod;
         this.typeIsPattern = typeIsPattern;
         this.instIsPattern = instIsPattern;
         this.attrIsPattern = attrIsPattern;
         if (!this.isEnabled) {
            this.isEnabled = isEnabled;
         }

         this.hashCode = this.computeHashcode();
      }

      public long getTimestamp() {
         return this.timestamp;
      }

      public void validate() throws IllegalArgumentException {
         this.validateType();
         this.validateInstance();
      }

      public void validateType() throws IllegalArgumentException {
         try {
            this.getTypePattern();
         } catch (PatternSyntaxException var2) {
            throw new IllegalArgumentException(var2);
         }
      }

      public void validateInstance() throws IllegalArgumentException {
         try {
            this.getInstancePattern();
            if (this.instPat == null) {
               this.getCachedObjectName();
            }

         } catch (PatternSyntaxException var2) {
            throw new IllegalArgumentException(var2);
         }
      }

      private ObjectName getCachedObjectName() {
         if (this.instName != null && this.cachedObjectName == null) {
            try {
               this.cachedObjectName = new ObjectName(this.instName);
            } catch (Exception var2) {
               throw new IllegalArgumentException(var2);
            }
         }

         return this.cachedObjectName;
      }

      private Pattern getInstancePattern() {
         if (this.instIsPattern && this.instPat == null) {
            this.instPat = Pattern.compile(this.instName);
         }

         return this.instPat;
      }

      private Pattern getTypePattern() {
         if (this.typeIsPattern && this.typePat == null) {
            this.typePat = Pattern.compile(this.typeName);
         }

         return this.typePat;
      }

      protected int computeHashcode() {
         return (this.namespace + "//" + this.typeName + "//" + this.instName + "//" + this.attributeName + "//" + this.samplePeriod).hashCode();
      }

      public boolean typeIsPattern() {
         return this.typeIsPattern;
      }

      public boolean instanceIsPattern() {
         return this.instIsPattern || this.isObjectNamePattern();
      }

      private boolean isObjectNamePattern() {
         return this.getCachedObjectName() != null && this.getCachedObjectName().isPattern();
      }

      public boolean attributeIsPattern() {
         return this.attrIsPattern;
      }

      public String toString() {
         String s = "" + this.attributeName + "(" + this.instName + "(" + this.typeName + "))";
         s = s + "[" + this.vid + "]";
         return s;
      }

      public boolean typeNameMatches(String s) {
         boolean ret = true;
         if (s == null) {
            ret = true;
         } else if (this.getTypePattern() != null) {
            Matcher m = this.getTypePattern().matcher(s);
            ret = m.matches();
         } else if (this.typeName != null) {
            ret = s.equals(this.typeName);
         }

         return ret;
      }

      public boolean instNameMatches(String s) {
         boolean ret = false;
         if (!this.passesFilters(s)) {
            return false;
         } else {
            if (s != null && this.instName != null) {
               if (this.getInstancePattern() != null) {
                  Matcher m = this.getInstancePattern().matcher(s);
                  ret = m.matches();
               } else if (this.isObjectNamePattern()) {
                  ret = this.applyObjectNamePattern(s);
               } else {
                  ret = s.equals(this.instName);
               }
            } else {
               ret = true;
            }

            return ret;
         }
      }

      private boolean passesFilters(String s) {
         boolean filtersPassed = true;
         if (WatchedValuesImpl.this.isPartitionScoped() && WatchedValuesImpl.this.instanceFilters != null && !WatchedValuesImpl.this.instanceFilters.isEmpty()) {
            Iterator var3 = WatchedValuesImpl.this.instanceFilters.iterator();

            while(var3.hasNext()) {
               String[] instanceFilter = (String[])var3.next();
               String filterKey = instanceFilter[0];
               String filter = instanceFilter[1];
               if (s.contains(filterKey)) {
                  filtersPassed = s.contains(filter);
               }
            }
         }

         return filtersPassed;
      }

      private boolean applyObjectNamePattern(String s) {
         boolean ret = false;

         try {
            ObjectName cachedON = this.getCachedObjectName();
            if (cachedON != null) {
               ret = this.getCachedObjectName().apply(new ObjectName(s));
            }

            return ret;
         } catch (Exception var4) {
            throw new RuntimeException(var4);
         }
      }

      public boolean matchesNamespace(String namespace) {
         boolean nsMatches = true;
         if (this.getNamespace() != null && namespace != null) {
            nsMatches = this.namespace.equals(namespace);
         }

         return nsMatches;
      }

      public boolean matches(String typeName, String instName) {
         return this.matches((String)null, typeName, instName);
      }

      public boolean matches(String namespace, String typeName, String instName) {
         boolean instMatches = this.instNameMatches(instName);
         boolean typeMatches = this.typeNameMatches(typeName);
         boolean doesMatch = instMatches && typeMatches;
         if (doesMatch && this.namespace != null) {
            boolean nsMatch = this.matchesNamespace(namespace);
            doesMatch = doesMatch && nsMatch;
         }

         return doesMatch;
      }

      public final String getInstanceName() {
         return this.instName;
      }

      public final String getAttributeName() {
         return this.attributeName;
      }

      public final String getTypeName() {
         return this.typeName;
      }

      public final int getVID() {
         return this.vid;
      }

      public final boolean isCurrent() {
         return this.timestamp >= WatchedValuesImpl.this.mostRecentValuesTimestamp;
      }

      public WatchedValues.Values.ValuesData getValues() {
         List rawValuesData = this.values != null ? this.values : Collections.EMPTY_LIST;
         List errorsData = this.errors != null ? this.errors : Collections.EMPTY_LIST;
         return new ValuesDataImpl((List)rawValuesData, (List)errorsData);
      }

      private void init() {
         if (this.values == null || this.values.size() > 0) {
            this.values = new ArrayList();
         }

         if (this.errors == null || this.errors.size() > 0) {
            this.errors = new ArrayList();
         }

         this.timestamp = WatchedValuesImpl.this.mostRecentValuesTimestamp;
      }

      public void resetRawValues() {
         synchronized(WatchedValuesImpl.this) {
            this.init();
         }
      }

      public void addValue(String typeName, String instanceName, String attributeName, Object value) {
         if (this.timestamp != WatchedValuesImpl.this.mostRecentValuesTimestamp) {
            this.init();
            WatchedValuesImpl.this.mostRecentValuesCount++;
         }

         this.values.add(new RawValueDataImpl(typeName, instanceName, attributeName, value));
         if (WatchedValuesImpl.this.previousSamplePeriod < 0L || (long)this.samplePeriod != WatchedValuesImpl.this.previousSamplePeriod) {
            WatchedValuesImpl.this.addSamplePeriod((long)this.samplePeriod);
         }

      }

      public void addError(String typeName, String instName, String reason, boolean isAttributeDeactivated) {
         if (this.timestamp != WatchedValuesImpl.this.mostRecentValuesTimestamp) {
            this.init();
            WatchedValuesImpl.this.mostRecentAttrsWithErrorsCount++;
         }

         this.errors.add(new ErrorDataImpl(typeName, instName, reason, isAttributeDeactivated));
         if (WatchedValuesImpl.this.previousSamplePeriod < 0L || (long)this.samplePeriod != WatchedValuesImpl.this.previousSamplePeriod) {
            WatchedValuesImpl.this.addSamplePeriod((long)this.samplePeriod);
         }

      }

      protected final long getValuesTimestamp() {
         return this.timestamp;
      }

      public String dump(String indent, boolean internal, boolean shortForm) {
         StringBuffer s = new StringBuffer(512);
         if (shortForm && !internal) {
            s.append("  Value: ^").append(this.getAttributeName()).append("^");
            s.append(this.getTypeName()).append("^").append(this.getInstanceName()).append("^[");
            boolean first = true;

            Iterator var10;
            for(var10 = this.getValues().getRawValues().iterator(); var10.hasNext(); first = false) {
               WatchedValues.Values.RawValueData value = (WatchedValues.Values.RawValueData)var10.next();
               s.append(first ? "" : ", ").append(value);
            }

            s.append("]^");

            for(var10 = this.getValues().getErrors().iterator(); var10.hasNext(); first = false) {
               WatchedValues.Values.ErrorData error = (WatchedValues.Values.ErrorData)var10.next();
               s.append(first ? "" : ", ").append(error);
            }

            s.append("]^").append(this.getTypeName());
         } else {
            String typeName = this.getTypeName();
            if (typeName != null) {
               s.append(indent).append("METRIC VALUE: ").append(this.getAttributeName()).append(" (").append(typeName).append(")\n");
            } else {
               s.append(indent).append("METRIC VALUE: ").append(this.getAttributeName()).append("\n");
            }

            s.append(indent).append("  MBean: ").append(this.getInstanceName()).append("\n");
            if (internal) {
               s.append(indent).append("  VID:     ").append(this.vid + "\n");
               s.append(indent).append("  SamplePeriod: ").append(this.getSamplePeriod()).append("\n");
               s.append(indent).append("  Enabled  :    ").append(this.isEnabled()).append("\n");
               s.append(indent).append("  Timestamp:    ").append(DateUtils.nanoDateToString(this.getValuesTimestamp(), true)).append("\n");
            }

            WatchedValues.Values.ValuesData vd = this.getValues();
            s.append(indent).append("  Values:").append("\n");
            Iterator var7 = vd.getRawValues().iterator();

            while(var7.hasNext()) {
               WatchedValues.Values.RawValueData valuex = (WatchedValues.Values.RawValueData)var7.next();
               s.append(indent).append("    ").append(valuex).append("\n");
            }

            s.append(indent).append("  Errors:").append("\n");
            var7 = vd.getErrors().iterator();

            while(var7.hasNext()) {
               WatchedValues.Values.ErrorData errorx = (WatchedValues.Values.ErrorData)var7.next();
               s.append(indent).append("    ").append(errorx).append("\n");
            }
         }

         return s.toString();
      }

      public int getSamplePeriod() {
         return this.samplePeriod;
      }

      public boolean isEnabled() {
         return this.isEnabled;
      }

      public boolean setEnabled(boolean isEnabled) {
         boolean oldVal = this.isEnabled;
         this.isEnabled = isEnabled;
         return oldVal == isEnabled;
      }

      public void addValue(String typeName, String instName, Object value) {
         this.addValue(typeName, instName, this.attributeName, value);
      }

      public String getNamespace() {
         return this.namespace;
      }

      public void setNamespace(String namespace) {
         this.namespace = namespace;
      }

      class ValuesDataImpl implements WatchedValues.Values.ValuesData {
         List rawValues;
         List errors;

         ValuesDataImpl(List rawValues, List errors) {
            this.rawValues = rawValues;
            this.errors = errors;
         }

         public List getRawValues() {
            return this.rawValues;
         }

         public List getErrors() {
            return this.errors;
         }
      }
   }

   static final class CycleBasedValuesIterator implements Iterator {
      Iterator it;
      ValuesImpl val = null;
      long baseTimestamp;

      CycleBasedValuesIterator(Iterator it, long baseTimestamp) {
         this.it = it;
         this.baseTimestamp = baseTimestamp;
      }

      public boolean hasNext() {
         if (this.val != null) {
            return true;
         } else {
            do {
               if (!this.it.hasNext()) {
                  this.val = null;
                  return false;
               }

               this.val = (ValuesImpl)this.it.next();
            } while(this.val.timestamp != this.baseTimestamp);

            return true;
         }
      }

      public Object next() {
         if (this.val == null) {
            this.hasNext();
         }

         Object retVal = this.val;
         this.val = null;
         return retVal;
      }

      public void remove() {
         this.it.remove();
      }
   }
}
