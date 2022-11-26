package com.bea.core.repackaged.springframework.scripting.bsh;

import bsh.EvalError;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scripting.ScriptCompilationException;
import com.bea.core.repackaged.springframework.scripting.ScriptFactory;
import com.bea.core.repackaged.springframework.scripting.ScriptSource;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.io.IOException;

public class BshScriptFactory implements ScriptFactory, BeanClassLoaderAware {
   private final String scriptSourceLocator;
   @Nullable
   private final Class[] scriptInterfaces;
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
   @Nullable
   private Class scriptClass;
   private final Object scriptClassMonitor = new Object();
   private boolean wasModifiedForTypeCheck = false;

   public BshScriptFactory(String scriptSourceLocator) {
      Assert.hasText(scriptSourceLocator, "'scriptSourceLocator' must not be empty");
      this.scriptSourceLocator = scriptSourceLocator;
      this.scriptInterfaces = null;
   }

   public BshScriptFactory(String scriptSourceLocator, @Nullable Class... scriptInterfaces) {
      Assert.hasText(scriptSourceLocator, "'scriptSourceLocator' must not be empty");
      this.scriptSourceLocator = scriptSourceLocator;
      this.scriptInterfaces = scriptInterfaces;
   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
   }

   public String getScriptSourceLocator() {
      return this.scriptSourceLocator;
   }

   @Nullable
   public Class[] getScriptInterfaces() {
      return this.scriptInterfaces;
   }

   public boolean requiresConfigInterface() {
      return true;
   }

   @Nullable
   public Object getScriptedObject(ScriptSource scriptSource, @Nullable Class... actualInterfaces) throws IOException, ScriptCompilationException {
      Class clazz;
      try {
         synchronized(this.scriptClassMonitor) {
            boolean requiresScriptEvaluation = this.wasModifiedForTypeCheck && this.scriptClass == null;
            this.wasModifiedForTypeCheck = false;
            if (scriptSource.isModified() || requiresScriptEvaluation) {
               Object result = BshScriptUtils.evaluateBshScript(scriptSource.getScriptAsString(), actualInterfaces, this.beanClassLoader);
               if (!(result instanceof Class)) {
                  return result;
               }

               this.scriptClass = (Class)result;
            }

            clazz = this.scriptClass;
         }
      } catch (EvalError var11) {
         this.scriptClass = null;
         throw new ScriptCompilationException(scriptSource, var11);
      }

      if (clazz != null) {
         try {
            return ReflectionUtils.accessibleConstructor(clazz).newInstance();
         } catch (Throwable var8) {
            throw new ScriptCompilationException(scriptSource, "Could not instantiate script class: " + clazz.getName(), var8);
         }
      } else {
         try {
            return BshScriptUtils.createBshObject(scriptSource.getScriptAsString(), actualInterfaces, this.beanClassLoader);
         } catch (EvalError var9) {
            throw new ScriptCompilationException(scriptSource, var9);
         }
      }
   }

   @Nullable
   public Class getScriptedObjectType(ScriptSource scriptSource) throws IOException, ScriptCompilationException {
      synchronized(this.scriptClassMonitor) {
         Class var10000;
         try {
            if (scriptSource.isModified()) {
               this.wasModifiedForTypeCheck = true;
               this.scriptClass = BshScriptUtils.determineBshObjectType(scriptSource.getScriptAsString(), this.beanClassLoader);
            }

            var10000 = this.scriptClass;
         } catch (EvalError var5) {
            this.scriptClass = null;
            throw new ScriptCompilationException(scriptSource, var5);
         }

         return var10000;
      }
   }

   public boolean requiresScriptedObjectRefresh(ScriptSource scriptSource) {
      synchronized(this.scriptClassMonitor) {
         return scriptSource.isModified() || this.wasModifiedForTypeCheck;
      }
   }

   public String toString() {
      return "BshScriptFactory: script source locator [" + this.scriptSourceLocator + "]";
   }
}
