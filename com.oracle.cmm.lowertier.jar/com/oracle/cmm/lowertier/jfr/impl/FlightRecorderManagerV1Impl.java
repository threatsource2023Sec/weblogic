package com.oracle.cmm.lowertier.jfr.impl;

import com.oracle.cmm.lowertier.jfr.ResourcePressureChangeEvent;
import com.oracle.cmm.lowertier.jfr.ResourcePressureEvaluationEvent;
import com.oracle.jrockit.jfr.EventToken;
import com.oracle.jrockit.jfr.FlightRecorder;
import com.oracle.jrockit.jfr.Producer;
import com.oracle.jrockit.jfr.client.EventSettingsBuilder;
import com.oracle.jrockit.jfr.client.FlightRecorderClient;
import com.oracle.jrockit.jfr.client.FlightRecordingClient;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

public class FlightRecorderManagerV1Impl extends FlightRecorderManagerBaseImpl {
   private FlightRecorderClient flightRecorderClient = null;
   private FlightRecordingClient flightRecordingClient = null;
   private Producer cmmProducer = null;

   private void initialize(Properties props) {
      this.jfrEnabled = this.isRecordingPossible();
      if (this.jfrEnabled) {
         if (props != null && !props.isEmpty()) {
            String volumeString = (String)props.get("com.oracle.cmm.lowertier.jfr.Volume");
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("Volume property = " + volumeString);
            }

            if ("Off".equalsIgnoreCase(volumeString)) {
               this.currentVolume = 0;
            } else if ("Low".equalsIgnoreCase(volumeString)) {
               this.currentVolume = 1;
            } else if ("Medium".equalsIgnoreCase(volumeString)) {
               this.currentVolume = 2;
            } else if ("High".equalsIgnoreCase(volumeString)) {
               this.currentVolume = 3;
            } else {
               this.currentVolume = 1;
               if (LOGGER.isLoggable(Level.FINER)) {
                  LOGGER.finer("Invalid volume specified: " + volumeString + ", defaulting to Low");
               }
            }
         }

         if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.finer("Volume = " + this.currentVolume);
         }

         try {
            this.cmmProducer = new Producer("Oracle CMM Lower Tier Producer", "Oracle CMM Lower Tier Producer", "http://www.oracle.com/oracle/cmm/lowertier");
            this.cmmProducer.addEvent(ResourcePressureChangeEvent.class);
            this.cmmProducer.addEvent(ResourcePressureEvaluationEvent.class);
            this.cmmProducer.register();
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("CMM Producer registered for FlightRecorder");
            }

            this.startRecording();
         } catch (Throwable var3) {
            if (LOGGER.isLoggable(Level.FINER)) {
               var3.printStackTrace();
            }
         }

      }
   }

   private final boolean isRecordingPossible() {
      boolean isNativeJFR = false;
      boolean isJFRActive = false;

      try {
         if (DISABLE_CMM_JFR) {
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("FlightRecorder is disabled by system property");
            }

            isNativeJFR = false;
            isJFRActive = false;
         } else {
            isNativeJFR = FlightRecorder.isNativeImplementation();
            isJFRActive = FlightRecorder.isActive();
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("FlightRecorder enablement checks returned: native: " + isNativeJFR + ", active: " + isJFRActive);
            }
         }
      } catch (SecurityException var4) {
         if (LOGGER.isLoggable(Level.FINER)) {
            var4.printStackTrace();
            LOGGER.finer("No permission to access FlightRecorder, Flight Recorder is disabled");
         }

         return false;
      } catch (Throwable var5) {
         if (LOGGER.isLoggable(Level.FINER)) {
            var5.printStackTrace();
            LOGGER.finer("Problem testing FlightRecorder availability, Flight Recorder is disabled");
         }

         return false;
      }

      boolean enabled = isNativeJFR ? isJFRActive : false;
      if (LOGGER.isLoggable(Level.FINER)) {
         LOGGER.finer("FlightRecorder enabled: " + enabled);
      }

      return enabled;
   }

   public void addEvent(Class eventClass) {
      if (this.jfrEnabled && this.cmmProducer != null) {
         try {
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("adding event class: " + eventClass.getName());
            }

            EventToken token = this.cmmProducer.addEvent(eventClass);
            this.flightRecordingClient.setEventEnabled(token.getId(), true);
         } catch (Throwable var3) {
            if (LOGGER.isLoggable(Level.FINER)) {
               var3.printStackTrace();
            }
         }

      }
   }

   private void startRecording() {
      try {
         this.flightRecorderClient = new FlightRecorderClient();
         this.flightRecordingClient = this.flightRecorderClient.createRecordingObject("OracleCMMLowerTierRecording");
         EventSettingsBuilder builder = new EventSettingsBuilder();
         builder.createSetting("http://www.oracle.com/*/jvm/*", false, false, Long.MAX_VALUE, 0L);
         builder.createSetting("http://www.oracle.com/oracle/cmm/lowertier/*", true, false, 0L, 0L);
         List jfrEvents = builder.createSettings(this.flightRecorderClient);
         this.flightRecorderClient.updateEventSettings(this.flightRecordingClient.getObjectName(), jfrEvents);
         this.flightRecordingClient.start();
      } catch (Throwable var3) {
         if (LOGGER.isLoggable(Level.FINER)) {
            var3.printStackTrace();
         }
      }

   }

   public static boolean detectActiveV1() {
      try {
         return FlightRecorder.isNativeImplementation() && FlightRecorder.isActive();
      } catch (NoClassDefFoundError var1) {
      } catch (SecurityException var2) {
      }

      return false;
   }
}
