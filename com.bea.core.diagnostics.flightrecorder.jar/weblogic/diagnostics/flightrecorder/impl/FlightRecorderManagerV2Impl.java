package weblogic.diagnostics.flightrecorder.impl;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import jdk.jfr.Configuration;
import jdk.jfr.Event;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Name;
import jdk.jfr.Recording;
import weblogic.diagnostics.flightrecorder.event.DebugEventContributor;
import weblogic.diagnostics.flightrecorder.event.StackTraced;
import weblogic.diagnostics.flightrecorder2.event.DebugEvent;

public class FlightRecorderManagerV2Impl extends FlightRecorderManagerBaseImpl {
   private FlightRecorder debugFlightRecorder = null;
   private FlightRecorder flightRecorder = null;
   private Recording debugFlightRecording = null;
   private Recording imageFlightRecording = null;
   private Map jvmDefaultEnabledEvents = null;
   private Map jvmDefaultDisabledEvents = null;
   private String imageFlightRecordingName = null;
   private Set stackTraced = new TreeSet();
   private Set alwaysStackTraced = new TreeSet();
   private long imageCopyCount = 0L;

   public synchronized void enableJVMEventsInImageRecording() {
      if (this.flightRecorder != null && this.imageFlightRecording != null && this.jvmDefaultEnabledEvents != null) {
         try {
            this.imageFlightRecording.setSettings(this.jvmDefaultEnabledEvents);
            if (this.debugLog.isDebugEnabled()) {
               this.debugLog.debug("Enabled JVM events in recording");
            }
         } catch (Throwable var2) {
            if (this.debugLog.isDebugEnabled()) {
               this.debugLog.debug("Failed to enable JVM events in recording", var2);
            }
         }

      }
   }

   /** @deprecated */
   @Deprecated
   public synchronized void disableJVMEventsInImageRecording() {
      if (this.flightRecorder != null && this.imageFlightRecording != null && this.jvmDefaultDisabledEvents != null) {
         try {
            this.imageFlightRecording.setSettings(this.jvmDefaultDisabledEvents);
            if (this.debugLog.isDebugEnabled()) {
               this.debugLog.debug("Disabled JVM events in recording");
            }
         } catch (Throwable var2) {
            if (this.debugLog.isDebugEnabled()) {
               this.debugLog.debug("Failed to disable JVM events in recording", var2);
            }
         }

      }
   }

   public synchronized void setDefaultSettings(int multiple, boolean enableStackTraces) {
      this.defaultMaxSizeMultiple = multiple;
      this.defaultEnableStackTraces = enableStackTraces;
   }

   public synchronized void setDebugEventContributor(DebugEventContributor theContributor) {
      if (this.contributor == null && theContributor != null) {
         this.contributor = theContributor;
      }

   }

   public void startImageFlightRecording(String recordingName, File destinationFile, boolean jvmEventsDisabled) throws Exception {
      if (this.getFlightRecorder() == null) {
         if (this.debugLog.isDebugEnabled()) {
            this.debugLog.debug("FlightRecorderSource.initialize() error: flight recorder not enabled");
         }

      } else {
         this.imageFlightRecording = new Recording();
         this.imageFlightRecording.setName(recordingName);
         this.imageFlightRecordingName = recordingName;
         this.jvmDefaultEnabledEvents = this.getDefaultEventSettings();
         this.jvmDefaultDisabledEvents = new HashMap();
         this.copyEventSettingsFromDefault(this.jvmDefaultDisabledEvents, "ActiveRecording");
         this.copyEventSettingsFromDefault(this.jvmDefaultDisabledEvents, "DumpReason");
         this.copyEventSettingsFromDefault(this.jvmDefaultDisabledEvents, "ActiveSetting");
         this.copyEventSettingsFromDefault(this.jvmDefaultDisabledEvents, "DataLoss");
         this.copyEventSettingsFromDefault(this.jvmDefaultDisabledEvents, "CPUInformation");
         this.copyEventSettingsFromDefault(this.jvmDefaultDisabledEvents, "VMInfo");
         this.copyEventSettingsFromDefault(this.jvmDefaultDisabledEvents, "ThreadEnd");
         this.copyEventSettingsFromDefault(this.jvmDefaultDisabledEvents, "ThreadStart");
         if (jvmEventsDisabled) {
            this.disableJVMEventsInImageRecording();
         } else {
            this.enableJVMEventsInImageRecording();
         }

         this.enableImageRecordingClientEvents("WLDF", this.defaultMaxSizeMultiple, this.defaultEnableStackTraces);
         this.imageFlightRecording.start();
         this.imageFlightRecording.setDestination(destinationFile.toPath());
      }
   }

   public synchronized boolean isImageRecordingActive() {
      return FlightRecorder.isInitialized();
   }

   public synchronized boolean copyImageRecordingToFile(File tempFile) throws Exception {
      Recording tempClient = null;

      boolean var3;
      try {
         tempClient = this.imageFlightRecording.copy(true);
         tempClient.setName(this.imageFlightRecordingName + "_" + this.imageCopyCount++);
         tempClient.dump(tempFile.toPath());
         var3 = true;
      } finally {
         if (tempClient != null) {
            tempClient.close();
         }

      }

      return var3;
   }

   public synchronized void enableImageRecordingClientEvents(String producerPrefix, int maxSizeMultiple, boolean enableEventStackTraces) {
      if (this.isImageRecordingActive() && this.imageFlightRecording != null) {
         try {
            long maxChunkSize = 12582912L;
            long maxRecordingSize = maxChunkSize * (long)maxSizeMultiple;
            if (maxRecordingSize < 5000000L) {
               maxRecordingSize = 5000000L;
            }

            this.imageFlightRecording.setMaxSize(maxRecordingSize);
            if (this.debugLog.isDebugEnabled()) {
               this.debugLog.debug("Limiting FlightRecording size to " + maxRecordingSize + " bytes");
            }

            if (this.debugLog.isDebugEnabled()) {
               this.debugRecorderDetails();
            }

            if (this.debugLog.isDebugEnabled()) {
               this.debugLog.debug("enabling events for producer: " + producerPrefix);
            }

            String producerName = producerPrefix.trim().equals("WLDF") ? "weblogic." : null;
            boolean stackTrace = false;
            if (producerName != null) {
               Iterator var10 = this.getWLDFEvents().iterator();

               while(true) {
                  Class clz;
                  String name;
                  do {
                     do {
                        if (!var10.hasNext()) {
                           return;
                        }

                        clz = (Class)var10.next();
                        name = this.getEventName(clz);
                     } while(name == null);
                  } while(!name.contains(producerName));

                  stackTrace = false;
                  if (enableEventStackTraces) {
                     if (this.internalStackTracesEnabled) {
                        stackTrace = true;
                     } else if (this.stackTraced.contains(clz.getName()) || this.alwaysStackTraced.contains(clz.getName())) {
                        stackTrace = true;
                     }
                  } else if (this.alwaysStackTraced.contains(clz.getName())) {
                     stackTrace = true;
                  }

                  this.createEventSettings(this.jvmDefaultDisabledEvents, name, true, stackTrace, this.threshold == 0L ? null : this.threshold + " ms", (String)null);
                  if (this.debugLog.isDebugEnabled()) {
                     this.debugLog.debug("enabled event: " + name);
                  }
               }
            }
         } catch (Throwable var13) {
            if (this.debugLog.isDebugEnabled()) {
               this.debugLog.debug("enableImageRecordingClientEvents failed", var13);
            }
         }

      }
   }

   public boolean isRecordingPossible() {
      try {
         if (!this.jfrChecksCached) {
            try {
               if (this.disableWLDFJFRUsage) {
                  this.isNativeJFR = false;
                  this.isJFRActive = false;
               } else {
                  this.isNativeJFR = true;

                  try {
                     if (this.flightRecorder == null) {
                        this.flightRecorder = FlightRecorder.getFlightRecorder();
                     }

                     this.isJFRActive = FlightRecorder.isInitialized();
                  } catch (IllegalStateException var2) {
                     this.isJFRActive = false;
                  }
               }

               this.jfrChecksCached = true;
            } catch (SecurityException var3) {
               if (this.debugLog.isDebugEnabled()) {
                  this.debugLog.debug("isRecordingPossible() SecurityException during detect, return false", var3);
               }

               this.isNativeJFR = false;
               this.isJFRActive = false;
               this.jfrChecksCached = true;
               return false;
            }
         }

         if (this.isNativeJFR) {
            if (this.debugLog.isDebugEnabled()) {
               this.debugLog.debug("isRecordingPossible() JFR Native implementation: " + this.isJFRActive);
            }

            return this.isJFRActive;
         }

         if (this.pureJavaAllowed) {
            if (this.debugLog.isDebugEnabled()) {
               this.debugLog.debug("isRecordingPossible() JFR Pure Java implementation: " + this.isJFRActive);
            }

            return this.isJFRActive;
         }

         if (this.debugLog.isDebugEnabled()) {
            this.debugLog.debug("isRecordingPossible() returns false (purejava not enabled for WLDF). purejava isActive = " + this.isJFRActive);
         }
      } catch (NoClassDefFoundError var4) {
         if (this.debugLog.isDebugEnabled()) {
            this.debugLog.debug("isRecordingPossible() NoClassDefFoundError during detect, return false");
         }
      } catch (SecurityException var5) {
         if (this.debugLog.isDebugEnabled()) {
            this.debugLog.debug("isRecordingPossible() SecurityException during detect, return false", var5);
         }
      }

      return false;
   }

   public final boolean isECIDEnabled() {
      return this.ECIDEnabled;
   }

   public final boolean areJVMEventsExpensive() {
      return this.disableJVMEventExpenseCheck ? false : this.JVMEventsExpensive;
   }

   public String getInstrumentedEventPackagePrefix() {
      return "weblogic.diagnostics.flightrecorder2.event.";
   }

   public void addEvent(Object tokenObj, Class eventClass) {
      if (tokenObj instanceof Event && eventClass != null) {
         Event event = (Event)tokenObj;
         if (StackTraced.class.isAssignableFrom(eventClass)) {
            this.stackTraced.add(eventClass.getName());
            if (DebugEvent.class.isAssignableFrom(eventClass)) {
               this.alwaysStackTraced.add(eventClass.getName());
            }
         }

      }
   }

   public synchronized boolean canGenerateDebug() {
      if (!this.isRecordingPossible()) {
         return false;
      } else if (!this.debugRecordingStarted && !this.debugRecordingStartupFailed) {
         try {
            this.debugFlightRecorder = FlightRecorder.getFlightRecorder();
            this.debugFlightRecording = new Recording();
            this.debugFlightRecording.setName("__WLDF_debug_events");
            Map debugSettings = new HashMap();
            this.createEventSettings(debugSettings, this.getEventName(DebugEvent.class), true, false, (String)null, (String)null);
            this.debugFlightRecording.setSettings(debugSettings);
            this.debugFlightRecording.start();
            this.debugRecordingStarted = true;
         } catch (Throwable var2) {
            this.debugRecordingStartupFailed = true;
            if (this.debugLog.isDebugEnabled()) {
               this.debugLog.debug("debugRecordingStartupFailed", var2);
            }
         }

         return this.debugRecordingStarted;
      } else {
         return this.debugRecordingStarted;
      }
   }

   public Object beginDebugTimedEvent(String component, String message, Object contributorOverride) {
      if (!this.canGenerateDebug()) {
         return null;
      } else {
         try {
            DebugEvent event = new DebugEvent();
            event.setTimed(true);
            event.setMessage(message);
            if (component != null) {
               event.setComponent(component);
            }

            if (contributorOverride != null || this.contributor != null) {
               try {
                  if (contributorOverride == null) {
                     this.contributor.contributeBefore(event);
                  } else {
                     DebugEventContributor tempContributor = (DebugEventContributor)contributorOverride;
                     tempContributor.contributeBefore(event);
                  }
               } catch (Throwable var6) {
               }
            }

            event.begin();
            return event;
         } catch (Throwable var7) {
            var7.printStackTrace();
            return null;
         }
      }
   }

   public void generateDebugEvent(String component, String message, Throwable debugTh, Object contributorOverride) {
      if (this.canGenerateDebug()) {
         try {
            DebugEvent event = new DebugEvent();
            event.setMessage(message);
            if (component != null) {
               event.setComponent(component);
            }

            if (debugTh != null) {
               event.setThrowableMessage(debugTh.getMessage());
            }

            if (contributorOverride != null || this.contributor != null) {
               try {
                  if (contributorOverride == null) {
                     this.contributor.contribute(event);
                  } else {
                     DebugEventContributor tempContributor = (DebugEventContributor)contributorOverride;
                     tempContributor.contribute(event);
                  }
               } catch (Throwable var7) {
               }
            }

            event.commit();
         } catch (Throwable var8) {
            var8.printStackTrace();
         }
      }
   }

   public void commitDebugTimedEvent(Object event, Throwable debugTh, Object contributorOverride) {
      if (event != null) {
         try {
            DebugEvent timedEvent = (DebugEvent)event;
            timedEvent.end();
            if (debugTh != null) {
               timedEvent.setThrowableMessage(debugTh.getMessage());
            }

            if (contributorOverride != null || this.contributor != null) {
               try {
                  if (contributorOverride == null) {
                     this.contributor.contributeAfter(timedEvent);
                  } else {
                     DebugEventContributor tempContributor = (DebugEventContributor)contributorOverride;
                     tempContributor.contributeAfter(timedEvent);
                  }
               } catch (Throwable var6) {
               }
            }

            timedEvent.commit();
         } catch (Throwable var7) {
            var7.printStackTrace();
         }

      }
   }

   public static boolean detectV2() {
      try {
         return FlightRecorder.isAvailable();
      } catch (Exception var1) {
         return false;
      }
   }

   public boolean isJFR2() {
      return true;
   }

   private synchronized FlightRecorder getFlightRecorder() {
      if (!this.isRecordingPossible()) {
         return null;
      } else if (this.clientCheckPerformed) {
         return this.flightRecorder;
      } else {
         try {
            if (this.flightRecorder == null) {
               this.flightRecorder = FlightRecorder.getFlightRecorder();
            }
         } catch (Exception var2) {
         }

         this.clientCheckPerformed = true;
         return this.flightRecorder;
      }
   }

   private Map createEventSettings(Map settings, String evntName, boolean enabled, boolean stackTrace, String threshold, String period) {
      String key = evntName + "#enabled";
      String value = Boolean.valueOf(enabled).toString();
      settings.put(key, value);
      if (stackTrace) {
         key = evntName + "#stackTrace";
         value = Boolean.valueOf(stackTrace).toString();
         settings.put(key, value);
      }

      if (threshold != null) {
         key = evntName + "#threshold";
         settings.put(key, threshold);
      }

      if (period != null) {
         key = evntName + "#period";
         settings.put(key, period);
      }

      return settings;
   }

   private Map copyEventSettingsFromDefault(Map settings, String eventName) {
      Configuration cfg = null;

      try {
         cfg = Configuration.getConfiguration("default");
         Iterator var4 = cfg.getSettings().entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry entry = (Map.Entry)var4.next();
            if (!((String)entry.getKey()).endsWith("stackTrace") && ((String)entry.getKey()).contains(eventName)) {
               settings.put(entry.getKey(), entry.getValue());
            }
         }
      } catch (Exception var6) {
         var6.printStackTrace();
         if (this.debugLog.isDebugEnabled()) {
            this.debugLog.debug("Problem setting the default JVM event settings: " + var6.getMessage());
         }
      }

      return settings;
   }

   private Map getDefaultEventSettings() {
      Configuration cfg = null;

      try {
         cfg = Configuration.getConfiguration("default");
         return cfg.getSettings();
      } catch (Exception var3) {
         var3.printStackTrace();
         if (this.debugLog.isDebugEnabled()) {
            this.debugLog.debug("Problem setting the default JVM event settings: " + var3.getMessage());
         }

         return new HashMap();
      }
   }

   private List getWLDFEvents() {
      return Collections.emptyList();
   }

   private String getEventName(Class clazz) {
      Name name = (Name)clazz.getAnnotation(Name.class);
      return name == null ? null : name.value();
   }
}
