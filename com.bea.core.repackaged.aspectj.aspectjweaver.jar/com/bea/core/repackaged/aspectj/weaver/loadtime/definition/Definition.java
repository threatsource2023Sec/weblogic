package com.bea.core.repackaged.aspectj.weaver.loadtime.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Definition {
   private final StringBuffer weaverOptions = new StringBuffer();
   private final List dumpPatterns = new ArrayList();
   private boolean dumpBefore = false;
   private boolean perClassloaderDumpDir = false;
   private final List includePatterns = new ArrayList();
   private final List excludePatterns = new ArrayList();
   private final List aspectClassNames = new ArrayList();
   private final List aspectExcludePatterns = new ArrayList();
   private final List aspectIncludePatterns = new ArrayList();
   private final List concreteAspects = new ArrayList();
   private final Map scopedAspects = new HashMap();
   private final Map requiredTypesForAspects = new HashMap();

   public String getWeaverOptions() {
      return this.weaverOptions.toString();
   }

   public List getDumpPatterns() {
      return this.dumpPatterns;
   }

   public void setDumpBefore(boolean b) {
      this.dumpBefore = b;
   }

   public boolean shouldDumpBefore() {
      return this.dumpBefore;
   }

   public void setCreateDumpDirPerClassloader(boolean b) {
      this.perClassloaderDumpDir = b;
   }

   public boolean createDumpDirPerClassloader() {
      return this.perClassloaderDumpDir;
   }

   public List getIncludePatterns() {
      return this.includePatterns;
   }

   public List getExcludePatterns() {
      return this.excludePatterns;
   }

   public List getAspectClassNames() {
      return this.aspectClassNames;
   }

   public List getAspectExcludePatterns() {
      return this.aspectExcludePatterns;
   }

   public List getAspectIncludePatterns() {
      return this.aspectIncludePatterns;
   }

   public List getConcreteAspects() {
      return this.concreteAspects;
   }

   public void appendWeaverOptions(String option) {
      this.weaverOptions.append(option.trim()).append(' ');
   }

   public void addScopedAspect(String name, String scopePattern) {
      this.scopedAspects.put(name, scopePattern);
   }

   public String getScopeForAspect(String name) {
      return (String)this.scopedAspects.get(name);
   }

   public void setAspectRequires(String name, String requiredType) {
      this.requiredTypesForAspects.put(name, requiredType);
   }

   public String getAspectRequires(String name) {
      return (String)this.requiredTypesForAspects.get(name);
   }

   public static class DeclareErrorOrWarning {
      public final boolean isError;
      public final String pointcut;
      public final String message;

      public DeclareErrorOrWarning(boolean isError, String pointcut, String message) {
         this.isError = isError;
         this.pointcut = pointcut;
         this.message = message;
      }
   }

   public static class PointcutAndAdvice {
      public final AdviceKind adviceKind;
      public final String pointcut;
      public final String adviceClass;
      public final String adviceMethod;

      public PointcutAndAdvice(AdviceKind adviceKind, String pointcut, String adviceClass, String adviceMethod) {
         this.adviceKind = adviceKind;
         this.pointcut = pointcut;
         this.adviceClass = adviceClass;
         this.adviceMethod = adviceMethod;
      }
   }

   public static class DeclareAnnotation {
      public final DeclareAnnotationKind declareAnnotationKind;
      public final String pattern;
      public final String annotation;

      public DeclareAnnotation(DeclareAnnotationKind kind, String pattern, String annotation) {
         this.declareAnnotationKind = kind;
         this.pattern = pattern;
         this.annotation = annotation;
      }
   }

   public static enum DeclareAnnotationKind {
      Method,
      Field,
      Type;
   }

   public static enum AdviceKind {
      Before,
      After,
      AfterReturning,
      AfterThrowing,
      Around;
   }

   public static class Pointcut {
      public final String name;
      public final String expression;

      public Pointcut(String name, String expression) {
         this.name = name;
         this.expression = expression;
      }
   }

   public static class ConcreteAspect {
      public final String name;
      public final String extend;
      public final String precedence;
      public final List pointcuts;
      public final List declareAnnotations;
      public final List pointcutsAndAdvice;
      public final String perclause;
      public List deows;

      public ConcreteAspect(String name, String extend) {
         this(name, extend, (String)null, (String)null);
      }

      public ConcreteAspect(String name, String extend, String precedence, String perclause) {
         this.name = name;
         if (extend != null && extend.length() != 0) {
            this.extend = extend;
         } else {
            this.extend = null;
            if (precedence != null && precedence.length() == 0) {
            }
         }

         this.precedence = precedence;
         this.pointcuts = new ArrayList();
         this.declareAnnotations = new ArrayList();
         this.pointcutsAndAdvice = new ArrayList();
         this.deows = new ArrayList();
         this.perclause = perclause;
      }
   }
}
