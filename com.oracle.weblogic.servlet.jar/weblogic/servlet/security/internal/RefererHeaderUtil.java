package weblogic.servlet.security.internal;

public class RefererHeaderUtil {
   private static String HTTP_SCHEMA = "http://";
   private static String HTTPS_SCHEMA = "https://";

   public static boolean isRelativeURI(String referer) {
      referer = referer.toLowerCase();
      return !referer.startsWith(HTTP_SCHEMA) && !referer.startsWith(HTTPS_SCHEMA);
   }

   public static RefererInfo getRefererInfo(String referer) {
      int ind = referer.indexOf(":");
      if (ind < 0) {
         return null;
      } else if (ind + 3 < referer.length() && referer.charAt(ind + 1) == '/' && referer.charAt(ind + 2) == '/') {
         String protocol = referer.substring(0, ind).toLowerCase();
         if (!protocol.equals("http") && !protocol.equals("https")) {
            return null;
         } else {
            ind += 3;
            int end = referer.indexOf(47, ind);
            if (end < 0) {
               end = referer.indexOf(63, ind);
               if (end < 0) {
                  end = referer.length();
               }
            }

            String host = referer.substring(ind, end);
            int port = -1;

            try {
               if (host.charAt(0) == '[') {
                  ind = host.indexOf(93);
                  if (ind <= -1) {
                     return null;
                  }

                  String nhost = host;
                  host = host.substring(0, ind + 1);
                  if (nhost.length() > ind + 1) {
                     if (nhost.charAt(ind + 1) != ':') {
                        return null;
                     }

                     ++ind;
                     if (nhost.length() > ind + 1) {
                        port = Integer.parseInt(nhost.substring(ind + 1));
                     }
                  }
               } else {
                  ind = host.indexOf(58);
                  if (ind >= 0) {
                     if (host.length() > ind + 1) {
                        port = Integer.parseInt(host.substring(ind + 1));
                     }

                     host = host.substring(0, ind);
                  }
               }
            } catch (NumberFormatException var7) {
               return null;
            }

            if (port == -1) {
               port = protocol.equals("http") ? 80 : 443;
            }

            return new RefererInfo(host, port);
         }
      } else {
         return null;
      }
   }

   static class RefererInfo {
      String host;
      int port;

      RefererInfo(String host, int port) {
         this.host = host;
         this.port = port;
      }
   }
}
