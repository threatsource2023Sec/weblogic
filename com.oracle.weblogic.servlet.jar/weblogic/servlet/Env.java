package weblogic.servlet;

import java.util.ArrayList;
import weblogic.utils.StringUtils;

public final class Env {
   private String[][] envStrings;

   public static synchronized native String globalGetenv(String var0);

   public static synchronized native void globalPutenv(String var0);

   private static synchronized native String getenvIndex(int var0);

   public static Env getenv() {
      return new Env();
   }

   public synchronized ArrayList getWholeEnv() {
      ArrayList env = new ArrayList();
      int k = 0;

      String tmp;
      while((tmp = getenvIndex(k)) != null) {
         ++k;
         env.add(tmp);
      }

      return env;
   }

   public synchronized String[] getWholeStrEnv() {
      ArrayList env = this.getWholeEnv();
      String[] strings = new String[env.size()];
      env.toArray(strings);
      return strings;
   }

   public void putenv(String nameEqVal) {
      int ind;
      if ((ind = nameEqVal.indexOf(61)) < 0) {
         throw new IllegalArgumentException("Illegal env string: '" + nameEqVal + "', it doesn't contain '='");
      } else {
         String name = nameEqVal.substring(0, ind);
         String val;
         if (ind <= nameEqVal.length() - 1) {
            val = "";
         } else {
            val = nameEqVal.substring(ind + 1);
         }

         this.putenv(name, val);
      }
   }

   public synchronized void putenv(String name, String val) {
      if (this.envStrings == null) {
         this.envStrings = this.getenvSplit();
      }

      int len = this.envStrings.length;

      for(int i = 0; i < len; ++i) {
         if (name.equals(this.envStrings[i][0])) {
            this.envStrings[i][1] = val;
            return;
         }
      }

      String[][] tmp = new String[len + 1][];
      System.arraycopy(this.envStrings, 0, tmp, 0, len);
      tmp[len][0] = name;
      tmp[len][1] = val;
      this.envStrings = tmp;
   }

   public synchronized String getenv(String name) {
      if (this.envStrings == null) {
         this.envStrings = this.getenvSplit();
      }

      int len = this.envStrings.length;

      for(int i = 0; i < len; ++i) {
         if (name.equals(this.envStrings[i][0])) {
            return this.envStrings[i][1];
         }
      }

      return null;
   }

   private synchronized String[][] getenvSplit() {
      ArrayList env = new ArrayList();
      int k = 0;

      String tmp;
      while((tmp = getenvIndex(k)) != null) {
         ++k;
         env.add(tmp);
      }

      String[][] ret = new String[k][];

      for(int i = 0; i < k; ++i) {
         ret[i] = StringUtils.split((String)env.get(i), '=');
      }

      return ret;
   }

   static {
      System.loadLibrary("wlenv");
   }
}
