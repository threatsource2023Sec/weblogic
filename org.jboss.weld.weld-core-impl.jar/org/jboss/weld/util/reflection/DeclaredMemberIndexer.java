package org.jboss.weld.util.reflection;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import org.jboss.weld.util.Preconditions;

public final class DeclaredMemberIndexer {
   private static final ConstructorComparator CONSTRUCTOR_COMPARATOR_INSTANCE = new ConstructorComparator();
   private static final MethodComparator METHOD_COMPARATOR_INSTANCE = new MethodComparator();
   private static final FieldComparator FIELD_COMPARATOR_INSTANCE = new FieldComparator();

   private DeclaredMemberIndexer() {
   }

   public static int getIndexForField(Field field) {
      Preconditions.checkNotNull(field);
      return getIndexForMember(field, getDeclaredFields(field.getDeclaringClass()));
   }

   public static Field getFieldForIndex(int index, Class declaringClass) {
      return (Field)getDeclaredFields(declaringClass).get(index);
   }

   public static int getIndexForMethod(Method method) {
      Preconditions.checkNotNull(method);
      return getIndexForMember(method, getDeclaredMethods(method.getDeclaringClass()));
   }

   public static Method getMethodForIndex(int index, Class declaringClass) {
      return (Method)getDeclaredMethods(declaringClass).get(index);
   }

   public static int getIndexForConstructor(Constructor constructor) {
      Preconditions.checkNotNull(constructor);
      return getIndexForMember(constructor, getDeclaredConstructors(constructor.getDeclaringClass()));
   }

   public static Constructor getConstructorForIndex(int index, Class declaringClass) {
      return (Constructor)Reflections.cast(getDeclaredConstructors(declaringClass).get(index));
   }

   private static int getIndexForMember(Member declaredMember, List declaredMembers) {
      ListIterator iterator = declaredMembers.listIterator();

      Member member;
      do {
         if (!iterator.hasNext()) {
            throw new IllegalStateException("No matching declared member found for: " + declaredMember);
         }

         member = (Member)iterator.next();
      } while(!member.equals(declaredMember));

      return iterator.previousIndex();
   }

   public static List getDeclaredFields(Class declaringClass) {
      Preconditions.checkNotNull(declaringClass);
      List declaredFields = Arrays.asList(SecurityActions.getDeclaredFields(declaringClass));
      Collections.sort(declaredFields, FIELD_COMPARATOR_INSTANCE);
      return declaredFields;
   }

   public static List getDeclaredMethods(Class declaringClass) {
      Preconditions.checkNotNull(declaringClass);
      List declaredMethods = Arrays.asList(SecurityActions.getDeclaredMethods(declaringClass));
      Collections.sort(declaredMethods, METHOD_COMPARATOR_INSTANCE);
      return declaredMethods;
   }

   public static List getDeclaredConstructors(Class declaringClass) {
      Preconditions.checkNotNull(declaringClass);
      List declaredConstructors = Arrays.asList(SecurityActions.getDeclaredConstructors(declaringClass));
      Collections.sort(declaredConstructors, CONSTRUCTOR_COMPARATOR_INSTANCE);
      return declaredConstructors;
   }

   private static int compareParamTypes(Class[] paramTypes1, Class[] paramTypes2) {
      if (paramTypes1.length != paramTypes2.length) {
         return paramTypes1.length - paramTypes2.length;
      } else {
         for(int i = 0; i < paramTypes1.length; ++i) {
            if (!paramTypes1[i].getName().equals(paramTypes2[i].getName())) {
               return paramTypes1[i].getName().compareTo(paramTypes2[i].getName());
            }
         }

         return 0;
      }
   }

   private static class FieldComparator implements Comparator, Serializable {
      private static final long serialVersionUID = -1417596921060498760L;

      private FieldComparator() {
      }

      public int compare(Field o1, Field o2) {
         return o1.getName().compareTo(o2.getName());
      }

      // $FF: synthetic method
      FieldComparator(Object x0) {
         this();
      }
   }

   private static class MethodComparator implements Comparator, Serializable {
      private static final long serialVersionUID = -2254993285161908832L;

      private MethodComparator() {
      }

      public int compare(Method m1, Method m2) {
         return !m1.getName().equals(m2.getName()) ? m1.getName().compareTo(m2.getName()) : DeclaredMemberIndexer.compareParamTypes(m1.getParameterTypes(), m2.getParameterTypes());
      }

      // $FF: synthetic method
      MethodComparator(Object x0) {
         this();
      }
   }

   private static class ConstructorComparator implements Comparator, Serializable {
      private static final long serialVersionUID = 4694814949925290433L;

      private ConstructorComparator() {
      }

      public int compare(Constructor c1, Constructor c2) {
         return DeclaredMemberIndexer.compareParamTypes(c1.getParameterTypes(), c2.getParameterTypes());
      }

      // $FF: synthetic method
      ConstructorComparator(Object x0) {
         this();
      }
   }
}
