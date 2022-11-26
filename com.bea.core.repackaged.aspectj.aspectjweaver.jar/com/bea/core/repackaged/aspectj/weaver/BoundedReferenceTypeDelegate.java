package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.weaver.patterns.PerClause;
import java.util.Collection;
import java.util.Collections;

class BoundedReferenceTypeDelegate extends AbstractReferenceTypeDelegate {
   public BoundedReferenceTypeDelegate(ReferenceType backing) {
      super(backing, false);
   }

   public boolean isAspect() {
      return this.resolvedTypeX.isAspect();
   }

   public boolean isAnnotationStyleAspect() {
      return this.resolvedTypeX.isAnnotationStyleAspect();
   }

   public boolean isInterface() {
      return this.resolvedTypeX.isInterface();
   }

   public boolean isEnum() {
      return this.resolvedTypeX.isEnum();
   }

   public boolean isAnnotation() {
      return this.resolvedTypeX.isAnnotation();
   }

   public boolean isAnnotationWithRuntimeRetention() {
      return this.resolvedTypeX.isAnnotationWithRuntimeRetention();
   }

   public boolean isAnonymous() {
      return this.resolvedTypeX.isAnonymous();
   }

   public boolean isNested() {
      return this.resolvedTypeX.isNested();
   }

   public ResolvedType getOuterClass() {
      return this.resolvedTypeX.getOuterClass();
   }

   public String getRetentionPolicy() {
      return this.resolvedTypeX.getRetentionPolicy();
   }

   public boolean canAnnotationTargetType() {
      return this.resolvedTypeX.canAnnotationTargetType();
   }

   public AnnotationTargetKind[] getAnnotationTargetKinds() {
      return this.resolvedTypeX.getAnnotationTargetKinds();
   }

   public boolean isGeneric() {
      return this.resolvedTypeX.isGenericType();
   }

   public String getDeclaredGenericSignature() {
      return this.resolvedTypeX.getDeclaredGenericSignature();
   }

   public boolean hasAnnotation(UnresolvedType ofType) {
      return this.resolvedTypeX.hasAnnotation(ofType);
   }

   public AnnotationAJ[] getAnnotations() {
      return this.resolvedTypeX.getAnnotations();
   }

   public boolean hasAnnotations() {
      return this.resolvedTypeX.hasAnnotations();
   }

   public ResolvedType[] getAnnotationTypes() {
      return this.resolvedTypeX.getAnnotationTypes();
   }

   public ResolvedMember[] getDeclaredFields() {
      return this.resolvedTypeX.getDeclaredFields();
   }

   public ResolvedType[] getDeclaredInterfaces() {
      return this.resolvedTypeX.getDeclaredInterfaces();
   }

   public ResolvedMember[] getDeclaredMethods() {
      return this.resolvedTypeX.getDeclaredMethods();
   }

   public ResolvedMember[] getDeclaredPointcuts() {
      return this.resolvedTypeX.getDeclaredPointcuts();
   }

   public PerClause getPerClause() {
      return this.resolvedTypeX.getPerClause();
   }

   public Collection getDeclares() {
      return this.resolvedTypeX.getDeclares();
   }

   public Collection getTypeMungers() {
      return this.resolvedTypeX.getTypeMungers();
   }

   public Collection getPrivilegedAccesses() {
      return Collections.emptyList();
   }

   public int getModifiers() {
      return this.resolvedTypeX.getModifiers();
   }

   public ResolvedType getSuperclass() {
      return this.resolvedTypeX.getSuperclass();
   }

   public WeaverStateInfo getWeaverState() {
      return null;
   }

   public TypeVariable[] getTypeVariables() {
      return this.resolvedTypeX.getTypeVariables();
   }
}
