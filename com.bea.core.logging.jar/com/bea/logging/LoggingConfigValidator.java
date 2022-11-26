package com.bea.logging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

public class LoggingConfigValidator {
   public static void validateSeverity(String severity) {
      if (!LoggingConfigValidator.SeveritiesSet.KNOWN_SEVERITIES.contains(severity)) {
         throw new InvalidSeverityStringException(severity);
      }
   }

   public static void validateLoggerSeverityProperties(Properties props) {
      if (props != null) {
         Iterator i = props.values().iterator();

         while(i.hasNext()) {
            String severity = (String)i.next();
            validateSeverity(severity);
         }
      }

   }

   private static Set getKnownSeverities() {
      HashSet result = new HashSet();
      result.add("Alert");
      result.add("Critical");
      result.add("Debug");
      result.add("Dynamic");
      result.add("Emergency");
      result.add("Error");
      result.add("Info");
      result.add("Notice");
      result.add("Off");
      result.add("Trace");
      result.add("Warning");
      return result;
   }

   public static boolean isLogStartTimeValid(String format, String time) {
      SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());

      try {
         df.parse(time);
         return true;
      } catch (ParseException var4) {
         return false;
      }
   }

   public static void validatePlatformLoggerLevels(Properties props) {
      if (props != null) {
         Iterator i = props.values().iterator();

         while(i.hasNext()) {
            String levelName = (String)i.next();
            Level.parse(levelName);
         }
      }

   }

   private static class SeveritiesSet {
      private static Set KNOWN_SEVERITIES = LoggingConfigValidator.getKnownSeverities();
   }
}
