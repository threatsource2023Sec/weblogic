package weblogic.rmi.utils.io;

import weblogic.utils.net.InetAddressHelper;

public class Codebase {
   private static String serverURL;

   public static void setDefaultCodebase(String url) {
      url = InetAddressHelper.convertIfIPV6URL(url);
      serverURL = url;
   }

   public static String getDefaultCodebase() {
      return serverURL;
   }
}
