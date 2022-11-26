package jnr.constants;

import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class Platform {
   private static final Platform INSTANCE = new Platform();
   public static final boolean FAKE = Boolean.valueOf(System.getProperty("jnr.constants.fake", "true"));
   public static final Map OS_NAMES = new HashMap() {
      public static final long serialVersionUID = 1L;

      {
         this.put("Mac OS X", "darwin");
         this.put("SunOS", "solaris");
      }
   };
   public static final Map ARCH_NAMES = new HashMap() {
      public static final long serialVersionUID = 1L;

      {
         this.put("x86", "i386");
      }
   };
   public static final String ARCH = initArchitecture();
   public static final String OS = initOperatingSystem();
   public static final String NAME;
   public static final int BIG_ENDIAN = 4321;
   public static final int LITTLE_ENDIAN = 1234;
   public static final int BYTE_ORDER;

   public static Platform getPlatform() {
      return INSTANCE;
   }

   private Platform() {
   }

   private static String getConstantsPackageName() {
      return Platform.PackageNameResolver.PACKAGE_NAME;
   }

   public String[] getPackagePrefixes() {
      return FAKE ? new String[]{this.getArchPackageName(), this.getOSPackageName(), this.getFakePackageName()} : new String[]{this.getArchPackageName(), this.getOSPackageName()};
   }

   public String getArchPackageName() {
      return String.format("%s.platform.%s.%s", getConstantsPackageName(), OS, ARCH);
   }

   public String getOSPackageName() {
      return String.format("%s.platform.%s", getConstantsPackageName(), OS);
   }

   public String getFakePackageName() {
      return String.format("%s.platform.fake", getConstantsPackageName());
   }

   private static String initOperatingSystem() {
      String osname = getProperty("os.name", "unknown").toLowerCase();
      Iterator var1 = OS_NAMES.keySet().iterator();

      String s;
      do {
         if (!var1.hasNext()) {
            if (osname.startsWith("windows")) {
               return "windows";
            }

            return osname;
         }

         s = (String)var1.next();
      } while(!s.equalsIgnoreCase(osname));

      return (String)OS_NAMES.get(s);
   }

   private static final String initArchitecture() {
      String arch = getProperty("os.arch", "unknown").toLowerCase();
      Iterator var1 = ARCH_NAMES.keySet().iterator();

      String s;
      do {
         if (!var1.hasNext()) {
            return arch;
         }

         s = (String)var1.next();
      } while(!s.equalsIgnoreCase(arch));

      return (String)ARCH_NAMES.get(s);
   }

   private static String getProperty(String property, String defValue) {
      try {
         return System.getProperty(property, defValue);
      } catch (SecurityException var3) {
         return defValue;
      }
   }

   static {
      NAME = String.format("%s-%s", ARCH, OS);
      BYTE_ORDER = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN) ? 4321 : 1234;
   }

   private static final class PackageNameResolver {
      public static final String PACKAGE_NAME = (new PackageNameResolver()).inferPackageName();

      private String inferPackageName() {
         try {
            Class cls = this.getClass();
            Package pkg = cls.getPackage();
            return pkg != null ? pkg.getName() : cls.getName().substring(0, cls.getName().lastIndexOf(46));
         } catch (NullPointerException var3) {
            return "jnr.constants";
         }
      }
   }
}
