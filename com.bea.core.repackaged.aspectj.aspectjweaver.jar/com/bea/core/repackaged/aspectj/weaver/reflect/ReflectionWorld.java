package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.bridge.AbortException;
import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.util.LangUtil;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.IWeavingSupport;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ReferenceTypeDelegate;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.WeakClassLoaderReference;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.util.HashMap;
import java.util.Map;

public class ReflectionWorld extends World implements IReflectionWorld {
   private WeakClassLoaderReference classLoaderReference;
   private AnnotationFinder annotationFinder;
   private boolean mustUseOneFourDelegates;
   private Map inProgressResolutionClasses;

   private ReflectionWorld() {
      this.mustUseOneFourDelegates = false;
      this.inProgressResolutionClasses = new HashMap();
   }

   public ReflectionWorld(ClassLoader aClassLoader) {
      this.mustUseOneFourDelegates = false;
      this.inProgressResolutionClasses = new HashMap();
      this.setMessageHandler(new ExceptionBasedMessageHandler());
      this.setBehaveInJava5Way(LangUtil.is15VMOrGreater());
      this.classLoaderReference = new WeakClassLoaderReference(aClassLoader);
      this.annotationFinder = makeAnnotationFinderIfAny(this.classLoaderReference.getClassLoader(), this);
   }

   public ReflectionWorld(boolean forceUseOf14Delegates, ClassLoader aClassLoader) {
      this(aClassLoader);
      this.mustUseOneFourDelegates = forceUseOf14Delegates;
      if (forceUseOf14Delegates) {
         this.setBehaveInJava5Way(false);
      }

   }

   public static AnnotationFinder makeAnnotationFinderIfAny(ClassLoader loader, World world) {
      AnnotationFinder annotationFinder = null;

      try {
         if (LangUtil.is15VMOrGreater()) {
            Class java15AnnotationFinder = Class.forName("com.bea.core.repackaged.aspectj.weaver.reflect.Java15AnnotationFinder");
            annotationFinder = (AnnotationFinder)java15AnnotationFinder.newInstance();
            annotationFinder.setClassLoader(loader);
            annotationFinder.setWorld(world);
         }
      } catch (ClassNotFoundException var4) {
      } catch (IllegalAccessException var5) {
         throw new BCException("AspectJ internal error", var5);
      } catch (InstantiationException var6) {
         throw new BCException("AspectJ internal error", var6);
      }

      return annotationFinder;
   }

   public ClassLoader getClassLoader() {
      return this.classLoaderReference.getClassLoader();
   }

   public AnnotationFinder getAnnotationFinder() {
      return this.annotationFinder;
   }

   public ResolvedType resolve(Class aClass) {
      return resolve(this, aClass);
   }

   public static ResolvedType resolve(World world, Class aClass) {
      String className = aClass.getName();
      return aClass.isArray() ? world.resolve(UnresolvedType.forSignature(className.replace('.', '/'))) : world.resolve(className);
   }

   public ResolvedType resolveUsingClass(Class clazz) {
      String signature = UnresolvedType.forName(clazz.getName()).getSignature();

      ResolvedType var3;
      try {
         this.inProgressResolutionClasses.put(signature, clazz);
         var3 = this.resolve(clazz.getName());
      } finally {
         this.inProgressResolutionClasses.remove(signature);
      }

      return var3;
   }

   protected ReferenceTypeDelegate resolveDelegate(ReferenceType ty) {
      ReflectionBasedReferenceTypeDelegate result;
      if (this.mustUseOneFourDelegates) {
         result = ReflectionBasedReferenceTypeDelegateFactory.create14Delegate(ty, this, this.classLoaderReference.getClassLoader());
      } else {
         result = ReflectionBasedReferenceTypeDelegateFactory.createDelegate(ty, this, (ClassLoader)this.classLoaderReference.getClassLoader());
      }

      if (result == null && this.inProgressResolutionClasses.size() != 0) {
         Class clazz = (Class)this.inProgressResolutionClasses.get(ty.getSignature());
         if (clazz != null) {
            result = ReflectionBasedReferenceTypeDelegateFactory.createDelegate(ty, this, (Class)clazz);
         }
      }

      return result;
   }

   public IWeavingSupport getWeavingSupport() {
      return null;
   }

   public boolean isLoadtimeWeaving() {
      return true;
   }

   private static class ExceptionBasedMessageHandler implements IMessageHandler {
      private ExceptionBasedMessageHandler() {
      }

      public boolean handleMessage(IMessage message) throws AbortException {
         throw new ReflectionWorldException(message.toString());
      }

      public boolean isIgnoring(IMessage.Kind kind) {
         return kind == IMessage.INFO;
      }

      public void dontIgnore(IMessage.Kind kind) {
      }

      public void ignore(IMessage.Kind kind) {
      }

      // $FF: synthetic method
      ExceptionBasedMessageHandler(Object x0) {
         this();
      }
   }

   public static class ReflectionWorldException extends RuntimeException {
      private static final long serialVersionUID = -3432261918302793005L;

      public ReflectionWorldException(String message) {
         super(message);
      }
   }
}
