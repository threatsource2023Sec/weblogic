package org.python.core;

import java.io.IOException;
import java.lang.reflect.Constructor;

public class PyRunnableBootstrap implements CodeBootstrap {
   public static final String REFLECTION_METHOD_NAME = "getFilenameConstructorReflectionBootstrap";
   private final PyRunnable runnable;

   PyRunnableBootstrap(PyRunnable runnable) {
      this.runnable = runnable;
   }

   public PyCode loadCode(CodeLoader loader) {
      if (this.runnable instanceof ContainsPyBytecode) {
         try {
            BytecodeLoader.fixPyBytecode(((ContainsPyBytecode)this.runnable).getClass());
         } catch (NoSuchFieldException var3) {
            throw Py.JavaError(var3);
         } catch (IOException var4) {
            throw Py.JavaError(var4);
         } catch (ClassNotFoundException var5) {
            throw Py.JavaError(var5);
         } catch (IllegalAccessException var6) {
            throw Py.JavaError(var6);
         }
      }

      return this.runnable.getMain();
   }

   public static CodeBootstrap getFilenameConstructorReflectionBootstrap(Class cls) {
      final Constructor constructor;
      try {
         constructor = cls.getConstructor(String.class);
      } catch (Exception var3) {
         throw new IllegalArgumentException("PyRunnable class does not specify apropriate constructor.", var3);
      }

      return new CodeBootstrap() {
         public PyCode loadCode(CodeLoader loader) {
            try {
               PyRunnable result = (PyRunnable)constructor.newInstance(loader.filename);
               if (result instanceof ContainsPyBytecode) {
                  try {
                     BytecodeLoader.fixPyBytecode(((ContainsPyBytecode)result).getClass());
                  } catch (NoSuchFieldException var4) {
                     throw Py.JavaError(var4);
                  } catch (IOException var5) {
                     throw Py.JavaError(var5);
                  } catch (ClassNotFoundException var6) {
                     throw Py.JavaError(var6);
                  } catch (IllegalAccessException var7) {
                     throw Py.JavaError(var7);
                  }
               }

               return result.getMain();
            } catch (Exception var8) {
               throw new IllegalArgumentException("PyRunnable class constructor does not support instantiation protocol.", var8);
            }
         }
      };
   }
}
