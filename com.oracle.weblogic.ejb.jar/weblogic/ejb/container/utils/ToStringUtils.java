package weblogic.ejb.container.utils;

import java.util.concurrent.TimeUnit;

public final class ToStringUtils {
   public static String isoToString(int isolationLevel) {
      switch (isolationLevel) {
         case -1:
            return "No Isolation Level Set";
         case 0:
            return "NONE";
         case 1:
            return "READ_UNCOMMITTED";
         case 2:
            return "READ_COMMITED";
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            throw new IllegalArgumentException("Bad iso level: " + isolationLevel);
         case 4:
            return "REPEATABLE_READ";
         case 8:
            return "SERIALIZABLE";
      }
   }

   public static String txAttributeToString(int txAttribute) {
      switch (txAttribute) {
         case 0:
            return "TX_NOT_SUPPORTED";
         case 1:
            return "TX_REQUIRED";
         case 2:
            return "TX_SUPPORTS";
         case 3:
            return "TX_REQUIRES_NEW";
         case 4:
            return "TX_MANDATORY";
         case 5:
            return "TX_NEVER";
         default:
            throw new IllegalArgumentException("Bad tx attribute: " + txAttribute);
      }
   }

   public static String timeUnitToString(TimeUnit tu) {
      switch (tu) {
         case DAYS:
            return "Days";
         case HOURS:
            return "Hours";
         case MINUTES:
            return "Minutes";
         case SECONDS:
            return "Seconds";
         case MILLISECONDS:
            return "Milliseconds";
         case MICROSECONDS:
            return "Microseconds";
         case NANOSECONDS:
            return "Nanoseconds";
         default:
            throw new IllegalArgumentException("Unknown time unit : " + tu);
      }
   }

   public static String escapedQuotesToString(String string) {
      int pos = 0;

      while(true) {
         pos = string.indexOf("'", pos);
         if (pos == -1) {
            return string;
         }

         String escaped;
         if (pos > 0) {
            escaped = string.substring(0, pos) + "''";
         } else {
            escaped = "''";
         }

         string = pos < string.length() - 1 ? escaped + string.substring(pos + 1) : escaped;
         pos += 2;
      }
   }

   public static String escapeBackSlash(String s) {
      int startPos = 0;
      int escapePos = s.indexOf(92, startPos);
      if (escapePos == -1) {
         return s;
      } else {
         StringBuilder outBuf = new StringBuilder();

         int endPos;
         for(endPos = s.length() - 1; escapePos != -1; escapePos = s.indexOf(92, startPos)) {
            outBuf.append(s.substring(startPos, escapePos));
            outBuf.append("\\\\");
            startPos = escapePos + 1;
            if (startPos > endPos) {
               break;
            }
         }

         if (startPos <= endPos) {
            outBuf.append(s.substring(startPos, endPos + 1));
         }

         return outBuf.toString();
      }
   }
}
