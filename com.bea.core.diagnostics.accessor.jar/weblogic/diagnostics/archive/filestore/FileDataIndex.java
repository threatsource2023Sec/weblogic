package weblogic.diagnostics.archive.filestore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.archive.FileUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;

class FileDataIndex {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   static final String INDEX_SUFFIX = ".idx";
   private static final int HEADER_SIZE = 32;
   private static final int MAGIC_NUMBER = -1510120791;
   private static final int CHUNK_SIZE = 32768;
   private static final int TUPLE_SIZE = 16;
   private File dataFile;
   private File indexFile;
   private byte[] recordMark;
   private RecordParser recordParser;
   private int decimationFactor;
   private int tuples;
   private int indexedSize;
   private long lowTimestamp;
   private long highTimestamp;
   private boolean valid;

   FileDataIndex(File dataFile, byte[] recordMark, RecordParser recordParser, int decimationFactor) throws IOException {
      this.dataFile = dataFile;
      this.recordMark = recordMark;
      this.recordParser = recordParser;
      this.decimationFactor = decimationFactor;
      this.indexFile = new File(dataFile.getParentFile(), dataFile.getName() + ".idx");
      if (!this.readIndex()) {
         this.buildIndex();
      }

   }

   FileDataIndex(File dataFile, byte[] recordMark, RecordParser recordParser) throws IOException {
      this(dataFile, recordMark, recordParser, 10);
   }

   File getDataFile() {
      return this.dataFile;
   }

   long getLowTimestamp() {
      return this.lowTimestamp;
   }

   long getHighTimestamp() {
      return this.highTimestamp;
   }

   int getIndexedSize() {
      return this.indexedSize;
   }

   int getElementCount() {
      return this.tuples;
   }

   boolean isValid() {
      return this.valid;
   }

   private boolean readIndex() {
      if (DEBUG.isDebugEnabled()) {
         DebugLogger.println("Reading index: " + this.indexFile);
      }

      if (this.indexFile.exists() && this.indexFile.length() >= 32L) {
         try {
            int size = (int)this.indexFile.length();
            byte[] buf = FileUtils.readFile(this.indexFile, 0, size);
            ByteBuffer bb = ByteBuffer.wrap(buf);
            int magic = bb.getInt(0);
            if (magic != -1510120791) {
               DiagnosticsLogger.logInvalidIndexFileMagicNumber(this.indexFile.toString());
               return false;
            } else {
               this.tuples = bb.getInt(4);
               this.indexedSize = bb.getInt(8);
               if (size != 32 + this.tuples * 16) {
                  DiagnosticsLogger.logInvalidIndexFileContents(this.indexFile.toString());
                  return false;
               } else {
                  if (this.tuples > 0) {
                     this.lowTimestamp = bb.getLong(32);
                     this.highTimestamp = bb.getLong(32 + 16 * (this.tuples - 1));
                  }

                  this.valid = true;
                  return true;
               }
            }
         } catch (IOException var5) {
            return false;
         }
      } else {
         return false;
      }
   }

   synchronized void buildIndex() throws IOException {
      if (!this.dataFile.exists()) {
         throw new FileNotFoundException(this.dataFile.toString());
      } else {
         if (DEBUG.isDebugEnabled()) {
            DebugLogger.println("Creating index: " + this.indexFile);
         }

         boolean incremental = this.valid && this.indexFile.exists();
         int startIndex = incremental ? this.indexedSize : 0;
         int newTuples = incremental ? this.tuples : 0;
         long newLowTimestamp = incremental ? this.lowTimestamp : 0L;
         long newHighTimestamp = incremental ? this.highTimestamp : 0L;
         int newIndexedSize = startIndex;
         int endIndex = (int)this.dataFile.length();
         int dataSize = endIndex - startIndex;
         RandomAccessFile out = null;
         byte[] oldIndex = null;

         try {
            out = new RandomAccessFile(this.indexFile, "rw");
            out.seek((long)(32 + newTuples * 16));
            byte[] buf = FileUtils.readFile(this.dataFile, startIndex, dataSize);
            RecordReader recordReader = new RecordReader(buf, this.recordMark, true, this.recordParser);
            int cnt = 0;

            while(!recordReader.endOfBuffer() && recordReader.getPosition() < dataSize) {
               long offset = (long)(startIndex + recordReader.getPosition());
               DataRecord dataRecord = recordReader.getRecord();
               if (dataRecord != null) {
                  if (DEBUG.isDebugEnabled()) {
                     DebugLogger.println("[" + cnt + "] " + dataRecord.toString());
                  }

                  long timestamp = this.recordParser.getTimestamp(dataRecord);
                  newIndexedSize = startIndex + recordReader.getPosition();
                  if (newLowTimestamp == 0L) {
                     newLowTimestamp = timestamp;
                  }

                  newHighTimestamp = timestamp;
                  if (cnt % this.decimationFactor == 0) {
                     out.writeLong(timestamp);
                     out.writeLong(offset);
                     ++newTuples;
                  }

                  ++cnt;
               }
            }

            out.seek(0L);
            out.writeInt(-1510120791);
            out.writeInt(newTuples);
            out.writeInt(newIndexedSize);
            out.writeInt(0);
            out.writeInt(0);
            out.writeInt(0);
            out.writeInt(0);
            out.writeInt(0);
            this.tuples = newTuples;
            this.indexedSize = newIndexedSize;
            this.lowTimestamp = newLowTimestamp;
            this.highTimestamp = newHighTimestamp;
            this.valid = true;
         } finally {
            if (out != null) {
               out.close();
            }

         }

      }
   }

   private boolean indexingNeeded() {
      if (!this.valid) {
         return true;
      } else {
         int fSize = (int)this.dataFile.length();
         return fSize > this.indexedSize + '耀';
      }
   }

   long findOffset(long timestamp) throws IOException {
      if (this.indexingNeeded()) {
         try {
            this.buildIndex();
         } catch (IOException var5) {
            return -1L;
         }
      }

      if (timestamp < this.lowTimestamp) {
         return -1L;
      } else if (timestamp > this.highTimestamp) {
         return -1L;
      } else {
         byte[] buf = FileUtils.readFile(this.indexFile, 0, (int)this.indexFile.length());
         ByteBuffer bb = ByteBuffer.wrap(buf);
         return this.findOffset(bb, timestamp);
      }
   }

   private long findOffset(ByteBuffer buf, long timestamp) {
      int max = buf.getInt(4);
      int lo = 0;
      int hi = max - 1;
      int answer = 0;

      while(lo <= hi) {
         answer = lo + (hi - lo) / 2;
         long t0 = buf.getLong(32 + answer * 16);
         if (timestamp == t0) {
            break;
         }

         if (timestamp < t0) {
            hi = answer - 1;
         }

         if (timestamp > t0) {
            lo = answer + 1;
         }
      }

      while(answer > 0 && timestamp < buf.getLong(32 + answer * 16)) {
         --answer;
      }

      long offset = buf.getLong(32 + answer * 16 + 8);
      return offset;
   }
}
