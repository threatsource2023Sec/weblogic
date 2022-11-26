package weblogic.ant.taskdefs.build.module;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.tools.ant.taskdefs.Javac;

final class JavacCloner {
   private AccessorMethod[] methods;
   private static JavacCloner theOne = null;

   static synchronized JavacCloner getJavaCloner() {
      if (theOne == null) {
         theOne = new JavacCloner();
      }

      return theOne;
   }

   private JavacCloner() {
      List mList = new ArrayList();
      Method[] pubMethods = Javac.class.getMethods();
      if (pubMethods != null) {
         for(int i = 0; i < pubMethods.length; ++i) {
            String methodName = pubMethods[i].getName();
            if (methodName.startsWith("get")) {
               String setMethodName = "s" + methodName.substring(1);

               try {
                  Method setMethod = Javac.class.getMethod(setMethodName, pubMethods[i].getReturnType());
                  mList.add(new AccessorMethod(pubMethods[i], setMethod));
               } catch (NoSuchMethodException var7) {
               }
            }
         }
      }

      this.methods = new AccessorMethod[mList.size()];
      this.methods = (AccessorMethod[])((AccessorMethod[])mList.toArray(this.methods));
   }

   public void copy(Javac from, Javac to) {
      for(int i = 0; i < this.methods.length; ++i) {
         this.methods[i].copy(from, to);
      }

      this.copyCompilerArgs(from, to);
   }

   private void copyCompilerArgs(Javac from, Javac to) {
      try {
         Method create = Javac.class.getMethod("createCompilerArg", (Class[])null);
         Method get = Javac.class.getMethod("getCurrentCompilerArgs", (Class[])null);
         Object o = get.invoke(from, (Object[])null);
         if (o == null) {
            return;
         }

         Object[] val = (Object[])((Object[])o);

         for(int i = 0; i < val.length; ++i) {
            Object arg = create.invoke(to, (Object[])null);
            Method set = arg.getClass().getMethod("setLine", String.class);
            set.invoke(arg, String.valueOf(val[i]));
         }
      } catch (Throwable var10) {
         System.err.println("Error processing compilerarg elements, set sys prop weblogic.debug.wlcompile for stacktrace");
         if (Boolean.getBoolean("weblogic.debug.wlcompile")) {
            throw new AssertionError(var10);
         }
      }

   }

   private static class AccessorMethod {
      private final Method getMethod;
      private final Method setMethod;

      AccessorMethod(Method getMethod, Method setMethod) {
         this.getMethod = getMethod;
         this.setMethod = setMethod;
      }

      public void copy(Object from, Object to) {
         try {
            Object val = this.getMethod.invoke(from, (Object[])null);
            this.setMethod.invoke(to, val);
         } catch (Exception var4) {
            throw new AssertionError(var4);
         }
      }
   }
}
