package com.kenai.jffi;

import java.util.Locale;

public abstract class Platform {
   private static final Locale LOCALE;
   private final OS os;
   private final int javaVersionMajor;

   private static final OS determineOS() {
      String osName = System.getProperty("os.name").split(" ")[0];
      if (!startsWithIgnoreCase(osName, "mac") && !startsWithIgnoreCase(osName, "darwin")) {
         if (startsWithIgnoreCase(osName, "linux")) {
            return Platform.OS.LINUX;
         } else if (!startsWithIgnoreCase(osName, "sunos") && !startsWithIgnoreCase(osName, "solaris")) {
            if (startsWithIgnoreCase(osName, "aix")) {
               return Platform.OS.AIX;
            } else if (startsWithIgnoreCase(osName, "openbsd")) {
               return Platform.OS.OPENBSD;
            } else if (startsWithIgnoreCase(osName, "freebsd")) {
               return Platform.OS.FREEBSD;
            } else {
               return startsWithIgnoreCase(osName, "windows") ? Platform.OS.WINDOWS : Platform.OS.UNKNOWN;
            }
         } else {
            return Platform.OS.SOLARIS;
         }
      } else {
         return Platform.OS.DARWIN;
      }
   }

   private static final Platform determinePlatform(OS os) {
      switch (os) {
         case DARWIN:
            return newDarwinPlatform();
         case WINDOWS:
            return newWindowsPlatform();
         default:
            return newDefaultPlatform(os);
      }
   }

   private static Platform newDarwinPlatform() {
      return new Darwin();
   }

   private static Platform newWindowsPlatform() {
      return new Windows();
   }

   private static Platform newDefaultPlatform(OS os) {
      return new Default(os);
   }

   private Platform(OS os) {
      this.os = os;
      int version = 5;

      try {
         String versionString = System.getProperty("java.version");
         if (versionString != null) {
            String[] v = versionString.split("\\.");
            version = Integer.valueOf(v[1]);
         }
      } catch (Exception var5) {
         version = 5;
      }

      this.javaVersionMajor = version;
   }

   public static final Platform getPlatform() {
      return Platform.SingletonHolder.PLATFORM;
   }

   public final OS getOS() {
      return this.os;
   }

   public final CPU getCPU() {
      return Platform.ArchHolder.cpu;
   }

   public final int getJavaMajorVersion() {
      return this.javaVersionMajor;
   }

   public abstract int longSize();

   public final int addressSize() {
      return this.getCPU().dataModel;
   }

   public final long addressMask() {
      return this.getCPU().addressMask;
   }

   public String getName() {
      String osName = System.getProperty("os.name").split(" ")[0];
      return this.getCPU().name().toLowerCase(LOCALE) + "-" + osName;
   }

   public String mapLibraryName(String libName) {
      return libName.matches(this.getLibraryNamePattern()) ? libName : System.mapLibraryName(libName);
   }

   public String getLibraryNamePattern() {
      return "lib.*\\.so.*$";
   }

   public boolean isSupported() {
      int version = Foreign.getInstance().getVersion();
      if ((version & 16776960) == (Foreign.VERSION_MAJOR << 16 | Foreign.VERSION_MINOR << 8)) {
         return true;
      } else {
         throw new UnsatisfiedLinkError("Incorrect native library version");
      }
   }

   private static boolean startsWithIgnoreCase(String s1, String s2) {
      return s1.startsWith(s2) || s1.toUpperCase(LOCALE).startsWith(s2.toUpperCase(LOCALE)) || s1.toLowerCase(LOCALE).startsWith(s2.toLowerCase(LOCALE));
   }

   // $FF: synthetic method
   Platform(OS x0, Object x1) {
      this(x0);
   }

   static {
      LOCALE = Locale.ENGLISH;
   }

   private static final class Windows extends Platform {
      public Windows() {
         super(Platform.OS.WINDOWS, null);
      }

      public String getLibraryNamePattern() {
         return ".*\\.dll$";
      }

      public final int longSize() {
         return 32;
      }
   }

   private static final class Darwin extends Platform {
      public Darwin() {
         super(Platform.OS.DARWIN, null);
      }

      public String mapLibraryName(String libName) {
         return libName.matches(this.getLibraryNamePattern()) ? libName : "lib" + libName + ".dylib";
      }

      public String getLibraryNamePattern() {
         return "lib.*\\.(dylib|jnilib)$";
      }

      public String getName() {
         return "Darwin";
      }

      public final int longSize() {
         return this.getCPU().dataModel;
      }
   }

   private static final class Default extends Platform {
      public Default(OS os) {
         super(os, null);
      }

      public final int longSize() {
         return this.getCPU().dataModel;
      }
   }

   private static final class ArchHolder {
      public static final CPU cpu = determineCPU();

      private static CPU determineCPU() {
         String archString = null;

         try {
            archString = Foreign.getInstance().getArch();
         } catch (UnsatisfiedLinkError var5) {
         }

         if (archString == null || "unknown".equals(archString)) {
            archString = System.getProperty("os.arch", "unknown");
         }

         if (!Util.equalsIgnoreCase("x86", archString, Platform.LOCALE) && !Util.equalsIgnoreCase("i386", archString, Platform.LOCALE) && !Util.equalsIgnoreCase("i86pc", archString, Platform.LOCALE)) {
            if (!Util.equalsIgnoreCase("x86_64", archString, Platform.LOCALE) && !Util.equalsIgnoreCase("amd64", archString, Platform.LOCALE)) {
               if (!Util.equalsIgnoreCase("ppc", archString, Platform.LOCALE) && !Util.equalsIgnoreCase("powerpc", archString, Platform.LOCALE)) {
                  if (!Util.equalsIgnoreCase("ppc64", archString, Platform.LOCALE) && !Util.equalsIgnoreCase("powerpc64", archString, Platform.LOCALE)) {
                     if (!Util.equalsIgnoreCase("ppc64le", archString, Platform.LOCALE) && !Util.equalsIgnoreCase("powerpc64le", archString, Platform.LOCALE)) {
                        if (!Util.equalsIgnoreCase("s390", archString, Platform.LOCALE) && !Util.equalsIgnoreCase("s390x", archString, Platform.LOCALE)) {
                           if (Util.equalsIgnoreCase("arm", archString, Platform.LOCALE)) {
                              return Platform.CPU.ARM;
                           } else if (Util.equalsIgnoreCase("aarch64", archString, Platform.LOCALE)) {
                              return Platform.CPU.AARCH64;
                           } else {
                              CPU[] var1 = Platform.CPU.values();
                              int var2 = var1.length;

                              for(int var3 = 0; var3 < var2; ++var3) {
                                 CPU cpu = var1[var3];
                                 if (cpu.name().equalsIgnoreCase(archString)) {
                                    return cpu;
                                 }
                              }

                              return Platform.CPU.UNKNOWN;
                           }
                        } else {
                           return Platform.CPU.S390X;
                        }
                     } else {
                        return Platform.CPU.PPC64LE;
                     }
                  } else {
                     return Platform.CPU.PPC64;
                  }
               } else {
                  return Platform.CPU.PPC;
               }
            } else {
               return Platform.CPU.X86_64;
            }
         } else {
            return Platform.CPU.I386;
         }
      }
   }

   private static final class SingletonHolder {
      static final Platform PLATFORM = Platform.determinePlatform(Platform.determineOS());
   }

   public static enum CPU {
      I386(32),
      X86_64(64),
      PPC(32),
      PPC64(64),
      PPC64LE(64),
      SPARC(32),
      SPARCV9(64),
      S390X(64),
      ARM(32),
      AARCH64(64),
      UNKNOWN(64);

      public final int dataModel;
      public final long addressMask;

      private CPU(int dataModel) {
         this.dataModel = dataModel;
         this.addressMask = dataModel == 32 ? 4294967295L : -1L;
      }

      public String toString() {
         return this.name().toLowerCase(Platform.LOCALE);
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
         return this.name().toLowerCase(Platform.LOCALE);
      }
   }
}
