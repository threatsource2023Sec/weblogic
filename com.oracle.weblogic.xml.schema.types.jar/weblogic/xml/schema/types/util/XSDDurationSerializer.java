package weblogic.xml.schema.types.util;

import java.math.BigInteger;
import weblogic.xml.schema.types.Duration;

public class XSDDurationSerializer {
   private static final char YEAR = 'Y';
   private static final char MONTH = 'M';
   private static final char DAY = 'D';
   private static final char HOUR = 'H';
   private static final char MINUTE = 'M';
   private static final char SECOND = 'S';

   public static String getString(Duration d) {
      StringBuffer b = new StringBuffer();
      setSign(d, b);
      b.append('P');
      setDate(d, b);
      setTime(d, b);
      return b.toString();
   }

   private static void setSign(Duration d, StringBuffer b) {
      if (d.getSignum() == -1) {
         b.append('-');
      }

   }

   private static void setDate(Duration d, StringBuffer b) {
      BigInteger bi = d.getYears();
      if (bi != null) {
         b.append(bi.toString() + 'Y');
      }

      bi = d.getMonths();
      if (bi != null) {
         b.append(bi.toString() + 'M');
      }

      bi = d.getDays();
      if (bi != null) {
         b.append(bi.toString() + 'D');
      }

   }

   private static void setTime(Duration d, StringBuffer b) {
      b.append('T');
      int i = 0;
      BigInteger bi = d.getHours();
      if (bi != null) {
         b.append(bi.toString() + 'H');
         ++i;
      }

      bi = d.getMinutes();
      if (bi != null) {
         b.append(bi.toString() + 'M');
         ++i;
      }

      bi = d.getSeconds();
      if (bi != null) {
         b.append(bi.toString());
         ++i;
         bi = d.getSecondFraction();
         if (bi != null) {
            b.append('.' + bi.toString() + 'S');
         } else {
            b.append('S');
         }
      }

      if (i == 0) {
         b.deleteCharAt(b.length() - 1);
      }

   }
}
