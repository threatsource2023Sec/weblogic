package com.sun.faces.scripting;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import groovy.util.GroovyScriptEngine;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;

class GroovyHelperImpl extends GroovyHelper {
   private static final Logger LOGGER;
   private static final String SCRIPT_PATH = "/WEB-INF/groovy/";
   private MojarraGroovyClassLoader loader;

   GroovyHelperImpl() throws Exception {
      FacesContext ctx = FacesContext.getCurrentInstance();
      URL u = ctx.getExternalContext().getResource("/WEB-INF/groovy/");
      if (u != null) {
         final GroovyScriptEngine engine = new GroovyScriptEngine(new URL[]{u}, Thread.currentThread().getContextClassLoader());
         this.loader = (MojarraGroovyClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
            public MojarraGroovyClassLoader run() {
               return new MojarraGroovyClassLoader(engine);
            }
         });
         if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Groovy support enabled.");
         }

         ctx.getExternalContext().getApplicationMap().put("com.sun.faces.groovyhelper", this);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public Class loadScript(String name) {
      try {
         String script = name;
         if (name.endsWith(".groovy")) {
            script = name.substring(0, name.indexOf(".groovy"));
         }

         return Util.loadClass(script, this);
      } catch (Exception var3) {
         throw new FacesException(var3);
      }
   }

   public void setClassLoader() {
      Thread.currentThread().setContextClassLoader(this.loader);
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }

   private static final class MojarraGroovyClassLoader extends URLClassLoader {
      private GroovyScriptEngine gse;

      public MojarraGroovyClassLoader(GroovyScriptEngine gse) {
         super(new URL[0], gse.getGroovyClassLoader());
         this.gse = gse;
      }

      public Class loadClass(String name) throws ClassNotFoundException {
         if (name == null) {
            throw new NullPointerException();
         } else {
            Class c;
            try {
               c = this.gse.getGroovyClassLoader().getParent().loadClass(name);
            } catch (ClassNotFoundException var6) {
               try {
                  c = this.gse.loadScriptByName(name);
               } catch (Exception var5) {
                  throw new ClassNotFoundException(name, var5);
               }
            }

            if (c == null) {
               throw new ClassNotFoundException(name);
            } else {
               return c;
            }
         }
      }
   }
}
