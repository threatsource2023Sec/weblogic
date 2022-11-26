package com.kenai.jffi.internal;

import com.kenai.jffi.Util;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class StubLoader {
   public static final int VERSION_MAJOR = getVersionField("MAJOR");
   public static final int VERSION_MINOR = getVersionField("MINOR");
   private static final String versionClassName = "com.kenai.jffi.Version";
   private static final Locale LOCALE;
   private static final String bootPropertyFilename = "boot.properties";
   private static final String bootLibraryPropertyName = "jffi.boot.library.path";
   private static final String stubLibraryName;
   private static volatile OS os;
   private static volatile CPU cpu;
   private static volatile Throwable failureCause;
   private static volatile boolean loaded;

   public static final boolean isLoaded() {
      return loaded;
   }

   public static final Throwable getFailureCause() {
      return failureCause;
   }

   private static OS determineOS() {
      String osName = System.getProperty("os.name").split(" ")[0];
      if (!Util.startsWithIgnoreCase(osName, "mac", LOCALE) && !Util.startsWithIgnoreCase(osName, "darwin", LOCALE)) {
         if (Util.startsWithIgnoreCase(osName, "linux", LOCALE)) {
            return StubLoader.OS.LINUX;
         } else if (!Util.startsWithIgnoreCase(osName, "sunos", LOCALE) && !Util.startsWithIgnoreCase(osName, "solaris", LOCALE)) {
            if (Util.startsWithIgnoreCase(osName, "aix", LOCALE)) {
               return StubLoader.OS.AIX;
            } else if (Util.startsWithIgnoreCase(osName, "openbsd", LOCALE)) {
               return StubLoader.OS.OPENBSD;
            } else if (Util.startsWithIgnoreCase(osName, "freebsd", LOCALE)) {
               return StubLoader.OS.FREEBSD;
            } else if (Util.startsWithIgnoreCase(osName, "windows", LOCALE)) {
               return StubLoader.OS.WINDOWS;
            } else {
               throw new RuntimeException("cannot determine operating system");
            }
         } else {
            return StubLoader.OS.SOLARIS;
         }
      } else {
         return StubLoader.OS.DARWIN;
      }
   }

   private static CPU determineCPU() {
      String archString = System.getProperty("os.arch", "unknown");
      if (!Util.equalsIgnoreCase("x86", archString, LOCALE) && !Util.equalsIgnoreCase("i386", archString, LOCALE) && !Util.equalsIgnoreCase("i86pc", archString, LOCALE)) {
         if (!Util.equalsIgnoreCase("x86_64", archString, LOCALE) && !Util.equalsIgnoreCase("amd64", archString, LOCALE)) {
            if (!Util.equalsIgnoreCase("ppc", archString, LOCALE) && !Util.equalsIgnoreCase("powerpc", archString, LOCALE)) {
               if (!Util.equalsIgnoreCase("ppc64", archString, LOCALE) && !Util.equalsIgnoreCase("powerpc64", archString, LOCALE)) {
                  if (!Util.equalsIgnoreCase("ppc64le", archString, LOCALE) && !Util.equalsIgnoreCase("powerpc64le", archString, LOCALE)) {
                     if (!Util.equalsIgnoreCase("s390", archString, LOCALE) && !Util.equalsIgnoreCase("s390x", archString, LOCALE)) {
                        if (Util.equalsIgnoreCase("arm", archString, LOCALE)) {
                           return StubLoader.CPU.ARM;
                        } else if (Util.equalsIgnoreCase("aarch64", archString, LOCALE)) {
                           return StubLoader.CPU.AARCH64;
                        } else {
                           CPU[] var1 = StubLoader.CPU.values();
                           int var2 = var1.length;

                           for(int var3 = 0; var3 < var2; ++var3) {
                              CPU cpu = var1[var3];
                              if (Util.equalsIgnoreCase(cpu.name(), archString, LOCALE)) {
                                 return cpu;
                              }
                           }

                           throw new RuntimeException("cannot determine CPU");
                        }
                     } else {
                        return StubLoader.CPU.S390X;
                     }
                  } else {
                     return StubLoader.CPU.PPC64LE;
                  }
               } else {
                  return "little".equals(System.getProperty("sun.cpu.endian")) ? StubLoader.CPU.PPC64LE : StubLoader.CPU.PPC64;
               }
            } else {
               return StubLoader.CPU.PPC;
            }
         } else {
            return StubLoader.CPU.X86_64;
         }
      } else {
         return StubLoader.CPU.I386;
      }
   }

   public static CPU getCPU() {
      return cpu != null ? cpu : (cpu = determineCPU());
   }

   public static OS getOS() {
      return os != null ? os : (os = determineOS());
   }

   private static String getStubLibraryName() {
      return stubLibraryName;
   }

   public static String getPlatformName() {
      if (getOS().equals(StubLoader.OS.DARWIN)) {
         return "Darwin";
      } else {
         String osName = System.getProperty("os.name").split(" ")[0];
         return getCPU().name().toLowerCase(LOCALE) + "-" + osName;
      }
   }

   private static String getStubLibraryPath() {
      return "jni/" + getPlatformName() + "/" + System.mapLibraryName(stubLibraryName);
   }

   static void load() {
      String libName = getStubLibraryName();
      List errors = new ArrayList();
      String bootPath = getBootPath();
      if (bootPath == null || !loadFromBootPath(libName, bootPath, errors)) {
         String libraryPath = System.getProperty("java.library.path");
         if (libraryPath == null || !loadFromBootPath(libName, libraryPath, errors)) {
            try {
               loadFromJar();
            } catch (Throwable var8) {
               errors.add(var8);
               if (!errors.isEmpty()) {
                  Collections.reverse(errors);
                  CharArrayWriter caw = new CharArrayWriter();
                  PrintWriter pw = new PrintWriter(caw);
                  Iterator var6 = errors.iterator();

                  while(var6.hasNext()) {
                     Throwable t = (Throwable)var6.next();
                     t.printStackTrace(pw);
                  }

                  throw new UnsatisfiedLinkError(new String(caw.toCharArray()));
               }
            }
         }
      }
   }

   private static String getBootPath() {
      String bootPath = System.getProperty("jffi.boot.library.path");
      if (bootPath != null) {
         return bootPath;
      } else {
         InputStream is = getResourceAsStream("boot.properties");
         if (is != null) {
            Properties p = new Properties();

            Object var4;
            try {
               p.load(is);
               String var3 = p.getProperty("jffi.boot.library.path");
               return var3;
            } catch (IOException var14) {
               var4 = null;
            } finally {
               try {
                  is.close();
               } catch (IOException var13) {
               }

            }

            return (String)var4;
         } else {
            return null;
         }
      }
   }

   private static String getAlternateLibraryPath(String path) {
      return path.endsWith("dylib") ? path.substring(0, path.lastIndexOf("dylib")) + "jnilib" : path.substring(0, path.lastIndexOf("jnilib")) + "dylib";
   }

   private static boolean loadFromBootPath(String libName, String bootPath, Collection errors) {
      String[] dirs = bootPath.split(File.pathSeparator);

      for(int i = 0; i < dirs.length; ++i) {
         String soname = System.mapLibraryName(libName);
         File stub = new File(new File(dirs[i], getPlatformName()), soname);
         if (!stub.isFile()) {
            stub = new File(new File(dirs[i]), soname);
         }

         String path = stub.getAbsolutePath();
         if (stub.isFile()) {
            try {
               System.load(path);
               return true;
            } catch (UnsatisfiedLinkError var10) {
               errors.add(var10);
            }
         }

         if (getOS() == StubLoader.OS.DARWIN) {
            path = getAlternateLibraryPath(path);
            if ((new File(path)).isFile()) {
               try {
                  System.load(path);
                  return true;
               } catch (UnsatisfiedLinkError var9) {
                  errors.add(var9);
               }
            }
         }
      }

      return false;
   }

   private static String dlExtension() {
      switch (getOS()) {
         case WINDOWS:
            return "dll";
         case DARWIN:
            return "dylib";
         default:
            return "so";
      }
   }

   private static void loadFromJar() throws IOException, UnsatisfiedLinkError {
      InputStream is = getStubLibraryStream();
      FileOutputStream os = null;

      try {
         File dstFile = File.createTempFile("jffi", "." + dlExtension());
         dstFile.deleteOnExit();
         os = new FileOutputStream(dstFile);
         ReadableByteChannel srcChannel = Channels.newChannel(is);

         for(long pos = 0L; is.available() > 0; pos += os.getChannel().transferFrom(srcChannel, pos, (long)Math.max(4096, is.available()))) {
         }

         os.close();
         os = null;
         System.load(dstFile.getAbsolutePath());
         dstFile.delete();
      } catch (IOException var9) {
         throw new UnsatisfiedLinkError(var9.getMessage());
      } finally {
         if (os != null) {
            os.close();
         }

         is.close();
      }
   }

   private static InputStream getStubLibraryStream() {
      String stubPath = getStubLibraryPath();
      String[] paths = new String[]{stubPath, "/" + stubPath};
      String[] var2 = paths;
      int var3 = paths.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String path = var2[var4];
         InputStream is = getResourceAsStream(path);
         if (is == null && getOS() == StubLoader.OS.DARWIN) {
            is = getResourceAsStream(getAlternateLibraryPath(path));
         }

         if (is != null) {
            return is;
         }
      }

      throw new UnsatisfiedLinkError("could not locate stub library in jar file.  Tried " + Arrays.deepToString(paths));
   }

   private static InputStream getResourceAsStream(String resourceName) {
      ClassLoader[] cls = new ClassLoader[]{ClassLoader.getSystemClassLoader(), StubLoader.class.getClassLoader(), Thread.currentThread().getContextClassLoader()};
      ClassLoader[] var2 = cls;
      int var3 = cls.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ClassLoader cl = var2[var4];
         InputStream is;
         if (cl != null && (is = cl.getResourceAsStream(resourceName)) != null) {
            return is;
         }
      }

      return null;
   }

   private static int getVersionField(String name) {
      try {
         Class c = Class.forName("com.kenai.jffi.Version");
         return (Integer)c.getField(name).get(c);
      } catch (Throwable var2) {
         throw new RuntimeException(var2);
      }
   }

   static {
      LOCALE = Locale.ENGLISH;
      stubLibraryName = String.format("jffi-%d.%d", VERSION_MAJOR, VERSION_MINOR);
      os = null;
      cpu = null;
      failureCause = null;
      loaded = false;

      try {
         load();
         loaded = true;
      } catch (Throwable var1) {
         failureCause = var1;
      }

   }

   public static enum CPU {
      I386,
      X86_64,
      PPC,
      PPC64,
      PPC64LE,
      SPARC,
      SPARCV9,
      S390X,
      ARM,
      AARCH64,
      UNKNOWN;

      public String toString() {
         return this.name().toLowerCase(StubLoader.LOCALE);
      }
   }

   public static enum OS {
      DARWIN,
      FREEBSD,
      NETBSD,
      OPENBSD,
      LINUX,
      SOLARIS,
      WINDOWS,
      AIX,
      ZLINUX,
      UNKNOWN;

      public String toString() {
         return this.name().toLowerCase(StubLoader.LOCALE);
      }
   }
}
