package weblogic.diagnostics.archive.filestore;

import java.io.File;

final class FileOffset {
   private File file;
   private long offset;
   private long recordId;

   FileOffset(File file, long offset) {
      this.file = file;
      this.offset = offset;
   }

   FileOffset(File file, long offset, long recordId) {
      this(file, offset);
      this.recordId = recordId;
   }

   File getFile() {
      return this.file;
   }

   void setFile(File file) {
      this.file = file;
   }

   long getOffset() {
      return this.offset;
   }

   void setOffset(long offset) {
      this.offset = offset;
   }

   long getRecordId() {
      return this.recordId;
   }

   void setRecordId(long recordId) {
      this.recordId = recordId;
   }

   public String toString() {
      return "FileOffset{" + this.file + ", " + this.offset + ", " + this.recordId + "}";
   }
}
