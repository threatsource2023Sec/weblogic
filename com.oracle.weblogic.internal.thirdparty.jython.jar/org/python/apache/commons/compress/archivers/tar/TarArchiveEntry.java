package org.python.apache.commons.compress.archivers.tar;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import org.python.apache.commons.compress.archivers.ArchiveEntry;
import org.python.apache.commons.compress.archivers.zip.ZipEncoding;
import org.python.apache.commons.compress.utils.ArchiveUtils;

public class TarArchiveEntry implements ArchiveEntry, TarConstants {
   private static final TarArchiveEntry[] EMPTY_TAR_ARCHIVE_ENTRIES = new TarArchiveEntry[0];
   private String name;
   private boolean preserveLeadingSlashes;
   private int mode;
   private long userId;
   private long groupId;
   private long size;
   private long modTime;
   private boolean checkSumOK;
   private byte linkFlag;
   private String linkName;
   private String magic;
   private String version;
   private String userName;
   private String groupName;
   private int devMajor;
   private int devMinor;
   private boolean isExtended;
   private long realSize;
   private boolean paxGNUSparse;
   private boolean starSparse;
   private final File file;
   public static final int MAX_NAMELEN = 31;
   public static final int DEFAULT_DIR_MODE = 16877;
   public static final int DEFAULT_FILE_MODE = 33188;
   public static final int MILLIS_PER_SECOND = 1000;

   private TarArchiveEntry() {
      this.name = "";
      this.userId = 0L;
      this.groupId = 0L;
      this.size = 0L;
      this.linkName = "";
      this.magic = "ustar\u0000";
      this.version = "00";
      this.groupName = "";
      this.devMajor = 0;
      this.devMinor = 0;
      String user = System.getProperty("user.name", "");
      if (user.length() > 31) {
         user = user.substring(0, 31);
      }

      this.userName = user;
      this.file = null;
   }

   public TarArchiveEntry(String name) {
      this(name, false);
   }

   public TarArchiveEntry(String name, boolean preserveLeadingSlashes) {
      this();
      this.preserveLeadingSlashes = preserveLeadingSlashes;
      name = normalizeFileName(name, preserveLeadingSlashes);
      boolean isDir = name.endsWith("/");
      this.name = name;
      this.mode = isDir ? 16877 : '膤';
      this.linkFlag = (byte)(isDir ? 53 : 48);
      this.modTime = (new Date()).getTime() / 1000L;
      this.userName = "";
   }

   public TarArchiveEntry(String name, byte linkFlag) {
      this(name, linkFlag, false);
   }

   public TarArchiveEntry(String name, byte linkFlag, boolean preserveLeadingSlashes) {
      this(name, preserveLeadingSlashes);
      this.linkFlag = linkFlag;
      if (linkFlag == 76) {
         this.magic = "ustar ";
         this.version = " \u0000";
      }

   }

   public TarArchiveEntry(File file) {
      this(file, file.getPath());
   }

   public TarArchiveEntry(File file, String fileName) {
      this.name = "";
      this.userId = 0L;
      this.groupId = 0L;
      this.size = 0L;
      this.linkName = "";
      this.magic = "ustar\u0000";
      this.version = "00";
      this.groupName = "";
      this.devMajor = 0;
      this.devMinor = 0;
      String normalizedName = normalizeFileName(fileName, false);
      this.file = file;
      if (file.isDirectory()) {
         this.mode = 16877;
         this.linkFlag = 53;
         int nameLength = normalizedName.length();
         if (nameLength != 0 && normalizedName.charAt(nameLength - 1) == '/') {
            this.name = normalizedName;
         } else {
            this.name = normalizedName + "/";
         }
      } else {
         this.mode = 33188;
         this.linkFlag = 48;
         this.size = file.length();
         this.name = normalizedName;
      }

      this.modTime = file.lastModified() / 1000L;
      this.userName = "";
   }

   public TarArchiveEntry(byte[] headerBuf) {
      this();
      this.parseTarHeader(headerBuf);
   }

   public TarArchiveEntry(byte[] headerBuf, ZipEncoding encoding) throws IOException {
      this();
      this.parseTarHeader(headerBuf, encoding);
   }

   public boolean equals(TarArchiveEntry it) {
      return it != null && this.getName().equals(it.getName());
   }

   public boolean equals(Object it) {
      return it != null && this.getClass() == it.getClass() ? this.equals((TarArchiveEntry)it) : false;
   }

   public int hashCode() {
      return this.getName().hashCode();
   }

   public boolean isDescendent(TarArchiveEntry desc) {
      return desc.getName().startsWith(this.getName());
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = normalizeFileName(name, this.preserveLeadingSlashes);
   }

   public void setMode(int mode) {
      this.mode = mode;
   }

   public String getLinkName() {
      return this.linkName;
   }

   public void setLinkName(String link) {
      this.linkName = link;
   }

   /** @deprecated */
   @Deprecated
   public int getUserId() {
      return (int)(this.userId & -1L);
   }

   public void setUserId(int userId) {
      this.setUserId((long)userId);
   }

   public long getLongUserId() {
      return this.userId;
   }

   public void setUserId(long userId) {
      this.userId = userId;
   }

   /** @deprecated */
   @Deprecated
   public int getGroupId() {
      return (int)(this.groupId & -1L);
   }

   public void setGroupId(int groupId) {
      this.setGroupId((long)groupId);
   }

   public long getLongGroupId() {
      return this.groupId;
   }

   public void setGroupId(long groupId) {
      this.groupId = groupId;
   }

   public String getUserName() {
      return this.userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getGroupName() {
      return this.groupName;
   }

   public void setGroupName(String groupName) {
      this.groupName = groupName;
   }

   public void setIds(int userId, int groupId) {
      this.setUserId(userId);
      this.setGroupId(groupId);
   }

   public void setNames(String userName, String groupName) {
      this.setUserName(userName);
      this.setGroupName(groupName);
   }

   public void setModTime(long time) {
      this.modTime = time / 1000L;
   }

   public void setModTime(Date time) {
      this.modTime = time.getTime() / 1000L;
   }

   public Date getModTime() {
      return new Date(this.modTime * 1000L);
   }

   public Date getLastModifiedDate() {
      return this.getModTime();
   }

   public boolean isCheckSumOK() {
      return this.checkSumOK;
   }

   public File getFile() {
      return this.file;
   }

   public int getMode() {
      return this.mode;
   }

   public long getSize() {
      return this.size;
   }

   public void setSize(long size) {
      if (size < 0L) {
         throw new IllegalArgumentException("Size is out of range: " + size);
      } else {
         this.size = size;
      }
   }

   public int getDevMajor() {
      return this.devMajor;
   }

   public void setDevMajor(int devNo) {
      if (devNo < 0) {
         throw new IllegalArgumentException("Major device number is out of range: " + devNo);
      } else {
         this.devMajor = devNo;
      }
   }

   public int getDevMinor() {
      return this.devMinor;
   }

   public void setDevMinor(int devNo) {
      if (devNo < 0) {
         throw new IllegalArgumentException("Minor device number is out of range: " + devNo);
      } else {
         this.devMinor = devNo;
      }
   }

   public boolean isExtended() {
      return this.isExtended;
   }

   public long getRealSize() {
      return this.realSize;
   }

   public boolean isGNUSparse() {
      return this.isOldGNUSparse() || this.isPaxGNUSparse();
   }

   public boolean isOldGNUSparse() {
      return this.linkFlag == 83;
   }

   public boolean isPaxGNUSparse() {
      return this.paxGNUSparse;
   }

   public boolean isStarSparse() {
      return this.starSparse;
   }

   public boolean isGNULongLinkEntry() {
      return this.linkFlag == 75;
   }

   public boolean isGNULongNameEntry() {
      return this.linkFlag == 76;
   }

   public boolean isPaxHeader() {
      return this.linkFlag == 120 || this.linkFlag == 88;
   }

   public boolean isGlobalPaxHeader() {
      return this.linkFlag == 103;
   }

   public boolean isDirectory() {
      if (this.file != null) {
         return this.file.isDirectory();
      } else if (this.linkFlag == 53) {
         return true;
      } else {
         return !this.isPaxHeader() && !this.isGlobalPaxHeader() && this.getName().endsWith("/");
      }
   }

   public boolean isFile() {
      if (this.file != null) {
         return this.file.isFile();
      } else if (this.linkFlag != 0 && this.linkFlag != 48) {
         return !this.getName().endsWith("/");
      } else {
         return true;
      }
   }

   public boolean isSymbolicLink() {
      return this.linkFlag == 50;
   }

   public boolean isLink() {
      return this.linkFlag == 49;
   }

   public boolean isCharacterDevice() {
      return this.linkFlag == 51;
   }

   public boolean isBlockDevice() {
      return this.linkFlag == 52;
   }

   public boolean isFIFO() {
      return this.linkFlag == 54;
   }

   public boolean isSparse() {
      return this.isGNUSparse() || this.isStarSparse();
   }

   public TarArchiveEntry[] getDirectoryEntries() {
      if (this.file != null && this.file.isDirectory()) {
         String[] list = this.file.list();
         if (list == null) {
            return EMPTY_TAR_ARCHIVE_ENTRIES;
         } else {
            TarArchiveEntry[] result = new TarArchiveEntry[list.length];

            for(int i = 0; i < result.length; ++i) {
               result[i] = new TarArchiveEntry(new File(this.file, list[i]));
            }

            return result;
         }
      } else {
         return EMPTY_TAR_ARCHIVE_ENTRIES;
      }
   }

   public void writeEntryHeader(byte[] outbuf) {
      try {
         this.writeEntryHeader(outbuf, TarUtils.DEFAULT_ENCODING, false);
      } catch (IOException var5) {
         try {
            this.writeEntryHeader(outbuf, TarUtils.FALLBACK_ENCODING, false);
         } catch (IOException var4) {
            throw new RuntimeException(var4);
         }
      }

   }

   public void writeEntryHeader(byte[] outbuf, ZipEncoding encoding, boolean starMode) throws IOException {
      int offset = 0;
      offset = TarUtils.formatNameBytes(this.name, outbuf, offset, 100, encoding);
      offset = this.writeEntryHeaderField((long)this.mode, outbuf, offset, 8, starMode);
      offset = this.writeEntryHeaderField(this.userId, outbuf, offset, 8, starMode);
      offset = this.writeEntryHeaderField(this.groupId, outbuf, offset, 8, starMode);
      offset = this.writeEntryHeaderField(this.size, outbuf, offset, 12, starMode);
      offset = this.writeEntryHeaderField(this.modTime, outbuf, offset, 12, starMode);
      int csOffset = offset;

      for(int c = 0; c < 8; ++c) {
         outbuf[offset++] = 32;
      }

      outbuf[offset++] = this.linkFlag;
      offset = TarUtils.formatNameBytes(this.linkName, outbuf, offset, 100, encoding);
      offset = TarUtils.formatNameBytes(this.magic, outbuf, offset, 6);
      offset = TarUtils.formatNameBytes(this.version, outbuf, offset, 2);
      offset = TarUtils.formatNameBytes(this.userName, outbuf, offset, 32, encoding);
      offset = TarUtils.formatNameBytes(this.groupName, outbuf, offset, 32, encoding);
      offset = this.writeEntryHeaderField((long)this.devMajor, outbuf, offset, 8, starMode);

      for(offset = this.writeEntryHeaderField((long)this.devMinor, outbuf, offset, 8, starMode); offset < outbuf.length; outbuf[offset++] = 0) {
      }

      long chk = TarUtils.computeCheckSum(outbuf);
      TarUtils.formatCheckSumOctalBytes(chk, outbuf, csOffset, 8);
   }

   private int writeEntryHeaderField(long value, byte[] outbuf, int offset, int length, boolean starMode) {
      return starMode || value >= 0L && value < 1L << 3 * (length - 1) ? TarUtils.formatLongOctalOrBinaryBytes(value, outbuf, offset, length) : TarUtils.formatLongOctalBytes(0L, outbuf, offset, length);
   }

   public void parseTarHeader(byte[] header) {
      try {
         this.parseTarHeader(header, TarUtils.DEFAULT_ENCODING);
      } catch (IOException var5) {
         try {
            this.parseTarHeader(header, TarUtils.DEFAULT_ENCODING, true);
         } catch (IOException var4) {
            throw new RuntimeException(var4);
         }
      }

   }

   public void parseTarHeader(byte[] header, ZipEncoding encoding) throws IOException {
      this.parseTarHeader(header, encoding, false);
   }

   private void parseTarHeader(byte[] header, ZipEncoding encoding, boolean oldStyle) throws IOException {
      int offset = 0;
      this.name = oldStyle ? TarUtils.parseName(header, offset, 100) : TarUtils.parseName(header, offset, 100, encoding);
      offset += 100;
      this.mode = (int)TarUtils.parseOctalOrBinary(header, offset, 8);
      offset += 8;
      this.userId = (long)((int)TarUtils.parseOctalOrBinary(header, offset, 8));
      offset += 8;
      this.groupId = (long)((int)TarUtils.parseOctalOrBinary(header, offset, 8));
      offset += 8;
      this.size = TarUtils.parseOctalOrBinary(header, offset, 12);
      offset += 12;
      this.modTime = TarUtils.parseOctalOrBinary(header, offset, 12);
      offset += 12;
      this.checkSumOK = TarUtils.verifyCheckSum(header);
      offset += 8;
      this.linkFlag = header[offset++];
      this.linkName = oldStyle ? TarUtils.parseName(header, offset, 100) : TarUtils.parseName(header, offset, 100, encoding);
      offset += 100;
      this.magic = TarUtils.parseName(header, offset, 6);
      offset += 6;
      this.version = TarUtils.parseName(header, offset, 2);
      offset += 2;
      this.userName = oldStyle ? TarUtils.parseName(header, offset, 32) : TarUtils.parseName(header, offset, 32, encoding);
      offset += 32;
      this.groupName = oldStyle ? TarUtils.parseName(header, offset, 32) : TarUtils.parseName(header, offset, 32, encoding);
      offset += 32;
      this.devMajor = (int)TarUtils.parseOctalOrBinary(header, offset, 8);
      offset += 8;
      this.devMinor = (int)TarUtils.parseOctalOrBinary(header, offset, 8);
      offset += 8;
      int type = this.evaluateType(header);
      String prefix;
      switch (type) {
         case 2:
            offset += 12;
            offset += 12;
            offset += 12;
            offset += 4;
            ++offset;
            offset += 96;
            this.isExtended = TarUtils.parseBoolean(header, offset);
            ++offset;
            this.realSize = TarUtils.parseOctal(header, offset, 12);
            offset += 12;
            break;
         case 3:
         default:
            prefix = oldStyle ? TarUtils.parseName(header, offset, 155) : TarUtils.parseName(header, offset, 155, encoding);
            if (this.isDirectory() && !this.name.endsWith("/")) {
               this.name = this.name + "/";
            }

            if (prefix.length() > 0) {
               this.name = prefix + "/" + this.name;
            }
            break;
         case 4:
            prefix = oldStyle ? TarUtils.parseName(header, offset, 131) : TarUtils.parseName(header, offset, 131, encoding);
            if (prefix.length() > 0) {
               this.name = prefix + "/" + this.name;
            }
      }

   }

   private static String normalizeFileName(String fileName, boolean preserveLeadingSlashes) {
      String osname = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
      if (osname != null) {
         int colon;
         if (osname.startsWith("windows")) {
            if (fileName.length() > 2) {
               colon = fileName.charAt(0);
               char ch2 = fileName.charAt(1);
               if (ch2 == ':' && (colon >= 97 && colon <= 122 || colon >= 65 && colon <= 90)) {
                  fileName = fileName.substring(2);
               }
            }
         } else if (osname.contains("netware")) {
            colon = fileName.indexOf(58);
            if (colon != -1) {
               fileName = fileName.substring(colon + 1);
            }
         }
      }

      for(fileName = fileName.replace(File.separatorChar, '/'); !preserveLeadingSlashes && fileName.startsWith("/"); fileName = fileName.substring(1)) {
      }

      return fileName;
   }

   private int evaluateType(byte[] header) {
      if (ArchiveUtils.matchAsciiBuffer("ustar ", header, 257, 6)) {
         return 2;
      } else if (ArchiveUtils.matchAsciiBuffer("ustar\u0000", header, 257, 6)) {
         return ArchiveUtils.matchAsciiBuffer("tar\u0000", header, 508, 4) ? 4 : 3;
      } else {
         return 0;
      }
   }

   void fillGNUSparse0xData(Map headers) {
      this.paxGNUSparse = true;
      this.realSize = (long)Integer.parseInt((String)headers.get("GNU.sparse.size"));
      if (headers.containsKey("GNU.sparse.name")) {
         this.name = (String)headers.get("GNU.sparse.name");
      }

   }

   void fillGNUSparse1xData(Map headers) {
      this.paxGNUSparse = true;
      this.realSize = (long)Integer.parseInt((String)headers.get("GNU.sparse.realsize"));
      this.name = (String)headers.get("GNU.sparse.name");
   }

   void fillStarSparseData(Map headers) {
      this.starSparse = true;
      if (headers.containsKey("SCHILY.realsize")) {
         this.realSize = Long.parseLong((String)headers.get("SCHILY.realsize"));
      }

   }
}
