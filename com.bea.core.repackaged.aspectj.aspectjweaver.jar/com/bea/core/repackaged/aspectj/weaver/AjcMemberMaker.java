package com.bea.core.repackaged.aspectj.weaver;

import java.lang.reflect.Modifier;

public class AjcMemberMaker {
   private static final int PUBLIC_STATIC_FINAL = 25;
   private static final int PRIVATE_STATIC = 10;
   private static final int PUBLIC_STATIC = 9;
   private static final int BRIDGE = 64;
   private static final int VISIBILITY = 7;
   public static final UnresolvedType CFLOW_STACK_TYPE = UnresolvedType.forName("com.bea.core.repackaged.aspectj.runtime.internal.CFlowStack");
   public static final UnresolvedType AROUND_CLOSURE_TYPE = UnresolvedType.forSignature("Lorg/aspectj/runtime/internal/AroundClosure;");
   public static final UnresolvedType CONVERSIONS_TYPE = UnresolvedType.forSignature("Lorg/aspectj/runtime/internal/Conversions;");
   public static final UnresolvedType NO_ASPECT_BOUND_EXCEPTION = UnresolvedType.forSignature("Lorg/aspectj/lang/NoAspectBoundException;");
   public static final UnresolvedType ASPECT_ANNOTATION = UnresolvedType.forSignature("Lorg/aspectj/lang/annotation/Aspect;");
   public static final UnresolvedType BEFORE_ANNOTATION = UnresolvedType.forSignature("Lorg/aspectj/lang/annotation/Before;");
   public static final UnresolvedType AROUND_ANNOTATION = UnresolvedType.forSignature("Lorg/aspectj/lang/annotation/Around;");
   public static final UnresolvedType AFTERRETURNING_ANNOTATION = UnresolvedType.forSignature("Lorg/aspectj/lang/annotation/AfterReturning;");
   public static final UnresolvedType AFTERTHROWING_ANNOTATION = UnresolvedType.forSignature("Lorg/aspectj/lang/annotation/AfterThrowing;");
   public static final UnresolvedType AFTER_ANNOTATION = UnresolvedType.forSignature("Lorg/aspectj/lang/annotation/After;");
   public static final UnresolvedType POINTCUT_ANNOTATION = UnresolvedType.forSignature("Lorg/aspectj/lang/annotation/Pointcut;");
   public static final UnresolvedType DECLAREERROR_ANNOTATION = UnresolvedType.forSignature("Lorg/aspectj/lang/annotation/DeclareError;");
   public static final UnresolvedType DECLAREWARNING_ANNOTATION = UnresolvedType.forSignature("Lorg/aspectj/lang/annotation/DeclareWarning;");
   public static final UnresolvedType DECLAREPRECEDENCE_ANNOTATION = UnresolvedType.forSignature("Lorg/aspectj/lang/annotation/DeclarePrecedence;");
   public static final UnresolvedType DECLAREPARENTS_ANNOTATION = UnresolvedType.forSignature("Lorg/aspectj/lang/annotation/DeclareParents;");
   public static final UnresolvedType DECLAREMIXIN_ANNOTATION = UnresolvedType.forSignature("Lorg/aspectj/lang/annotation/DeclareMixin;");
   public static final UnresolvedType TYPEX_JOINPOINT = UnresolvedType.forSignature("Lorg/aspectj/lang/JoinPoint;");
   public static final UnresolvedType TYPEX_PROCEEDINGJOINPOINT = UnresolvedType.forSignature("Lorg/aspectj/lang/ProceedingJoinPoint;");
   public static final UnresolvedType TYPEX_STATICJOINPOINT = UnresolvedType.forSignature("Lorg/aspectj/lang/JoinPoint$StaticPart;");
   public static final UnresolvedType TYPEX_ENCLOSINGSTATICJOINPOINT = UnresolvedType.forSignature("Lorg/aspectj/lang/JoinPoint$EnclosingStaticPart;");

   public static ResolvedMember ajcPreClinitMethod(UnresolvedType declaringType) {
      return new ResolvedMemberImpl(Member.METHOD, declaringType, 10, "ajc$preClinit", "()V");
   }

   public static ResolvedMember ajcPostClinitMethod(UnresolvedType declaringType) {
      return new ResolvedMemberImpl(Member.METHOD, declaringType, 10, "ajc$postClinit", "()V");
   }

   public static Member noAspectBoundExceptionInit() {
      return new ResolvedMemberImpl(Member.METHOD, NO_ASPECT_BOUND_EXCEPTION, 1, "<init>", "()V");
   }

   public static Member noAspectBoundExceptionInit2() {
      return new ResolvedMemberImpl(Member.METHOD, NO_ASPECT_BOUND_EXCEPTION, 1, "<init>", "(Ljava/lang/String;Ljava/lang/Throwable;)V");
   }

   public static Member noAspectBoundExceptionInitWithCause() {
      return new ResolvedMemberImpl(Member.METHOD, NO_ASPECT_BOUND_EXCEPTION, 1, "<init>", "(Ljava/lang/String;Ljava/lang/Throwable;)V");
   }

   public static ResolvedMember perCflowPush(UnresolvedType declaringType) {
      return new ResolvedMemberImpl(Member.METHOD, declaringType, 9, "ajc$perCflowPush", "()V");
   }

   public static ResolvedMember perCflowField(UnresolvedType declaringType) {
      return new ResolvedMemberImpl(Member.FIELD, declaringType, 25, "ajc$perCflowStack", CFLOW_STACK_TYPE.getSignature());
   }

   public static ResolvedMember perSingletonField(UnresolvedType declaringType) {
      return new ResolvedMemberImpl(Member.FIELD, declaringType, 25, "ajc$perSingletonInstance", declaringType.getSignature());
   }

   public static ResolvedMember initFailureCauseField(UnresolvedType declaringType) {
      return new ResolvedMemberImpl(Member.FIELD, declaringType, 10, "ajc$initFailureCause", UnresolvedType.THROWABLE.getSignature());
   }

   public static ResolvedMember perObjectField(UnresolvedType declaringType, ResolvedType aspectType) {
      int modifiers = 2;
      if (!UnresolvedType.SERIALIZABLE.resolve(aspectType.getWorld()).isAssignableFrom(aspectType)) {
         modifiers |= 128;
      }

      return new ResolvedMemberImpl(Member.FIELD, declaringType, modifiers, aspectType, NameMangler.perObjectInterfaceField(aspectType), UnresolvedType.NONE);
   }

   public static ResolvedMember perTypeWithinField(UnresolvedType declaringType, ResolvedType aspectType) {
      int modifiers = 10;
      if (!isSerializableAspect(aspectType)) {
         modifiers |= 128;
      }

      return new ResolvedMemberImpl(Member.FIELD, declaringType, modifiers, aspectType, NameMangler.perTypeWithinFieldForTarget(aspectType), UnresolvedType.NONE);
   }

   public static ResolvedMember perTypeWithinWithinTypeField(UnresolvedType declaringType, ResolvedType aspectType) {
      int modifiers = 2;
      if (!isSerializableAspect(aspectType)) {
         modifiers |= 128;
      }

      return new ResolvedMemberImpl(Member.FIELD, declaringType, modifiers, UnresolvedType.JL_STRING, "ajc$withinType", UnresolvedType.NONE);
   }

   private static boolean isSerializableAspect(ResolvedType aspectType) {
      return UnresolvedType.SERIALIZABLE.resolve(aspectType.getWorld()).isAssignableFrom(aspectType);
   }

   public static ResolvedMember perObjectBind(UnresolvedType declaringType) {
      return new ResolvedMemberImpl(Member.METHOD, declaringType, 41, "ajc$perObjectBind", "(Ljava/lang/Object;)V");
   }

   public static ResolvedMember perTypeWithinGetInstance(UnresolvedType declaringType) {
      ResolvedMemberImpl rm = new ResolvedMemberImpl(Member.METHOD, declaringType, 10, declaringType, "ajc$getInstance", new UnresolvedType[]{UnresolvedType.JL_CLASS});
      return rm;
   }

   public static ResolvedMember perTypeWithinGetWithinTypeNameMethod(UnresolvedType declaringType, boolean inJava5Mode) {
      ResolvedMemberImpl rm = new ResolvedMemberImpl(Member.METHOD, declaringType, 1, UnresolvedType.JL_STRING, "getWithinTypeName", UnresolvedType.NONE);
      return rm;
   }

   public static ResolvedMember perTypeWithinCreateAspectInstance(UnresolvedType declaringType) {
      ResolvedMemberImpl rm = new ResolvedMemberImpl(Member.METHOD, declaringType, 9, declaringType, "ajc$createAspectInstance", new UnresolvedType[]{UnresolvedType.forSignature("Ljava/lang/String;")}, new UnresolvedType[0]);
      return rm;
   }

   public static UnresolvedType perObjectInterfaceType(UnresolvedType aspectType) {
      return UnresolvedType.forName(aspectType.getName() + "$ajcMightHaveAspect");
   }

   public static ResolvedMember perObjectInterfaceGet(UnresolvedType aspectType) {
      return new ResolvedMemberImpl(Member.METHOD, perObjectInterfaceType(aspectType), 1025, NameMangler.perObjectInterfaceGet(aspectType), "()" + aspectType.getSignature());
   }

   public static ResolvedMember perObjectInterfaceSet(UnresolvedType aspectType) {
      return new ResolvedMemberImpl(Member.METHOD, perObjectInterfaceType(aspectType), 1025, NameMangler.perObjectInterfaceSet(aspectType), "(" + aspectType.getSignature() + ")V");
   }

   public static ResolvedMember perTypeWithinLocalAspectOf(UnresolvedType shadowType, UnresolvedType aspectType) {
      return new ResolvedMemberImpl(Member.METHOD, shadowType, 9, NameMangler.perTypeWithinLocalAspectOf(aspectType), "()" + aspectType.getSignature());
   }

   public static ResolvedMember perSingletonAspectOfMethod(UnresolvedType declaringType) {
      return new ResolvedMemberImpl(Member.METHOD, declaringType, 9, "aspectOf", "()" + declaringType.getSignature());
   }

   public static ResolvedMember perSingletonHasAspectMethod(UnresolvedType declaringType) {
      return new ResolvedMemberImpl(Member.METHOD, declaringType, 9, "hasAspect", "()Z");
   }

   public static ResolvedMember perCflowAspectOfMethod(UnresolvedType declaringType) {
      return perSingletonAspectOfMethod(declaringType);
   }

   public static ResolvedMember perCflowHasAspectMethod(UnresolvedType declaringType) {
      return perSingletonHasAspectMethod(declaringType);
   }

   public static ResolvedMember perObjectAspectOfMethod(UnresolvedType declaringType) {
      return new ResolvedMemberImpl(Member.METHOD, declaringType, 9, "aspectOf", "(Ljava/lang/Object;)" + declaringType.getSignature());
   }

   public static ResolvedMember perObjectHasAspectMethod(UnresolvedType declaringType) {
      return new ResolvedMemberImpl(Member.METHOD, declaringType, 9, "hasAspect", "(Ljava/lang/Object;)Z");
   }

   public static ResolvedMember perTypeWithinAspectOfMethod(UnresolvedType declaringType, boolean inJava5Mode) {
      UnresolvedType parameterType = null;
      if (inJava5Mode) {
         parameterType = UnresolvedType.forRawTypeName("java.lang.Class");
      } else {
         parameterType = UnresolvedType.forSignature("Ljava/lang/Class;");
      }

      return new ResolvedMemberImpl(Member.METHOD, declaringType, 9, declaringType, "aspectOf", new UnresolvedType[]{parameterType});
   }

   public static ResolvedMember perTypeWithinHasAspectMethod(UnresolvedType declaringType, boolean inJava5Mode) {
      UnresolvedType parameterType = null;
      if (inJava5Mode) {
         parameterType = UnresolvedType.forRawTypeName("java.lang.Class");
      } else {
         parameterType = UnresolvedType.forSignature("Ljava/lang/Class;");
      }

      return new ResolvedMemberImpl(Member.METHOD, declaringType, 9, UnresolvedType.BOOLEAN, "hasAspect", new UnresolvedType[]{parameterType});
   }

   public static ResolvedMember privilegedAccessMethodForMethod(UnresolvedType aspectType, ResolvedMember method) {
      return new ResolvedMemberImpl(Member.METHOD, method.getDeclaringType(), 1 | (Modifier.isStatic(method.getModifiers()) ? 8 : 0), method.getReturnType(), NameMangler.privilegedAccessMethodForMethod(method.getName(), method.getDeclaringType(), aspectType), method.getParameterTypes(), method.getExceptions());
   }

   public static ResolvedMember privilegedAccessMethodForFieldGet(UnresolvedType aspectType, Member field, boolean shortSyntax) {
      UnresolvedType fieldDeclaringType = field.getDeclaringType();
      String getterName;
      if (shortSyntax) {
         getterName = null;
         Object args;
         if (Modifier.isStatic(field.getModifiers())) {
            args = ResolvedType.NONE;
         } else {
            args = new UnresolvedType[]{fieldDeclaringType};
         }

         StringBuffer name = new StringBuffer("ajc$get$");
         name.append(field.getName());
         return new ResolvedMemberImpl(Member.METHOD, fieldDeclaringType, 9, field.getReturnType(), name.toString(), (UnresolvedType[])args);
      } else {
         getterName = NameMangler.privilegedAccessMethodForFieldGet(field.getName(), fieldDeclaringType, aspectType);
         String sig;
         if (Modifier.isStatic(field.getModifiers())) {
            sig = "()" + field.getReturnType().getSignature();
         } else {
            sig = "(" + fieldDeclaringType.getSignature() + ")" + field.getReturnType().getSignature();
         }

         return new ResolvedMemberImpl(Member.METHOD, fieldDeclaringType, 9, getterName, sig);
      }
   }

   public static ResolvedMember privilegedAccessMethodForFieldSet(UnresolvedType aspectType, Member field, boolean shortSyntax) {
      UnresolvedType fieldDeclaringType = field.getDeclaringType();
      String setterName;
      if (shortSyntax) {
         setterName = null;
         UnresolvedType[] args;
         if (Modifier.isStatic(field.getModifiers())) {
            args = new UnresolvedType[]{field.getType()};
         } else {
            args = new UnresolvedType[]{fieldDeclaringType, field.getType()};
         }

         StringBuffer name = new StringBuffer("ajc$set$");
         name.append(field.getName());
         return new ResolvedMemberImpl(Member.METHOD, fieldDeclaringType, 9, UnresolvedType.VOID, name.toString(), args);
      } else {
         setterName = NameMangler.privilegedAccessMethodForFieldSet(field.getName(), fieldDeclaringType, aspectType);
         String sig;
         if (Modifier.isStatic(field.getModifiers())) {
            sig = "(" + field.getReturnType().getSignature() + ")V";
         } else {
            sig = "(" + fieldDeclaringType.getSignature() + field.getReturnType().getSignature() + ")V";
         }

         return new ResolvedMemberImpl(Member.METHOD, fieldDeclaringType, 9, setterName, sig);
      }
   }

   public static ResolvedMember superAccessMethod(UnresolvedType baseType, ResolvedMember method) {
      UnresolvedType[] paramTypes = method.getParameterTypes();
      return new ResolvedMemberImpl(Member.METHOD, baseType, 1, method.getReturnType(), NameMangler.superDispatchMethod(baseType, method.getName()), paramTypes, method.getExceptions());
   }

   public static ResolvedMember inlineAccessMethodForMethod(UnresolvedType aspectType, ResolvedMember method) {
      UnresolvedType[] paramTypes = method.getParameterTypes();
      if (!Modifier.isStatic(method.getModifiers())) {
         paramTypes = UnresolvedType.insert(method.getDeclaringType(), paramTypes);
      }

      return new ResolvedMemberImpl(Member.METHOD, aspectType, 9, method.getReturnType(), NameMangler.inlineAccessMethodForMethod(method.getName(), method.getDeclaringType(), aspectType), paramTypes, method.getExceptions());
   }

   public static ResolvedMember inlineAccessMethodForFieldGet(UnresolvedType aspectType, Member field) {
      String sig;
      if (Modifier.isStatic(field.getModifiers())) {
         sig = "()" + field.getReturnType().getSignature();
      } else {
         sig = "(" + field.getDeclaringType().getSignature() + ")" + field.getReturnType().getSignature();
      }

      return new ResolvedMemberImpl(Member.METHOD, aspectType, 9, NameMangler.inlineAccessMethodForFieldGet(field.getName(), field.getDeclaringType(), aspectType), sig);
   }

   public static ResolvedMember inlineAccessMethodForFieldSet(UnresolvedType aspectType, Member field) {
      String sig;
      if (Modifier.isStatic(field.getModifiers())) {
         sig = "(" + field.getReturnType().getSignature() + ")V";
      } else {
         sig = "(" + field.getDeclaringType().getSignature() + field.getReturnType().getSignature() + ")V";
      }

      return new ResolvedMemberImpl(Member.METHOD, aspectType, 9, NameMangler.inlineAccessMethodForFieldSet(field.getName(), field.getDeclaringType(), aspectType), sig);
   }

   public static Member cflowStackPeekInstance() {
      return new MemberImpl(Member.METHOD, CFLOW_STACK_TYPE, 0, "peekInstance", "()Ljava/lang/Object;");
   }

   public static Member cflowStackPushInstance() {
      return new MemberImpl(Member.METHOD, CFLOW_STACK_TYPE, 0, "pushInstance", "(Ljava/lang/Object;)V");
   }

   public static Member cflowStackIsValid() {
      return new MemberImpl(Member.METHOD, CFLOW_STACK_TYPE, 0, "isValid", "()Z");
   }

   public static Member cflowStackInit() {
      return new MemberImpl(Member.CONSTRUCTOR, CFLOW_STACK_TYPE, 0, "<init>", "()V");
   }

   public static Member aroundClosurePreInitializationField() {
      return new MemberImpl(Member.FIELD, AROUND_CLOSURE_TYPE, 0, "preInitializationState", "[Ljava/lang/Object;");
   }

   public static Member aroundClosurePreInitializationGetter() {
      return new MemberImpl(Member.METHOD, AROUND_CLOSURE_TYPE, 0, "getPreInitializationState", "()[Ljava/lang/Object;");
   }

   public static ResolvedMember preIntroducedConstructor(UnresolvedType aspectType, UnresolvedType targetType, UnresolvedType[] paramTypes) {
      return new ResolvedMemberImpl(Member.METHOD, aspectType, 25, UnresolvedType.OBJECTARRAY, NameMangler.preIntroducedConstructor(aspectType, targetType), paramTypes);
   }

   public static ResolvedMember postIntroducedConstructor(UnresolvedType aspectType, UnresolvedType targetType, UnresolvedType[] paramTypes) {
      return new ResolvedMemberImpl(Member.METHOD, aspectType, 25, UnresolvedType.VOID, NameMangler.postIntroducedConstructor(aspectType, targetType), UnresolvedType.insert(targetType, paramTypes));
   }

   public static ResolvedMember itdAtDeclareParentsField(ResolvedType targetType, UnresolvedType itdType, UnresolvedType aspectType) {
      return new ResolvedMemberImpl(Member.FIELD, targetType, 2, itdType, NameMangler.itdAtDeclareParentsField(aspectType, itdType), (UnresolvedType[])null);
   }

   public static ResolvedMember interConstructor(ResolvedType targetType, ResolvedMember constructor, UnresolvedType aspectType) {
      ResolvedMember ret = new ResolvedMemberImpl(Member.CONSTRUCTOR, targetType, 1, UnresolvedType.VOID, "<init>", constructor.getParameterTypes(), constructor.getExceptions());
      if (Modifier.isPublic(constructor.getModifiers())) {
         return (ResolvedMember)ret;
      } else {
         do {
            ret = addCookieTo((ResolvedMember)ret, aspectType);
         } while(targetType.lookupMemberNoSupers((Member)ret) != null);

         return (ResolvedMember)ret;
      }
   }

   public static ResolvedMember interFieldInitializer(ResolvedMember field, UnresolvedType aspectType) {
      return new ResolvedMemberImpl(Member.METHOD, aspectType, 9, NameMangler.interFieldInitializer(aspectType, field.getDeclaringType(), field.getName()), Modifier.isStatic(field.getModifiers()) ? "()V" : "(" + field.getDeclaringType().getSignature() + ")V");
   }

   private static int makePublicNonFinal(int modifiers) {
      return modifiers & -8 & -17 | 1;
   }

   private static int makeNonFinal(int modifiers) {
      return modifiers & -17;
   }

   public static ResolvedMember interFieldSetDispatcher(ResolvedMember field, UnresolvedType aspectType) {
      ResolvedMember rm = new ResolvedMemberImpl(Member.METHOD, aspectType, 9, UnresolvedType.VOID, NameMangler.interFieldSetDispatcher(aspectType, field.getDeclaringType(), field.getName()), Modifier.isStatic(field.getModifiers()) ? new UnresolvedType[]{field.getReturnType()} : new UnresolvedType[]{field.getDeclaringType(), field.getReturnType()});
      rm.setTypeVariables(field.getTypeVariables());
      return rm;
   }

   public static ResolvedMember interFieldGetDispatcher(ResolvedMember field, UnresolvedType aspectType) {
      ResolvedMember rm = new ResolvedMemberImpl(Member.METHOD, aspectType, 9, field.getReturnType(), NameMangler.interFieldGetDispatcher(aspectType, field.getDeclaringType(), field.getName()), Modifier.isStatic(field.getModifiers()) ? UnresolvedType.NONE : new UnresolvedType[]{field.getDeclaringType()}, UnresolvedType.NONE);
      rm.setTypeVariables(field.getTypeVariables());
      return rm;
   }

   public static ResolvedMember interFieldClassField(ResolvedMember field, UnresolvedType aspectType, boolean newStyle) {
      int modifiers = newStyle ? makeNonFinal(field.getModifiers()) : makePublicNonFinal(field.getModifiers());
      String name = null;
      if (newStyle) {
         name = field.getName();
      } else {
         name = NameMangler.interFieldClassField(field.getModifiers(), aspectType, field.getDeclaringType(), field.getName());
      }

      return new ResolvedMemberImpl(Member.FIELD, field.getDeclaringType(), modifiers, field.getReturnType(), name, UnresolvedType.NONE, UnresolvedType.NONE);
   }

   public static ResolvedMember interFieldInterfaceField(ResolvedMember field, UnresolvedType onClass, UnresolvedType aspectType, boolean newStyle) {
      String name = null;
      if (newStyle) {
         name = field.getName();
      } else {
         name = NameMangler.interFieldInterfaceField(aspectType, field.getDeclaringType(), field.getName());
      }

      return new ResolvedMemberImpl(Member.FIELD, onClass, makePublicNonFinal(field.getModifiers()), field.getReturnType(), name, UnresolvedType.NONE, UnresolvedType.NONE);
   }

   public static ResolvedMember interFieldInterfaceSetter(ResolvedMember field, ResolvedType onType, UnresolvedType aspectType) {
      int modifiers = 1;
      if (onType.isInterface()) {
         modifiers |= 1024;
      }

      ResolvedMember rm = new ResolvedMemberImpl(Member.METHOD, onType, modifiers, UnresolvedType.VOID, NameMangler.interFieldInterfaceSetter(aspectType, field.getDeclaringType(), field.getName()), new UnresolvedType[]{field.getReturnType()}, UnresolvedType.NONE);
      rm.setTypeVariables(field.getTypeVariables());
      return rm;
   }

   public static ResolvedMember interFieldInterfaceGetter(ResolvedMember field, ResolvedType onType, UnresolvedType aspectType) {
      int modifiers = 1;
      if (onType.isInterface()) {
         modifiers |= 1024;
      }

      ResolvedMember rm = new ResolvedMemberImpl(Member.METHOD, onType, modifiers, field.getReturnType(), NameMangler.interFieldInterfaceGetter(aspectType, field.getDeclaringType(), field.getName()), UnresolvedType.NONE, UnresolvedType.NONE);
      rm.setTypeVariables(field.getTypeVariables());
      return rm;
   }

   public static ResolvedMember interMethod(ResolvedMember meth, UnresolvedType aspectType, boolean onInterface) {
      if (Modifier.isPublic(meth.getModifiers()) && !onInterface) {
         return meth;
      } else {
         int modifiers = makePublicNonFinal(meth.getModifiers());
         if (onInterface) {
            modifiers |= 1024;
         }

         ResolvedMemberImpl rmi = new ResolvedMemberImpl(Member.METHOD, meth.getDeclaringType(), modifiers, meth.getReturnType(), NameMangler.interMethod(meth.getModifiers(), aspectType, meth.getDeclaringType(), meth.getName()), meth.getParameterTypes(), meth.getExceptions());
         rmi.setParameterNames(meth.getParameterNames());
         rmi.setTypeVariables(meth.getTypeVariables());
         return rmi;
      }
   }

   public static ResolvedMember interMethodBridger(ResolvedMember meth, UnresolvedType aspectType, boolean onInterface) {
      int modifiers = makePublicNonFinal(meth.getModifiers()) | 64;
      if (onInterface) {
         modifiers |= 1024;
      }

      ResolvedMemberImpl rmi = new ResolvedMemberImpl(Member.METHOD, meth.getDeclaringType(), modifiers, meth.getReturnType(), NameMangler.interMethod(meth.getModifiers(), aspectType, meth.getDeclaringType(), meth.getName()), meth.getParameterTypes(), meth.getExceptions());
      rmi.setTypeVariables(meth.getTypeVariables());
      return rmi;
   }

   public static ResolvedMember bridgerToInterMethod(ResolvedMember meth, UnresolvedType aspectType) {
      int modifiers = makePublicNonFinal(meth.getModifiers());
      ResolvedMemberImpl rmi = new ResolvedMemberImpl(Member.METHOD, aspectType, modifiers, meth.getReturnType(), NameMangler.interMethod(meth.getModifiers(), aspectType, meth.getDeclaringType(), meth.getName()), meth.getParameterTypes(), meth.getExceptions());
      rmi.setTypeVariables(meth.getTypeVariables());
      return rmi;
   }

   public static ResolvedMember interMethodDispatcher(ResolvedMember meth, UnresolvedType aspectType) {
      UnresolvedType[] paramTypes = meth.getParameterTypes();
      if (!Modifier.isStatic(meth.getModifiers())) {
         paramTypes = UnresolvedType.insert(meth.getDeclaringType(), paramTypes);
      }

      ResolvedMemberImpl rmi = new ResolvedMemberImpl(Member.METHOD, aspectType, 9, meth.getReturnType(), NameMangler.interMethodDispatcher(aspectType, meth.getDeclaringType(), meth.getName()), paramTypes, meth.getExceptions());
      rmi.setParameterNames(meth.getParameterNames());
      rmi.setTypeVariables(meth.getTypeVariables());
      return rmi;
   }

   public static ResolvedMember interMethodBody(ResolvedMember meth, UnresolvedType aspectType) {
      UnresolvedType[] paramTypes = meth.getParameterTypes();
      if (!Modifier.isStatic(meth.getModifiers())) {
         paramTypes = UnresolvedType.insert(meth.getDeclaringType(), paramTypes);
      }

      int modifiers = 9;
      if (Modifier.isStrict(meth.getModifiers())) {
         modifiers |= 2048;
      }

      ResolvedMemberImpl rmi = new ResolvedMemberImpl(Member.METHOD, aspectType, modifiers, meth.getReturnType(), NameMangler.interMethodBody(aspectType, meth.getDeclaringType(), meth.getName()), paramTypes, meth.getExceptions());
      rmi.setParameterNames(meth.getParameterNames());
      rmi.setTypeVariables(meth.getTypeVariables());
      return rmi;
   }

   private static ResolvedMember addCookieTo(ResolvedMember ret, UnresolvedType aspectType) {
      UnresolvedType[] params = ret.getParameterTypes();
      UnresolvedType[] freshParams = UnresolvedType.add(params, aspectType);
      return new ResolvedMemberImpl(ret.getKind(), ret.getDeclaringType(), ret.getModifiers(), ret.getReturnType(), ret.getName(), freshParams, ret.getExceptions());
   }

   public static ResolvedMember toObjectConversionMethod(UnresolvedType fromType) {
      if (fromType.isPrimitiveType()) {
         String name = fromType.toString() + "Object";
         return new ResolvedMemberImpl(Member.METHOD, CONVERSIONS_TYPE, 9, UnresolvedType.OBJECT, name, new UnresolvedType[]{fromType}, UnresolvedType.NONE);
      } else {
         return null;
      }
   }

   public static Member interfaceConstructor(ResolvedType resolvedTypeX) {
      ResolvedType declaringType = resolvedTypeX;
      if (resolvedTypeX.isRawType()) {
         declaringType = resolvedTypeX.getGenericType();
      }

      return new ResolvedMemberImpl(Member.CONSTRUCTOR, (UnresolvedType)declaringType, 1, "<init>", "()V");
   }
}
