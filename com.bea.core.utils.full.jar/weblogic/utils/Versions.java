package weblogic.utils;

import weblogic.common.internal.PackageInfo;
import weblogic.common.internal.VersionInfo;

public class Versions {
   private static PackageInfo wlsPackageInfo;

   public static void main(String[] args) {
      String result = getVersions(args);
      System.out.println("\n" + result);
   }

   public static String getVersions(String[] args) {
      StringBuffer str = new StringBuffer();
      PackageInfo[] packages = VersionInfo.getPackageInfos(args);
      int packageLength = packages.length;
      if (packageLength > 0) {
         for(int i = 0; i < packageLength - 1; ++i) {
            str.append(packages[i].getImplementationTitle() + " ImplVersion: " + packages[i].getImplementationVersion());
            str.append("\n");
         }

         str.append(packages[packageLength - 1].getImplementationTitle() + " ImplVersion: " + packages[packageLength - 1].getImplementationVersion());
      }

      return str.toString();
   }

   public static String getWebLogicServerVersion() {
      if (wlsPackageInfo == null) {
         String[] wls = new String[]{"WebLogic Server"};
         PackageInfo[] packages = VersionInfo.getPackageInfos(wls);
         if (packages.length > 0) {
            wlsPackageInfo = packages[0];
         }
      }

      return wlsPackageInfo == null ? "1.0.0.0" : wlsPackageInfo.getImplementationVersion();
   }

   public static String getWebLogicServerMajorVersion() {
      String wlsVersion = getWebLogicServerVersion();
      return StringUtils.splitCompletely(wlsVersion, ".", false)[0];
   }

   public static String getWebLogicServerMinorVersion() {
      String wlsVersion = getWebLogicServerVersion();
      return StringUtils.splitCompletely(wlsVersion, ".", false)[1];
   }
}
