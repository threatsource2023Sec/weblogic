package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.MemberKind;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMemberImpl;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import java.lang.reflect.Member;
import java.util.Iterator;
import java.util.Set;

public class ReflectionBasedResolvedMemberImpl extends ResolvedMemberImpl {
   private AnnotationFinder annotationFinder = null;
   private GenericSignatureInformationProvider gsigInfoProvider = new Java14GenericSignatureInformationProvider();
   private Member reflectMember;

   public ReflectionBasedResolvedMemberImpl(MemberKind kind, UnresolvedType declaringType, int modifiers, UnresolvedType returnType, String name, UnresolvedType[] parameterTypes, Member reflectMember) {
      super(kind, declaringType, modifiers, returnType, name, parameterTypes);
      this.reflectMember = reflectMember;
   }

   public ReflectionBasedResolvedMemberImpl(MemberKind kind, UnresolvedType declaringType, int modifiers, UnresolvedType returnType, String name, UnresolvedType[] parameterTypes, UnresolvedType[] checkedExceptions, Member reflectMember) {
      super(kind, declaringType, modifiers, returnType, name, parameterTypes, checkedExceptions);
      this.reflectMember = reflectMember;
   }

   public ReflectionBasedResolvedMemberImpl(MemberKind kind, UnresolvedType declaringType, int modifiers, UnresolvedType returnType, String name, UnresolvedType[] parameterTypes, UnresolvedType[] checkedExceptions, ResolvedMember backingGenericMember, Member reflectMember) {
      super(kind, declaringType, modifiers, returnType, name, parameterTypes, checkedExceptions, backingGenericMember);
      this.reflectMember = reflectMember;
   }

   public ReflectionBasedResolvedMemberImpl(MemberKind kind, UnresolvedType declaringType, int modifiers, String name, String signature, Member reflectMember) {
      super(kind, declaringType, modifiers, name, signature);
      this.reflectMember = reflectMember;
   }

   public Member getMember() {
      return this.reflectMember;
   }

   public void setGenericSignatureInformationProvider(GenericSignatureInformationProvider gsigProvider) {
      this.gsigInfoProvider = gsigProvider;
   }

   public UnresolvedType[] getGenericParameterTypes() {
      return this.gsigInfoProvider.getGenericParameterTypes(this);
   }

   public UnresolvedType getGenericReturnType() {
      return this.gsigInfoProvider.getGenericReturnType(this);
   }

   public boolean isSynthetic() {
      return this.gsigInfoProvider.isSynthetic(this);
   }

   public boolean isVarargsMethod() {
      return this.gsigInfoProvider.isVarArgs(this);
   }

   public boolean isBridgeMethod() {
      return this.gsigInfoProvider.isBridge(this);
   }

   public void setAnnotationFinder(AnnotationFinder finder) {
      this.annotationFinder = finder;
   }

   public boolean hasAnnotation(UnresolvedType ofType) {
      this.unpackAnnotations();
      return super.hasAnnotation(ofType);
   }

   public boolean hasAnnotations() {
      this.unpackAnnotations();
      return super.hasAnnotations();
   }

   public ResolvedType[] getAnnotationTypes() {
      this.unpackAnnotations();
      return super.getAnnotationTypes();
   }

   public AnnotationAJ getAnnotationOfType(UnresolvedType ofType) {
      this.unpackAnnotations();
      if (this.annotationFinder != null && this.annotationTypes != null) {
         ResolvedType[] arr$ = this.annotationTypes;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            ResolvedType type = arr$[i$];
            if (type.getSignature().equals(ofType.getSignature())) {
               return this.annotationFinder.getAnnotationOfType(ofType, this.reflectMember);
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public String getAnnotationDefaultValue() {
      return this.annotationFinder == null ? null : this.annotationFinder.getAnnotationDefaultValue(this.reflectMember);
   }

   public ResolvedType[][] getParameterAnnotationTypes() {
      if (this.parameterAnnotationTypes == null && this.annotationFinder != null) {
         this.parameterAnnotationTypes = this.annotationFinder.getParameterAnnotationTypes(this.reflectMember);
      }

      return this.parameterAnnotationTypes;
   }

   private void unpackAnnotations() {
      if (this.annotationTypes == null && this.annotationFinder != null) {
         Set s = this.annotationFinder.getAnnotations(this.reflectMember);
         if (s.size() == 0) {
            this.annotationTypes = ResolvedType.EMPTY_ARRAY;
         } else {
            this.annotationTypes = new ResolvedType[s.size()];
            int i = 0;

            Object o;
            for(Iterator i$ = s.iterator(); i$.hasNext(); this.annotationTypes[i++] = (ResolvedType)o) {
               o = i$.next();
            }
         }
      }

   }
}
