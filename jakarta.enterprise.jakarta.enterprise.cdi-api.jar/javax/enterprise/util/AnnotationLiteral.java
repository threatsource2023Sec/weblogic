package javax.enterprise.util;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public abstract class AnnotationLiteral implements Annotation, Serializable {
   private static final long serialVersionUID = 1L;
   private transient Class annotationType;
   private transient Method[] members;
   private transient Integer cachedHashCode;

   protected AnnotationLiteral() {
      if (this.getMembers().length == 0) {
         this.cachedHashCode = 0;
      } else {
         this.cachedHashCode = null;
      }

   }

   private Method[] getMembers() {
      if (this.members == null) {
         this.members = SecurityActions.getDeclaredMethods(this.annotationType());
         if (this.members.length > 0 && !this.annotationType().isAssignableFrom(this.getClass())) {
            throw new RuntimeException(this.getClass() + " does not implement the annotation type with members " + this.annotationType().getName());
         }
      }

      return this.members;
   }

   private static Class getAnnotationLiteralSubclass(Class clazz) {
      Class superclass = clazz.getSuperclass();
      if (superclass.equals(AnnotationLiteral.class)) {
         return clazz;
      } else {
         return superclass.equals(Object.class) ? null : getAnnotationLiteralSubclass(superclass);
      }
   }

   private static Class getTypeParameter(Class annotationLiteralSuperclass) {
      Type type = annotationLiteralSuperclass.getGenericSuperclass();
      if (type instanceof ParameterizedType) {
         ParameterizedType parameterizedType = (ParameterizedType)type;
         if (parameterizedType.getActualTypeArguments().length == 1) {
            return (Class)parameterizedType.getActualTypeArguments()[0];
         }
      }

      return null;
   }

   public Class annotationType() {
      if (this.annotationType == null) {
         Class annotationLiteralSubclass = getAnnotationLiteralSubclass(this.getClass());
         if (annotationLiteralSubclass == null) {
            throw new RuntimeException(this.getClass() + " is not a subclass of AnnotationLiteral");
         }

         this.annotationType = getTypeParameter(annotationLiteralSubclass);
         if (this.annotationType == null) {
            throw new RuntimeException(this.getClass() + " does not specify the type parameter T of AnnotationLiteral<T>");
         }
      }

      return this.annotationType;
   }

   public String toString() {
      StringBuilder string = new StringBuilder();
      string.append('@').append(this.annotationType().getName()).append('(');

      for(int i = 0; i < this.getMembers().length; ++i) {
         string.append(this.getMembers()[i].getName()).append('=');
         Object value = getMemberValue(this.getMembers()[i], this);
         if (value instanceof boolean[]) {
            this.appendInBraces(string, Arrays.toString((boolean[])((boolean[])value)));
         } else if (value instanceof byte[]) {
            this.appendInBraces(string, Arrays.toString((byte[])((byte[])value)));
         } else if (value instanceof short[]) {
            this.appendInBraces(string, Arrays.toString((short[])((short[])value)));
         } else if (value instanceof int[]) {
            this.appendInBraces(string, Arrays.toString((int[])((int[])value)));
         } else if (value instanceof long[]) {
            this.appendInBraces(string, Arrays.toString((long[])((long[])value)));
         } else if (value instanceof float[]) {
            this.appendInBraces(string, Arrays.toString((float[])((float[])value)));
         } else if (value instanceof double[]) {
            this.appendInBraces(string, Arrays.toString((double[])((double[])value)));
         } else if (value instanceof char[]) {
            this.appendInBraces(string, Arrays.toString((char[])((char[])value)));
         } else {
            String[] names;
            int j;
            if (value instanceof String[]) {
               String[] strings = (String[])((String[])value);
               names = new String[strings.length];

               for(j = 0; j < strings.length; ++j) {
                  names[j] = "\"" + strings[j] + "\"";
               }

               this.appendInBraces(string, Arrays.toString(names));
            } else if (!(value instanceof Class[])) {
               if (value instanceof Object[]) {
                  this.appendInBraces(string, Arrays.toString((Object[])((Object[])value)));
               } else if (value instanceof String) {
                  string.append('"').append(value).append('"');
               } else if (value instanceof Class) {
                  string.append(((Class)value).getName()).append(".class");
               } else {
                  string.append(value);
               }
            } else {
               Class[] classes = (Class[])((Class[])value);
               names = new String[classes.length];

               for(j = 0; j < classes.length; ++j) {
                  names[j] = classes[j].getName() + ".class";
               }

               this.appendInBraces(string, Arrays.toString(names));
            }
         }

         if (i < this.getMembers().length - 1) {
            string.append(", ");
         }
      }

      return string.append(')').toString();
   }

   private void appendInBraces(StringBuilder buf, String s) {
      buf.append('{').append(s.substring(1, s.length() - 1)).append('}');
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other == null) {
         return false;
      } else {
         if (other instanceof Annotation) {
            Annotation that = (Annotation)other;
            if (this.annotationType().equals(that.annotationType())) {
               Method[] var3 = this.getMembers();
               int var4 = var3.length;

               for(int var5 = 0; var5 < var4; ++var5) {
                  Method member = var3[var5];
                  Object thisValue = getMemberValue(member, this);
                  Object thatValue = getMemberValue(member, that);
                  if (thisValue instanceof byte[] && thatValue instanceof byte[]) {
                     if (!Arrays.equals((byte[])((byte[])thisValue), (byte[])((byte[])thatValue))) {
                        return false;
                     }
                  } else if (thisValue instanceof short[] && thatValue instanceof short[]) {
                     if (!Arrays.equals((short[])((short[])thisValue), (short[])((short[])thatValue))) {
                        return false;
                     }
                  } else if (thisValue instanceof int[] && thatValue instanceof int[]) {
                     if (!Arrays.equals((int[])((int[])thisValue), (int[])((int[])thatValue))) {
                        return false;
                     }
                  } else if (thisValue instanceof long[] && thatValue instanceof long[]) {
                     if (!Arrays.equals((long[])((long[])thisValue), (long[])((long[])thatValue))) {
                        return false;
                     }
                  } else if (thisValue instanceof float[] && thatValue instanceof float[]) {
                     if (!Arrays.equals((float[])((float[])thisValue), (float[])((float[])thatValue))) {
                        return false;
                     }
                  } else if (thisValue instanceof double[] && thatValue instanceof double[]) {
                     if (!Arrays.equals((double[])((double[])thisValue), (double[])((double[])thatValue))) {
                        return false;
                     }
                  } else if (thisValue instanceof char[] && thatValue instanceof char[]) {
                     if (!Arrays.equals((char[])((char[])thisValue), (char[])((char[])thatValue))) {
                        return false;
                     }
                  } else if (thisValue instanceof boolean[] && thatValue instanceof boolean[]) {
                     if (!Arrays.equals((boolean[])((boolean[])thisValue), (boolean[])((boolean[])thatValue))) {
                        return false;
                     }
                  } else if (thisValue instanceof Object[] && thatValue instanceof Object[]) {
                     if (!Arrays.equals((Object[])((Object[])thisValue), (Object[])((Object[])thatValue))) {
                        return false;
                     }
                  } else if (!thisValue.equals(thatValue)) {
                     return false;
                  }
               }

               return true;
            }
         }

         return false;
      }
   }

   public int hashCode() {
      if (this.cachedHashCode != null) {
         return this.cachedHashCode;
      } else {
         int hashCode = 0;
         Method[] var2 = this.getMembers();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method member = var2[var4];
            int memberNameHashCode = 127 * member.getName().hashCode();
            Object value = getMemberValue(member, this);
            int memberValueHashCode;
            if (value instanceof boolean[]) {
               memberValueHashCode = Arrays.hashCode((boolean[])((boolean[])value));
            } else if (value instanceof short[]) {
               memberValueHashCode = Arrays.hashCode((short[])((short[])value));
            } else if (value instanceof int[]) {
               memberValueHashCode = Arrays.hashCode((int[])((int[])value));
            } else if (value instanceof long[]) {
               memberValueHashCode = Arrays.hashCode((long[])((long[])value));
            } else if (value instanceof float[]) {
               memberValueHashCode = Arrays.hashCode((float[])((float[])value));
            } else if (value instanceof double[]) {
               memberValueHashCode = Arrays.hashCode((double[])((double[])value));
            } else if (value instanceof byte[]) {
               memberValueHashCode = Arrays.hashCode((byte[])((byte[])value));
            } else if (value instanceof char[]) {
               memberValueHashCode = Arrays.hashCode((char[])((char[])value));
            } else if (value instanceof Object[]) {
               memberValueHashCode = Arrays.hashCode((Object[])((Object[])value));
            } else {
               memberValueHashCode = value.hashCode();
            }

            hashCode += memberNameHashCode ^ memberValueHashCode;
         }

         return hashCode;
      }
   }

   private static Object getMemberValue(Method member, Annotation instance) {
      Object value = invoke(member, instance);
      if (value == null) {
         throw new IllegalArgumentException("Annotation member value " + instance.getClass().getName() + "." + member.getName() + " must not be null");
      } else {
         return value;
      }
   }

   private static Object invoke(Method method, Object instance) {
      try {
         if (!method.isAccessible()) {
            SecurityActions.setAccessible(method);
         }

         return method.invoke(instance);
      } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException var3) {
         throw new RuntimeException("Error checking value of member method " + method.getName() + " on " + method.getDeclaringClass(), var3);
      }
   }
}
