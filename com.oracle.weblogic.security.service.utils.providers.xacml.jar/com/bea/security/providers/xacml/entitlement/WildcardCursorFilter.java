package com.bea.security.providers.xacml.entitlement;

import java.util.ArrayList;
import java.util.List;

public class WildcardCursorFilter extends CursorFilter {
   private static final String WILDCARD = "*";
   private static final char ESCAPE_CHAR = '\\';
   private List compare = new ArrayList();

   public WildcardCursorFilter(String compareString) {
      int idx = indexOf(compareString, "*", 0);
      if (idx < 0) {
         this.compare.add(new EqualsWildcardHelper(compareString));
      } else {
         if (idx != 0) {
            this.compare.add(new StartsWithWildcardHelper(compareString.substring(0, idx)));
         }

         int sidx = idx + 1;
         idx = indexOf(compareString, "*", sidx);
         if (idx < 0) {
            this.compare.add(new EndsWithWildcardHelper(compareString.substring(sidx)));
         } else {
            this.compare.add(new ContainsWildcardHelper(compareString.substring(sidx, idx)));
            ++idx;

            while(true) {
               int nidx = indexOf(compareString, "*", idx);
               if (nidx < 0) {
                  if (idx + 1 < compareString.length()) {
                     this.compare.add(new EndsWithWildcardHelper(compareString.substring(idx + 1)));
                  }
                  break;
               }

               this.compare.add(new ContainsWildcardHelper(compareString.substring(idx, nidx)));
               idx = nidx + 1;
            }
         }
      }

   }

   private static int indexOf(String src, String str, int fromIndex) {
      int index;
      for(index = src.indexOf(str, fromIndex); index >= 0 && isEscapedCharAt(src, index); index = src.indexOf(str, index + 1)) {
      }

      return index;
   }

   private static int lastIndexOf(String src, String str, int fromIndex) {
      int index;
      for(index = src.lastIndexOf(str, fromIndex); index >= 0 && isEscapedCharAt(src, index); index = src.lastIndexOf(str, index - 1)) {
      }

      return index;
   }

   public boolean isValidResource(String resource) {
      return ((WildcardHelper)this.compare.get(0)).isMatch(resource, this.compare.size() > 1 ? this.compare.subList(1, this.compare.size()) : null);
   }

   private static boolean isEscapedCharAt(String str, int index) {
      --index;
      return index >= 0 && str.charAt(index) == '\\' && !isEscapedCharAt(str, index);
   }

   static class ContainsWildcardHelper implements WildcardHelper {
      private String term;

      public ContainsWildcardHelper(String term) {
         this.term = term;
      }

      public boolean isMatch(String data, List remainder) {
         if (data == null) {
            return false;
         } else {
            int idx = data.indexOf(this.term);
            if (idx < 0) {
               return false;
            } else if (remainder != null && !remainder.isEmpty()) {
               WildcardHelper next = (WildcardHelper)remainder.get(0);
               remainder = remainder.subList(1, remainder.size());

               while(!next.isMatch(data.substring(idx + this.term.length()), remainder)) {
                  idx = data.indexOf(this.term, idx + 1);
                  if (idx < 0) {
                     return false;
                  }
               }

               return true;
            } else {
               return true;
            }
         }
      }
   }

   static class EndsWithWildcardHelper implements WildcardHelper {
      private String term;

      public EndsWithWildcardHelper(String term) {
         this.term = term;
      }

      public boolean isMatch(String data, List remainder) {
         if (data != null && data.endsWith(this.term)) {
            return remainder != null && !remainder.isEmpty() ? ((WildcardHelper)remainder.get(0)).isMatch(data.substring(0, data.length() - this.term.length()), remainder.subList(1, remainder.size())) : true;
         } else {
            return false;
         }
      }
   }

   static class StartsWithWildcardHelper implements WildcardHelper {
      private String term;

      public StartsWithWildcardHelper(String term) {
         this.term = term;
      }

      public boolean isMatch(String data, List remainder) {
         if (data != null && data.startsWith(this.term)) {
            return remainder != null && !remainder.isEmpty() ? ((WildcardHelper)remainder.get(0)).isMatch(data.substring(this.term.length()), remainder.subList(1, remainder.size())) : true;
         } else {
            return false;
         }
      }
   }

   static class EqualsWildcardHelper implements WildcardHelper {
      private String term;

      public EqualsWildcardHelper(String term) {
         this.term = term;
      }

      public boolean isMatch(String data, List remainder) {
         if (data != null && data.equals(this.term)) {
            return remainder != null && !remainder.isEmpty() ? ((WildcardHelper)remainder.get(0)).isMatch(data, remainder.subList(1, remainder.size())) : true;
         } else {
            return false;
         }
      }
   }

   interface WildcardHelper {
      boolean isMatch(String var1, List var2);
   }
}
