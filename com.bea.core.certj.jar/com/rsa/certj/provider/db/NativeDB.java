package com.rsa.certj.provider.db;

import codebase.Code4jni;
import codebase.Data4jni;
import codebase.Error4;
import codebase.Error4entry;
import codebase.Error4field;
import codebase.Error4file;
import codebase.Error4locked;
import codebase.Error4message;
import codebase.Error4relateMatch;
import codebase.Error4tagName;
import codebase.Error4unexpected;
import codebase.Error4unique;
import codebase.Error4usage;
import codebase.Field4byteArray;
import codebase.Field4deleteFlag;
import codebase.Field4info;
import codebase.Field4stringBuffer;
import codebase.Tag4info;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJException;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.NotSupportedException;
import com.rsa.certj.Provider;
import com.rsa.certj.ProviderImplementation;
import com.rsa.certj.ProviderManagementException;
import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.spi.db.DatabaseInterface;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_MessageDigest;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_SymmetricCipher;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/** @deprecated */
public final class NativeDB extends Provider {
   private static final String CDX_FILE_TYPE = ".cdx";
   private static final String DBF_FILE_TYPE = ".dbf";
   private static final String FPT_FILE_TYPE = ".fpt";
   private static final char CERT_FILE_PREFIX = 'c';
   private static final char CRL_FILE_PREFIX = 'r';
   private static final char KEY_FILE_PREFIX = 'p';
   static final String SUBJECT_FIELD_NAME = "SUBJECT";
   static final String ISSUER_FIELD_NAME = "ISSUER";
   static final String SERIAL_FIELD_NAME = "SERIAL";
   static final String CERT_FIELD_NAME = "CERT";
   static final String CRL_ISSUER_FIELD_NAME = "ISSUER";
   static final String THIS_UPDATE_FIELD_NAME = "LAST";
   static final String CRL_FIELD_NAME = "CRL";
   static final String SPKI_FIELD_NAME = "SPKI";
   static final String KEY_FIELD_NAME = "KEY";
   static final String SALT_FIELD_NAME = "SALT";
   static final String IV_FIELD_NAME = "IV";
   private static final String SUBJECT_TAG_NAME = "SUBJECT";
   private static final String ISN_TAG_NAME = "ISN";
   private static final String ILU_TAG_NAME = "ILU";
   private static final String SPKI_TAG_NAME = "SPKI";
   private static final String NOT_DELETED_FILTER = ".NOT.DELETED()";
   private static final int NAME_LEN = 128;
   private static final int SUBJECT_LEN = 128;
   private static final int ISSUER_LEN = 128;
   private static final int SERIAL_LEN = 32;
   private static final int THIS_UPDATE_LEN = 4;
   private static final int SPKI_LEN = 20;
   private static final int SALT_LEN = 8;
   private static final int IV_LEN = 8;
   private static final byte KEY_TYPE_RSA = 0;
   private static final byte KEY_TYPE_DSA = 1;
   private static final String MASTER_TABLE_NAME = "rsadb";
   private static final String DATABASE_NAME_FIELD_NAME = "NAME";
   private static final String DATABASE_ID_FIELD_NAME = "ID";
   private static final String DATABASE_NAME_TAG_NAME = "NAME";
   private static final int DATABASE_ID_LEN = 7;
   private static final int DATABASE_NAME_LEN = 32;
   private static final String PBE_CIPHER = "PBE/SHA1/3DES_EDE/CBC/PKCS5V2PBE-1000-3";
   private static final long SCHEMA_VERSION = 3L;
   private static Hashtable masterTableLockHash = new Hashtable();
   private static Hashtable accessHash = new Hashtable();
   private static Code4jni code4;
   private static final Object CODE_4_LOCK = new Object();
   File path;
   String databaseName;
   char[] password;
   boolean newDatabase;

   /** @deprecated */
   public static boolean create(String var0, String var1) throws InvalidParameterException, DatabaseException {
      if (var0 == null) {
         throw new InvalidParameterException("PathString should not be null.");
      } else {
         return create(new File(var0), var1);
      }
   }

   /** @deprecated */
   public static boolean create(File var0, String var1) throws InvalidParameterException, DatabaseException {
      if (var0 == null) {
         throw new InvalidParameterException("Path should not be null.");
      } else if (var1 == null) {
         throw new InvalidParameterException("DatabaseName should not be null.");
      } else {
         return createDatabase(var0, var1);
      }
   }

   /** @deprecated */
   public static boolean delete(String var0, String var1) throws InvalidParameterException, DatabaseException {
      if (var0 == null) {
         throw new InvalidParameterException("PathString should not be null.");
      } else {
         return delete(new File(var0), var1);
      }
   }

   /** @deprecated */
   public static boolean delete(File var0, String var1) throws InvalidParameterException, DatabaseException {
      if (var0 == null) {
         throw new InvalidParameterException("Path should not be null.");
      } else if (var1 == null) {
         throw new InvalidParameterException("DatabaseName should not be null.");
      } else {
         return removeDatabase(var0, var1);
      }
   }

   /** @deprecated */
   public static String[] listAllDatabaseNames(String var0) throws InvalidParameterException, DatabaseException {
      if (var0 == null) {
         throw new InvalidParameterException("PathString should not be null.");
      } else {
         return listAllDatabaseNames(new File(var0));
      }
   }

   /** @deprecated */
   public static String[] listAllDatabaseNames(File var0) throws InvalidParameterException, DatabaseException {
      if (var0 == null) {
         throw new InvalidParameterException("Path should not be null.");
      } else if (!var0.exists()) {
         throw new DatabaseException("Path " + var0.toString() + " does not exist.");
      } else {
         Code4jni var1 = getCode4();
         synchronized(getMasterTableLock(var0)) {
            Data4jni var3 = openMasterTable(var1, var0);

            try {
               lock(var3);
               int var4 = var3.recCount();
               ArrayList var5 = new ArrayList();
               Field4stringBuffer var6 = new Field4stringBuffer(var3, "NAME");
               new Field4stringBuffer(var3, "ID");
               Field4deleteFlag var7 = new Field4deleteFlag(var3);

               for(int var8 = 1; var8 <= var4; ++var8) {
                  var3.go(var8);
                  if (!var7.deleted) {
                     var5.add(decodeDatabaseName(var6.contents));
                  }
               }

               String[] var19 = (String[])var5.toArray(new String[var5.size()]);
               return var19;
            } catch (IOException var15) {
               throw new DatabaseException(var15);
            } catch (Error4 var16) {
               throw new DatabaseException(error4Message(var16));
            } finally {
               close(var3);
            }
         }
      }
   }

   /** @deprecated */
   public NativeDB(String var1, String var2, String var3, char[] var4, boolean var5) throws InvalidParameterException {
      super(1, var1);
      if (var2 == null) {
         throw new InvalidParameterException("NativeDB.NativeDB: pathString should not be null.");
      } else if (var3 == null) {
         throw new InvalidParameterException("NativeDB.NativeDB: databaseName should not be null.");
      } else {
         this.path = new File(var2);
         this.databaseName = var3;
         this.password = var4;
         this.newDatabase = var5;
      }
   }

   /** @deprecated */
   public NativeDB(String var1, File var2, String var3, char[] var4, boolean var5) throws InvalidParameterException {
      super(1, var1);
      if (var2 == null) {
         throw new InvalidParameterException("NativeDB.NativeDB: path should not be null.");
      } else if (var3 == null) {
         throw new InvalidParameterException("NativeDB.NativeDB: databaseName should not be null.");
      } else {
         this.path = var2;
         this.databaseName = var3;
         this.password = var4;
         this.newDatabase = var5;
      }
   }

   /** @deprecated */
   public ProviderImplementation instantiate(CertJ var1) throws ProviderManagementException {
      try {
         if (this.newDatabase) {
            boolean var2 = createDatabase(this.path, this.databaseName);
            if (!var2) {
               throw new ProviderManagementException("Creation of database failed. Check if the database already exists. If so, delete the database first if you want to create a new database with the same name, or create NativeDB with newDatabase being false if you want to use the existing database.");
            }
         }

         return new b(var1, this.getName());
      } catch (CertJException var3) {
         throw new ProviderManagementException(var3);
      }
   }

   private static Code4jni getCode4() throws DatabaseException {
      synchronized(CODE_4_LOCK) {
         if (code4 != null) {
            return code4;
         } else {
            try {
               code4 = new Code4jni();
            } catch (IOException var4) {
               throw new DatabaseException("NativeDB.getCode4: Java Native Interface access failed.", var4);
            } catch (UnsatisfiedLinkError var5) {
               throw new DatabaseException("NativeDB.getCode4: Java Native Interface access failed. You need to install NativeDB library for your platform." + var5.getMessage() + ").");
            }

            code4.safety(false);

            try {
               code4.accessMode((byte)1);
            } catch (Error4usage var3) {
               throw new DatabaseException("NativeDB.getCode4: unable to get exclusive lock on database" + error4Message(var3));
            }

            return code4;
         }
      }
   }

   private static String findExistingId(Code4jni var0, File var1, String var2) throws DatabaseException {
      synchronized(getMasterTableLock(var1)) {
         Data4jni var4 = openMasterTable(var0, var1);

         String var7;
         try {
            lock(var4);
            Field4stringBuffer var5 = new Field4stringBuffer(var4, "ID");
            int var6 = getMasterRecord(var4, var2);
            if (var6 != 0) {
               var4.go(var6);
               var7 = var5.contents.toString();
               return var7;
            }

            var7 = null;
         } catch (IOException var14) {
            throw new DatabaseException("NativeDB.findExistingId.", var14);
         } catch (Error4 var15) {
            throw new DatabaseException("NativeDB.findExistingId: " + error4Message(var15));
         } finally {
            close(var4);
         }

         return var7;
      }
   }

   private static String addDatabase(Code4jni var0, File var1, String var2) throws DatabaseException {
      if (findExistingId(var0, var1, var2) != null) {
         return null;
      } else {
         String[] var3;
         try {
            var3 = listAllDatabaseNames(var1);
         } catch (InvalidParameterException var21) {
            throw new DatabaseException(var21);
         }

         if (var3.length > 0) {
            throw new DatabaseException("Another database already exists with different name, delete old one first before creating a new one in " + var1.getAbsolutePath());
         } else {
            synchronized(getMasterTableLock(var1)) {
               Data4jni var5 = openMasterTable(var0, var1);

               String var13;
               try {
                  lock(var5);
                  Field4byteArray var6 = new Field4byteArray(var5, "NAME");
                  Field4stringBuffer var7 = new Field4stringBuffer(var5, "ID");
                  Field4deleteFlag var8 = new Field4deleteFlag(var5);
                  int var9 = getMasterRecord(var5, var2);
                  StringBuffer var10;
                  if (var9 != 0) {
                     var10 = null;
                     return var10;
                  }

                  var5.go(1);
                  var10 = var7.contents;
                  var9 = findAvailableMasterRecord(var5);
                  var5.go(var9);
                  StringBuffer var11 = encodeDatabaseName(var2);
                  byte[] var12 = var11.toString().getBytes();
                  var6.contents = new byte[33];
                  System.arraycopy(var12, 0, var6.contents, 0, 32);
                  var6.contents[32] = 0;
                  var7.contents = var10;
                  var8.deleted = false;
                  var5.update();
                  var13 = var10.toString();
               } catch (IOException var22) {
                  throw new DatabaseException("NativeDB.addDatabase.", var22);
               } catch (Error4 var23) {
                  throw new DatabaseException("NativeDB.addDatabase: " + error4Message(var23));
               } finally {
                  close(var5);
               }

               return var13;
            }
         }
      }
   }

   private static String deleteDatabase(Code4jni var0, File var1, String var2) throws DatabaseException {
      synchronized(getMasterTableLock(var1)) {
         Data4jni var4 = openMasterTable(var0, var1);

         String var11;
         try {
            lock(var4);
            Field4byteArray var5 = new Field4byteArray(var4, "NAME");
            Field4stringBuffer var6 = new Field4stringBuffer(var4, "ID");
            Field4deleteFlag var7 = new Field4deleteFlag(var4);
            int var8 = getMasterRecord(var4, var2);
            String var9;
            if (var8 == 0) {
               var9 = null;
               return var9;
            }

            var4.go(var8);
            var9 = var6.contents.toString();
            var4.go(1);
            byte[] var10 = var5.contents;
            var5.contents = encodeRecNoAndVersion(var8);
            var4.update();
            var4.go(var8);
            var5.contents = var10;
            var7.deleted = true;
            var4.update();
            var11 = var9;
         } catch (IOException var18) {
            throw new DatabaseException("NativeDB.deleteDatabase.", var18);
         } catch (Error4 var19) {
            throw new DatabaseException("NativeDB.deleteDatabase: " + error4Message(var19));
         } finally {
            close(var4);
         }

         return var11;
      }
   }

   private static Data4jni openMasterTable(Code4jni var0, File var1) throws DatabaseException {
      try {
         String var2 = (new File(var1, "rsadb")).toString();
         Data4jni var3 = new Data4jni(var0);

         try {
            var3.open(var2);
         } catch (IOException var5) {
            throw new DatabaseException("NativeDB.openMasterTable.", var5);
         } catch (Error4 var6) {
            createMasterTable(var3, var2);
            var3.open(var2);
         }

         checkSchemaVersion(var3);
         close(var3);
         var3.open(var2);
         return var3;
      } catch (IOException var7) {
         throw new DatabaseException("NativeDB.openMasterTable.", var7);
      } catch (Error4 var8) {
         throw new DatabaseException("NativeDB.openMasterTable: " + error4Message(var8));
      }
   }

   private static void checkSchemaVersion(Data4jni var0) throws DatabaseException {
      try {
         Field4byteArray var1 = new Field4byteArray(var0, "NAME");
         var0.go(1);
         byte[] var2 = new byte[4];
         System.arraycopy(var1.contents, 4, var2, 0, 4);
         int var3 = decodeInt(var2);
         if ((long)var3 != 3L) {
            throw new DatabaseException("NativeDB.checkSchemaVersion: schema version(" + var3 + ") is wrong(" + 3L + " is expected).");
         }
      } catch (IOException var4) {
         throw new DatabaseException("NativeDB.checkSchemaVersion.", var4);
      } catch (Error4 var5) {
         throw new DatabaseException("NativeDB.checkSchemaVersion: " + error4Message(var5));
      }
   }

   private static void createMasterTable(Data4jni var0, String var1) throws DatabaseException {
      try {
         Field4info var2 = new Field4info();
         Tag4info var3 = new Tag4info();
         var2.add("NAME", 'C', 33, 0, false);
         var2.add("ID", 'C', 7, 0, false);
         var3.add("NAME", "NAME", ".NOT.DELETED()", (byte)20, false);
         var0.create(var1, var2, var3);
         var0.blank();
         var0.append();
         Field4byteArray var4 = new Field4byteArray(var0, "NAME");
         Field4stringBuffer var5 = new Field4stringBuffer(var0, "ID");
         Field4deleteFlag var6 = new Field4deleteFlag(var0);
         var4.contents = encodeRecNoAndVersion(0);
         var5.contents = new StringBuffer("aaaaaaa");
         var6.deleted = true;
         var0.update();
         close(var0);
      } catch (IOException var7) {
         throw new DatabaseException("NativeDB.createMasterFile.", var7);
      } catch (Error4 var8) {
         throw new DatabaseException("NativeDB.createMasterFile: " + error4Message(var8));
      }
   }

   private static byte[] encodeRecNoAndVersion(int var0) {
      byte[] var1 = new byte[33];
      byte[] var2 = encodeLong((long)var0);
      System.arraycopy(var2, 0, var1, 0, 4);
      var2 = encodeLong(3L);
      System.arraycopy(var2, 0, var1, 4, 4);

      for(int var3 = 8; var3 < 33; ++var3) {
         var1[var3] = 32;
      }

      return var1;
   }

   private static int getMasterRecord(Data4jni var0, String var1) throws DatabaseException {
      try {
         var0.select("NAME");
         int var2 = var0.seek(encodeDatabaseName(var1).toString());
         return var2 == 0 ? var0.recNo() : 0;
      } catch (Error4 var3) {
         throw new DatabaseException("NativeDB.getMasterRecord: " + error4Message(var3));
      }
   }

   private static StringBuffer encodeDatabaseName(String var0) {
      if (var0.length() > 32) {
         return new StringBuffer(var0.substring(0, 32));
      } else {
         StringBuffer var1 = new StringBuffer(var0);

         for(int var2 = 0; var2 < 32 - var0.length(); ++var2) {
            var1.append(' ');
         }

         return var1;
      }
   }

   private static String decodeDatabaseName(StringBuffer var0) {
      int var1 = 0;

      for(int var2 = var0.length() - 1; var2 >= 0; --var2) {
         if (var0.charAt(var2) != ' ') {
            var1 = var2 + 1;
            break;
         }
      }

      char[] var3 = new char[var1];
      var0.getChars(0, var1, var3, 0);
      return new String(var3);
   }

   private static int findAvailableMasterRecord(Data4jni var0) throws DatabaseException {
      try {
         Field4byteArray var1 = new Field4byteArray(var0, "NAME");
         var0.go(1);
         byte[] var2 = var1.contents;
         byte[] var3 = new byte[4];
         System.arraycopy(var2, 0, var3, 0, 4);
         int var4 = decodeInt(var3);
         if (var4 == 0) {
            int var8 = var0.bottom();
            if (var8 != 0 && var8 != 3) {
               throw new DatabaseException("NativeDB.findAvailableMasterRecord: error in going to the bottom.");
            } else {
               var0.blank();
               var0.append();
               return var0.recNo();
            }
         } else {
            var0.go(var4);
            byte[] var5 = var1.contents;
            var0.go(1);
            var1.contents = var5;
            var0.update();
            return var4;
         }
      } catch (IOException var6) {
         throw new DatabaseException("NativeDB.findAvailableMasterRecord.", var6);
      } catch (Error4 var7) {
         throw new DatabaseException("NativeDB.findAvailableMasterRecord: " + error4Message(var7));
      }
   }

   private static String databaseName(File var0, String var1, char var2) {
      StringBuffer var3 = new StringBuffer();
      var3.append(var2);
      var3.append(var1);
      return (new File(var0, var3.toString())).toString();
   }

   private static void deleteDatabaseFiles(String var0) {
      File var1 = new File(var0 + ".cdx");
      if (var1.exists()) {
         var1.delete();
      }

      var1 = new File(var0 + ".dbf");
      if (var1.exists()) {
         var1.delete();
      }

      var1 = new File(var0 + ".fpt");
      if (var1.exists()) {
         var1.delete();
      }

   }

   private static byte[] encodeLong(long var0) {
      byte[] var2 = new byte[4];
      long var3 = var0;

      for(int var5 = var2.length - 1; var5 >= 0; --var5) {
         var2[var5] = (byte)((int)(var3 & 255L));
         var3 >>= 8;
      }

      return var2;
   }

   private static int decodeInt(byte[] var0) {
      int var1 = 0;

      for(int var2 = 0; var2 < 4; ++var2) {
         int var3 = var0[var2];
         if (var3 < 0) {
            var3 += 256;
         }

         var1 <<= 8;
         var1 |= var3;
      }

      return var1;
   }

   private static boolean createDatabase(File var0, String var1) throws DatabaseException {
      if (!var0.exists() && !var0.mkdirs()) {
         throw new DatabaseException("Unable to create directory " + var0.toString() + ".");
      } else {
         Code4jni var2 = getCode4();
         String var3 = addDatabase(var2, var0, var1);
         if (var3 == null) {
            return false;
         } else {
            Data4jni var4;
            try {
               var4 = new Data4jni(var2);
            } catch (Error4 var6) {
               throw new DatabaseException("Creating each database with one deleted record failed." + error4Message(var6));
            }

            createCertDatabase(var4, databaseName(var0, var3, 'c'));
            createCRLDatabase(var4, databaseName(var0, var3, 'r'));
            createKeyDatabase(var4, databaseName(var0, var3, 'p'));
            return true;
         }
      }
   }

   private static void createCertDatabase(Data4jni var0, String var1) throws DatabaseException {
      try {
         Field4info var2 = new Field4info();
         Tag4info var3 = new Tag4info();
         var2.add("SUBJECT", 'C', 128, 0, false);
         var2.add("ISSUER", 'C', 128, 0, false);
         var2.add("SERIAL", 'C', 32, 0, false);
         var2.add("CERT", 'M', 1, 0, false);
         var3.add("SUBJECT", "SUBJECT", ".NOT.DELETED()", (byte)0, false);
         var3.add("ISN", "ISSUER+SERIAL", ".NOT.DELETED()", (byte)0, false);
         var0.create(var1, var2, var3);
         var0.blank();
         var0.append();
         CertFields var4 = new CertFields(var0);
         var4.a.contents = encodeLong(0L);
         var4.b.contents = new byte[0];
         var4.c.contents = new byte[0];
         var4.d.contents = new byte[0];
         var4.e.deleted = true;
         var0.update();
      } catch (IOException var9) {
         throw new DatabaseException("NativeDB.createCertDatabase.", var9);
      } catch (Error4 var10) {
         deleteDatabaseFiles(var1);
         throw new DatabaseException("NativeDB.createCertDatabase: Creating a database with one deleted record failed." + error4Message(var10));
      } finally {
         close(var0);
      }

   }

   private static void createCRLDatabase(Data4jni var0, String var1) throws DatabaseException {
      try {
         Field4info var2 = new Field4info();
         Tag4info var3 = new Tag4info();
         var2.add("ISSUER", 'C', 128, 0, false);
         var2.add("LAST", 'C', 4, 0, false);
         var2.add("CRL", 'M', 1, 0, false);
         var3.add("ILU", "ISSUER+LAST", ".NOT.DELETED()", (byte)0, true);
         var0.create(var1, var2, var3);
         var0.blank();
         var0.append();
         CRLFields var4 = new CRLFields(var0);
         var4.a.contents = encodeLong(0L);
         var4.b.contents = new byte[0];
         var4.c.contents = new byte[0];
         var4.d.deleted = true;
         var0.update();
      } catch (IOException var9) {
         throw new DatabaseException("NativeDB.createCRLDatabase.", var9);
      } catch (Error4 var10) {
         deleteDatabaseFiles(var1);
         throw new DatabaseException("NativeDB.createCRLDatabase: Creating a database with one deleted record failed." + error4Message(var10));
      } finally {
         close(var0);
      }

   }

   private static void createKeyDatabase(Data4jni var0, String var1) throws DatabaseException {
      try {
         Field4info var2 = new Field4info();
         Tag4info var3 = new Tag4info();
         var2.add("SPKI", 'C', 20, 0, false);
         var2.add("SALT", 'C', 8, 0, false);
         var2.add("IV", 'C', 8, 0, false);
         var2.add("KEY", 'M', 1, 0, false);
         var3.add("SPKI", "SPKI", ".NOT.DELETED()", (byte)20, true);
         var0.create(var1, var2, var3);
         var0.blank();
         var0.append();
         KeyFields var4 = new KeyFields(var0);
         var4.a.contents = encodeLong(0L);
         var4.b.contents = new byte[0];
         var4.c.contents = new byte[0];
         var4.d.contents = new byte[0];
         var4.e.deleted = true;
         var0.update();
      } catch (IOException var9) {
         throw new DatabaseException("NativeDB.createKeyDatabase.", var9);
      } catch (Error4 var10) {
         deleteDatabaseFiles(var1);
         throw new DatabaseException("NativeDB.createKeyDatabase: Creating a database with one deleted record failed." + error4Message(var10));
      } finally {
         close(var0);
      }

   }

   private static boolean removeDatabase(File var0, String var1) throws DatabaseException {
      if (!var0.exists()) {
         return false;
      } else {
         Code4jni var2 = getCode4();

         Data4jni var3;
         Data4jni var4;
         Data4jni var5;
         try {
            var3 = new Data4jni(var2);
            var4 = new Data4jni(var2);
            var5 = new Data4jni(var2);
         } catch (Error4usage var19) {
            throw new DatabaseException("NativeDB.removeDatabase: unable to create Data4jni(" + error4Message(var19) + ").");
         }

         String var6 = deleteDatabase(var2, var0, var1);
         if (var6 == null) {
            return false;
         } else {
            boolean var10;
            try {
               String var7 = databaseName(var0, var6, 'c');
               String var8 = databaseName(var0, var6, 'r');
               String var9 = databaseName(var0, var6, 'p');
               var3.open(var7);
               lock(var3);
               var4.open(var8);
               lock(var4);
               var5.open(var9);
               lock(var5);
               deleteDatabaseFiles(var7);
               deleteDatabaseFiles(var8);
               deleteDatabaseFiles(var9);
               accessHash.remove(new File(var0, var6));
               close(var3);
               close(var4);
               close(var5);
               var10 = true;
            } catch (IOException var16) {
               throw new DatabaseException("NativeDB.removeDatabase.", var16);
            } catch (Error4 var17) {
               throw new DatabaseException("NativeDB.removeDatabase: unable to delete database files" + error4Message(var17));
            } finally {
               closeQuietly(var3);
               closeQuietly(var4);
               closeQuietly(var5);
            }

            return var10;
         }
      }
   }

   private static String getError4DescriptionEntry() {
      return "unable to position to a record entry";
   }

   private static String getError4DescriptionField() {
      return "unable to associate Field4 object with a database field";
   }

   private static String getError4DescriptionFile() {
      return "unable to create or open file";
   }

   private static String getError4DescriptionLocked() {
      return "unable to lock an item that has been already locked";
   }

   private static String getError4DescriptionMessage() {
      return "unable to understand a server message";
   }

   private static String getError4DescriptionRelatedmatch() {
      return "unable to locate a slave record";
   }

   private static String getError4DescriptionTagName() {
      return "specified tag name is not open";
   }

   private static String getError4Description(Error4unexpected var0) {
      String var1 = var0.getMessage();
      String var2 = var1.substring(16, var1.length());
      int var3 = Integer.parseInt(var2, 10);
      if (var3 <= -400 && var3 >= -600) {
         return "expression evaluation error";
      } else if (var3 <= -1300 && var3 >= -1400) {
         return "communication error";
      } else if (var3 <= -2100 && var3 >= -2110) {
         return "server error";
      } else {
         switch (var3) {
            case -1430:
               return "The requested operation could not be performed because the requester has insufficient authority to perform the operation. For example, a user without creation privileges has made a call to Data4.create(java.lang.String, codebase.Field4info, codebase.Tag4info).";
            case -1420:
               return "The specified name was invalid or not found.";
            case -1400:
               return "The capabilities of CodeBase or the server have been maxed out. For example, the maximum allowable connections may have been exceeded by the server.";
            case -1120:
               return "Could not create memo file.";
            case -1110:
               return "A memo file or entry is corrupt.";
            case -1095:
               return "Version mismatch (e.g. client version mismatches server version).";
            case -1090:
               return "Operation generally not supported in this configuration.";
            case -970:
               return "CodeBase internal structures have been detected as invalid.";
            case -960:
               return "Unexpected result while attempting to verify the integrity of a structure.";
            case -950:
               return "A CodeBase function returned an unexpected result to another CodeBase function.";
            case -940:
               return "Exceeded maximum support due to demo version of CodeBase.";
            case -935:
               return "A CodeBase method was passed a null value.";
            case -930:
               return "A CodeBase method was passed an unexpected value.";
            case -920:
               return "CodeBase tried to allocate some memory from the heap but no memory was available.";
            case -910:
               return "CodeBase discovered an unexpected value in one of its internal variables.";
            case -720:
               return "CodeBase could not locate the master record's corresponding slave record.";
            case -710:
               return "A general CodeBase relation error was discovered.";
            case -350:
               return "An attempt to create an index failed because the Tag4info class contained invalid information.";
            case -310:
               return "An index corruption was detected.";
            case -300:
               return "A tag entry is missing. This error occurs when a key, corresponding to a database record, should be in a tag but is not.";
            case -250:
               return "This error can occur if Data4.seek(double) tries to do a seek on a non-numeric tag.";
            case -230:
               return "The total record length is too large. The maximum is 65534 bytes.";
            case -220:
               return "A data field had an unrecognized field type.";
            case -200:
               return "Database corruption detected. This error occurs when attempting to open a database that is not actually a true data file. If the file is a data file, its header and possibly its data is corrupt.";
            case -120:
               return "An error occurred while writing to a file. This error can occur when the disk is full.";
            case -110:
               return "An error occurred while unlocking part of a file.";
            case -90:
               return "An error occurred while renaming a file. This error can be caused when the file name already exists.";
            case -80:
               return "An error occurred while attempting to remove a file. This error will occur when the file  is opened by another user or the current process, and an attempt is made to remove that file.";
            case -70:
               return "An error occurred while reading a file.";
            case -40:
               return "An error occurred while setting the length of a file. This error occurs when an application does not have write access to the file or is out of disk space.";
            case -30:
               return "An error occurred while attempting to determine the length of a file. This error occurs when CodeBase runs out of valid file handles. If you receive this error, reduce the number of files opened by your application at any given time.";
            case -10:
               return "An error occurred while attempting to close a file.";
            default:
               return "Unknown error code";
         }
      }
   }

   private static String getError4DescriptionUnique() {
      return "unable to add a duplicate key to a unique tag";
   }

   private static String getError4DescriptionUsage() {
      return "a CodeBase method is called in an incorrect manner";
   }

   static String error4Message(Error4 var0) {
      try {
         throw var0;
      } catch (Error4usage var2) {
         return getError4DescriptionUsage();
      } catch (Error4unexpected var3) {
         return getError4Description(var3);
      } catch (Error4unique var4) {
         return getError4DescriptionUnique();
      } catch (Error4locked var5) {
         return getError4DescriptionLocked();
      } catch (Error4field var6) {
         return getError4DescriptionField();
      } catch (Error4message var7) {
         return getError4DescriptionMessage();
      } catch (Error4entry var8) {
         return getError4DescriptionEntry();
      } catch (Error4file var9) {
         return getError4DescriptionFile();
      } catch (Error4tagName var10) {
         return getError4DescriptionTagName();
      } catch (Error4relateMatch var11) {
         return getError4DescriptionRelatedmatch();
      } catch (Error4 var12) {
         return "unknown CodeBase error";
      }
   }

   private static Object getMasterTableLock(File var0) {
      synchronized(masterTableLockHash) {
         Object var2 = masterTableLockHash.get(var0);
         if (var2 == null) {
            var2 = new Object();
            masterTableLockHash.put(var0, var2);
         }

         return var2;
      }
   }

   private static void lock(Data4jni var0) throws DatabaseException {
      try {
         var0.lockAddAll();
         code4.lock();
      } catch (Error4 var2) {
         throw new DatabaseException("NativeDB.lock: " + error4Message(var2));
      }
   }

   private static void close(Data4jni var0) throws DatabaseException {
      try {
         var0.close();
         code4.unlock();
      } catch (Error4 var2) {
         throw new DatabaseException("NativeDB.close: unable to close data4(" + error4Message(var2) + ").");
      }
   }

   private static void closeQuietly(Data4jni var0) {
      if (var0 != null) {
         try {
            close(var0);
         } catch (DatabaseException var2) {
         }

      }
   }

   private static JSAFE_SymmetricCipher preparePBECipher(byte[] var0, byte[] var1, char[] var2, boolean var3, CertJ var4) throws DatabaseException {
      try {
         JSAFE_SymmetricCipher var5 = h.c("PBE/SHA1/3DES_EDE/CBC/PKCS5V2PBE-1000-3", var4.getDevice(), var4);
         var5.setIV(var1, 0, 8);
         var5.setSalt(var0, 0, 8);
         JSAFE_SecretKey var6 = var5.getBlankKey();
         var6.setPassword(var2, 0, var2.length);
         if (var3) {
            var5.encryptInit(var6, (SecureRandom)null);
         } else {
            var5.decryptInit(var6, (SecureRandom)null);
         }

         return var5;
      } catch (JSAFE_Exception var8) {
         throw new DatabaseException("NativeDB.preparePBECipher.", var8);
      }
   }

   private final class a {
      private String b;
      private String c;
      private String d;
      private Data4jni e;
      private Data4jni f;
      private Data4jni g;

      private a(File var2, String var3) throws DatabaseException {
         Code4jni var4 = NativeDB.getCode4();

         try {
            this.e = new Data4jni(var4);
            this.f = new Data4jni(var4);
            this.g = new Data4jni(var4);
         } catch (Error4usage var6) {
            throw new DatabaseException("NativeDB$Access.Access: unable to create Data4jni(" + NativeDB.getError4DescriptionUsage() + ").");
         }

         this.b = NativeDB.databaseName(var2, var3, 'c');
         this.c = NativeDB.databaseName(var2, var3, 'r');
         this.d = NativeDB.databaseName(var2, var3, 'p');
      }

      private void a(X509Certificate var1, byte[] var2, byte[] var3, byte[] var4, byte[] var5, byte[] var6) throws DatabaseException {
         synchronized(this.e) {
            try {
               this.e.open(this.b);
               NativeDB.lock(this.e);
               CertFields var8 = new CertFields(this.e);
               SearchResult var9 = this.a(var2, var1.getIssuerName(), var1.getSerialNumber(), var8);
               if (var9 != null && var9.a > 0) {
                  return;
               }

               this.e.go(this.a(this.e, var8.a));
               var8.a.contents = var3;
               var8.b.contents = var4;
               var8.c.contents = var5;
               var8.d.contents = var6;
               var8.e.deleted = false;
               this.e.update();
            } catch (IOException var16) {
               throw new DatabaseException("NativeDB$Acccess.insertCertificate: unable to open database file.", var16);
            } catch (Error4 var17) {
               throw new DatabaseException("NativeDB$Acccess.insertCertificate: " + NativeDB.error4Message(var17));
            } finally {
               NativeDB.close(this.e);
            }

         }
      }

      private void a(X509CRL var1, byte[] var2, byte[] var3, byte[] var4, byte[] var5) throws DatabaseException {
         synchronized(this.f) {
            try {
               this.f.open(this.c);
               NativeDB.lock(this.f);
               CRLFields var7 = new CRLFields(this.f);
               if (this.a(var2, var1.getIssuerName(), var1.getThisUpdate(), var7) <= 0) {
                  this.f.go(this.a(this.f, var7.a));
                  var7.a.contents = var3;
                  var7.b.contents = var4;
                  var7.c.contents = var5;
                  var7.d.deleted = false;
                  this.f.update();
                  return;
               }
            } catch (IOException var14) {
               throw new DatabaseException("NativeDB$Access.insertCRL: unable to open database file.", var14);
            } catch (Error4 var15) {
               throw new DatabaseException("NativeDB$Access.insertCRL: " + NativeDB.error4Message(var15));
            } finally {
               NativeDB.close(this.f);
            }

         }
      }

      private void a(byte[] var1, byte[] var2, byte[] var3, byte[] var4, char[] var5, CertJ var6) throws DatabaseException {
         synchronized(this.g) {
            try {
               this.g.open(this.d);
               NativeDB.lock(this.g);
               KeyFields var8 = new KeyFields(this.g);
               SearchResult var9 = this.a(var1, var8, var6, var5);
               if (var9 != null && var9.a > 0) {
                  return;
               }

               this.g.go(this.a(this.g, var8.a));
               var8.a.contents = var1;
               var8.b.contents = var2;
               var8.c.contents = var3;
               var8.d.contents = var4;
               var8.e.deleted = false;
               this.g.update();
            } catch (IOException var16) {
               throw new DatabaseException("NativeDB$Access.insertPrivateKey: unable to open database file.", var16);
            } catch (Error4 var17) {
               throw new DatabaseException("NativeDB$Access.insertPrivateKey: " + NativeDB.error4Message(var17));
            } finally {
               NativeDB.close(this.g);
            }

         }
      }

      private int a(X500Name var1, byte[] var2, byte[] var3, Vector var4) throws DatabaseException {
         synchronized(this.e) {
            byte var8;
            try {
               this.e.open(this.b);
               NativeDB.lock(this.e);
               CertFields var6 = new CertFields(this.e);
               SearchResult var7 = this.a(var3, var1, var2, var6);
               if (var7 != null) {
                  X509Certificate var20 = (X509Certificate)var7.b;
                  if (!var4.contains(var20)) {
                     var4.addElement(var20);
                  }

                  byte var9 = 1;
                  return var9;
               }

               var8 = 0;
            } catch (IOException var16) {
               throw new DatabaseException("NativeDB$Access.selectCertificateByIssuerAndSerialNumber: unable to open database file.", var16);
            } catch (Error4 var17) {
               throw new DatabaseException("NativeDB$Access.selectCertificateByIssuerAndSerialNumber: " + NativeDB.error4Message(var17));
            } finally {
               NativeDB.close(this.e);
            }

            return var8;
         }
      }

      private int a(X500Name var1, byte[] var2, Vector var3) throws DatabaseException {
         synchronized(this.e) {
            try {
               this.e.open(this.b);
               NativeDB.lock(this.e);
               CertFields var5 = new CertFields(this.e);
               this.e.select("SUBJECT");
               int var6 = this.e.seek(var2);

               int var7;
               for(var7 = 0; var6 == 0; var6 = this.e.skip(1)) {
                  if (var5.d.contents.length > 0) {
                     X509Certificate var8 = new X509Certificate(var5.d.contents, 0, 0);
                     if (var1.equals(var8.getSubjectName()) && !var5.e.deleted) {
                        if (!var3.contains(var8)) {
                           var3.addElement(var8);
                        }

                        ++var7;
                     }
                  }
               }

               int var21 = var7;
               return var21;
            } catch (IOException var16) {
               throw new DatabaseException("NativeDB$Access.selectCertificateBySubject: unable to open database file.", var16);
            } catch (Error4 var17) {
               throw new DatabaseException("NativeDB$Access.selectCertificateBySubject: " + NativeDB.error4Message(var17));
            } catch (CertificateException var18) {
               throw new DatabaseException("NativeDB$Access.selectCertificateBySubject.", var18);
            } finally {
               NativeDB.close(this.e);
            }
         }
      }

      private int a(X500Name var1, X509V3Extensions var2, Vector var3) throws DatabaseException {
         synchronized(this.e) {
            int var8;
            try {
               this.e.open(this.b);
               NativeDB.lock(this.e);
               CertFields var5 = new CertFields(this.e);
               int var6 = 0;
               int var7 = this.e.recCount();
               if (var7 <= 0) {
                  byte var23 = 0;
                  return var23;
               }

               for(var8 = 1; var8 <= var7; ++var8) {
                  this.e.go(var8);
                  if (!var5.e.deleted) {
                     X509Certificate var9 = new X509Certificate(var5.d.contents, 0, 0);
                     X500Name var10 = var9.getSubjectName();
                     if ((var1 == null || var10.contains(var1)) && CertJUtils.compareExtensions(var2, var9.getExtensions())) {
                        if (!var3.contains(var9)) {
                           var3.addElement(var9);
                        }

                        ++var6;
                     }
                  }
               }

               var8 = var6;
            } catch (IOException var18) {
               throw new DatabaseException("NativeDB$Access.selectCertificateByExtensions: unable to open database file.", var18);
            } catch (Error4 var19) {
               throw new DatabaseException("NativeDB$Access.selectCertificateByExtensions: " + NativeDB.error4Message(var19));
            } catch (CertificateException var20) {
               throw new DatabaseException("NativeDB$Access.selectCertificateByExtensions", var20);
            } finally {
               NativeDB.close(this.e);
            }

            return var8;
         }
      }

      private int a(X500Name var1, Date var2, byte[] var3, Vector var4) throws DatabaseException {
         synchronized(this.f) {
            byte var26;
            try {
               this.f.open(this.c);
               NativeDB.lock(this.f);
               CRLFields var6 = new CRLFields(this.f);
               this.f.select("ILU");
               int var7 = this.f.seek(var3);
               Date var8 = new Date(0L);

               X509CRL var9;
               for(var9 = null; var7 == 0; var7 = this.f.skip(1)) {
                  if (var6.c.contents.length > 0) {
                     X509CRL var10 = new X509CRL(var6.c.contents, 0, 0);
                     if (var1.equals(var10.getIssuerName())) {
                        Date var11 = var10.getThisUpdate();
                        if (!var11.after(var2) && var11.after(var8) && !var6.d.deleted) {
                           var8 = var11;
                           var9 = var10;
                        }
                     }
                  }
               }

               if (var9 != null) {
                  if (!var4.contains(var9)) {
                     try {
                        var4.addElement((CRL)var9.clone());
                     } catch (CloneNotSupportedException var20) {
                        throw new DatabaseException("NativeDBProvider.selectCRL: Unable to clone a CRL.", var20);
                     }
                  }

                  var26 = 1;
                  return var26;
               }

               var26 = 0;
            } catch (IOException var21) {
               throw new DatabaseException("NativeDB$Access.selectCRL: unable to open database file.", var21);
            } catch (Error4 var22) {
               throw new DatabaseException("NativeDB$Access.selectCRL: " + NativeDB.error4Message(var22));
            } catch (CertificateException var23) {
               throw new DatabaseException("NativeDB$Access.selectCRL.", var23);
            } finally {
               NativeDB.close(this.f);
            }

            return var26;
         }
      }

      private JSAFE_PrivateKey a(byte[] var1, char[] var2, CertJ var3) throws DatabaseException {
         synchronized(this.g) {
            JSAFE_PrivateKey var7;
            try {
               this.g.open(this.d);
               NativeDB.lock(this.g);
               KeyFields var5 = new KeyFields(this.g);
               SearchResult var6 = this.a(var1, var5, var3, var2);
               if (var6 != null) {
                  var7 = (JSAFE_PrivateKey)var6.b;
                  return var7;
               }

               var7 = null;
            } catch (IOException var14) {
               throw new DatabaseException("NativeDB$Access.selectKey: unable to open database file.", var14);
            } catch (Error4 var15) {
               throw new DatabaseException("NativeDB$Access.selectKey: " + NativeDB.error4Message(var15));
            } finally {
               NativeDB.close(this.g);
            }

            return var7;
         }
      }

      private void a(X500Name var1, byte[] var2, byte[] var3) throws DatabaseException {
         synchronized(this.e) {
            try {
               this.e.open(this.b);
               NativeDB.lock(this.e);
               CertFields var5 = new CertFields(this.e);
               SearchResult var6 = this.a(var3, var1, var2, var5);
               if (var6 == null) {
                  return;
               }

               this.a(this.e, var6.a, var5.a, var5.e);
            } catch (IOException var13) {
               throw new DatabaseException("NativeDB$Access.deleteCertificate: unable to open database file.", var13);
            } catch (Error4 var14) {
               throw new DatabaseException("NativeDB$Access.deleteCertificate: " + NativeDB.error4Message(var14));
            } finally {
               NativeDB.close(this.e);
            }

         }
      }

      private void a(X500Name var1, Date var2, byte[] var3) throws DatabaseException {
         synchronized(this.f) {
            try {
               this.f.open(this.c);
               NativeDB.lock(this.f);
               CRLFields var5 = new CRLFields(this.f);
               int var6 = this.a(var3, var1, var2, var5);
               if (var6 > 0) {
                  this.a(this.f, var6, var5.a, var5.d);
                  return;
               }
            } catch (IOException var13) {
               throw new DatabaseException("NativeDB$Access.deleteCRL: unable to open database file.", var13);
            } catch (Error4 var14) {
               throw new DatabaseException("NativeDB$Access.deleteCRL: " + NativeDB.error4Message(var14));
            } finally {
               NativeDB.close(this.f);
            }

         }
      }

      private void b(byte[] var1, char[] var2, CertJ var3) throws DatabaseException {
         synchronized(this.g) {
            try {
               this.g.open(this.d);
               NativeDB.lock(this.g);
               KeyFields var5 = new KeyFields(this.g);
               SearchResult var6 = this.a(var1, var5, var3, var2);
               if (var6 == null) {
                  return;
               }

               this.a(this.g, var6.a, var5.a, var5.e);
            } catch (IOException var13) {
               throw new DatabaseException("NativeDB$Access.deleteKey: unable to open database file.", var13);
            } catch (Error4 var14) {
               throw new DatabaseException("NativeDB$Access.deleteKey: " + NativeDB.error4Message(var14));
            } finally {
               NativeDB.close(this.g);
            }

         }
      }

      private Certificate a(b var1) throws DatabaseException {
         synchronized(this.e) {
            Object var4;
            try {
               this.e.open(this.b);
               NativeDB.lock(this.e);
               CertFields var3 = new CertFields(this.e);
               int var20 = this.e.recCount();
               X509Certificate var5;
               if (var1.b > var20) {
                  var5 = null;
                  return var5;
               }

               while(var1.b <= var20) {
                  this.e.go(var1.b);
                  if (!var3.e.deleted) {
                     var5 = new X509Certificate(var3.d.contents, 0, 0);
                     return var5;
                  }

                  var1.b++;
               }

               var1.b--;
               var5 = null;
               return var5;
            } catch (IOException var14) {
               throw new DatabaseException("NativeDB$Access.findNextCert: unable to open database file.", var14);
            } catch (CertificateException var15) {
               throw new DatabaseException("NativeDB$Access.findNextCert: unable to create a certificate object.", var15);
            } catch (Error4entry var16) {
               var4 = null;
            } catch (Error4 var17) {
               throw new DatabaseException("NativeDB$Access.findNextCert: " + NativeDB.error4Message(var17));
            } finally {
               NativeDB.close(this.e);
            }

            return (Certificate)var4;
         }
      }

      private CRL b(b var1) throws DatabaseException {
         synchronized(this.f) {
            X509CRL var5;
            try {
               this.f.open(this.c);
               NativeDB.lock(this.f);
               CRLFields var3 = new CRLFields(this.f);
               int var20 = this.f.recCount();
               if (var1.c <= var20) {
                  while(var1.c <= var20) {
                     this.f.go(var1.c);
                     if (!var3.d.deleted) {
                        var5 = new X509CRL(var3.c.contents, 0, 0);
                        return var5;
                     }

                     var1.c++;
                  }

                  var1.c--;
                  var5 = null;
                  return var5;
               }

               var5 = null;
            } catch (IOException var14) {
               throw new DatabaseException("NativeDB$Access.findNextCRL: unable to open database file.", var14);
            } catch (CertificateException var15) {
               throw new DatabaseException("NativeDB$Access.findNextCRL: unable to create a CRL object.", var15);
            } catch (Error4entry var16) {
               Object var4 = null;
               return (CRL)var4;
            } catch (Error4 var17) {
               throw new DatabaseException("NativeDB$Access.findNextCRL: " + NativeDB.error4Message(var17));
            } finally {
               NativeDB.close(this.f);
            }

            return var5;
         }
      }

      private JSAFE_PrivateKey a(b var1, CertJ var2, char[] var3) throws DatabaseException {
         synchronized(this.g) {
            JSAFE_PrivateKey var7;
            try {
               this.g.open(this.d);
               NativeDB.lock(this.g);
               KeyFields var5 = new KeyFields(this.g);
               int var20 = this.g.recCount();
               if (var1.d <= var20) {
                  while(var1.d <= var20) {
                     this.g.go(var1.d);
                     if (!var5.e.deleted) {
                        var7 = this.a(var5.d.contents, var5.b.contents, var5.c.contents, var3, var2);
                        return var7;
                     }

                     var1.d++;
                  }

                  var1.d--;
                  var7 = null;
                  return var7;
               }

               var7 = null;
            } catch (IOException var15) {
               throw new DatabaseException("NativeDB$Access.findNextKey: unable to open database file.", var15);
            } catch (Error4entry var16) {
               Object var6 = null;
               return (JSAFE_PrivateKey)var6;
            } catch (Error4 var17) {
               throw new DatabaseException("NativeDB$Access.findNextKey: " + NativeDB.error4Message(var17));
            } finally {
               NativeDB.close(this.g);
            }

            return var7;
         }
      }

      private void a(Data4jni var1, int var2, Field4byteArray var3, Field4deleteFlag var4) throws DatabaseException {
         try {
            var1.go(1);
            byte[] var5 = var3.contents;
            var3.contents = NativeDB.encodeLong((long)var2);
            var1.update();
            var1.go(var2);
            var3.contents = var5;
            var4.deleted = true;
            var1.update();
         } catch (Error4 var6) {
            throw new DatabaseException("NativeDB$Access.deleteRecord: " + NativeDB.error4Message(var6));
         }
      }

      private int a(Data4jni var1, Field4byteArray var2) throws DatabaseException {
         try {
            var1.go(1);
            int var3;
            if (NativeDB.decodeInt(var2.contents) == 0) {
               var3 = var1.bottom();
               if (var3 != 0 && var3 != 3) {
                  throw new DatabaseException("NativeDB$Access.findAvailableRecord: error in going to the bottom.");
               } else {
                  var1.blank();
                  var1.append();
                  return var1.recNo();
               }
            } else {
               var3 = NativeDB.decodeInt(var2.contents);
               var1.go(var3);
               byte[] var4 = var2.contents;
               var1.go(1);
               var2.contents = var4;
               var1.update();
               return var3;
            }
         } catch (IOException var5) {
            throw new DatabaseException("NativeDB$Access.findAvailableRecord.", var5);
         } catch (Error4 var6) {
            throw new DatabaseException("NativeDB$Access.findAvailableRecord: " + NativeDB.error4Message(var6));
         }
      }

      private SearchResult a(byte[] var1, X500Name var2, byte[] var3, CertFields var4) throws DatabaseException {
         try {
            this.e.select("ISN");

            for(int var5 = this.e.seek(var1); var5 == 0; var5 = this.e.skip(1)) {
               if (var4.d.contents.length > 0) {
                  X509Certificate var6 = new X509Certificate(var4.d.contents, 0, 0);
                  if (var6.getIssuerName().equals(var2) && CertJUtils.byteArraysEqual(var6.getSerialNumber(), var3) && !var4.e.deleted) {
                     return new SearchResult(this.e.recNo(), var6);
                  }
               }
            }

            return null;
         } catch (CertificateException var7) {
            throw new DatabaseException("NativeDB$Access.findCert: unable to create an X509Certificate object.", var7);
         } catch (IOException var8) {
            throw new DatabaseException("NativeDB$Access.findCert.", var8);
         } catch (Error4 var9) {
            throw new DatabaseException("NativeDB$Access.findCert: " + NativeDB.error4Message(var9));
         }
      }

      private int a(byte[] var1, X500Name var2, Date var3, CRLFields var4) throws DatabaseException {
         try {
            this.f.select("ILU");

            for(int var5 = this.f.seek(var1); var5 == 0; var5 = this.f.skip(1)) {
               if (var4.c.contents.length > 0) {
                  X509CRL var6 = new X509CRL(var4.c.contents, 0, 0);
                  if (var2.equals(var6.getIssuerName()) && var3.equals(var6.getThisUpdate()) && !var4.d.deleted) {
                     return this.f.recNo();
                  }
               }
            }

            return 0;
         } catch (CertificateException var7) {
            throw new DatabaseException("NativeDB$Access.findCRL: unable to create an X509CRL object.", var7);
         } catch (IOException var8) {
            throw new DatabaseException("NativeDB$Access.findCRL.", var8);
         } catch (Error4 var9) {
            throw new DatabaseException("NativeDB$Access.findCRL: " + NativeDB.error4Message(var9));
         }
      }

      private SearchResult a(byte[] var1, KeyFields var2, CertJ var3, char[] var4) throws DatabaseException {
         try {
            this.g.select("SPKI");

            for(int var5 = this.g.seek(var1); var5 == 0; var5 = this.g.skip(1)) {
               if (var2.d.contents.length > 0) {
                  JSAFE_PrivateKey var6 = this.a(var2.d.contents, var2.b.contents, var2.c.contents, var4, var3);
                  return new SearchResult(this.g.recNo(), var6);
               }
            }

            return null;
         } catch (IOException var7) {
            throw new DatabaseException("NativeDB$Access.findKey.", var7);
         } catch (Error4 var8) {
            throw new DatabaseException("NativeDB$Access.findKey: " + NativeDB.error4Message(var8));
         }
      }

      private JSAFE_PrivateKey a(byte[] var1, byte[] var2, byte[] var3, char[] var4, CertJ var5) throws DatabaseException {
         byte[] var6 = new byte[var1.length - 1];
         System.arraycopy(var1, 1, var6, 0, var1.length - 1);
         byte[] var7 = this.b(var6, var2, var3, var4, var5);
         String var8 = var5.getDevice();
         byte[][] var10 = new byte[][]{var7};

         try {
            JSAFE_PrivateKey var9;
            switch (var1[0]) {
               case 0:
                  var9 = h.e("RSA", var8, var5);
                  var9.setKeyData("RSAPrivateKeyBER", var10);
                  break;
               case 1:
                  var9 = h.e("DSA", var8, var5);
                  var9.setKeyData("DSAPrivateKeyBER", var10);
                  break;
               default:
                  throw new DatabaseException("NativeDB$Access.decryptPrivateKey: unknown key type " + var1[0]);
            }

            return var9;
         } catch (JSAFE_Exception var12) {
            throw new DatabaseException("NativeDB$Access.decryptPrivateKey.", var12);
         }
      }

      private byte[] b(byte[] var1, byte[] var2, byte[] var3, char[] var4, CertJ var5) throws DatabaseException {
         byte[] var6 = new byte[var1.length];
         JSAFE_SymmetricCipher var7 = null;

         byte[] var10;
         try {
            var7 = NativeDB.preparePBECipher(var2, var3, var4, false, var5);
            int var8 = var7.decryptUpdate(var1, 0, var1.length, var6, 0);
            var8 += var7.decryptFinal(var6, var8);
            byte[] var9 = new byte[var8];
            System.arraycopy(var6, 0, var9, 0, var8);
            var10 = var9;
         } catch (JSAFE_Exception var14) {
            throw new DatabaseException("NativeDB$Access.pbeDecrypt: PBE decryption failed.", var14);
         } finally {
            if (var7 != null) {
               var7.clearSensitiveData();
            }

         }

         return var10;
      }

      // $FF: synthetic method
      a(File var2, String var3, Object var4) throws DatabaseException {
         this(var2, var3);
      }
   }

   private final class b extends ProviderImplementation implements DatabaseInterface {
      private int b;
      private int c;
      private int d;
      private final Object e;
      private final Object f;
      private final Object g;
      private a h;

      private b(CertJ var2, String var3) throws InvalidParameterException, DatabaseException {
         super(var2, var3);
         this.e = new Object();
         this.f = new Object();
         this.g = new Object();
         String var4 = NativeDB.findExistingId(NativeDB.getCode4(), NativeDB.this.path, NativeDB.this.databaseName);
         if (var4 == null) {
            throw new DatabaseException("NativeDB$Implementation.Implementation: database named " + NativeDB.this.databaseName + " does not exist in " + NativeDB.this.path.toString() + ".");
         } else {
            synchronized(NativeDB.accessHash) {
               File var6 = new File(NativeDB.this.path, var4);
               this.h = (a)NativeDB.accessHash.get(var6);
               if (this.h == null) {
                  this.h = NativeDB.this.new a(NativeDB.this.path, var4);
                  NativeDB.accessHash.put(var6, this.h);
               }

            }
         }
      }

      public void insertCertificate(Certificate var1) throws NotSupportedException, DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("NativeDB$Implementation.insertCertificate: certificate should not be null.");
         } else if (!(var1 instanceof X509Certificate)) {
            throw new DatabaseException("NativeDB$Implementation.insertCertificate: certificate should be an instance of X509Certificate.");
         } else {
            X509Certificate var2 = (X509Certificate)var1;
            byte[] var3 = this.a((X500Name)var2.getSubjectName(), 128);
            byte[] var4 = this.a((X500Name)var2.getIssuerName(), 128);
            byte[] var5 = this.a(var2.getSerialNumber());
            byte[] var6 = this.a(var2.getIssuerName(), var2.getSerialNumber());
            byte[] var7 = new byte[var2.getDERLen(0)];

            try {
               var2.getDEREncoding(var7, 0, 0);
            } catch (CertificateException var9) {
               throw new NotSupportedException("NativeDB$Implementation.insertCertificate: unable to encode the given certificate.", var9);
            }

            this.h.a(var2, var6, var3, var4, var5, var7);
         }
      }

      public void insertCRL(CRL var1) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("NativeDB$Implementation.insertCRL: genericCRL should not be null.");
         } else if (!(var1 instanceof X509CRL)) {
            throw new DatabaseException("NativeDB$Implementation.insertCRL: genericCRL should be an instance of X509CRL.");
         } else {
            X509CRL var2 = (X509CRL)var1;
            byte[] var3 = this.a((X500Name)var2.getIssuerName(), 128);
            byte[] var4 = this.a((Date)var2.getThisUpdate(), 4);
            byte[] var5 = new byte[var2.getDERLen(0)];

            try {
               var2.getDEREncoding(var5, 0, 0);
            } catch (CertificateException var7) {
               throw new DatabaseException("NativeDB$Implementation.insertCRL.", var7);
            }

            byte[] var6 = this.a(var2.getIssuerName(), var2.getThisUpdate());
            this.h.a(var2, var6, var3, var4, var5);
         }
      }

      public void insertPrivateKeyByCertificate(Certificate var1, JSAFE_PrivateKey var2) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("NativeDB$Implementation.insertPrivateKeyByCertificate: cert should not be null.");
         } else {
            try {
               this.insertPrivateKeyByPublicKey(var1.getSubjectPublicKey(this.certJ.getDevice()), var2);
            } catch (CertificateException var4) {
               throw new DatabaseException("NativeDB$Implementation.insertPrivateKeyByCertificate.", var4);
            }
         }
      }

      public void insertPrivateKeyByPublicKey(JSAFE_PublicKey var1, JSAFE_PrivateKey var2) throws DatabaseException {
         if (var1 != null && var2 != null) {
            byte[] var3 = this.a(var1, this.certJ);
            byte[] var4 = new byte[8];
            byte[] var5 = new byte[8];

            JSAFE_SecureRandom var6;
            try {
               var6 = this.certJ.getRandomObject();
            } catch (CertJException var8) {
               throw new DatabaseException("NativeDB$Implementation.insertPrivateKey: random provider is not available in certJ.", var8);
            }

            var6.nextBytes(var4);
            var6.nextBytes(var5);
            byte[] var7 = this.a(var2, var4, var5, NativeDB.this.password, this.certJ);
            this.h.a(var3, var4, var5, var7, NativeDB.this.password, this.certJ);
         } else {
            throw new DatabaseException("NativeDB$Implementation.insertPrivateKeyByPublicKey: neither publicKey nor key should be null.");
         }
      }

      public int selectCertificateByIssuerAndSerialNumber(X500Name var1, byte[] var2, Vector var3) throws DatabaseException {
         if (var1 != null && var2 != null) {
            byte[] var4 = this.a(var1, var2);
            return this.h.a(var1, var2, var4, var3);
         } else {
            throw new DatabaseException("NativeDB$Implementation.selectCertificateByIssuerAndSerialNumber: neither issuerName nor serialNumber should be null.");
         }
      }

      public int selectCertificateBySubject(X500Name var1, Vector var2) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("NativeDB$Implementation.selectCertificateBySubject: subjectName should not be null.");
         } else {
            byte[] var3 = this.a((X500Name)var1, 128);
            return this.h.a(var1, var3, var2);
         }
      }

      public int selectCertificateByExtensions(X500Name var1, X509V3Extensions var2, Vector var3) throws DatabaseException {
         if (var1 == null && var2 == null) {
            throw new DatabaseException("NativeDB.selectCertificateByExtensions: either baseName or extensions should have a non-null value.");
         } else {
            return this.h.a(var1, var2, var3);
         }
      }

      public boolean isCertificateIteratorSetup() {
         synchronized(this.e) {
            return this.b != 0;
         }
      }

      public void setupCertificateIterator() {
         synchronized(this.e) {
            this.b = 1;
         }
      }

      public Certificate firstCertificate() {
         synchronized(this.e) {
            this.setupCertificateIterator();

            Certificate var10000;
            try {
               var10000 = this.nextCertificate();
            } catch (DatabaseException var4) {
               return null;
            }

            return var10000;
         }
      }

      public Certificate nextCertificate() throws DatabaseException {
         synchronized(this.e) {
            if (!this.isCertificateIteratorSetup()) {
               this.setupCertificateIterator();
            }

            Certificate var2 = this.h.a(this);
            if (var2 == null) {
               this.b = 0;
            } else {
               ++this.b;
            }

            return var2;
         }
      }

      public boolean hasMoreCertificates() throws DatabaseException {
         synchronized(this.e) {
            if (!this.isCertificateIteratorSetup()) {
               this.setupCertificateIterator();
            }

            Certificate var2 = this.h.a(this);
            return var2 != null;
         }
      }

      public int selectCRLByIssuerAndTime(X500Name var1, Date var2, Vector var3) throws DatabaseException {
         if (var1 != null && var2 != null) {
            byte[] var4 = this.a((X500Name)var1, 128);
            return this.h.a(var1, var2, var4, var3);
         } else {
            throw new DatabaseException("NativeDB$Implementation.selectCRLByIssuerAndTime: neither issuerName nor time should be null.");
         }
      }

      public boolean isCRLIteratorSetup() {
         synchronized(this.f) {
            return this.c != 0;
         }
      }

      public void setupCRLIterator() {
         synchronized(this.f) {
            this.c = 1;
         }
      }

      public CRL firstCRL() {
         synchronized(this.f) {
            this.setupCRLIterator();

            CRL var10000;
            try {
               var10000 = this.nextCRL();
            } catch (DatabaseException var4) {
               return null;
            }

            return var10000;
         }
      }

      public CRL nextCRL() throws DatabaseException {
         synchronized(this.f) {
            if (!this.isCRLIteratorSetup()) {
               this.setupCRLIterator();
            }

            CRL var2 = this.h.b(this);
            if (var2 == null) {
               this.c = 0;
            } else {
               ++this.c;
            }

            return var2;
         }
      }

      public boolean hasMoreCRLs() throws DatabaseException {
         synchronized(this.f) {
            if (!this.isCRLIteratorSetup()) {
               this.setupCRLIterator();
            }

            CRL var2 = this.h.b(this);
            return var2 != null;
         }
      }

      public JSAFE_PrivateKey selectPrivateKeyByCertificate(Certificate var1) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("NativeDB$Implementation.selectPrivateKeyByCertificate: cert should not be null.");
         } else {
            try {
               return this.selectPrivateKeyByPublicKey(var1.getSubjectPublicKey(this.certJ.getDevice()));
            } catch (CertificateException var3) {
               throw new DatabaseException("NativeDB$Implementation.selectPrivateKeyByCertificate.", var3);
            }
         }
      }

      public JSAFE_PrivateKey selectPrivateKeyByPublicKey(JSAFE_PublicKey var1) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("NativeDB$Implementation.selectPrivateKeyByPublicKey: publicKey should not be null.");
         } else {
            byte[] var2 = this.a(var1, this.certJ);
            return this.h.a(var2, NativeDB.this.password, this.certJ);
         }
      }

      public boolean isPrivateKeyIteratorSetup() {
         synchronized(this.g) {
            return this.d != 0;
         }
      }

      public void setupPrivateKeyIterator() {
         synchronized(this.g) {
            this.d = 1;
         }
      }

      public JSAFE_PrivateKey firstPrivateKey() {
         synchronized(this.g) {
            this.setupPrivateKeyIterator();

            JSAFE_PrivateKey var10000;
            try {
               var10000 = this.nextPrivateKey();
            } catch (DatabaseException var4) {
               return null;
            }

            return var10000;
         }
      }

      public JSAFE_PrivateKey nextPrivateKey() throws DatabaseException {
         synchronized(this.g) {
            if (!this.isPrivateKeyIteratorSetup()) {
               this.setupPrivateKeyIterator();
            }

            JSAFE_PrivateKey var2 = this.h.a(this, this.certJ, NativeDB.this.password);
            if (var2 == null) {
               this.d = 0;
            } else {
               ++this.d;
            }

            return var2;
         }
      }

      public boolean hasMorePrivateKeys() throws DatabaseException {
         synchronized(this.g) {
            if (!this.isPrivateKeyIteratorSetup()) {
               this.setupPrivateKeyIterator();
            }

            JSAFE_PrivateKey var2 = this.h.a(this, this.certJ, NativeDB.this.password);
            return var2 != null;
         }
      }

      public void deleteCertificate(X500Name var1, byte[] var2) throws DatabaseException {
         if (var1 != null && var2 != null) {
            byte[] var3 = this.a(var1, var2);
            this.h.a(var1, var2, var3);
         } else {
            throw new DatabaseException("NativeDB$Implementation.deleteCertificate: neither issuerName nor serialNumber is null.");
         }
      }

      public void deleteCRL(X500Name var1, Date var2) throws DatabaseException {
         if (var1 != null && var2 != null) {
            byte[] var3 = this.a(var1, var2);
            this.h.a(var1, var2, var3);
         } else {
            throw new DatabaseException("NativeDB$Implementation.deleteCRL: neither issuerName nor lastUpdate should be null.");
         }
      }

      public void deletePrivateKeyByCertificate(Certificate var1) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("NativeDB$Implementation.deletePrivateKeyByCertificate: cert should not be null.");
         } else {
            try {
               this.deletePrivateKeyByPublicKey(var1.getSubjectPublicKey(this.certJ.getDevice()));
            } catch (CertificateException var3) {
               throw new DatabaseException("NativeDB$Implementation.deletePrivateKeyByCertificate.", var3);
            }
         }
      }

      public void deletePrivateKeyByPublicKey(JSAFE_PublicKey var1) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("NativeDB$Implementation.deletePrivateKeyByCertificate: publicKey should not be null.");
         } else {
            byte[] var2 = this.a(var1, this.certJ);
            this.h.b(var2, NativeDB.this.password, this.certJ);
         }
      }

      public String toString() {
         return "NativeDB database provider named: " + super.getName();
      }

      private byte[] a(X500Name var1, int var2) {
         int var3 = var2;
         if (var2 == 0) {
            var3 = 128;
         }

         String var4 = var1.toString(false);
         var4 = var4.toUpperCase();
         byte[] var5 = new byte[var3];

         for(int var6 = 0; var6 < var5.length; ++var6) {
            var5[var6] = 0;
         }

         byte[] var7 = var4.getBytes();
         if (var4.length() < var3) {
            System.arraycopy(var7, 0, var5, 0, var7.length);
         } else {
            System.arraycopy(var7, 0, var5, 0, var3);
         }

         return var5;
      }

      private byte[] a(byte[] var1) {
         byte[] var2 = new byte[32];

         for(int var3 = 0; var3 < 32; ++var3) {
            var2[var3] = 0;
         }

         if (var1.length < 32) {
            System.arraycopy(var1, 0, var2, 0, var1.length);
         } else {
            System.arraycopy(var1, 0, var2, 0, 32);
         }

         return var2;
      }

      private byte[] a(X500Name var1, byte[] var2) {
         byte[] var3 = new byte[160];

         for(int var4 = 0; var4 < var3.length; ++var4) {
            var3[var4] = 0;
         }

         byte[] var5 = this.a((X500Name)var1, 0);
         if (var5.length < 128) {
            System.arraycopy(var5, 0, var3, 0, var5.length);
         } else {
            System.arraycopy(var5, 0, var3, 0, 128);
         }

         if (var2.length < 32) {
            System.arraycopy(var2, 0, var3, 128, var2.length);
         } else {
            System.arraycopy(var2, 0, var3, 128, 32);
         }

         return var3;
      }

      private byte[] a(X500Name var1, Date var2) {
         byte[] var3 = new byte[132];

         for(int var4 = 0; var4 < var3.length; ++var4) {
            var3[var4] = 0;
         }

         byte[] var6 = this.a((X500Name)var1, 128);
         if (var6.length < 128) {
            System.arraycopy(var6, 0, var3, 0, var6.length);
         } else {
            System.arraycopy(var6, 0, var3, 0, 128);
         }

         byte[] var5 = this.a((Date)var2, 4);
         System.arraycopy(var5, 0, var3, 128, 4);
         return var3;
      }

      private byte[] a(Date var1, int var2) {
         long var3 = var1.getTime() / 1000L;
         byte[] var5 = new byte[var2];

         for(int var6 = var2 - 1; var6 >= 0; --var6) {
            var5[var6] = (byte)((int)var3);
            var3 >>= 8;
         }

         return var5;
      }

      private byte[] a(JSAFE_PublicKey var1, CertJ var2) throws DatabaseException {
         JSAFE_MessageDigest var3 = null;

         byte[] var10;
         try {
            byte[][] var4 = var1.getKeyData(var1.getAlgorithm() + "PublicKeyBER");
            byte[] var5 = var4[0];
            int var6 = var5.length;
            var3 = h.a("SHA1", var2.getDevice(), this.context.b);
            byte[] var7 = new byte[var3.getDigestSize()];
            var3.digestInit();
            int var8 = 0;

            for(byte var9 = 64; var8 < var6; var8 += var9) {
               var3.digestUpdate(var5, var8, var6 - var8 > var9 ? var9 : var6 - var8);
            }

            var3.digestFinal(var7, 0);
            var10 = var7;
         } catch (JSAFE_Exception var14) {
            throw new DatabaseException("NativeDB$Implementation.encodeSpki.", var14);
         } finally {
            if (var3 != null) {
               var3.clearSensitiveData();
            }

         }

         return var10;
      }

      private byte[] a(JSAFE_PrivateKey var1, byte[] var2, byte[] var3, char[] var4, CertJ var5) throws DatabaseException {
         byte[][] var6;
         byte var7;
         try {
            String var8 = var1.getAlgorithm();
            if (var8.equals("RSA")) {
               var7 = 0;
            } else {
               if (!var8.equals("DSA")) {
                  throw new DatabaseException("NativeDB$Implementation.encryptPrivateKey: unknown private key type(" + var8 + ").");
               }

               var7 = 1;
            }

            var6 = var1.getKeyData(var8 + "PrivateKeyBER");
         } catch (JSAFE_Exception var10) {
            throw new DatabaseException("NativeDB$Implementation.encryptPrivateKey: private key operation failed.", var10);
         }

         byte[] var11 = this.a(var6[0], var2, var3, var4, var5);
         byte[] var9 = new byte[var11.length + 1];
         System.arraycopy(var11, 0, var9, 1, var11.length);
         var9[0] = var7;
         return var9;
      }

      private byte[] a(byte[] var1, byte[] var2, byte[] var3, char[] var4, CertJ var5) throws DatabaseException {
         byte[] var6 = new byte[var1.length + 8];
         JSAFE_SymmetricCipher var7 = null;

         byte[] var10;
         try {
            var7 = NativeDB.preparePBECipher(var2, var3, var4, true, var5);
            int var8 = var7.encryptUpdate(var1, 0, var1.length, var6, 0);
            var8 += var7.encryptFinal(var6, var8);
            byte[] var9 = new byte[var8];
            System.arraycopy(var6, 0, var9, 0, var8);
            var10 = var9;
         } catch (JSAFE_Exception var14) {
            throw new DatabaseException("NativeDB$Implementation.pbeEncrypt: PBE encryption failed.", var14);
         } finally {
            if (var7 != null) {
               var7.clearSensitiveData();
            }

         }

         return var10;
      }

      // $FF: synthetic method
      b(CertJ var2, String var3, Object var4) throws InvalidParameterException, DatabaseException {
         this(var2, var3);
      }
   }
}
