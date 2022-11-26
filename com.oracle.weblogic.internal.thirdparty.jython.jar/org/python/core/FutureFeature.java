package org.python.core;

import org.python.antlr.ParseException;

public enum FutureFeature implements Pragma {
   nested_scopes(CodeFlag.CO_NESTED),
   division(CodeFlag.CO_FUTURE_DIVISION),
   generators(CodeFlag.CO_GENERATOR_ALLOWED),
   absolute_import(CodeFlag.CO_FUTURE_ABSOLUTE_IMPORT),
   with_statement(CodeFlag.CO_FUTURE_WITH_STATEMENT),
   print_function(CodeFlag.CO_FUTURE_PRINT_FUNCTION),
   unicode_literals(CodeFlag.CO_FUTURE_UNICODE_LITERALS),
   braces {
      public void addTo(PragmaReceiver features) {
         throw new ParseException("not a chance");
      }
   },
   GIL {
      public void addTo(PragmaReceiver features) {
         throw new ParseException("Never going to happen!");
      }
   },
   global_interpreter_lock {
      public void addTo(PragmaReceiver features) {
         GIL.addTo(features);
      }
   };

   public static final String MODULE_NAME = "__future__";
   public static final Pragma.PragmaModule PRAGMA_MODULE = new Pragma.PragmaModule("__future__") {
      public Pragma getPragma(String name) {
         return FutureFeature.getFeature(name);
      }

      public Pragma getStarPragma() {
         throw new ParseException("future feature * is not defined");
      }
   };
   private final CodeFlag flag;

   private FutureFeature(CodeFlag flag) {
      this.flag = flag;
   }

   private FutureFeature() {
      this((CodeFlag)null);
   }

   public void addTo(PragmaReceiver features) {
      features.add(this);
   }

   public static void addFeature(String featureName, PragmaReceiver features) {
      getFeature(featureName).addTo(features);
   }

   private static FutureFeature getFeature(String featureName) {
      try {
         return valueOf(featureName);
      } catch (IllegalArgumentException var2) {
         throw new ParseException("future feature " + featureName + " is not defined");
      }
   }

   public void setFlag(CompilerFlags cflags) {
      if (this.flag != null) {
         cflags.setFlag(this.flag);
      }

   }

   // $FF: synthetic method
   FutureFeature(Object x2) {
      this();
   }
}
