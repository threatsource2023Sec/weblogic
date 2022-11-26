package weblogic.nodemanager.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.utils.encoders.BASE64Encoder;

public class UserInfo {
   private byte[] salt;
   private String hash;
   private boolean saveNeeded;
   public static final String USERNAME_PROP = "username";
   public static final String PASSWORD_PROP = "password";
   public static final String HASHED_PROP = "hashed";
   private static final String HASH_ALGORITHM = "SHA-256";
   private static final String OLD_HASH_ALGORITHM = "SHA";
   private static final String HASH_ALGORITHM_TAG = "{Algorithm=SHA-256}";
   private static final String HEADER = "Node manager user information";
   private long timestamp = -1L;
   private File userFile;
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();

   public UserInfo(byte[] salt) {
      if (salt != null && salt.length != 0) {
         this.salt = new byte[salt.length];
         System.arraycopy(salt, 0, this.salt, 0, salt.length);
      }

   }

   public UserInfo(DomainDir domainDir) throws IOException {
      File file = domainDir.getSaltFile();
      if (!file.exists()) {
         file = domainDir.getOldSaltFile();
         if (!file.exists()) {
            throw new FileNotFoundException(nmText.getDomainSaltFileNotFound());
         }
      }

      this.salt = loadSalt(file);
   }

   public synchronized boolean verify(String user, String pass) {
      if (this.hash == null) {
         throw new IllegalStateException("Must set username and password first");
      } else {
         if (this.timestamp <= this.userFile.lastModified() && this.userFile.canRead()) {
            try {
               this.load(this.userFile);
            } catch (IOException var4) {
            }
         }

         String theHash = null;
         if (this.hash.startsWith("{Algorithm=SHA-256}")) {
            theHash = "{Algorithm=SHA-256}" + hash("SHA-256", this.salt, user, pass);
         } else {
            theHash = hash("SHA", this.salt, user, pass);
         }

         return this.hash.equals(theHash);
      }
   }

   public synchronized void set(String user, String pass) {
      this.hash = "{Algorithm=SHA-256}" + hash("SHA-256", this.salt, user, pass);
   }

   String getHash() {
      return this.hash;
   }

   public synchronized void load(File file) throws IOException {
      if (this.timestamp <= 0L || this.timestamp < file.lastModified()) {
         this.userFile = file;
         Properties props = new Properties();
         RandomAccessFile raf = new RandomAccessFile(file, "r");
         FileChannel fc = raf.getChannel();
         FileLock lock = null;

         try {
            lock = fc.lock(0L, file.length(), true);
            RandomAccessFileInputStream rafis = new RandomAccessFileInputStream(raf);

            try {
               props.load(rafis);
            } finally {
               rafis.close();
               if (lock != null) {
                  lock.release();
               }

            }
         } finally {
            fc.close();
            raf.close();
         }

         String user = props.getProperty("username");
         String pass = props.getProperty("password");
         if (user != null && pass != null) {
            this.set(user, pass);
            this.saveNeeded = true;
         } else {
            this.hash = props.getProperty("hashed");
            if (this.hash == null) {
               throw new IllegalStateException(nmText.credentialsFileEmpty());
            }

            this.saveNeeded = false;
         }

         this.timestamp = file.lastModified();
      }
   }

   public synchronized boolean saveNeeded() {
      return this.saveNeeded;
   }

   public synchronized void save(File file) throws IOException {
      Properties props = new Properties();
      props.setProperty("hashed", this.hash);
      RandomAccessFile raf = new RandomAccessFile(file, "rws");
      FileChannel fc = raf.getChannel();

      try {
         FileLock lock = fc.lock(0L, file.length(), false);
         RandomAccessFileOutputStream rafos = new RandomAccessFileOutputStream(raf);

         try {
            props.store(new RandomAccessFileOutputStream(raf), "Node manager user information");
         } finally {
            rafos.close();
            if (lock != null) {
               lock.release();
            }

         }
      } finally {
         fc.close();
         raf.close();
      }

      this.timestamp = file.lastModified();
      this.userFile = file;
   }

   private static String hash(String algorithm, byte[] salt, String user, String pass) {
      MessageDigest md;
      try {
         md = MessageDigest.getInstance(algorithm);
      } catch (NoSuchAlgorithmException var6) {
         throw (InternalError)(new InternalError(algorithm + " digest algorithm not found")).initCause(var6);
      }

      md.update(salt);
      md.update(user.getBytes());
      md.update(pass.getBytes());
      return (new BASE64Encoder()).encodeBuffer(md.digest());
   }

   private static byte[] loadSalt(File file) throws IOException {
      try {
         return SerializedSystemIni.getSalt(file.getPath());
      } catch (RuntimeException var2) {
         throw (IOException)(new IOException(var2.getMessage())).initCause(var2);
      }
   }

   private static class RandomAccessFileOutputStream extends OutputStream {
      RandomAccessFile raf;

      RandomAccessFileOutputStream(RandomAccessFile raf) {
         this.raf = raf;
      }

      public void write(int val) throws IOException {
         this.raf.write(val);
      }
   }

   private static class RandomAccessFileInputStream extends InputStream {
      RandomAccessFile raf;

      RandomAccessFileInputStream(RandomAccessFile raf) {
         this.raf = raf;
      }

      public int read() throws IOException {
         return this.raf.read();
      }
   }
}
