package org.python.apache.commons.compress.archivers.tar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.python.apache.commons.compress.archivers.ArchiveEntry;
import org.python.apache.commons.compress.archivers.ArchiveInputStream;
import org.python.apache.commons.compress.archivers.zip.ZipEncoding;
import org.python.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.python.apache.commons.compress.utils.ArchiveUtils;
import org.python.apache.commons.compress.utils.IOUtils;

public class TarArchiveInputStream extends ArchiveInputStream {
   private static final int SMALL_BUFFER_SIZE = 256;
   private final byte[] smallBuf;
   private final int recordSize;
   private final int blockSize;
   private boolean hasHitEOF;
   private long entrySize;
   private long entryOffset;
   private final InputStream is;
   private TarArchiveEntry currEntry;
   private final ZipEncoding zipEncoding;
   final String encoding;
   private Map globalPaxHeaders;

   public TarArchiveInputStream(InputStream is) {
      this(is, 10240, 512);
   }

   public TarArchiveInputStream(InputStream is, String encoding) {
      this(is, 10240, 512, encoding);
   }

   public TarArchiveInputStream(InputStream is, int blockSize) {
      this(is, blockSize, 512);
   }

   public TarArchiveInputStream(InputStream is, int blockSize, String encoding) {
      this(is, blockSize, 512, encoding);
   }

   public TarArchiveInputStream(InputStream is, int blockSize, int recordSize) {
      this(is, blockSize, recordSize, (String)null);
   }

   public TarArchiveInputStream(InputStream is, int blockSize, int recordSize, String encoding) {
      this.smallBuf = new byte[256];
      this.globalPaxHeaders = new HashMap();
      this.is = is;
      this.hasHitEOF = false;
      this.encoding = encoding;
      this.zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
      this.recordSize = recordSize;
      this.blockSize = blockSize;
   }

   public void close() throws IOException {
      this.is.close();
   }

   public int getRecordSize() {
      return this.recordSize;
   }

   public int available() throws IOException {
      if (this.isDirectory()) {
         return 0;
      } else {
         return this.entrySize - this.entryOffset > 2147483647L ? Integer.MAX_VALUE : (int)(this.entrySize - this.entryOffset);
      }
   }

   public long skip(long n) throws IOException {
      if (n > 0L && !this.isDirectory()) {
         long available = this.entrySize - this.entryOffset;
         long skipped = this.is.skip(Math.min(n, available));
         this.count(skipped);
         this.entryOffset += skipped;
         return skipped;
      } else {
         return 0L;
      }
   }

   public boolean markSupported() {
      return false;
   }

   public void mark(int markLimit) {
   }

   public synchronized void reset() {
   }

   public TarArchiveEntry getNextTarEntry() throws IOException {
      if (this.hasHitEOF) {
         return null;
      } else {
         if (this.currEntry != null) {
            IOUtils.skip(this, Long.MAX_VALUE);
            this.skipRecordPadding();
         }

         byte[] headerBuf = this.getRecord();
         if (headerBuf == null) {
            this.currEntry = null;
            return null;
         } else {
            try {
               this.currEntry = new TarArchiveEntry(headerBuf, this.zipEncoding);
            } catch (IllegalArgumentException var3) {
               throw new IOException("Error detected parsing the header", var3);
            }

            this.entryOffset = 0L;
            this.entrySize = this.currEntry.getSize();
            byte[] longNameData;
            if (this.currEntry.isGNULongLinkEntry()) {
               longNameData = this.getLongNameData();
               if (longNameData == null) {
                  return null;
               }

               this.currEntry.setLinkName(this.zipEncoding.decode(longNameData));
            }

            if (this.currEntry.isGNULongNameEntry()) {
               longNameData = this.getLongNameData();
               if (longNameData == null) {
                  return null;
               }

               this.currEntry.setName(this.zipEncoding.decode(longNameData));
            }

            if (this.currEntry.isGlobalPaxHeader()) {
               this.readGlobalPaxHeaders();
            }

            if (this.currEntry.isPaxHeader()) {
               this.paxHeaders();
            } else if (!this.globalPaxHeaders.isEmpty()) {
               this.applyPaxHeadersToCurrentEntry(this.globalPaxHeaders);
            }

            if (this.currEntry.isOldGNUSparse()) {
               this.readOldGNUSparse();
            }

            this.entrySize = this.currEntry.getSize();
            return this.currEntry;
         }
      }
   }

   private void skipRecordPadding() throws IOException {
      if (!this.isDirectory() && this.entrySize > 0L && this.entrySize % (long)this.recordSize != 0L) {
         long numRecords = this.entrySize / (long)this.recordSize + 1L;
         long padding = numRecords * (long)this.recordSize - this.entrySize;
         long skipped = IOUtils.skip(this.is, padding);
         this.count(skipped);
      }

   }

   protected byte[] getLongNameData() throws IOException {
      ByteArrayOutputStream longName = new ByteArrayOutputStream();
      int length = false;

      int length;
      while((length = this.read(this.smallBuf)) >= 0) {
         longName.write(this.smallBuf, 0, length);
      }

      this.getNextEntry();
      if (this.currEntry == null) {
         return null;
      } else {
         byte[] longNameData = longName.toByteArray();

         for(length = longNameData.length; length > 0 && longNameData[length - 1] == 0; --length) {
         }

         if (length != longNameData.length) {
            byte[] l = new byte[length];
            System.arraycopy(longNameData, 0, l, 0, length);
            longNameData = l;
         }

         return longNameData;
      }
   }

   private byte[] getRecord() throws IOException {
      byte[] headerBuf = this.readRecord();
      this.hasHitEOF = this.isEOFRecord(headerBuf);
      if (this.hasHitEOF && headerBuf != null) {
         this.tryToConsumeSecondEOFRecord();
         this.consumeRemainderOfLastBlock();
         headerBuf = null;
      }

      return headerBuf;
   }

   protected boolean isEOFRecord(byte[] record) {
      return record == null || ArchiveUtils.isArrayZero(record, this.recordSize);
   }

   protected byte[] readRecord() throws IOException {
      byte[] record = new byte[this.recordSize];
      int readNow = IOUtils.readFully(this.is, record);
      this.count(readNow);
      return readNow != this.recordSize ? null : record;
   }

   private void readGlobalPaxHeaders() throws IOException {
      this.globalPaxHeaders = this.parsePaxHeaders(this);
      this.getNextEntry();
   }

   private void paxHeaders() throws IOException {
      Map headers = this.parsePaxHeaders(this);
      this.getNextEntry();
      this.applyPaxHeadersToCurrentEntry(headers);
   }

   Map parsePaxHeaders(InputStream i) throws IOException {
      Map headers = new HashMap(this.globalPaxHeaders);

      int ch;
      label44:
      do {
         int len = 0;

         for(int read = 0; (ch = i.read()) != -1; len += ch - 48) {
            ++read;
            if (ch == 10) {
               break;
            }

            if (ch == 32) {
               ByteArrayOutputStream coll = new ByteArrayOutputStream();

               while(true) {
                  if ((ch = i.read()) == -1) {
                     continue label44;
                  }

                  ++read;
                  if (ch == 61) {
                     String keyword = coll.toString("UTF-8");
                     int restLen = len - read;
                     if (restLen == 1) {
                        headers.remove(keyword);
                     } else {
                        byte[] rest = new byte[restLen];
                        int got = IOUtils.readFully(i, rest);
                        if (got != restLen) {
                           throw new IOException("Failed to read Paxheader. Expected " + restLen + " bytes, read " + got);
                        }

                        String value = new String(rest, 0, restLen - 1, "UTF-8");
                        headers.put(keyword, value);
                     }
                     continue label44;
                  }

                  coll.write((byte)ch);
               }
            }

            len *= 10;
         }
      } while(ch != -1);

      return headers;
   }

   private void applyPaxHeadersToCurrentEntry(Map headers) {
      Iterator var2 = headers.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry ent = (Map.Entry)var2.next();
         String key = (String)ent.getKey();
         String val = (String)ent.getValue();
         if ("path".equals(key)) {
            this.currEntry.setName(val);
         } else if ("linkpath".equals(key)) {
            this.currEntry.setLinkName(val);
         } else if ("gid".equals(key)) {
            this.currEntry.setGroupId(Long.parseLong(val));
         } else if ("gname".equals(key)) {
            this.currEntry.setGroupName(val);
         } else if ("uid".equals(key)) {
            this.currEntry.setUserId(Long.parseLong(val));
         } else if ("uname".equals(key)) {
            this.currEntry.setUserName(val);
         } else if ("size".equals(key)) {
            this.currEntry.setSize(Long.parseLong(val));
         } else if ("mtime".equals(key)) {
            this.currEntry.setModTime((long)(Double.parseDouble(val) * 1000.0));
         } else if ("SCHILY.devminor".equals(key)) {
            this.currEntry.setDevMinor(Integer.parseInt(val));
         } else if ("SCHILY.devmajor".equals(key)) {
            this.currEntry.setDevMajor(Integer.parseInt(val));
         } else if ("GNU.sparse.size".equals(key)) {
            this.currEntry.fillGNUSparse0xData(headers);
         } else if ("GNU.sparse.realsize".equals(key)) {
            this.currEntry.fillGNUSparse1xData(headers);
         } else if ("SCHILY.filetype".equals(key) && "sparse".equals(val)) {
            this.currEntry.fillStarSparseData(headers);
         }
      }

   }

   private void readOldGNUSparse() throws IOException {
      TarArchiveSparseEntry entry;
      if (this.currEntry.isExtended()) {
         do {
            byte[] headerBuf = this.getRecord();
            if (headerBuf == null) {
               this.currEntry = null;
               break;
            }

            entry = new TarArchiveSparseEntry(headerBuf);
         } while(entry.isExtended());
      }

   }

   private boolean isDirectory() {
      return this.currEntry != null && this.currEntry.isDirectory();
   }

   public ArchiveEntry getNextEntry() throws IOException {
      return this.getNextTarEntry();
   }

   private void tryToConsumeSecondEOFRecord() throws IOException {
      boolean shouldReset = true;
      boolean marked = this.is.markSupported();
      if (marked) {
         this.is.mark(this.recordSize);
      }

      try {
         shouldReset = !this.isEOFRecord(this.readRecord());
      } finally {
         if (shouldReset && marked) {
            this.pushedBackBytes((long)this.recordSize);
            this.is.reset();
         }

      }

   }

   public int read(byte[] buf, int offset, int numToRead) throws IOException {
      int totalRead = false;
      if (!this.hasHitEOF && !this.isDirectory() && this.entryOffset < this.entrySize) {
         if (this.currEntry == null) {
            throw new IllegalStateException("No current tar entry");
         } else {
            numToRead = Math.min(numToRead, this.available());
            int totalRead = this.is.read(buf, offset, numToRead);
            if (totalRead == -1) {
               if (numToRead > 0) {
                  throw new IOException("Truncated TAR archive");
               }

               this.hasHitEOF = true;
            } else {
               this.count(totalRead);
               this.entryOffset += (long)totalRead;
            }

            return totalRead;
         }
      } else {
         return -1;
      }
   }

   public boolean canReadEntryData(ArchiveEntry ae) {
      if (ae instanceof TarArchiveEntry) {
         TarArchiveEntry te = (TarArchiveEntry)ae;
         return !te.isSparse();
      } else {
         return false;
      }
   }

   public TarArchiveEntry getCurrentEntry() {
      return this.currEntry;
   }

   protected final void setCurrentEntry(TarArchiveEntry e) {
      this.currEntry = e;
   }

   protected final boolean isAtEOF() {
      return this.hasHitEOF;
   }

   protected final void setAtEOF(boolean b) {
      this.hasHitEOF = b;
   }

   private void consumeRemainderOfLastBlock() throws IOException {
      long bytesReadOfLastBlock = this.getBytesRead() % (long)this.blockSize;
      if (bytesReadOfLastBlock > 0L) {
         long skipped = IOUtils.skip(this.is, (long)this.blockSize - bytesReadOfLastBlock);
         this.count(skipped);
      }

   }

   public static boolean matches(byte[] signature, int length) {
      if (length < 265) {
         return false;
      } else if (ArchiveUtils.matchAsciiBuffer("ustar\u0000", signature, 257, 6) && ArchiveUtils.matchAsciiBuffer("00", signature, 263, 2)) {
         return true;
      } else if (ArchiveUtils.matchAsciiBuffer("ustar ", signature, 257, 6) && (ArchiveUtils.matchAsciiBuffer(" \u0000", signature, 263, 2) || ArchiveUtils.matchAsciiBuffer("0\u0000", signature, 263, 2))) {
         return true;
      } else {
         return ArchiveUtils.matchAsciiBuffer("ustar\u0000", signature, 257, 6) && ArchiveUtils.matchAsciiBuffer("\u0000\u0000", signature, 263, 2);
      }
   }
}
