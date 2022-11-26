package org.hibernate.validator.internal.util;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringHelper {
   private static final Pattern DOT = Pattern.compile("\\.");

   private StringHelper() {
   }

   public static String join(Object[] array, String separator) {
      return array != null ? join((Iterable)Arrays.asList(array), separator) : null;
   }

   public static String join(Iterable iterable, String separator) {
      if (iterable == null) {
         return null;
      } else {
         StringBuilder sb = new StringBuilder();
         boolean isFirst = true;

         Object object;
         for(Iterator var4 = iterable.iterator(); var4.hasNext(); sb.append(object)) {
            object = var4.next();
            if (!isFirst) {
               sb.append(separator);
            } else {
               isFirst = false;
            }
         }

         return sb.toString();
      }
   }

   public static String decapitalize(String string) {
      return string != null && !string.isEmpty() && !startsWithSeveralUpperCaseLetters(string) ? string.substring(0, 1).toLowerCase(Locale.ROOT) + string.substring(1) : string;
   }

   public static boolean isNullOrEmptyString(String value) {
      return value == null || value.trim().isEmpty();
   }

   public static String toShortString(Member member) {
      if (member instanceof Field) {
         return toShortString((Field)member);
      } else {
         return member instanceof Method ? toShortString((Method)member) : member.toString();
      }
   }

   private static String toShortString(Field field) {
      return toShortString(field.getGenericType()) + " " + toShortString(field.getDeclaringClass()) + "#" + field.getName();
   }

   private static String toShortString(Method method) {
      return toShortString(method.getGenericReturnType()) + " " + method.getName() + (String)Arrays.stream(method.getGenericParameterTypes()).map(StringHelper::toShortString).collect(Collectors.joining(", ", "(", ")"));
   }

   public static String toShortString(Type type) {
      if (type instanceof Class) {
         return toShortString((Class)type);
      } else {
         return type instanceof ParameterizedType ? toShortString((ParameterizedType)type) : type.toString();
      }
   }

   private static String toShortString(Class type) {
      if (type.isArray()) {
         return toShortString(type.getComponentType()) + "[]";
      } else if (type.getEnclosingClass() != null) {
         return toShortString(type.getEnclosingClass()) + "$" + type.getSimpleName();
      } else {
         return type.getPackage() == null ? type.getName() : toShortString(type.getPackage()) + "." + type.getSimpleName();
      }
   }

   private static String toShortString(ParameterizedType parameterizedType) {
      Class rawType = ReflectionHelper.getClassFromType(parameterizedType);
      if (rawType.getPackage() == null) {
         return parameterizedType.toString();
      } else {
         String typeArgumentsString = (String)Arrays.stream(parameterizedType.getActualTypeArguments()).map((t) -> {
            return toShortString(t);
         }).collect(Collectors.joining(", ", "<", ">"));
         return toShortString(rawType) + typeArgumentsString;
      }
   }

   private static String toShortString(Package pakkage) {
      String[] packageParts = DOT.split(pakkage.getName());
      return (String)Arrays.stream(packageParts).map((n) -> {
         return n.substring(0, 1);
      }).collect(Collectors.joining("."));
   }

   private static boolean startsWithSeveralUpperCaseLetters(String string) {
      return string.length() > 1 && Character.isUpperCase(string.charAt(0)) && Character.isUpperCase(string.charAt(1));
   }
}
