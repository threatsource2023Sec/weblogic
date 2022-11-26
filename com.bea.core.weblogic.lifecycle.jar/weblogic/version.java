package weblogic;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.BuilderHelper;
import weblogic.common.internal.PackageInfo;
import weblogic.common.internal.VersionInfo;
import weblogic.common.internal.VersionInfoFactory;
import weblogic.opatch.OPatchUtil;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.utils.StringUtils;

public class version {
   private static final String VERBOSE = "-verbose";
   private static final String WLS_TITLE = "WebLogic Server";
   private static PackageInfo WLS_PACKAGEINFO = null;
   public static final String DISPLAY_PATCHINFO_PROP = "weblogic.log.DisplayPatchInfo";

   private static VersionInfo getVersionInfo() {
      return VersionInfoFactory.getVersionInfo();
   }

   public static String getBuildVersion() {
      return getVersionInfo().getImplementationTitle();
   }

   public static String getReleaseBuildVersion() {
      return getVersionInfo().getImplementationVersion();
   }

   public static boolean findArg(String[] args, String arg) {
      if (args != null && args.length > 0 && arg != null && arg.length() > 0) {
         String[] var2 = args;
         int var3 = args.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String anArg = var2[var4];
            if (arg.equalsIgnoreCase(anArg)) {
               return true;
            }

            if (arg.equalsIgnoreCase("-" + anArg)) {
               return true;
            }

            if (("-" + arg).equalsIgnoreCase(anArg)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static void main(String[] argv) {
      long start = 0L;
      VersionInfoFactory.initialize(true);
      boolean isVerbose = findArg(argv, "-verbose");
      boolean isSlow = isVerbose || findArg(argv, "-legacy");
      boolean isHardCoded = findArg(argv, "-hardCoded");
      boolean isUsage = findArg(argv, "-help") || findArg(argv, "-h");
      boolean isDebug = findArg(argv, "-debug");
      if (isUsage) {
         usage();
      }

      try {
         start = System.nanoTime();
         if (isHardCoded) {
            System.out.println("\n" + getReleaseBuildVersion());
         }

         if (isSlow) {
            System.out.println("\n" + getVersions(isVerbose));
         } else {
            System.out.println("\n" + getWLSVersion());
         }

         long end = System.nanoTime();
         double nanos = (double)(end - start);
         double msecs = nanos / 1000.0 / 1000.0;
         if (isDebug) {
            System.out.printf("Time: %.1f msec\n", msecs);
         }

         if (isVerbose) {
            System.out.println("\n" + getServiceVersions());
         } else {
            System.out.println("\nUse 'weblogic.version -verbose' to get subsystem information");
            System.out.println("\nUse 'weblogic.utils.Versions' to get version information for all modules");
         }

         System.out.flush();
      } finally {
         System.exit(0);
      }

   }

   private static void usage() {
      System.out.println("\n________________________________________________________________________");
      System.out.println("\njava weblogic.version [-verbose|-hardCoded|-legacy|-help]");
      System.out.println("    -verbose   -- print out service versions");
      System.out.println("    -legacy    -- look inside every jar for version information");
      System.out.println("    -hardCoded -- use the hard-coded version information");
      System.out.println("    no args    -- use the legacy check through every jar file");
      System.out.println("    -help      -- Print this help page");
      System.out.println("\n________________________________________________________________________");
      System.exit(0);
   }

   public static String getWLSVersion() {
      return getVersionsInternal(false, true);
   }

   public static String getVersions() {
      return getVersions(false);
   }

   public static String getVersions(boolean isVerbose) {
      return getVersionsInternal(isVerbose, false);
   }

   private static String getVersionsInternal(boolean isVerbose, boolean wls_only) {
      if (isVerbose && wls_only) {
         throw new IllegalArgumentException();
      } else {
         StringBuffer str = new StringBuffer();
         PackageInfo[] packages = getVersionInfo().getPackages(wls_only);
         int packageLength = packages.length;
         boolean firstLine = true;

         for(int i = 0; i < packageLength; ++i) {
            String packageTitle = packages[i].getImplementationTitle();
            if (isVerbose || packageTitle.startsWith("WebLogic Server")) {
               if (!firstLine) {
                  str.append("\n");
               }

               str.append(packages[i].getImplementationTitle());
               if (isVerbose) {
                  str.append(" ImplVersion: " + packages[i].getImplementationVersion());
               }

               firstLine = false;
            }
         }

         boolean displayOPatches = Boolean.getBoolean("weblogic.log.DisplayPatchInfo");
         if (displayOPatches || isVerbose) {
            String[] patchInfos = OPatchUtil.getInstance().getPatchInfos(Home.getMiddlewareHomePath());
            if (patchInfos.length > 0) {
               str.append("\nOPatch Patches:");
               String[] var8 = patchInfos;
               int var9 = patchInfos.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  String patch = var8[var10];
                  str.append("\n").append(patch);
               }
            }
         }

         return str.toString();
      }
   }

   public static String getServiceVersions() {
      StringBuffer sb = new StringBuffer();
      sb.append(truncate("SERVICE NAME") + "\tVERSION INFORMATION\n");
      sb.append(truncate("============") + "\t===================\n");
      ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
      List serverServices = serviceLocator.getDescriptors(BuilderHelper.createContractFilter(ServerService.class.getName()));
      Iterator var3 = serverServices.iterator();

      while(var3.hasNext()) {
         ActiveDescriptor serverService = (ActiveDescriptor)var3.next();
         String className = serverService.getImplementation();

         try {
            String name = null;
            String version = null;
            Class clazz = Class.forName(className);
            Object obj = clazz.newInstance();
            Method versionMethod = clazz.getMethod("getVersion", (Class[])null);
            version = (String)versionMethod.invoke(obj, (Object[])null);
            Method nameMethod = clazz.getMethod("getName", (Class[])null);
            name = (String)nameMethod.invoke(obj, (Object[])null);
            if (name != null && version != null && version.trim().length() > 0) {
               sb.append(truncate(name) + "\t" + version + "\n");
            }
         } catch (Throwable var12) {
         }
      }

      return sb.toString();
   }

   private static String truncate(String name) {
      return name.length() >= 30 ? name.substring(0, 30) : StringUtils.padStringWidth(name, 30);
   }

   public static String[] getPLInfo() {
      String[] str = new String[2];
      PackageInfo[] packages = getVersionInfo().getPackages();
      PackageInfo wlsVersionInfo = null;

      for(int i = 0; i < packages.length; ++i) {
         String packageTitle = packages[i].getImplementationTitle();
         if (packageTitle.startsWith("WebLogic Server")) {
            wlsVersionInfo = packages[i];
            WLS_PACKAGEINFO = wlsVersionInfo;
            break;
         }
      }

      str[0] = "WebLogic Server";
      str[1] = "" + wlsVersionInfo.getMajor() + "." + wlsVersionInfo.getMinor();
      return str;
   }

   public static PackageInfo getWLSPackageInfo() {
      if (WLS_PACKAGEINFO == null) {
         getPLInfo();
      }

      return WLS_PACKAGEINFO;
   }

   public static final String getWebServerReleaseInfo() {
      PackageInfo[] packages = getVersionInfo().getPackages();
      StringBuffer sb = new StringBuffer();
      StringBuffer patchBuff = new StringBuffer();

      for(int i = 0; i < packages.length; ++i) {
         String title = packages[i].getImplementationTitle();
         String[] tokens;
         if (title.indexOf("Server") <= 0) {
            if (title.indexOf("Temporary Patch") > 0) {
               tokens = StringUtils.splitCompletely(title);

               for(int j = 0; j < tokens.length; ++j) {
                  if (tokens[j].startsWith("CR")) {
                     patchBuff.append(' ');
                     patchBuff.append(tokens[j]);
                     break;
                  }
               }
            }
         } else {
            tokens = StringUtils.splitCompletely(title);
            StringBuffer base = new StringBuffer();

            for(int j = 0; j < tokens.length; ++j) {
               base.append(tokens[j]);
               base.append(' ');
            }

            sb.append(base.toString());
         }
      }

      if (patchBuff.length() > 0) {
         sb.append("with");
         sb.append(patchBuff.toString());
      }

      return sb.toString();
   }
}
