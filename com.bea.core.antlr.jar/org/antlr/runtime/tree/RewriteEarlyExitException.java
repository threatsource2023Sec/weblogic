package org.antlr.runtime.tree;

public class RewriteEarlyExitException extends RewriteCardinalityException {
   public RewriteEarlyExitException() {
      super((String)null);
   }

   public RewriteEarlyExitException(String elementDescription) {
      super(elementDescription);
   }
}
