package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;

public class WildcardFileFilter extends AbstractFileFilter implements Serializable {
   private static final long serialVersionUID = -7426486598995782105L;
   private final String[] wildcards;
   private final IOCase caseSensitivity;

   public WildcardFileFilter(String wildcard) {
      this(wildcard, IOCase.SENSITIVE);
   }

   public WildcardFileFilter(String wildcard, IOCase caseSensitivity) {
      if (wildcard == null) {
         throw new IllegalArgumentException("The wildcard must not be null");
      } else {
         this.wildcards = new String[]{wildcard};
         this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
      }
   }

   public WildcardFileFilter(String[] wildcards) {
      this(wildcards, IOCase.SENSITIVE);
   }

   public WildcardFileFilter(String[] wildcards, IOCase caseSensitivity) {
      if (wildcards == null) {
         throw new IllegalArgumentException("The wildcard array must not be null");
      } else {
         this.wildcards = new String[wildcards.length];
         System.arraycopy(wildcards, 0, this.wildcards, 0, wildcards.length);
         this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
      }
   }

   public WildcardFileFilter(List wildcards) {
      this(wildcards, IOCase.SENSITIVE);
   }

   public WildcardFileFilter(List wildcards, IOCase caseSensitivity) {
      if (wildcards == null) {
         throw new IllegalArgumentException("The wildcard list must not be null");
      } else {
         this.wildcards = (String[])wildcards.toArray(new String[wildcards.size()]);
         this.caseSensitivity = caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity;
      }
   }

   public boolean accept(File dir, String name) {
      String[] var3 = this.wildcards;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String wildcard = var3[var5];
         if (FilenameUtils.wildcardMatch(name, wildcard, this.caseSensitivity)) {
            return true;
         }
      }

      return false;
   }

   public boolean accept(File file) {
      String name = file.getName();
      String[] var3 = this.wildcards;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String wildcard = var3[var5];
         if (FilenameUtils.wildcardMatch(name, wildcard, this.caseSensitivity)) {
            return true;
         }
      }

      return false;
   }

   public String toString() {
      StringBuilder buffer = new StringBuilder();
      buffer.append(super.toString());
      buffer.append("(");
      if (this.wildcards != null) {
         for(int i = 0; i < this.wildcards.length; ++i) {
            if (i > 0) {
               buffer.append(",");
            }

            buffer.append(this.wildcards[i]);
         }
      }

      buffer.append(")");
      return buffer.toString();
   }
}
