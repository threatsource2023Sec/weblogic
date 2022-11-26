package com.bea.security.providers.xacml.entitlement.parser;

public class Escaping {
   private char[] SpecialChars;
   private boolean searching;
   private char searchChar;

   public Escaping(char[] specials) {
      this.searching = false;
      this.SpecialChars = new char[specials.length];
      System.arraycopy(specials, 0, this.SpecialChars, 0, specials.length);
   }

   public Escaping(char[] specials, char c) {
      this(specials);
      this.searching = true;
      this.searchChar = c;
   }

   public String escapeString(String inName) {
      if (inName == null) {
         return null;
      } else {
         char[] name = inName.toCharArray();
         char[] out = new char[name.length * 2];
         int k = 0;

         for(int i = 0; i < name.length; ++i) {
            out[k] = name[i];

            for(int j = 0; j < this.SpecialChars.length; ++j) {
               if (this.SpecialChars[j] == name[i] && (!this.searching || this.searchChar != name[i])) {
                  out[k++] = this.SpecialChars[0];
                  out[k] = (char)(65 + j);
                  break;
               }
            }

            ++k;
         }

         String escaped = k == name.length ? inName : new String(out, 0, k);
         return escaped;
      }
   }

   public String unescapeString(String inName) {
      char[] name = inName.toCharArray();
      int k = -1;

      for(int i = 0; i < name.length; ++i) {
         if (name[i] == this.SpecialChars[0]) {
            if (k == -1) {
               k = i;
            }

            int var10001 = k++;
            ++i;
            name[var10001] = this.SpecialChars[name[i] - 65];
         } else if (k >= 0) {
            name[k++] = name[i];
         }
      }

      String unescaped = k > 0 ? new String(name, 0, k) : inName;
      return unescaped;
   }
}
