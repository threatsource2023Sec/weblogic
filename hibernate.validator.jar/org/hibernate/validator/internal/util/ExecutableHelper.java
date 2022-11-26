package org.hibernate.validator.internal.util;

import com.fasterxml.classmate.AnnotationConfiguration;
import com.fasterxml.classmate.AnnotationOverrides;
import com.fasterxml.classmate.Filter;
import com.fasterxml.classmate.MemberResolver;
import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.ResolvedTypeWithMembers;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.classmate.members.RawMethod;
import com.fasterxml.classmate.members.ResolvedMethod;
import java.lang.annotation.ElementType;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetResolvedMemberMethods;

public final class ExecutableHelper {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final TypeResolver typeResolver;

   public ExecutableHelper(TypeResolutionHelper typeResolutionHelper) {
      this.typeResolver = typeResolutionHelper.getTypeResolver();
   }

   public boolean overrides(Method subTypeMethod, Method superTypeMethod) {
      Contracts.assertValueNotNull(subTypeMethod, "subTypeMethod");
      Contracts.assertValueNotNull(superTypeMethod, "superTypeMethod");
      if (subTypeMethod.equals(superTypeMethod)) {
         return false;
      } else if (!subTypeMethod.getName().equals(superTypeMethod.getName())) {
         return false;
      } else if (subTypeMethod.getParameterTypes().length != superTypeMethod.getParameterTypes().length) {
         return false;
      } else if (!superTypeMethod.getDeclaringClass().isAssignableFrom(subTypeMethod.getDeclaringClass())) {
         return false;
      } else if (!Modifier.isStatic(superTypeMethod.getModifiers()) && !Modifier.isStatic(subTypeMethod.getModifiers())) {
         if (subTypeMethod.isBridge()) {
            return false;
         } else if (Modifier.isPrivate(superTypeMethod.getModifiers())) {
            return false;
         } else {
            return !Modifier.isPublic(superTypeMethod.getModifiers()) && !Modifier.isProtected(superTypeMethod.getModifiers()) && !superTypeMethod.getDeclaringClass().getPackage().equals(subTypeMethod.getDeclaringClass().getPackage()) ? false : this.instanceMethodParametersResolveToSameTypes(subTypeMethod, superTypeMethod);
         }
      } else {
         return false;
      }
   }

   public static String getSimpleName(Executable executable) {
      return executable instanceof Constructor ? executable.getDeclaringClass().getSimpleName() : executable.getName();
   }

   public static String getSignature(Executable executable) {
      return getSignature(getSimpleName(executable), executable.getParameterTypes());
   }

   public static String getSignature(String name, Class[] parameterTypes) {
      return (String)Stream.of(parameterTypes).map((t) -> {
         return t.getName();
      }).collect(Collectors.joining(",", name + "(", ")"));
   }

   public static String getExecutableAsString(String name, Class... parameterTypes) {
      return (String)Stream.of(parameterTypes).map((t) -> {
         return t.getSimpleName();
      }).collect(Collectors.joining(", ", name + "(", ")"));
   }

   public static ElementType getElementType(Executable executable) {
      return executable instanceof Constructor ? ElementType.CONSTRUCTOR : ElementType.METHOD;
   }

   private boolean instanceMethodParametersResolveToSameTypes(Method subTypeMethod, Method superTypeMethod) {
      if (subTypeMethod.getParameterTypes().length == 0) {
         return true;
      } else {
         ResolvedType resolvedSubType = this.typeResolver.resolve(subTypeMethod.getDeclaringClass(), new Type[0]);
         MemberResolver memberResolver = new MemberResolver(this.typeResolver);
         memberResolver.setMethodFilter(new SimpleMethodFilter(subTypeMethod, superTypeMethod));
         ResolvedTypeWithMembers typeWithMembers = memberResolver.resolve(resolvedSubType, (AnnotationConfiguration)null, (AnnotationOverrides)null);
         ResolvedMethod[] resolvedMethods = (ResolvedMethod[])this.run(GetResolvedMemberMethods.action(typeWithMembers));
         if (resolvedMethods.length == 1) {
            return true;
         } else {
            try {
               for(int i = 0; i < resolvedMethods[0].getArgumentCount(); ++i) {
                  if (!resolvedMethods[0].getArgumentType(i).equals(resolvedMethods[1].getArgumentType(i))) {
                     return false;
                  }
               }
            } catch (ArrayIndexOutOfBoundsException var8) {
               LOG.debug("Error in ExecutableHelper#instanceMethodParametersResolveToSameTypes comparing " + subTypeMethod + " with " + superTypeMethod);
            }

            return true;
         }
      }
   }

   private Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }

   private static class SimpleMethodFilter implements Filter {
      private final Method method1;
      private final Method method2;

      private SimpleMethodFilter(Method method1, Method method2) {
         this.method1 = method1;
         this.method2 = method2;
      }

      public boolean include(RawMethod element) {
         return element.getRawMember().equals(this.method1) || element.getRawMember().equals(this.method2);
      }

      // $FF: synthetic method
      SimpleMethodFilter(Method x0, Method x1, Object x2) {
         this(x0, x1);
      }
   }
}
