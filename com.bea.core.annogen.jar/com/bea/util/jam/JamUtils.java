package com.bea.util.jam;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

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
   private static Comparator ALPHABETICAL_COMPARATOR = new Comparator() {
      public int compare(Object o1, Object o2) {
         String n1 = ((JElement)o1).getSimpleName();
         String n2 = ((JElement)o2).getSimpleName();
         return n1.compareTo(n2);
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

   public void placeInAlphabeticalOrder(JElement[] elements) {
      if (elements == null) {
         throw new IllegalArgumentException("null elements");
      } else {
         Arrays.sort(elements, ALPHABETICAL_COMPARATOR);
      }
   }

   public JClass[] getClassesCorrespondingTo(File[] files, JClass[] classes) {
      if (files == null) {
         throw new IllegalArgumentException("null files");
      } else if (classes == null) {
         throw new IllegalArgumentException("null classes");
      } else {
         Map uri2class = new HashMap();

         URI uri;
         for(int i = 0; i < classes.length; ++i) {
            if (classes[i] != null) {
               JSourcePosition sp = classes[i].getSourcePosition();
               if (sp != null) {
                  uri = sp.getSourceURI();
                  if (uri != null) {
                     uri2class.put(uri, classes[i]);
                  }
               }
            }
         }

         JClass[] out = new JClass[files.length];

         for(int i = 0; i < out.length; ++i) {
            if (files[i] != null) {
               uri = files[i].toURI();
               out[i] = (JClass)uri2class.get(uri);
            }
         }

         return out;
      }
   }
}
