package weblogic.jms.utils.tracing;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import weblogic.jms.utils.Simple;
import weblogic.utils.time.Timer;

public class DataLog {
   private static Object lock = new Object();
   private static int maxCount = 6291456;
   private static boolean beenHere = false;
   private static RecordFile recordFile;
   private static final int RECORD_SIZE = 9;
   private static String base = "/tmp/timer_";
   private static boolean initDone = false;
   private static Timer timer;
   private static Random random = new Random();

   public static void init(int initMaxCount) {
      if (!initDone) {
         initDone = true;
         maxCount = initMaxCount;
         init();
      }

   }

   public static void init() {
      if (maxCount != 0) {
         timer = PreciseTimerFactory.newTimer();
      }

      recordFile = new RecordFile(base + Math.abs(random.nextInt() % 10000), maxCount);
   }

   public static void addToHeader(String addThis) {
      if (!beenHere) {
         synchronized(lock) {
            if (!beenHere) {
               init();
               beenHere = true;
            }
         }
      }

      recordFile.addToHeader(addThis);
   }

   public static void record(int point, int data) {
      if (!beenHere) {
         synchronized(lock) {
            if (!beenHere) {
               init();
               beenHere = true;
            }
         }
      }

      recordFile.record(point, data);
   }

   public static SubBuffer newDataArea(String identification, int length) {
      if (!beenHere) {
         synchronized(lock) {
            if (!beenHere) {
               init();
               beenHere = true;
            }
         }
      }

      return recordFile.newDataArea(identification, length);
   }

   public static void main(String[] args) {
      for(int i = 0; i < 100000; ++i) {
         record(0, 1234);
         record(1, 1234);
         record(2, 1234);
         record(3, 1234);
         record(4, 1234);
         record(5, 1234);
         record(6, 1234);
      }

   }

   static {
      String tmp = Simple.getenv("weblogic.jms.PerformanceMaxCount");
      if (tmp != null) {
         maxCount = Integer.decode(tmp);
      }

      tmp = Simple.getenv("weblogic.jms.PerformanceFileBase");
      if (tmp != null) {
         base = tmp;
      }

   }

   private static class RecordFile {
      private MappedByteBuffer buffer;
      private AtomicInteger position = new AtomicInteger(40960);
      private AtomicInteger headerPosition = new AtomicInteger();
      private int maxPosition;
      private long baseTime;

      RecordFile(String base, int maxCount) {
         this.maxPosition = maxCount * 9 + 'ꀀ';
         File myFile = new File(base + ".out");

         int i;
         try {
            RandomAccessFile output = new RandomAccessFile(myFile, "rw");
            byte[] b = new byte[1024];

            for(i = 0; i < this.maxPosition; i += 1024) {
               output.write(b);
            }

            FileChannel channel = output.getChannel();
            this.buffer = channel.map(MapMode.READ_WRITE, 0L, (long)this.maxPosition);
         } catch (Exception var9) {
            System.err.println("Failed!: " + var9);
            var9.printStackTrace(System.err);
            System.exit(1);
         }

         if (maxCount != 0) {
            long baseTimeMilli = (new Date()).getTime();

            for(i = 0; i < 2; ++i) {
               long newTimeMilli;
               do {
                  newTimeMilli = (new Date()).getTime();
                  this.baseTime = DataLog.timer.timestamp() / 1000L;
               } while(newTimeMilli == baseTimeMilli);

               baseTimeMilli = newTimeMilli;
            }

            System.out.println("Base time is " + this.baseTime);
            this.buffer.putLong(baseTimeMilli * 1000L);
         }

         this.headerPosition.addAndGet(8);
      }

      void addToHeader(String addThis) {
         char[] chars = (addThis + "\n").toCharArray();
         int postPutPosition = this.headerPosition.addAndGet(2 + chars.length * 2);
         int offset = postPutPosition - 2 - chars.length * 2;
         this.buffer.putShort(offset, (short)(chars.length * 2));
         offset += 2;

         for(int i = 0; i < chars.length; ++i) {
            this.buffer.putChar(offset, chars[i]);
            offset += 2;
         }

      }

      void record(int point, int data) {
         int postPutPosition = this.position.addAndGet(9);
         if (postPutPosition <= this.maxPosition) {
            int putPosition = postPutPosition - 9;
            if (point == 128) {
               point = 127;
            } else {
               point -= 128;
            }

            this.buffer.put(putPosition++, (byte)point);
            long timePlay = DataLog.timer.timestamp() / 1000L - this.baseTime;
            this.buffer.put(putPosition++, (byte)((int)((timePlay & 255L) - 128L)));
            timePlay >>= 8;
            this.buffer.put(putPosition++, (byte)((int)((timePlay & 255L) - 128L)));
            timePlay >>= 8;
            this.buffer.put(putPosition++, (byte)((int)((timePlay & 255L) - 128L)));
            timePlay >>= 8;
            this.buffer.put(putPosition++, (byte)((int)((timePlay & 255L) - 128L)));
            this.buffer.putInt(putPosition, data);
         }

      }

      public SubBuffer newDataArea(String identification, int length) {
         char[] chars = (identification + "\n").toCharArray();
         int postPutPosition = this.headerPosition.addAndGet(2 + chars.length * 2 + 2 + length);
         int offset = postPutPosition - 2 - chars.length * 2 - 2 - length;
         this.buffer.putShort(offset, (short)(chars.length * 2));
         offset += 2;

         for(int i = 0; i < chars.length; ++i) {
            this.buffer.putChar(offset, chars[i]);
            offset += 2;
         }

         this.buffer.putShort(offset, (short)length);
         return new SubBuffer(this.buffer, offset + 2, length);
      }
   }
}
