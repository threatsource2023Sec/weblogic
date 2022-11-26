package com.oracle.cmm.lowertier.pressure;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParseUtils {
   private static final Logger LOGGER = Logger.getLogger(ParseUtils.class.getPackage().getName());

   private ParseUtils() {
   }

   public static void parseCommaSeparatedNamedValues(String commaSepNamedValues, Properties props) {
      if (commaSepNamedValues != null && commaSepNamedValues.length() != 0 && props != null) {
         String[] namedValues = commaSepNamedValues.split(",");
         if (namedValues.length == 0) {
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("no comma separated named value pairs found: " + commaSepNamedValues);
            }

         } else {
            String[] var3 = namedValues;
            int var4 = namedValues.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               String namedValue = var3[var5];
               if (namedValue != null && namedValue.length() != 0) {
                  String[] nameAndValue = namedValue.trim().split("=");
                  if (nameAndValue.length == 2 && nameAndValue[0] != null && nameAndValue[1] != null) {
                     String trimmedName = nameAndValue[0].trim();
                     if (trimmedName.length() != 0) {
                        if (LOGGER.isLoggable(Level.FINER)) {
                           LOGGER.finer("NV pair set: " + trimmedName + " : " + nameAndValue[1].trim());
                        }

                        props.put(trimmedName, nameAndValue[1].trim());
                     }
                  }
               }
            }

         }
      } else {
         if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.finer("nothing to parse");
         }

      }
   }

   public static String[] parseCommaSeparatedValues(String commaSeparatedValues) {
      if (commaSeparatedValues != null && commaSeparatedValues.length() != 0) {
         String[] values = commaSeparatedValues.split(",");
         if (values.length == 0 && LOGGER.isLoggable(Level.FINER)) {
            LOGGER.finer("no comma separated values found: " + commaSeparatedValues);
         }

         return values;
      } else {
         if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.finer("nothing to parse");
         }

         return new String[0];
      }
   }

   public static long getLongValueOrDefault(Properties props, String name, long defaultValue) {
      long retValue = defaultValue;
      if (props != null && name != null) {
         String value = (String)props.get(name);
         if (value == null) {
            return defaultValue;
         }

         try {
            retValue = Long.parseLong(value);
         } catch (Throwable var8) {
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("Failure parsing to long, name: " + name + ", value: " + value + ", th: " + var8.toString());
            }
         }
      }

      return retValue;
   }

   public static double getDoubleValueOrDefault(Properties props, String name, double defaultValue) {
      double retValue = defaultValue;
      if (props != null && name != null) {
         String value = (String)props.get(name);
         if (value == null) {
            return defaultValue;
         }

         try {
            retValue = Double.parseDouble(value);
         } catch (Throwable var8) {
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("Failure parsing to double, name: " + name + ", value: " + value + ", th: " + var8.toString());
            }
         }
      }

      return retValue;
   }

   public static boolean getBooleanProperty(final String name) {
      return (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return Boolean.getBoolean(name);
         }
      });
   }
}
