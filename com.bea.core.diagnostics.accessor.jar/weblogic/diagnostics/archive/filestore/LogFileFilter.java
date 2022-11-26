package weblogic.diagnostics.archive.filestore;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LogFileFilter implements FileFilter {
   private Pattern filePattern;
   private String baseName;

   LogFileFilter(String pat) {
      this.baseName = pat.replaceAll("%", "");
      pat = pat.replaceAll("\\.", "\\\\.");
      Pattern p = Pattern.compile("%.*%");
      Matcher matcher = p.matcher(pat);
      if (matcher.find()) {
         pat = matcher.replaceFirst("(.*)");
      } else {
         pat = pat + "\\d+";
      }

      pat = "^" + pat + "$";
      this.filePattern = Pattern.compile(pat);
   }

   public boolean accept(File pathname) {
      if (pathname.length() <= 0L) {
         return false;
      } else {
         String name = pathname.getName();
         if (name.equals(this.baseName)) {
            return false;
         } else {
            Matcher matcher = this.filePattern.matcher(name);
            boolean retVal = matcher.find();
            return retVal;
         }
      }
   }
}
