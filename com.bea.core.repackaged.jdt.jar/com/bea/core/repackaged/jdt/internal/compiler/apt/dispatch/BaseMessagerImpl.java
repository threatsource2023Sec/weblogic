package com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch;

import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.apt.model.AnnotationMemberValue;
import com.bea.core.repackaged.jdt.internal.compiler.apt.model.AnnotationMirrorImpl;
import com.bea.core.repackaged.jdt.internal.compiler.apt.model.ExecutableElementImpl;
import com.bea.core.repackaged.jdt.internal.compiler.apt.model.TypeElementImpl;
import com.bea.core.repackaged.jdt.internal.compiler.apt.model.VariableElementImpl;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Annotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ArrayInitializer;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Expression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FieldDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LocalDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.MemberValuePair;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.impl.ReferenceContext;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AptSourceLocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;

public class BaseMessagerImpl {
   static final String[] NO_ARGUMENTS = new String[0];
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$javax$tools$Diagnostic$Kind;

   public static AptProblem createProblem(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a, AnnotationValue v) {
      ReferenceContext referenceContext = null;
      Annotation[] elementAnnotations = null;
      int startPosition = 0;
      int endPosition = 0;
      AbstractMethodDeclaration lineEnds;
      if (e != null) {
         Binding binding;
         switch (e.getKind()) {
            case PACKAGE:
            case ENUM_CONSTANT:
            case LOCAL_VARIABLE:
            case EXCEPTION_PARAMETER:
            case STATIC_INIT:
            case INSTANCE_INIT:
            case TYPE_PARAMETER:
            default:
               break;
            case ENUM:
            case CLASS:
            case ANNOTATION_TYPE:
            case INTERFACE:
               TypeElementImpl typeElementImpl = (TypeElementImpl)e;
               Binding typeBinding = typeElementImpl._binding;
               if (typeBinding instanceof SourceTypeBinding) {
                  SourceTypeBinding sourceTypeBinding = (SourceTypeBinding)typeBinding;
                  TypeDeclaration typeDeclaration = (TypeDeclaration)sourceTypeBinding.scope.referenceContext();
                  referenceContext = typeDeclaration;
                  elementAnnotations = typeDeclaration.annotations;
                  startPosition = typeDeclaration.sourceStart;
                  endPosition = typeDeclaration.sourceEnd;
               }
               break;
            case FIELD:
            case PARAMETER:
               VariableElementImpl variableElementImpl = (VariableElementImpl)e;
               binding = variableElementImpl._binding;
               if (binding instanceof FieldBinding) {
                  FieldBinding fieldBinding = (FieldBinding)binding;
                  FieldDeclaration fieldDeclaration = fieldBinding.sourceField();
                  if (fieldDeclaration != null) {
                     ReferenceBinding declaringClass = fieldBinding.declaringClass;
                     if (declaringClass instanceof SourceTypeBinding) {
                        SourceTypeBinding sourceTypeBinding = (SourceTypeBinding)declaringClass;
                        TypeDeclaration typeDeclaration = (TypeDeclaration)sourceTypeBinding.scope.referenceContext();
                        referenceContext = typeDeclaration;
                     }

                     elementAnnotations = fieldDeclaration.annotations;
                     startPosition = fieldDeclaration.sourceStart;
                     endPosition = fieldDeclaration.sourceEnd;
                  }
               } else if (binding instanceof AptSourceLocalVariableBinding) {
                  AptSourceLocalVariableBinding parameterBinding = (AptSourceLocalVariableBinding)binding;
                  LocalDeclaration parameterDeclaration = parameterBinding.declaration;
                  if (parameterDeclaration != null) {
                     MethodBinding methodBinding = parameterBinding.methodBinding;
                     if (methodBinding != null) {
                        referenceContext = methodBinding.sourceMethod();
                     }

                     elementAnnotations = parameterDeclaration.annotations;
                     startPosition = parameterDeclaration.sourceStart;
                     endPosition = parameterDeclaration.sourceEnd;
                  }
               }
               break;
            case METHOD:
            case CONSTRUCTOR:
               ExecutableElementImpl executableElementImpl = (ExecutableElementImpl)e;
               binding = executableElementImpl._binding;
               if (binding instanceof MethodBinding) {
                  MethodBinding methodBinding = (MethodBinding)binding;
                  lineEnds = methodBinding.sourceMethod();
                  if (lineEnds != null) {
                     referenceContext = lineEnds;
                     elementAnnotations = lineEnds.annotations;
                     startPosition = lineEnds.sourceStart;
                     endPosition = lineEnds.sourceEnd;
                  }
               }
         }
      }

      StringBuilder builder = new StringBuilder();
      if (msg != null) {
         builder.append(msg);
      }

      if (a != null && elementAnnotations != null) {
         AnnotationBinding annotationBinding = ((AnnotationMirrorImpl)a)._binding;
         Annotation annotation = findAnnotation(elementAnnotations, annotationBinding);
         if (annotation != null) {
            startPosition = annotation.sourceStart;
            endPosition = annotation.sourceEnd;
            if (v != null && v instanceof AnnotationMemberValue) {
               MethodBinding methodBinding = ((AnnotationMemberValue)v).getMethodBinding();
               MemberValuePair[] memberValuePairs = annotation.memberValuePairs();
               MemberValuePair memberValuePair = null;

               for(int i = 0; memberValuePair == null && i < memberValuePairs.length; ++i) {
                  if (methodBinding == memberValuePairs[i].binding) {
                     memberValuePair = memberValuePairs[i];
                  }
               }

               if (memberValuePair != null) {
                  startPosition = memberValuePair.sourceStart;
                  endPosition = memberValuePair.sourceEnd;
               }
            }
         }
      }

      int lineNumber = 0;
      int columnNumber = 1;
      char[] fileName = null;
      if (referenceContext != null) {
         CompilationResult result = ((ReferenceContext)referenceContext).compilationResult();
         fileName = result.fileName;
         lineEnds = null;
         int[] lineEnds;
         lineNumber = startPosition >= 0 ? Util.getLineNumber(startPosition, lineEnds = result.getLineSeparatorPositions(), 0, lineEnds.length - 1) : 0;
         columnNumber = startPosition >= 0 ? Util.searchColumnNumber(result.getLineSeparatorPositions(), lineNumber, startPosition) : 0;
      }

      byte severity;
      switch (kind) {
         case ERROR:
            severity = 1;
            break;
         default:
            severity = 0;
      }

      return new AptProblem((ReferenceContext)referenceContext, fileName, String.valueOf(builder), 0, NO_ARGUMENTS, severity, startPosition, endPosition, lineNumber, columnNumber);
   }

   private static Annotation findAnnotation(Annotation[] elementAnnotations, AnnotationBinding annotationBinding) {
      for(int i = 0; i < elementAnnotations.length; ++i) {
         Annotation annotation = findAnnotation(elementAnnotations[i], annotationBinding);
         if (annotation != null) {
            return annotation;
         }
      }

      return null;
   }

   private static Annotation findAnnotation(Annotation elementAnnotation, AnnotationBinding annotationBinding) {
      if (annotationBinding == elementAnnotation.getCompilerAnnotation()) {
         return elementAnnotation;
      } else {
         MemberValuePair[] memberValuePairs = elementAnnotation.memberValuePairs();
         MemberValuePair[] var6 = memberValuePairs;
         int var5 = memberValuePairs.length;

         for(int var4 = 0; var4 < var5; ++var4) {
            MemberValuePair mvp = var6[var4];
            Expression v = mvp.value;
            if (v instanceof Annotation) {
               Annotation a = findAnnotation((Annotation)v, annotationBinding);
               if (a != null) {
                  return a;
               }
            } else if (v instanceof ArrayInitializer) {
               Expression[] expressions = ((ArrayInitializer)v).expressions;
               Expression[] var12 = expressions;
               int var11 = expressions.length;

               for(int var10 = 0; var10 < var11; ++var10) {
                  Expression e = var12[var10];
                  if (e instanceof Annotation) {
                     Annotation a = findAnnotation((Annotation)e, annotationBinding);
                     if (a != null) {
                        return a;
                     }
                  }
               }
            }
         }

         return null;
      }
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind() {
      int[] var10000 = $SWITCH_TABLE$javax$lang$model$element$ElementKind;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[ElementKind.values().length];

         try {
            var0[ElementKind.ANNOTATION_TYPE.ordinal()] = 4;
         } catch (NoSuchFieldError var18) {
         }

         try {
            var0[ElementKind.CLASS.ordinal()] = 3;
         } catch (NoSuchFieldError var17) {
         }

         try {
            var0[ElementKind.CONSTRUCTOR.ordinal()] = 12;
         } catch (NoSuchFieldError var16) {
         }

         try {
            var0[ElementKind.ENUM.ordinal()] = 2;
         } catch (NoSuchFieldError var15) {
         }

         try {
            var0[ElementKind.ENUM_CONSTANT.ordinal()] = 6;
         } catch (NoSuchFieldError var14) {
         }

         try {
            var0[ElementKind.EXCEPTION_PARAMETER.ordinal()] = 10;
         } catch (NoSuchFieldError var13) {
         }

         try {
            var0[ElementKind.FIELD.ordinal()] = 7;
         } catch (NoSuchFieldError var12) {
         }

         try {
            var0[ElementKind.INSTANCE_INIT.ordinal()] = 14;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[ElementKind.INTERFACE.ordinal()] = 5;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[ElementKind.LOCAL_VARIABLE.ordinal()] = 9;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[ElementKind.METHOD.ordinal()] = 11;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[ElementKind.MODULE.ordinal()] = 18;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[ElementKind.OTHER.ordinal()] = 16;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[ElementKind.PACKAGE.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[ElementKind.PARAMETER.ordinal()] = 8;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[ElementKind.RESOURCE_VARIABLE.ordinal()] = 17;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[ElementKind.STATIC_INIT.ordinal()] = 13;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[ElementKind.TYPE_PARAMETER.ordinal()] = 15;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$javax$lang$model$element$ElementKind = var0;
         return var0;
      }
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$javax$tools$Diagnostic$Kind() {
      int[] var10000 = $SWITCH_TABLE$javax$tools$Diagnostic$Kind;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[Kind.values().length];

         try {
            var0[Kind.ERROR.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[Kind.MANDATORY_WARNING.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[Kind.NOTE.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[Kind.OTHER.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[Kind.WARNING.ordinal()] = 2;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$javax$tools$Diagnostic$Kind = var0;
         return var0;
      }
   }
}
