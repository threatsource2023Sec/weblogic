package com.octetstring.tools;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

public class VerifyDataFiles {
   private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   private static final String TRANFILE_NAME = "EmbeddedLDAP.tran";
   private static final String READ_POSFILE_NAME = "EmbeddedLDAP.trpos";
   private static final String WRITE_POSFILE_NAME = "EmbeddedLDAP.twpos";
   private static final String DATAFILE_NAME = "EmbeddedLDAP.data";
   private static final String INDEXFILE_NAME = "EmbeddedLDAP.index";
   private static final String DELETEFILE_NAME = "EmbeddedLDAP.delete";
   private static final int CHANGE_ADD = 1;
   private static final int CHANGE_MOD = 2;
   private static final int CHANGE_DEL = 3;
   private static final int CHANGE_REN = 4;
   private static String[] CHANGE_TYPES = new String[]{"ADD", "MODIFY", "DELETE", "RENAME"};
   private static RandomAccessFile tranfile = null;
   private static RandomAccessFile readposfile = null;
   private static RandomAccessFile writeposfile = null;
   private static RandomAccessFile datafile = null;
   private static RandomAccessFile indexfile = null;
   private static RandomAccessFile deletefile = null;
   private static final int COLS_PER_ROW = 16;
   private static final int BYTES_PER_ROW = 32;

   public static void main(String[] args) {
      try {
         openTransactionLogFiles();
         openDataFiles();
         processDataFiles();
         processTransationLogFiles();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private static void openTransactionLogFiles() throws Exception {
      tranfile = new RandomAccessFile("EmbeddedLDAP.tran", "rw");
      readposfile = new RandomAccessFile("EmbeddedLDAP.trpos", "rw");
      writeposfile = new RandomAccessFile("EmbeddedLDAP.twpos", "rw");
      System.out.println("tranfile.length: " + tranfile.length());
      System.out.println("read posfile.length: " + readposfile.length());
      System.out.println("write posfile.length: " + writeposfile.length());
   }

   private static void openDataFiles() throws Exception {
      datafile = new RandomAccessFile("EmbeddedLDAP.data", "rw");
      indexfile = new RandomAccessFile("EmbeddedLDAP.index", "rw");
      deletefile = new RandomAccessFile("EmbeddedLDAP.delete", "rw");
      System.out.println("datafile.length: " + datafile.length());
      System.out.println("indexfile.length: " + indexfile.length());
      System.out.println("deletefile.length: " + deletefile.length());
   }

   private static void processTransationLogFiles() throws Exception {
      long readpos = 0L;
      long writepos = 0L;
      int idx = 0;

      try {
         if (readposfile.length() > 0L) {
            readposfile.seek(0L);
            readpos = readposfile.readLong();
            System.out.println("read position: " + readpos);
         }

         if (readpos >= tranfile.length()) {
            readpos = 0L;
         }

         if (writeposfile.length() > 0L) {
            writeposfile.seek(0L);
            writepos = writeposfile.readLong();
            System.out.println("write position: " + writepos);
         }

         System.out.println("Seek to position: " + readpos + " in the tran file");
         tranfile.seek(readpos);
      } catch (IOException var15) {
         var15.printStackTrace();
      }

      byte[] ctbyte = new byte[1];
      int changeType = false;
      long startPos = 0L;

      while(readpos < tranfile.length()) {
         try {
            ++idx;
            if (null == tranfile) {
               long var10000 = 0L;
            } else {
               tranfile.length();
            }

            System.out.println("Index: " + idx + " Seek to position: " + readpos + " in the tran file");
            tranfile.seek(readpos);
            tranfile.readFully(ctbyte, 0, 1);
            ++readpos;
            int changeType = ctbyte[0];
            System.out.println("change type: " + getChangeType(changeType));
            int renameId;
            byte[] endbytes;
            byte[] newname;
            if (changeType != 1 && changeType != 2) {
               if (changeType == 3) {
                  renameId = tranfile.readInt();
                  System.out.println("delete id" + renameId);
                  endbytes = new byte[4];
                  tranfile.readFully(endbytes, 0, 4);
                  readpos += 8L;
                  if (endbytes[0] == -1 && endbytes[1] == -1 && endbytes[2] == -1 && endbytes[3] == -1) {
                     System.out.println("delete end tx is valid:");
                  } else {
                     System.out.println("delete end tx is NOT valid:");
                  }
               } else if (changeType == 4) {
                  renameId = tranfile.readInt();
                  int newnamelen = tranfile.readInt();
                  System.out.println("rename id: " + renameId + " new name len: " + newnamelen);
                  newname = new byte[newnamelen];
                  tranfile.readFully(newname, 0, newnamelen);
                  byte[] endbytes = new byte[4];
                  tranfile.readFully(endbytes, 0, 4);
                  readpos = readpos + 12L + (long)newnamelen;
                  if (endbytes[0] == -1 && endbytes[1] == -1 && endbytes[2] == -1 && endbytes[3] == -1) {
                     System.out.println("rename end tx is valid:");
                  } else {
                     System.out.println("rename end tx is NOT valid:");
                  }
               } else {
                  System.out.println("***** Invalid change type:" + changeType + " pos: " + readpos);
               }
            } else {
               renameId = tranfile.readInt();
               System.out.println("entity length: " + renameId);
               endbytes = new byte[renameId];
               tranfile.readFully(endbytes, 0, renameId);
               newname = new byte[4];
               tranfile.readFully(newname, 0, 4);
               readpos = readpos + (long)renameId + 8L;
               if (readpos < 10000000L) {
                  String buf = dump(endbytes, 0, renameId);
                  System.out.println(buf);
               }

               if (newname[0] == -1 && newname[1] == -1 && newname[2] == -1 && newname[3] == -1) {
                  System.out.println("end tx is valid:");
               } else {
                  System.out.println("end tx is NOT valid:");
               }
            }
         } catch (Exception var16) {
            throw var16;
         }
      }

   }

   private static void processDataFiles() throws Exception {
      int highEid = (int)(indexfile.length() / 12L) - 1;
      indexfile.seek(12L);
      System.out.println("Processing index file: high Eid: " + highEid);

      int highDelete;
      for(highDelete = 1; highDelete <= highEid; ++highDelete) {
         long loc = indexfile.readLong();
         int len = indexfile.readInt();
         System.out.println("idx: " + highDelete + " location: " + loc + " length: " + len);
      }

      highDelete = (int)(deletefile.length() / 12L);
      System.out.println("Processing delete file: high delete: " + highDelete);

      long loc;
      int len;
      int i;
      for(i = 1; i <= highDelete; ++i) {
         loc = deletefile.readLong();
         len = deletefile.readInt();
         System.out.println("delete idx: " + i + " location: " + loc + " length: " + len);
      }

      System.out.println("Processing data file: high entry: " + highEid);
      indexfile.seek(12L);

      for(i = 1; i <= highEid; ++i) {
         loc = indexfile.readLong();
         len = indexfile.readInt();
         System.out.println("data idx: " + i + " location: " + loc + " length: " + len);
         if (loc == -1L && len == 0) {
            System.out.println("deleted entry");
         } else {
            datafile.seek(loc);
            byte[] entryBytes = new byte[len];
            datafile.read(entryBytes, 0, len);
            String buf = dump(entryBytes, 0, len);
            System.out.println(buf);
         }
      }

   }

   private static String getChangeType(int type) {
      return type <= CHANGE_TYPES.length && type > 0 ? CHANGE_TYPES[type - 1] : "UNKNOWN";
   }

   public static String dump(byte[] bytes) {
      return bytes == null ? dump(new byte[0], 0, 0) : dump(bytes, 0, bytes.length);
   }

   public static String dump(byte[] bytes, int offset, int len) {
      if (offset < 0) {
         offset = 0;
      }

      int end = Math.min(bytes.length, offset + len);
      int displayStart = offset & -32;
      int displayEnd = end + 31 & -32;
      StringBuffer out = new StringBuffer();
      int rowStart = displayStart;

      for(int i = displayStart; i < displayEnd; ++i) {
         if (i % 32 == 0) {
            lineLabel(out, i);
            rowStart = i;
         }

         if (i >= offset && i < end) {
            out.append(asHex(bytes[i]));
         } else {
            out.append("  ");
         }

         if (i % 2 == 1) {
            out.append(' ');
         }

         if (i % 32 == 31) {
            out.append("  ");

            for(int j = rowStart; j < rowStart + 32; ++j) {
               if (j >= offset && j < end) {
                  out.append(toPrint(bytes[j]));
               } else {
                  out.append(' ');
               }
            }

            out.append('\n');
         }
      }

      return out.toString();
   }

   public static String asHex(int b) {
      char[] buf = new char[]{HEX_CHARS[(b & 240) >> 4], HEX_CHARS[(b & 15) >> 0]};
      return new String(buf);
   }

   public static String asHex(byte[] barray, int len) {
      return asHex(barray, len, true);
   }

   public static String asHex(byte[] barray, int len, boolean prefix) {
      return new String(asHexBytes(barray, len, prefix), 0);
   }

   public static byte[] asHexBytes(byte[] barray, int len, boolean prefix) {
      int loopLen = Math.min(len, barray.length);
      int j = 0;
      byte[] buf;
      if (prefix) {
         buf = new byte[loopLen * 2 + 2];
         buf[0] = 48;
         buf[1] = 120;
         j += 2;
      } else {
         buf = new byte[loopLen * 2];
      }

      for(int i = 0; i < loopLen; ++i) {
         buf[j++] = (byte)HEX_CHARS[(barray[i] & 240) >> 4];
         buf[j++] = (byte)HEX_CHARS[(barray[i] & 15) >> 0];
      }

      return buf;
   }

   public static String asHex(byte[] barray) {
      return asHex(barray, barray.length);
   }

   public static String asHex(String s, String encoding) throws UnsupportedEncodingException {
      byte[] b = s.getBytes(encoding);
      return asHex(b);
   }

   private static char toPrint(byte b) {
      return b >= 32 && b <= 126 ? (char)b : '.';
   }

   private static void lineLabel(StringBuffer out, int i) {
      String istring = (new Integer(i)).toString();
      StringBuffer sb;
      if (istring.length() <= 5) {
         sb = new StringBuffer("    ");
         sb.insert(5 - istring.length(), istring);
         sb.setLength(5);
      } else {
         sb = new StringBuffer(istring);
      }

      out.append(sb);
      out.append(": ");
   }
}
