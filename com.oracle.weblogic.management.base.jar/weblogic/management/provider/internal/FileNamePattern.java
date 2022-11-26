package weblogic.management.provider.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNamePattern {
   private static final Pattern EmptyPattern = Pattern.compile("");
   String dir;
   Pattern pathPattern;
   Pattern filenamePattern;
   String file;

   public FileNamePattern(String dirStr, String patnStr) {
      dirStr = dirStr == null ? null : dirStr.trim();
      if (dirStr != null && dirStr.length() != 0) {
         this.dir = dirStr.replace('\\', '/');
         if (this.dir.contains("../")) {
            throw new IllegalArgumentException();
         } else {
            if (!this.dir.endsWith("/")) {
               this.dir = this.dir + '/';
            }

            if (this.dir.equals("./")) {
               this.dir = "";
            }

            String[] jpatnStrs = this.convertAntFileNamePattern2(patnStr);
            if (jpatnStrs == null) {
               this.pathPattern = null;
               this.filenamePattern = null;
            } else {
               this.pathPattern = jpatnStrs[0].length() == 0 ? EmptyPattern : Pattern.compile(jpatnStrs[0]);
               this.filenamePattern = Pattern.compile(jpatnStrs[1]);
            }

            this.file = null;
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   public FileNamePattern(String fileStr) {
      if (fileStr != null && fileStr.trim().length() != 0) {
         this.file = fileStr.replace('\\', '/');
         if (this.file.contains("../")) {
            throw new IllegalArgumentException();
         } else {
            this.dir = null;
            this.pathPattern = null;
            this.filenamePattern = null;
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   protected String[] convertAntFileNamePattern2(String str) {
      if (str != null && str.trim().length() != 0) {
         str = str.replace('\\', '/');
         String[] result = new String[2];
         if (!str.endsWith("/**") && !str.equals("**")) {
            int pos = str.lastIndexOf(47);
            if (pos < 0) {
               result[0] = "";
               result[1] = this.convertAntFileNamePattern(str);
            } else {
               result[0] = this.convertAntFileNamePattern(str.substring(0, pos));
               if (!result[0].startsWith(".*")) {
                  result[0] = '/' + result[0];
               }

               if (!result[0].endsWith(".*")) {
                  result[0] = result[0] + '/';
               }

               result[1] = this.convertAntFileNamePattern(str.substring(pos + 1));
            }

            return result;
         } else {
            result[0] = this.convertAntFileNamePattern(str);
            result[1] = this.convertAntFileNamePattern("*");
            return result;
         }
      } else {
         return null;
      }
   }

   protected String convertAntFileNamePattern(String str) {
      if (str != null && str.trim().length() != 0) {
         str = str.replace('\\', '/');
         StringBuffer buf = new StringBuffer();
         char[] chars = str.toCharArray();

         for(int i = 0; i < chars.length; ++i) {
            switch (chars[i]) {
               case '$':
               case '(':
               case ')':
               case '.':
               case '[':
               case ']':
               case '^':
               case '{':
               case '}':
                  buf.append("\\").append(chars[i]);
                  break;
               case '*':
                  if (i + 1 < chars.length && chars[i + 1] == '*') {
                     buf.append(".*");
                     ++i;
                     break;
                  }

                  buf.append("[^/]*");
                  break;
               case '?':
                  buf.append("[^/]");
                  break;
               default:
                  buf.append(chars[i]);
            }
         }

         return buf.toString();
      } else {
         return null;
      }
   }

   public boolean matches(String name) {
      if (name != null && name.trim().length() != 0) {
         name = name.replace('\\', '/');
         if (this.file != null) {
            if (this.file.equals(name)) {
               return true;
            }
         } else {
            if (this.dir.equals(name + '/') || this.dir.equals(name)) {
               return true;
            }

            if (name.startsWith(this.dir)) {
               if (this.pathPattern == null) {
                  return true;
               }

               String leftover = name.substring(this.dir.length());
               int pos = leftover.lastIndexOf(47);
               String pathPart;
               String namePart;
               if (pos < 0) {
                  pathPart = "";
                  namePart = leftover;
               } else {
                  pathPart = leftover.substring(0, pos + 1);
                  if (pathPart.length() > 0 && pathPart.charAt(0) != '/') {
                     pathPart = '/' + pathPart;
                  }

                  namePart = leftover.substring(pos + 1);
               }

               Matcher matcher1 = this.pathPattern.matcher(pathPart);
               Matcher matcher2 = this.filenamePattern.matcher(namePart);
               return matcher1.matches() && matcher2.matches();
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      if (this.file != null) {
         buf.append("file=").append(this.file);
      } else {
         buf.append("dir=").append(this.dir);
         if (this.pathPattern != null || this.filenamePattern != null) {
            buf.append(",pattern=").append(this.pathPattern).append(",").append(this.filenamePattern);
         }
      }

      return buf.toString();
   }
}
