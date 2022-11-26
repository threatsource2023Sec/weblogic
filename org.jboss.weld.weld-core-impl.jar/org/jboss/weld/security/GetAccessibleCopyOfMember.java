package org.jboss.weld.security;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.security.PrivilegedAction;

public class GetAccessibleCopyOfMember implements PrivilegedAction {
   private static final String UNABLE_TO_OBTAIN_AN_ACCESSIBLE_COPY_OF = "Unable to obtain an accessible copy of ";
   private final AccessibleObject originalMember;

   public GetAccessibleCopyOfMember(AccessibleObject originalMember) {
      this.originalMember = originalMember;
   }

   public static AccessibleObject of(AccessibleObject member) {
      AccessibleObject copy = copyMember(member);
      copy.setAccessible(true);
      return copy;
   }

   public AccessibleObject run() {
      return of(this.originalMember);
   }

   private static AccessibleObject copyMember(AccessibleObject originalMember) {
      Class declaringClass = ((Member)originalMember).getDeclaringClass();

      try {
         if (originalMember instanceof Field) {
            return copyField((Field)originalMember, declaringClass);
         }

         if (originalMember instanceof Constructor) {
            return copyConstructor((Constructor)originalMember, declaringClass);
         }

         if (originalMember instanceof Method) {
            return copyMethod((Method)originalMember, declaringClass);
         }
      } catch (Exception var3) {
         throw new IllegalArgumentException("Unable to obtain an accessible copy of " + originalMember, var3);
      }

      throw new IllegalArgumentException("Unable to obtain an accessible copy of " + originalMember);
   }

   private static Field copyField(Field field, Class declaringClass) throws NoSuchFieldException {
      return declaringClass.getDeclaredField(field.getName());
   }

   private static Method copyMethod(Method method, Class declaringClass) throws NoSuchMethodException {
      return declaringClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
   }

   private static Constructor copyConstructor(Constructor constructor, Class declaringClass) throws NoSuchMethodException {
      return declaringClass.getDeclaredConstructor(constructor.getParameterTypes());
   }
}
