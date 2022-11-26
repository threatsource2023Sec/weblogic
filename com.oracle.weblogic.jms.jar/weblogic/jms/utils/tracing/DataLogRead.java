package weblogic.jms.utils.tracing;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.LinkedList;

public class DataLogRead {
   private static int currentHeaderFile = 0;
   private static FileInfo[] fileInfos;

   public static void readInit(String[] args) throws Exception {
      fileInfos = new FileInfo[args.length];

      for(int i = 0; i < args.length; ++i) {
         fileInfos[i] = new FileInfo(args[i]);
      }

   }

   public static SubBuffer readHeader() {
      while(currentHeaderFile < fileInfos.length) {
         SubBuffer ret = fileInfos[currentHeaderFile].readHeader();
         if (ret != null) {
            return ret;
         }

         ++currentHeaderFile;
      }

      return null;
   }

   public static void read(DataLogInterpreter interpreter) {
      int recordCount = 0;

      while(true) {
         DataLogRecord best = null;
         int besti = -1;

         for(int i = 0; i < fileInfos.length; ++i) {
            DataLogRecord thisRecord = fileInfos[i].curRecord;
            if (thisRecord.time != 0L) {
               if (fileInfos[i].timeOffset != 0) {
                  thisRecord = new DataLogRecord(thisRecord.point, thisRecord.time + (long)fileInfos[i].timeOffset, thisRecord.data);
               }

               if (best == null || thisRecord.time < best.time) {
                  besti = i;
                  best = thisRecord;
               }
            }
         }

         if (best == null) {
            return;
         }

         interpreter.dataPoint(best);
         fileInfos[besti].next();
         ++recordCount;
      }
   }

   private static class FileInfo {
      private MappedByteBuffer header;
      private int headerPosition;
      private MappedByteBuffer buffer;
      DataLogRecord curRecord;
      String fileName;
      private LinkedList lookAhead;
      private boolean done;
      private long baseTime;
      RandomAccessFile rafile;
      private boolean headersDone;
      int timeOffset;

      SubBuffer readHeader() {
         if (this.headersDone) {
            return null;
         } else {
            short length = this.header.getShort(this.headerPosition);
            this.headerPosition += 2;
            if (length == 0) {
               this.headersDone = true;
               return null;
            } else {
               int position = this.headerPosition;
               this.headerPosition += length;
               return new SubBuffer(this.header, position, length);
            }
         }
      }

      private void readAhead() {
         while(this.lookAhead.size() < 10) {
            if (this.buffer.position() >= this.buffer.limit()) {
               return;
            }

            int point = this.buffer.get();
            if (point == 0) {
               try {
                  this.rafile.setLength((long)(this.buffer.position() + 'ꀀ'));
               } catch (IOException var8) {
                  System.out.println("Cannot truncate file: " + var8);
               }

               return;
            }

            if (point == 127) {
               point = 128;
            } else {
               point += 128;
            }

            long nextTimeStamp = this.baseTime + (long)this.buffer.get() + 128L + ((long)this.buffer.get() + 128L << 8) + ((long)this.buffer.get() + 128L << 16) + ((long)this.buffer.get() + 128L << 24);
            int data = this.buffer.getInt();
            long lastTime;
            if (!this.lookAhead.isEmpty()) {
               lastTime = ((DataLogRecord)this.lookAhead.getLast()).time;
            } else {
               lastTime = 0L;
            }

            if (nextTimeStamp < lastTime && lastTime - nextTimeStamp > 4194304L) {
               ++nextTimeStamp;
               ++this.baseTime;
            }

            if (nextTimeStamp >= lastTime) {
               this.lookAhead.add(new DataLogRecord(point, nextTimeStamp, (long)data));
            } else {
               int i;
               for(i = this.lookAhead.size() - 2; i >= 0 && nextTimeStamp < ((DataLogRecord)this.lookAhead.get(i)).time; --i) {
               }

               this.lookAhead.add(i + 1, new DataLogRecord(point, nextTimeStamp, (long)data));
            }
         }

      }

      private void next() {
         if (this.done) {
            this.curRecord = new DataLogRecord(0, 0L, 0L);
         } else {
            if (this.lookAhead.size() < 10) {
               this.readAhead();
            }

            if (this.lookAhead.isEmpty()) {
               this.done = true;
               this.curRecord = new DataLogRecord(0, 0L, 0L);
            } else {
               this.curRecord = (DataLogRecord)this.lookAhead.removeFirst();
            }
         }
      }

      private FileInfo(String fileName) throws Exception {
         this.curRecord = null;
         this.lookAhead = new LinkedList();
         this.done = false;
         this.headersDone = false;
         this.fileName = fileName;
         this.rafile = new RandomAccessFile(new File(fileName), "rw");
         this.header = this.rafile.getChannel().map(MapMode.READ_WRITE, 0L, 40960L);
         this.buffer = this.rafile.getChannel().map(MapMode.READ_WRITE, 40960L, this.rafile.length() - 40960L);
         this.baseTime = this.header.getLong();
         this.headerPosition = this.header.position();
         this.next();
      }

      // $FF: synthetic method
      FileInfo(String x0, Object x1) throws Exception {
         this(x0);
      }
   }
}
