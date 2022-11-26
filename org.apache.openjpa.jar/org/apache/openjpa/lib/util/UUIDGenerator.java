package org.apache.openjpa.lib.util;

import java.io.IOException;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.Random;
import org.apache.commons.lang.exception.NestableRuntimeException;

public class UUIDGenerator {
   private static final byte IDX_TIME_HI = 6;
   private static final byte IDX_TYPE = 6;
   private static final byte IDX_TIME_MID = 4;
   private static final byte IDX_TIME_LO = 0;
   private static final byte IDX_TIME_SEQ = 8;
   private static final byte IDX_VARIATION = 8;
   private static final byte TS_TIME_LO_IDX = 4;
   private static final byte TS_TIME_LO_LEN = 4;
   private static final byte TS_TIME_MID_IDX = 2;
   private static final byte TS_TIME_MID_LEN = 2;
   private static final byte TS_TIME_HI_IDX = 0;
   private static final byte TS_TIME_HI_LEN = 2;
   private static final long GREG_OFFSET = 12219292800000L;
   private static final long MILLI_MULT = 10000L;
   private static final byte TYPE_TIME_BASED = 16;
   private static final Random RANDOM = new SecureRandom();
   private static final byte[] IP;
   private static int _counter;
   private static long _currentMillis;
   private static long _lastMillis = 0L;
   private static final int MAX_14BIT = 16383;
   private static short _seq;

   public static byte[] next() {
      byte[] uuid = new byte[16];
      System.arraycopy(IP, 0, uuid, 10, IP.length);
      long now = 0L;
      Class var3 = UUIDGenerator.class;
      synchronized(UUIDGenerator.class) {
         now = getTime();
         uuid[8] = (byte)((_seq & 16128) >>> 8);
         uuid[8] = (byte)(uuid[8] | 128);
         uuid[9] = (byte)(_seq & 255);
      }

      byte[] timeBytes = Bytes.toBytes(now);
      System.arraycopy(timeBytes, 4, uuid, 0, 4);
      System.arraycopy(timeBytes, 2, uuid, 4, 2);
      System.arraycopy(timeBytes, 0, uuid, 6, 2);
      uuid[6] = (byte)(uuid[6] | 16);
      return uuid;
   }

   public static String nextString() {
      byte[] bytes = next();

      try {
         return new String(bytes, "ISO-8859-1");
      } catch (Exception var2) {
         return new String(bytes);
      }
   }

   public static String nextHex() {
      return Base16Encoder.encode(next());
   }

   static long getTime() {
      long newTime = getUUIDTime();
      if (newTime <= _lastMillis) {
         incrementSequence();
         newTime = getUUIDTime();
      }

      _lastMillis = newTime;
      return newTime;
   }

   private static long getUUIDTime() {
      if (_currentMillis != System.currentTimeMillis()) {
         _currentMillis = System.currentTimeMillis();
         _counter = 0;
      }

      if ((long)(_counter + 1) >= 10000L) {
         ++_currentMillis;
         _counter = 0;
      }

      long currentTime = (_currentMillis + 12219292800000L) * 10000L;
      return currentTime + (long)(_counter++);
   }

   private static void incrementSequence() {
      if (++_seq > 16383) {
         _seq = (short)RANDOM.nextInt(16383);
      }

   }

   static {
      _seq = (short)RANDOM.nextInt(16383);
      byte[] ip = null;

      byte[] ip;
      try {
         ip = InetAddress.getLocalHost().getAddress();
      } catch (IOException var2) {
         throw new NestableRuntimeException(var2);
      }

      IP = new byte[6];
      RANDOM.nextBytes(IP);
      System.arraycopy(ip, 0, IP, 2, ip.length);
   }
}
