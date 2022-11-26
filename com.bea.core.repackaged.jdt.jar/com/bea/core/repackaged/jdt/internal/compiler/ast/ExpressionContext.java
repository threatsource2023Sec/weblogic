package com.bea.core.repackaged.jdt.internal.compiler.ast;

public enum ExpressionContext {
   ASSIGNMENT_CONTEXT {
      public String toString() {
         return "assignment context";
      }

      public boolean definesTargetType() {
         return true;
      }
   },
   INVOCATION_CONTEXT {
      public String toString() {
         return "invocation context";
      }

      public boolean definesTargetType() {
         return true;
      }
   },
   CASTING_CONTEXT {
      public String toString() {
         return "casting context";
      }

      public boolean definesTargetType() {
         return false;
      }
   },
   VANILLA_CONTEXT {
      public String toString() {
         return "vanilla context";
      }

      public boolean definesTargetType() {
         return false;
      }
   };

   private ExpressionContext() {
   }

   public abstract boolean definesTargetType();

   // $FF: synthetic method
   ExpressionContext(ExpressionContext var3) {
      this();
   }
}
