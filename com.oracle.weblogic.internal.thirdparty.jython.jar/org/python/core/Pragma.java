package org.python.core;

import org.python.antlr.ParseException;

public interface Pragma {
   void addTo(PragmaReceiver var1);

   public static final class ForbiddenPragmaModule extends PragmaModule {
      private final String message;

      public ForbiddenPragmaModule(String name) {
         this(name, "pragma " + name + " is not allowed in this context.");
      }

      public ForbiddenPragmaModule(String name, String message) {
         super(name);
         this.message = message;
      }

      public Pragma getPragma(String name) {
         throw new ParseException(this.message);
      }

      public Pragma getStarPragma() {
         throw new ParseException(this.message);
      }

      public void addTo(PragmaReceiver receiver) {
         throw new ParseException(this.message);
      }
   }

   public abstract static class PragmaModule {
      public final String name;

      protected PragmaModule(String name) {
         this.name = name;
      }

      public abstract Pragma getPragma(String var1);

      public abstract Pragma getStarPragma();
   }
}
