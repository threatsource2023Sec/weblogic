package org.apache.xmlbeans.impl.jam;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class JamUtils {
   private static final JamUtils INSTANCE = new JamUtils();
   private static Comparator SOURCE_POSITION_COMPARATOR = new Comparator() {
      public int compare(Object o, Object o1) {
         JSourcePosition p1 = ((JElement)o).getSourcePosition();
         JSourcePosition p2 = ((JElement)o1).getSourcePosition();
         if (p1 == null) {
            return p2 == null ? 0 : -1;
         } else if (p2 == null) {
            return 1;
         } else {
            return p1.getLine() < p2.getLine() ? -1 : (p1.getLine() > p2.getLine() ? 1 : 0);
         }
      }
   };

   public static final JamUtils getInstance() {
      return INSTANCE;
   }

   private JamUtils() {
   }

   public Method getMethodOn(JMethod method, Class containedin) throws NoSuchMethodException, ClassNotFoundException {
      if (containedin == null) {
         throw new IllegalArgumentException("null class");
      } else if (method == null) {
         throw new IllegalArgumentException("null method");
      } else {
         Class[] types = null;
         JParameter[] params = method.getParameters();
         if (params != null && params.length > 0) {
            types = new Class[params.length];

            for(int i = 0; i < types.length; ++i) {
               types[i] = this.loadClass(params[i].getType(), containedin.getClassLoader());
            }
         }

         return containedin.getMethod(method.getSimpleName(), types);
      }
   }

   public Constructor getConstructorOn(JConstructor ctor, Class containedin) throws NoSuchMethodException, ClassNotFoundException {
      if (containedin == null) {
         throw new IllegalArgumentException("null class");
      } else if (ctor == null) {
         throw new IllegalArgumentException("null ctor");
      } else {
         Class[] types = null;
         JParameter[] params = ctor.getParameters();
         if (params != null && params.length > 0) {
            types = new Class[params.length];

            for(int i = 0; i < types.length; ++i) {
               types[i] = this.loadClass(params[i].getType(), containedin.getClassLoader());
            }
         }

         return containedin.getConstructor(types);
      }
   }

   public Field getFieldOn(JField field, Class containedin) throws NoSuchFieldException {
      if (containedin == null) {
         throw new IllegalArgumentException("null class");
      } else if (field == null) {
         throw new IllegalArgumentException("null field");
      } else {
         return containedin.getField(field.getSimpleName());
      }
   }

   public Class loadClass(JClass clazz, ClassLoader inThisClassloader) throws ClassNotFoundException {
      return inThisClassloader.loadClass(clazz.getQualifiedName());
   }

   public void placeInSourceOrder(JElement[] elements) {
      if (elements == null) {
         throw new IllegalArgumentException("null elements");
      } else {
         Arrays.sort(elements, SOURCE_POSITION_COMPARATOR);
      }
   }
}
