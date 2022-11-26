package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.ast.Var;
import java.lang.reflect.Member;

public final class ReflectionVar extends Var {
   static final int THIS_VAR = 0;
   static final int TARGET_VAR = 1;
   static final int ARGS_VAR = 2;
   static final int AT_THIS_VAR = 3;
   static final int AT_TARGET_VAR = 4;
   static final int AT_ARGS_VAR = 5;
   static final int AT_WITHIN_VAR = 6;
   static final int AT_WITHINCODE_VAR = 7;
   static final int AT_ANNOTATION_VAR = 8;
   private AnnotationFinder annotationFinder = null;
   private int argsIndex = 0;
   private int varType;

   public static ReflectionVar createThisVar(ResolvedType type, AnnotationFinder finder) {
      ReflectionVar ret = new ReflectionVar(type, finder);
      ret.varType = 0;
      return ret;
   }

   public static ReflectionVar createTargetVar(ResolvedType type, AnnotationFinder finder) {
      ReflectionVar ret = new ReflectionVar(type, finder);
      ret.varType = 1;
      return ret;
   }

   public static ReflectionVar createArgsVar(ResolvedType type, int index, AnnotationFinder finder) {
      ReflectionVar ret = new ReflectionVar(type, finder);
      ret.varType = 2;
      ret.argsIndex = index;
      return ret;
   }

   public static ReflectionVar createThisAnnotationVar(ResolvedType type, AnnotationFinder finder) {
      ReflectionVar ret = new ReflectionVar(type, finder);
      ret.varType = 3;
      return ret;
   }

   public static ReflectionVar createTargetAnnotationVar(ResolvedType type, AnnotationFinder finder) {
      ReflectionVar ret = new ReflectionVar(type, finder);
      ret.varType = 4;
      return ret;
   }

   public static ReflectionVar createArgsAnnotationVar(ResolvedType type, int index, AnnotationFinder finder) {
      ReflectionVar ret = new ReflectionVar(type, finder);
      ret.varType = 5;
      ret.argsIndex = index;
      return ret;
   }

   public static ReflectionVar createWithinAnnotationVar(ResolvedType annType, AnnotationFinder finder) {
      ReflectionVar ret = new ReflectionVar(annType, finder);
      ret.varType = 6;
      return ret;
   }

   public static ReflectionVar createWithinCodeAnnotationVar(ResolvedType annType, AnnotationFinder finder) {
      ReflectionVar ret = new ReflectionVar(annType, finder);
      ret.varType = 7;
      return ret;
   }

   public static ReflectionVar createAtAnnotationVar(ResolvedType annType, AnnotationFinder finder) {
      ReflectionVar ret = new ReflectionVar(annType, finder);
      ret.varType = 8;
      return ret;
   }

   private ReflectionVar(ResolvedType type, AnnotationFinder finder) {
      super(type);
      this.annotationFinder = finder;
   }

   public Object getBindingAtJoinPoint(Object thisObject, Object targetObject, Object[] args) {
      return this.getBindingAtJoinPoint(thisObject, targetObject, args, (Member)null, (Member)null, (Class)null);
   }

   public Object getBindingAtJoinPoint(Object thisObject, Object targetObject, Object[] args, Member subject, Member withinCode, Class withinType) {
      switch (this.varType) {
         case 0:
            return thisObject;
         case 1:
            return targetObject;
         case 2:
            if (this.argsIndex > args.length - 1) {
               return null;
            }

            return args[this.argsIndex];
         case 3:
            if (this.annotationFinder != null) {
               return this.annotationFinder.getAnnotation(this.getType(), thisObject);
            }

            return null;
         case 4:
            if (this.annotationFinder != null) {
               return this.annotationFinder.getAnnotation(this.getType(), targetObject);
            }

            return null;
         case 5:
            if (this.argsIndex > args.length - 1) {
               return null;
            } else {
               if (this.annotationFinder != null) {
                  return this.annotationFinder.getAnnotation(this.getType(), args[this.argsIndex]);
               }

               return null;
            }
         case 6:
            if (this.annotationFinder != null) {
               return this.annotationFinder.getAnnotationFromClass(this.getType(), withinType);
            }

            return null;
         case 7:
            if (this.annotationFinder != null) {
               return this.annotationFinder.getAnnotationFromMember(this.getType(), withinCode);
            }

            return null;
         case 8:
            if (this.annotationFinder != null) {
               return this.annotationFinder.getAnnotationFromMember(this.getType(), subject);
            }

            return null;
         default:
            return null;
      }
   }
}
