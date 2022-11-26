package org.python.compiler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyMethod;
import org.python.core.PyObject;
import org.python.core.PyProxy;
import org.python.core.PyReflectedFunction;
import org.python.objectweb.asm.Type;
import org.python.util.Generic;

public class ProxyCodeHelpers {
   public static final int tBoolean = 0;
   public static final int tByte = 1;
   public static final int tShort = 2;
   public static final int tInteger = 3;
   public static final int tLong = 4;
   public static final int tFloat = 5;
   public static final int tDouble = 6;
   public static final int tCharacter = 7;
   public static final int tVoid = 8;
   public static final int tOther = 9;
   public static final int tNone = 10;
   public static Map types = fillTypes();

   public static Map fillTypes() {
      Map typeMap = Generic.map();
      typeMap.put(Boolean.TYPE, 0);
      typeMap.put(Byte.TYPE, 1);
      typeMap.put(Short.TYPE, 2);
      typeMap.put(Integer.TYPE, 3);
      typeMap.put(Long.TYPE, 4);
      typeMap.put(Float.TYPE, 5);
      typeMap.put(Double.TYPE, 6);
      typeMap.put(Character.TYPE, 7);
      typeMap.put(Void.TYPE, 8);
      return typeMap;
   }

   public static int getType(Class c) {
      if (c == null) {
         return 10;
      } else {
         Object i = types.get(c);
         return i == null ? 9 : (Integer)i;
      }
   }

   public static PyObject findPython(PyProxy proxy, String name) {
      PyObject o = proxy._getPyInstance();
      if (o == null) {
         proxy.__initProxy__(new Object[0]);
         o = proxy._getPyInstance();
      }

      PyObject ret = o.__findattr__(name);
      if (ret instanceof PyMethod) {
         PyMethod meth = (PyMethod)ret;
         if (meth.__func__ instanceof PyReflectedFunction) {
            PyReflectedFunction func = (PyReflectedFunction)meth.__func__;
            if (func.nargs > 0 && proxy.getClass() == func.argslist[0].declaringClass) {
               return null;
            }
         }
      }

      Py.setSystemState(proxy._getPySystemState());
      return ret;
   }

   public static PyException notImplementedAbstractMethod(PyProxy proxy, String name, String superClass) {
      PyObject o = proxy._getPyInstance();
      String msg = String.format("'%.200s' object does not implement abstract method '%.200s' from '%.200s'", o.getType().fastGetName(), name, superClass);
      return Py.NotImplementedError(msg);
   }

   public static String mapClass(Class c) {
      String name = c.getName();
      int index = name.indexOf(".");
      if (index == -1) {
         return name;
      } else {
         StringBuffer buf = new StringBuffer(name.length());

         int last_index;
         for(last_index = 0; index != -1; index = name.indexOf(".", last_index)) {
            buf.append(name.substring(last_index, index));
            buf.append("/");
            last_index = index + 1;
         }

         buf.append(name.substring(last_index, name.length()));
         return buf.toString();
      }
   }

   public static String mapType(Class var0) {
      // $FF: Couldn't be decompiled
   }

   public static String makeSig(Class ret, Class... sig) {
      String[] mapped = new String[sig.length];

      for(int i = 0; i < mapped.length; ++i) {
         mapped[i] = mapType(sig[i]);
      }

      return makeSig(mapType(ret), mapped);
   }

   public static String makeSig(String returnType, String... parameterTypes) {
      StringBuilder buf = new StringBuilder("(");
      String[] var3 = parameterTypes;
      int var4 = parameterTypes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String param = var3[var5];
         buf.append(param);
      }

      return buf.append(')').append(returnType).toString();
   }

   public static void doReturn(Code var0, Class var1) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public static void doNullReturn(Code var0, Class var1) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public static String[] mapClasses(Class[] classes) {
      String[] mapped = new String[classes.length];

      for(int i = 0; i < mapped.length; ++i) {
         mapped[i] = mapClass(classes[i]);
      }

      return mapped;
   }

   public static String[] mapExceptions(Class[] classes) {
      String[] exceptionTypes = new String[classes.length];

      for(int i = 0; i < classes.length; ++i) {
         exceptionTypes[i] = Type.getType(classes[i]).getInternalName();
      }

      return exceptionTypes;
   }

   public static class AnnotationDescr {
      public final Class annotation;
      public final Map fields;

      public AnnotationDescr(Class annotation) {
         this.annotation = annotation;
         this.fields = null;
      }

      public AnnotationDescr(Class annotation, Map fields) {
         this.annotation = annotation;
         this.fields = fields;
      }

      public boolean hasFields() {
         return this.fields != null;
      }

      public String getName() {
         return ProxyCodeHelpers.mapType(this.annotation);
      }

      public Map getFields() {
         return this.fields;
      }

      public int hashCode() {
         if (!this.hasFields()) {
            return this.annotation.hashCode();
         } else {
            int hash = this.annotation.hashCode();

            Map.Entry field;
            for(Iterator var2 = this.fields.entrySet().iterator(); var2.hasNext(); hash += ((String)field.getKey()).hashCode() + field.getValue().hashCode()) {
               field = (Map.Entry)var2.next();
            }

            return hash;
         }
      }
   }

   public static class ConstructorDescr extends MethodDescr {
      public ConstructorDescr(Constructor cons) {
         this(cons.getParameterTypes(), cons.getExceptionTypes());
      }

      public ConstructorDescr(Class[] parameters, Class[] exceptions) {
         super("<init>", Void.TYPE, parameters, exceptions);
      }
   }

   public static class MethodDescr {
      public final Class returnType;
      public final String name;
      public final Class[] parameters;
      public final Class[] exceptions;
      public final Map methodAnnotations;
      public final Map[] parameterAnnotations;

      public MethodDescr(Method m) {
         this(m.getName(), m.getReturnType(), m.getParameterTypes(), m.getExceptionTypes(), (Map)null, (Map[])null);
      }

      public MethodDescr(String name, Class returnType, Class[] parameters, Class[] exceptions) {
         this.name = name;
         this.returnType = returnType;
         this.parameters = parameters;
         this.exceptions = exceptions;
         this.methodAnnotations = null;
         this.parameterAnnotations = null;
      }

      public MethodDescr(String name, Class returnType, Class[] parameters, Class[] exceptions, Map methodAnnotations, Map[] parameterAnnotations) {
         this.name = name;
         this.returnType = returnType;
         this.parameters = parameters;
         this.exceptions = exceptions;
         this.methodAnnotations = methodAnnotations;
         this.parameterAnnotations = parameterAnnotations;
      }

      public int hashCode() {
         return this.name.hashCode() + this.parameters.length;
      }

      public boolean equals(Object obj) {
         if (!(obj instanceof MethodDescr)) {
            return false;
         } else {
            MethodDescr oDescr = (MethodDescr)obj;
            if (this.name.equals(oDescr.name) && this.parameters.length == oDescr.parameters.length) {
               for(int i = 0; i < this.parameters.length; ++i) {
                  if (!this.parameters[i].equals(oDescr.parameters[i])) {
                     return false;
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }
}
