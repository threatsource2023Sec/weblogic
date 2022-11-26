package weblogic.management.jmx;

import java.util.StringTokenizer;

class PreviewStringTokenizer extends StringTokenizer {
   String nextToken = null;

   public PreviewStringTokenizer(String str, String delim, boolean returnDelims) {
      super(str, delim, returnDelims);
   }

   public PreviewStringTokenizer(String str, String delim) {
      super(str, delim);
   }

   public PreviewStringTokenizer(String str) {
      super(str);
   }

   public String previewToken() {
      if (this.nextToken != null) {
         return this.nextToken;
      } else {
         if (super.hasMoreTokens()) {
            this.nextToken = (String)super.nextElement();
         }

         return this.nextToken;
      }
   }

   public String nextToken() {
      if (this.nextToken != null) {
         String result = this.nextToken;
         this.nextToken = null;
         return result;
      } else {
         return super.hasMoreTokens() ? super.nextToken() : null;
      }
   }

   public int countTokens() {
      return super.countTokens() + (this.nextToken == null ? 0 : 1);
   }

   public boolean hasMoreTokens() {
      return super.hasMoreTokens() || this.nextToken != null;
   }
}
