package weblogic.transaction.internal;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import weblogic.management.DomainDir;
import weblogic.store.PersistentStore;
import weblogic.transaction.TransactionLoggable;
import weblogic.utils.StackTraceUtils;

public final class TransactionLogUpgradeHelper {
   private final ArrayList abbrevTable = new ArrayList();
   private final List fileList = new LinkedList();

   public void attemptUpgrade(PersistentStore store, String aFilePrefix, String serverName, String rootDirectory) throws IOException, Exception {
      StoreTransactionLoggerImpl tlog = new StoreTransactionLoggerImpl(store);
      File hdrFile = this.findOldTLOG(aFilePrefix, serverName, rootDirectory);
      if (hdrFile != null) {
         List recoveredObjects = this.recover();
         tlog.store(recoveredObjects);
         hdrFile.delete();
         this.delete();
      }

   }

   public File findOldTLOG(String aFilePrefix, String serverName, String rootDirectory) throws IOException {
      aFilePrefix = aFilePrefix + serverName;
      String root = this.addSeparator(rootDirectory);
      String defaultLogPath = getRelativePrefixRelativeServerDir(serverName);
      if (defaultLogPath.length() > 0 && !defaultLogPath.endsWith("/") && !defaultLogPath.endsWith(File.separatorChar + "")) {
         defaultLogPath = defaultLogPath + File.separatorChar;
      }

      if (TxDebug.JTATLOG.isDebugEnabled()) {
         TxDebug.JTATLOG.debug("Looking for TLOG: DefaultLogPath=\"" + defaultLogPath + "\", RootDir=\"" + root + "\"");
      }

      if (this.isRelativePath(aFilePrefix)) {
         try {
            return this.readHeader(defaultLogPath + aFilePrefix + ".");
         } catch (FileNotFoundException var11) {
            try {
               return this.readHeader(root + defaultLogPath + aFilePrefix + ".");
            } catch (FileNotFoundException var10) {
               try {
                  return this.readHeader(root + aFilePrefix + ".");
               } catch (FileNotFoundException var9) {
                  try {
                     return this.readHeader(aFilePrefix);
                  } catch (FileNotFoundException var8) {
                  }
               }
            }
         }
      } else {
         try {
            return this.readHeader(aFilePrefix + ".");
         } catch (FileNotFoundException var12) {
            try {
               return this.readHeader(aFilePrefix);
            } catch (FileNotFoundException var7) {
            }
         }
      }

      return null;
   }

   private File readHeader(String filePrefix) throws IOException {
      String headerFileName = filePrefix + "0000.tlog";
      String headerFileNameTmp = filePrefix + "0000.tmp";
      if (TxDebug.JTATLOG.isDebugEnabled()) {
         TxDebug.JTATLOG.debug("TLOG header: reading, fname=" + headerFileName);
      }

      File f = new File(headerFileName);
      if (!f.exists()) {
         f = new File(headerFileNameTmp);
         if (!f.exists()) {
            if (TxDebug.JTATLOG.isDebugEnabled()) {
               TxDebug.JTATLOG.debug("TLOG header: file not found, headerFileName=" + headerFileName);
            }

            throw new FileNotFoundException(headerFileName);
         }

         TXLogger.logTLOGRecoveredBackupHeader(headerFileNameTmp);
      }

      byte[] buf = this.readFile(f);
      if (buf.length == 0) {
         return f;
      } else {
         UpgradeLogDataInputImpl decoder = new UpgradeLogDataInputImpl(new LogByteArrayInputStream(buf));
         int version = decoder.readNonNegativeInt();
         if (TxDebug.JTATLOG.isDebugEnabled()) {
            TxDebug.JTATLOG.debug("TLOG header: read version=" + version);
         }

         if (version != 0) {
            TXLogger.logTLOGUnrecognizedHeaderVersionNumber();
            return f;
         } else {
            int nextFileID = decoder.readNonNegativeInt();
            if (TxDebug.JTATLOG.isDebugEnabled()) {
               TxDebug.JTATLOG.debug("TLOG header: read nextFileID=" + nextFileID);
            }

            int cnt = decoder.readNonNegativeInt();
            if (TxDebug.JTATLOG.isDebugEnabled()) {
               TxDebug.JTATLOG.debug("TLOG header: read file cnt=" + cnt);
            }

            int i;
            for(i = 0; i < cnt; ++i) {
               int id = decoder.readNonNegativeInt();
               if (TxDebug.JTATLOG.isDebugEnabled()) {
                  TxDebug.JTATLOG.debug("TLOG header: read active file id[" + i + "]=" + id);
               }

               this.fileList.add(this.makeName(filePrefix, id));
            }

            cnt = decoder.readNonNegativeInt();
            if (TxDebug.JTATLOG.isDebugEnabled()) {
               TxDebug.JTATLOG.debug("TLOG header: read abbreviation cnt=" + cnt);
            }

            for(i = 0; i < cnt; ++i) {
               String abbrev = decoder.readString();
               if (TxDebug.JTATLOG.isDebugEnabled()) {
                  TxDebug.JTATLOG.debug("TLOG header: read abbreviation[" + i + "]=" + abbrev);
               }

               this.abbrevTable.add(abbrev);
            }

            return f;
         }
      }
   }

   List recover() throws IOException {
      List ret = new LinkedList();
      Iterator i = this.fileList.iterator();

      while(i.hasNext()) {
         this.recoverFile(ret, (String)i.next());
      }

      return ret;
   }

   void delete() throws IOException {
      Iterator i = this.fileList.iterator();

      while(i.hasNext()) {
         File f = new File((String)i.next());
         f.delete();
      }

   }

   private void recoverFile(List recoverList, String fname) throws IOException {
      if (TxDebug.JTATLOG.isDebugEnabled()) {
         TxDebug.JTATLOG.debug("TLOG file: recovering, fname=" + fname);
      }

      File f = new File(fname);
      if (!f.exists()) {
         TXLogger.logTLOGMissing(fname);
      } else {
         byte[] buf = this.readFile(f);
         UpgradeLogDataInputImpl decoder = new UpgradeLogDataInputImpl(new LogByteArrayInputStream(buf));

         while(decoder.available() > 0) {
            int recLen = 0;
            int recOffset = -1;

            try {
               recLen = decoder.readNonNegativeInt();
               recOffset = decoder.getPos();
            } catch (Exception var11) {
               TXLogger.logTLOGFileReadFormatException(100, fname, var11);
            }

            if (recLen <= 0 || recOffset < 0) {
               TXLogger.logTLOGFileReadFormatError(200, fname);
               break;
            }

            decoder.skip(recLen);

            try {
               TransactionLoggable obj = this.readLogRecord(buf, recOffset, recLen);
               if (obj != null) {
                  if (obj instanceof ResourceCheckpoint) {
                     ((ResourceCheckpoint)obj).convertPre810JTSName();
                  }

                  if (obj instanceof ServerTransactionImpl) {
                     ((ServerTransactionImpl)obj).convertPre810JTSName();
                  }

                  recoverList.add(obj);
               } else {
                  TXLogger.logTLOGFileReadFormatError(300, fname);
               }
            } catch (Exception var10) {
               TXLogger.logTLOGFileReadFormatException(400, fname, var10);
            }
         }

      }
   }

   private TransactionLoggable readLogRecord(byte[] buf, int offset, int len) {
      if (offset + len <= buf.length && len >= 5) {
         try {
            CRC32 crc = new CRC32();
            crc.update(buf, offset, len - 4);
            long checksum = crc.getValue();
            long verify = ((long)buf[offset + len - 4] & 255L) << 24;
            verify += ((long)buf[offset + len - 3] & 255L) << 16;
            verify += ((long)buf[offset + len - 2] & 255L) << 8;
            verify += (long)buf[offset + len - 1] & 255L;
            if (checksum != verify) {
               TXLogger.logTLOGRecordChecksumMismatch(1);
               if (TxDebug.JTATLOG.isDebugEnabled()) {
                  TxDebug.JTATLOG.debug("Mismatch!  checksum=" + checksum + ", verify=" + verify);
               }

               return null;
            }
         } catch (Exception var13) {
            TXLogger.logTLOGRecordChecksumException(2, var13);
            return null;
         }

         UpgradeLogDataInputImpl decoder;
         try {
            decoder = new UpgradeLogDataInputImpl(new LogByteArrayInputStream(buf, offset));
         } catch (IOException var12) {
            return null;
         }

         String className = decoder.readAbbrevString();

         TransactionLoggable obj;
         try {
            obj = (TransactionLoggable)Class.forName(className).newInstance();
         } catch (Exception var11) {
            TXLogger.logTLOGRecordClassInstantiationException(className, var11);
            return null;
         }

         try {
            obj.readExternal(decoder);
         } catch (Throwable var10) {
            TXLogger.logTLOGReadExternalException(className, var10);
            return null;
         }

         if (TxDebug.JTATLOG.isDebugEnabled()) {
            TxDebug.JTATLOG.debug("TLOG read log record, obj=" + obj);
         }

         return obj;
      } else {
         TXLogger.logTLOGReadChecksumError();
         return null;
      }
   }

   private byte[] readFile(File f) {
      byte[] buf = new byte[0];
      FileInputStream fis = null;

      try {
         buf = new byte[(int)f.length()];
         fis = new FileInputStream(f);
         int off = 0;

         int numRead;
         do {
            numRead = fis.read(buf, off, buf.length - off);
            if (numRead > 0) {
               off += numRead;
            }
         } while(numRead > 0);
      } catch (Exception var14) {
         TXLogger.logTLOGFileReadError(f.getAbsolutePath(), var14);
      } finally {
         try {
            if (fis != null) {
               fis.close();
            }
         } catch (Exception var13) {
            if (TxDebug.JTATLOG.isDebugEnabled()) {
               TxDebug.JTATLOG.debug("TLOG header: close exception, fname=" + f.getAbsolutePath(), var13);
            }
         }

      }

      return buf;
   }

   private String makeName(String filePrefix, int num) {
      String[] prefix = new String[]{"000", "00", "0", ""};
      String s = Integer.toString(num);
      return filePrefix + prefix[s.length() - 1] + s + ".tlog";
   }

   private String addSeparator(String dir) {
      if (dir == null) {
         return "";
      } else {
         return dir.length() > 0 && !dir.endsWith("/") && !dir.endsWith(File.separatorChar + "") ? dir + File.separatorChar : dir;
      }
   }

   private boolean isRelativePath(String p) {
      if (p != null && p.length() != 0) {
         File f = new File(p);
         if (f.isAbsolute()) {
            return false;
         } else {
            return !f.toString().startsWith(File.separatorChar + "");
         }
      } else {
         return true;
      }
   }

   private static String getRelativePrefixRelativeServerDir(String serverName) {
      return DomainDir.getRootDir() + File.separator + serverName;
   }

   private final class UpgradeLogDataInputImpl extends DataInputStream implements LogDataInput {
      private final LogByteArrayInputStream inStream;

      public UpgradeLogDataInputImpl(LogByteArrayInputStream ais) throws IOException {
         super(ais);
         this.inStream = ais;
      }

      public int getPos() {
         return this.inStream.getPos();
      }

      public void skip(int bytes) {
         this.inStream.skip((long)bytes);
      }

      private byte readByteIgnore() {
         try {
            return super.readByte();
         } catch (IOException var2) {
            return 0;
         }
      }

      private int readIntIgnore() {
         try {
            return super.readInt();
         } catch (IOException var2) {
            return 0;
         }
      }

      public int readNonNegativeInt() {
         int ans = 0;

         byte b;
         do {
            b = this.readByteIgnore();
            ans = ans << 7 | b & 127;
         } while((b & 128) != 0);

         return ans;
      }

      public String readString() {
         int len = this.readNonNegativeInt();
         if (len > this.inStream.available()) {
            throw new RuntimeException("transaction log decoder:  String(" + len + ") too big");
         } else {
            StringBuffer ans = new StringBuffer(len);

            for(int i = 0; i < len; ++i) {
               ans.append((char)this.readNonNegativeInt());
            }

            return ans.toString();
         }
      }

      public String readAbbrevString() {
         int len = this.readNonNegativeInt();
         if (len == 0) {
            int num = this.readNonNegativeInt();
            return num == 0 ? "" : (String)TransactionLogUpgradeHelper.this.abbrevTable.get(num - 1);
         } else if (len > this.inStream.available()) {
            throw new RuntimeException("transaction log decoder:  String(" + len + ") too big");
         } else {
            StringBuffer ans = new StringBuffer(len);

            for(int i = 0; i < len; ++i) {
               ans.append((char)this.readNonNegativeInt());
            }

            return ans.toString();
         }
      }

      public byte[] readByteArray() {
         int numBytes = this.readNonNegativeInt();
         if (numBytes <= 0) {
            return null;
         } else if (numBytes > this.inStream.available()) {
            throw new RuntimeException("transaction log decoder:  String(" + numBytes + ") too big");
         } else {
            byte[] barray = new byte[numBytes];

            for(int i = 0; i < numBytes; ++i) {
               barray[i] = this.readByteIgnore();
            }

            return barray;
         }
      }

      public Map readProperties() throws IOException {
         HashMap props = new HashMap(5);
         int numProperties = this.readNonNegativeInt();

         for(int i = 0; i < numProperties; ++i) {
            String propName = this.readAbbrevString();
            if (propName == null || propName.equals("")) {
               throw new InvalidObjectException("transaction log record: missing property name");
            }

            int propType = this.readNonNegativeInt();
            XidImpl xid;
            switch (propType) {
               case 1:
                  String propStringVal = this.readString();
                  props.put(propName, propStringVal);
                  break;
               case 2:
                  String propIntVal = this.readString();
                  props.put(propName, new Integer(propIntVal));
                  break;
               case 3:
                  xid = XidImpl.create(this.readNonNegativeInt(), this.readByteArray(), this.readByteArray());
                  props.put(propName, xid);
                  break;
               case 4:
                  xid = XidImpl.create(this.readIntIgnore(), this.readByteArray(), this.readByteArray());
                  props.put(propName, xid);
                  break;
               case 5:
                  props.put(propName, new Integer(this.readIntIgnore()));
                  break;
               case 6:
                  props.put(propName, this.readObject());
                  break;
               default:
                  throw new InvalidObjectException("transaction log record: bad property type " + propType + " for property:" + propName);
            }
         }

         return props;
      }

      private Object readObject() throws IOException {
         ObjectInputStream is = new ObjectInputStream(this.inStream);

         try {
            return is.readObject();
         } catch (ClassNotFoundException var3) {
            throw new IOException(StackTraceUtils.throwable2StackTrace(var3));
         }
      }
   }

   private final class LogByteArrayInputStream extends ByteArrayInputStream {
      LogByteArrayInputStream(byte[] buf) {
         super(buf);
      }

      LogByteArrayInputStream(byte[] buf, int offset) {
         super(buf, offset, buf.length - offset);
      }

      int getPos() {
         return this.pos;
      }
   }
}
