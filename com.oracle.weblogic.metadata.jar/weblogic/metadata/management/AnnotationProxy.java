package weblogic.metadata.management;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import weblogic.j2ee.descriptor.wl.AnnotationInstanceBean;
import weblogic.j2ee.descriptor.wl.ArrayMemberBean;
import weblogic.j2ee.descriptor.wl.MemberBean;
import weblogic.j2ee.descriptor.wl.NestedAnnotationBean;

public class AnnotationProxy implements InvocationHandler {
   private AnnotationInstanceBean _annotationOverrides;
   private String _annotationClassName;
   private HashSet _annotationMembers = new HashSet();
   private Annotation _originalAnnotation;

   public static Annotation newInstance(Class c, AnnotationInstanceBean ao) {
      ClassLoader classLoader = c.getClassLoader();
      Class[] interfaces = c.getInterfaces();
      Object proxy = Proxy.newProxyInstance(classLoader, interfaces, new AnnotationProxy(c, ao));
      return (Annotation)proxy;
   }

   private AnnotationProxy(Class c, AnnotationInstanceBean ao) {
      this._annotationClassName = c.getName();
      this._annotationOverrides = ao;
      Method[] m = c.getDeclaredMethods();

      for(int i = 0; i < m.length; ++i) {
         this._annotationMembers.add(m[i].getName());
      }

   }

   public void setDelegate(Annotation a) {
      this._originalAnnotation = a;
   }

   public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
      assert args == null;

      Object result = null;
      String memberName = m.getName();
      if (!this._annotationMembers.contains(memberName)) {
         if (this._originalAnnotation == null) {
            result = m.invoke(this, args);
         } else {
            result = m.invoke(this._originalAnnotation, args);
         }

         return result;
      } else {
         Class memberType = m.getReturnType();
         return getValueForMember(memberName, memberType, this._annotationOverrides);
      }
   }

   public static Object getValueForMember(MemberBean mb, Class clazz, AnnotationInstanceBean aib) throws NoSuchMethodException {
      String memberName = mb.getMemberName();
      Method method = clazz.getDeclaredMethod(memberName);
      Class memberType = method.getReturnType();
      return getValueForMember(memberName, memberType, aib);
   }

   public static Object getValueForMember(String memberName, Class memberType, AnnotationInstanceBean aib) {
      Object result = null;

      try {
         if (memberType.isArray()) {
            if (Annotation.class.isAssignableFrom(memberType.getComponentType())) {
               throw new RuntimeException("Arrays of annotations not yet implemented");
            }

            ArrayMemberBean[] memberArrays = aib.getArrayMembers();
            ArrayMemberBean memberArray = findMemberArray(memberArrays, memberName);
            if (memberArray != null) {
               String[] overrideValues = memberArray.getOverrideValues();
               if (overrideValues != null && overrideValues.length > 0) {
                  result = buildReturnValues(memberType, overrideValues, aib, memberName);
               }
            }
         } else {
            MemberBean member = findMember(aib.getMembers(), memberName);
            if (member != null) {
               String overrideValue = member.getOverrideValue();
               if (overrideValue != null && !overrideValue.trim().equals("")) {
                  result = buildReturnValue(memberType, overrideValue, aib, memberName);
               }
            }
         }

         return result;
      } catch (Exception var7) {
         throw new RuntimeException("Error Getting Override Value. method:" + memberName, var7);
      }
   }

   private static NestedAnnotationBean findNestedAnnotation(NestedAnnotationBean[] members, String memberName) {
      NestedAnnotationBean nestedAnnotationBean = null;

      for(int i = 0; i < members.length; ++i) {
         if (members[i].getMemberName().equals(memberName)) {
            nestedAnnotationBean = members[i];
         }
      }

      return nestedAnnotationBean;
   }

   private static ArrayMemberBean findMemberArray(ArrayMemberBean[] members, String memberName) {
      ArrayMemberBean memberArrayBean = null;

      for(int i = 0; i < members.length; ++i) {
         if (members[i].getMemberName().equals(memberName)) {
            memberArrayBean = members[i];
         }
      }

      assert memberArrayBean != null;

      return memberArrayBean;
   }

   private static MemberBean findMember(MemberBean[] members, String memberName) {
      MemberBean memberBean = null;

      for(int i = 0; i < members.length; ++i) {
         if (members[i].getMemberName().equals(memberName)) {
            memberBean = members[i];
         }
      }

      assert memberBean != null;

      return memberBean;
   }

   private static Object buildReturnValues(Class memberType, String[] values, AnnotationInstanceBean aib, String memberName) {
      assert memberType.isArray();

      assert values != null;

      Object returnValue = null;
      Class type = memberType.getComponentType();
      int length = values.length;
      if (type == String.class) {
         returnValue = values;
      } else {
         int i;
         if (type.isPrimitive()) {
            if (type == Byte.TYPE) {
               byte[] array = new byte[length];

               for(i = 0; i < length; ++i) {
                  array[i] = Byte.parseByte(values[i]);
               }

               returnValue = array;
            } else if (type == Short.TYPE) {
               short[] array = new short[length];

               for(i = 0; i < length; ++i) {
                  array[i] = Short.parseShort(values[i]);
               }

               returnValue = array;
            } else if (type == Integer.TYPE) {
               int[] array = new int[length];

               for(i = 0; i < length; ++i) {
                  array[i] = Integer.parseInt(values[i]);
               }

               returnValue = array;
            } else if (type == Long.TYPE) {
               long[] array = new long[length];

               for(i = 0; i < length; ++i) {
                  array[i] = Long.parseLong(values[i]);
               }

               returnValue = array;
            } else if (type == Double.TYPE) {
               double[] array = new double[length];

               for(i = 0; i < length; ++i) {
                  array[i] = Double.parseDouble(values[i]);
               }

               returnValue = array;
            } else if (type == Float.TYPE) {
               float[] array = new float[length];

               for(i = 0; i < length; ++i) {
                  array[i] = Float.parseFloat(values[i]);
               }

               returnValue = array;
            } else if (type == Character.TYPE) {
               char[] array = new char[length];

               for(i = 0; i < length; ++i) {
                  array[i] = values[i].charAt(0);
               }

               returnValue = array;
            } else {
               if (type != Boolean.TYPE) {
                  throw new RuntimeException("Unknown primitive type:" + type.getName());
               }

               boolean[] array = new boolean[length];

               for(i = 0; i < length; ++i) {
                  array[i] = Boolean.parseBoolean(values[i]);
               }

               returnValue = array;
            }
         } else {
            Object[] array = new Object[length];

            for(i = 0; i < length; ++i) {
               array[i] = buildReturnValue(type, values[i], aib, memberName);
            }

            returnValue = array;
         }
      }

      return returnValue;
   }

   private static Object buildReturnValue(Class memberType, String value, AnnotationInstanceBean aib, String memberName) {
      Object returnValue = null;
      if (memberType.isPrimitive()) {
         if (memberType == Byte.TYPE) {
            returnValue = Byte.valueOf(value);
         } else if (memberType == Short.TYPE) {
            returnValue = Short.valueOf(value);
         } else if (memberType == Integer.TYPE) {
            returnValue = Integer.valueOf(value);
         } else if (memberType == Long.TYPE) {
            returnValue = Long.valueOf(value);
         } else if (memberType == Double.TYPE) {
            returnValue = Double.valueOf(value);
         } else if (memberType == Float.TYPE) {
            returnValue = Float.valueOf(value);
         } else if (memberType == Character.TYPE) {
            returnValue = value.charAt(0);
         } else {
            if (memberType != Boolean.TYPE) {
               throw new RuntimeException("Unknown primitive type:" + memberType.getName());
            }

            returnValue = Boolean.valueOf(value);
         }
      } else if (memberType.equals(String.class)) {
         returnValue = value;
      } else if (Enum.class.isAssignableFrom(memberType)) {
         returnValue = Enum.valueOf(memberType, value);
      } else if (Class.class.isAssignableFrom(memberType)) {
         ClassLoader cl = Thread.currentThread().getContextClassLoader();

         try {
            returnValue = cl.loadClass(value);
         } catch (ClassNotFoundException var10) {
            System.err.println(AnnotationProxy.class.getName() + ": unable to load class: " + value);
         }
      } else {
         if (!Annotation.class.isAssignableFrom(memberType)) {
            throw new RuntimeException("Unknown Member Type:" + memberType);
         }

         NestedAnnotationBean nab = findNestedAnnotation(aib.getNestedAnnotations(), memberName);
         if (nab == null) {
            throw new RuntimeException("unable to find NestedAnnotationBean - memberName = " + memberName);
         }

         AnnotationInstanceBean aib2 = nab.getAnnotation();
         ClassLoader cl = Thread.currentThread().getContextClassLoader();

         try {
            Class clz = cl.loadClass(aib2.getAnnotationClassName());
            returnValue = newInstance(clz, aib2);
         } catch (ClassNotFoundException var9) {
            System.err.println(AnnotationProxy.class.getName() + ": unable to load class: " + value);
         }
      }

      return returnValue;
   }
}
