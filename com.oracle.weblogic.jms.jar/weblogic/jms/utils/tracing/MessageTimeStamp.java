package weblogic.jms.utils.tracing;

import java.util.Random;
import javax.jms.JMSException;
import javax.jms.Message;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.utils.Simple;

public class MessageTimeStamp {
   private static Object lock = new Object();
   public static final int SEND_RESPONSE = 1;
   public static final int ACK = 2;
   public static final int ACK_RESPONSE = 3;
   public static final int PUSH = 4;
   public static final int SEND = 5;
   public static final int NMESSAGE_TYPES = 6;
   public static final int CLIENT_PRODUCER = 0;
   public static final int FE_PRODUCER = 1;
   public static final int BE_PRODUCER = 2;
   public static final int KERNEL = 3;
   public static final int BE_CONSUMER = 4;
   public static final int FE_CONSUMER = 5;
   public static final int CLIENT_CONSUMER = 6;
   public static final int AGGREGATION = 7;
   public static final int RESPONSE_PATH_START = 8;
   public static final int ACK_PATH_START = 9;
   public static final int READ = 10;
   public static final int WRITE = 16;
   public static final int SERIALIZE_START = 22;
   public static final int SERIALIZE_END = 28;
   public static final int DESERIALIZE_START = 34;
   public static final int DESERIALIZE_END = 40;
   private static boolean serverOnly = true;
   private static boolean aggregationOnly = false;
   private static boolean on = true;
   private static Random random = new Random();
   private static boolean beenHere = false;

   private static void makeHeader() {
      DataLog.addToHeader("FLOW CLIENT_PRODUCER 0 CLIENT FIRST PATH1");
      DataLog.addToHeader("FLOW SERIALIZE_START_SEND 27 CLIENT NO_DYE PATH1");
      DataLog.addToHeader("FLOW SERIALIZE_END_SEND 33 CLIENT NO_DYE PATH1");
      DataLog.addToHeader("FLOW WRITE_SEND 21 CLIENT NO_DYE PATH1");
      DataLog.addToHeader("FLOW READ_SEND 15 FIRST NO_DYE PATH1");
      DataLog.addToHeader("FLOW DESERIALIZE_START_SEND 39 NO_DYE PATH1");
      DataLog.addToHeader("FLOW DESERIALIZE_END_SEND 45 NO_DYE PATH1");
      DataLog.addToHeader("FLOW FE_PRODUCER 1 PATH1 FIRST_DYED");
      DataLog.addToHeader("FLOW BE_PRODUCER 2 PATH1");
      DataLog.addToHeader("FLOW KERNEL 3 PATH1");
      DataLog.addToHeader("FLOW BE_CONSUMER 4 PATH1");
      DataLog.addToHeader("FLOW FE_CONSUMER 5 PATH1");
      DataLog.addToHeader("FLOW SERIALIZE_START_PUSH 26 NO_DYE PATH1");
      DataLog.addToHeader("FLOW SERIALIZE_END_PUSH 32 NO_DYE PATH1");
      DataLog.addToHeader("FLOW WRITE_PUSH 20 NO_DYE PATH1");
      DataLog.addToHeader("FLOW READ_PUSH 14 CLIENT NO_DYE PATH1");
      DataLog.addToHeader("FLOW DESERIALIZE_START_PUSH 38 CLIENT NO_DYE PATH1");
      DataLog.addToHeader("FLOW DESERIALIZE_END_PUSH 44 CLIENT NO_DYE PATH1");
      DataLog.addToHeader("FLOW CLIENT_CONSUMER 6 CLIENT LAST PATH1");
      DataLog.addToHeader("FLOW SEND_RESPONSE_PATH_START 8 PATH2 FIRST");
      DataLog.addToHeader("FLOW WRITE_SEND_RESPONSE 17 LAST NO_DYE PATH2");
      DataLog.addToHeader("FLOW READ_SEND_RESPONSE 11 CLIENT NO_DYE PATH2 LAST");
      DataLog.addToHeader("FLOW ACK_PATH_START 9 CLIENT PATH3 FIRST");
      DataLog.addToHeader("FLOW WRITE_ACK 18 CLIENT NO_DYE PATH3");
      DataLog.addToHeader("FLOW READ_ACK 12 NO_DYE PATH3 FIRST");
      DataLog.addToHeader("FLOW WRITE_ACK_RESPONSE 19 NO_DYE PATH3");
      DataLog.addToHeader("FLOW READ_ACK_RESPONSE 13 CLIENT NO_DYE PATH3 LAST");
   }

   public static void record(int point, int data) {
      if (on && !aggregationOnly) {
         if (!serverOnly || point != 0 && point != 6) {
            if (!beenHere) {
               synchronized(lock) {
                  if (!beenHere) {
                     makeHeader();
                     beenHere = true;
                  }
               }
            }

            DataLog.record(point, data);
         }
      }
   }

   public static void record(int point, Message mess) {
      if (on) {
         if (!serverOnly || point != 0 && point != 6) {
            MessageImpl msg = (MessageImpl)mess;
            int perf = false;

            try {
               byte[] bytes;
               int perf;
               if (point == 0 || serverOnly && point == 1) {
                  bytes = new byte[6];
                  perf = random.nextInt() & Integer.MAX_VALUE;
                  bytes[0] = 68;
                  bytes[1] = 89;
                  bytes[2] = (byte)((perf & 63) + 32);
                  bytes[3] = (byte)(((perf & 16128) >> 8) + 32);
                  bytes[4] = (byte)(((perf & 4128768) >> 16) + 32);
                  bytes[5] = (byte)(((perf & 1056964608) >> 24) + 32);
                  msg.setJMSCorrelationIDAsBytes(bytes);
                  perf = (bytes[2] & 255) + ((bytes[3] & 255) << 8) + ((bytes[4] & 255) << 16) + ((bytes[5] & 255) << 24);
               } else {
                  bytes = msg.getJMSCorrelationIDAsBytes();
                  if (bytes == null) {
                     perf = 0;
                  } else {
                     perf = (bytes[2] & 255) + ((bytes[3] & 255) << 8) + ((bytes[4] & 255) << 16) + ((bytes[5] & 255) << 24);
                  }
               }

               if (perf != 0) {
                  record(point, perf);
               }
            } catch (JMSException var5) {
               System.err.println("Exception at point " + point + ": " + var5);
            }

         }
      }
   }

   public static AggregationCounter newAggregationCounter(String id, int nvalues) {
      if (!on) {
         return null;
      } else {
         if (!beenHere && aggregationOnly) {
            synchronized(lock) {
               if (!beenHere) {
                  DataLog.init(0);
                  beenHere = true;
               }
            }
         }

         return new AggregationCounter(id, nvalues);
      }
   }

   static {
      String tmp = Simple.getenv("weblogic.jms.PerformanceCounters");
      on = tmp != null;
      tmp = Simple.getenv("weblogic.jms.PerformanceCounters.ServerOnly");
      serverOnly = tmp != null;
      tmp = Simple.getenv("weblogic.jms.PerformanceCounters.AggregationOnly");
      aggregationOnly = tmp != null;
      on = on || serverOnly || aggregationOnly;
      if (on) {
         System.out.println("Performance counters: on = " + on + ", serverOnly = " + serverOnly + ", aggregationOnly = " + aggregationOnly);
      }

   }
}
