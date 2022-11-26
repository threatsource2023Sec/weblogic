package weblogic.diagnostics.context;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicLong;

public class ECIDUtils {
   private static final int ENCODING_FORMAT_IPv4_LENGTH = 17;
   private static final int ENCODING_FORMAT_IPv6_LENGTH = 28;
   private static final int UB1_BITS = 8;
   private static final int UB1_MASK = 255;
   private static final int UB4_BITS = 32;
   private static final int IPv4_SHIFT = 5;
   private static final int IPv4_MOVE = 27;
   private static final int IPv4_MASK = 134217727;
   private static final int IPv6_SHIFT = 11;
   private static final int IPv6_MOVE = 21;
   private static final int IPv6_MASK = 2097151;
   private static AtomicLong sECIDCounter = new AtomicLong(0L);
   private static int s_ecidSize;
   private static String s_processId = null;
   public static final String CLASS_NAME;

   private static String createProcessId() {
      String processId = createProcessIdIP();
      return processId;
   }

   private static String createProcessIdIP() {
      String processId = null;
      int pid = 0;
      if (pid == 0) {
         long stamp = System.currentTimeMillis();
         stamp /= 1000L;
         pid = (int)(stamp & 4294967295L);
      }

      byte[] ipBytes;
      try {
         ipBytes = InetAddress.getLocalHost().getAddress();
      } catch (Exception var5) {
         ipBytes = new byte[]{0, 0, 0, 0};
      }

      if (ipBytes.length == 16) {
         processId = createProcessIdIPv6(ipBytes, pid);
      } else {
         processId = createProcessIdIPv4(ipBytes, pid);
      }

      return processId;
   }

   private static String createProcessIdIPv6(byte[] ipBytes, int pid) {
      int[] ipInts = new int[]{ipBytes[0] << 24 & -16777216 | ipBytes[1] << 16 & 16711680 | ipBytes[2] << 8 & '\uff00' | ipBytes[3] & 255, ipBytes[4] << 24 & -16777216 | ipBytes[5] << 16 & 16711680 | ipBytes[6] << 8 & '\uff00' | ipBytes[7] & 255, ipBytes[8] << 24 & -16777216 | ipBytes[9] << 16 & 16711680 | ipBytes[10] << 8 & '\uff00' | ipBytes[11] & 255, ipBytes[12] << 24 & -16777216 | ipBytes[13] << 16 & 16711680 | ipBytes[14] << 8 & '\uff00' | ipBytes[15] & 255};
      ipInts[1] = ~ipInts[1];
      int save0 = ipInts[0] & 2097151;
      int save1 = ipInts[1] & 2097151;
      int save2 = ipInts[2] & 2097151;
      int save3 = ipInts[3] & 2097151;
      ipInts[0] >>>= 11;
      ipInts[1] >>>= 11;
      ipInts[2] >>>= 11;
      ipInts[3] >>>= 11;
      ipInts[0] |= save3 << 21;
      ipInts[1] |= save0 << 21;
      ipInts[2] |= save1 << 21;
      ipInts[3] |= save2 << 21;
      save0 = ipInts[0];
      save1 = ipInts[1];
      save2 = ipInts[2];
      save3 = ipInts[3];
      ipInts[0] = byteSwap(save2, 3, 3) | byteSwap(save1, 3, 2) | byteSwap(save1, 1, 1) | byteSwap(save3, 2, 0);
      ipInts[1] = byteSwap(save2, 2, 3) | byteSwap(save0, 0, 2) | byteSwap(save3, 3, 1) | byteSwap(save1, 0, 0);
      ipInts[2] = byteSwap(save2, 1, 3) | byteSwap(save0, 3, 2) | byteSwap(save3, 0, 1) | byteSwap(save0, 1, 0);
      ipInts[3] = byteSwap(save0, 2, 3) | byteSwap(save2, 0, 2) | byteSwap(save1, 2, 1) | byteSwap(save3, 1, 0);
      ipInts[0] = bitSwap(ipInts[0], 2);
      ipInts[1] = bitSwap(ipInts[1], 26);
      ipInts[2] = bitSwap(ipInts[2], 18);
      ipInts[3] = bitSwap(ipInts[3], 10);
      long ipHigh = (long)ipInts[0] << 32 & -4294967296L | (long)ipInts[1] & 4294967295L;
      long ipLow = (long)ipInts[2] << 32 & -4294967296L | (long)ipInts[3] & 4294967295L;
      char[] format = new char[28];
      int index = 0;
      ContextEncode.encodeLong64(ipHigh, format, index, 11);
      index += 11;
      ContextEncode.encodeLong64(ipLow, format, index, 11);
      index += 11;
      ContextEncode.encodeInt64(pid, format, index, 6);
      return new String(format);
   }

   private static String createProcessIdIPv4(byte[] ipBytes, int pid) {
      int[] ipInts = new int[]{0, ipBytes[0] << 24 & -16777216 | ipBytes[1] << 16 & 16711680 | ipBytes[2] << 8 & '\uff00' | ipBytes[3] & 255};
      ipInts[0] = ~ipInts[1];
      int save0 = ipInts[0] & 134217727;
      int save1 = ipInts[1] & 134217727;
      ipInts[0] >>>= 5;
      ipInts[1] >>>= 5;
      ipInts[0] |= save1 << 27;
      ipInts[1] |= save0 << 27;
      save0 = ipInts[0];
      save1 = ipInts[1];
      ipInts[0] = byteSwap(save0, 0, 3) | byteSwap(save1, 0, 2) | byteSwap(save1, 1, 1) | byteSwap(save0, 2, 0);
      ipInts[1] = byteSwap(save0, 1, 3) | byteSwap(save1, 3, 2) | byteSwap(save0, 3, 1) | byteSwap(save1, 2, 0);
      ipInts[0] = bitSwap(ipInts[0], 12);
      ipInts[1] = bitSwap(ipInts[1], 22);
      long ipLong = (long)ipInts[0] << 32 & -4294967296L | (long)ipInts[1] & 4294967295L;
      char[] format = new char[17];
      int index = 0;
      ContextEncode.encodeLong64(ipLong, format, index, 11);
      index += 11;
      ContextEncode.encodeInt64(pid, format, index, 6);
      return new String(format);
   }

   private static int byteSwap(int value, int source, int target) {
      return byteGet(value, source) << byteShift(target);
   }

   private static int bitSwap(int value, int skip) {
      skip &= -2;
      int swap = 0;

      for(int count = 0; count < 32; count += 2) {
         int bit1 = value & 1 << count;
         int bit2 = value & 2 << count;
         if (count != skip) {
            swap |= bit1 << 1 | bit2 >>> 1;
         } else {
            swap |= bit1 | bit2;
         }
      }

      return swap;
   }

   static int byteShift(int target) {
      return target * 8;
   }

   static int byteGet(int value, int target) {
      return value >>> byteShift(target) & 255;
   }

   public static String createECID() {
      StringBuilder ecid = new StringBuilder(s_ecidSize);
      char[] format = new char[11];
      long stamp = System.currentTimeMillis();
      ContextEncode.encodeLong64(stamp, format, 0, 11);
      ecid.append(format);
      ecid.append(s_processId);
      stamp = sECIDCounter.incrementAndGet();
      ContextEncode.encodeLong64(stamp, format, 0, 6);
      ecid.append(format, 0, 6);
      return ecid.toString();
   }

   static {
      s_processId = createProcessId();
      s_ecidSize = s_processId.length() + 11 + 6;
      CLASS_NAME = WrapUtils.class.getName();
   }
}
