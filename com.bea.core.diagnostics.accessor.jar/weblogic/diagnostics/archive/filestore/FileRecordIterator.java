package weblogic.diagnostics.archive.filestore;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.archive.FileUtils;
import weblogic.diagnostics.archive.RecordIterator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.query.QueryException;

public final class FileRecordIterator extends RecordIterator {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   private static final int CHUNK_SIZE = 32768;
   private static final int MAX_RECORDS = 200;
   private FileOffset position;
   private boolean endReached;

   FileRecordIterator(FileDataArchive archive, long startTime, long endTime, String queryString) throws QueryException {
      super(archive, startTime, endTime, queryString);
      archive.addAccessIterator(this);
   }

   FileRecordIterator(FileDataArchive archive, long startId, long endId, long endTime, String queryString) throws QueryException {
      super(archive, startId, endId, endTime, queryString);
      archive.addAccessIterator(this);
   }

   protected long findFirstRecord(long value, boolean useTimestamp) {
      long recordId = -1L;

      try {
         this.position = ((FileDataArchive)this.archive).findOffset(value, useTimestamp);
         recordId = this.position.getRecordId();
      } catch (IOException var7) {
         this.endReached = true;
      }

      return recordId;
   }

   private boolean readRecords(byte[] buf, boolean isLastChunk) {
      FileDataArchive fileArchive = (FileDataArchive)this.archive;
      RecordParser recordParser = fileArchive.getRecordParser();
      byte[] recordMarker = fileArchive.getRecordMarker();
      RecordReader recordReader = new RecordReader(buf, recordMarker, isLastChunk, recordParser);
      int cnt = 0;
      int relativePosition = recordReader.getPosition();

      while(!this.endReached && cnt < 200 && !recordReader.endOfBuffer()) {
         DataRecord dataRecord = recordReader.getRecord();
         if (dataRecord != null || !recordReader.endOfBuffer()) {
            relativePosition = recordReader.getPosition();
         }

         if (dataRecord != null) {
            Object[] values = dataRecord.getValues();
            long recordId = this.getCurrentRecordId();
            this.position.setRecordId(recordId);
            if (values != null && values.length > 0) {
               values[0] = recordId;
            }

            fileArchive.setLastKnownRecordId(recordId);
            boolean include = false;
            long timestamp = recordParser.getTimestamp(dataRecord);
            if (this.useTimestamp) {
               if (timestamp >= this.startTime && timestamp < this.endTime) {
                  include = true;
               }
            } else if (recordId >= this.startId && recordId < this.endId && timestamp < this.endTime) {
               include = true;
            }

            if (DEBUG.isDebugEnabled()) {
               int values_len = values != null ? values.length : 0;
               Object recID_debug = values_len > 0 ? values[0] : "Unknown";
               DebugLogger.println("FileRecordIterator: useTimestamp=" + this.useTimestamp + " values=" + Arrays.toString(values) + " values_len=" + values_len + " recordId=" + recordId + " recID_debug=" + recID_debug + " timestamp=" + timestamp + " startTime=" + this.startTime + " endTime=" + this.endTime + " startId=" + this.startId + " endId=" + this.endId + " include=" + include);
            }

            if (timestamp >= this.endTime) {
               this.endReached = true;
            }

            if (!this.useTimestamp && recordId >= this.endId) {
               this.endReached = true;
            }

            if (include) {
               try {
                  this.currentDataRecord = dataRecord;
                  if (this.query == null || this.query.executeQuery(this)) {
                     this.dataRecords.add(dataRecord);
                     ++cnt;
                  }
               } catch (QueryException var18) {
                  if (DEBUG.isDebugEnabled()) {
                     DebugLogger.println("Failed to execute query on: " + dataRecord);
                     var18.printStackTrace();
                  }
               }
            }
         }
      }

      if (DEBUG.isDebugEnabled()) {
         DebugLogger.println("FileRecordIterator: Read " + this.dataRecords.size() + " records from " + this.position.getFile());
      }

      if (relativePosition == 0 && !isLastChunk) {
         return true;
      } else {
         long newOffset = this.position.getOffset() + (long)relativePosition;
         if (relativePosition > 0 && newOffset < this.position.getFile().length()) {
            this.position.setOffset(newOffset);
         } else {
            this.computeNextPosition();
         }

         return false;
      }
   }

   private synchronized void computeNextPosition() {
      FileDataArchive fileArchive = (FileDataArchive)this.archive;
      if (DEBUG.isDebugEnabled()) {
         DebugLogger.println("computeNextPosition: archive=" + this.archive.getName() + " FROM position=" + this.position);
      }

      this.position = fileArchive.getNextOffset(this.position);
      if (this.position == null) {
         this.endReached = true;
      }

      if (DEBUG.isDebugEnabled()) {
         DebugLogger.println("computeNextPosition: archive=" + this.archive.getName() + " TO position=" + this.position);
      }

   }

   protected void fill() {
      FileDataArchive fileArchive = (FileDataArchive)this.archive;
      int chunkSize = 32768;

      while(!this.endReached && this.dataRecords.size() == 0) {
         try {
            long fileSize = this.position.getFile().length();
            boolean isLastChunk = this.position.getOffset() + (long)chunkSize >= fileSize;
            if (DEBUG.isDebugEnabled()) {
               DebugLogger.println("FileRecordIterator: Attempting to fill: " + this.position.getFile() + " offset=" + this.position.getOffset() + " fileSize = " + fileSize + " chunkSize=" + chunkSize + " isLastChunk=" + isLastChunk + " at time " + new Date());
            }

            byte[] buf = FileUtils.readFile(this.position.getFile(), (int)this.position.getOffset(), chunkSize);
            boolean retry = this.readRecords(buf, isLastChunk);
            if (retry) {
               chunkSize += 32768;
            }
         } catch (IOException var8) {
            if (DEBUG.isDebugEnabled()) {
               var8.printStackTrace();
            }

            this.computeNextPosition();
         }
      }

      if (this.dataRecords.size() == 0) {
         fileArchive.removeAccessIterator(this);
      }

   }

   synchronized void realign(File fromFile, File toFile) {
      if (DEBUG.isDebugEnabled()) {
         DebugLogger.println("FileRecordIterator.realign FROM " + fromFile);
         DebugLogger.println("FileRecordIterator.realign TO " + toFile);
         DebugLogger.println("OLD Offset: " + this.position);
      }

      if (this.position != null && fromFile.equals(this.position.getFile())) {
         this.position.setFile(toFile);
      }

      if (DEBUG.isDebugEnabled()) {
         DebugLogger.println("FileRecordIterator: NEW Offset: " + this.position);
      }

   }
}
