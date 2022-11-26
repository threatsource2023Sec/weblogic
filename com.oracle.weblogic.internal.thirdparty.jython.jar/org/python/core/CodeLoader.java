package org.python.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class CodeLoader {
   public static final String GET_BOOTSTRAP_METHOD_NAME = "getCodeBootstrap";
   public final String name;
   public final String filename;
   public static final String SIMPLE_FACTORY_METHOD_NAME = "createSimpleBootstrap";

   private CodeLoader(String name, String filename) {
      this.name = name;
      this.filename = filename;
   }

   public static boolean canLoad(Class cls) {
      try {
         Method getBootstrap = cls.getMethod("getCodeBootstrap");
         return Modifier.isStatic(getBootstrap.getModifiers());
      } catch (Exception var2) {
         return false;
      }
   }

   public static PyCode loadCode(Class cls, String name, String filename) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
      Method getBootstrap = cls.getMethod("getCodeBootstrap");
      CodeBootstrap bootstrap = (CodeBootstrap)getBootstrap.invoke((Object)null);
      return loadCode(bootstrap, name, filename);
   }

   public static PyCode loadCode(Class cls) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      return loadCode((Class)cls, (String)null, (String)null);
   }

   public static PyCode loadCode(CodeBootstrap bootstrap, String name, String filename) {
      return bootstrap.loadCode(new CodeLoader(name, filename));
   }

   public static PyCode loadCode(CodeBootstrap bootstrap) {
      return loadCode((CodeBootstrap)bootstrap, (String)null, (String)null);
   }

   public static CodeBootstrap createSimpleBootstrap(final PyCode code) {
      return new CodeBootstrap() {
         public PyCode loadCode(CodeLoader loader) {
            return code;
         }
      };
   }
}
