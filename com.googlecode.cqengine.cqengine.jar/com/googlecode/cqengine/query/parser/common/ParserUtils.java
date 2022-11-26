package com.googlecode.cqengine.query.parser.common;

import org.antlr.v4.runtime.ParserRuleContext;

public class ParserUtils {
   public static ParserRuleContext getParentContextOfType(ParserRuleContext currentContext, Class... parentContextTypes) {
      label23:
      while(true) {
         if (currentContext != null) {
            currentContext = currentContext.getParent();
            if (currentContext == null) {
               continue;
            }

            Class[] var2 = parentContextTypes;
            int var3 = parentContextTypes.length;
            int var4 = 0;

            while(true) {
               if (var4 >= var3) {
                  continue label23;
               }

               Class parentContextType = var2[var4];
               if (parentContextType.isAssignableFrom(currentContext.getClass())) {
                  return currentContext;
               }

               ++var4;
            }
         }

         return null;
      }
   }

   public static void validateObjectTypeParameter(Class expectedType, String actualType) {
      if (!expectedType.getSimpleName().equals(actualType)) {
         throw new IllegalStateException("Unexpected object type parameter, expected: " + expectedType.getSimpleName() + ", found: " + actualType);
      }
   }

   public static void validateExpectedNumberOfChildQueries(int expected, int actual) {
      if (actual != expected) {
         throw new IllegalStateException("Unexpected number of child queries, expected: " + expected + ", actual: " + actual);
      }
   }

   public static void validateAllQueriesParsed(int numQueriesEncountered, int numQueriesParsed) {
      if (numQueriesEncountered != numQueriesParsed) {
         throw new IllegalStateException("A query declared in the antlr grammar, was not parsed by the listener. If a new query is added in the grammar, a corresponding handler must also be added in the listener.");
      }
   }
}
