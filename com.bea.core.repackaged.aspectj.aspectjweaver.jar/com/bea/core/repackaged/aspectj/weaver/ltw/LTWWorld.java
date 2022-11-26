package com.bea.core.repackaged.aspectj.weaver.ltw;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;
import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.util.LangUtil;
import com.bea.core.repackaged.aspectj.weaver.Dump;
import com.bea.core.repackaged.aspectj.weaver.ICrossReferenceHandler;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ReferenceTypeDelegate;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.bcel.BcelWorld;
import com.bea.core.repackaged.aspectj.weaver.loadtime.IWeavingContext;
import com.bea.core.repackaged.aspectj.weaver.reflect.AnnotationFinder;
import com.bea.core.repackaged.aspectj.weaver.reflect.IReflectionWorld;
import com.bea.core.repackaged.aspectj.weaver.reflect.ReflectionBasedReferenceTypeDelegateFactory;
import com.bea.core.repackaged.aspectj.weaver.reflect.ReflectionWorld;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LTWWorld extends BcelWorld implements IReflectionWorld {
   private AnnotationFinder annotationFinder;
   private IWeavingContext weavingContext;
   private String classLoaderString;
   private String classLoaderParentString;
   protected static final Class concurrentMapClass = null;
   private static final boolean ShareBootstrapTypes = false;
   protected static Map bootstrapTypes;
   private static final long serialVersionUID = 1L;
   private boolean typeCompletionInProgress = false;
   private List typesForCompletion = new ArrayList();

   public LTWWorld(ClassLoader loader, IWeavingContext weavingContext, IMessageHandler handler, ICrossReferenceHandler xrefHandler) {
      super(loader, handler, xrefHandler);
      this.weavingContext = weavingContext;
      this.classLoaderString = loader.toString();
      this.classLoaderParentString = loader.getParent() == null ? "<NullParent>" : loader.getParent().toString();
      this.setBehaveInJava5Way(LangUtil.is15VMOrGreater());
      this.annotationFinder = ReflectionWorld.makeAnnotationFinderIfAny(loader, this);
   }

   public ClassLoader getClassLoader() {
      return this.weavingContext.getClassLoader();
   }

   protected ReferenceTypeDelegate resolveDelegate(ReferenceType ty) {
      ReferenceTypeDelegate bootstrapLoaderDelegate = this.resolveIfBootstrapDelegate(ty);
      return bootstrapLoaderDelegate != null ? bootstrapLoaderDelegate : super.resolveDelegate(ty);
   }

   protected ReferenceTypeDelegate resolveIfBootstrapDelegate(ReferenceType ty) {
      return null;
   }

   private ReferenceTypeDelegate resolveReflectionTypeDelegate(ReferenceType ty, ClassLoader resolutionLoader) {
      ReferenceTypeDelegate res = ReflectionBasedReferenceTypeDelegateFactory.createDelegate(ty, this, (ClassLoader)resolutionLoader);
      return res;
   }

   public void loadedClass(Class clazz) {
   }

   public AnnotationFinder getAnnotationFinder() {
      return this.annotationFinder;
   }

   public ResolvedType resolve(Class aClass) {
      return ReflectionWorld.resolve(this, aClass);
   }

   private static Map makeConcurrentMap() {
      if (concurrentMapClass != null) {
         try {
            return (Map)concurrentMapClass.newInstance();
         } catch (InstantiationException var1) {
         } catch (IllegalAccessException var2) {
         }
      }

      return Collections.synchronizedMap(new HashMap());
   }

   private static Class makeConcurrentMapClass() {
      String[] betterChoices = new String[]{"java.util.concurrent.ConcurrentHashMap", "edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap", "EDU.oswego.cs.dl.util.concurrent.ConcurrentHashMap"};

      for(int i = 0; i < betterChoices.length; ++i) {
         try {
            return Class.forName(betterChoices[i]);
         } catch (ClassNotFoundException var3) {
         } catch (SecurityException var4) {
         }
      }

      return null;
   }

   public boolean isRunMinimalMemory() {
      return this.isRunMinimalMemorySet() ? super.isRunMinimalMemory() : false;
   }

   protected void completeBinaryType(ResolvedType ret) {
      if (this.isLocallyDefined(ret.getName())) {
         if (this.typeCompletionInProgress) {
            this.typesForCompletion.add(ret);
         } else {
            try {
               this.typeCompletionInProgress = true;
               this.completeHierarchyForType(ret);
            } finally {
               this.typeCompletionInProgress = false;
            }

            while(this.typesForCompletion.size() != 0) {
               ResolvedType rt = (ResolvedType)this.typesForCompletion.get(0);
               this.completeHierarchyForType(rt);
               this.typesForCompletion.remove(0);
            }
         }
      } else if (!ret.needsModifiableDelegate()) {
         this.completeNonLocalType(ret);
      }

   }

   private void completeHierarchyForType(ResolvedType ret) {
      this.getLint().typeNotExposedToWeaver.setSuppressed(true);
      this.weaveInterTypeDeclarations(ret);
      this.getLint().typeNotExposedToWeaver.setSuppressed(false);
   }

   protected boolean needsCompletion() {
      return true;
   }

   public boolean isLocallyDefined(String classname) {
      return this.weavingContext.isLocallyDefined(classname);
   }

   protected ResolvedType completeNonLocalType(ResolvedType ret) {
      if (ret.isMissing()) {
         return ret;
      } else {
         ResolvedType toResolve = ret;
         if (ret.isParameterizedType() || ret.isGenericType()) {
            toResolve = ret.getGenericType();
         }

         ReferenceTypeDelegate rtd = this.resolveReflectionTypeDelegate((ReferenceType)toResolve, this.getClassLoader());
         ((ReferenceType)ret).setDelegate(rtd);
         return ret;
      }
   }

   public void storeClass(JavaClass clazz) {
      this.ensureRepositorySetup();
      this.delegate.storeClass(clazz);
   }

   public void accept(Dump.IVisitor visitor) {
      visitor.visitObject("Class loader:");
      visitor.visitObject(this.classLoaderString);
      visitor.visitObject("Class loader parent:");
      visitor.visitObject(this.classLoaderParentString);
      super.accept(visitor);
   }

   public boolean isLoadtimeWeaving() {
      return true;
   }
}
