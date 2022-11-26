package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

/** @deprecated */
@Deprecated
public class WildcardFilter extends AbstractFileFilter implements Serializable {
   private static final long serialVersionUID = -5037645902506953517L;
   private final String[] wildcards;

   public WildcardFilter(String wildcard) {
      if (wildcard == null) {
         throw new IllegalArgumentException("The wildcard must not be null");
      } else {
         this.wildcards = new String[]{wildcard};
      }
   }

   public WildcardFilter(String[] wildcards) {
      if (wildcards == null) {
         throw new IllegalArgumentException("The wildcard array must not be null");
      } else {
         this.wildcards = new String[wildcards.length];
         System.arraycopy(wildcards, 0, this.wildcards, 0, wildcards.length);
      }
   }

   public WildcardFilter(List wildcards) {
      if (wildcards == null) {
         throw new IllegalArgumentException("The wildcard list must not be null");
      } else {
         this.wildcards = (String[])wildcards.toArray(new String[wildcards.size()]);
      }
   }

   public boolean accept(File dir, String name) {
      if (dir != null && (new File(dir, name)).isDirectory()) {
         return false;
      } else {
         String[] var3 = this.wildcards;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String wildcard = var3[var5];
            if (FilenameUtils.wildcardMatch(name, wildcard)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean accept(File file) {
      if (file.isDirectory()) {
         return false;
      } else {
         String[] var2 = this.wildcards;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String wildcard = var2[var4];
            if (FilenameUtils.wildcardMatch(file.getName(), wildcard)) {
               return true;
            }
         }

         return false;
      }
   }
}
