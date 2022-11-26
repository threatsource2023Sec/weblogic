package weblogic.cache;

import java.util.NoSuchElementException;

public class KeyParser {
   private String key;
   private String keyScope;

   public KeyParser(String string) {
      int scopePosition = string.indexOf(".");
      if (scopePosition == -1) {
         this.key = string;
         this.keyScope = "any";
      } else {
         this.key = string.substring(scopePosition + 1);
         this.keyScope = string.substring(0, scopePosition);
         if (this.key.length() == 0 || this.keyScope.length() == 0) {
            throw new NoSuchElementException("Invalid string attribute key = " + string);
         }
      }

   }

   public String getKey() {
      return this.key;
   }

   public String getKeyScope() {
      return this.keyScope;
   }
}
