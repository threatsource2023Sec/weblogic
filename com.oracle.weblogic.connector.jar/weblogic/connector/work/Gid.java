package weblogic.connector.work;

import java.util.Arrays;
import weblogic.connector.common.Debug;

public class Gid {
   byte[] bytes;
   int hc;
   int len;
   private static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

   public Gid(byte[] arr) {
      boolean debug = Debug.isXAworkEnabled();
      this.bytes = arr;
      this.len = this.bytes.length;
      if (debug) {
         Debug.xaWork("Gid( arr ) len = " + this.len);
      }

      for(int i = 0; i < this.len; ++i) {
         this.hc += this.bytes[i];
      }

      if (debug) {
         Debug.xaWork("Gid() hashcode for " + Arrays.toString(arr) + " is = " + this.hc);
      }

   }

   public boolean equals(Object obj) {
      if (obj instanceof Gid && obj != null) {
         byte[] objBytes = ((Gid)obj).bytes;
         if (objBytes.length == this.len) {
            for(int i = 0; i < this.len; ++i) {
               if (objBytes[i] != this.bytes[i]) {
                  return false;
               }
            }

            return true;
         }
      }

      return false;
   }

   public int hashCode() {
      return this.hc;
   }

   public String toString() {
      return byteArrayToString(this.bytes);
   }

   private static String byteArrayToString(byte[] barray) {
      if (barray == null) {
         return "";
      } else {
         StringBuffer buf = new StringBuffer();

         for(int i = 0; i < barray.length; ++i) {
            buf.append(DIGITS[(barray[i] & 240) >>> 4]);
            buf.append(DIGITS[barray[i] & 15]);
         }

         return buf.toString();
      }
   }
}
