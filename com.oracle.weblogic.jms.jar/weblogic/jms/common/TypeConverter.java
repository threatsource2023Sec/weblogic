package weblogic.jms.common;

import weblogic.jms.JMSClientExceptionLogger;

public final class TypeConverter {
   public static boolean toBoolean(Object o) throws javax.jms.MessageFormatException {
      if (o == null) {
         return false;
      } else if (o instanceof Boolean) {
         return (Boolean)o;
      } else if (o instanceof String) {
         return Boolean.valueOf((String)o);
      } else {
         throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logConvertBooleanLoggable(o.toString()).getMessage());
      }
   }

   public static byte toByte(Object o) throws javax.jms.MessageFormatException {
      if (o == null) {
         throw new NumberFormatException(JMSClientExceptionLogger.logNullByteLoggable().getMessage());
      } else if (o instanceof Byte) {
         return (Byte)o;
      } else if (o instanceof String) {
         return Byte.parseByte((String)o);
      } else {
         throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logConvertByteLoggable(o.toString()).getMessage());
      }
   }

   public static short toShort(Object o) throws javax.jms.MessageFormatException {
      if (o == null) {
         throw new NumberFormatException(JMSClientExceptionLogger.logNullShortLoggable().getMessage());
      } else if (!(o instanceof Byte) && !(o instanceof Short)) {
         if (o instanceof String) {
            return Short.parseShort((String)o);
         } else {
            throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logConvertShortLoggable(o.toString()).getMessage());
         }
      } else {
         return ((Number)o).shortValue();
      }
   }

   public static char toChar(Object o) throws javax.jms.MessageFormatException {
      if (o == null) {
         throw new NullPointerException(JMSClientExceptionLogger.logNullCharLoggable().getMessage());
      } else {
         try {
            Character c = (Character)o;
            return c;
         } catch (ClassCastException var4) {
            try {
               String s = (String)o;
               if (s.length() == 1) {
                  return s.charAt(0);
               }
            } catch (ClassCastException var3) {
            }

            throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logConvertCharLoggable(o.toString()).getMessage());
         }
      }
   }

   public static int toInt(Object o) throws javax.jms.MessageFormatException {
      if (o == null) {
         throw new NumberFormatException(JMSClientExceptionLogger.logNullIntLoggable().getMessage());
      } else if (!(o instanceof Integer) && !(o instanceof Short) && !(o instanceof Byte)) {
         if (o instanceof String) {
            return Integer.parseInt((String)o);
         } else {
            throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logConvertIntLoggable(o.toString()).getMessage());
         }
      } else {
         return ((Number)o).intValue();
      }
   }

   public static long toLong(Object o) throws javax.jms.MessageFormatException {
      if (o == null) {
         throw new NumberFormatException(JMSClientExceptionLogger.logNullLongLoggable().getMessage());
      } else if (!(o instanceof Long) && !(o instanceof Integer) && !(o instanceof Short) && !(o instanceof Byte)) {
         if (o instanceof String) {
            return Long.parseLong((String)o);
         } else {
            throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logConvertLongLoggable(o.toString()).getMessage());
         }
      } else {
         return ((Number)o).longValue();
      }
   }

   public static float toFloat(Object o) throws javax.jms.MessageFormatException {
      if (o == null) {
         throw new NullPointerException(JMSClientExceptionLogger.logNullFloatLoggable().getMessage());
      } else if (o instanceof Float) {
         return ((Number)o).floatValue();
      } else if (o instanceof String) {
         return Float.valueOf((String)o);
      } else {
         throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logConvertFloatLoggable(o.toString()).getMessage());
      }
   }

   public static double toDouble(Object o) throws javax.jms.MessageFormatException {
      if (o == null) {
         throw new NullPointerException(JMSClientExceptionLogger.logNullDoubleLoggable().getMessage());
      } else if (!(o instanceof Double) && !(o instanceof Float)) {
         if (o instanceof String) {
            return Double.valueOf((String)o);
         } else {
            throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logConvertDoubleLoggable(o.toString()).getMessage());
         }
      } else {
         return ((Number)o).doubleValue();
      }
   }

   public static String toString(Object o) throws javax.jms.MessageFormatException {
      if (o == null) {
         return null;
      } else if (o instanceof byte[]) {
         throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logConvertByteArrayLoggable().getMessage());
      } else {
         return o.toString();
      }
   }

   public static byte[] toByteArray(Object o) throws javax.jms.MessageFormatException {
      if (o == null) {
         return null;
      } else {
         try {
            return (byte[])((byte[])o);
         } catch (ClassCastException var2) {
            throw new javax.jms.MessageFormatException(JMSClientExceptionLogger.logConvertToByteArrayLoggable(o.toString()).getMessage());
         }
      }
   }
}
