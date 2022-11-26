package org.glassfish.soteria.cdi;

import java.lang.reflect.Array;
import java.util.Arrays;
import javax.el.ELProcessor;
import org.glassfish.soteria.Utils;

public class AnnotationELPProcessor {
   public static String evalImmediate(String expression) {
      return evalImmediate((ELProcessor)null, expression);
   }

   public static String evalImmediate(ELProcessor getELProcessor, String expression) {
      return isELExpression(expression) && !isDeferredExpression(expression) ? (String)CdiUtils.getELProcessor(getELProcessor).eval(toRawExpression(expression)) : expression;
   }

   public static boolean evalImmediate(String expression, boolean defaultValue) {
      return evalImmediate((ELProcessor)null, expression, defaultValue);
   }

   public static boolean evalImmediate(ELProcessor getELProcessor, String expression, boolean defaultValue) {
      if (isELExpression(expression) && !isDeferredExpression(expression)) {
         Object outcome = CdiUtils.getELProcessor(getELProcessor).eval(toRawExpression(expression));
         if (outcome instanceof Boolean) {
            return (Boolean)outcome;
         } else {
            throw new IllegalStateException("Expression " + expression + " should evaluate to boolean but evaluated to " + outcome == null ? " null" : outcome.getClass() + " " + outcome);
         }
      } else {
         return defaultValue;
      }
   }

   public static int evalImmediate(String expression, int defaultValue) {
      return evalImmediate((ELProcessor)null, expression, defaultValue);
   }

   public static int evalImmediate(ELProcessor getELProcessor, String expression, int defaultValue) {
      return isELExpression(expression) && !isDeferredExpression(expression) ? (Integer)CdiUtils.getELProcessor(getELProcessor).getValue(toRawExpression(expression), Integer.class) : defaultValue;
   }

   public static Object evalImmediate(String expression, Object defaultValue) {
      return isELExpression(expression) && !isDeferredExpression(expression) ? CdiUtils.getELProcessor(CdiUtils.getELProcessor((ELProcessor)null)).eval(toRawExpression(expression)) : defaultValue;
   }

   public static String emptyIfImmediate(String expression) {
      return isImmediateExpression(expression) ? "" : expression;
   }

   public static String evalELExpression(String expression) {
      return evalELExpression((ELProcessor)null, expression);
   }

   public static String evalELExpression(ELProcessor getELProcessor, String expression) {
      return !isELExpression(expression) ? expression : (String)CdiUtils.getELProcessor(getELProcessor).eval(toRawExpression(expression));
   }

   public static boolean evalELExpression(String expression, boolean defaultValue) {
      return evalELExpression((ELProcessor)null, expression, defaultValue);
   }

   public static boolean evalELExpression(ELProcessor getELProcessor, String expression, boolean defaultValue) {
      return !isELExpression(expression) ? defaultValue : (Boolean)CdiUtils.getELProcessor(getELProcessor).eval(toRawExpression(expression));
   }

   public static Object evalELExpression(String expression, Object defaultValue) {
      return evalELExpression((ELProcessor)null, expression, defaultValue);
   }

   public static Object evalELExpression(ELProcessor getELProcessor, String expression, Object defaultValue) {
      if (!isELExpression(expression)) {
         return defaultValue;
      } else {
         Object outcome = CdiUtils.getELProcessor(getELProcessor).eval(toRawExpression(expression));
         Enum enumConstant;
         if (defaultValue instanceof Enum && outcome instanceof String) {
            Enum defaultValueEnum = (Enum)defaultValue;
            enumConstant = Enum.valueOf(defaultValueEnum.getClass(), (String)outcome);
            return enumConstant;
         } else if (defaultValue instanceof Enum[] && outcome instanceof String) {
            Enum[] defaultValueEnum = (Enum[])((Enum[])defaultValue);
            enumConstant = Enum.valueOf(defaultValueEnum.getClass().getComponentType(), (String)outcome);
            Enum[] outcomeArray = (Enum[])((Enum[])Array.newInstance(defaultValueEnum.getClass().getComponentType(), 1));
            outcomeArray[0] = enumConstant;
            return outcomeArray;
         } else {
            return outcome;
         }
      }
   }

   public static int evalELExpression(String expression, int defaultValue) {
      return evalELExpression((ELProcessor)null, expression, defaultValue);
   }

   public static int evalELExpression(ELProcessor getELProcessor, String expression, int defaultValue) {
      return !isELExpression(expression) ? defaultValue : (Integer)CdiUtils.getELProcessor(getELProcessor).getValue(toRawExpression(expression), Integer.class);
   }

   @SafeVarargs
   public static boolean hasAnyELExpression(String... expressions) {
      return Arrays.stream(expressions).anyMatch((expr) -> {
         return isELExpression(expr);
      });
   }

   private static boolean isELExpression(String expression) {
      return !Utils.isEmpty(expression) && (isDeferredExpression(expression) || isImmediateExpression(expression));
   }

   private static boolean isDeferredExpression(String expression) {
      return expression.startsWith("#{") && expression.endsWith("}");
   }

   private static boolean isImmediateExpression(String expression) {
      return expression.startsWith("${") && expression.endsWith("}");
   }

   private static String toRawExpression(String expression) {
      return expression.substring(2, expression.length() - 1);
   }
}
