package net.shibboleth.utilities.java.support.xml;

import javax.annotation.Nullable;

public class XMLParserException extends Exception {
   private static final long serialVersionUID = 7260425832643941776L;

   public XMLParserException() {
   }

   public XMLParserException(@Nullable String message) {
      super(message);
   }

   public XMLParserException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public XMLParserException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
