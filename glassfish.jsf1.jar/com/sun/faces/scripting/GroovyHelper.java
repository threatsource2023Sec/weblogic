package com.sun.faces.scripting;

import com.sun.faces.util.Util;
import java.lang.reflect.Constructor;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public abstract class GroovyHelper {
   public static GroovyHelper getCurrentInstance(FacesContext ctx) {
      return (GroovyHelper)ctx.getExternalContext().getApplicationMap().get("com.sun.faces.groovyhelper");
   }

   public static GroovyHelper getCurrentInstance(ServletContext sc) {
      return (GroovyHelper)sc.getAttribute("com.sun.faces.groovyhelper");
   }

   public static GroovyHelper getCurrentInstance() {
      FacesContext ctx = FacesContext.getCurrentInstance();
      return ctx != null ? getCurrentInstance(ctx) : null;
   }

   public abstract Class loadScript(String var1);

   public static Object newInstance(String name, Class type, Object root) throws Exception {
      Class delegate = Util.loadClass(name, GroovyHelper.class);

      try {
         Constructor decorationCtor = requiresDecoration(delegate, type, root);
         return decorationCtor != null ? decorationCtor.newInstance(root) : delegate.newInstance();
      } catch (Exception var5) {
         throw new FacesException(var5);
      }
   }

   public static Object newInstance(String name) throws Exception {
      return newInstance(name, (Class)null, (Object)null);
   }

   public abstract void setClassLoader();

   private static Constructor requiresDecoration(Class groovyClass, Class ctorArgument, Object root) {
      if (root != null) {
         try {
            return groovyClass.getConstructor(ctorArgument);
         } catch (Exception var4) {
         }
      }

      return null;
   }
}
