package org.python.core;

import org.python.antlr.BaseParser;
import org.python.antlr.base.mod;

public enum CompileMode {
   eval {
      mod dispatch(BaseParser parser) {
         return parser.parseExpression();
      }
   },
   single {
      mod dispatch(BaseParser parser) {
         return parser.parseInteractive();
      }
   },
   exec {
      mod dispatch(BaseParser parser) {
         return parser.parseModule();
      }
   };

   private CompileMode() {
   }

   abstract mod dispatch(BaseParser var1);

   public static CompileMode getMode(String mode) {
      if (!mode.equals("exec") && !mode.equals("eval") && !mode.equals("single")) {
         throw Py.ValueError("compile() arg 3 must be 'exec' or 'eval' or 'single'");
      } else {
         return valueOf(mode);
      }
   }

   // $FF: synthetic method
   CompileMode(Object x2) {
      this();
   }
}
