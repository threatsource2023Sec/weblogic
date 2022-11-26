package com.bea.core.repackaged.aspectj.weaver;

import java.lang.reflect.Modifier;

public class NameMangler {
   public static final String PREFIX = "ajc$";
   public static final char[] PREFIX_CHARS = "ajc$".toCharArray();
   public static final String ITD_PREFIX = "ajc$interType$";
   public static final String CFLOW_STACK_TYPE = "com.bea.core.repackaged.aspectj.runtime.internal.CFlowStack";
   public static final String CFLOW_COUNTER_TYPE = "com.bea.core.repackaged.aspectj.runtime.internal.CFlowCounter";
   public static final UnresolvedType CFLOW_STACK_UNRESOLVEDTYPE = UnresolvedType.forSignature("Lorg/aspectj/runtime/internal/CFlowStack;");
   public static final UnresolvedType CFLOW_COUNTER_UNRESOLVEDTYPE = UnresolvedType.forSignature("Lorg/aspectj/runtime/internal/CFlowCounter;");
   public static final String SOFT_EXCEPTION_TYPE = "com.bea.core.repackaged.aspectj.lang.SoftException";
   public static final String PERSINGLETON_FIELD_NAME = "ajc$perSingletonInstance";
   public static final String PERCFLOW_FIELD_NAME = "ajc$perCflowStack";
   public static final String PERCFLOW_PUSH_METHOD = "ajc$perCflowPush";
   public static final String PEROBJECT_BIND_METHOD = "ajc$perObjectBind";
   public static final String PERTYPEWITHIN_GETINSTANCE_METHOD = "ajc$getInstance";
   public static final String PERTYPEWITHIN_CREATEASPECTINSTANCE_METHOD = "ajc$createAspectInstance";
   public static final String PERTYPEWITHIN_WITHINTYPEFIELD = "ajc$withinType";
   public static final String PERTYPEWITHIN_GETWITHINTYPENAME_METHOD = "getWithinTypeName";
   public static final String AJC_PRE_CLINIT_NAME = "ajc$preClinit";
   public static final String AJC_POST_CLINIT_NAME = "ajc$postClinit";
   public static final String INITFAILURECAUSE_FIELD_NAME = "ajc$initFailureCause";
   public static final String ANNOTATION_CACHE_FIELD_NAME = "ajc$anno$";

   public static boolean isSyntheticMethod(String methodName, boolean declaredInAspect) {
      if (methodName.startsWith("ajc$")) {
         if (!methodName.startsWith("ajc$before") && !methodName.startsWith("ajc$after")) {
            if (methodName.startsWith("ajc$around")) {
               return methodName.endsWith("proceed");
            } else {
               return !methodName.startsWith("ajc$interMethod$");
            }
         } else {
            return false;
         }
      } else {
         return methodName.indexOf("_aroundBody") != -1;
      }
   }

   public static String perObjectInterfaceGet(UnresolvedType aspectType) {
      return makeName(aspectType.getNameAsIdentifier(), "perObjectGet");
   }

   public static String perObjectInterfaceSet(UnresolvedType aspectType) {
      return makeName(aspectType.getNameAsIdentifier(), "perObjectSet");
   }

   public static String perObjectInterfaceField(UnresolvedType aspectType) {
      return makeName(aspectType.getNameAsIdentifier(), "perObjectField");
   }

   public static String perTypeWithinFieldForTarget(UnresolvedType aspectType) {
      return makeName(aspectType.getNameAsIdentifier(), "ptwAspectInstance");
   }

   public static String perTypeWithinLocalAspectOf(UnresolvedType aspectType) {
      return makeName(aspectType.getNameAsIdentifier(), "localAspectOf");
   }

   public static String itdAtDeclareParentsField(UnresolvedType aspectType, UnresolvedType itdType) {
      return makeName("instance", aspectType.getNameAsIdentifier(), itdType.getNameAsIdentifier());
   }

   public static String privilegedAccessMethodForMethod(String name, UnresolvedType objectType, UnresolvedType aspectType) {
      return makeName("privMethod", aspectType.getNameAsIdentifier(), objectType.getNameAsIdentifier(), name);
   }

   public static String privilegedAccessMethodForFieldGet(String name, UnresolvedType objectType, UnresolvedType aspectType) {
      StringBuilder nameBuilder = new StringBuilder();
      nameBuilder.append(makeName("privFieldGet", aspectType.getNameAsIdentifier(), objectType.getNameAsIdentifier(), name));
      return nameBuilder.toString();
   }

   public static String privilegedAccessMethodForFieldSet(String name, UnresolvedType objectType, UnresolvedType aspectType) {
      return makeName("privFieldSet", aspectType.getNameAsIdentifier(), objectType.getNameAsIdentifier(), name);
   }

   public static String inlineAccessMethodForMethod(String name, UnresolvedType objectType, UnresolvedType aspectType) {
      return makeName("inlineAccessMethod", aspectType.getNameAsIdentifier(), objectType.getNameAsIdentifier(), name);
   }

   public static String inlineAccessMethodForFieldGet(String name, UnresolvedType objectType, UnresolvedType aspectType) {
      return makeName("inlineAccessFieldGet", aspectType.getNameAsIdentifier(), objectType.getNameAsIdentifier(), name);
   }

   public static String inlineAccessMethodForFieldSet(String name, UnresolvedType objectType, UnresolvedType aspectType) {
      return makeName("inlineAccessFieldSet", aspectType.getNameAsIdentifier(), objectType.getNameAsIdentifier(), name);
   }

   public static String adviceName(String nameAsIdentifier, AdviceKind kind, int adviceSeqNumber, int pcdHash) {
      String newname = makeName(kind.getName(), nameAsIdentifier, Integer.toString(adviceSeqNumber), Integer.toHexString(pcdHash));
      return newname;
   }

   public static String interFieldInterfaceField(UnresolvedType aspectType, UnresolvedType interfaceType, String name) {
      return makeName("interField", aspectType.getNameAsIdentifier(), interfaceType.getNameAsIdentifier(), name);
   }

   public static String interFieldInterfaceSetter(UnresolvedType aspectType, UnresolvedType interfaceType, String name) {
      return makeName("interFieldSet", aspectType.getNameAsIdentifier(), interfaceType.getNameAsIdentifier(), name);
   }

   public static String interFieldInterfaceGetter(UnresolvedType aspectType, UnresolvedType interfaceType, String name) {
      return makeName("interFieldGet", aspectType.getNameAsIdentifier(), interfaceType.getNameAsIdentifier(), name);
   }

   public static String interFieldSetDispatcher(UnresolvedType aspectType, UnresolvedType onType, String name) {
      return makeName("interFieldSetDispatch", aspectType.getNameAsIdentifier(), onType.getNameAsIdentifier(), name);
   }

   public static String interFieldGetDispatcher(UnresolvedType aspectType, UnresolvedType onType, String name) {
      return makeName("interFieldGetDispatch", aspectType.getNameAsIdentifier(), onType.getNameAsIdentifier(), name);
   }

   public static String interFieldClassField(int modifiers, UnresolvedType aspectType, UnresolvedType classType, String name) {
      return Modifier.isPublic(modifiers) ? name : makeName("interField", makeVisibilityName(modifiers, aspectType), name);
   }

   public static String interFieldInitializer(UnresolvedType aspectType, UnresolvedType classType, String name) {
      return makeName("interFieldInit", aspectType.getNameAsIdentifier(), classType.getNameAsIdentifier(), name);
   }

   public static String interMethod(int modifiers, UnresolvedType aspectType, UnresolvedType classType, String name) {
      return Modifier.isPublic(modifiers) ? name : makeName("interMethodDispatch2", makeVisibilityName(modifiers, aspectType), name);
   }

   public static String interMethodDispatcher(UnresolvedType aspectType, UnresolvedType classType, String name) {
      return makeName("interMethodDispatch1", aspectType.getNameAsIdentifier(), classType.getNameAsIdentifier(), name);
   }

   public static String interMethodBody(UnresolvedType aspectType, UnresolvedType classType, String name) {
      return makeName("interMethod", aspectType.getNameAsIdentifier(), classType.getNameAsIdentifier(), name);
   }

   public static String preIntroducedConstructor(UnresolvedType aspectType, UnresolvedType targetType) {
      return makeName("preInterConstructor", aspectType.getNameAsIdentifier(), targetType.getNameAsIdentifier());
   }

   public static String postIntroducedConstructor(UnresolvedType aspectType, UnresolvedType targetType) {
      return makeName("postInterConstructor", aspectType.getNameAsIdentifier(), targetType.getNameAsIdentifier());
   }

   public static String superDispatchMethod(UnresolvedType classType, String name) {
      return makeName("superDispatch", classType.getNameAsIdentifier(), name);
   }

   public static String protectedDispatchMethod(UnresolvedType classType, String name) {
      return makeName("protectedDispatch", classType.getNameAsIdentifier(), name);
   }

   private static String makeVisibilityName(int modifiers, UnresolvedType aspectType) {
      if (Modifier.isPrivate(modifiers)) {
         return aspectType.getOutermostType().getNameAsIdentifier();
      } else if (Modifier.isProtected(modifiers)) {
         throw new RuntimeException("protected inter-types not allowed");
      } else {
         return Modifier.isPublic(modifiers) ? "" : aspectType.getPackageNameAsIdentifier();
      }
   }

   private static String makeName(String s1, String s2) {
      return "ajc$" + s1 + "$" + s2;
   }

   public static String makeName(String s1, String s2, String s3) {
      return "ajc$" + s1 + "$" + s2 + "$" + s3;
   }

   public static String makeName(String s1, String s2, String s3, String s4) {
      return "ajc$" + s1 + "$" + s2 + "$" + s3 + "$" + s4;
   }

   public static String cflowStack(CrosscuttingMembers xcut) {
      return makeName("cflowStack", Integer.toHexString(xcut.getCflowEntries().size()));
   }

   public static String cflowCounter(CrosscuttingMembers xcut) {
      return makeName("cflowCounter", Integer.toHexString(xcut.getCflowEntries().size()));
   }

   public static String makeClosureClassName(UnresolvedType enclosingType, String suffix) {
      return enclosingType.getName() + "$AjcClosure" + suffix;
   }

   public static String aroundShadowMethodName(Member shadowSig, String suffixTag) {
      StringBuffer ret = new StringBuffer();
      ret.append(getExtractableName(shadowSig)).append("_aroundBody").append(suffixTag);
      return ret.toString();
   }

   public static String aroundAdviceMethodName(Member shadowSig, String suffixTag) {
      StringBuffer ret = new StringBuffer();
      ret.append(getExtractableName(shadowSig)).append("_aroundBody").append(suffixTag).append("$advice");
      return ret.toString();
   }

   public static String getExtractableName(Member shadowSignature) {
      String name = shadowSignature.getName();
      MemberKind kind = shadowSignature.getKind();
      if (kind == Member.CONSTRUCTOR) {
         return "init$";
      } else {
         return kind == Member.STATIC_INITIALIZATION ? "clinit$" : name;
      }
   }

   public static String proceedMethodName(String adviceMethodName) {
      return adviceMethodName + "proceed";
   }
}
