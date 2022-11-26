package com.sun.faces.util.copier;

import com.sun.faces.util.ReflectionUtils;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.faces.context.FacesContext;

public class CopierUtils {
   private static final String ERROR_COPIER_NAME = "The copier name should be a Java valid simple/qualified name.";
   private static final String COPIER_PREFIX = "com.sun.faces.util.copier.";
   private static final Set keywords;

   public static Copier getCopier(FacesContext context, String copierType) {
      Copier copier = null;
      if (!isEmpty(copierType)) {
         if (isCopierTypeSimpleName(copierType)) {
            copierType = "com.sun.faces.util.copier.".concat(copierType);
         } else if (!isName(copierType)) {
            throw new IllegalArgumentException("The copier name should be a Java valid simple/qualified name.");
         }

         Object expressionResult = evaluateExpressionGet(context, copierType);
         if (expressionResult instanceof Copier) {
            copier = (Copier)expressionResult;
         } else if (expressionResult instanceof String) {
            copier = (Copier)ReflectionUtils.instance((String)expressionResult);
         }
      }

      if (copier == null) {
         copier = new MultiStrategyCopier();
      }

      return (Copier)copier;
   }

   private static Object evaluateExpressionGet(FacesContext context, String expression) {
      return expression == null ? null : context.getApplication().evaluateExpressionGet(context, expression, Object.class);
   }

   private static boolean isCopierTypeSimpleName(String copierType) {
      return isIdentifier(copierType) && !isKeyword(copierType);
   }

   private static boolean isEmpty(String string) {
      return string == null || string.isEmpty();
   }

   private static boolean isName(CharSequence name) {
      String id = name.toString();
      String[] var2 = id.split("\\.", -1);
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String s = var2[var4];
         if (!isIdentifier(s) || isKeyword(s)) {
            return false;
         }
      }

      return true;
   }

   private static boolean isIdentifier(CharSequence name) {
      String id = name.toString();
      if (id.length() == 0) {
         return false;
      } else {
         int cp = id.codePointAt(0);
         if (!Character.isJavaIdentifierStart(cp)) {
            return false;
         } else {
            for(int i = Character.charCount(cp); i < id.length(); i += Character.charCount(cp)) {
               cp = id.codePointAt(i);
               if (!Character.isJavaIdentifierPart(cp)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private static boolean isKeyword(CharSequence s) {
      String keywordOrLiteral = s.toString();
      return keywords.contains(keywordOrLiteral);
   }

   static {
      Set s = new HashSet();
      String[] kws = new String[]{"abstract", "continue", "for", "new", "switch", "assert", "default", "if", "package", "synchronized", "boolean", "do", "goto", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while", "null", "true", "false"};
      String[] var2 = kws;
      int var3 = kws.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String kw = var2[var4];
         s.add(kw);
      }

      keywords = Collections.unmodifiableSet(s);
   }
}
