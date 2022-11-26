package org.stringtemplate.v4.compiler;

import org.antlr.runtime.Token;

public class FormalArgument {
   public String name;
   public int index;
   public Token defaultValueToken;
   public Object defaultValue;
   public CompiledST compiledDefaultValue;

   public FormalArgument(String name) {
      this.name = name;
   }

   public FormalArgument(String name, Token defaultValueToken) {
      this.name = name;
      this.defaultValueToken = defaultValueToken;
   }

   public int hashCode() {
      return this.name.hashCode() + this.defaultValueToken.hashCode();
   }

   public boolean equals(Object o) {
      if (o != null && o instanceof FormalArgument) {
         FormalArgument other = (FormalArgument)o;
         if (!this.name.equals(other.name)) {
            return false;
         } else {
            return (this.defaultValueToken == null || other.defaultValueToken != null) && (this.defaultValueToken != null || other.defaultValueToken == null);
         }
      } else {
         return false;
      }
   }

   public String toString() {
      return this.defaultValueToken != null ? this.name + "=" + this.defaultValueToken.getText() : this.name;
   }
}
