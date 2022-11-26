package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.util.LangUtil;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMemberImpl;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class ReflectionBasedReferenceTypeDelegateFactory {
   public static ReflectionBasedReferenceTypeDelegate createDelegate(ReferenceType forReferenceType, World inWorld, ClassLoader usingClassLoader) {
      try {
         Class c = Class.forName(forReferenceType.getName(), false, usingClassLoader);
         if (LangUtil.is15VMOrGreater()) {
            ReflectionBasedReferenceTypeDelegate rbrtd = create15Delegate(forReferenceType, c, usingClassLoader, inWorld);
            if (rbrtd != null) {
               return rbrtd;
            }
         }

         return new ReflectionBasedReferenceTypeDelegate(c, usingClassLoader, inWorld, forReferenceType);
      } catch (ClassNotFoundException var5) {
         return null;
      }
   }

   public static ReflectionBasedReferenceTypeDelegate createDelegate(ReferenceType forReferenceType, World inWorld, Class clazz) {
      if (LangUtil.is15VMOrGreater()) {
         ReflectionBasedReferenceTypeDelegate rbrtd = create15Delegate(forReferenceType, clazz, clazz.getClassLoader(), inWorld);
         if (rbrtd != null) {
            return rbrtd;
         }
      }

      return new ReflectionBasedReferenceTypeDelegate(clazz, clazz.getClassLoader(), inWorld, forReferenceType);
   }

   public static ReflectionBasedReferenceTypeDelegate create14Delegate(ReferenceType forReferenceType, World inWorld, ClassLoader usingClassLoader) {
      try {
         Class c = Class.forName(forReferenceType.getName(), false, usingClassLoader);
         return new ReflectionBasedReferenceTypeDelegate(c, usingClassLoader, inWorld, forReferenceType);
      } catch (ClassNotFoundException var4) {
         return null;
      }
   }

   private static ReflectionBasedReferenceTypeDelegate create15Delegate(ReferenceType forReferenceType, Class forClass, ClassLoader usingClassLoader, World inWorld) {
      try {
         Class delegateClass = Class.forName("com.bea.core.repackaged.aspectj.weaver.reflect.Java15ReflectionBasedReferenceTypeDelegate");
         ReflectionBasedReferenceTypeDelegate ret = (ReflectionBasedReferenceTypeDelegate)delegateClass.newInstance();
         ret.initialize(forReferenceType, forClass, usingClassLoader, inWorld);
         return ret;
      } catch (ClassNotFoundException var6) {
         throw new IllegalStateException("Attempted to create Java 1.5 reflection based delegate but org.aspectj.weaver.reflect.Java15ReflectionBasedReferenceTypeDelegate was not found on classpath");
      } catch (InstantiationException var7) {
         throw new IllegalStateException("Attempted to create Java 1.5 reflection based delegate but InstantiationException: " + var7 + " occured");
      } catch (IllegalAccessException var8) {
         throw new IllegalStateException("Attempted to create Java 1.5 reflection based delegate but IllegalAccessException: " + var8 + " occured");
      }
   }

   private static GenericSignatureInformationProvider createGenericSignatureProvider(World inWorld) {
      if (LangUtil.is15VMOrGreater()) {
         try {
            Class providerClass = Class.forName("com.bea.core.repackaged.aspectj.weaver.reflect.Java15GenericSignatureInformationProvider");
            Constructor cons = providerClass.getConstructor(World.class);
            GenericSignatureInformationProvider ret = (GenericSignatureInformationProvider)cons.newInstance(inWorld);
            return ret;
         } catch (ClassNotFoundException var4) {
         } catch (NoSuchMethodException var5) {
            throw new IllegalStateException("Attempted to create Java 1.5 generic signature provider but: " + var5 + " occured");
         } catch (InstantiationException var6) {
            throw new IllegalStateException("Attempted to create Java 1.5 generic signature provider but: " + var6 + " occured");
         } catch (InvocationTargetException var7) {
            throw new IllegalStateException("Attempted to create Java 1.5 generic signature provider but: " + var7 + " occured");
         } catch (IllegalAccessException var8) {
            throw new IllegalStateException("Attempted to create Java 1.5 generic signature provider but: " + var8 + " occured");
         }
      }

      return new Java14GenericSignatureInformationProvider();
   }

   public static ResolvedMember createResolvedMember(Member reflectMember, World inWorld) {
      if (reflectMember instanceof Method) {
         return createResolvedMethod((Method)reflectMember, inWorld);
      } else {
         return reflectMember instanceof Constructor ? createResolvedConstructor((Constructor)reflectMember, inWorld) : createResolvedField((Field)reflectMember, inWorld);
      }
   }

   public static ResolvedMember createResolvedMethod(Method aMethod, World inWorld) {
      ReflectionBasedResolvedMemberImpl ret = new ReflectionBasedResolvedMemberImpl(com.bea.core.repackaged.aspectj.weaver.Member.METHOD, toResolvedType(aMethod.getDeclaringClass(), (IReflectionWorld)inWorld), aMethod.getModifiers(), toResolvedType(aMethod.getReturnType(), (IReflectionWorld)inWorld), aMethod.getName(), toResolvedTypeArray(aMethod.getParameterTypes(), inWorld), toResolvedTypeArray(aMethod.getExceptionTypes(), inWorld), aMethod);
      if (inWorld instanceof IReflectionWorld) {
         ret.setAnnotationFinder(((IReflectionWorld)inWorld).getAnnotationFinder());
      }

      ret.setGenericSignatureInformationProvider(createGenericSignatureProvider(inWorld));
      return ret;
   }

   public static ResolvedMember createResolvedAdviceMember(Method aMethod, World inWorld) {
      ReflectionBasedResolvedMemberImpl ret = new ReflectionBasedResolvedMemberImpl(com.bea.core.repackaged.aspectj.weaver.Member.ADVICE, toResolvedType(aMethod.getDeclaringClass(), (IReflectionWorld)inWorld), aMethod.getModifiers(), toResolvedType(aMethod.getReturnType(), (IReflectionWorld)inWorld), aMethod.getName(), toResolvedTypeArray(aMethod.getParameterTypes(), inWorld), toResolvedTypeArray(aMethod.getExceptionTypes(), inWorld), aMethod);
      if (inWorld instanceof IReflectionWorld) {
         ret.setAnnotationFinder(((IReflectionWorld)inWorld).getAnnotationFinder());
      }

      ret.setGenericSignatureInformationProvider(createGenericSignatureProvider(inWorld));
      return ret;
   }

   public static ResolvedMember createStaticInitMember(Class forType, World inWorld) {
      return new ResolvedMemberImpl(com.bea.core.repackaged.aspectj.weaver.Member.STATIC_INITIALIZATION, toResolvedType(forType, (IReflectionWorld)inWorld), 8, UnresolvedType.VOID, "<clinit>", new UnresolvedType[0], new UnresolvedType[0]);
   }

   public static ResolvedMember createResolvedConstructor(Constructor aConstructor, World inWorld) {
      ReflectionBasedResolvedMemberImpl ret = new ReflectionBasedResolvedMemberImpl(com.bea.core.repackaged.aspectj.weaver.Member.CONSTRUCTOR, toResolvedType(aConstructor.getDeclaringClass(), (IReflectionWorld)inWorld), aConstructor.getModifiers(), UnresolvedType.VOID, "<init>", toResolvedTypeArray(aConstructor.getParameterTypes(), inWorld), toResolvedTypeArray(aConstructor.getExceptionTypes(), inWorld), aConstructor);
      if (inWorld instanceof IReflectionWorld) {
         ret.setAnnotationFinder(((IReflectionWorld)inWorld).getAnnotationFinder());
      }

      ret.setGenericSignatureInformationProvider(createGenericSignatureProvider(inWorld));
      return ret;
   }

   public static ResolvedMember createResolvedField(Field aField, World inWorld) {
      ReflectionBasedResolvedMemberImpl ret = new ReflectionBasedResolvedMemberImpl(com.bea.core.repackaged.aspectj.weaver.Member.FIELD, toResolvedType(aField.getDeclaringClass(), (IReflectionWorld)inWorld), aField.getModifiers(), toResolvedType(aField.getType(), (IReflectionWorld)inWorld), aField.getName(), new UnresolvedType[0], aField);
      if (inWorld instanceof IReflectionWorld) {
         ret.setAnnotationFinder(((IReflectionWorld)inWorld).getAnnotationFinder());
      }

      ret.setGenericSignatureInformationProvider(createGenericSignatureProvider(inWorld));
      return ret;
   }

   public static ResolvedMember createHandlerMember(Class exceptionType, Class inType, World inWorld) {
      return new ResolvedMemberImpl(com.bea.core.repackaged.aspectj.weaver.Member.HANDLER, toResolvedType(inType, (IReflectionWorld)inWorld), 8, "<catch>", "(" + inWorld.resolve(exceptionType.getName()).getSignature() + ")V");
   }

   public static ResolvedType resolveTypeInWorld(Class aClass, World aWorld) {
      String className = aClass.getName();
      return aClass.isArray() ? aWorld.resolve(UnresolvedType.forSignature(className.replace('.', '/'))) : aWorld.resolve(className);
   }

   private static ResolvedType toResolvedType(Class aClass, IReflectionWorld aWorld) {
      return aWorld.resolve(aClass);
   }

   private static ResolvedType[] toResolvedTypeArray(Class[] classes, World inWorld) {
      ResolvedType[] ret = new ResolvedType[classes.length];

      for(int i = 0; i < ret.length; ++i) {
         ret[i] = ((IReflectionWorld)inWorld).resolve(classes[i]);
      }

      return ret;
   }
}
