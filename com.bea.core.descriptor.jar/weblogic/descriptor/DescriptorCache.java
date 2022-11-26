package weblogic.descriptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import javax.xml.stream.XMLStreamException;
import weblogic.diagnostics.debug.DebugLogger;

public final class DescriptorCache {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugDescriptor");
   private static final String CRC_FILE = "crc.ser";
   private static final String DES_FILE = "des.ser";
   private static final String VER_FILE = "ver.ser";
   private static final DescriptorCache SINGLETON = new DescriptorCache();
   private static final Class fileOwnerFixerClass = getFileOwnerClass();
   private static final Method addPathMethod = getAddPathMethod();

   private DescriptorCache() {
   }

   public static DescriptorCache getInstance() {
      return SINGLETON;
   }

   private long computeCRC(IOHelper h) throws IOException {
      CRC32 crc = new CRC32();
      CheckedInputStream cis = null;

      try {
         cis = new CheckedInputStream(h.openInputStream(), crc);
         if (cis.markSupported()) {
            cis.mark(Integer.MAX_VALUE);
         }

         byte[] buf = new byte[1024];

         for(int i = 0; i != -1; i = cis.read(buf)) {
         }

         long var18 = crc.getValue();
         return var18;
      } finally {
         if (cis != null) {
            try {
               if (cis.markSupported()) {
                  cis.reset();
               }
            } catch (IOException var16) {
            }

            try {
               cis.close();
            } catch (IOException var15) {
            }
         }

      }
   }

   private long readCRC(File crcFile) throws IOException {
      ObjectInputStream ois = null;

      long var3;
      try {
         ois = new ObjectInputStream(new FileInputStream(crcFile));
         if (ois.markSupported()) {
            ois.mark(Integer.MAX_VALUE);
         }

         var3 = ois.readLong();
      } finally {
         if (ois != null) {
            try {
               if (ois.markSupported()) {
                  ois.reset();
               }
            } catch (IOException var14) {
            }

            try {
               ois.close();
            } catch (IOException var13) {
            }
         }

      }

      return var3;
   }

   private String readVersion(File verFile) throws IOException, ClassNotFoundException {
      ObjectInputStream ois = null;

      String var3;
      try {
         ois = new ObjectInputStream(new FileInputStream(verFile));
         if (ois.markSupported()) {
            ois.mark(Integer.MAX_VALUE);
         }

         var3 = (String)ois.readObject();
      } finally {
         if (ois != null) {
            try {
               if (ois.markSupported()) {
                  ois.reset();
               }
            } catch (IOException var13) {
            }

            try {
               ois.close();
            } catch (IOException var12) {
            }
         }

      }

      return var3;
   }

   private Object findMatchInCache(File dir, long CRC, IOHelper helper) throws IOException {
      File crcFile = new File(dir, "crc.ser");
      if (!crcFile.exists()) {
         if (debug.isDebugEnabled()) {
            debug.debug("** CrcFile wasn't there: " + crcFile);
         }

         return null;
      } else {
         if (debug.isDebugEnabled()) {
            debug.debug("** found CRC file");
         }

         long cachedCRC = this.readCRC(crcFile);
         File desFile = new File(dir, "des.ser");
         if (cachedCRC == CRC && desFile.exists()) {
            try {
               return helper.readCachedBean(desFile);
            } catch (IOException var10) {
               if (debug.isDebugEnabled()) {
                  debug.debug("** Exception from helper", var10);
                  var10.printStackTrace();
               }

               crcFile.delete();
               desFile.delete();
               return null;
            }
         } else {
            if (debug.isDebugEnabled()) {
               debug.debug("** descriptor has changed");
            }

            crcFile.delete();
            if (desFile.exists()) {
               desFile.delete();
            }

            return null;
         }
      }
   }

   private boolean findMatchingCRCInCache(File dir, long CRC, IOHelper helper) throws IOException {
      File crcFile = new File(dir, "crc.ser");
      if (!crcFile.exists()) {
         if (debug.isDebugEnabled()) {
            debug.debug("** CrcFile wasn't there: " + crcFile);
         }

         return false;
      } else {
         if (debug.isDebugEnabled()) {
            debug.debug("** found CRC file");
         }

         long cachedCRC = this.readCRC(crcFile);
         return cachedCRC == CRC;
      }
   }

   private void writeToCache(Object o, File dir, long CRC) throws IOException {
      if (debug.isDebugEnabled()) {
         debug.debug("** writeToCache " + o.getClass().getName());
      }

      dir.mkdirs();
      ObjectOutputStream oos = null;

      try {
         oos = new ObjectOutputStream(new FileOutputStream(new File(dir, "crc.ser")));
         oos.writeLong(CRC);
      } finally {
         if (oos != null) {
            try {
               oos.close();
            } catch (IOException var23) {
            }
         }

         oos = null;
      }

      try {
         oos = new ObjectOutputStream(new FileOutputStream(new File(dir, "des.ser")));
         oos.writeObject(o);
      } finally {
         if (oos != null) {
            try {
               oos.close();
            } catch (IOException var22) {
            }
         }

      }

   }

   private static Class getFileOwnerClass() {
      try {
         return Class.forName("weblogic.t3.srvr.FileOwnerFixer");
      } catch (ClassNotFoundException var1) {
         return null;
      }
   }

   private static Method getAddPathMethod() {
      if (fileOwnerFixerClass != null) {
         try {
            return fileOwnerFixerClass.getMethod("addPathJDK6", File.class);
         } catch (NoSuchMethodException var1) {
            throw new RuntimeException(var1);
         } catch (SecurityException var2) {
            throw new RuntimeException(var2);
         }
      } else {
         return null;
      }
   }

   private static void addPath(File f) {
      if (fileOwnerFixerClass != null) {
         try {
            addPathMethod.invoke((Object)null, f);
         } catch (IllegalAccessException var2) {
            throw new RuntimeException(var2);
         } catch (IllegalArgumentException var3) {
            throw new RuntimeException(var3);
         } catch (InvocationTargetException var4) {
            throw new RuntimeException(var4);
         }
      }

   }

   private void writeCRCToCache(File dir, long CRC) throws IOException {
      if (debug.isDebugEnabled()) {
         debug.debug("** writeCRCToCache " + CRC);
      }

      addPath(dir);
      dir.mkdirs();
      ObjectOutputStream oos = null;

      try {
         oos = new ObjectOutputStream(new FileOutputStream(new File(dir, "crc.ser")));
         oos.writeLong(CRC);
      } finally {
         if (oos != null) {
            try {
               oos.close();
            } catch (IOException var11) {
            }
         }

         oos = null;
      }

   }

   public boolean hasChanged(File cacheDir, IOHelper helper) {
      try {
         File crcFile = new File(cacheDir, "crc.ser");

         long CRC;
         try {
            CRC = this.computeCRC(helper);
         } catch (Exception var10) {
            return true;
         }

         if (debug.isDebugEnabled()) {
            debug.debug("** crc is " + CRC);
         }

         if (!crcFile.exists()) {
            if (debug.isDebugEnabled()) {
               debug.debug("** CrcFile wasn't there: " + crcFile);
            }

            this.writeCRCToCache(cacheDir, CRC);
            return true;
         } else {
            if (debug.isDebugEnabled()) {
               debug.debug("** found CRC file");
            }

            long cachedCRC;
            try {
               cachedCRC = this.readCRC(crcFile);
            } catch (Exception var9) {
               return true;
            }

            if (cachedCRC != CRC) {
               this.writeCRCToCache(cacheDir, CRC);
            }

            return cachedCRC != CRC;
         }
      } catch (Throwable var11) {
         return true;
      }
   }

   public void removeCRC(File cacheDir) {
      try {
         File crcFile = new File(cacheDir, "crc.ser");
         if (crcFile.exists()) {
            crcFile.delete();
         }
      } catch (Throwable var3) {
      }

   }

   public Object parseXML(File cacheDir, IOHelper helper) throws IOException, XMLStreamException {
      long CRC = this.computeCRC(helper);
      if (debug.isDebugEnabled()) {
         debug.debug("** crc is " + CRC);
      }

      Object o = null;
      if (helper.useCaching()) {
         o = this.findMatchInCache(cacheDir, CRC, helper);
         if (o != null) {
            if (debug.isDebugEnabled()) {
               debug.debug("** CACHE Hit");
            }

            return o;
         }

         if (debug.isDebugEnabled()) {
            debug.debug("** cache miss");
         }
      }

      InputStream is = null;

      Object var7;
      try {
         is = helper.openInputStream();
         o = helper.parseXML(is);
         if (debug.isDebugEnabled()) {
            debug.debug("** HELPER.parseXML returned " + o.getClass().getName());
         }

         if (helper.useCaching()) {
            this.writeToCache(o, cacheDir, CRC);
            if (debug.isDebugEnabled()) {
               debug.debug("** Wrote descriptor to " + cacheDir);
            }
         }

         var7 = o;
      } finally {
         if (is != null) {
            try {
               is.close();
            } catch (IOException var14) {
            }
         }

      }

      return var7;
   }

   public boolean hasVersionChanged(File cacheDir, String strVersion) {
      try {
         File verFile = new File(cacheDir, "ver.ser");
         if (debug.isDebugEnabled()) {
            debug.debug("** ver is " + strVersion);
         }

         if (!verFile.exists()) {
            if (debug.isDebugEnabled()) {
               debug.debug("** VerFile wasn't there: " + verFile);
            }

            return true;
         } else {
            if (debug.isDebugEnabled()) {
               debug.debug("** found VER file");
            }

            String cachedVersion;
            try {
               cachedVersion = this.readVersion(verFile);
            } catch (Exception var6) {
               return true;
            }

            return !strVersion.equals(cachedVersion);
         }
      } catch (Throwable var7) {
         return true;
      }
   }

   public void writeVersion(File dir, String strVersion) {
      if (debug.isDebugEnabled()) {
         debug.debug("** writeVersion " + strVersion);
      }

      dir.mkdirs();
      ObjectOutputStream oos = null;

      try {
         oos = new ObjectOutputStream(new FileOutputStream(new File(dir, "ver.ser")));
         oos.writeObject(strVersion);
      } catch (Exception var13) {
      } finally {
         if (oos != null) {
            try {
               oos.close();
            } catch (IOException var12) {
            }
         }

         oos = null;
      }

   }

   public void removeVersion(File cacheDir) {
      try {
         File verFile = new File(cacheDir, "ver.ser");
         if (verFile.exists()) {
            verFile.delete();
         }
      } catch (Throwable var3) {
      }

   }

   public interface IOHelper {
      InputStream openInputStream() throws IOException;

      Object readCachedBean(File var1) throws IOException;

      Object parseXML(InputStream var1) throws IOException, XMLStreamException;

      boolean useCaching();
   }
}
