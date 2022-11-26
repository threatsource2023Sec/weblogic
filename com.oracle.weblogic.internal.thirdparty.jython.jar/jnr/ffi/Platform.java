package jnr.ffi;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Platform {
   private static final Locale LOCALE;
   private final OS os;
   private final CPU cpu;
   private final int addressSize;
   private final int longSize;
   protected final Pattern libPattern;

   private static OS determineOS() {
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

   private static Platform determinePlatform(OS os) {
      switch (os) {
         case DARWIN:
            return new Darwin();
         case LINUX:
            return new Linux();
         case WINDOWS:
            return new Windows();
         case UNKNOWN:
            return new Unsupported(os);
         default:
            return new Default(os);
      }
   }

   private static Platform determinePlatform() {
      String providerName = System.getProperty("jnr.ffi.provider");

      try {
         Class c = Class.forName(providerName + "$Platform");
         return (Platform)c.newInstance();
      } catch (ClassNotFoundException var2) {
         return determinePlatform(determineOS());
      } catch (IllegalAccessException var3) {
         throw new ExceptionInInitializerError(var3);
      } catch (InstantiationException var4) {
         throw new ExceptionInInitializerError(var4);
      }
   }

   private static CPU determineCPU() {
      String archString = System.getProperty("os.arch");
      if (!equalsIgnoreCase("x86", archString) && !equalsIgnoreCase("i386", archString) && !equalsIgnoreCase("i86pc", archString) && !equalsIgnoreCase("i686", archString)) {
         if (!equalsIgnoreCase("x86_64", archString) && !equalsIgnoreCase("amd64", archString)) {
            if (!equalsIgnoreCase("ppc", archString) && !equalsIgnoreCase("powerpc", archString)) {
               if (!equalsIgnoreCase("ppc64", archString) && !equalsIgnoreCase("powerpc64", archString)) {
                  if (!equalsIgnoreCase("ppc64le", archString) && !equalsIgnoreCase("powerpc64le", archString)) {
                     if (!equalsIgnoreCase("s390", archString) && !equalsIgnoreCase("s390x", archString)) {
                        if (equalsIgnoreCase("aarch64", archString)) {
                           return Platform.CPU.AARCH64;
                        } else {
                           CPU[] var1 = Platform.CPU.values();
                           int var2 = var1.length;

                           for(int var3 = 0; var3 < var2; ++var3) {
                              CPU cpu = var1[var3];
                              if (equalsIgnoreCase(cpu.name(), archString)) {
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
                  return "little".equals(System.getProperty("sun.cpu.endian")) ? Platform.CPU.PPC64LE : Platform.CPU.PPC64;
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

   public Platform(OS os, CPU cpu, int addressSize, int longSize, String libPattern) {
      this.os = os;
      this.cpu = cpu;
      this.addressSize = addressSize;
      this.longSize = longSize;
      this.libPattern = Pattern.compile(libPattern);
   }

   private Platform(OS os) {
      this.os = os;
      this.cpu = determineCPU();
      String libpattern;
      switch (os) {
         case DARWIN:
            libpattern = "lib.*\\.(dylib|jnilib)$";
            break;
         case WINDOWS:
            libpattern = ".*\\.dll$";
            break;
         default:
            libpattern = "lib.*\\.so.*$";
      }

      this.libPattern = Pattern.compile(libpattern);
      this.addressSize = calculateAddressSize(this.cpu);
      this.longSize = os == Platform.OS.WINDOWS ? 32 : this.addressSize;
   }

   private static int calculateAddressSize(CPU cpu) {
      int dataModel = Integer.getInteger("sun.arch.data.model");
      if (dataModel != 32 && dataModel != 64) {
         switch (cpu) {
            case I386:
            case PPC:
            case SPARC:
               dataModel = 32;
               break;
            case X86_64:
            case PPC64:
            case PPC64LE:
            case SPARCV9:
            case S390X:
            case AARCH64:
               dataModel = 64;
               break;
            default:
               throw new ExceptionInInitializerError("Cannot determine cpu address size");
         }
      }

      return dataModel;
   }

   public static Platform getNativePlatform() {
      return Platform.SingletonHolder.PLATFORM;
   }

   /** @deprecated */
   @Deprecated
   public static Platform getPlatform() {
      return Platform.SingletonHolder.PLATFORM;
   }

   public final OS getOS() {
      return this.os;
   }

   public final CPU getCPU() {
      return this.cpu;
   }

   public final boolean isBSD() {
      return this.os == Platform.OS.FREEBSD || this.os == Platform.OS.OPENBSD || this.os == Platform.OS.NETBSD || this.os == Platform.OS.DARWIN;
   }

   public final boolean isUnix() {
      return this.os != Platform.OS.WINDOWS;
   }

   /** @deprecated */
   public final int longSize() {
      return this.longSize;
   }

   /** @deprecated */
   public final int addressSize() {
      return this.addressSize;
   }

   public String getName() {
      return this.cpu + "-" + this.os;
   }

   public String getStandardCLibraryName() {
      switch (this.os) {
         case LINUX:
            return "libc.so.6";
         case WINDOWS:
            return "msvcrt";
         case UNKNOWN:
         default:
            return "c";
         case SOLARIS:
            return "c";
         case FREEBSD:
         case NETBSD:
            return "c";
         case AIX:
            return this.addressSize == 32 ? "libc.a(shr.o)" : "libc.a(shr_64.o)";
      }
   }

   public String mapLibraryName(String libName) {
      return this.libPattern.matcher(libName).find() ? libName : System.mapLibraryName(libName);
   }

   public String locateLibrary(String libName, List libraryPath) {
      String mappedName = this.mapLibraryName(libName);
      Iterator var4 = libraryPath.iterator();

      File libFile;
      do {
         if (!var4.hasNext()) {
            return mappedName;
         }

         String path = (String)var4.next();
         libFile = new File(path, mappedName);
      } while(!libFile.exists());

      return libFile.getAbsolutePath();
   }

   private static boolean startsWithIgnoreCase(String s1, String s2) {
      return s1.startsWith(s2) || s1.toUpperCase(LOCALE).startsWith(s2.toUpperCase(LOCALE)) || s1.toLowerCase(LOCALE).startsWith(s2.toLowerCase(LOCALE));
   }

   private static boolean equalsIgnoreCase(String s1, String s2) {
      return s1.equalsIgnoreCase(s2) || s1.toUpperCase(LOCALE).equals(s2.toUpperCase(LOCALE)) || s1.toLowerCase(LOCALE).equals(s2.toLowerCase(LOCALE));
   }

   // $FF: synthetic method
   Platform(OS x0, Object x1) {
      this(x0);
   }

   static {
      LOCALE = Locale.ENGLISH;
   }

   private static class Windows extends Supported {
      public Windows() {
         super(Platform.OS.WINDOWS);
      }
   }

   static final class Linux extends Supported {
      public Linux() {
         super(Platform.OS.LINUX);
      }

      public String locateLibrary(String libName, List libraryPaths) {
         Pattern exclude;
         if (this.getCPU() == Platform.CPU.X86_64) {
            exclude = Pattern.compile(".*(lib[a-z]*32|i[0-9]86).*");
         } else {
            exclude = Pattern.compile(".*(lib[a-z]*64|amd64|x86_64).*");
         }

         final Pattern versionedLibPattern = Pattern.compile("lib" + libName + "\\.so((?:\\.[0-9]+)*)$");
         FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
               return versionedLibPattern.matcher(name).matches();
            }
         };
         Map matches = new LinkedHashMap();
         Iterator var7 = libraryPaths.iterator();

         while(true) {
            File[] files;
            do {
               String bestMatch;
               do {
                  if (!var7.hasNext()) {
                     int[] bestVersion = null;
                     bestMatch = null;
                     Iterator var21 = matches.entrySet().iterator();

                     while(var21.hasNext()) {
                        Map.Entry entry = (Map.Entry)var21.next();
                        String file = (String)entry.getKey();
                        int[] fileVersion = (int[])entry.getValue();
                        if (compareVersions(fileVersion, bestVersion) > 0) {
                           bestMatch = file;
                           bestVersion = fileVersion;
                        }
                     }

                     return bestMatch != null ? bestMatch : this.mapLibraryName(libName);
                  }

                  bestMatch = (String)var7.next();
               } while(exclude.matcher(bestMatch).matches());

               File libraryPath = new File(bestMatch);
               files = libraryPath.listFiles(filter);
            } while(files == null);

            File[] var11 = files;
            int var12 = files.length;

            for(int var13 = 0; var13 < var12; ++var13) {
               File file = var11[var13];
               Matcher matcher = versionedLibPattern.matcher(file.getName());
               String versionString = matcher.matches() ? matcher.group(1) : "";
               int[] version;
               if (versionString != null && !versionString.isEmpty()) {
                  String[] parts = versionString.split("\\.");
                  version = new int[parts.length - 1];

                  for(int i = 1; i < parts.length; ++i) {
                     version[i - 1] = Integer.parseInt(parts[i]);
                  }
               } else {
                  version = new int[0];
               }

               matches.put(file.getAbsolutePath(), version);
            }
         }
      }

      private static int compareVersions(int[] version1, int[] version2) {
         if (version1 == null) {
            return version2 == null ? 0 : -1;
         } else if (version2 == null) {
            return 1;
         } else {
            int commonLength = Math.min(version1.length, version2.length);

            for(int i = 0; i < commonLength; ++i) {
               if (version1[i] < version2[i]) {
                  return -1;
               }

               if (version1[i] > version2[i]) {
                  return 1;
               }
            }

            if (version1.length < version2.length) {
               return -1;
            } else if (version1.length > version2.length) {
               return 1;
            } else {
               return 0;
            }
         }
      }

      public String mapLibraryName(String libName) {
         return !"c".equals(libName) && !"libc.so".equals(libName) ? super.mapLibraryName(libName) : "libc.so.6";
      }
   }

   private static final class Darwin extends Supported {
      public Darwin() {
         super(Platform.OS.DARWIN);
      }

      public String mapLibraryName(String libName) {
         return this.libPattern.matcher(libName).find() ? libName : "lib" + libName + ".dylib";
      }

      public String getName() {
         return "Darwin";
      }
   }

   private static final class Default extends Supported {
      public Default(OS os) {
         super(os);
      }
   }

   private static class Unsupported extends Platform {
      public Unsupported(OS os) {
         super(os, null);
      }
   }

   private static class Supported extends Platform {
      public Supported(OS os) {
         super(os, null);
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
      MIPS32,
      ARM,
      AARCH64,
      UNKNOWN;

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

   private static final class SingletonHolder {
      static final Platform PLATFORM = Platform.determinePlatform();
   }
}
