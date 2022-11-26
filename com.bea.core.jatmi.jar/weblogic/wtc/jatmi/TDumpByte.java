package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.util.Arrays;
import weblogic.wtc.WTCLogger;

public final class TDumpByte {
   private int p1;
   private int p2;
   private char[] buf;
   private static char[] convert = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   private int max = -1;
   private int start = 0;
   private static int dumplen = -1;

   public static void getDumpLen() {
      if (dumplen < 0) {
         String dumplen_str;
         if ((dumplen_str = System.getProperty("weblogic.wtc.DebugWTCUDataLength")) != null) {
            try {
               dumplen = Integer.parseInt(dumplen_str);
            } catch (NumberFormatException var2) {
               dumplen = -1;
            }

            if (dumplen < 0) {
               dumplen = 128;
            }
         } else {
            dumplen = 128;
         }
      }

   }

   public TDumpByte() {
      this.init();
   }

   public TDumpByte(String title, byte[] in) {
      this.init();
      this.printDump(title, in);
   }

   public TDumpByte(String title, byte[] in, int m) {
      this.init();
      if (m == -1) {
         m = dumplen;
      }

      if (m == 0) {
         this.max = in.length;
      } else if (m <= in.length) {
         this.max = m;
      } else {
         this.max = in.length;
      }

      this.printDump(title, in);
   }

   public TDumpByte(String title, byte[] in, int b, int m) {
      this.init();
      int length = in.length;
      if (b >= length) {
         b = 0;
      }

      this.start = b;
      length -= b;
      if (m == -1) {
         m = dumplen;
      }

      if (m == 0) {
         this.max = length;
      } else if (m <= length) {
         this.max = m;
      } else {
         this.max = length;
      }

      this.printDump(title, in);
   }

   public TDumpByte(byte[] in) {
      this.init();
      this.printDump(in);
   }

   public void reinit() {
      Arrays.fill(this.buf, ' ');
      this.p1 = 0;
      this.p2 = 60;
   }

   private void init() {
      this.buf = new char[80];
      Arrays.fill(this.buf, ' ');
      this.p1 = 0;
      this.p2 = 60;
      getDumpLen();
   }

   private void printLine() {
      String msg = new String(this.buf);
      WTCLogger.logDumpOneLine(msg);
   }

   public void printDump(String title, byte[] in) {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(100);
      if (traceEnabled) {
         int total = in.length - this.start;
         if (this.max != -1 && total > this.max) {
            title = title + " " + total + " bytes, truncated to ";
            total = this.max;
         }

         WTCLogger.logDebugTDumpByteStart(title, total);
         this.printDump(in);
         WTCLogger.logDebugTDumpByteEnd();
      }
   }

   public void printDump(byte[] in) {
      int j = 0;
      int total = in.length - this.start;
      if (this.max != -1 && total > this.max) {
         total = this.max;
      }

      int end = this.start + total;

      for(int i = this.start; i < end; ++i) {
         this.toByteCode(in[i]);
         ++j;
         if (j == 20) {
            this.printLine();
            this.reinit();
            j = 0;
         } else {
            this.p1 += 3;
            ++this.p2;
         }
      }

      if (j != 0) {
         this.printLine();
      }

      this.reinit();
   }

   private void toByteCode(byte b) {
      Character ch = new Character((char)b);
      int hnibble = b >> 4 & 15;
      int lnibble = b & 15;
      char c = (char)b;
      this.buf[this.p1] = convert[hnibble];
      this.buf[this.p1 + 1] = convert[lnibble];
      if (Character.isLetterOrDigit(c)) {
         this.buf[this.p2] = c;
      } else {
         this.buf[this.p2] = '.';
      }

   }
}
