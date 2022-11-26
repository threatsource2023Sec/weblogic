package weblogic.diagnostics.archive.filestore;

import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.debug.DebugLogger;

class RecordReader {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   private byte[] bytes;
   private byte[] header;
   private RecordParser parser;
   private int headerLength;
   private boolean eob;
   private int curPosition;
   private boolean isLastChunk;

   RecordReader(byte[] bytes, byte[] header, boolean isLastChunk, RecordParser parser) {
      this.bytes = bytes;
      this.header = header;
      this.parser = parser;
      this.isLastChunk = isLastChunk;
      this.headerLength = header != null ? header.length : 0;
   }

   RecordReader(byte[] bytes, String header, boolean isLastChunk, RecordParser parser) {
      this(bytes, header.getBytes(), isLastChunk, parser);
   }

   RecordReader(byte[] bytes, String header, RecordParser parser) {
      this(bytes, header, false, parser);
   }

   RecordReader(byte[] bytes, byte[] header, RecordParser parser) {
      this(bytes, header, false, parser);
   }

   private boolean headerMatches() throws IndexOutOfBoundsException {
      int size = this.headerLength;

      for(int i = 0; i < size; ++i) {
         if (this.bytes[this.curPosition + i] != this.header[i]) {
            return false;
         }
      }

      return true;
   }

   private boolean isNewLine() throws IndexOutOfBoundsException {
      byte b1 = this.bytes[this.curPosition];
      return b1 == 13 || b1 == 10;
   }

   private int align() throws IndexOutOfBoundsException {
      for(boolean foundNL = false; !foundNL || !this.headerMatches(); ++this.curPosition) {
         foundNL = this.isNewLine();
      }

      return this.curPosition;
   }

   int getPosition() {
      return this.curPosition;
   }

   boolean endOfBuffer() {
      return this.eob;
   }

   DataRecord getRecord() {
      DataRecord retVal = null;
      if (this.eob) {
         return null;
      } else {
         int recordPosition = this.curPosition;

         try {
            this.curPosition = this.align();
         } catch (IndexOutOfBoundsException var6) {
            this.eob = true;
            if (!this.isLastChunk) {
               return null;
            }

            this.curPosition = this.bytes.length;
         }

         String rawData = new String(this.bytes, recordPosition, this.curPosition - recordPosition);
         DataRecord rec;
         if (this.parser != null) {
            rec = this.parser.parseRecord(this.bytes, recordPosition, this.curPosition - recordPosition);
            if (DEBUG.isDebugEnabled() && rec == null) {
               DebugLogger.println("Partial/Corrupt record. recordPosition=" + recordPosition + " curPosition=" + this.curPosition + " body=" + new String(this.bytes, recordPosition, this.curPosition - recordPosition));
            }
         } else {
            Object[] data = new Object[]{null, rawData};
            rec = new DataRecord(data);
         }

         if (rec != null) {
            rec.setRawData(rawData);
         }

         return rec;
      }
   }
}
