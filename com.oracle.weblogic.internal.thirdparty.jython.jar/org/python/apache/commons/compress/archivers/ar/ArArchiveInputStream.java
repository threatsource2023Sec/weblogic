package org.python.apache.commons.compress.archivers.ar;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.python.apache.commons.compress.archivers.ArchiveEntry;
import org.python.apache.commons.compress.archivers.ArchiveInputStream;
import org.python.apache.commons.compress.utils.ArchiveUtils;
import org.python.apache.commons.compress.utils.IOUtils;

public class ArArchiveInputStream extends ArchiveInputStream {
   private final InputStream input;
   private long offset = 0L;
   private boolean closed;
   private ArArchiveEntry currentEntry = null;
   private byte[] namebuffer = null;
   private long entryOffset = -1L;
   private final byte[] nameBuf = new byte[16];
   private final byte[] lastModifiedBuf = new byte[12];
   private final byte[] idBuf = new byte[6];
   private final byte[] fileModeBuf = new byte[8];
   private final byte[] lengthBuf = new byte[10];
   static final String BSD_LONGNAME_PREFIX = "#1/";
   private static final int BSD_LONGNAME_PREFIX_LEN = "#1/".length();
   private static final String BSD_LONGNAME_PATTERN = "^#1/\\d+";
   private static final String GNU_STRING_TABLE_NAME = "//";
   private static final String GNU_LONGNAME_PATTERN = "^/\\d+";

   public ArArchiveInputStream(InputStream pInput) {
      this.input = pInput;
      this.closed = false;
   }

   public ArArchiveEntry getNextArEntry() throws IOException {
      if (this.currentEntry != null) {
         long entryEnd = this.entryOffset + this.currentEntry.getLength();
         IOUtils.skip(this, entryEnd - this.offset);
         this.currentEntry = null;
      }

      byte[] expected;
      int read;
      if (this.offset == 0L) {
         byte[] expected = ArchiveUtils.toAsciiBytes("!<arch>\n");
         expected = new byte[expected.length];
         int read = IOUtils.readFully((InputStream)this, (byte[])expected);
         if (read != expected.length) {
            throw new IOException("failed to read header. Occured at byte: " + this.getBytesRead());
         }

         for(read = 0; read < expected.length; ++read) {
            if (expected[read] != expected[read]) {
               throw new IOException("invalid header " + ArchiveUtils.toAsciiString(expected));
            }
         }
      }

      if (this.offset % 2L != 0L && this.read() < 0) {
         return null;
      } else if (this.input.available() == 0) {
         return null;
      } else {
         IOUtils.readFully((InputStream)this, (byte[])this.nameBuf);
         IOUtils.readFully((InputStream)this, (byte[])this.lastModifiedBuf);
         IOUtils.readFully((InputStream)this, (byte[])this.idBuf);
         int userId = this.asInt(this.idBuf, true);
         IOUtils.readFully((InputStream)this, (byte[])this.idBuf);
         IOUtils.readFully((InputStream)this, (byte[])this.fileModeBuf);
         IOUtils.readFully((InputStream)this, (byte[])this.lengthBuf);
         expected = ArchiveUtils.toAsciiBytes("`\n");
         byte[] realized = new byte[expected.length];
         read = IOUtils.readFully((InputStream)this, (byte[])realized);
         if (read != expected.length) {
            throw new IOException("failed to read entry trailer. Occured at byte: " + this.getBytesRead());
         } else {
            int nameLen;
            for(nameLen = 0; nameLen < expected.length; ++nameLen) {
               if (expected[nameLen] != realized[nameLen]) {
                  throw new IOException("invalid entry trailer. not read the content? Occured at byte: " + this.getBytesRead());
               }
            }

            this.entryOffset = this.offset;
            String temp = ArchiveUtils.toAsciiString(this.nameBuf).trim();
            if (isGNUStringTable(temp)) {
               this.currentEntry = this.readGNUStringTable(this.lengthBuf);
               return this.getNextArEntry();
            } else {
               long len = this.asLong(this.lengthBuf);
               if (temp.endsWith("/")) {
                  temp = temp.substring(0, temp.length() - 1);
               } else if (this.isGNULongName(temp)) {
                  nameLen = Integer.parseInt(temp.substring(1));
                  temp = this.getExtendedName(nameLen);
               } else if (isBSDLongName(temp)) {
                  temp = this.getBSDLongName(temp);
                  nameLen = temp.length();
                  len -= (long)nameLen;
                  this.entryOffset += (long)nameLen;
               }

               this.currentEntry = new ArArchiveEntry(temp, len, userId, this.asInt(this.idBuf, true), this.asInt(this.fileModeBuf, 8), this.asLong(this.lastModifiedBuf));
               return this.currentEntry;
            }
         }
      }
   }

   private String getExtendedName(int offset) throws IOException {
      if (this.namebuffer == null) {
         throw new IOException("Cannot process GNU long filename as no // record was found");
      } else {
         for(int i = offset; i < this.namebuffer.length; ++i) {
            if (this.namebuffer[i] == 10 || this.namebuffer[i] == 0) {
               if (this.namebuffer[i - 1] == 47) {
                  --i;
               }

               return ArchiveUtils.toAsciiString(this.namebuffer, offset, i - offset);
            }
         }

         throw new IOException("Failed to read entry: " + offset);
      }
   }

   private long asLong(byte[] byteArray) {
      return Long.parseLong(ArchiveUtils.toAsciiString(byteArray).trim());
   }

   private int asInt(byte[] byteArray) {
      return this.asInt(byteArray, 10, false);
   }

   private int asInt(byte[] byteArray, boolean treatBlankAsZero) {
      return this.asInt(byteArray, 10, treatBlankAsZero);
   }

   private int asInt(byte[] byteArray, int base) {
      return this.asInt(byteArray, base, false);
   }

   private int asInt(byte[] byteArray, int base, boolean treatBlankAsZero) {
      String string = ArchiveUtils.toAsciiString(byteArray).trim();
      return string.length() == 0 && treatBlankAsZero ? 0 : Integer.parseInt(string, base);
   }

   public ArchiveEntry getNextEntry() throws IOException {
      return this.getNextArEntry();
   }

   public void close() throws IOException {
      if (!this.closed) {
         this.closed = true;
         this.input.close();
      }

      this.currentEntry = null;
   }

   public int read(byte[] b, int off, int len) throws IOException {
      int toRead = len;
      if (this.currentEntry != null) {
         long entryEnd = this.entryOffset + this.currentEntry.getLength();
         if (len <= 0 || entryEnd <= this.offset) {
            return -1;
         }

         toRead = (int)Math.min((long)len, entryEnd - this.offset);
      }

      int ret = this.input.read(b, off, toRead);
      this.count(ret);
      this.offset += ret > 0 ? (long)ret : 0L;
      return ret;
   }

   public static boolean matches(byte[] signature, int length) {
      if (length < 8) {
         return false;
      } else if (signature[0] != 33) {
         return false;
      } else if (signature[1] != 60) {
         return false;
      } else if (signature[2] != 97) {
         return false;
      } else if (signature[3] != 114) {
         return false;
      } else if (signature[4] != 99) {
         return false;
      } else if (signature[5] != 104) {
         return false;
      } else if (signature[6] != 62) {
         return false;
      } else {
         return signature[7] == 10;
      }
   }

   private static boolean isBSDLongName(String name) {
      return name != null && name.matches("^#1/\\d+");
   }

   private String getBSDLongName(String bsdLongName) throws IOException {
      int nameLen = Integer.parseInt(bsdLongName.substring(BSD_LONGNAME_PREFIX_LEN));
      byte[] name = new byte[nameLen];
      int read = IOUtils.readFully((InputStream)this, (byte[])name);
      if (read != nameLen) {
         throw new EOFException();
      } else {
         return ArchiveUtils.toAsciiString(name);
      }
   }

   private static boolean isGNUStringTable(String name) {
      return "//".equals(name);
   }

   private ArArchiveEntry readGNUStringTable(byte[] length) throws IOException {
      int bufflen = this.asInt(length);
      this.namebuffer = new byte[bufflen];
      int read = IOUtils.readFully(this, this.namebuffer, 0, bufflen);
      if (read != bufflen) {
         throw new IOException("Failed to read complete // record: expected=" + bufflen + " read=" + read);
      } else {
         return new ArArchiveEntry("//", (long)bufflen);
      }
   }

   private boolean isGNULongName(String name) {
      return name != null && name.matches("^/\\d+");
   }
}
