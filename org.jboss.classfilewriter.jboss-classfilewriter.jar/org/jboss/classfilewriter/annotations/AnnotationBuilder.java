package org.jboss.classfilewriter.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.List;
import org.jboss.classfilewriter.constpool.ConstPool;

public class AnnotationBuilder {
   public static ClassAnnotation createAnnotation(ConstPool constPool, final Annotation annotation) {
      Class annotationType = annotation.annotationType();
      List values = new ArrayList();

      try {
         Method[] var4 = annotationType.getDeclaredMethods();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            final Method m = var4[var6];
            Object value = AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws InvocationTargetException, IllegalAccessException {
                  m.setAccessible(true);
                  return m.invoke(annotation);
               }
            });
            values.add(createValue(constPool, m.getName(), value));
         }
      } catch (IllegalArgumentException var9) {
         throw new RuntimeException(var9);
      } catch (PrivilegedActionException var10) {
         throw new RuntimeException(var10);
      }

      return new ClassAnnotation(constPool, annotationType.getName(), values);
   }

   public static AnnotationValue createValue(ConstPool constPool, String name, Object value) {
      Class type = value.getClass();
      if (type == String.class) {
         return new StringAnnotationValue(constPool, name, (String)value);
      } else if (type != Integer.TYPE && type != Integer.class) {
         if (type != Short.TYPE && type != Short.class) {
            if (type != Byte.TYPE && type != Byte.class) {
               if (type != Boolean.TYPE && type != Boolean.class) {
                  if (type != Character.TYPE && type != Character.class) {
                     if (type != Long.TYPE && type != Long.class) {
                        if (type != Float.TYPE && type != Float.class) {
                           if (type != Double.TYPE && type != Double.class) {
                              if (type == Class.class) {
                                 return new ClassAnnotationValue(constPool, name, (Class)value);
                              } else if (!type.isEnum() && (type.getEnclosingClass() == null || !type.getEnclosingClass().isEnum())) {
                                 if (value instanceof Annotation) {
                                    return new AnnotationAnnotationValue(constPool, name, createAnnotation(constPool, (Annotation)value));
                                 } else if (!type.isArray()) {
                                    throw new RuntimeException("Invalid type for annotation value. Type: " + type + " Value: " + value);
                                 } else {
                                    int length = Array.getLength(value);
                                    List values = new ArrayList();

                                    for(int i = 0; i < length; ++i) {
                                       values.add(createValue(constPool, (String)null, Array.get(value, i)));
                                    }

                                    return new ArrayAnnotationValue(constPool, name, values);
                                 }
                              } else {
                                 return new EnumAnnotationValue(constPool, name, (Enum)value);
                              }
                           } else {
                              return new DoubleAnnotationValue(constPool, name, (Double)value);
                           }
                        } else {
                           return new FloatAnnotationValue(constPool, name, (Float)value);
                        }
                     } else {
                        return new LongAnnotationValue(constPool, name, (Long)value);
                     }
                  } else {
                     return new CharAnnotationValue(constPool, name, (Character)value);
                  }
               } else {
                  return new BooleanAnnotationValue(constPool, name, (Boolean)value);
               }
            } else {
               return new ByteAnnotationValue(constPool, name, (Byte)value);
            }
         } else {
            return new ShortAnnotationValue(constPool, name, (Short)value);
         }
      } else {
         return new IntAnnotationValue(constPool, name, (Integer)value);
      }
   }

   private AnnotationBuilder() {
   }
}
