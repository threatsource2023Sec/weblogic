package com.oracle.cmm.lowertier.jfr.impl;

import com.oracle.cmm.lowertier.jfr.FlightRecorderManager;
import com.oracle.cmm.lowertier.pressure.ParseUtils;
import java.util.logging.Logger;

public class FlightRecorderManagerBaseImpl implements FlightRecorderManager {
   protected static final String PRODUCER_INFO = "Oracle CMM Lower Tier Producer";
   protected static final String PRODUCER_URI = "http://www.oracle.com/oracle/cmm/lowertier";
   protected static final String RECORDING_NAME = "OracleCMMLowerTierRecording";
   protected static final String JFR_PROPERTY_PREFIX = "com.oracle.cmm.lowertier.jfr.";
   protected static final String JFR_VOLUME_KEY = "com.oracle.cmm.lowertier.jfr.Volume";
   protected static final String OFF = "Off";
   protected static final String LOW = "Low";
   protected static final String MEDIUM = "Medium";
   protected static final String HIGH = "High";
   protected static final boolean DISABLE_CMM_JFR = ParseUtils.getBooleanProperty("com.oracle.cmm.lowertier.jfr.Disabled");
   protected static final Logger LOGGER = Logger.getLogger(FlightRecorderManager.class.getPackage().getName());
   protected int currentVolume = 1;
   protected boolean jfrEnabled = false;

   public boolean isEventGenerationEnabled(int volumeForEvent) {
      if (this.jfrEnabled && volumeForEvent >= 0 && volumeForEvent <= 3) {
         return volumeForEvent <= this.currentVolume;
      } else {
         return false;
      }
   }

   public void addEvent(Class eventClass) {
   }
}
