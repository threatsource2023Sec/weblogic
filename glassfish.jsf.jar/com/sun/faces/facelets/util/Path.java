package com.sun.faces.facelets.util;

public final class Path {
   public static String normalize(String path) {
      if (path.length() == 0) {
         return path;
      } else {
         String n = path;

         boolean abs;
         for(abs = false; n.indexOf(92) >= 0; n = n.replace('\\', '/')) {
         }

         if (n.charAt(0) != '/') {
            n = '/' + n;
            abs = true;
         }

         int idx = false;

         while(true) {
            int idx = n.indexOf("%20");
            if (idx == -1) {
               while(true) {
                  idx = n.indexOf("/./");
                  if (idx == -1) {
                     if (abs) {
                        n = n.substring(1);
                     }

                     return n;
                  }

                  n = n.substring(0, idx) + n.substring(idx + 2);
               }
            }

            n = n.substring(0, idx) + " " + n.substring(idx + 3);
         }
      }
   }

   public static String relative(String ctx, String path) {
      if (path.length() == 0) {
         return context(ctx);
      } else {
         String c = context(normalize(ctx));
         String p = normalize(path);
         p = c + p;
         int idx = false;

         while(true) {
            int idx = p.indexOf("/../");
            if (idx == -1) {
               break;
            }

            int s = p.lastIndexOf(47, idx - 3);
            if (s == -1) {
               break;
            }

            p = p.substring(0, s) + p.substring(idx + 3);
         }

         return p;
      }
   }

   public static String context(String path) {
      int idx = path.lastIndexOf(47);
      return idx == -1 ? "/" : path.substring(0, idx + 1);
   }
}
