package org.python.apache.commons.compress.archivers.sevenz;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.zip.CRC32;
import org.python.apache.commons.compress.utils.BoundedInputStream;
import org.python.apache.commons.compress.utils.CRC32VerifyingInputStream;
import org.python.apache.commons.compress.utils.IOUtils;

public class SevenZFile implements Closeable {
   static final int SIGNATURE_HEADER_SIZE = 32;
   private final String fileName;
   private SeekableByteChannel channel;
   private final Archive archive;
   private int currentEntryIndex;
   private int currentFolderIndex;
   private InputStream currentFolderInputStream;
   private byte[] password;
   private final ArrayList deferredBlockStreams;
   static final byte[] sevenZSignature = new byte[]{55, 122, -68, -81, 39, 28};

   public SevenZFile(File filename, byte[] password) throws IOException {
      this(Files.newByteChannel(filename.toPath(), EnumSet.of(StandardOpenOption.READ)), filename.getAbsolutePath(), password, true);
   }

   public SevenZFile(SeekableByteChannel channel) throws IOException {
      this(channel, "unknown archive", (byte[])null);
   }

   public SevenZFile(SeekableByteChannel channel, byte[] password) throws IOException {
      this(channel, "unknown archive", password);
   }

   public SevenZFile(SeekableByteChannel channel, String filename, byte[] password) throws IOException {
      this(channel, filename, password, false);
   }

   private SevenZFile(SeekableByteChannel channel, String filename, byte[] password, boolean closeOnError) throws IOException {
      this.currentEntryIndex = -1;
      this.currentFolderIndex = -1;
      this.currentFolderInputStream = null;
      this.deferredBlockStreams = new ArrayList();
      boolean succeeded = false;
      this.channel = channel;
      this.fileName = filename;

      try {
         this.archive = this.readHeaders(password);
         if (password != null) {
            this.password = new byte[password.length];
            System.arraycopy(password, 0, this.password, 0, password.length);
         } else {
            this.password = null;
         }

         succeeded = true;
      } finally {
         if (!succeeded && closeOnError) {
            this.channel.close();
         }

      }

   }

   public SevenZFile(File filename) throws IOException {
      this((File)filename, (byte[])null);
   }

   public void close() throws IOException {
      if (this.channel != null) {
         try {
            this.channel.close();
         } finally {
            this.channel = null;
            if (this.password != null) {
               Arrays.fill(this.password, (byte)0);
            }

            this.password = null;
         }
      }

   }

   public SevenZArchiveEntry getNextEntry() throws IOException {
      if (this.currentEntryIndex >= this.archive.files.length - 1) {
         return null;
      } else {
         ++this.currentEntryIndex;
         SevenZArchiveEntry entry = this.archive.files[this.currentEntryIndex];
         this.buildDecodingStream();
         return entry;
      }
   }

   public Iterable getEntries() {
      return Arrays.asList(this.archive.files);
   }

   private Archive readHeaders(byte[] password) throws IOException {
      ByteBuffer buf = ByteBuffer.allocate(12).order(ByteOrder.LITTLE_ENDIAN);
      this.readFully(buf);
      byte[] signature = new byte[6];
      buf.get(signature);
      if (!Arrays.equals(signature, sevenZSignature)) {
         throw new IOException("Bad 7z signature");
      } else {
         byte archiveVersionMajor = buf.get();
         byte archiveVersionMinor = buf.get();
         if (archiveVersionMajor != 0) {
            throw new IOException(String.format("Unsupported 7z version (%d,%d)", archiveVersionMajor, archiveVersionMinor));
         } else {
            long startHeaderCrc = 4294967295L & (long)buf.getInt();
            StartHeader startHeader = this.readStartHeader(startHeaderCrc);
            int nextHeaderSizeInt = (int)startHeader.nextHeaderSize;
            if ((long)nextHeaderSizeInt != startHeader.nextHeaderSize) {
               throw new IOException("cannot handle nextHeaderSize " + startHeader.nextHeaderSize);
            } else {
               this.channel.position(32L + startHeader.nextHeaderOffset);
               buf = ByteBuffer.allocate(nextHeaderSizeInt).order(ByteOrder.LITTLE_ENDIAN);
               this.readFully(buf);
               CRC32 crc = new CRC32();
               crc.update(buf.array());
               if (startHeader.nextHeaderCrc != crc.getValue()) {
                  throw new IOException("NextHeader CRC mismatch");
               } else {
                  Archive archive = new Archive();
                  int nid = getUnsignedByte(buf);
                  if (nid == 23) {
                     buf = this.readEncodedHeader(buf, archive, password);
                     archive = new Archive();
                     nid = getUnsignedByte(buf);
                  }

                  if (nid == 1) {
                     this.readHeader(buf, archive);
                     return archive;
                  } else {
                     throw new IOException("Broken or unsupported archive: no Header");
                  }
               }
            }
         }
      }
   }

   private StartHeader readStartHeader(long startHeaderCrc) throws IOException {
      StartHeader startHeader = new StartHeader();
      DataInputStream dataInputStream = new DataInputStream(new CRC32VerifyingInputStream(new BoundedSeekableByteChannelInputStream(this.channel, 20L), 20L, startHeaderCrc));
      Throwable var5 = null;

      StartHeader var6;
      try {
         startHeader.nextHeaderOffset = Long.reverseBytes(dataInputStream.readLong());
         startHeader.nextHeaderSize = Long.reverseBytes(dataInputStream.readLong());
         startHeader.nextHeaderCrc = 4294967295L & (long)Integer.reverseBytes(dataInputStream.readInt());
         var6 = startHeader;
      } catch (Throwable var15) {
         var5 = var15;
         throw var15;
      } finally {
         if (dataInputStream != null) {
            if (var5 != null) {
               try {
                  dataInputStream.close();
               } catch (Throwable var14) {
                  var5.addSuppressed(var14);
               }
            } else {
               dataInputStream.close();
            }
         }

      }

      return var6;
   }

   private void readHeader(ByteBuffer header, Archive archive) throws IOException {
      int nid = getUnsignedByte(header);
      if (nid == 2) {
         this.readArchiveProperties(header);
         nid = getUnsignedByte(header);
      }

      if (nid == 3) {
         throw new IOException("Additional streams unsupported");
      } else {
         if (nid == 4) {
            this.readStreamsInfo(header, archive);
            nid = getUnsignedByte(header);
         }

         if (nid == 5) {
            this.readFilesInfo(header, archive);
            nid = getUnsignedByte(header);
         }

         if (nid != 0) {
            throw new IOException("Badly terminated header, found " + nid);
         }
      }
   }

   private void readArchiveProperties(ByteBuffer input) throws IOException {
      for(int nid = getUnsignedByte(input); nid != 0; nid = getUnsignedByte(input)) {
         long propertySize = readUint64(input);
         byte[] property = new byte[(int)propertySize];
         input.get(property);
      }

   }

   private ByteBuffer readEncodedHeader(ByteBuffer header, Archive archive, byte[] password) throws IOException {
      this.readStreamsInfo(header, archive);
      Folder folder = archive.folders[0];
      int firstPackStreamIndex = false;
      long folderOffset = 32L + archive.packPos + 0L;
      this.channel.position(folderOffset);
      InputStream inputStreamStack = new BoundedSeekableByteChannelInputStream(this.channel, archive.packSizes[0]);

      Coder coder;
      for(Iterator var9 = folder.getOrderedCoders().iterator(); var9.hasNext(); inputStreamStack = Coders.addDecoder(this.fileName, (InputStream)inputStreamStack, folder.getUnpackSizeForCoder(coder), coder, password)) {
         coder = (Coder)var9.next();
         if (coder.numInStreams != 1L || coder.numOutStreams != 1L) {
            throw new IOException("Multi input/output stream coders are not yet supported");
         }
      }

      if (folder.hasCrc) {
         inputStreamStack = new CRC32VerifyingInputStream((InputStream)inputStreamStack, folder.getUnpackSize(), folder.crc);
      }

      byte[] nextHeader = new byte[(int)folder.getUnpackSize()];
      DataInputStream nextHeaderInputStream = new DataInputStream((InputStream)inputStreamStack);
      Throwable var11 = null;

      try {
         nextHeaderInputStream.readFully(nextHeader);
      } catch (Throwable var20) {
         var11 = var20;
         throw var20;
      } finally {
         if (nextHeaderInputStream != null) {
            if (var11 != null) {
               try {
                  nextHeaderInputStream.close();
               } catch (Throwable var19) {
                  var11.addSuppressed(var19);
               }
            } else {
               nextHeaderInputStream.close();
            }
         }

      }

      return ByteBuffer.wrap(nextHeader).order(ByteOrder.LITTLE_ENDIAN);
   }

   private void readStreamsInfo(ByteBuffer header, Archive archive) throws IOException {
      int nid = getUnsignedByte(header);
      if (nid == 6) {
         this.readPackInfo(header, archive);
         nid = getUnsignedByte(header);
      }

      if (nid == 7) {
         this.readUnpackInfo(header, archive);
         nid = getUnsignedByte(header);
      } else {
         archive.folders = new Folder[0];
      }

      if (nid == 8) {
         this.readSubStreamsInfo(header, archive);
         nid = getUnsignedByte(header);
      }

      if (nid != 0) {
         throw new IOException("Badly terminated StreamsInfo");
      }
   }

   private void readPackInfo(ByteBuffer header, Archive archive) throws IOException {
      archive.packPos = readUint64(header);
      long numPackStreams = readUint64(header);
      int nid = getUnsignedByte(header);
      int i;
      if (nid == 9) {
         archive.packSizes = new long[(int)numPackStreams];

         for(i = 0; i < archive.packSizes.length; ++i) {
            archive.packSizes[i] = readUint64(header);
         }

         nid = getUnsignedByte(header);
      }

      if (nid == 10) {
         archive.packCrcsDefined = this.readAllOrBits(header, (int)numPackStreams);
         archive.packCrcs = new long[(int)numPackStreams];

         for(i = 0; i < (int)numPackStreams; ++i) {
            if (archive.packCrcsDefined.get(i)) {
               archive.packCrcs[i] = 4294967295L & (long)header.getInt();
            }
         }

         nid = getUnsignedByte(header);
      }

      if (nid != 0) {
         throw new IOException("Badly terminated PackInfo (" + nid + ")");
      }
   }

   private void readUnpackInfo(ByteBuffer header, Archive archive) throws IOException {
      int nid = getUnsignedByte(header);
      if (nid != 11) {
         throw new IOException("Expected kFolder, got " + nid);
      } else {
         long numFolders = readUint64(header);
         Folder[] folders = new Folder[(int)numFolders];
         archive.folders = folders;
         int external = getUnsignedByte(header);
         if (external != 0) {
            throw new IOException("External unsupported");
         } else {
            for(int i = 0; i < (int)numFolders; ++i) {
               folders[i] = this.readFolder(header);
            }

            nid = getUnsignedByte(header);
            if (nid != 12) {
               throw new IOException("Expected kCodersUnpackSize, got " + nid);
            } else {
               Folder[] var13 = folders;
               int i = folders.length;

               for(int var10 = 0; var10 < i; ++var10) {
                  Folder folder = var13[var10];
                  folder.unpackSizes = new long[(int)folder.totalOutputStreams];

                  for(int i = 0; (long)i < folder.totalOutputStreams; ++i) {
                     folder.unpackSizes[i] = readUint64(header);
                  }
               }

               nid = getUnsignedByte(header);
               if (nid == 10) {
                  BitSet crcsDefined = this.readAllOrBits(header, (int)numFolders);

                  for(i = 0; i < (int)numFolders; ++i) {
                     if (crcsDefined.get(i)) {
                        folders[i].hasCrc = true;
                        folders[i].crc = 4294967295L & (long)header.getInt();
                     } else {
                        folders[i].hasCrc = false;
                     }
                  }

                  nid = getUnsignedByte(header);
               }

               if (nid != 0) {
                  throw new IOException("Badly terminated UnpackInfo");
               }
            }
         }
      }
   }

   private void readSubStreamsInfo(ByteBuffer header, Archive archive) throws IOException {
      Folder[] var3 = archive.folders;
      int nid = var3.length;

      for(int var5 = 0; var5 < nid; ++var5) {
         Folder folder = var3[var5];
         folder.numUnpackSubStreams = 1;
      }

      int totalUnpackStreams = archive.folders.length;
      nid = getUnsignedByte(header);
      int numDigests;
      int nextUnpackStream;
      if (nid == 13) {
         totalUnpackStreams = 0;
         Folder[] var24 = archive.folders;
         nextUnpackStream = var24.length;

         for(numDigests = 0; numDigests < nextUnpackStream; ++numDigests) {
            Folder folder = var24[numDigests];
            long numStreams = readUint64(header);
            folder.numUnpackSubStreams = (int)numStreams;
            totalUnpackStreams = (int)((long)totalUnpackStreams + numStreams);
         }

         nid = getUnsignedByte(header);
      }

      SubStreamsInfo subStreamsInfo = new SubStreamsInfo();
      subStreamsInfo.unpackSizes = new long[totalUnpackStreams];
      subStreamsInfo.hasCrc = new BitSet(totalUnpackStreams);
      subStreamsInfo.crcs = new long[totalUnpackStreams];
      nextUnpackStream = 0;
      Folder[] var27 = archive.folders;
      int var28 = var27.length;

      int var11;
      int i;
      for(var11 = 0; var11 < var28; ++var11) {
         Folder folder = var27[var11];
         if (folder.numUnpackSubStreams != 0) {
            long sum = 0L;
            if (nid == 9) {
               for(i = 0; i < folder.numUnpackSubStreams - 1; ++i) {
                  long size = readUint64(header);
                  subStreamsInfo.unpackSizes[nextUnpackStream++] = size;
                  sum += size;
               }
            }

            subStreamsInfo.unpackSizes[nextUnpackStream++] = folder.getUnpackSize() - sum;
         }
      }

      if (nid == 9) {
         nid = getUnsignedByte(header);
      }

      numDigests = 0;
      Folder[] var29 = archive.folders;
      var11 = var29.length;

      int nextCrc;
      for(nextCrc = 0; nextCrc < var11; ++nextCrc) {
         Folder folder = var29[nextCrc];
         if (folder.numUnpackSubStreams != 1 || !folder.hasCrc) {
            numDigests += folder.numUnpackSubStreams;
         }
      }

      if (nid == 10) {
         BitSet hasMissingCrc = this.readAllOrBits(header, numDigests);
         long[] missingCrcs = new long[numDigests];

         for(nextCrc = 0; nextCrc < numDigests; ++nextCrc) {
            if (hasMissingCrc.get(nextCrc)) {
               missingCrcs[nextCrc] = 4294967295L & (long)header.getInt();
            }
         }

         nextCrc = 0;
         int nextMissingCrc = 0;
         Folder[] var19 = archive.folders;
         i = var19.length;

         for(int var20 = 0; var20 < i; ++var20) {
            Folder folder = var19[var20];
            if (folder.numUnpackSubStreams == 1 && folder.hasCrc) {
               subStreamsInfo.hasCrc.set(nextCrc, true);
               subStreamsInfo.crcs[nextCrc] = folder.crc;
               ++nextCrc;
            } else {
               for(int i = 0; i < folder.numUnpackSubStreams; ++i) {
                  subStreamsInfo.hasCrc.set(nextCrc, hasMissingCrc.get(nextMissingCrc));
                  subStreamsInfo.crcs[nextCrc] = missingCrcs[nextMissingCrc];
                  ++nextCrc;
                  ++nextMissingCrc;
               }
            }
         }

         nid = getUnsignedByte(header);
      }

      if (nid != 0) {
         throw new IOException("Badly terminated SubStreamsInfo");
      } else {
         archive.subStreamsInfo = subStreamsInfo;
      }
   }

   private Folder readFolder(ByteBuffer header) throws IOException {
      Folder folder = new Folder();
      long numCoders = readUint64(header);
      Coder[] coders = new Coder[(int)numCoders];
      long totalInStreams = 0L;
      long totalOutStreams = 0L;

      for(int i = 0; i < coders.length; ++i) {
         coders[i] = new Coder();
         int bits = getUnsignedByte(header);
         int idSize = bits & 15;
         boolean isSimple = (bits & 16) == 0;
         boolean hasAttributes = (bits & 32) != 0;
         boolean moreAlternativeMethods = (bits & 128) != 0;
         coders[i].decompressionMethodId = new byte[idSize];
         header.get(coders[i].decompressionMethodId);
         if (isSimple) {
            coders[i].numInStreams = 1L;
            coders[i].numOutStreams = 1L;
         } else {
            coders[i].numInStreams = readUint64(header);
            coders[i].numOutStreams = readUint64(header);
         }

         totalInStreams += coders[i].numInStreams;
         totalOutStreams += coders[i].numOutStreams;
         if (hasAttributes) {
            long propertiesSize = readUint64(header);
            coders[i].properties = new byte[(int)propertiesSize];
            header.get(coders[i].properties);
         }

         if (moreAlternativeMethods) {
            throw new IOException("Alternative methods are unsupported, please report. The reference implementation doesn't support them either.");
         }
      }

      folder.coders = coders;
      folder.totalInputStreams = totalInStreams;
      folder.totalOutputStreams = totalOutStreams;
      if (totalOutStreams == 0L) {
         throw new IOException("Total output streams can't be 0");
      } else {
         long numBindPairs = totalOutStreams - 1L;
         BindPair[] bindPairs = new BindPair[(int)numBindPairs];

         for(int i = 0; i < bindPairs.length; ++i) {
            bindPairs[i] = new BindPair();
            bindPairs[i].inIndex = readUint64(header);
            bindPairs[i].outIndex = readUint64(header);
         }

         folder.bindPairs = bindPairs;
         if (totalInStreams < numBindPairs) {
            throw new IOException("Total input streams can't be less than the number of bind pairs");
         } else {
            long numPackedStreams = totalInStreams - numBindPairs;
            long[] packedStreams = new long[(int)numPackedStreams];
            int i;
            if (numPackedStreams == 1L) {
               for(i = 0; i < (int)totalInStreams && folder.findBindPairForInStream(i) >= 0; ++i) {
               }

               if (i == (int)totalInStreams) {
                  throw new IOException("Couldn't find stream's bind pair index");
               }

               packedStreams[0] = (long)i;
            } else {
               for(i = 0; i < (int)numPackedStreams; ++i) {
                  packedStreams[i] = readUint64(header);
               }
            }

            folder.packedStreams = packedStreams;
            return folder;
         }
      }
   }

   private BitSet readAllOrBits(ByteBuffer header, int size) throws IOException {
      int areAllDefined = getUnsignedByte(header);
      BitSet bits;
      if (areAllDefined != 0) {
         bits = new BitSet(size);

         for(int i = 0; i < size; ++i) {
            bits.set(i, true);
         }
      } else {
         bits = this.readBits(header, size);
      }

      return bits;
   }

   private BitSet readBits(ByteBuffer header, int size) throws IOException {
      BitSet bits = new BitSet(size);
      int mask = 0;
      int cache = 0;

      for(int i = 0; i < size; ++i) {
         if (mask == 0) {
            mask = 128;
            cache = getUnsignedByte(header);
         }

         bits.set(i, (cache & mask) != 0);
         mask >>>= 1;
      }

      return bits;
   }

   private void readFilesInfo(ByteBuffer header, Archive archive) throws IOException {
      long numFiles = readUint64(header);
      SevenZArchiveEntry[] files = new SevenZArchiveEntry[(int)numFiles];

      for(int i = 0; i < files.length; ++i) {
         files[i] = new SevenZArchiveEntry();
      }

      BitSet isEmptyStream = null;
      BitSet isEmptyFile = null;
      BitSet isAnti = null;

      label169:
      while(true) {
         int nonEmptyFileCounter = getUnsignedByte(header);
         if (nonEmptyFileCounter == 0) {
            nonEmptyFileCounter = 0;
            int emptyFileCounter = 0;

            for(int i = 0; i < files.length; ++i) {
               files[i].setHasStream(isEmptyStream == null || !isEmptyStream.get(i));
               if (files[i].hasStream()) {
                  files[i].setDirectory(false);
                  files[i].setAntiItem(false);
                  files[i].setHasCrc(archive.subStreamsInfo.hasCrc.get(nonEmptyFileCounter));
                  files[i].setCrcValue(archive.subStreamsInfo.crcs[nonEmptyFileCounter]);
                  files[i].setSize(archive.subStreamsInfo.unpackSizes[nonEmptyFileCounter]);
                  ++nonEmptyFileCounter;
               } else {
                  files[i].setDirectory(isEmptyFile == null || !isEmptyFile.get(emptyFileCounter));
                  files[i].setAntiItem(isAnti != null && isAnti.get(emptyFileCounter));
                  files[i].setHasCrc(false);
                  files[i].setSize(0L);
                  ++emptyFileCounter;
               }
            }

            archive.files = files;
            this.calculateStreamMap(archive);
            return;
         }

         long size = readUint64(header);
         BitSet attributesDefined;
         int external;
         int nextFile;
         switch (nonEmptyFileCounter) {
            case 14:
               isEmptyStream = this.readBits(header, files.length);
               break;
            case 15:
               if (isEmptyStream == null) {
                  throw new IOException("Header format error: kEmptyStream must appear before kEmptyFile");
               }

               isEmptyFile = this.readBits(header, isEmptyStream.cardinality());
               break;
            case 16:
               if (isEmptyStream == null) {
                  throw new IOException("Header format error: kEmptyStream must appear before kAnti");
               }

               isAnti = this.readBits(header, isEmptyStream.cardinality());
               break;
            case 17:
               int external = getUnsignedByte(header);
               if (external != 0) {
                  throw new IOException("Not implemented");
               }

               if ((size - 1L & 1L) != 0L) {
                  throw new IOException("File names length invalid");
               }

               byte[] names = new byte[(int)(size - 1L)];
               header.get(names);
               nextFile = 0;
               int nextName = 0;
               int i = 0;

               for(; i < names.length; i += 2) {
                  if (names[i] == 0 && names[i + 1] == 0) {
                     files[nextFile++].setName(new String(names, nextName, i - nextName, "UTF-16LE"));
                     nextName = i + 2;
                  }
               }

               if (nextName != names.length || nextFile != files.length) {
                  throw new IOException("Error parsing file names");
               }
               break;
            case 18:
               attributesDefined = this.readAllOrBits(header, files.length);
               external = getUnsignedByte(header);
               if (external != 0) {
                  throw new IOException("Unimplemented");
               }

               nextFile = 0;

               while(true) {
                  if (nextFile >= files.length) {
                     continue label169;
                  }

                  files[nextFile].setHasCreationDate(attributesDefined.get(nextFile));
                  if (files[nextFile].getHasCreationDate()) {
                     files[nextFile].setCreationDate(header.getLong());
                  }

                  ++nextFile;
               }
            case 19:
               attributesDefined = this.readAllOrBits(header, files.length);
               external = getUnsignedByte(header);
               if (external != 0) {
                  throw new IOException("Unimplemented");
               }

               nextFile = 0;

               while(true) {
                  if (nextFile >= files.length) {
                     continue label169;
                  }

                  files[nextFile].setHasAccessDate(attributesDefined.get(nextFile));
                  if (files[nextFile].getHasAccessDate()) {
                     files[nextFile].setAccessDate(header.getLong());
                  }

                  ++nextFile;
               }
            case 20:
               attributesDefined = this.readAllOrBits(header, files.length);
               external = getUnsignedByte(header);
               if (external != 0) {
                  throw new IOException("Unimplemented");
               }

               nextFile = 0;

               while(true) {
                  if (nextFile >= files.length) {
                     continue label169;
                  }

                  files[nextFile].setHasLastModifiedDate(attributesDefined.get(nextFile));
                  if (files[nextFile].getHasLastModifiedDate()) {
                     files[nextFile].setLastModifiedDate(header.getLong());
                  }

                  ++nextFile;
               }
            case 21:
               attributesDefined = this.readAllOrBits(header, files.length);
               external = getUnsignedByte(header);
               if (external != 0) {
                  throw new IOException("Unimplemented");
               }

               nextFile = 0;

               while(true) {
                  if (nextFile >= files.length) {
                     continue label169;
                  }

                  files[nextFile].setHasWindowsAttributes(attributesDefined.get(nextFile));
                  if (files[nextFile].getHasWindowsAttributes()) {
                     files[nextFile].setWindowsAttributes(header.getInt());
                  }

                  ++nextFile;
               }
            case 22:
            case 23:
            default:
               if (skipBytesFully(header, size) < size) {
                  throw new IOException("Incomplete property of type " + nonEmptyFileCounter);
               }
               break;
            case 24:
               throw new IOException("kStartPos is unsupported, please report");
            case 25:
               if (skipBytesFully(header, size) < size) {
                  throw new IOException("Incomplete kDummy property");
               }
         }
      }
   }

   private void calculateStreamMap(Archive archive) throws IOException {
      StreamMap streamMap = new StreamMap();
      int nextFolderPackStreamIndex = 0;
      int numFolders = archive.folders != null ? archive.folders.length : 0;
      streamMap.folderFirstPackStreamIndex = new int[numFolders];

      for(int i = 0; i < numFolders; ++i) {
         streamMap.folderFirstPackStreamIndex[i] = nextFolderPackStreamIndex;
         nextFolderPackStreamIndex += archive.folders[i].packedStreams.length;
      }

      long nextPackStreamOffset = 0L;
      int numPackSizes = archive.packSizes != null ? archive.packSizes.length : 0;
      streamMap.packStreamOffsets = new long[numPackSizes];

      int nextFolderIndex;
      for(nextFolderIndex = 0; nextFolderIndex < numPackSizes; ++nextFolderIndex) {
         streamMap.packStreamOffsets[nextFolderIndex] = nextPackStreamOffset;
         nextPackStreamOffset += archive.packSizes[nextFolderIndex];
      }

      streamMap.folderFirstFileIndex = new int[numFolders];
      streamMap.fileFolderIndex = new int[archive.files.length];
      nextFolderIndex = 0;
      int nextFolderUnpackStreamIndex = 0;

      for(int i = 0; i < archive.files.length; ++i) {
         if (!archive.files[i].hasStream() && nextFolderUnpackStreamIndex == 0) {
            streamMap.fileFolderIndex[i] = -1;
         } else {
            if (nextFolderUnpackStreamIndex == 0) {
               while(nextFolderIndex < archive.folders.length) {
                  streamMap.folderFirstFileIndex[nextFolderIndex] = i;
                  if (archive.folders[nextFolderIndex].numUnpackSubStreams > 0) {
                     break;
                  }

                  ++nextFolderIndex;
               }

               if (nextFolderIndex >= archive.folders.length) {
                  throw new IOException("Too few folders in archive");
               }
            }

            streamMap.fileFolderIndex[i] = nextFolderIndex;
            if (archive.files[i].hasStream()) {
               ++nextFolderUnpackStreamIndex;
               if (nextFolderUnpackStreamIndex >= archive.folders[nextFolderIndex].numUnpackSubStreams) {
                  ++nextFolderIndex;
                  nextFolderUnpackStreamIndex = 0;
               }
            }
         }
      }

      archive.streamMap = streamMap;
   }

   private void buildDecodingStream() throws IOException {
      int folderIndex = this.archive.streamMap.fileFolderIndex[this.currentEntryIndex];
      if (folderIndex < 0) {
         this.deferredBlockStreams.clear();
      } else {
         SevenZArchiveEntry file = this.archive.files[this.currentEntryIndex];
         if (this.currentFolderIndex == folderIndex) {
            file.setContentMethods(this.archive.files[this.currentEntryIndex - 1].getContentMethods());
         } else {
            this.currentFolderIndex = folderIndex;
            this.deferredBlockStreams.clear();
            if (this.currentFolderInputStream != null) {
               this.currentFolderInputStream.close();
               this.currentFolderInputStream = null;
            }

            Folder folder = this.archive.folders[folderIndex];
            int firstPackStreamIndex = this.archive.streamMap.folderFirstPackStreamIndex[folderIndex];
            long folderOffset = 32L + this.archive.packPos + this.archive.streamMap.packStreamOffsets[firstPackStreamIndex];
            this.currentFolderInputStream = this.buildDecoderStack(folder, folderOffset, firstPackStreamIndex, file);
         }

         InputStream fileStream = new BoundedInputStream(this.currentFolderInputStream, file.getSize());
         if (file.getHasCrc()) {
            fileStream = new CRC32VerifyingInputStream((InputStream)fileStream, file.getSize(), file.getCrcValue());
         }

         this.deferredBlockStreams.add(fileStream);
      }
   }

   private InputStream buildDecoderStack(Folder folder, long folderOffset, int firstPackStreamIndex, SevenZArchiveEntry entry) throws IOException {
      this.channel.position(folderOffset);
      InputStream inputStreamStack = new BufferedInputStream(new BoundedSeekableByteChannelInputStream(this.channel, this.archive.packSizes[firstPackStreamIndex]));
      LinkedList methods = new LinkedList();
      Iterator var8 = folder.getOrderedCoders().iterator();

      while(var8.hasNext()) {
         Coder coder = (Coder)var8.next();
         if (coder.numInStreams != 1L || coder.numOutStreams != 1L) {
            throw new IOException("Multi input/output stream coders are not yet supported");
         }

         SevenZMethod method = SevenZMethod.byId(coder.decompressionMethodId);
         inputStreamStack = Coders.addDecoder(this.fileName, (InputStream)inputStreamStack, folder.getUnpackSizeForCoder(coder), coder, this.password);
         methods.addFirst(new SevenZMethodConfiguration(method, Coders.findByMethod(method).getOptionsFromCoder(coder, (InputStream)inputStreamStack)));
      }

      entry.setContentMethods(methods);
      if (folder.hasCrc) {
         return new CRC32VerifyingInputStream((InputStream)inputStreamStack, folder.getUnpackSize(), folder.crc);
      } else {
         return (InputStream)inputStreamStack;
      }
   }

   public int read() throws IOException {
      return this.getCurrentStream().read();
   }

   private InputStream getCurrentStream() throws IOException {
      if (this.archive.files[this.currentEntryIndex].getSize() == 0L) {
         return new ByteArrayInputStream(new byte[0]);
      } else if (this.deferredBlockStreams.isEmpty()) {
         throw new IllegalStateException("No current 7z entry (call getNextEntry() first).");
      } else {
         while(this.deferredBlockStreams.size() > 1) {
            InputStream stream = (InputStream)this.deferredBlockStreams.remove(0);
            Throwable var2 = null;

            try {
               IOUtils.skip(stream, Long.MAX_VALUE);
            } catch (Throwable var11) {
               var2 = var11;
               throw var11;
            } finally {
               if (stream != null) {
                  if (var2 != null) {
                     try {
                        stream.close();
                     } catch (Throwable var10) {
                        var2.addSuppressed(var10);
                     }
                  } else {
                     stream.close();
                  }
               }

            }
         }

         return (InputStream)this.deferredBlockStreams.get(0);
      }
   }

   public int read(byte[] b) throws IOException {
      return this.read(b, 0, b.length);
   }

   public int read(byte[] b, int off, int len) throws IOException {
      return this.getCurrentStream().read(b, off, len);
   }

   private static long readUint64(ByteBuffer in) throws IOException {
      long firstByte = (long)getUnsignedByte(in);
      int mask = 128;
      long value = 0L;

      for(int i = 0; i < 8; ++i) {
         if ((firstByte & (long)mask) == 0L) {
            return value | (firstByte & (long)(mask - 1)) << 8 * i;
         }

         long nextByte = (long)getUnsignedByte(in);
         value |= nextByte << 8 * i;
         mask >>>= 1;
      }

      return value;
   }

   private static int getUnsignedByte(ByteBuffer buf) {
      return buf.get() & 255;
   }

   public static boolean matches(byte[] signature, int length) {
      if (length < sevenZSignature.length) {
         return false;
      } else {
         for(int i = 0; i < sevenZSignature.length; ++i) {
            if (signature[i] != sevenZSignature[i]) {
               return false;
            }
         }

         return true;
      }
   }

   private static long skipBytesFully(ByteBuffer input, long bytesToSkip) throws IOException {
      if (bytesToSkip < 1L) {
         return 0L;
      } else {
         int current = input.position();
         int maxSkip = input.remaining();
         if ((long)maxSkip < bytesToSkip) {
            bytesToSkip = (long)maxSkip;
         }

         input.position(current + (int)bytesToSkip);
         return bytesToSkip;
      }
   }

   private void readFully(ByteBuffer buf) throws IOException {
      buf.rewind();
      IOUtils.readFully((ReadableByteChannel)this.channel, (ByteBuffer)buf);
      buf.flip();
   }

   public String toString() {
      return this.archive.toString();
   }
}
