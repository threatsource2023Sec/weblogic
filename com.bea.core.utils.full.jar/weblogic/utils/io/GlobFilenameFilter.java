package weblogic.utils.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.StringTokenizer;

public class GlobFilenameFilter implements FilenameFilter {
   public final String GLOB_CHAR = "*";
   private String glob;

   public GlobFilenameFilter(String glob) {
      this.glob = glob;
   }

   public boolean accept(File dir, String name) {
      boolean anchoredStart = !this.glob.startsWith("*");
      boolean anchoredEnd = !this.glob.endsWith("*");
      StringTokenizer toker = new StringTokenizer(this.glob, "*");
      int idx = 0;
      boolean failed = false;

      do {
         if (!toker.hasMoreElements()) {
            return true;
         }

         String part = toker.nextToken();
         idx = name.indexOf(part, idx);
         if (idx == -1) {
            return false;
         }

         if (anchoredStart) {
            if (idx > 0) {
               return false;
            }

            anchoredStart = false;
         }

         idx += part.length();
      } while(!anchoredEnd || toker.hasMoreElements() || idx >= name.length());

      return false;
   }
}
