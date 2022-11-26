package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.weaver.patterns.PerClause;
import java.util.Collection;

public interface ReferenceTypeDelegate {
   boolean isAspect();

   boolean isAnnotationStyleAspect();

   boolean isInterface();

   boolean isEnum();

   boolean isAnnotation();

   String getRetentionPolicy();

   boolean canAnnotationTargetType();

   AnnotationTargetKind[] getAnnotationTargetKinds();

   boolean isAnnotationWithRuntimeRetention();

   boolean isClass();

   boolean isGeneric();

   boolean isAnonymous();

   boolean isNested();

   boolean hasAnnotation(UnresolvedType var1);

   AnnotationAJ[] getAnnotations();

   ResolvedType[] getAnnotationTypes();

   ResolvedMember[] getDeclaredFields();

   ResolvedType[] getDeclaredInterfaces();

   ResolvedMember[] getDeclaredMethods();

   ResolvedMember[] getDeclaredPointcuts();

   TypeVariable[] getTypeVariables();

   int getModifiers();

   PerClause getPerClause();

   Collection getDeclares();

   Collection getTypeMungers();

   Collection getPrivilegedAccesses();

   ResolvedType getSuperclass();

   WeaverStateInfo getWeaverState();

   ReferenceType getResolvedTypeX();

   boolean isExposedToWeaver();

   boolean doesNotExposeShadowMungers();

   ISourceContext getSourceContext();

   String getSourcefilename();

   String getDeclaredGenericSignature();

   ResolvedType getOuterClass();

   boolean copySourceContext();

   boolean isCacheable();

   int getCompilerVersion();

   void ensureConsistent();

   boolean isWeavable();

   boolean hasBeenWoven();

   boolean hasAnnotations();
}
