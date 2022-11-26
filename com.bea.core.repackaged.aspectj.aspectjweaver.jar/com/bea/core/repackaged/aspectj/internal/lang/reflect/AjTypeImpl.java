package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import com.bea.core.repackaged.aspectj.internal.lang.annotation.ajcDeclareEoW;
import com.bea.core.repackaged.aspectj.internal.lang.annotation.ajcDeclareParents;
import com.bea.core.repackaged.aspectj.internal.lang.annotation.ajcDeclarePrecedence;
import com.bea.core.repackaged.aspectj.internal.lang.annotation.ajcDeclareSoft;
import com.bea.core.repackaged.aspectj.internal.lang.annotation.ajcITD;
import com.bea.core.repackaged.aspectj.internal.lang.annotation.ajcPrivileged;
import com.bea.core.repackaged.aspectj.lang.annotation.After;
import com.bea.core.repackaged.aspectj.lang.annotation.AfterReturning;
import com.bea.core.repackaged.aspectj.lang.annotation.AfterThrowing;
import com.bea.core.repackaged.aspectj.lang.annotation.Around;
import com.bea.core.repackaged.aspectj.lang.annotation.Aspect;
import com.bea.core.repackaged.aspectj.lang.annotation.Before;
import com.bea.core.repackaged.aspectj.lang.annotation.DeclareError;
import com.bea.core.repackaged.aspectj.lang.annotation.DeclareParents;
import com.bea.core.repackaged.aspectj.lang.annotation.DeclareWarning;
import com.bea.core.repackaged.aspectj.lang.reflect.Advice;
import com.bea.core.repackaged.aspectj.lang.reflect.AdviceKind;
import com.bea.core.repackaged.aspectj.lang.reflect.AjType;
import com.bea.core.repackaged.aspectj.lang.reflect.AjTypeSystem;
import com.bea.core.repackaged.aspectj.lang.reflect.DeclareAnnotation;
import com.bea.core.repackaged.aspectj.lang.reflect.DeclareErrorOrWarning;
import com.bea.core.repackaged.aspectj.lang.reflect.DeclarePrecedence;
import com.bea.core.repackaged.aspectj.lang.reflect.DeclareSoft;
import com.bea.core.repackaged.aspectj.lang.reflect.InterTypeConstructorDeclaration;
import com.bea.core.repackaged.aspectj.lang.reflect.InterTypeFieldDeclaration;
import com.bea.core.repackaged.aspectj.lang.reflect.InterTypeMethodDeclaration;
import com.bea.core.repackaged.aspectj.lang.reflect.NoSuchAdviceException;
import com.bea.core.repackaged.aspectj.lang.reflect.NoSuchPointcutException;
import com.bea.core.repackaged.aspectj.lang.reflect.PerClause;
import com.bea.core.repackaged.aspectj.lang.reflect.PerClauseKind;
import com.bea.core.repackaged.aspectj.lang.reflect.Pointcut;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class AjTypeImpl implements AjType {
   private static final String ajcMagic = "ajc$";
   private Class clazz;
   private Pointcut[] declaredPointcuts = null;
   private Pointcut[] pointcuts = null;
   private Advice[] declaredAdvice = null;
   private Advice[] advice = null;
   private InterTypeMethodDeclaration[] declaredITDMethods = null;
   private InterTypeMethodDeclaration[] itdMethods = null;
   private InterTypeFieldDeclaration[] declaredITDFields = null;
   private InterTypeFieldDeclaration[] itdFields = null;
   private InterTypeConstructorDeclaration[] itdCons = null;
   private InterTypeConstructorDeclaration[] declaredITDCons = null;

   public AjTypeImpl(Class fromClass) {
      this.clazz = fromClass;
   }

   public String getName() {
      return this.clazz.getName();
   }

   public Package getPackage() {
      return this.clazz.getPackage();
   }

   public AjType[] getInterfaces() {
      Class[] baseInterfaces = this.clazz.getInterfaces();
      return this.toAjTypeArray(baseInterfaces);
   }

   public int getModifiers() {
      return this.clazz.getModifiers();
   }

   public Class getJavaClass() {
      return this.clazz;
   }

   public AjType getSupertype() {
      Class superclass = this.clazz.getSuperclass();
      return superclass == null ? null : new AjTypeImpl(superclass);
   }

   public Type getGenericSupertype() {
      return this.clazz.getGenericSuperclass();
   }

   public Method getEnclosingMethod() {
      return this.clazz.getEnclosingMethod();
   }

   public Constructor getEnclosingConstructor() {
      return this.clazz.getEnclosingConstructor();
   }

   public AjType getEnclosingType() {
      Class enc = this.clazz.getEnclosingClass();
      return enc != null ? new AjTypeImpl(enc) : null;
   }

   public AjType getDeclaringType() {
      Class dec = this.clazz.getDeclaringClass();
      return dec != null ? new AjTypeImpl(dec) : null;
   }

   public PerClause getPerClause() {
      if (this.isAspect()) {
         Aspect aspectAnn = (Aspect)this.clazz.getAnnotation(Aspect.class);
         String perClause = aspectAnn.value();
         if (perClause.equals("")) {
            return (PerClause)(this.getSupertype().isAspect() ? this.getSupertype().getPerClause() : new PerClauseImpl(PerClauseKind.SINGLETON));
         } else if (perClause.startsWith("perthis(")) {
            return new PointcutBasedPerClauseImpl(PerClauseKind.PERTHIS, perClause.substring("perthis(".length(), perClause.length() - 1));
         } else if (perClause.startsWith("pertarget(")) {
            return new PointcutBasedPerClauseImpl(PerClauseKind.PERTARGET, perClause.substring("pertarget(".length(), perClause.length() - 1));
         } else if (perClause.startsWith("percflow(")) {
            return new PointcutBasedPerClauseImpl(PerClauseKind.PERCFLOW, perClause.substring("percflow(".length(), perClause.length() - 1));
         } else if (perClause.startsWith("percflowbelow(")) {
            return new PointcutBasedPerClauseImpl(PerClauseKind.PERCFLOWBELOW, perClause.substring("percflowbelow(".length(), perClause.length() - 1));
         } else if (perClause.startsWith("pertypewithin")) {
            return new TypePatternBasedPerClauseImpl(PerClauseKind.PERTYPEWITHIN, perClause.substring("pertypewithin(".length(), perClause.length() - 1));
         } else {
            throw new IllegalStateException("Per-clause not recognized: " + perClause);
         }
      } else {
         return null;
      }
   }

   public boolean isAnnotationPresent(Class annotationType) {
      return this.clazz.isAnnotationPresent(annotationType);
   }

   public Annotation getAnnotation(Class annotationType) {
      return this.clazz.getAnnotation(annotationType);
   }

   public Annotation[] getAnnotations() {
      return this.clazz.getAnnotations();
   }

   public Annotation[] getDeclaredAnnotations() {
      return this.clazz.getDeclaredAnnotations();
   }

   public AjType[] getAjTypes() {
      Class[] classes = this.clazz.getClasses();
      return this.toAjTypeArray(classes);
   }

   public AjType[] getDeclaredAjTypes() {
      Class[] classes = this.clazz.getDeclaredClasses();
      return this.toAjTypeArray(classes);
   }

   public Constructor getConstructor(AjType... parameterTypes) throws NoSuchMethodException {
      return this.clazz.getConstructor(this.toClassArray(parameterTypes));
   }

   public Constructor[] getConstructors() {
      return this.clazz.getConstructors();
   }

   public Constructor getDeclaredConstructor(AjType... parameterTypes) throws NoSuchMethodException {
      return this.clazz.getDeclaredConstructor(this.toClassArray(parameterTypes));
   }

   public Constructor[] getDeclaredConstructors() {
      return this.clazz.getDeclaredConstructors();
   }

   public Field getDeclaredField(String name) throws NoSuchFieldException {
      Field f = this.clazz.getDeclaredField(name);
      if (f.getName().startsWith("ajc$")) {
         throw new NoSuchFieldException(name);
      } else {
         return f;
      }
   }

   public Field[] getDeclaredFields() {
      Field[] fields = this.clazz.getDeclaredFields();
      List filteredFields = new ArrayList();
      Field[] ret = fields;
      int len$ = fields.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Field field = ret[i$];
         if (!field.getName().startsWith("ajc$") && !field.isAnnotationPresent(DeclareWarning.class) && !field.isAnnotationPresent(DeclareError.class)) {
            filteredFields.add(field);
         }
      }

      ret = new Field[filteredFields.size()];
      filteredFields.toArray(ret);
      return ret;
   }

   public Field getField(String name) throws NoSuchFieldException {
      Field f = this.clazz.getField(name);
      if (f.getName().startsWith("ajc$")) {
         throw new NoSuchFieldException(name);
      } else {
         return f;
      }
   }

   public Field[] getFields() {
      Field[] fields = this.clazz.getFields();
      List filteredFields = new ArrayList();
      Field[] ret = fields;
      int len$ = fields.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Field field = ret[i$];
         if (!field.getName().startsWith("ajc$") && !field.isAnnotationPresent(DeclareWarning.class) && !field.isAnnotationPresent(DeclareError.class)) {
            filteredFields.add(field);
         }
      }

      ret = new Field[filteredFields.size()];
      filteredFields.toArray(ret);
      return ret;
   }

   public Method getDeclaredMethod(String name, AjType... parameterTypes) throws NoSuchMethodException {
      Method m = this.clazz.getDeclaredMethod(name, this.toClassArray(parameterTypes));
      if (!this.isReallyAMethod(m)) {
         throw new NoSuchMethodException(name);
      } else {
         return m;
      }
   }

   public Method getMethod(String name, AjType... parameterTypes) throws NoSuchMethodException {
      Method m = this.clazz.getMethod(name, this.toClassArray(parameterTypes));
      if (!this.isReallyAMethod(m)) {
         throw new NoSuchMethodException(name);
      } else {
         return m;
      }
   }

   public Method[] getDeclaredMethods() {
      Method[] methods = this.clazz.getDeclaredMethods();
      List filteredMethods = new ArrayList();
      Method[] ret = methods;
      int len$ = methods.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Method method = ret[i$];
         if (this.isReallyAMethod(method)) {
            filteredMethods.add(method);
         }
      }

      ret = new Method[filteredMethods.size()];
      filteredMethods.toArray(ret);
      return ret;
   }

   public Method[] getMethods() {
      Method[] methods = this.clazz.getMethods();
      List filteredMethods = new ArrayList();
      Method[] ret = methods;
      int len$ = methods.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Method method = ret[i$];
         if (this.isReallyAMethod(method)) {
            filteredMethods.add(method);
         }
      }

      ret = new Method[filteredMethods.size()];
      filteredMethods.toArray(ret);
      return ret;
   }

   private boolean isReallyAMethod(Method method) {
      if (method.getName().startsWith("ajc$")) {
         return false;
      } else if (method.getAnnotations().length == 0) {
         return true;
      } else if (method.isAnnotationPresent(com.bea.core.repackaged.aspectj.lang.annotation.Pointcut.class)) {
         return false;
      } else if (method.isAnnotationPresent(Before.class)) {
         return false;
      } else if (method.isAnnotationPresent(After.class)) {
         return false;
      } else if (method.isAnnotationPresent(AfterReturning.class)) {
         return false;
      } else if (method.isAnnotationPresent(AfterThrowing.class)) {
         return false;
      } else {
         return !method.isAnnotationPresent(Around.class);
      }
   }

   public Pointcut getDeclaredPointcut(String name) throws NoSuchPointcutException {
      Pointcut[] pcs = this.getDeclaredPointcuts();
      Pointcut[] arr$ = pcs;
      int len$ = pcs.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Pointcut pc = arr$[i$];
         if (pc.getName().equals(name)) {
            return pc;
         }
      }

      throw new NoSuchPointcutException(name);
   }

   public Pointcut getPointcut(String name) throws NoSuchPointcutException {
      Pointcut[] pcs = this.getPointcuts();
      Pointcut[] arr$ = pcs;
      int len$ = pcs.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Pointcut pc = arr$[i$];
         if (pc.getName().equals(name)) {
            return pc;
         }
      }

      throw new NoSuchPointcutException(name);
   }

   public Pointcut[] getDeclaredPointcuts() {
      if (this.declaredPointcuts != null) {
         return this.declaredPointcuts;
      } else {
         List pointcuts = new ArrayList();
         Method[] methods = this.clazz.getDeclaredMethods();
         Method[] arr$ = methods;
         int len$ = methods.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Method method = arr$[i$];
            Pointcut pc = this.asPointcut(method);
            if (pc != null) {
               pointcuts.add(pc);
            }
         }

         Pointcut[] ret = new Pointcut[pointcuts.size()];
         pointcuts.toArray(ret);
         this.declaredPointcuts = ret;
         return ret;
      }
   }

   public Pointcut[] getPointcuts() {
      if (this.pointcuts != null) {
         return this.pointcuts;
      } else {
         List pcuts = new ArrayList();
         Method[] methods = this.clazz.getMethods();
         Method[] arr$ = methods;
         int len$ = methods.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Method method = arr$[i$];
            Pointcut pc = this.asPointcut(method);
            if (pc != null) {
               pcuts.add(pc);
            }
         }

         Pointcut[] ret = new Pointcut[pcuts.size()];
         pcuts.toArray(ret);
         this.pointcuts = ret;
         return ret;
      }
   }

   private Pointcut asPointcut(Method method) {
      com.bea.core.repackaged.aspectj.lang.annotation.Pointcut pcAnn = (com.bea.core.repackaged.aspectj.lang.annotation.Pointcut)method.getAnnotation(com.bea.core.repackaged.aspectj.lang.annotation.Pointcut.class);
      if (pcAnn != null) {
         String name = method.getName();
         if (name.startsWith("ajc$")) {
            int nameStart = name.indexOf("$$");
            name = name.substring(nameStart + 2, name.length());
            int nextDollar = name.indexOf("$");
            if (nextDollar != -1) {
               name = name.substring(0, nextDollar);
            }
         }

         return new PointcutImpl(name, pcAnn.value(), method, AjTypeSystem.getAjType(method.getDeclaringClass()), pcAnn.argNames());
      } else {
         return null;
      }
   }

   public Advice[] getDeclaredAdvice(AdviceKind... ofType) {
      EnumSet types;
      if (ofType.length == 0) {
         types = EnumSet.allOf(AdviceKind.class);
      } else {
         types = EnumSet.noneOf(AdviceKind.class);
         types.addAll(Arrays.asList(ofType));
      }

      return this.getDeclaredAdvice((Set)types);
   }

   public Advice[] getAdvice(AdviceKind... ofType) {
      EnumSet types;
      if (ofType.length == 0) {
         types = EnumSet.allOf(AdviceKind.class);
      } else {
         types = EnumSet.noneOf(AdviceKind.class);
         types.addAll(Arrays.asList(ofType));
      }

      return this.getAdvice((Set)types);
   }

   private Advice[] getDeclaredAdvice(Set ofAdviceTypes) {
      if (this.declaredAdvice == null) {
         this.initDeclaredAdvice();
      }

      List adviceList = new ArrayList();
      Advice[] ret = this.declaredAdvice;
      int len$ = ret.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Advice a = ret[i$];
         if (ofAdviceTypes.contains(a.getKind())) {
            adviceList.add(a);
         }
      }

      ret = new Advice[adviceList.size()];
      adviceList.toArray(ret);
      return ret;
   }

   private void initDeclaredAdvice() {
      Method[] methods = this.clazz.getDeclaredMethods();
      List adviceList = new ArrayList();
      Method[] arr$ = methods;
      int len$ = methods.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Method method = arr$[i$];
         Advice advice = this.asAdvice(method);
         if (advice != null) {
            adviceList.add(advice);
         }
      }

      this.declaredAdvice = new Advice[adviceList.size()];
      adviceList.toArray(this.declaredAdvice);
   }

   private Advice[] getAdvice(Set ofAdviceTypes) {
      if (this.advice == null) {
         this.initAdvice();
      }

      List adviceList = new ArrayList();
      Advice[] ret = this.advice;
      int len$ = ret.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Advice a = ret[i$];
         if (ofAdviceTypes.contains(a.getKind())) {
            adviceList.add(a);
         }
      }

      ret = new Advice[adviceList.size()];
      adviceList.toArray(ret);
      return ret;
   }

   private void initAdvice() {
      Method[] methods = this.clazz.getMethods();
      List adviceList = new ArrayList();
      Method[] arr$ = methods;
      int len$ = methods.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Method method = arr$[i$];
         Advice advice = this.asAdvice(method);
         if (advice != null) {
            adviceList.add(advice);
         }
      }

      this.advice = new Advice[adviceList.size()];
      adviceList.toArray(this.advice);
   }

   public Advice getAdvice(String name) throws NoSuchAdviceException {
      if (name.equals("")) {
         throw new IllegalArgumentException("use getAdvice(AdviceType...) instead for un-named advice");
      } else {
         if (this.advice == null) {
            this.initAdvice();
         }

         Advice[] arr$ = this.advice;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Advice a = arr$[i$];
            if (a.getName().equals(name)) {
               return a;
            }
         }

         throw new NoSuchAdviceException(name);
      }
   }

   public Advice getDeclaredAdvice(String name) throws NoSuchAdviceException {
      if (name.equals("")) {
         throw new IllegalArgumentException("use getAdvice(AdviceType...) instead for un-named advice");
      } else {
         if (this.declaredAdvice == null) {
            this.initDeclaredAdvice();
         }

         Advice[] arr$ = this.declaredAdvice;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Advice a = arr$[i$];
            if (a.getName().equals(name)) {
               return a;
            }
         }

         throw new NoSuchAdviceException(name);
      }
   }

   private Advice asAdvice(Method method) {
      if (method.getAnnotations().length == 0) {
         return null;
      } else {
         Before beforeAnn = (Before)method.getAnnotation(Before.class);
         if (beforeAnn != null) {
            return new AdviceImpl(method, beforeAnn.value(), AdviceKind.BEFORE);
         } else {
            After afterAnn = (After)method.getAnnotation(After.class);
            if (afterAnn != null) {
               return new AdviceImpl(method, afterAnn.value(), AdviceKind.AFTER);
            } else {
               AfterReturning afterReturningAnn = (AfterReturning)method.getAnnotation(AfterReturning.class);
               if (afterReturningAnn != null) {
                  String pcExpr = afterReturningAnn.pointcut();
                  if (pcExpr.equals("")) {
                     pcExpr = afterReturningAnn.value();
                  }

                  return new AdviceImpl(method, pcExpr, AdviceKind.AFTER_RETURNING, afterReturningAnn.returning());
               } else {
                  AfterThrowing afterThrowingAnn = (AfterThrowing)method.getAnnotation(AfterThrowing.class);
                  if (afterThrowingAnn != null) {
                     String pcExpr = afterThrowingAnn.pointcut();
                     if (pcExpr == null) {
                        pcExpr = afterThrowingAnn.value();
                     }

                     return new AdviceImpl(method, pcExpr, AdviceKind.AFTER_THROWING, afterThrowingAnn.throwing());
                  } else {
                     Around aroundAnn = (Around)method.getAnnotation(Around.class);
                     return aroundAnn != null ? new AdviceImpl(method, aroundAnn.value(), AdviceKind.AROUND) : null;
                  }
               }
            }
         }
      }
   }

   public InterTypeMethodDeclaration getDeclaredITDMethod(String name, AjType target, AjType... parameterTypes) throws NoSuchMethodException {
      InterTypeMethodDeclaration[] itdms = this.getDeclaredITDMethods();
      InterTypeMethodDeclaration[] arr$ = itdms;
      int len$ = itdms.length;

      label42:
      for(int i$ = 0; i$ < len$; ++i$) {
         InterTypeMethodDeclaration itdm = arr$[i$];

         try {
            if (itdm.getName().equals(name)) {
               AjType itdTarget = itdm.getTargetType();
               if (itdTarget.equals(target)) {
                  AjType[] ptypes = itdm.getParameterTypes();
                  if (ptypes.length == parameterTypes.length) {
                     for(int i = 0; i < ptypes.length; ++i) {
                        if (!ptypes[i].equals(parameterTypes[i])) {
                           continue label42;
                        }
                     }

                     return itdm;
                  }
               }
            }
         } catch (ClassNotFoundException var12) {
         }
      }

      throw new NoSuchMethodException(name);
   }

   public InterTypeMethodDeclaration[] getDeclaredITDMethods() {
      if (this.declaredITDMethods == null) {
         List itdms = new ArrayList();
         Method[] baseMethods = this.clazz.getDeclaredMethods();
         Method[] arr$ = baseMethods;
         int len$ = baseMethods.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Method m = arr$[i$];
            if (m.getName().contains("ajc$interMethodDispatch1$") && m.isAnnotationPresent(ajcITD.class)) {
               ajcITD ann = (ajcITD)m.getAnnotation(ajcITD.class);
               InterTypeMethodDeclaration itdm = new InterTypeMethodDeclarationImpl(this, ann.targetType(), ann.modifiers(), ann.name(), m);
               itdms.add(itdm);
            }
         }

         this.addAnnotationStyleITDMethods(itdms, false);
         this.declaredITDMethods = new InterTypeMethodDeclaration[itdms.size()];
         itdms.toArray(this.declaredITDMethods);
      }

      return this.declaredITDMethods;
   }

   public InterTypeMethodDeclaration getITDMethod(String name, AjType target, AjType... parameterTypes) throws NoSuchMethodException {
      InterTypeMethodDeclaration[] itdms = this.getITDMethods();
      InterTypeMethodDeclaration[] arr$ = itdms;
      int len$ = itdms.length;

      label42:
      for(int i$ = 0; i$ < len$; ++i$) {
         InterTypeMethodDeclaration itdm = arr$[i$];

         try {
            if (itdm.getName().equals(name)) {
               AjType itdTarget = itdm.getTargetType();
               if (itdTarget.equals(target)) {
                  AjType[] ptypes = itdm.getParameterTypes();
                  if (ptypes.length == parameterTypes.length) {
                     for(int i = 0; i < ptypes.length; ++i) {
                        if (!ptypes[i].equals(parameterTypes[i])) {
                           continue label42;
                        }
                     }

                     return itdm;
                  }
               }
            }
         } catch (ClassNotFoundException var12) {
         }
      }

      throw new NoSuchMethodException(name);
   }

   public InterTypeMethodDeclaration[] getITDMethods() {
      if (this.itdMethods == null) {
         List itdms = new ArrayList();
         Method[] baseMethods = this.clazz.getDeclaredMethods();
         Method[] arr$ = baseMethods;
         int len$ = baseMethods.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Method m = arr$[i$];
            if (m.getName().contains("ajc$interMethod$") && m.isAnnotationPresent(ajcITD.class)) {
               ajcITD ann = (ajcITD)m.getAnnotation(ajcITD.class);
               if (Modifier.isPublic(ann.modifiers())) {
                  InterTypeMethodDeclaration itdm = new InterTypeMethodDeclarationImpl(this, ann.targetType(), ann.modifiers(), ann.name(), m);
                  itdms.add(itdm);
               }
            }
         }

         this.addAnnotationStyleITDMethods(itdms, true);
         this.itdMethods = new InterTypeMethodDeclaration[itdms.size()];
         itdms.toArray(this.itdMethods);
      }

      return this.itdMethods;
   }

   private void addAnnotationStyleITDMethods(List toList, boolean publicOnly) {
      if (this.isAspect()) {
         Field[] arr$ = this.clazz.getDeclaredFields();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Field f = arr$[i$];
            if (f.getType().isInterface() && f.isAnnotationPresent(DeclareParents.class)) {
               Class decPAnnClass = DeclareParents.class;
               DeclareParents decPAnn = (DeclareParents)f.getAnnotation(decPAnnClass);
               if (decPAnn.defaultImpl() != decPAnnClass) {
                  Method[] arr$ = f.getType().getDeclaredMethods();
                  int len$ = arr$.length;

                  for(int i$ = 0; i$ < len$; ++i$) {
                     Method itdM = arr$[i$];
                     if (Modifier.isPublic(itdM.getModifiers()) || !publicOnly) {
                        InterTypeMethodDeclaration itdm = new InterTypeMethodDeclarationImpl(this, AjTypeSystem.getAjType(f.getType()), itdM, 1);
                        toList.add(itdm);
                     }
                  }
               }
            }
         }
      }

   }

   private void addAnnotationStyleITDFields(List toList, boolean publicOnly) {
   }

   public InterTypeConstructorDeclaration getDeclaredITDConstructor(AjType target, AjType... parameterTypes) throws NoSuchMethodException {
      InterTypeConstructorDeclaration[] itdcs = this.getDeclaredITDConstructors();
      InterTypeConstructorDeclaration[] arr$ = itdcs;
      int len$ = itdcs.length;

      label38:
      for(int i$ = 0; i$ < len$; ++i$) {
         InterTypeConstructorDeclaration itdc = arr$[i$];

         try {
            AjType itdTarget = itdc.getTargetType();
            if (itdTarget.equals(target)) {
               AjType[] ptypes = itdc.getParameterTypes();
               if (ptypes.length == parameterTypes.length) {
                  for(int i = 0; i < ptypes.length; ++i) {
                     if (!ptypes[i].equals(parameterTypes[i])) {
                        continue label38;
                     }
                  }

                  return itdc;
               }
            }
         } catch (ClassNotFoundException var11) {
         }
      }

      throw new NoSuchMethodException();
   }

   public InterTypeConstructorDeclaration[] getDeclaredITDConstructors() {
      if (this.declaredITDCons == null) {
         List itdcs = new ArrayList();
         Method[] baseMethods = this.clazz.getDeclaredMethods();
         Method[] arr$ = baseMethods;
         int len$ = baseMethods.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Method m = arr$[i$];
            if (m.getName().contains("ajc$postInterConstructor") && m.isAnnotationPresent(ajcITD.class)) {
               ajcITD ann = (ajcITD)m.getAnnotation(ajcITD.class);
               InterTypeConstructorDeclaration itdc = new InterTypeConstructorDeclarationImpl(this, ann.targetType(), ann.modifiers(), m);
               itdcs.add(itdc);
            }
         }

         this.declaredITDCons = new InterTypeConstructorDeclaration[itdcs.size()];
         itdcs.toArray(this.declaredITDCons);
      }

      return this.declaredITDCons;
   }

   public InterTypeConstructorDeclaration getITDConstructor(AjType target, AjType... parameterTypes) throws NoSuchMethodException {
      InterTypeConstructorDeclaration[] itdcs = this.getITDConstructors();
      InterTypeConstructorDeclaration[] arr$ = itdcs;
      int len$ = itdcs.length;

      label38:
      for(int i$ = 0; i$ < len$; ++i$) {
         InterTypeConstructorDeclaration itdc = arr$[i$];

         try {
            AjType itdTarget = itdc.getTargetType();
            if (itdTarget.equals(target)) {
               AjType[] ptypes = itdc.getParameterTypes();
               if (ptypes.length == parameterTypes.length) {
                  for(int i = 0; i < ptypes.length; ++i) {
                     if (!ptypes[i].equals(parameterTypes[i])) {
                        continue label38;
                     }
                  }

                  return itdc;
               }
            }
         } catch (ClassNotFoundException var11) {
         }
      }

      throw new NoSuchMethodException();
   }

   public InterTypeConstructorDeclaration[] getITDConstructors() {
      if (this.itdCons == null) {
         List itdcs = new ArrayList();
         Method[] baseMethods = this.clazz.getMethods();
         Method[] arr$ = baseMethods;
         int len$ = baseMethods.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Method m = arr$[i$];
            if (m.getName().contains("ajc$postInterConstructor") && m.isAnnotationPresent(ajcITD.class)) {
               ajcITD ann = (ajcITD)m.getAnnotation(ajcITD.class);
               if (Modifier.isPublic(ann.modifiers())) {
                  InterTypeConstructorDeclaration itdc = new InterTypeConstructorDeclarationImpl(this, ann.targetType(), ann.modifiers(), m);
                  itdcs.add(itdc);
               }
            }
         }

         this.itdCons = new InterTypeConstructorDeclaration[itdcs.size()];
         itdcs.toArray(this.itdCons);
      }

      return this.itdCons;
   }

   public InterTypeFieldDeclaration getDeclaredITDField(String name, AjType target) throws NoSuchFieldException {
      InterTypeFieldDeclaration[] itdfs = this.getDeclaredITDFields();
      InterTypeFieldDeclaration[] arr$ = itdfs;
      int len$ = itdfs.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         InterTypeFieldDeclaration itdf = arr$[i$];
         if (itdf.getName().equals(name)) {
            try {
               AjType itdTarget = itdf.getTargetType();
               if (itdTarget.equals(target)) {
                  return itdf;
               }
            } catch (ClassNotFoundException var9) {
            }
         }
      }

      throw new NoSuchFieldException(name);
   }

   public InterTypeFieldDeclaration[] getDeclaredITDFields() {
      List itdfs = new ArrayList();
      if (this.declaredITDFields == null) {
         Method[] baseMethods = this.clazz.getDeclaredMethods();
         Method[] arr$ = baseMethods;
         int len$ = baseMethods.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Method m = arr$[i$];
            if (m.isAnnotationPresent(ajcITD.class) && m.getName().contains("ajc$interFieldInit")) {
               ajcITD ann = (ajcITD)m.getAnnotation(ajcITD.class);
               String interFieldInitMethodName = m.getName();
               String interFieldGetDispatchMethodName = interFieldInitMethodName.replace("FieldInit", "FieldGetDispatch");

               try {
                  Method dispatch = this.clazz.getDeclaredMethod(interFieldGetDispatchMethodName, m.getParameterTypes());
                  InterTypeFieldDeclaration itdf = new InterTypeFieldDeclarationImpl(this, ann.targetType(), ann.modifiers(), ann.name(), AjTypeSystem.getAjType(dispatch.getReturnType()), dispatch.getGenericReturnType());
                  itdfs.add(itdf);
               } catch (NoSuchMethodException var12) {
                  throw new IllegalStateException("Can't find field get dispatch method for " + m.getName());
               }
            }
         }

         this.addAnnotationStyleITDFields(itdfs, false);
         this.declaredITDFields = new InterTypeFieldDeclaration[itdfs.size()];
         itdfs.toArray(this.declaredITDFields);
      }

      return this.declaredITDFields;
   }

   public InterTypeFieldDeclaration getITDField(String name, AjType target) throws NoSuchFieldException {
      InterTypeFieldDeclaration[] itdfs = this.getITDFields();
      InterTypeFieldDeclaration[] arr$ = itdfs;
      int len$ = itdfs.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         InterTypeFieldDeclaration itdf = arr$[i$];
         if (itdf.getName().equals(name)) {
            try {
               AjType itdTarget = itdf.getTargetType();
               if (itdTarget.equals(target)) {
                  return itdf;
               }
            } catch (ClassNotFoundException var9) {
            }
         }
      }

      throw new NoSuchFieldException(name);
   }

   public InterTypeFieldDeclaration[] getITDFields() {
      List itdfs = new ArrayList();
      if (this.itdFields == null) {
         Method[] baseMethods = this.clazz.getMethods();
         Method[] arr$ = baseMethods;
         int len$ = baseMethods.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Method m = arr$[i$];
            if (m.isAnnotationPresent(ajcITD.class)) {
               ajcITD ann = (ajcITD)m.getAnnotation(ajcITD.class);
               if (m.getName().contains("ajc$interFieldInit") && Modifier.isPublic(ann.modifiers())) {
                  String interFieldInitMethodName = m.getName();
                  String interFieldGetDispatchMethodName = interFieldInitMethodName.replace("FieldInit", "FieldGetDispatch");

                  try {
                     Method dispatch = m.getDeclaringClass().getDeclaredMethod(interFieldGetDispatchMethodName, m.getParameterTypes());
                     InterTypeFieldDeclaration itdf = new InterTypeFieldDeclarationImpl(this, ann.targetType(), ann.modifiers(), ann.name(), AjTypeSystem.getAjType(dispatch.getReturnType()), dispatch.getGenericReturnType());
                     itdfs.add(itdf);
                  } catch (NoSuchMethodException var12) {
                     throw new IllegalStateException("Can't find field get dispatch method for " + m.getName());
                  }
               }
            }
         }

         this.addAnnotationStyleITDFields(itdfs, true);
         this.itdFields = new InterTypeFieldDeclaration[itdfs.size()];
         itdfs.toArray(this.itdFields);
      }

      return this.itdFields;
   }

   public DeclareErrorOrWarning[] getDeclareErrorOrWarnings() {
      List deows = new ArrayList();
      Field[] arr$ = this.clazz.getDeclaredFields();
      int len$ = arr$.length;

      int i$;
      for(i$ = 0; i$ < len$; ++i$) {
         Field field = arr$[i$];

         try {
            String message;
            DeclareErrorOrWarningImpl deow;
            if (field.isAnnotationPresent(DeclareWarning.class)) {
               DeclareWarning dw = (DeclareWarning)field.getAnnotation(DeclareWarning.class);
               if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                  message = (String)field.get((Object)null);
                  deow = new DeclareErrorOrWarningImpl(dw.value(), message, false, this);
                  deows.add(deow);
               }
            } else if (field.isAnnotationPresent(DeclareError.class)) {
               DeclareError de = (DeclareError)field.getAnnotation(DeclareError.class);
               if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                  message = (String)field.get((Object)null);
                  deow = new DeclareErrorOrWarningImpl(de.value(), message, true, this);
                  deows.add(deow);
               }
            }
         } catch (IllegalArgumentException var9) {
         } catch (IllegalAccessException var10) {
         }
      }

      Method[] arr$ = this.clazz.getDeclaredMethods();
      len$ = arr$.length;

      for(i$ = 0; i$ < len$; ++i$) {
         Method method = arr$[i$];
         if (method.isAnnotationPresent(ajcDeclareEoW.class)) {
            ajcDeclareEoW deowAnn = (ajcDeclareEoW)method.getAnnotation(ajcDeclareEoW.class);
            DeclareErrorOrWarning deow = new DeclareErrorOrWarningImpl(deowAnn.pointcut(), deowAnn.message(), deowAnn.isError(), this);
            deows.add(deow);
         }
      }

      DeclareErrorOrWarning[] ret = new DeclareErrorOrWarning[deows.size()];
      deows.toArray(ret);
      return ret;
   }

   public com.bea.core.repackaged.aspectj.lang.reflect.DeclareParents[] getDeclareParents() {
      List decps = new ArrayList();
      Method[] arr$ = this.clazz.getDeclaredMethods();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Method method = arr$[i$];
         if (method.isAnnotationPresent(ajcDeclareParents.class)) {
            ajcDeclareParents decPAnn = (ajcDeclareParents)method.getAnnotation(ajcDeclareParents.class);
            DeclareParentsImpl decp = new DeclareParentsImpl(decPAnn.targetTypePattern(), decPAnn.parentTypes(), decPAnn.isExtends(), this);
            decps.add(decp);
         }
      }

      this.addAnnotationStyleDeclareParents(decps);
      if (this.getSupertype().isAspect()) {
         decps.addAll(Arrays.asList(this.getSupertype().getDeclareParents()));
      }

      com.bea.core.repackaged.aspectj.lang.reflect.DeclareParents[] ret = new com.bea.core.repackaged.aspectj.lang.reflect.DeclareParents[decps.size()];
      decps.toArray(ret);
      return ret;
   }

   private void addAnnotationStyleDeclareParents(List toList) {
      Field[] arr$ = this.clazz.getDeclaredFields();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Field f = arr$[i$];
         if (f.isAnnotationPresent(DeclareParents.class) && f.getType().isInterface()) {
            DeclareParents ann = (DeclareParents)f.getAnnotation(DeclareParents.class);
            String parentType = f.getType().getName();
            DeclareParentsImpl decp = new DeclareParentsImpl(ann.value(), parentType, false, this);
            toList.add(decp);
         }
      }

   }

   public DeclareSoft[] getDeclareSofts() {
      List decs = new ArrayList();
      Method[] arr$ = this.clazz.getDeclaredMethods();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Method method = arr$[i$];
         if (method.isAnnotationPresent(ajcDeclareSoft.class)) {
            ajcDeclareSoft decSAnn = (ajcDeclareSoft)method.getAnnotation(ajcDeclareSoft.class);
            DeclareSoftImpl ds = new DeclareSoftImpl(this, decSAnn.pointcut(), decSAnn.exceptionType());
            decs.add(ds);
         }
      }

      if (this.getSupertype().isAspect()) {
         decs.addAll(Arrays.asList(this.getSupertype().getDeclareSofts()));
      }

      DeclareSoft[] ret = new DeclareSoft[decs.size()];
      decs.toArray(ret);
      return ret;
   }

   public DeclareAnnotation[] getDeclareAnnotations() {
      List decAs = new ArrayList();
      Method[] arr$ = this.clazz.getDeclaredMethods();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Method method = arr$[i$];
         if (method.isAnnotationPresent(ajcDeclareAnnotation.class)) {
            ajcDeclareAnnotation decAnn = (ajcDeclareAnnotation)method.getAnnotation(ajcDeclareAnnotation.class);
            Annotation targetAnnotation = null;
            Annotation[] anns = method.getAnnotations();
            Annotation[] arr$ = anns;
            int len$ = anns.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               Annotation ann = arr$[i$];
               if (ann.annotationType() != ajcDeclareAnnotation.class) {
                  targetAnnotation = ann;
                  break;
               }
            }

            DeclareAnnotationImpl da = new DeclareAnnotationImpl(this, decAnn.kind(), decAnn.pattern(), targetAnnotation, decAnn.annotation());
            decAs.add(da);
         }
      }

      if (this.getSupertype().isAspect()) {
         decAs.addAll(Arrays.asList(this.getSupertype().getDeclareAnnotations()));
      }

      DeclareAnnotation[] ret = new DeclareAnnotation[decAs.size()];
      decAs.toArray(ret);
      return ret;
   }

   public DeclarePrecedence[] getDeclarePrecedence() {
      List decps = new ArrayList();
      if (this.clazz.isAnnotationPresent(com.bea.core.repackaged.aspectj.lang.annotation.DeclarePrecedence.class)) {
         com.bea.core.repackaged.aspectj.lang.annotation.DeclarePrecedence ann = (com.bea.core.repackaged.aspectj.lang.annotation.DeclarePrecedence)this.clazz.getAnnotation(com.bea.core.repackaged.aspectj.lang.annotation.DeclarePrecedence.class);
         DeclarePrecedenceImpl decp = new DeclarePrecedenceImpl(ann.value(), this);
         decps.add(decp);
      }

      Method[] arr$ = this.clazz.getDeclaredMethods();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Method method = arr$[i$];
         if (method.isAnnotationPresent(ajcDeclarePrecedence.class)) {
            ajcDeclarePrecedence decPAnn = (ajcDeclarePrecedence)method.getAnnotation(ajcDeclarePrecedence.class);
            DeclarePrecedenceImpl decp = new DeclarePrecedenceImpl(decPAnn.value(), this);
            decps.add(decp);
         }
      }

      if (this.getSupertype().isAspect()) {
         decps.addAll(Arrays.asList(this.getSupertype().getDeclarePrecedence()));
      }

      DeclarePrecedence[] ret = new DeclarePrecedence[decps.size()];
      decps.toArray(ret);
      return ret;
   }

   public Object[] getEnumConstants() {
      return this.clazz.getEnumConstants();
   }

   public TypeVariable[] getTypeParameters() {
      return this.clazz.getTypeParameters();
   }

   public boolean isEnum() {
      return this.clazz.isEnum();
   }

   public boolean isInstance(Object o) {
      return this.clazz.isInstance(o);
   }

   public boolean isInterface() {
      return this.clazz.isInterface();
   }

   public boolean isLocalClass() {
      return this.clazz.isLocalClass() && !this.isAspect();
   }

   public boolean isMemberClass() {
      return this.clazz.isMemberClass() && !this.isAspect();
   }

   public boolean isArray() {
      return this.clazz.isArray();
   }

   public boolean isPrimitive() {
      return this.clazz.isPrimitive();
   }

   public boolean isAspect() {
      return this.clazz.getAnnotation(Aspect.class) != null;
   }

   public boolean isMemberAspect() {
      return this.clazz.isMemberClass() && this.isAspect();
   }

   public boolean isPrivileged() {
      return this.isAspect() && this.clazz.isAnnotationPresent(ajcPrivileged.class);
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof AjTypeImpl)) {
         return false;
      } else {
         AjTypeImpl other = (AjTypeImpl)obj;
         return other.clazz.equals(this.clazz);
      }
   }

   public int hashCode() {
      return this.clazz.hashCode();
   }

   private AjType[] toAjTypeArray(Class[] classes) {
      AjType[] ajtypes = new AjType[classes.length];

      for(int i = 0; i < ajtypes.length; ++i) {
         ajtypes[i] = AjTypeSystem.getAjType(classes[i]);
      }

      return ajtypes;
   }

   private Class[] toClassArray(AjType[] ajTypes) {
      Class[] classes = new Class[ajTypes.length];

      for(int i = 0; i < classes.length; ++i) {
         classes[i] = ajTypes[i].getJavaClass();
      }

      return classes;
   }

   public String toString() {
      return this.getName();
   }
}
