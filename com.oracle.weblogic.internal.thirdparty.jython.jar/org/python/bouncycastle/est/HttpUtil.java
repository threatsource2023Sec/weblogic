package org.python.bouncycastle.est;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class HttpUtil {
   static String mergeCSL(String var0, Map var1) {
      StringWriter var2 = new StringWriter();
      var2.write(var0);
      var2.write(32);
      boolean var3 = false;
      Iterator var4 = var1.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry var5 = (Map.Entry)var4.next();
         if (!var3) {
            var3 = true;
         } else {
            var2.write(44);
         }

         var2.write((String)var5.getKey());
         var2.write("=\"");
         var2.write((String)var5.getValue());
         var2.write(34);
      }

      return var2.toString();
   }

   static Map splitCSL(String var0, String var1) {
      var1 = var1.trim();
      if (var1.startsWith(var0)) {
         var1 = var1.substring(var0.length());
      }

      return (new PartLexer(var1)).Parse();
   }

   public static String[] append(String[] var0, String var1) {
      if (var0 == null) {
         return new String[]{var1};
      } else {
         int var2 = var0.length;
         String[] var3 = new String[var2 + 1];
         System.arraycopy(var0, 0, var3, 0, var2);
         var3[var2] = var1;
         return var3;
      }
   }

   static class Headers extends HashMap {
      public Headers() {
      }

      public String getFirstValue(String var1) {
         String[] var2 = this.getValues(var1);
         return var2 != null && var2.length > 0 ? var2[0] : null;
      }

      public String[] getValues(String var1) {
         var1 = this.actualKey(var1);
         return var1 == null ? null : (String[])this.get(var1);
      }

      private String actualKey(String var1) {
         if (this.containsKey(var1)) {
            return var1;
         } else {
            Iterator var2 = this.keySet().iterator();

            String var3;
            do {
               if (!var2.hasNext()) {
                  return null;
               }

               var3 = (String)var2.next();
            } while(!var1.equalsIgnoreCase(var3));

            return var3;
         }
      }

      private boolean hasHeader(String var1) {
         return this.actualKey(var1) != null;
      }

      public void set(String var1, String var2) {
         this.put(var1, new String[]{var2});
      }

      public void add(String var1, String var2) {
         this.put(var1, HttpUtil.append((String[])this.get(var1), var2));
      }

      public void ensureHeader(String var1, String var2) {
         if (!this.containsKey(var1)) {
            this.set(var1, var2);
         }

      }

      public Object clone() {
         Headers var1 = new Headers();
         Iterator var2 = this.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry var3 = (Map.Entry)var2.next();
            var1.put((String)var3.getKey(), this.copy((String[])((String[])var3.getValue())));
         }

         return var1;
      }

      private String[] copy(String[] var1) {
         String[] var2 = new String[var1.length];
         System.arraycopy(var1, 0, var2, 0, var2.length);
         return var2;
      }
   }

   static class PartLexer {
      private final String src;
      int last = 0;
      int p = 0;

      PartLexer(String var1) {
         this.src = var1;
      }

      Map Parse() {
         HashMap var1 = new HashMap();
         String var2 = null;
         String var3 = null;

         while(true) {
            if (this.p < this.src.length()) {
               this.skipWhiteSpace();
               var2 = this.consumeAlpha();
               if (var2.length() == 0) {
                  throw new IllegalArgumentException("Expecting alpha label.");
               }

               this.skipWhiteSpace();
               if (!this.consumeIf('=')) {
                  throw new IllegalArgumentException("Expecting assign: '='");
               }

               this.skipWhiteSpace();
               if (!this.consumeIf('"')) {
                  throw new IllegalArgumentException("Expecting start quote: '\"'");
               }

               this.discard();
               var3 = this.consumeUntil('"');
               this.discard(1);
               var1.put(var2, var3);
               this.skipWhiteSpace();
               if (this.consumeIf(',')) {
                  this.discard();
                  continue;
               }
            }

            return var1;
         }
      }

      private String consumeAlpha() {
         for(char var1 = this.src.charAt(this.p); this.p < this.src.length() && (var1 >= 'a' && var1 <= 'z' || var1 >= 'A' && var1 <= 'Z'); var1 = this.src.charAt(this.p)) {
            ++this.p;
         }

         String var2 = this.src.substring(this.last, this.p);
         this.last = this.p;
         return var2;
      }

      private void skipWhiteSpace() {
         while(this.p < this.src.length() && this.src.charAt(this.p) < '!') {
            ++this.p;
         }

         this.last = this.p;
      }

      private boolean consumeIf(char var1) {
         if (this.p < this.src.length() && this.src.charAt(this.p) == var1) {
            ++this.p;
            return true;
         } else {
            return false;
         }
      }

      private String consumeUntil(char var1) {
         while(this.p < this.src.length() && this.src.charAt(this.p) != var1) {
            ++this.p;
         }

         String var2 = this.src.substring(this.last, this.p);
         this.last = this.p;
         return var2;
      }

      private void discard() {
         this.last = this.p;
      }

      private void discard(int var1) {
         this.p += var1;
         this.last = this.p;
      }
   }
}
