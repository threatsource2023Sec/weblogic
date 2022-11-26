package org.glassfish.grizzly;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortRange {
   private static final Pattern FORMAT = Pattern.compile("(\\d+)(?:(?:,|:)(\\d+))?");
   private final int lower;
   private final int upper;

   public PortRange(int low, int high) {
      if (low >= 1 && high >= low && 65535 >= high) {
         this.lower = low;
         this.upper = high;
      } else {
         throw new IllegalArgumentException("Invalid range");
      }
   }

   public PortRange(int port) {
      this(port, port);
   }

   public static PortRange valueOf(String s) throws IllegalArgumentException {
      Matcher m = FORMAT.matcher(s);
      if (!m.matches()) {
         throw new IllegalArgumentException("Invalid string format: " + s);
      } else {
         int low;
         int high;
         try {
            low = Integer.parseInt(m.group(1));
            high = m.groupCount() == 1 ? low : Integer.parseInt(m.group(2));
         } catch (NumberFormatException var5) {
            throw new IllegalArgumentException("Invalid string format: " + s);
         }

         return new PortRange(low, high);
      }
   }

   public int getLower() {
      return this.lower;
   }

   public int getUpper() {
      return this.upper;
   }

   public String toString() {
      return String.format("%d:%d", this.lower, this.upper);
   }
}
