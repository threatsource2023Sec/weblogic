package com.bea.core.repackaged.jdt.internal.compiler;

public class DefaultErrorHandlingPolicies {
   public static IErrorHandlingPolicy exitAfterAllProblems() {
      return new IErrorHandlingPolicy() {
         public boolean stopOnFirstError() {
            return false;
         }

         public boolean proceedOnErrors() {
            return false;
         }

         public boolean ignoreAllErrors() {
            return false;
         }
      };
   }

   public static IErrorHandlingPolicy exitOnFirstError() {
      return new IErrorHandlingPolicy() {
         public boolean stopOnFirstError() {
            return true;
         }

         public boolean proceedOnErrors() {
            return false;
         }

         public boolean ignoreAllErrors() {
            return false;
         }
      };
   }

   public static IErrorHandlingPolicy proceedOnFirstError() {
      return new IErrorHandlingPolicy() {
         public boolean stopOnFirstError() {
            return true;
         }

         public boolean proceedOnErrors() {
            return true;
         }

         public boolean ignoreAllErrors() {
            return false;
         }
      };
   }

   public static IErrorHandlingPolicy proceedWithAllProblems() {
      return new IErrorHandlingPolicy() {
         public boolean stopOnFirstError() {
            return false;
         }

         public boolean proceedOnErrors() {
            return true;
         }

         public boolean ignoreAllErrors() {
            return false;
         }
      };
   }

   public static IErrorHandlingPolicy ignoreAllProblems() {
      return new IErrorHandlingPolicy() {
         public boolean stopOnFirstError() {
            return false;
         }

         public boolean proceedOnErrors() {
            return true;
         }

         public boolean ignoreAllErrors() {
            return true;
         }
      };
   }
}
