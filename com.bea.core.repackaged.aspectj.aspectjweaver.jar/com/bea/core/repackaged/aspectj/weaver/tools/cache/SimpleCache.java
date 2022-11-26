package com.bea.core.repackaged.aspectj.weaver.tools.cache;

import com.bea.core.repackaged.aspectj.weaver.Dump;
import com.bea.core.repackaged.aspectj.weaver.tools.Trace;
import com.bea.core.repackaged.aspectj.weaver.tools.TraceFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

public class SimpleCache {
   private static final String SAME_BYTES_STRING = "IDEM";
   private static final byte[] SAME_BYTES = "IDEM".getBytes();
   private Map cacheMap;
   private boolean enabled = false;
   private Map generatedCache;
   private static final String GENERATED_CACHE_SUBFOLDER = "panenka.cache";
   private static final String GENERATED_CACHE_SEPARATOR = ";";
   public static final String IMPL_NAME = "shared";
   private Method defineClassMethod = null;
   private Method defineClassWithProtectionDomainMethod = null;

   protected SimpleCache(String folder, boolean enabled) {
      this.enabled = enabled;
      this.cacheMap = Collections.synchronizedMap(SimpleCache.StoreableCachingMap.init(folder));
      if (enabled) {
         String generatedCachePath = folder + File.separator + "panenka.cache";
         File f = new File(generatedCachePath);
         if (!f.exists()) {
            f.mkdir();
         }

         this.generatedCache = Collections.synchronizedMap(SimpleCache.StoreableCachingMap.init(generatedCachePath, 0));
      }

   }

   public byte[] getAndInitialize(String classname, byte[] bytes, ClassLoader loader, ProtectionDomain protectionDomain) {
      if (!this.enabled) {
         return null;
      } else {
         byte[] res = this.get(classname, bytes);
         if (Arrays.equals(SAME_BYTES, res)) {
            return bytes;
         } else {
            if (res != null) {
               this.initializeClass(classname, res, loader, protectionDomain);
            }

            return res;
         }
      }
   }

   private byte[] get(String classname, byte[] bytes) {
      String key = this.generateKey(classname, bytes);
      byte[] res = (byte[])this.cacheMap.get(key);
      return res;
   }

   public void put(String classname, byte[] origbytes, byte[] wovenbytes) {
      if (this.enabled) {
         String key = this.generateKey(classname, origbytes);
         if (Arrays.equals(origbytes, wovenbytes)) {
            this.cacheMap.put(key, SAME_BYTES);
         } else {
            this.cacheMap.put(key, wovenbytes);
         }
      }
   }

   private String generateKey(String classname, byte[] bytes) {
      CRC32 checksum = new CRC32();
      checksum.update(bytes);
      long crc = checksum.getValue();
      classname = classname.replace("/", ".");
      return new String(classname + "-" + crc);
   }

   private void initializeClass(String className, byte[] bytes, ClassLoader loader, ProtectionDomain protectionDomain) {
      String[] generatedClassesNames = this.getGeneratedClassesNames(className, bytes);
      if (generatedClassesNames != null) {
         String[] arr$ = generatedClassesNames;
         int len$ = generatedClassesNames.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String generatedClassName = arr$[i$];
            byte[] generatedBytes = this.get(generatedClassName, bytes);
            if (protectionDomain == null) {
               this.defineClass(loader, generatedClassName, generatedBytes);
            } else {
               this.defineClass(loader, generatedClassName, generatedBytes, protectionDomain);
            }
         }

      }
   }

   private String[] getGeneratedClassesNames(String className, byte[] bytes) {
      String key = this.generateKey(className, bytes);
      byte[] readBytes = (byte[])this.generatedCache.get(key);
      if (readBytes == null) {
         return null;
      } else {
         String readString = new String(readBytes);
         return readString.split(";");
      }
   }

   public void addGeneratedClassesNames(String parentClassName, byte[] parentBytes, String generatedClassName) {
      if (this.enabled) {
         String key = this.generateKey(parentClassName, parentBytes);
         byte[] storedBytes = (byte[])this.generatedCache.get(key);
         if (storedBytes == null) {
            this.generatedCache.put(key, generatedClassName.getBytes());
         } else {
            String storedClasses = new String(storedBytes);
            storedClasses = storedClasses + ";" + generatedClassName;
            this.generatedCache.put(key, storedClasses.getBytes());
         }

      }
   }

   private void defineClass(ClassLoader loader, String name, byte[] bytes) {
      Object clazz = null;

      try {
         if (this.defineClassMethod == null) {
            this.defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, bytes.getClass(), Integer.TYPE, Integer.TYPE);
         }

         this.defineClassMethod.setAccessible(true);
         this.defineClassMethod.invoke(loader, name, bytes, new Integer(0), new Integer(bytes.length));
      } catch (InvocationTargetException var6) {
         if (var6.getTargetException() instanceof LinkageError) {
            var6.printStackTrace();
         } else {
            System.out.println("define generated class failed" + var6.getTargetException());
         }
      } catch (Exception var7) {
         var7.printStackTrace();
         Dump.dumpWithException(var7);
      }

   }

   private void defineClass(ClassLoader loader, String name, byte[] bytes, ProtectionDomain protectionDomain) {
      Object clazz = null;

      try {
         if (this.defineClassWithProtectionDomainMethod == null) {
            this.defineClassWithProtectionDomainMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, bytes.getClass(), Integer.TYPE, Integer.TYPE, ProtectionDomain.class);
         }

         this.defineClassWithProtectionDomainMethod.setAccessible(true);
         this.defineClassWithProtectionDomainMethod.invoke(loader, name, bytes, 0, new Integer(bytes.length), protectionDomain);
      } catch (InvocationTargetException var7) {
         if (var7.getTargetException() instanceof LinkageError) {
            var7.printStackTrace();
         } else {
            var7.printStackTrace();
         }
      } catch (NullPointerException var8) {
         System.out.println("NullPointerException loading class:" + name + ".  Probabily caused by a corruput cache. Please clean it and reboot the server");
      } catch (Exception var9) {
         var9.printStackTrace();
         Dump.dumpWithException(var9);
      }

   }

   private static class StoreableCachingMap extends HashMap {
      private String folder;
      private static final String CACHENAMEIDX = "cache.idx";
      private long lastStored = System.currentTimeMillis();
      private static int DEF_STORING_TIMER = 60000;
      private int storingTimer;
      private transient Trace trace;

      private void initTrace() {
         this.trace = TraceFactory.getTraceFactory().getTrace(StoreableCachingMap.class);
      }

      private StoreableCachingMap(String folder, int storingTimer) {
         this.folder = folder;
         this.initTrace();
         this.storingTimer = storingTimer;
      }

      public static StoreableCachingMap init(String folder) {
         return init(folder, DEF_STORING_TIMER);
      }

      public static StoreableCachingMap init(String folder, int storingTimer) {
         File file = new File(folder + File.separator + "cache.idx");
         if (file.exists()) {
            try {
               ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
               StoreableCachingMap sm = (StoreableCachingMap)in.readObject();
               sm.initTrace();
               in.close();
               return sm;
            } catch (Exception var5) {
               Trace trace = TraceFactory.getTraceFactory().getTrace(StoreableCachingMap.class);
               trace.error("Error reading Storable Cache", var5);
            }
         }

         return new StoreableCachingMap(folder, storingTimer);
      }

      public Object get(Object obj) {
         try {
            if (super.containsKey(obj)) {
               String path = (String)super.get(obj);
               return path.equals("IDEM") ? SimpleCache.SAME_BYTES : this.readFromPath(path);
            } else {
               return null;
            }
         } catch (IOException var3) {
            this.trace.error("Error reading key:" + obj.toString(), var3);
            Dump.dumpWithException(var3);
            return null;
         }
      }

      public Object put(Object key, Object value) {
         try {
            String path = null;
            byte[] valueBytes = (byte[])((byte[])value);
            if (Arrays.equals(valueBytes, SimpleCache.SAME_BYTES)) {
               path = "IDEM";
            } else {
               path = this.writeToPath((String)key, valueBytes);
            }

            Object result = super.put(key, path);
            this.storeMap();
            return result;
         } catch (IOException var6) {
            this.trace.error("Error inserting in cache: key:" + key.toString() + "; value:" + value.toString(), var6);
            Dump.dumpWithException(var6);
            return null;
         }
      }

      public void storeMap() {
         long now = System.currentTimeMillis();
         if (now - this.lastStored >= (long)this.storingTimer) {
            File file = new File(this.folder + File.separator + "cache.idx");

            try {
               ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
               out.writeObject(this);
               out.close();
               this.lastStored = now;
            } catch (Exception var5) {
               this.trace.error("Error storing cache; cache file:" + file.getAbsolutePath(), var5);
               Dump.dumpWithException(var5);
            }

         }
      }

      private byte[] readFromPath(String fullPath) throws IOException {
         FileInputStream is = null;

         try {
            is = new FileInputStream(fullPath);
         } catch (FileNotFoundException var6) {
            System.out.println("FileNotFoundExceptions: The aspectj cache is corrupt. Please clean it and reboot the server. Cache path:" + this.folder);
            var6.printStackTrace();
            return null;
         }

         ByteArrayOutputStream buffer = new ByteArrayOutputStream();
         byte[] data = new byte[16384];

         int nRead;
         while((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
         }

         buffer.flush();
         is.close();
         return buffer.toByteArray();
      }

      private String writeToPath(String key, byte[] bytes) throws IOException {
         String fullPath = this.folder + File.separator + key;
         FileOutputStream fos = new FileOutputStream(fullPath);
         fos.write(bytes);
         fos.flush();
         fos.close();
         return fullPath;
      }
   }
}
