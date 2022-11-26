package org.python.icu.impl;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.python.icu.util.ICUUncheckedIOException;
import org.python.icu.util.VersionInfo;

public final class ICUBinary {
   private static final List icuDataFiles = new ArrayList();
   private static final byte MAGIC1 = -38;
   private static final byte MAGIC2 = 39;
   private static final byte CHAR_SET_ = 0;
   private static final byte CHAR_SIZE_ = 2;
   private static final String MAGIC_NUMBER_AUTHENTICATION_FAILED_ = "ICU data file error: Not an ICU data file";
   private static final String HEADER_AUTHENTICATION_FAILED_ = "ICU data file error: Header authentication failed, please check if you have a valid ICU data file";

   private static void addDataFilesFromPath(String dataPath, List files) {
      int sepIndex;
      for(int pathStart = 0; pathStart < dataPath.length(); pathStart = sepIndex + 1) {
         sepIndex = dataPath.indexOf(File.pathSeparatorChar, pathStart);
         int pathLimit;
         if (sepIndex >= 0) {
            pathLimit = sepIndex;
         } else {
            pathLimit = dataPath.length();
         }

         String path = dataPath.substring(pathStart, pathLimit).trim();
         if (path.endsWith(File.separator)) {
            path = path.substring(0, path.length() - 1);
         }

         if (path.length() != 0) {
            addDataFilesFromFolder(new File(path), new StringBuilder(), icuDataFiles);
         }

         if (sepIndex < 0) {
            break;
         }
      }

   }

   private static void addDataFilesFromFolder(File folder, StringBuilder itemPath, List dataFiles) {
      File[] files = folder.listFiles();
      if (files != null && files.length != 0) {
         int folderPathLength = itemPath.length();
         if (folderPathLength > 0) {
            itemPath.append('/');
            ++folderPathLength;
         }

         File[] var5 = files;
         int var6 = files.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            File file = var5[var7];
            String fileName = file.getName();
            if (!fileName.endsWith(".txt")) {
               itemPath.append(fileName);
               if (file.isDirectory()) {
                  addDataFilesFromFolder(file, itemPath, dataFiles);
               } else if (fileName.endsWith(".dat")) {
                  ByteBuffer pkgBytes = mapFile(file);
                  if (pkgBytes != null && ICUBinary.DatPackageReader.validate(pkgBytes)) {
                     dataFiles.add(new PackageDataFile(itemPath.toString(), pkgBytes));
                  }
               } else {
                  dataFiles.add(new SingleDataFile(itemPath.toString(), file));
               }

               itemPath.setLength(folderPathLength);
            }
         }

      }
   }

   static int compareKeys(CharSequence key, ByteBuffer bytes, int offset) {
      int i = 0;

      while(true) {
         int c2 = bytes.get(offset);
         if (c2 == 0) {
            if (i == key.length()) {
               return 0;
            }

            return 1;
         }

         if (i == key.length()) {
            return -1;
         }

         int diff = key.charAt(i) - c2;
         if (diff != 0) {
            return diff;
         }

         ++i;
         ++offset;
      }
   }

   static int compareKeys(CharSequence key, byte[] bytes, int offset) {
      int i = 0;

      while(true) {
         int c2 = bytes[offset];
         if (c2 == 0) {
            if (i == key.length()) {
               return 0;
            }

            return 1;
         }

         if (i == key.length()) {
            return -1;
         }

         int diff = key.charAt(i) - c2;
         if (diff != 0) {
            return diff;
         }

         ++i;
         ++offset;
      }
   }

   public static ByteBuffer getData(String itemPath) {
      return getData((ClassLoader)null, (String)null, itemPath, false);
   }

   public static ByteBuffer getData(ClassLoader loader, String resourceName, String itemPath) {
      return getData(loader, resourceName, itemPath, false);
   }

   public static ByteBuffer getRequiredData(String itemPath) {
      return getData((ClassLoader)null, (String)null, itemPath, true);
   }

   private static ByteBuffer getData(ClassLoader loader, String resourceName, String itemPath, boolean required) {
      ByteBuffer bytes = getDataFromFile(itemPath);
      if (bytes != null) {
         return bytes;
      } else {
         if (loader == null) {
            loader = ClassLoaderUtil.getClassLoader(ICUData.class);
         }

         if (resourceName == null) {
            resourceName = "org/python/icu/impl/data/icudt59b/" + itemPath;
         }

         ByteBuffer buffer = null;

         try {
            InputStream is = ICUData.getStream(loader, resourceName, required);
            if (is == null) {
               return null;
            } else {
               buffer = getByteBufferFromInputStreamAndCloseStream(is);
               return buffer;
            }
         } catch (IOException var7) {
            throw new ICUUncheckedIOException(var7);
         }
      }
   }

   private static ByteBuffer getDataFromFile(String itemPath) {
      Iterator var1 = icuDataFiles.iterator();

      ByteBuffer data;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         DataFile dataFile = (DataFile)var1.next();
         data = dataFile.getData(itemPath);
      } while(data == null);

      return data;
   }

   private static ByteBuffer mapFile(File path) {
      try {
         FileInputStream file = new FileInputStream(path);
         FileChannel channel = file.getChannel();
         ByteBuffer bytes = null;

         try {
            bytes = channel.map(MapMode.READ_ONLY, 0L, channel.size());
         } finally {
            file.close();
         }

         return bytes;
      } catch (FileNotFoundException var9) {
         System.err.println(var9);
      } catch (IOException var10) {
         System.err.println(var10);
      }

      return null;
   }

   public static void addBaseNamesInFileFolder(String folder, String suffix, Set names) {
      Iterator var3 = icuDataFiles.iterator();

      while(var3.hasNext()) {
         DataFile dataFile = (DataFile)var3.next();
         dataFile.addBaseNamesInFolder(folder, suffix, names);
      }

   }

   public static VersionInfo readHeaderAndDataVersion(ByteBuffer bytes, int dataFormat, Authenticate authenticate) throws IOException {
      return getVersionInfoFromCompactInt(readHeader(bytes, dataFormat, authenticate));
   }

   public static int readHeader(ByteBuffer bytes, int dataFormat, Authenticate authenticate) throws IOException {
      assert bytes != null && bytes.position() == 0;

      byte magic1 = bytes.get(2);
      byte magic2 = bytes.get(3);
      if (magic1 == -38 && magic2 == 39) {
         byte isBigEndian = bytes.get(8);
         byte charsetFamily = bytes.get(9);
         byte sizeofUChar = bytes.get(10);
         if (isBigEndian >= 0 && 1 >= isBigEndian && charsetFamily == 0 && sizeofUChar == 2) {
            bytes.order(isBigEndian != 0 ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
            int headerSize = bytes.getChar(0);
            int sizeofUDataInfo = bytes.getChar(4);
            if (sizeofUDataInfo >= 20 && headerSize >= sizeofUDataInfo + 4) {
               byte[] formatVersion = new byte[]{bytes.get(16), bytes.get(17), bytes.get(18), bytes.get(19)};
               if (bytes.get(12) == (byte)(dataFormat >> 24) && bytes.get(13) == (byte)(dataFormat >> 16) && bytes.get(14) == (byte)(dataFormat >> 8) && bytes.get(15) == (byte)dataFormat && (authenticate == null || authenticate.isDataVersionAcceptable(formatVersion))) {
                  bytes.position(headerSize);
                  return bytes.get(20) << 24 | (bytes.get(21) & 255) << 16 | (bytes.get(22) & 255) << 8 | bytes.get(23) & 255;
               } else {
                  throw new IOException("ICU data file error: Header authentication failed, please check if you have a valid ICU data file" + String.format("; data format %02x%02x%02x%02x, format version %d.%d.%d.%d", bytes.get(12), bytes.get(13), bytes.get(14), bytes.get(15), formatVersion[0] & 255, formatVersion[1] & 255, formatVersion[2] & 255, formatVersion[3] & 255));
               }
            } else {
               throw new IOException("Internal Error: Header size error");
            }
         } else {
            throw new IOException("ICU data file error: Header authentication failed, please check if you have a valid ICU data file");
         }
      } else {
         throw new IOException("ICU data file error: Not an ICU data file");
      }
   }

   public static int writeHeader(int dataFormat, int formatVersion, int dataVersion, DataOutputStream dos) throws IOException {
      dos.writeChar(32);
      dos.writeByte(-38);
      dos.writeByte(39);
      dos.writeChar(20);
      dos.writeChar(0);
      dos.writeByte(1);
      dos.writeByte(0);
      dos.writeByte(2);
      dos.writeByte(0);
      dos.writeInt(dataFormat);
      dos.writeInt(formatVersion);
      dos.writeInt(dataVersion);
      dos.writeLong(0L);

      assert dos.size() == 32;

      return 32;
   }

   public static void skipBytes(ByteBuffer bytes, int skipLength) {
      if (skipLength > 0) {
         bytes.position(bytes.position() + skipLength);
      }

   }

   public static String getString(ByteBuffer bytes, int length, int additionalSkipLength) {
      CharSequence cs = bytes.asCharBuffer();
      String s = cs.subSequence(0, length).toString();
      skipBytes(bytes, length * 2 + additionalSkipLength);
      return s;
   }

   public static char[] getChars(ByteBuffer bytes, int length, int additionalSkipLength) {
      char[] dest = new char[length];
      bytes.asCharBuffer().get(dest);
      skipBytes(bytes, length * 2 + additionalSkipLength);
      return dest;
   }

   public static short[] getShorts(ByteBuffer bytes, int length, int additionalSkipLength) {
      short[] dest = new short[length];
      bytes.asShortBuffer().get(dest);
      skipBytes(bytes, length * 2 + additionalSkipLength);
      return dest;
   }

   public static int[] getInts(ByteBuffer bytes, int length, int additionalSkipLength) {
      int[] dest = new int[length];
      bytes.asIntBuffer().get(dest);
      skipBytes(bytes, length * 4 + additionalSkipLength);
      return dest;
   }

   public static long[] getLongs(ByteBuffer bytes, int length, int additionalSkipLength) {
      long[] dest = new long[length];
      bytes.asLongBuffer().get(dest);
      skipBytes(bytes, length * 8 + additionalSkipLength);
      return dest;
   }

   public static ByteBuffer sliceWithOrder(ByteBuffer bytes) {
      ByteBuffer b = bytes.slice();
      return b.order(bytes.order());
   }

   public static ByteBuffer getByteBufferFromInputStreamAndCloseStream(InputStream is) throws IOException {
      ByteBuffer var10;
      try {
         int avail = is.available();
         byte[] bytes;
         if (avail > 32) {
            bytes = new byte[avail];
         } else {
            bytes = new byte[128];
         }

         int length = 0;

         while(true) {
            int nextByte;
            if (length < bytes.length) {
               nextByte = is.read(bytes, length, bytes.length - length);
               if (nextByte < 0) {
                  break;
               }

               length += nextByte;
            } else {
               nextByte = is.read();
               if (nextByte < 0) {
                  break;
               }

               int capacity = 2 * bytes.length;
               if (capacity < 128) {
                  capacity = 128;
               } else if (capacity < 16384) {
                  capacity *= 2;
               }

               byte[] newBytes = new byte[capacity];
               System.arraycopy(bytes, 0, newBytes, 0, length);
               bytes = newBytes;
               newBytes[length++] = (byte)nextByte;
            }
         }

         var10 = ByteBuffer.wrap(bytes, 0, length);
      } finally {
         is.close();
      }

      return var10;
   }

   public static VersionInfo getVersionInfoFromCompactInt(int version) {
      return VersionInfo.getInstance(version >>> 24, version >> 16 & 255, version >> 8 & 255, version & 255);
   }

   public static byte[] getVersionByteArrayFromCompactInt(int version) {
      return new byte[]{(byte)(version >> 24), (byte)(version >> 16), (byte)(version >> 8), (byte)version};
   }

   static {
      String dataPath = ICUConfig.get(ICUBinary.class.getName() + ".dataPath");
      if (dataPath != null) {
         addDataFilesFromPath(dataPath, icuDataFiles);
      }

   }

   public interface Authenticate {
      boolean isDataVersionAcceptable(byte[] var1);
   }

   private static final class PackageDataFile extends DataFile {
      private final ByteBuffer pkgBytes;

      PackageDataFile(String item, ByteBuffer bytes) {
         super(item);
         this.pkgBytes = bytes;
      }

      ByteBuffer getData(String requestedPath) {
         return ICUBinary.DatPackageReader.getData(this.pkgBytes, requestedPath);
      }

      void addBaseNamesInFolder(String folder, String suffix, Set names) {
         ICUBinary.DatPackageReader.addBaseNamesInFolder(this.pkgBytes, folder, suffix, names);
      }
   }

   private static final class SingleDataFile extends DataFile {
      private final File path;

      SingleDataFile(String item, File path) {
         super(item);
         this.path = path;
      }

      public String toString() {
         return this.path.toString();
      }

      ByteBuffer getData(String requestedPath) {
         return requestedPath.equals(this.itemPath) ? ICUBinary.mapFile(this.path) : null;
      }

      void addBaseNamesInFolder(String folder, String suffix, Set names) {
         if (this.itemPath.length() > folder.length() + suffix.length() && this.itemPath.startsWith(folder) && this.itemPath.endsWith(suffix) && this.itemPath.charAt(folder.length()) == '/' && this.itemPath.indexOf(47, folder.length() + 1) < 0) {
            names.add(this.itemPath.substring(folder.length() + 1, this.itemPath.length() - suffix.length()));
         }

      }
   }

   private abstract static class DataFile {
      protected final String itemPath;

      DataFile(String item) {
         this.itemPath = item;
      }

      public String toString() {
         return this.itemPath;
      }

      abstract ByteBuffer getData(String var1);

      abstract void addBaseNamesInFolder(String var1, String var2, Set var3);
   }

   private static final class DatPackageReader {
      private static final int DATA_FORMAT = 1131245124;
      private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable();

      static boolean validate(ByteBuffer bytes) {
         try {
            ICUBinary.readHeader(bytes, 1131245124, IS_ACCEPTABLE);
         } catch (IOException var2) {
            return false;
         }

         int count = bytes.getInt(bytes.position());
         if (count <= 0) {
            return false;
         } else if (bytes.position() + 4 + count * 24 > bytes.capacity()) {
            return false;
         } else {
            return startsWithPackageName(bytes, getNameOffset(bytes, 0)) && startsWithPackageName(bytes, getNameOffset(bytes, count - 1));
         }
      }

      private static boolean startsWithPackageName(ByteBuffer bytes, int start) {
         int length = "icudt59b".length() - 1;

         for(int i = 0; i < length; ++i) {
            if (bytes.get(start + i) != "icudt59b".charAt(i)) {
               return false;
            }
         }

         byte c = bytes.get(start + length++);
         return (c == 98 || c == 108) && bytes.get(start + length) == 47;
      }

      static ByteBuffer getData(ByteBuffer bytes, CharSequence key) {
         int index = binarySearch(bytes, key);
         if (index >= 0) {
            ByteBuffer data = bytes.duplicate();
            data.position(getDataOffset(bytes, index));
            data.limit(getDataOffset(bytes, index + 1));
            return ICUBinary.sliceWithOrder(data);
         } else {
            return null;
         }
      }

      static void addBaseNamesInFolder(ByteBuffer bytes, String folder, String suffix, Set names) {
         int index = binarySearch(bytes, folder);
         if (index < 0) {
            index = ~index;
         }

         int base = bytes.position();
         int count = bytes.getInt(base);

         for(StringBuilder sb = new StringBuilder(); index < count && addBaseName(bytes, index, folder, suffix, sb, names); ++index) {
         }

      }

      private static int binarySearch(ByteBuffer bytes, CharSequence key) {
         int base = bytes.position();
         int count = bytes.getInt(base);
         int start = 0;
         int limit = count;

         while(start < limit) {
            int mid = start + limit >>> 1;
            int nameOffset = getNameOffset(bytes, mid);
            nameOffset += "icudt59b".length() + 1;
            int result = ICUBinary.compareKeys(key, bytes, nameOffset);
            if (result < 0) {
               limit = mid;
            } else {
               if (result <= 0) {
                  return mid;
               }

               start = mid + 1;
            }
         }

         return ~start;
      }

      private static int getNameOffset(ByteBuffer bytes, int index) {
         int base = bytes.position();

         assert 0 <= index && index < bytes.getInt(base);

         return base + bytes.getInt(base + 4 + index * 8);
      }

      private static int getDataOffset(ByteBuffer bytes, int index) {
         int base = bytes.position();
         int count = bytes.getInt(base);
         if (index == count) {
            return bytes.capacity();
         } else {
            assert 0 <= index && index < count;

            return base + bytes.getInt(base + 4 + 4 + index * 8);
         }
      }

      static boolean addBaseName(ByteBuffer bytes, int index, String folder, String suffix, StringBuilder sb, Set names) {
         int offset = getNameOffset(bytes, index);
         offset += "icudt59b".length() + 1;
         if (folder.length() != 0) {
            for(int i = 0; i < folder.length(); ++offset) {
               if (bytes.get(offset) != folder.charAt(i)) {
                  return false;
               }

               ++i;
            }

            if (bytes.get(offset++) != 47) {
               return false;
            }
         }

         sb.setLength(0);

         int nameLimit;
         byte b;
         while((b = bytes.get(offset++)) != 0) {
            nameLimit = (char)b;
            if (nameLimit == 47) {
               return true;
            }

            sb.append((char)nameLimit);
         }

         nameLimit = sb.length() - suffix.length();
         if (sb.lastIndexOf(suffix, nameLimit) >= 0) {
            names.add(sb.substring(0, nameLimit));
         }

         return true;
      }

      private static final class IsAcceptable implements Authenticate {
         private IsAcceptable() {
         }

         public boolean isDataVersionAcceptable(byte[] version) {
            return version[0] == 1;
         }

         // $FF: synthetic method
         IsAcceptable(Object x0) {
            this();
         }
      }
   }
}
