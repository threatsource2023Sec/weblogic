package com.bea.core.repackaged.aspectj.asm.internal;

import com.bea.core.repackaged.aspectj.asm.IProgramElement;

public class HandleProviderDelimiter {
   public static final HandleProviderDelimiter JAVAPROJECT = new HandleProviderDelimiter('=');
   public static final HandleProviderDelimiter PACKAGEFRAGMENT = new HandleProviderDelimiter('<');
   public static final HandleProviderDelimiter FIELD = new HandleProviderDelimiter('^');
   public static final HandleProviderDelimiter METHOD = new HandleProviderDelimiter('~');
   public static final HandleProviderDelimiter INITIALIZER = new HandleProviderDelimiter('|');
   public static final HandleProviderDelimiter COMPILATIONUNIT = new HandleProviderDelimiter('{');
   public static final HandleProviderDelimiter CLASSFILE = new HandleProviderDelimiter('(');
   public static final HandleProviderDelimiter TYPE = new HandleProviderDelimiter('[');
   public static final HandleProviderDelimiter IMPORTDECLARATION = new HandleProviderDelimiter('#');
   public static final HandleProviderDelimiter COUNT = new HandleProviderDelimiter('!');
   public static final HandleProviderDelimiter ESCAPE = new HandleProviderDelimiter('\\');
   public static final HandleProviderDelimiter PACKAGEDECLARATION = new HandleProviderDelimiter('%');
   public static final HandleProviderDelimiter PACKAGEFRAGMENTROOT = new HandleProviderDelimiter('/');
   public static final HandleProviderDelimiter LOCALVARIABLE = new HandleProviderDelimiter('@');
   public static final HandleProviderDelimiter TYPE_PARAMETER = new HandleProviderDelimiter(']');
   public static final HandleProviderDelimiter ASPECT_CU = new HandleProviderDelimiter('*');
   public static final HandleProviderDelimiter ADVICE = new HandleProviderDelimiter('&');
   public static final HandleProviderDelimiter ASPECT_TYPE = new HandleProviderDelimiter('\'');
   public static final HandleProviderDelimiter CODEELEMENT = new HandleProviderDelimiter('?');
   public static final HandleProviderDelimiter ITD_FIELD = new HandleProviderDelimiter(',');
   public static final HandleProviderDelimiter ITD = new HandleProviderDelimiter(')');
   public static final HandleProviderDelimiter DECLARE = new HandleProviderDelimiter('`');
   public static final HandleProviderDelimiter POINTCUT = new HandleProviderDelimiter('"');
   public static final HandleProviderDelimiter PHANTOM = new HandleProviderDelimiter(';');
   private static char empty = ' ';
   private final char delim;

   private HandleProviderDelimiter(char delim) {
      this.delim = delim;
   }

   public char getDelimiter() {
      return this.delim;
   }

   public static char getDelimiter(IProgramElement ipe) {
      IProgramElement.Kind kind = ipe.getKind();
      if (kind.equals(IProgramElement.Kind.PROJECT)) {
         return JAVAPROJECT.getDelimiter();
      } else if (kind.equals(IProgramElement.Kind.PACKAGE)) {
         return PACKAGEFRAGMENT.getDelimiter();
      } else if (kind.equals(IProgramElement.Kind.FILE_JAVA)) {
         return ipe.getName().endsWith(".aj") ? ASPECT_CU.getDelimiter() : COMPILATIONUNIT.getDelimiter();
      } else if (kind.equals(IProgramElement.Kind.FILE_ASPECTJ)) {
         return ASPECT_CU.getDelimiter();
      } else if (kind.equals(IProgramElement.Kind.IMPORT_REFERENCE)) {
         return IMPORTDECLARATION.getDelimiter();
      } else if (kind.equals(IProgramElement.Kind.PACKAGE_DECLARATION)) {
         return PACKAGEDECLARATION.getDelimiter();
      } else if (!kind.equals(IProgramElement.Kind.CLASS) && !kind.equals(IProgramElement.Kind.INTERFACE) && !kind.equals(IProgramElement.Kind.ENUM) && !kind.equals(IProgramElement.Kind.ANNOTATION)) {
         if (kind.equals(IProgramElement.Kind.ASPECT)) {
            return ipe.isAnnotationStyleDeclaration() ? TYPE.getDelimiter() : ASPECT_TYPE.getDelimiter();
         } else if (kind.equals(IProgramElement.Kind.INITIALIZER)) {
            return INITIALIZER.getDelimiter();
         } else if (kind.equals(IProgramElement.Kind.INTER_TYPE_FIELD)) {
            return ITD_FIELD.getDelimiter();
         } else if (!kind.equals(IProgramElement.Kind.INTER_TYPE_METHOD) && !kind.equals(IProgramElement.Kind.INTER_TYPE_CONSTRUCTOR) && !kind.equals(IProgramElement.Kind.INTER_TYPE_PARENT)) {
            if (!kind.equals(IProgramElement.Kind.CONSTRUCTOR) && !kind.equals(IProgramElement.Kind.METHOD)) {
               if (!kind.equals(IProgramElement.Kind.FIELD) && !kind.equals(IProgramElement.Kind.ENUM_VALUE)) {
                  if (kind.equals(IProgramElement.Kind.POINTCUT)) {
                     return ipe.isAnnotationStyleDeclaration() ? METHOD.getDelimiter() : POINTCUT.getDelimiter();
                  } else if (kind.equals(IProgramElement.Kind.ADVICE)) {
                     return ipe.isAnnotationStyleDeclaration() ? METHOD.getDelimiter() : ADVICE.getDelimiter();
                  } else if (!kind.equals(IProgramElement.Kind.DECLARE_PARENTS) && !kind.equals(IProgramElement.Kind.DECLARE_WARNING) && !kind.equals(IProgramElement.Kind.DECLARE_ERROR) && !kind.equals(IProgramElement.Kind.DECLARE_SOFT) && !kind.equals(IProgramElement.Kind.DECLARE_PRECEDENCE) && !kind.equals(IProgramElement.Kind.DECLARE_ANNOTATION_AT_CONSTRUCTOR) && !kind.equals(IProgramElement.Kind.DECLARE_ANNOTATION_AT_FIELD) && !kind.equals(IProgramElement.Kind.DECLARE_ANNOTATION_AT_METHOD) && !kind.equals(IProgramElement.Kind.DECLARE_ANNOTATION_AT_TYPE)) {
                     if (kind.equals(IProgramElement.Kind.CODE)) {
                        return CODEELEMENT.getDelimiter();
                     } else if (kind == IProgramElement.Kind.FILE) {
                        if (ipe.getName().endsWith(".class")) {
                           return CLASSFILE.getDelimiter();
                        } else if (ipe.getName().endsWith(".aj")) {
                           return ASPECT_CU.getDelimiter();
                        } else {
                           return ipe.getName().endsWith(".java") ? COMPILATIONUNIT.getDelimiter() : empty;
                        }
                     } else {
                        return empty;
                     }
                  } else {
                     return DECLARE.getDelimiter();
                  }
               } else {
                  return FIELD.getDelimiter();
               }
            } else {
               return METHOD.getDelimiter();
            }
         } else {
            return ITD.getDelimiter();
         }
      } else {
         return TYPE.getDelimiter();
      }
   }
}
