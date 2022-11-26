package weblogic.utils.enumerations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipFile;

public abstract class ResourceEnumerator {
   private static final int STARTS_WITH = 0;
   private static final int ENDS_WITH = 1;
   private static final int CONTAINS = 2;
   private static final int EXACT_MATCH = 3;
   private static final int COMPLEX_MATCH = 4;
   protected String[] ignore;
   protected int[] ignoreFlags;
   protected String[] match;
   protected int[] matchFlags;
   protected Map matchers = new HashMap();

   public ResourceEnumerator(String[] ignore_x, String[] match_x) {
      int len = ignore_x.length;
      this.ignore = new String[len + 1];
      System.arraycopy(ignore_x, 0, this.ignore, 0, len);
      this.ignore[len++] = "/META-INF/*";
      this.match = match_x;
      this.ignoreFlags = this.parse(this.ignore);
      this.matchFlags = this.parse(this.match);
   }

   public abstract void close();

   private int[] parse(String[] sa) {
      int salen = sa.length;
      int[] flags = new int[salen];

      for(int i = 0; i < salen; ++i) {
         sa[i] = sa[i].replace(File.separatorChar, '/');
         int len = sa[i].length();
         String str = sa[i];
         if (str.indexOf("*", 1) > 0 && str.indexOf("*", 1) < str.length() - 1) {
            flags[i] = 4;
            this.matchers.put(str, new Matcher(str));
         } else if (str.startsWith("*") && str.endsWith("*")) {
            flags[i] = 2;
            str = str.substring(1, len - 1);
         } else if (str.startsWith("*")) {
            flags[i] = 1;
            str = str.substring(1, len);
         } else if (str.endsWith("*")) {
            flags[i] = 0;
            str = str.substring(0, len - 1);
         } else {
            flags[i] = 3;
         }

         sa[i] = str;
      }

      return flags;
   }

   private static String str(int i) {
      String s = null;
      switch (i) {
         case 0:
            s = "StartsWith";
            break;
         case 1:
            s = "EndsWith";
            break;
         case 2:
            s = "Contains";
            break;
         case 3:
            s = "Exact";
            break;
         case 4:
            s = "Matchs";
            break;
         default:
            s = "<UNKNOWN>";
      }

      return s;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("[").append(this.getClass().getName()).append(": ignore=");

      int i;
      String s;
      for(i = 0; i < this.ignoreFlags.length; ++i) {
         s = str(this.ignoreFlags[i]);
         sb.append(s).append(" \"").append(this.ignore[i]).append("\"|");
      }

      sb.append(", match=");

      for(i = 0; i < this.matchFlags.length; ++i) {
         s = str(this.matchFlags[i]);
         sb.append(s).append(" \"").append(this.match[i]).append("\"|");
      }

      sb.append(']');
      return sb.toString();
   }

   static void p(String s) {
      System.err.println("[JSPEnum] " + s);
   }

   public abstract String getNextURI();

   protected boolean shouldMatch(String uri) {
      return this.match(uri, this.matchFlags, this.match);
   }

   protected boolean shouldIgnore(String uri) {
      return this.match(uri, this.ignoreFlags, this.ignore);
   }

   protected String fix(String s) {
      return s.replace(File.separatorChar, '/');
   }

   protected boolean match(String uri, int[] flags, String[] sa) {
      for(int i = 0; i < flags.length; ++i) {
         switch (flags[i]) {
            case 0:
               if (uri.startsWith(sa[i])) {
                  return true;
               }
               break;
            case 1:
               if (uri.endsWith(sa[i])) {
                  return true;
               }
               break;
            case 2:
               if (uri.indexOf(sa[i]) >= 0) {
                  return true;
               }
               break;
            case 3:
               if (sa[i].equals(uri)) {
                  return true;
               }
               break;
            case 4:
               if (((Matcher)this.matchers.get(sa[i])).match(uri)) {
                  return true;
               }
         }
      }

      return false;
   }

   public static ResourceEnumerator makeInstance(File dirOrJar, String[] ignore, String[] match) throws IllegalArgumentException {
      if (ignore == null) {
         ignore = new String[0];
      }

      if (dirOrJar.isDirectory()) {
         return new DirectoryResourceEnumerator(dirOrJar, ignore, match);
      } else {
         try {
            return new JarResourceEnumerator(new ZipFile(dirOrJar), ignore, match);
         } catch (IOException var4) {
            throw new IllegalArgumentException(dirOrJar.getAbsolutePath() + " is neither a directory nor a zip file");
         }
      }
   }

   public static void main(String[] a) throws Exception {
      String[] I = new String[]{"*.html", "*foo/bar*"};
      String[] M = new String[]{"*.jsp"};
      ResourceEnumerator enum_ = makeInstance(new File(a[0].replace('/', File.separatorChar)), I, M);
      System.err.println(enum_.toString());

      String o;
      while((o = enum_.getNextURI()) != null) {
         System.err.println(o.toString());
      }

   }

   private static class Matcher {
      private String[] matchs;
      private boolean startWithStar = false;
      private boolean endWithStar = false;

      Matcher(String match) {
         String[] tokens = match.split("\\*");
         List list = new ArrayList(tokens.length);
         String[] var4 = tokens;
         int var5 = tokens.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String t = var4[var6];
            if (t != null && t.length() != 0) {
               list.add(t);
            }
         }

         this.matchs = (String[])list.toArray(new String[list.size()]);
         this.startWithStar = match.startsWith("*");
         this.endWithStar = match.endsWith("*");
      }

      public boolean match(String str) {
         if (this.matchs.length == 0) {
            return true;
         } else {
            int index = 0;
            if (!this.startWithStar && !str.startsWith(this.matchs[0])) {
               return false;
            } else {
               String[] var3 = this.matchs;
               int var4 = var3.length;

               for(int var5 = 0; var5 < var4; ++var5) {
                  String t = var3[var5];
                  index = str.indexOf(t, index);
                  if (index < 0) {
                     return false;
                  }

                  index += t.length();
               }

               if (!this.endWithStar && index < str.length()) {
                  return false;
               } else {
                  return true;
               }
            }
         }
      }
   }
}
