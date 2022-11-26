package com.oracle.weblogic.diagnostics.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.management.InstanceNotFoundException;

public class DiagnosticsUtils {
   private static final String SCHEDULE_VALIDATION_PATTERN = "^\\s*[0-9]+\\s*([a-zA-Z]+)?\\s*$";
   private static final String SCHEDULE_PARSE_PATTERN = "(^\\s*[0-9]+|\\s*[a-zA-Z]+\\s*$)";
   private static Pattern scheduleValidationPattern = null;
   private static Pattern scheduleParserPattern = null;

   public static int parseScheduleString(String schedule) throws IllegalArgumentException {
      String s = schedule.trim();
      Class var2 = DiagnosticsUtils.class;
      synchronized(DiagnosticsUtils.class) {
         if (scheduleValidationPattern == null) {
            scheduleValidationPattern = Pattern.compile("^\\s*[0-9]+\\s*([a-zA-Z]+)?\\s*$", 2);
         }

         if (scheduleParserPattern == null) {
            scheduleParserPattern = Pattern.compile("(^\\s*[0-9]+|\\s*[a-zA-Z]+\\s*$)", 2);
         }
      }

      if (!scheduleValidationPattern.matcher(s).matches()) {
         throw new IllegalArgumentException(s);
      } else {
         Matcher matcher = scheduleParserPattern.matcher(s);
         String numUnitsString = "";
         String unitsLabel = "s";
         if (matcher.find()) {
            numUnitsString = matcher.group().trim();
         }

         if (matcher.find()) {
            unitsLabel = matcher.group().trim();
         }

         int numUnits = Integer.parseInt(numUnitsString);
         int secondsPerUnit = 1;
         switch (unitsLabel.toLowerCase(Locale.US).charAt(0)) {
            case 'h':
               secondsPerUnit = 3600;
               break;
            case 'm':
               secondsPerUnit = 60;
            case 's':
               break;
            default:
               throw new IllegalArgumentException(schedule);
         }

         int numSeconds = numUnits * secondsPerUnit;
         return numSeconds;
      }
   }

   public static Properties toProperties(Properties configProperties) {
      Properties props = new Properties();
      if (configProperties != null) {
         Iterator iterator = configProperties.entrySet().iterator();

         while(iterator.hasNext()) {
            Map.Entry p = (Map.Entry)iterator.next();
            props.put(p.getKey(), p.getValue());
         }
      }

      return props;
   }

   public static boolean isLeafValueType(Class type) {
      return isPrimitiveUnBoxedType(type) || String.class.isAssignableFrom(type) || isNumber(type) || isBoolean(type) || Byte.class.isAssignableFrom(type) || Byte.TYPE.isAssignableFrom(type);
   }

   public static boolean isPrimitiveUnBoxedType(Class type) {
      return Integer.TYPE.isAssignableFrom(type) || Long.TYPE.isAssignableFrom(type) || Double.TYPE.isAssignableFrom(type) || Float.TYPE.isAssignableFrom(type) || Byte.TYPE.isAssignableFrom(type) || Boolean.TYPE.isAssignableFrom(type);
   }

   public static boolean isBoolean(Class type) {
      return Boolean.class.isAssignableFrom(type) || Boolean.TYPE.isAssignableFrom(type);
   }

   public static boolean isNumber(Class type) {
      return Integer.TYPE.isAssignableFrom(type) || Long.TYPE.isAssignableFrom(type) || Double.TYPE.isAssignableFrom(type) || Float.TYPE.isAssignableFrom(type) || Float.class.isAssignableFrom(type) || Integer.class.isAssignableFrom(type) || Short.class.isAssignableFrom(type) || Short.class.isAssignableFrom(type) || Double.class.isAssignableFrom(type) || Long.class.isAssignableFrom(type);
   }

   public static boolean isIgnorable(Throwable t) {
      if (isCommunicationsException(t)) {
         return true;
      } else {
         return t.getCause() != null ? isIgnorable(t.getCause()) : false;
      }
   }

   public static boolean isCommunicationsException(Throwable t) {
      return t instanceof IOException || t instanceof InstanceNotFoundException;
   }
}
