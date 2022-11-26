package org.antlr.runtime.tree;

public class RewriteCardinalityException extends RuntimeException {
   public String elementDescription;

   public RewriteCardinalityException(String elementDescription) {
      this.elementDescription = elementDescription;
   }

   public String getMessage() {
      return this.elementDescription != null ? this.elementDescription : null;
   }
}
