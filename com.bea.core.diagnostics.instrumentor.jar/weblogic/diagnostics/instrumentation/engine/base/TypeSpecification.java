package weblogic.diagnostics.instrumentation.engine.base;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.InstrumentationDebug;
import weblogic.diagnostics.instrumentation.InvalidPointcutException;

public class TypeSpecification implements Serializable {
   private static final long serialVersionUID = 6761092190535458621L;
   static final int TYPE_ANY = 1;
   static final int TYPE_ELIPSES = 2;
   static final int TYPE_REGEX = 3;
   private int type;
   private boolean allowInheritance;
   private boolean checkAnnotations;
   private Pattern typePattern;
   private String patternString;
   private boolean isPrimitive;
   private int dimension;
   private boolean isSensitive = true;
   private boolean isGatherable = false;
   private String argumentName = null;
   private String valueRendererClassName = null;

   TypeSpecification() {
   }

   boolean isElipses() {
      return this.type == 2;
   }

   boolean hasMatch(ClassInstrumentor classInstrumentor, String className) {
      if (this.checkAnnotations) {
         return this.hasMatchingAnnotation(classInstrumentor.getClassInfo().getClassAnnotations());
      } else if (this.type != 1 && this.type != 2) {
         int len = className.length();
         int dim = 0;

         for(int i = 0; i < len && className.charAt(i) == '['; ++i) {
            ++dim;
         }

         if (dim != this.dimension) {
            return false;
         } else {
            if (dim > 0) {
               className = className.substring(dim, len);
            }

            if (className.charAt(0) == 'L') {
               if (this.isPrimitive) {
                  return false;
               }

               className = className.substring(1, className.length() - 1);
            } else if (!this.isPrimitive) {
               return false;
            }

            if (this.typePattern.matcher(className).matches()) {
               return true;
            } else {
               return this.allowInheritance && classInstrumentor != null ? this.hasInheritedMatch(classInstrumentor, className) : false;
            }
         }
      } else {
         return true;
      }
   }

   private boolean hasInheritedMatch(ClassInstrumentor classInstrumentor, String className) {
      if (className == null) {
         return false;
      } else {
         if (InstrumentationDebug.DEBUG_CLASSINFO.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_CLASSINFO.debug("Inherited matching " + className + " with " + this.patternString);
         }

         ClassInfo classInfo = classInstrumentor.getClassInfo(className);
         if (classInfo != null && classInfo.isValid()) {
            ClassInfo.MatchEntry entry = classInfo.getMatchEntry(this.patternString);
            if (entry != null) {
               return entry.matched;
            } else {
               String[] interfaces = classInfo.getInterfaceNames();
               if (classInfo.isInterface()) {
                  if (this.hasInterfaceMatch(classInstrumentor, interfaces)) {
                     return this.enterMatch(classInfo, true);
                  }
               } else {
                  String superClassName = classInfo.getSuperClassName();
                  if (superClassName != null && this.typePattern.matcher(superClassName).matches()) {
                     return this.enterMatch(classInfo, true);
                  }

                  if (this.hasInterfaceMatch(classInstrumentor, interfaces)) {
                     return this.enterMatch(classInfo, true);
                  }

                  if (superClassName != null && this.hasInheritedMatch(classInstrumentor, superClassName)) {
                     return this.enterMatch(classInfo, true);
                  }
               }

               return this.enterMatch(classInfo, false);
            }
         } else {
            if (InstrumentationDebug.DEBUG_CLASSINFO.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_CLASSINFO.debug("Inherited matching " + className + " with " + this.patternString + " is not completely possible due to null or invalid classinfo for " + className + ", verify all dependencies are available");
            }

            return false;
         }
      }
   }

   private boolean enterMatch(ClassInfo classInfo, boolean status) {
      classInfo.enterMatch(this.patternString, status);
      return status;
   }

   private boolean hasInterfaceMatch(ClassInstrumentor classInstrumentor, String[] interfaces) {
      int interfaceCnt = interfaces != null ? interfaces.length : 0;

      int i;
      for(i = 0; i < interfaceCnt; ++i) {
         if (this.typePattern.matcher(interfaces[i]).matches()) {
            return true;
         }
      }

      for(i = 0; i < interfaceCnt; ++i) {
         if (this.hasInheritedMatch(classInstrumentor, interfaces[i])) {
            return true;
         }
      }

      return false;
   }

   private boolean hasMatchingAnnotation(List annotations) {
      if (this.typePattern != null && annotations != null) {
         Iterator it = annotations.iterator();

         String annoType;
         do {
            if (!it.hasNext()) {
               return false;
            }

            annoType = (String)it.next();
            annoType = annoType.substring(1, annoType.length() - 1);
         } while(!this.typePattern.matcher(annoType).matches());

         return true;
      } else {
         return false;
      }
   }

   boolean isInheritanceAllowed() {
      return this.allowInheritance;
   }

   void setInheritanceAllowed(boolean allowed) {
      this.allowInheritance = allowed;
   }

   int getType() {
      return this.type;
   }

   void setType(int type) {
      switch (type) {
         case 1:
         case 2:
         case 3:
            this.type = type;
         default:
      }
   }

   void setPattern(String pattern, boolean checkAnnotations) throws InvalidPointcutException {
      try {
         this.patternString = pattern;
         this.typePattern = Pattern.compile(this.patternString);
         this.checkAnnotations = checkAnnotations;
      } catch (PatternSyntaxException var4) {
         throw new InvalidPointcutException("Invalid type pattern " + this.patternString);
      }
   }

   void setPattern(String pattern) throws InvalidPointcutException {
      this.setPattern(pattern, false);
   }

   int getDimension() {
      return this.dimension;
   }

   void setDimension(int dimension) {
      this.dimension = dimension;
   }

   void setPrimitive(boolean primitive) {
      this.isPrimitive = primitive;
   }

   void setSensitive(boolean sensitive) throws InvalidPointcutException {
      if (sensitive || this.type != 1 && this.type != 2 && (this.patternString == null || !this.patternString.contains("*"))) {
         this.isSensitive = sensitive;
      } else {
         throw new InvalidPointcutException(DiagnosticsLogger.getWildcardedTypesNotAllowedToBeInSensitive());
      }
   }

   boolean isSensitive() {
      return this.isSensitive;
   }

   void setGatherable(boolean gatherable) {
      this.isGatherable = gatherable;
      if (this.isSensitive) {
         gatherable = false;
      }

   }

   boolean isGatherable() {
      return this.isGatherable;
   }

   void setArgumentName(String argumentName) {
      this.argumentName = argumentName;
   }

   String getArgumentName() {
      return this.argumentName;
   }

   void setValueRendererClassName(String valueRendererClassName) {
      this.valueRendererClassName = valueRendererClassName;
   }

   String getValueRendererClassName() {
      return this.valueRendererClassName;
   }

   public String toString() {
      String retVal = "TypeSelector{type=" + this.type + ", inherit=" + this.allowInheritance + ", primitive=" + this.isPrimitive + ", pattern=" + this.patternString + ", dimension=" + this.dimension + ", isSensitive=" + this.isSensitive + ", isGatherable=" + this.isGatherable + ", argumentName=" + this.argumentName + ", valueRendererClassName=" + this.valueRendererClassName + "}";
      return retVal;
   }
}
