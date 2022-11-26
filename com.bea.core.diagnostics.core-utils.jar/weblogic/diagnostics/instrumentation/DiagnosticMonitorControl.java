package weblogic.diagnostics.instrumentation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import weblogic.diagnostics.context.CorrelationManager;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager.Factory;
import weblogic.diagnostics.instrumentation.rtsupport.InstrumentationSupportImpl;

public abstract class DiagnosticMonitorControl implements DiagnosticMonitor, Serializable, Comparable {
   private static final FlightRecorderManager flightRecorderMgr = Factory.getInstance();
   private InstrumentationScope instrumentationScope;
   private String name;
   private String description;
   private String type;
   private boolean allowServerScope;
   private boolean allowComponentScope;
   public boolean enabled;
   private boolean enableDyeFiltering;
   private long[] dye_masks;
   private String[] attributeNames;
   private Properties properties;
   private String[] includes;
   private String[] excludes;
   public boolean argumentsCaptureNeeded;
   private boolean serverManaged;
   private String diagnosticVolume;
   private String eventClassName;
   private Class eventClass;

   public DiagnosticMonitorControl(DiagnosticMonitorControl mon) {
      this(mon.getType());
      this.allowServerScope = mon.allowServerScope;
      this.allowComponentScope = mon.allowComponentScope;
      this.attributeNames = mon.attributeNames;
      this.serverManaged = mon.serverManaged;
      this.diagnosticVolume = mon.diagnosticVolume;
      this.eventClassName = mon.eventClassName;
      if (flightRecorderMgr.isRecordingPossible()) {
         this.resolveEventClass();
      }

   }

   public DiagnosticMonitorControl(String type) {
      this("", type);
   }

   public DiagnosticMonitorControl(String name, String type) {
      this.dye_masks = new long[]{0L};
      this.properties = new Properties();
      this.argumentsCaptureNeeded = false;
      this.serverManaged = false;
      this.diagnosticVolume = null;
      this.eventClassName = null;
      this.eventClass = null;
      this.name = name;
      this.type = type;
   }

   void setInstrumentationScope(InstrumentationScope instrumentationScope) {
      this.instrumentationScope = instrumentationScope;
   }

   public InstrumentationScope getInstrumentationScope() {
      return this.instrumentationScope;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getType() {
      return this.type;
   }

   protected Properties getProperties() {
      return this.properties;
   }

   protected void setProperties(Properties p) {
      this.properties = p;
   }

   public boolean isServerScopeAllowed() {
      return this.allowServerScope;
   }

   void setServerScopeAllowed(boolean allowed) {
      this.allowServerScope = allowed;
   }

   public boolean isComponentScopeAllowed() {
      return this.allowComponentScope;
   }

   void setComponentScopeAllowed(boolean allowed) {
      this.allowComponentScope = allowed;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean enable) {
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("DiagnosticMonitorControl.setEnabled " + this.getType() + " to " + enable);
      }

      this.enabled = enable;
   }

   public boolean isEnabledAndNotDyeFiltered() {
      if (!this.enabled) {
         return false;
      } else if (!this.enableDyeFiltering) {
         return !this.serverManaged || CorrelationManager.isJFRThrottled();
      } else {
         return InstrumentationSupportImpl.dyeMatches(this);
      }
   }

   public boolean isArgumentsCaptureNeeded() {
      return this.argumentsCaptureNeeded;
   }

   public boolean isServerManaged() {
      return this.serverManaged;
   }

   public void setServerManaged(boolean serverManaged) {
      this.serverManaged = serverManaged;
   }

   public String getEventClassName() {
      return this.eventClassName;
   }

   public void setEventClassName(String eventClassName) {
      this.eventClassName = eventClassName;
   }

   public Class getEventClass() {
      return this.eventClass;
   }

   private void resolveEventClass() {
      if (this.eventClassName != null && this.eventClassName.length() != 0) {
         try {
            this.eventClass = Class.forName(this.eventClassName);
            if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_CONFIG.debug("DiagnosticMonitorControl.resolveEventClass " + this.eventClassName + " resolved and is not pooled");
            }
         } catch (Exception var2) {
            if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_CONFIG.debug("DiagnosticMonitorControl.resolveEventClass exception resolving " + this.eventClassName, var2);
            }
         }

      }
   }

   public String getDiagnosticVolume() {
      return this.diagnosticVolume;
   }

   public void setDiagnosticVolume(String diagnosticVolume) {
      this.diagnosticVolume = diagnosticVolume;
   }

   public String[] getAttributeNames() {
      return this.attributeNames;
   }

   public void setAttributeNames(String[] names) {
      this.attributeNames = names;
   }

   public String getAttribute(String attributeName) {
      String retVal = null;
      if (this.isValidAttributeName(attributeName)) {
         retVal = this.properties.getProperty(attributeName);
      }

      return retVal;
   }

   public void setAttribute(String attributeName, String attributeValue) {
      if (this.isValidAttributeName(attributeName)) {
         if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_CONFIG.debug("DiagnosticMonitorControl.setAttribute " + this.getType() + " " + attributeName + "=" + attributeValue);
         }

         if (attributeValue == null) {
            this.properties.remove(attributeName);
         } else {
            this.properties.setProperty(attributeName, attributeValue);
         }
      }

   }

   private boolean isValidAttributeName(String attributeName) {
      int len = this.attributeNames != null ? this.attributeNames.length : 0;

      for(int i = 0; i < len; ++i) {
         if (attributeName.equals(this.attributeNames[i])) {
            return true;
         }
      }

      return false;
   }

   public boolean isDyeFilteringEnabled() {
      return this.enableDyeFiltering;
   }

   public void setDyeFilteringEnabled(boolean enable) {
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("DiagnosticMonitorControl.setDyeFilteringEnabled " + this.getType() + " to " + enable);
      }

      this.enableDyeFiltering = enable;
   }

   public long getDyeMask() {
      return this.dye_masks[0];
   }

   public long[] getDyeMasks() {
      return this.dye_masks;
   }

   public void setDyeMask(long dye_mask) {
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("DiagnosticMonitorControl.setDyeMask " + this.getType() + " to " + dye_mask);
      }

      this.dye_masks[0] = dye_mask;
   }

   public void setDyeMasks(long[] masks) {
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("DiagnosticMonitorControl.setDyeMask " + this.getType() + " to " + Arrays.toString(masks));
      }

      this.dye_masks = masks;
   }

   public String[] getIncludes() {
      return this.includes;
   }

   public void setIncludes(String[] includes) {
      this.includes = includes;
   }

   public String[] getExcludes() {
      return this.excludes;
   }

   public void setExcludes(String[] excludes) {
      this.excludes = excludes;
   }

   protected synchronized boolean merge(DiagnosticMonitorControl other) {
      boolean retVal = false;
      if (other != null && this.type.equals(other.type)) {
         this.name = other.name;
         this.description = other.description;
         this.allowServerScope = other.allowServerScope;
         this.allowComponentScope = other.allowComponentScope;
         this.enabled = other.enabled;
         this.enableDyeFiltering = other.enableDyeFiltering;
         this.dye_masks = other.dye_masks;
         this.attributeNames = other.attributeNames;
         this.properties = other.properties;
         this.includes = other.includes;
         this.excludes = other.excludes;
         this.argumentsCaptureNeeded = other.argumentsCaptureNeeded;
         this.serverManaged = other.serverManaged;
         this.diagnosticVolume = other.diagnosticVolume;
         retVal = true;
      }

      return retVal;
   }

   public int compareTo(DiagnosticMonitorControl obj) {
      return this.type.compareTo(obj.type);
   }

   synchronized void unionOf(DiagnosticMonitorControl other) {
      if (other != null && this.type.equals(other.type) && this.name.equals(other.name)) {
         if (this.description == null) {
            this.description = other.description;
         }

         this.enabled = this.enabled || other.enabled;
         this.enableDyeFiltering = this.enableDyeFiltering || other.enableDyeFiltering;
         this.dye_masks = mergeDyeMasks(this.dye_masks, other.dye_masks);
         if (this.attributeNames == null) {
            this.attributeNames = other.attributeNames;
         }

         if (this.properties == null) {
            this.properties = other.properties;
         } else {
            this.mergeProperties(other.properties);
         }

         this.includes = InstrumentationUtils.unionOf(this.includes, other.includes);
         this.excludes = InstrumentationUtils.unionOf(this.excludes, other.excludes);
      }

   }

   private static long[] mergeDyeMasks(long[] left, long[] right) {
      HashSet resultSet = new HashSet();
      long[] array = left;
      int var4 = left.length;

      int i;
      long mask;
      for(i = 0; i < var4; ++i) {
         mask = array[i];
         if (mask != 0L) {
            resultSet.add(mask);
         }
      }

      array = right;
      var4 = right.length;

      for(i = 0; i < var4; ++i) {
         mask = array[i];
         if (mask != 0L) {
            resultSet.add(mask);
         }
      }

      if (resultSet.size() <= 0) {
         return new long[]{0L};
      } else {
         array = new long[resultSet.size()];
         Iterator longIt = resultSet.iterator();

         for(i = 0; i < resultSet.size(); ++i) {
            array[i] = (Long)longIt.next();
         }

         return array;
      }
   }

   protected abstract void mergeProperties(Properties var1);
}
