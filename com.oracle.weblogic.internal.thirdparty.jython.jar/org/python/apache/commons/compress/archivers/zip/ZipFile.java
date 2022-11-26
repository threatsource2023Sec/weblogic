package org.python.apache.commons.compress.archivers.zip;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipException;
import org.python.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.python.apache.commons.compress.utils.IOUtils;

public class ZipFile implements Closeable {
   private static final int HASH_SIZE = 509;
   static final int NIBLET_MASK = 15;
   static final int BYTE_SHIFT = 8;
   private static final int POS_0 = 0;
   private static final int POS_1 = 1;
   private static final int POS_2 = 2;
   private static final int POS_3 = 3;
   private final List entries;
   private final Map nameMap;
   private final String encoding;
   private final ZipEncoding zipEncoding;
   private final String archiveName;
   private final SeekableByteChannel archive;
   private final boolean useUnicodeExtraFields;
   private volatile boolean closed;
   private final byte[] dwordBuf;
   private final byte[] wordBuf;
   private final byte[] cfhBuf;
   private final byte[] shortBuf;
   private final ByteBuffer dwordBbuf;
   private final ByteBuffer wordBbuf;
   private final ByteBuffer cfhBbuf;
   private static final int CFH_LEN = 42;
   private static final long CFH_SIG;
   static final int MIN_EOCD_SIZE = 22;
   private static final int MAX_EOCD_SIZE = 65557;
   private static final int CFD_LOCATOR_OFFSET = 16;
   private static final int ZIP64_EOCDL_LENGTH = 20;
   private static final int ZIP64_EOCDL_LOCATOR_OFFSET = 8;
   private static final int ZIP64_EOCD_CFD_LOCATOR_OFFSET = 48;
   private static final long LFH_OFFSET_FOR_FILENAME_LENGTH = 26L;
   private final Comparator offsetComparator;

   public ZipFile(File f) throws IOException {
      this(f, "UTF8");
   }

   public ZipFile(String name) throws IOException {
      this(new File(name), "UTF8");
   }

   public ZipFile(String name, String encoding) throws IOException {
      this(new File(name), encoding, true);
   }

   public ZipFile(File f, String encoding) throws IOException {
      this(f, encoding, true);
   }

   public ZipFile(File f, String encoding, boolean useUnicodeExtraFields) throws IOException {
      this(Files.newByteChannel(f.toPath(), EnumSet.of(StandardOpenOption.READ)), f.getAbsolutePath(), encoding, useUnicodeExtraFields, true);
   }

   public ZipFile(SeekableByteChannel channel) throws IOException {
      this(channel, "unknown archive", "UTF8", true);
   }

   public ZipFile(SeekableByteChannel channel, String encoding) throws IOException {
      this(channel, "unknown archive", encoding, true);
   }

   public ZipFile(SeekableByteChannel channel, String archiveName, String encoding, boolean useUnicodeExtraFields) throws IOException {
      this(channel, archiveName, encoding, useUnicodeExtraFields, false);
   }

   private ZipFile(SeekableByteChannel channel, String archiveName, String encoding, boolean useUnicodeExtraFields, boolean closeOnError) throws IOException {
      this.entries = new LinkedList();
      this.nameMap = new HashMap(509);
      this.closed = true;
      this.dwordBuf = new byte[8];
      this.wordBuf = new byte[4];
      this.cfhBuf = new byte[42];
      this.shortBuf = new byte[2];
      this.dwordBbuf = ByteBuffer.wrap(this.dwordBuf);
      this.wordBbuf = ByteBuffer.wrap(this.wordBuf);
      this.cfhBbuf = ByteBuffer.wrap(this.cfhBuf);
      this.offsetComparator = new Comparator() {
         public int compare(ZipArchiveEntry e1, ZipArchiveEntry e2) {
            if (e1 == e2) {
               return 0;
            } else {
               Entry ent1 = e1 instanceof Entry ? (Entry)e1 : null;
               Entry ent2 = e2 instanceof Entry ? (Entry)e2 : null;
               if (ent1 == null) {
                  return 1;
               } else if (ent2 == null) {
                  return -1;
               } else {
                  long val = ent1.getLocalHeaderOffset() - ent2.getLocalHeaderOffset();
                  return val == 0L ? 0 : (val < 0L ? -1 : 1);
               }
            }
         }
      };
      this.archiveName = archiveName;
      this.encoding = encoding;
      this.zipEncoding = ZipEncodingHelper.getZipEncoding(encoding);
      this.useUnicodeExtraFields = useUnicodeExtraFields;
      this.archive = channel;
      boolean success = false;

      try {
         Map entriesWithoutUTF8Flag = this.populateFromCentralDirectory();
         this.resolveLocalFileHeaderData(entriesWithoutUTF8Flag);
         success = true;
      } finally {
         this.closed = !success;
         if (!success && closeOnError) {
            IOUtils.closeQuietly(this.archive);
         }

      }

   }

   public String getEncoding() {
      return this.encoding;
   }

   public void close() throws IOException {
      this.closed = true;
      this.archive.close();
   }

   public static void closeQuietly(ZipFile zipfile) {
      IOUtils.closeQuietly(zipfile);
   }

   public Enumeration getEntries() {
      return Collections.enumeration(this.entries);
   }

   public Enumeration getEntriesInPhysicalOrder() {
      ZipArchiveEntry[] allEntries = (ZipArchiveEntry[])this.entries.toArray(new ZipArchiveEntry[this.entries.size()]);
      Arrays.sort(allEntries, this.offsetComparator);
      return Collections.enumeration(Arrays.asList(allEntries));
   }

   public ZipArchiveEntry getEntry(String name) {
      LinkedList entriesOfThatName = (LinkedList)this.nameMap.get(name);
      return entriesOfThatName != null ? (ZipArchiveEntry)entriesOfThatName.getFirst() : null;
   }

   public Iterable getEntries(String name) {
      List entriesOfThatName = (List)this.nameMap.get(name);
      return entriesOfThatName != null ? entriesOfThatName : Collections.emptyList();
   }

   public Iterable getEntriesInPhysicalOrder(String name) {
      ZipArchiveEntry[] entriesOfThatName = new ZipArchiveEntry[0];
      if (this.nameMap.containsKey(name)) {
         entriesOfThatName = (ZipArchiveEntry[])((LinkedList)this.nameMap.get(name)).toArray(entriesOfThatName);
         Arrays.sort(entriesOfThatName, this.offsetComparator);
      }

      return Arrays.asList(entriesOfThatName);
   }

   public boolean canReadEntryData(ZipArchiveEntry ze) {
      return ZipUtil.canHandleEntryData(ze);
   }

   public InputStream getRawInputStream(ZipArchiveEntry ze) {
      if (!(ze instanceof Entry)) {
         return null;
      } else {
         long start = ze.getDataOffset();
         return this.createBoundedInputStream(start, ze.getCompressedSize());
      }
   }

   public void copyRawEntries(ZipArchiveOutputStream target, ZipArchiveEntryPredicate predicate) throws IOException {
      Enumeration src = this.getEntriesInPhysicalOrder();

      while(src.hasMoreElements()) {
         ZipArchiveEntry entry = (ZipArchiveEntry)src.nextElement();
         if (predicate.test(entry)) {
            target.addRawArchiveEntry(entry, this.getRawInputStream(entry));
         }
      }

   }

   public InputStream getInputStream(ZipArchiveEntry ze) throws IOException, ZipException {
      if (!(ze instanceof Entry)) {
         return null;
      } else {
         ZipUtil.checkRequestedFeatures(ze);
         long start = ze.getDataOffset();
         BoundedInputStream bis = this.createBoundedInputStream(start, ze.getCompressedSize());
         switch (ZipMethod.getMethodByCode(ze.getMethod())) {
            case STORED:
               return bis;
            case UNSHRINKING:
               return new UnshrinkingInputStream(bis);
            case IMPLODING:
               return new ExplodingInputStream(ze.getGeneralPurposeBit().getSlidingDictionarySize(), ze.getGeneralPurposeBit().getNumberOfShannonFanoTrees(), new BufferedInputStream(bis));
            case DEFLATED:
               bis.addDummy();
               final Inflater inflater = new Inflater(true);
               return new InflaterInputStream(bis, inflater) {
                  public void close() throws IOException {
                     try {
                        super.close();
                     } finally {
                        inflater.end();
                     }

                  }
               };
            case BZIP2:
               return new BZip2CompressorInputStream(bis);
            case AES_ENCRYPTED:
            case ENHANCED_DEFLATED:
            case EXPANDING_LEVEL_1:
            case EXPANDING_LEVEL_2:
            case EXPANDING_LEVEL_3:
            case EXPANDING_LEVEL_4:
            case JPEG:
            case LZMA:
            case PKWARE_IMPLODING:
            case PPMD:
            case TOKENIZATION:
            case UNKNOWN:
            case WAVPACK:
            default:
               throw new ZipException("Found unsupported compression method " + ze.getMethod());
         }
      }
   }

   public String getUnixSymlink(ZipArchiveEntry entry) throws IOException {
      if (entry != null && entry.isUnixSymlink()) {
         InputStream in = this.getInputStream(entry);
         Throwable var3 = null;

         String var4;
         try {
            var4 = this.zipEncoding.decode(IOUtils.toByteArray(in));
         } catch (Throwable var13) {
            var3 = var13;
            throw var13;
         } finally {
            if (in != null) {
               if (var3 != null) {
                  try {
                     in.close();
                  } catch (Throwable var12) {
                     var3.addSuppressed(var12);
                  }
               } else {
                  in.close();
               }
            }

         }

         return var4;
      } else {
         return null;
      }
   }

   protected void finalize() throws Throwable {
      try {
         if (!this.closed) {
            System.err.println("Cleaning up unclosed ZipFile for archive " + this.archiveName);
            this.close();
         }
      } finally {
         super.finalize();
      }

   }

   private Map populateFromCentralDirectory() throws IOException {
      HashMap noUTF8Flag = new HashMap();
      this.positionAtCentralDirectory();
      this.wordBbuf.rewind();
      IOUtils.readFully((ReadableByteChannel)this.archive, (ByteBuffer)this.wordBbuf);
      long sig = ZipLong.getValue(this.wordBuf);
      if (sig != CFH_SIG && this.startsWithLocalFileHeader()) {
         throw new IOException("central directory is empty, can't expand corrupt archive.");
      } else {
         while(sig == CFH_SIG) {
            this.readCentralDirectoryEntry(noUTF8Flag);
            this.wordBbuf.rewind();
            IOUtils.readFully((ReadableByteChannel)this.archive, (ByteBuffer)this.wordBbuf);
            sig = ZipLong.getValue(this.wordBuf);
         }

         return noUTF8Flag;
      }
   }

   private void readCentralDirectoryEntry(Map noUTF8Flag) throws IOException {
      this.cfhBbuf.rewind();
      IOUtils.readFully((ReadableByteChannel)this.archive, (ByteBuffer)this.cfhBbuf);
      int off = 0;
      Entry ze = new Entry();
      int versionMadeBy = ZipShort.getValue(this.cfhBuf, off);
      off += 2;
      ze.setVersionMadeBy(versionMadeBy);
      ze.setPlatform(versionMadeBy >> 8 & 15);
      ze.setVersionRequired(ZipShort.getValue(this.cfhBuf, off));
      off += 2;
      GeneralPurposeBit gpFlag = GeneralPurposeBit.parse(this.cfhBuf, off);
      boolean hasUTF8Flag = gpFlag.usesUTF8ForNames();
      ZipEncoding entryEncoding = hasUTF8Flag ? ZipEncodingHelper.UTF8_ZIP_ENCODING : this.zipEncoding;
      ze.setGeneralPurposeBit(gpFlag);
      ze.setRawFlag(ZipShort.getValue(this.cfhBuf, off));
      off += 2;
      ze.setMethod(ZipShort.getValue(this.cfhBuf, off));
      off += 2;
      long time = ZipUtil.dosToJavaTime(ZipLong.getValue(this.cfhBuf, off));
      ze.setTime(time);
      off += 4;
      ze.setCrc(ZipLong.getValue(this.cfhBuf, off));
      off += 4;
      ze.setCompressedSize(ZipLong.getValue(this.cfhBuf, off));
      off += 4;
      ze.setSize(ZipLong.getValue(this.cfhBuf, off));
      off += 4;
      int fileNameLen = ZipShort.getValue(this.cfhBuf, off);
      off += 2;
      int extraLen = ZipShort.getValue(this.cfhBuf, off);
      off += 2;
      int commentLen = ZipShort.getValue(this.cfhBuf, off);
      off += 2;
      int diskStart = ZipShort.getValue(this.cfhBuf, off);
      off += 2;
      ze.setInternalAttributes(ZipShort.getValue(this.cfhBuf, off));
      off += 2;
      ze.setExternalAttributes(ZipLong.getValue(this.cfhBuf, off));
      off += 4;
      byte[] fileName = new byte[fileNameLen];
      IOUtils.readFully((ReadableByteChannel)this.archive, (ByteBuffer)ByteBuffer.wrap(fileName));
      ze.setName(entryEncoding.decode(fileName), fileName);
      ze.setLocalHeaderOffset(ZipLong.getValue(this.cfhBuf, off));
      this.entries.add(ze);
      byte[] cdExtraData = new byte[extraLen];
      IOUtils.readFully((ReadableByteChannel)this.archive, (ByteBuffer)ByteBuffer.wrap(cdExtraData));
      ze.setCentralDirectoryExtra(cdExtraData);
      this.setSizesAndOffsetFromZip64Extra(ze, diskStart);
      byte[] comment = new byte[commentLen];
      IOUtils.readFully((ReadableByteChannel)this.archive, (ByteBuffer)ByteBuffer.wrap(comment));
      ze.setComment(entryEncoding.decode(comment));
      if (!hasUTF8Flag && this.useUnicodeExtraFields) {
         noUTF8Flag.put(ze, new NameAndComment(fileName, comment));
      }

   }

   private void setSizesAndOffsetFromZip64Extra(ZipArchiveEntry ze, int diskStart) throws IOException {
      Zip64ExtendedInformationExtraField z64 = (Zip64ExtendedInformationExtraField)ze.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
      if (z64 != null) {
         boolean hasUncompressedSize = ze.getSize() == 4294967295L;
         boolean hasCompressedSize = ze.getCompressedSize() == 4294967295L;
         boolean hasRelativeHeaderOffset = ze.getLocalHeaderOffset() == 4294967295L;
         z64.reparseCentralDirectoryData(hasUncompressedSize, hasCompressedSize, hasRelativeHeaderOffset, diskStart == 65535);
         if (hasUncompressedSize) {
            ze.setSize(z64.getSize().getLongValue());
         } else if (hasCompressedSize) {
            z64.setSize(new ZipEightByteInteger(ze.getSize()));
         }

         if (hasCompressedSize) {
            ze.setCompressedSize(z64.getCompressedSize().getLongValue());
         } else if (hasUncompressedSize) {
            z64.setCompressedSize(new ZipEightByteInteger(ze.getCompressedSize()));
         }

         if (hasRelativeHeaderOffset) {
            ze.setLocalHeaderOffset(z64.getRelativeHeaderOffset().getLongValue());
         }
      }

   }

   private void positionAtCentralDirectory() throws IOException {
      this.positionAtEndOfCentralDirectoryRecord();
      boolean found = false;
      boolean searchedForZip64EOCD = this.archive.position() > 20L;
      if (searchedForZip64EOCD) {
         this.archive.position(this.archive.position() - 20L);
         this.wordBbuf.rewind();
         IOUtils.readFully((ReadableByteChannel)this.archive, (ByteBuffer)this.wordBbuf);
         found = Arrays.equals(ZipArchiveOutputStream.ZIP64_EOCD_LOC_SIG, this.wordBuf);
      }

      if (!found) {
         if (searchedForZip64EOCD) {
            this.skipBytes(16);
         }

         this.positionAtCentralDirectory32();
      } else {
         this.positionAtCentralDirectory64();
      }

   }

   private void positionAtCentralDirectory64() throws IOException {
      this.skipBytes(4);
      this.dwordBbuf.rewind();
      IOUtils.readFully((ReadableByteChannel)this.archive, (ByteBuffer)this.dwordBbuf);
      this.archive.position(ZipEightByteInteger.getLongValue(this.dwordBuf));
      this.wordBbuf.rewind();
      IOUtils.readFully((ReadableByteChannel)this.archive, (ByteBuffer)this.wordBbuf);
      if (!Arrays.equals(this.wordBuf, ZipArchiveOutputStream.ZIP64_EOCD_SIG)) {
         throw new ZipException("archive's ZIP64 end of central directory locator is corrupt.");
      } else {
         this.skipBytes(44);
         this.dwordBbuf.rewind();
         IOUtils.readFully((ReadableByteChannel)this.archive, (ByteBuffer)this.dwordBbuf);
         this.archive.position(ZipEightByteInteger.getLongValue(this.dwordBuf));
      }
   }

   private void positionAtCentralDirectory32() throws IOException {
      this.skipBytes(16);
      this.wordBbuf.rewind();
      IOUtils.readFully((ReadableByteChannel)this.archive, (ByteBuffer)this.wordBbuf);
      this.archive.position(ZipLong.getValue(this.wordBuf));
   }

   private void positionAtEndOfCentralDirectoryRecord() throws IOException {
      boolean found = this.tryToLocateSignature(22L, 65557L, ZipArchiveOutputStream.EOCD_SIG);
      if (!found) {
         throw new ZipException("archive is not a ZIP archive");
      }
   }

   private boolean tryToLocateSignature(long minDistanceFromEnd, long maxDistanceFromEnd, byte[] sig) throws IOException {
      boolean found = false;
      long off = this.archive.size() - minDistanceFromEnd;
      long stopSearching = Math.max(0L, this.archive.size() - maxDistanceFromEnd);
      if (off >= 0L) {
         for(; off >= stopSearching; --off) {
            this.archive.position(off);

            try {
               this.wordBbuf.rewind();
               IOUtils.readFully((ReadableByteChannel)this.archive, (ByteBuffer)this.wordBbuf);
               this.wordBbuf.flip();
            } catch (EOFException var12) {
               break;
            }

            int curr = this.wordBbuf.get();
            if (curr == sig[0]) {
               curr = this.wordBbuf.get();
               if (curr == sig[1]) {
                  curr = this.wordBbuf.get();
                  if (curr == sig[2]) {
                     curr = this.wordBbuf.get();
                     if (curr == sig[3]) {
                        found = true;
                        break;
                     }
                  }
               }
            }
         }
      }

      if (found) {
         this.archive.position(off);
      }

      return found;
   }

   private void skipBytes(int count) throws IOException {
      long currentPosition = this.archive.position();
      long newPosition = currentPosition + (long)count;
      if (newPosition > this.archive.size()) {
         throw new EOFException();
      } else {
         this.archive.position(newPosition);
      }
   }

   private void resolveLocalFileHeaderData(Map entriesWithoutUTF8Flag) throws IOException {
      Entry ze;
      LinkedList entriesOfThatName;
      for(Iterator var2 = this.entries.iterator(); var2.hasNext(); entriesOfThatName.addLast(ze)) {
         ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry)var2.next();
         ze = (Entry)zipArchiveEntry;
         long offset = ze.getLocalHeaderOffset();
         this.archive.position(offset + 26L);
         this.wordBbuf.rewind();
         IOUtils.readFully((ReadableByteChannel)this.archive, (ByteBuffer)this.wordBbuf);
         this.wordBbuf.flip();
         this.wordBbuf.get(this.shortBuf);
         int fileNameLen = ZipShort.getValue(this.shortBuf);
         this.wordBbuf.get(this.shortBuf);
         int extraFieldLen = ZipShort.getValue(this.shortBuf);
         this.skipBytes(fileNameLen);
         byte[] localExtraData = new byte[extraFieldLen];
         IOUtils.readFully((ReadableByteChannel)this.archive, (ByteBuffer)ByteBuffer.wrap(localExtraData));
         ze.setExtra(localExtraData);
         ze.setDataOffset(offset + 26L + 2L + 2L + (long)fileNameLen + (long)extraFieldLen);
         ze.setStreamContiguous(true);
         if (entriesWithoutUTF8Flag.containsKey(ze)) {
            NameAndComment nc = (NameAndComment)entriesWithoutUTF8Flag.get(ze);
            ZipUtil.setNameAndCommentFromExtraFields(ze, nc.name, nc.comment);
         }

         String name = ze.getName();
         entriesOfThatName = (LinkedList)this.nameMap.get(name);
         if (entriesOfThatName == null) {
            entriesOfThatName = new LinkedList();
            this.nameMap.put(name, entriesOfThatName);
         }
      }

   }

   private boolean startsWithLocalFileHeader() throws IOException {
      this.archive.position(0L);
      this.wordBbuf.rewind();
      IOUtils.readFully((ReadableByteChannel)this.archive, (ByteBuffer)this.wordBbuf);
      return Arrays.equals(this.wordBuf, ZipArchiveOutputStream.LFH_SIG);
   }

   private BoundedInputStream createBoundedInputStream(long start, long remaining) {
      return (BoundedInputStream)(this.archive instanceof FileChannel ? new BoundedFileChannelInputStream(start, remaining) : new BoundedInputStream(start, remaining));
   }

   static {
      CFH_SIG = ZipLong.getValue(ZipArchiveOutputStream.CFH_SIG);
   }

   private static class Entry extends ZipArchiveEntry {
      Entry() {
      }

      public int hashCode() {
         return 3 * super.hashCode() + (int)this.getLocalHeaderOffset() + (int)(this.getLocalHeaderOffset() >> 32);
      }

      public boolean equals(Object other) {
         if (!super.equals(other)) {
            return false;
         } else {
            Entry otherEntry = (Entry)other;
            return this.getLocalHeaderOffset() == otherEntry.getLocalHeaderOffset() && this.getDataOffset() == otherEntry.getDataOffset();
         }
      }
   }

   private static final class NameAndComment {
      private final byte[] name;
      private final byte[] comment;

      private NameAndComment(byte[] name, byte[] comment) {
         this.name = name;
         this.comment = comment;
      }

      // $FF: synthetic method
      NameAndComment(byte[] x0, byte[] x1, Object x2) {
         this(x0, x1);
      }
   }

   private class BoundedFileChannelInputStream extends BoundedInputStream {
      private final FileChannel archive;

      BoundedFileChannelInputStream(long start, long remaining) {
         super(start, remaining);
         this.archive = (FileChannel)ZipFile.this.archive;
      }

      protected int read(long pos, ByteBuffer buf) throws IOException {
         int read = this.archive.read(buf, pos);
         buf.flip();
         return read;
      }
   }

   private class BoundedInputStream extends InputStream {
      private ByteBuffer singleByteBuffer;
      private final long end;
      private long loc;
      private boolean addDummy = false;

      BoundedInputStream(long start, long remaining) {
         this.end = start + remaining;
         if (this.end < start) {
            throw new IllegalArgumentException("Invalid length of stream at offset=" + start + ", length=" + remaining);
         } else {
            this.loc = start;
         }
      }

      public synchronized int read() throws IOException {
         if (this.loc >= this.end) {
            if (this.loc == this.end && this.addDummy) {
               this.addDummy = false;
               return 0;
            } else {
               return -1;
            }
         } else {
            if (this.singleByteBuffer == null) {
               this.singleByteBuffer = ByteBuffer.allocate(1);
            } else {
               this.singleByteBuffer.rewind();
            }

            int read = this.read(this.loc, this.singleByteBuffer);
            if (read < 0) {
               return read;
            } else {
               ++this.loc;
               return this.singleByteBuffer.get() & 255;
            }
         }
      }

      public synchronized int read(byte[] b, int off, int len) throws IOException {
         if (len <= 0) {
            return 0;
         } else {
            if ((long)len > this.end - this.loc) {
               if (this.loc >= this.end) {
                  if (this.loc == this.end && this.addDummy) {
                     this.addDummy = false;
                     b[off] = 0;
                     return 1;
                  }

                  return -1;
               }

               len = (int)(this.end - this.loc);
            }

            ByteBuffer buf = ByteBuffer.wrap(b, off, len);
            int ret = this.read(this.loc, buf);
            if (ret > 0) {
               this.loc += (long)ret;
               return ret;
            } else {
               return ret;
            }
         }
      }

      protected int read(long pos, ByteBuffer buf) throws IOException {
         int read;
         synchronized(ZipFile.this.archive) {
            ZipFile.this.archive.position(pos);
            read = ZipFile.this.archive.read(buf);
         }

         buf.flip();
         return read;
      }

      synchronized void addDummy() {
         this.addDummy = true;
      }
   }
}
