package weblogic.diagnostics.archive.filestore;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import weblogic.common.CompletionRequest;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.PersistentStoreTransaction;

final class FileIndexMetaInfo implements Serializable, Comparable {
   static final long serialVersionUID = 12345L;
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticFileArchive");
   private static int connectionWritePolicy = 0;
   private static final int CHUNK_SIZE = 1048576;
   private static final int TUPLE_SIZE = 24;
   private static final int TIMESTAMP_OFFSET = 0;
   private static final int OFFSET_OFFSET = 8;
   private static final int RECORDID_OFFSET = 16;
   private File archiveFile;
   private File dataFile;
   private long datafileModTime;
   private PersistentHandle dataHandle;
   private transient PersistentHandle indexHandle;
   private int decimationFactor = 10;
   private int tuples;
   private int indexedSize;
   private long lowTimestamp;
   private long highTimestamp;
   private long baseRecordId;
   private long recordCount;
   private boolean valid;
   private transient boolean deleted;

   FileIndexMetaInfo(File archiveFile, File dataFile, long baseRecordId) {
      this.setDataFile(dataFile);
      this.setArchiveFile(archiveFile);
      this.baseRecordId = baseRecordId;
   }

   File getArchiveFile() {
      return this.archiveFile;
   }

   void setArchiveFile(File archiveFile) {
      try {
         archiveFile = archiveFile.getCanonicalFile();
      } catch (Exception var3) {
      }

      this.archiveFile = archiveFile;
   }

   File getDataFile() {
      return this.dataFile;
   }

   void setDataFile(File dataFile) {
      this.dataFile = dataFile;
      this.datafileModTime = dataFile.lastModified();
   }

   long getModTime() {
      return this.datafileModTime;
   }

   PersistentHandle getDataHandle() {
      return this.dataHandle;
   }

   PersistentHandle getIndexHandle() {
      return this.indexHandle;
   }

   void setIndexHandle(PersistentHandle handle) {
      this.indexHandle = handle;
   }

   long getLowTimestamp() {
      return this.lowTimestamp;
   }

   long getHighTimestamp() {
      return this.highTimestamp;
   }

   int getTuples() {
      return this.tuples;
   }

   int getIndexedSize() {
      return this.indexedSize;
   }

   long getBaseRecordId() {
      return this.baseRecordId;
   }

   void setBaseRecordId(long baseRecordId) {
      this.baseRecordId = baseRecordId;
   }

   long getRecordCount() {
      return this.recordCount;
   }

   boolean isValid() {
      return this.valid;
   }

   boolean isDeleted() {
      return this.deleted || this.dataFile == null || !this.dataFile.exists();
   }

   private FileOffset getFirstOffset() {
      return new FileOffset(this.dataFile, 0L, 0L);
   }

   boolean isReindexingNeeded() {
      if (!this.deleted && this.dataFile != null) {
         if (this.indexHandle != null && this.dataHandle != null) {
            int size = (int)this.dataFile.length();
            return size - this.indexedSize > 1048576;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   void buildIndex(FileDataArchive archive) throws Exception {
      RecordParser recordParser = archive.getRecordParser();
      byte[] recordMarker = archive.getRecordMarker();
      byte[] indexData;
      if (this.indexHandle != null && this.dataHandle != null) {
         indexData = this.getIndexData(archive);
         byte[] incrementalIndexData = this.buildIndex(recordMarker, recordParser);
         int oldIndexDataLength = indexData != null ? indexData.length : 0;
         int incrementalIndexDataLength = incrementalIndexData != null ? incrementalIndexData.length : 0;
         if (incrementalIndexDataLength == 0) {
            return;
         }

         byte[] indexData = new byte[oldIndexDataLength + incrementalIndexDataLength];
         if (oldIndexDataLength > 0) {
            System.arraycopy(indexData, 0, indexData, 0, oldIndexDataLength);
         }

         System.arraycopy(incrementalIndexData, 0, indexData, oldIndexDataLength, incrementalIndexDataLength);
         this.updateIndexData(archive, indexData);
         if (DEBUG.isDebugEnabled()) {
            DebugLogger.println("FileIndexMetaInfo.buildIndex updated " + this.toString());
         }
      } else {
         indexData = this.buildIndex(recordMarker, recordParser);
         if (indexData == null) {
            return;
         }

         synchronized(archive.getStoreLock()) {
            PersistentStoreTransaction tx = archive.getIndexStore().begin();
            PersistentStoreConnection dataConn = archive.getDataConnection();
            this.dataHandle = dataConn.create(tx, indexData, connectionWritePolicy);
            PersistentStoreConnection metaConn = archive.getMetaConnection();
            this.indexHandle = metaConn.create(tx, this, connectionWritePolicy);
            tx.commit();
         }

         if (DEBUG.isDebugEnabled()) {
            DebugLogger.println("FileIndexMetaInfo.buildIndex created " + this.toString());
         }
      }

   }

   private int fillBuffer(InputStream in, byte[] buf, int startPos, int size) throws IOException {
      int nread;
      int cnt;
      for(nread = 0; size > 0; size -= cnt) {
         cnt = in.read(buf, startPos, size);
         if (cnt < 0) {
            break;
         }

         startPos += cnt;
         nread += cnt;
      }

      return nread;
   }

   private int findNextRecordStart(byte[] buf, int start, int end, byte[] recordMark) {
      int markLength = recordMark != null ? recordMark.length : 0;

      boolean headerMatches;
      do {
         if (start >= end) {
            return -1;
         }

         while(start < end && buf[start] != 10 && buf[start] != 13) {
            ++start;
         }

         ++start;
         headerMatches = true;

         for(int i = 0; headerMatches && i < markLength; ++i) {
            if (start + i >= end || recordMark[i] != buf[start + i]) {
               headerMatches = false;
            }
         }
      } while(!headerMatches);

      return start;
   }

   private byte[] buildIndex(byte[] recordMark, RecordParser recordParser) throws IOException {
      byte[] indexBytes = new byte[0];
      if (!this.dataFile.exists()) {
         throw new FileNotFoundException(this.dataFile.toString());
      } else {
         int startIndex = 0;
         int endIndex = (int)this.dataFile.length();
         int var10000 = endIndex - startIndex;
         boolean lastChunk = false;
         byte[] buf = new byte[1048576];
         InputStream in = null;
         ByteArrayOutputStream bos = null;
         DataOutputStream dos = null;
         DataRecord firstRecord = null;
         DataRecord lastRecord = null;
         this.recordCount = 0L;
         this.lowTimestamp = 0L;
         this.highTimestamp = 0L;
         this.tuples = 0;
         int fillIndex = 0;

         try {
            in = new BufferedInputStream(new FileInputStream(this.dataFile));
            bos = new ByteArrayOutputStream();
            dos = new DataOutputStream(bos);

            label204:
            while(true) {
               while(true) {
                  if (startIndex >= endIndex) {
                     break label204;
                  }

                  int dataSize = endIndex - startIndex;
                  int availableSpace = 1048576 - fillIndex;
                  if (dataSize > availableSpace) {
                     dataSize = availableSpace;
                  } else {
                     lastChunk = true;
                  }

                  int nread = this.fillBuffer(in, buf, fillIndex, dataSize);
                  if (nread <= 0) {
                     break label204;
                  }

                  startIndex += nread;
                  fillIndex += nread;

                  int nextPos;
                  for(int pos = 0; pos < fillIndex; pos = nextPos) {
                     nextPos = this.findNextRecordStart(buf, pos, fillIndex, recordMark);
                     if (nextPos <= pos) {
                        int remainder = fillIndex - pos;

                        for(int i = 0; i < remainder; ++i) {
                           buf[i] = buf[pos + i];
                        }

                        fillIndex = remainder;
                        if (lastChunk) {
                           if (recordParser != null) {
                              lastRecord = recordParser.parseRecord(buf, 0, remainder);
                           }

                           ++this.recordCount;
                        }
                        break;
                     }

                     if (firstRecord == null) {
                        if (recordParser != null) {
                           firstRecord = recordParser.parseRecord(buf, pos, nextPos - pos);
                        } else {
                           Object[] data = new Object[]{null, new String(buf, pos, nextPos - pos)};
                           firstRecord = new DataRecord(data);
                        }
                     }

                     ++this.recordCount;
                  }
               }
            }

            if (firstRecord == null) {
               firstRecord = lastRecord;
            }

            if (firstRecord != null) {
               if (recordParser != null) {
                  this.lowTimestamp = recordParser.getTimestamp(firstRecord);
               }

               dos.writeLong(this.lowTimestamp);
               dos.writeLong(0L);
               dos.writeLong(0L);
               ++this.tuples;
            }

            if (lastRecord != null && recordParser != null) {
               this.highTimestamp = recordParser.getTimestamp(lastRecord);
            }

            dos.flush();
            indexBytes = bos.toByteArray();
         } finally {
            if (in != null) {
               in.close();
            }

            if (dos != null) {
               dos.close();
            }

         }

         return indexBytes;
      }
   }

   void delete(FileDataArchive archive) throws PersistentStoreException {
      this.deleted = true;
      synchronized(archive.getStoreLock()) {
         if (DEBUG.isDebugEnabled()) {
            DebugLogger.println("FileIndexMetaInfo.delete BEGIN " + this.toString());
         }

         PersistentStoreTransaction tx = archive.getIndexStore().begin();
         if (this.existsMetaInfo(archive)) {
            boolean changed = false;
            PersistentStoreConnection conn = archive.getDataConnection();
            if (this.dataHandle != null) {
               conn.delete(tx, this.dataHandle, 0);
               changed = true;
            }

            conn = archive.getMetaConnection();
            if (this.indexHandle != null) {
               conn.delete(tx, this.indexHandle, 0);
               changed = true;
            }

            if (changed) {
               tx.commit();
            }
         }

         if (DEBUG.isDebugEnabled()) {
            DebugLogger.println("FileIndexMetaInfo.delete END " + this.toString());
         }

         this.dataHandle = null;
         this.indexHandle = null;
      }
   }

   void writeMetaInfo(FileDataArchive archive) throws PersistentStoreException {
      synchronized(archive.getStoreLock()) {
         if (DEBUG.isDebugEnabled()) {
            DebugLogger.println("FileIndexMetaInfo.writeMetaInfo BEGIN " + this.toString());
         }

         if (this.indexHandle != null && this.existsMetaInfo(archive)) {
            PersistentStoreTransaction tx = archive.getIndexStore().begin();
            PersistentStoreConnection metaConn = archive.getMetaConnection();
            metaConn.update(tx, this.indexHandle, this, connectionWritePolicy);
            tx.commit();
            if (DEBUG.isDebugEnabled()) {
               DebugLogger.println("FileIndexMetaInfo.writeMetaInfo END " + this.toString());
            }

         }
      }
   }

   private void updateIndexData(FileDataArchive archive, byte[] indexData) throws Exception {
      synchronized(archive.getStoreLock()) {
         if (DEBUG.isDebugEnabled()) {
            DebugLogger.println("FileIndexMetaInfo.updateIndexData BEGIN " + this.toString());
         }

         if (this.dataHandle != null && this.indexHandle != null && this.existsMetaInfo(archive)) {
            PersistentStoreTransaction tx = archive.getIndexStore().begin();
            PersistentStoreConnection dataConn = archive.getDataConnection();
            dataConn.update(tx, this.dataHandle, indexData, connectionWritePolicy);
            PersistentStoreConnection metaConn = archive.getMetaConnection();
            metaConn.update(tx, this.indexHandle, this, connectionWritePolicy);
            tx.commit();
            if (DEBUG.isDebugEnabled()) {
               DebugLogger.println("FileIndexMetaInfo.updateIndexData END " + this.toString());
            }

         }
      }
   }

   byte[] getIndexData(FileDataArchive archive) throws Exception {
      synchronized(archive.getStoreLock()) {
         if (DEBUG.isDebugEnabled()) {
            DebugLogger.println("FileIndexMetaInfo.getIndexData BEGIN " + this.toString());
         }

         if (this.dataHandle != null && this.existsMetaInfo(archive)) {
            PersistentStoreConnection dataConn = archive.getDataConnection();
            PersistentStoreTransaction tx = archive.getIndexStore().begin();
            CompletionRequest cr = new CompletionRequest();
            dataConn.read(tx, this.dataHandle, cr);
            PersistentStoreRecord record = null;

            try {
               record = (PersistentStoreRecord)cr.getResult();
            } catch (Throwable var9) {
               UnexpectedExceptionHandler.handle("Could not read index data on " + this.toString(), var9);
               throw new Exception(var9);
            }

            byte[] indexData = (byte[])((byte[])record.getData());
            if (DEBUG.isDebugEnabled()) {
               DebugLogger.println("FileIndexMetaInfo.getIndexData END " + this.toString());
            }

            return indexData;
         } else {
            return null;
         }
      }
   }

   private boolean existsMetaInfo(FileDataArchive archive) {
      if (this.indexHandle == null) {
         return false;
      } else {
         Object data = null;

         try {
            PersistentStoreConnection metaConn = archive.getMetaConnection();
            PersistentStoreTransaction tx = archive.getIndexStore().begin();
            CompletionRequest cr = new CompletionRequest();
            metaConn.read(tx, this.indexHandle, cr);
            PersistentStoreRecord record = (PersistentStoreRecord)cr.getResult();
            data = record.getData();
         } catch (Throwable var7) {
            return false;
         }

         return data != null;
      }
   }

   long getLatestKnownRecordID(FileDataArchive archive) throws Exception {
      return this.baseRecordId + this.recordCount;
   }

   FileOffset findOffsetByTimestamp(FileDataArchive archive, long timestamp) throws IOException {
      if (timestamp < this.lowTimestamp) {
         return null;
      } else {
         return timestamp > this.highTimestamp ? null : this.getAbsoluteFileOffset(this.findOffset((FileDataArchive)archive, timestamp, 0));
      }
   }

   private FileOffset getAbsoluteFileOffset(FileOffset offset) {
      offset.setRecordId(offset.getRecordId() + this.baseRecordId);
      return offset;
   }

   FileOffset findOffsetByRecordId(FileDataArchive archive, long recordId) throws IOException {
      FileOffset offset = null;
      if (recordId <= 0L) {
         offset = this.getFirstOffset();
      } else {
         offset = this.findOffset((FileDataArchive)archive, recordId, 16);
      }

      return this.getAbsoluteFileOffset(offset);
   }

   private FileOffset findOffset(FileDataArchive archive, long cmpValue, int memberOffset) throws IOException {
      byte[] indexData = null;

      byte[] indexData;
      try {
         indexData = this.getIndexData(archive);
      } catch (Exception var8) {
         IOException ioe = new IOException(var8.getMessage());
         ioe.initCause(var8);
         throw ioe;
      }

      int size = indexData != null ? indexData.length : 0;
      if (size == 0) {
         return this.getFirstOffset();
      } else {
         ByteBuffer bb = ByteBuffer.wrap(indexData);
         return this.findOffset(bb, cmpValue, memberOffset);
      }
   }

   private FileOffset findOffset(ByteBuffer buf, long cmpValue, int memberOffset) {
      int max = this.tuples;
      if (max <= 0) {
         return this.getFirstOffset();
      } else {
         int lo = 0;
         int hi = max - 1;
         int answer = 0;

         while(lo <= hi) {
            answer = lo + (hi - lo) / 2;
            long memberVal = buf.getLong(answer * 24 + memberOffset);
            if (cmpValue == memberVal) {
               break;
            }

            if (cmpValue < memberVal) {
               hi = answer - 1;
            }

            if (cmpValue > memberVal) {
               lo = answer + 1;
            }
         }

         while(answer > 0 && cmpValue <= buf.getLong(answer * 24 + memberOffset)) {
            --answer;
         }

         long offset = buf.getLong(answer * 24 + 8);
         long recordId = buf.getLong(answer * 24 + 16);
         return new FileOffset(this.dataFile, offset, recordId);
      }
   }

   public int compareTo(Object obj) {
      FileIndexMetaInfo other = (FileIndexMetaInfo)obj;
      if (other == null) {
         return -1;
      } else if (this.datafileModTime > other.datafileModTime) {
         return 1;
      } else {
         return this.datafileModTime < other.datafileModTime ? -1 : 0;
      }
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else {
         FileIndexMetaInfo other = (FileIndexMetaInfo)obj;
         return this.datafileModTime == other.datafileModTime;
      }
   }

   public String toString() {
      return "FileIndexMetaInfo@" + this.hashCode() + " indexH=" + this.indexHandle + " dataH=" + this.dataHandle + " file=" + this.dataFile;
   }

   public void printIndex(FileDataArchive archive) {
      DebugLogger.println("File: " + this.dataFile);
      DebugLogger.println(" tuples        " + this.tuples);
      DebugLogger.println(" indexedSize   " + this.indexedSize);
      DebugLogger.println(" lowTimestamp  " + this.lowTimestamp);
      DebugLogger.println(" highTimestamp " + this.highTimestamp);
      DebugLogger.println(" baseRecordId  " + this.baseRecordId);
      DebugLogger.println(" recordCount   " + this.recordCount);
      DebugLogger.println("");

      try {
         byte[] indexData = this.getIndexData(archive);
         ByteBuffer buf = ByteBuffer.wrap(indexData);
         int size = indexData.length / 24;

         for(int i = 0; i < size; ++i) {
            long timestamp = buf.getLong(i * 24 + 0);
            long offset = buf.getLong(i * 24 + 8);
            long recordId = buf.getLong(i * 24 + 16);
            DebugLogger.println("  tpl=" + i + " recordid=" + recordId + " timestamp=" + timestamp + " offset=" + offset);
         }
      } catch (Exception var12) {
         DebugLogger.println(">>>> Error reading index data for " + this.dataFile);
      }

      DebugLogger.println("");
   }
}
