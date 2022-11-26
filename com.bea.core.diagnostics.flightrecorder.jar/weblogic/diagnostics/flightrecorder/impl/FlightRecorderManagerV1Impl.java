package weblogic.diagnostics.flightrecorder.impl;

import com.oracle.jrockit.jfr.EventToken;
import com.oracle.jrockit.jfr.FlightRecorder;
import com.oracle.jrockit.jfr.Producer;
import com.oracle.jrockit.jfr.client.EventSettingsBuilder;
import com.oracle.jrockit.jfr.client.FlightRecorderClient;
import com.oracle.jrockit.jfr.client.FlightRecordingClient;
import java.io.File;
import java.io.IOException;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import javax.management.InstanceNotFoundException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import weblogic.diagnostics.flightrecorder.event.DebugEvent;
import weblogic.diagnostics.flightrecorder.event.DebugEventContributor;
import weblogic.diagnostics.flightrecorder.event.DebugTimedEvent;
import weblogic.diagnostics.flightrecorder.event.StackTraced;

public class FlightRecorderManagerV1Impl extends FlightRecorderManagerBaseImpl {
   private List enabledJVMEvents = null;
   private List disabledJVMEvents = null;
   private FlightRecorderClient flightRecorderClient = null;
   private FlightRecordingClient imageFlightRecordingClient = null;
   private BitSet stackTraced = new BitSet();
   private BitSet alwaysStackTraced = new BitSet();
   private FlightRecorderClient debugFlightRecorderClient = null;
   private FlightRecordingClient debugFlightRecordingClient = null;
   private Producer debugProducer = null;
   private long imageCopyCount = 0L;
   private String imageFlightRecordingName = null;

   public synchronized void enableJVMEventsInImageRecording() {
      if (this.flightRecorderClient != null && this.imageFlightRecordingClient != null && this.enabledJVMEvents != null) {
         try {
            this.flightRecorderClient.updateEventSettings(this.imageFlightRecordingClient.getObjectName(), this.enabledJVMEvents);
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

   public synchronized void disableJVMEventsInImageRecording() {
      if (this.flightRecorderClient != null && this.imageFlightRecordingClient != null && this.disabledJVMEvents != null) {
         try {
            this.flightRecorderClient.updateEventSettings(this.imageFlightRecordingClient.getObjectName(), this.disabledJVMEvents);
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
      if (this.getFlightRecorderClient() == null) {
         if (this.debugLog.isDebugEnabled()) {
            this.debugLog.debug("FlightRecorderSource.initialize() no FlightRecorderClient found");
         }

      } else {
         this.imageFlightRecordingClient = this.flightRecorderClient.createRecordingObject(recordingName);
         this.imageFlightRecordingName = recordingName;
         List jvmevents = null;
         boolean newStyle = false;

         EventSettingsBuilder builder;
         try {
            builder = new EventSettingsBuilder();
            builder.addSettings("default");
            jvmevents = builder.createSettings(this.flightRecorderClient);
            newStyle = true;
         } catch (Exception var7) {
            if (this.debugLog.isDebugEnabled()) {
               this.debugLog.debug("Problem setting the default JVM event settings using new style, most likely older JVM: " + var7.getMessage());
            }
         }

         if (jvmevents == null) {
            if (this.debugLog.isDebugEnabled()) {
               this.debugLog.debug("Setting default JVM events with old style");
            }

            jvmevents = this.flightRecorderClient.getEventSettings(this.imageFlightRecordingClient.getObjectName());
         }

         this.enabledJVMEvents = jvmevents;
         builder = new EventSettingsBuilder();
         builder.createSetting("http://www.oracle.com/*/jvm/*", false, false, Long.MAX_VALUE, 0L);
         builder.createSetting("http://www.oracle.com/*/jfr-info/*", true, false, Long.MAX_VALUE, 0L);
         builder.createSetting("http://www.oracle.com/*/jvm/vm/cpu_info", true, false, Long.MAX_VALUE, 0L);
         builder.createSetting("http://www.oracle.com/*/jvm/vm/info", true, false, Long.MAX_VALUE, 0L);
         builder.createSetting("http://www.oracle.com/*/jvm/vm/system_properties", true, false, Long.MAX_VALUE, 0L);
         builder.createSetting("http://www.oracle.com/*/jvm/os/environment", true, false, Long.MAX_VALUE, 0L);
         builder.createSetting("http://www.oracle.com/*/jvm/java/thread_end", true, false, Long.MAX_VALUE, 0L);
         builder.createSetting("http://www.oracle.com/*/jvm/java/thread_start", true, false, Long.MAX_VALUE, 0L);
         this.disabledJVMEvents = builder.createSettings(this.flightRecorderClient);
         if (jvmEventsDisabled) {
            this.disableJVMEventsInImageRecording();
         } else if (newStyle) {
            this.enableJVMEventsInImageRecording();
         }

         this.imageFlightRecordingClient.setDestination(destinationFile.getAbsolutePath());
         this.enableImageRecordingClientEvents("WLDF", this.defaultMaxSizeMultiple, this.defaultEnableStackTraces);
         this.imageFlightRecordingClient.start();
      }
   }

   public synchronized boolean isImageRecordingActive() {
      return this.imageFlightRecordingClient != null;
   }

   public synchronized boolean copyImageRecordingToFile(File tempFile) throws Exception {
      FlightRecordingClient tempClient = null;

      boolean var3;
      try {
         tempClient = this.imageFlightRecordingClient.cloneRecordingObject(this.imageFlightRecordingName + "_" + this.imageCopyCount++, true);
         tempClient.copyTo(tempFile.getAbsolutePath());
         var3 = true;
      } finally {
         if (tempClient != null) {
            tempClient.close();
         }

      }

      return var3;
   }

   public synchronized void enableImageRecordingClientEvents(String producerPrefix, int maxSizeMultiple, boolean enableEventStackTraces) {
      if (this.flightRecorderClient != null && this.imageFlightRecordingClient != null) {
         try {
            long maxChunkSize = this.flightRecorderClient.getMaximumRepositoryChunkSize();
            long maxRecordingSize = maxChunkSize * (long)maxSizeMultiple;
            if (maxRecordingSize < 5000000L) {
               maxRecordingSize = 5000000L;
            }

            this.imageFlightRecordingClient.setMaxSize(maxRecordingSize);
            if (this.debugLog.isDebugEnabled()) {
               this.debugLog.debug("Limiting FlightRecording size to " + maxRecordingSize + " bytes");
            }

            if (this.debugLog.isDebugEnabled()) {
               this.debugRecorderDetails();
            }

            Iterator var8 = this.flightRecorderClient.getProducers().iterator();

            while(true) {
               CompositeData producer;
               String producerName;
               do {
                  if (!var8.hasNext()) {
                     return;
                  }

                  producer = (CompositeData)var8.next();
                  producerName = (String)producer.get("name");
               } while(producerPrefix != null && !producerName.startsWith(producerPrefix));

               if (this.debugLog.isDebugEnabled()) {
                  this.debugLog.debug("enabling events for producer: " + producerName);
               }

               CompositeData[] var11 = (CompositeData[])((CompositeData[])producer.get("events"));
               int var12 = var11.length;

               for(int var13 = 0; var13 < var12; ++var13) {
                  CompositeData event = var11[var13];
                  int id = (Integer)event.get("id");
                  this.imageFlightRecordingClient.setEventEnabled(id, true);
                  if (enableEventStackTraces) {
                     if (this.internalStackTracesEnabled) {
                        this.imageFlightRecordingClient.setStackTraceEnabled(id, true);
                     } else if (this.stackTraced.get(id) || this.alwaysStackTraced.get(id)) {
                        this.imageFlightRecordingClient.setStackTraceEnabled(id, true);
                     }
                  } else if (this.alwaysStackTraced.get(id)) {
                     this.imageFlightRecordingClient.setStackTraceEnabled(id, true);
                  }

                  if (this.threshold > 0L) {
                     this.imageFlightRecordingClient.setThreshold(id, this.threshold);
                  }

                  if (this.debugLog.isDebugEnabled()) {
                     this.debugLog.debug("enabled event: " + event.get("name"));
                  }
               }
            }
         } catch (OpenDataException var16) {
            if (this.debugLog.isDebugEnabled()) {
               this.debugLog.debug("enableImageRecordingClientEvents failed", var16);
            }
         } catch (Throwable var17) {
            if (this.debugLog.isDebugEnabled()) {
               this.debugLog.debug("enableImageRecordingClientEvents failed", var17);
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
                  this.isNativeJFR = FlightRecorder.isNativeImplementation();
                  this.isJFRActive = FlightRecorder.isActive();
               }

               this.jfrChecksCached = true;
            } catch (SecurityException var2) {
               if (this.debugLog.isDebugEnabled()) {
                  this.debugLog.debug("isRecordingPossible() SecurityException during detect, return false", var2);
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
      } catch (NoClassDefFoundError var3) {
         if (this.debugLog.isDebugEnabled()) {
            this.debugLog.debug("isRecordingPossible() NoClassDefFoundError during detect, return false");
         }
      } catch (SecurityException var4) {
         if (this.debugLog.isDebugEnabled()) {
            this.debugLog.debug("isRecordingPossible() SecurityException during detect, return false", var4);
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
      return "weblogic.diagnostics.flightrecorder.event.";
   }

   public void addEvent(Object tokenObj, Class eventClass) {
      if (tokenObj instanceof EventToken && eventClass != null) {
         EventToken token = (EventToken)tokenObj;
         if (StackTraced.class.isAssignableFrom(eventClass)) {
            int id = token.getId();
            this.stackTraced.set(id);
            if (DebugEvent.class.isAssignableFrom(eventClass)) {
               this.alwaysStackTraced.set(id);
            }
         }

      }
   }

   public synchronized boolean canGenerateDebug() {
      if (!this.isRecordingPossible()) {
         return false;
      } else if (!this.debugRecordingStarted && !this.debugRecordingStartupFailed) {
         try {
            this.debugProducer = new Producer("WLDF Debug Event Producer", "WLDF Debug Event Producer", "http://www.oracle.com/wls/flightrecorder/debug");
            this.debugProducer.addEvent(DebugEvent.class);
            this.debugProducer.addEvent(DebugTimedEvent.class);
            this.debugProducer.register();
            this.debugFlightRecorderClient = new FlightRecorderClient();
            this.debugFlightRecordingClient = this.debugFlightRecorderClient.createRecordingObject("__WLDF_debug_events");
            EventSettingsBuilder builder = new EventSettingsBuilder();
            builder.createSetting("http://www.oracle.com/*/jvm/*", false, false, Long.MAX_VALUE, 0L);
            builder.createSetting("http://www.oracle.com/wls/flightrecorder/debug/*", true, true, 0L, 0L);
            List jfrEvents = builder.createSettings(this.debugFlightRecorderClient);
            this.debugFlightRecorderClient.updateEventSettings(this.debugFlightRecordingClient.getObjectName(), jfrEvents);
            this.debugFlightRecordingClient.start();
            this.debugRecordingStarted = true;
         } catch (Throwable var3) {
            this.debugRecordingStartupFailed = true;
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
            DebugTimedEvent event = new DebugTimedEvent();
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

   public void commitDebugTimedEvent(Object event, Throwable debugTh, Object contributorOverride) {
      if (event != null) {
         try {
            DebugTimedEvent timedEvent = (DebugTimedEvent)event;
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
         }

      }
   }

   public void debugRecorderDetails() {
      if (this.debugLog.isDebugEnabled()) {
         FlightRecorderClient fr = this.getFlightRecorderClient();
         if (fr == null) {
            this.debugLog.debug("debugRecorderDetails() no flight recorder client");
         } else {
            List theList = null;

            Iterator var3;
            CompositeData setting;
            try {
               this.debugLog.debug("dumpRecorderDetails()");
               theList = fr.getEventSettings();
               this.debugLog.debug("  eventSettings:");
               if (theList != null) {
                  var3 = theList.iterator();

                  while(var3.hasNext()) {
                     setting = (CompositeData)var3.next();
                     this.debugLog.debug("    id            : " + setting.get("id"));
                     this.debugLog.debug("    enabled       : " + setting.get("enabled"));
                     this.debugLog.debug("    stacktrace    : " + setting.get("stacktrace"));
                     this.debugLog.debug("    threshold     : " + setting.get("threshold"));
                     this.debugLog.debug("    requestPeriod : " + setting.get("requestPeriod"));
                  }
               }
            } catch (Throwable var9) {
               this.debugLog.debug("debugRecorderDetails() failed to enumerate events: ", var9);
            }

            try {
               this.debugLog.debug("  producers:");
               theList = fr.getProducers();
               if (theList != null) {
                  var3 = theList.iterator();

                  label74:
                  while(true) {
                     CompositeData[] events;
                     do {
                        if (!var3.hasNext()) {
                           break label74;
                        }

                        setting = (CompositeData)var3.next();
                        this.debugLog.debug("    id          : " + setting.get("id"));
                        this.debugLog.debug("    name        : " + setting.get("name"));
                        this.debugLog.debug("    description : " + setting.get("description"));
                        this.debugLog.debug("    uri         : " + setting.get("uri"));
                        this.debugLog.debug("    events      : ");
                        events = (CompositeData[])((CompositeData[])setting.get("events"));
                     } while(events == null);

                     for(int i = 0; i < events.length; ++i) {
                        this.debugLog.debug("      events[" + i + "] :");
                        if (events[i] != null) {
                           this.debugLog.debug("        id : " + events[i].get("id"));
                        }
                     }
                  }
               }
            } catch (Throwable var8) {
               this.debugLog.debug("debugRecorderDetails() failed to enumerate producers: ", var8);
            }

            try {
               this.debugLog.debug("  Recordings:");
               theList = fr.getRecordings();
               if (theList != null) {
                  var3 = theList.iterator();

                  while(true) {
                     CompositeData options;
                     do {
                        if (!var3.hasNext()) {
                           return;
                        }

                        setting = (CompositeData)var3.next();
                        this.debugLog.debug("    id           : " + setting.get("id"));
                        this.debugLog.debug("    name          : " + setting.get("name"));
                        this.debugLog.debug("    objectName    : " + setting.get("objectName"));
                        this.debugLog.debug("    dataStartTime : " + setting.get("dataStartTime"));
                        this.debugLog.debug("    dataEndTime   : " + setting.get("dataEndTime"));
                        this.debugLog.debug("    options       : ");
                        options = (CompositeData)setting.get("options");
                     } while(options == null);

                     Iterator iter = options.values().iterator();

                     while(iter.hasNext()) {
                        this.debugLog.debug("       value       : " + iter.next());
                     }
                  }
               }
            } catch (Throwable var7) {
               this.debugLog.debug("debugRecorderDetails() failed to enumerate recordings: ", var7);
            }

         }
      }
   }

   public static boolean detectActiveV1() {
      try {
         return FlightRecorder.isNativeImplementation() && FlightRecorder.isActive();
      } catch (NoClassDefFoundError var1) {
      } catch (SecurityException var2) {
      } catch (Throwable var3) {
      }

      return false;
   }

   private synchronized FlightRecorderClient getFlightRecorderClient() {
      if (!this.isRecordingPossible()) {
         return null;
      } else if (this.clientCheckPerformed) {
         return this.flightRecorderClient;
      } else {
         try {
            this.flightRecorderClient = new FlightRecorderClient();
         } catch (InstanceNotFoundException var2) {
         } catch (IOException var3) {
         }

         this.clientCheckPerformed = true;
         return this.flightRecorderClient;
      }
   }
}
