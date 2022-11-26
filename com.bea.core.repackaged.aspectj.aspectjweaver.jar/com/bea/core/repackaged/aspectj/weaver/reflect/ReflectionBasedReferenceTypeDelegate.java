package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.weaver.AjAttribute;
import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.AnnotationTargetKind;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ReferenceTypeDelegate;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.SourceContextImpl;
import com.bea.core.repackaged.aspectj.weaver.TypeVariable;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.WeakClassLoaderReference;
import com.bea.core.repackaged.aspectj.weaver.WeaverStateInfo;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerClause;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Collections;

public class ReflectionBasedReferenceTypeDelegate implements ReferenceTypeDelegate {
   private static final ClassLoader bootClassLoader = new URLClassLoader(new URL[0]);
   protected Class myClass = null;
   protected WeakClassLoaderReference classLoaderReference = null;
   protected World world;
   private ReferenceType resolvedType;
   private ResolvedMember[] fields = null;
   private ResolvedMember[] methods = null;
   private ResolvedType[] interfaces = null;

   public ReflectionBasedReferenceTypeDelegate(Class forClass, ClassLoader aClassLoader, World inWorld, ReferenceType resolvedType) {
      this.initialize(resolvedType, forClass, aClassLoader, inWorld);
   }

   public ReflectionBasedReferenceTypeDelegate() {
   }

   public void initialize(ReferenceType aType, Class aClass, ClassLoader aClassLoader, World aWorld) {
      this.myClass = aClass;
      this.resolvedType = aType;
      this.world = aWorld;
      this.classLoaderReference = new WeakClassLoaderReference(aClassLoader != null ? aClassLoader : bootClassLoader);
   }

   public Class getClazz() {
      return this.myClass;
   }

   protected Class getBaseClass() {
      return this.myClass;
   }

   protected World getWorld() {
      return this.world;
   }

   public ReferenceType buildGenericType() {
      throw new UnsupportedOperationException("Shouldn't be asking for generic type at 1.4 source level or lower");
   }

   public boolean isAspect() {
      return false;
   }

   public boolean isAnnotationStyleAspect() {
      return false;
   }

   public boolean isInterface() {
      return this.myClass.isInterface();
   }

   public boolean isEnum() {
      return false;
   }

   public boolean isAnnotationWithRuntimeRetention() {
      return false;
   }

   public boolean isAnnotation() {
      return false;
   }

   public String getRetentionPolicy() {
      return null;
   }

   public boolean canAnnotationTargetType() {
      return false;
   }

   public AnnotationTargetKind[] getAnnotationTargetKinds() {
      return null;
   }

   public boolean isClass() {
      return !this.myClass.isInterface() && !this.myClass.isPrimitive() && !this.myClass.isArray();
   }

   public boolean isGeneric() {
      return false;
   }

   public boolean isAnonymous() {
      return this.myClass.isAnonymousClass();
   }

   public boolean isNested() {
      return this.myClass.isMemberClass();
   }

   public ResolvedType getOuterClass() {
      return ReflectionBasedReferenceTypeDelegateFactory.resolveTypeInWorld(this.myClass.getEnclosingClass(), this.world);
   }

   public boolean isExposedToWeaver() {
      return false;
   }

   public boolean hasAnnotation(UnresolvedType ofType) {
      return false;
   }

   public AnnotationAJ[] getAnnotations() {
      return AnnotationAJ.EMPTY_ARRAY;
   }

   public boolean hasAnnotations() {
      return false;
   }

   public ResolvedType[] getAnnotationTypes() {
      return new ResolvedType[0];
   }

   public ResolvedMember[] getDeclaredFields() {
      if (this.fields == null) {
         Field[] reflectFields = this.myClass.getDeclaredFields();
         ResolvedMember[] rFields = new ResolvedMember[reflectFields.length];

         for(int i = 0; i < reflectFields.length; ++i) {
            rFields[i] = ReflectionBasedReferenceTypeDelegateFactory.createResolvedMember(reflectFields[i], this.world);
         }

         this.fields = rFields;
      }

      return this.fields;
   }

   public ResolvedType[] getDeclaredInterfaces() {
      if (this.interfaces == null) {
         Class[] reflectInterfaces = this.myClass.getInterfaces();
         ResolvedType[] rInterfaces = new ResolvedType[reflectInterfaces.length];

         for(int i = 0; i < reflectInterfaces.length; ++i) {
            rInterfaces[i] = ReflectionBasedReferenceTypeDelegateFactory.resolveTypeInWorld(reflectInterfaces[i], this.world);
         }

         this.interfaces = rInterfaces;
      }

      return this.interfaces;
   }

   public boolean isCacheable() {
      return true;
   }

   public ResolvedMember[] getDeclaredMethods() {
      if (this.methods == null) {
         Method[] reflectMethods = this.myClass.getDeclaredMethods();
         Constructor[] reflectCons = this.myClass.getDeclaredConstructors();
         ResolvedMember[] rMethods = new ResolvedMember[reflectMethods.length + reflectCons.length];

         int i;
         for(i = 0; i < reflectMethods.length; ++i) {
            rMethods[i] = ReflectionBasedReferenceTypeDelegateFactory.createResolvedMember(reflectMethods[i], this.world);
         }

         for(i = 0; i < reflectCons.length; ++i) {
            rMethods[i + reflectMethods.length] = ReflectionBasedReferenceTypeDelegateFactory.createResolvedMember(reflectCons[i], this.world);
         }

         this.methods = rMethods;
      }

      return this.methods;
   }

   public ResolvedMember[] getDeclaredPointcuts() {
      return new ResolvedMember[0];
   }

   public TypeVariable[] getTypeVariables() {
      return new TypeVariable[0];
   }

   public PerClause getPerClause() {
      return null;
   }

   public Collection getDeclares() {
      return Collections.EMPTY_SET;
   }

   public Collection getTypeMungers() {
      return Collections.EMPTY_SET;
   }

   public Collection getPrivilegedAccesses() {
      return Collections.EMPTY_SET;
   }

   public int getModifiers() {
      return this.myClass.getModifiers();
   }

   public ResolvedType getSuperclass() {
      if (this.myClass.getSuperclass() == null) {
         return this.myClass == Object.class ? null : this.world.resolve(UnresolvedType.OBJECT);
      } else {
         return ReflectionBasedReferenceTypeDelegateFactory.resolveTypeInWorld(this.myClass.getSuperclass(), this.world);
      }
   }

   public WeaverStateInfo getWeaverState() {
      return null;
   }

   public ReferenceType getResolvedTypeX() {
      return this.resolvedType;
   }

   public boolean doesNotExposeShadowMungers() {
      return false;
   }

   public String getDeclaredGenericSignature() {
      return null;
   }

   public ReflectionBasedResolvedMemberImpl createResolvedMemberFor(Member aMember) {
      return null;
   }

   public String getSourcefilename() {
      return this.resolvedType.getName() + ".class";
   }

   public ISourceContext getSourceContext() {
      return SourceContextImpl.UNKNOWN_SOURCE_CONTEXT;
   }

   public boolean copySourceContext() {
      return true;
   }

   public int getCompilerVersion() {
      return AjAttribute.WeaverVersionInfo.getCurrentWeaverMajorVersion();
   }

   public void ensureConsistent() {
   }

   public boolean isWeavable() {
      return false;
   }

   public boolean hasBeenWoven() {
      return false;
   }
}
