package com.bea.logging;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public final class LogfileFilter implements FilenameFilter {
   private String wlsLogfile;
   private static final int NUM_OF_DIGITS_IN_SUFFIX = String.valueOf(99999).length();
   private Pattern pattern;

   public LogfileFilter(String name) {
      this.pattern = null;
      this.wlsLogfile = name;
      if (this.wlsLogfile.indexOf(37) > -1) {
         try {
            String s = Pattern.compile("%.*?%").matcher(name).replaceAll(".*?") + "([0-9]{" + NUM_OF_DIGITS_IN_SUFFIX + "})*";
            this.pattern = Pattern.compile(s);
         } catch (PatternSyntaxException var3) {
            this.pattern = null;
         }
      }

   }

   public LogfileFilter(File name) {
      this(name.getName());
   }

   public boolean accept(File dir, String fname) {
      if (this.pattern != null) {
         Matcher m = this.pattern.matcher(fname);
         return m.matches();
      } else {
         int baseLen = this.wlsLogfile.length();
         if (!fname.startsWith(this.wlsLogfile)) {
            return false;
         } else if (fname.length() != baseLen + NUM_OF_DIGITS_IN_SUFFIX) {
            return false;
         } else {
            for(int i = 0; i < NUM_OF_DIGITS_IN_SUFFIX; ++i) {
               if (!Character.isDigit(fname.charAt(baseLen + i))) {
                  return false;
               }
            }

            return true;
         }
      }
   }
}
