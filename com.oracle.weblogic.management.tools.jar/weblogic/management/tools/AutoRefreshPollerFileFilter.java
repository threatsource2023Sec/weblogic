package weblogic.management.tools;

import java.io.File;
import java.io.FileFilter;
import weblogic.utils.StringUtils;

public class AutoRefreshPollerFileFilter implements FileFilter {
   public static final boolean debug = false;
   private String[] filter;

   public AutoRefreshPollerFileFilter(String fil) {
      if (fil != null) {
         this.filter = StringUtils.splitCompletely(fil, ",");

         for(int i = 0; i < this.filter.length; ++i) {
         }
      }

   }

   public boolean accept(File dir) {
      if (this.filter == null) {
         return true;
      } else {
         String name = dir.getName();

         for(int i = 0; i < this.filter.length; ++i) {
            if (name.endsWith(this.filter[i]) && !dir.isDirectory()) {
               return true;
            }
         }

         return false;
      }
   }
}
