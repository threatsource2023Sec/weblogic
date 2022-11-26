package com.bea.core.repackaged.springframework.scripting.groovy;

import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scripting.ScriptCompilationException;
import com.bea.core.repackaged.springframework.scripting.ScriptFactory;
import com.bea.core.repackaged.springframework.scripting.ScriptSource;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.Script;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;

public class GroovyScriptFactory implements ScriptFactory, BeanFactoryAware, BeanClassLoaderAware {
   private final String scriptSourceLocator;
   @Nullable
   private GroovyObjectCustomizer groovyObjectCustomizer;
   @Nullable
   private CompilerConfiguration compilerConfiguration;
   @Nullable
   private GroovyClassLoader groovyClassLoader;
   @Nullable
   private Class scriptClass;
   @Nullable
   private Class scriptResultClass;
   @Nullable
   private CachedResultHolder cachedResult;
   private final Object scriptClassMonitor;
   private boolean wasModifiedForTypeCheck;

   public GroovyScriptFactory(String scriptSourceLocator) {
      this.scriptClassMonitor = new Object();
      this.wasModifiedForTypeCheck = false;
      Assert.hasText(scriptSourceLocator, "'scriptSourceLocator' must not be empty");
      this.scriptSourceLocator = scriptSourceLocator;
   }

   public GroovyScriptFactory(String scriptSourceLocator, @Nullable GroovyObjectCustomizer groovyObjectCustomizer) {
      this(scriptSourceLocator);
      this.groovyObjectCustomizer = groovyObjectCustomizer;
   }

   public GroovyScriptFactory(String scriptSourceLocator, @Nullable CompilerConfiguration compilerConfiguration) {
      this(scriptSourceLocator);
      this.compilerConfiguration = compilerConfiguration;
   }

   public GroovyScriptFactory(String scriptSourceLocator, CompilationCustomizer... compilationCustomizers) {
      this(scriptSourceLocator);
      if (!ObjectUtils.isEmpty((Object[])compilationCustomizers)) {
         this.compilerConfiguration = new CompilerConfiguration();
         this.compilerConfiguration.addCompilationCustomizers(compilationCustomizers);
      }

   }

   public void setBeanFactory(BeanFactory beanFactory) {
      if (beanFactory instanceof ConfigurableListableBeanFactory) {
         ((ConfigurableListableBeanFactory)beanFactory).ignoreDependencyType(MetaClass.class);
      }

   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.groovyClassLoader = this.buildGroovyClassLoader(classLoader);
   }

   public GroovyClassLoader getGroovyClassLoader() {
      synchronized(this.scriptClassMonitor) {
         if (this.groovyClassLoader == null) {
            this.groovyClassLoader = this.buildGroovyClassLoader(ClassUtils.getDefaultClassLoader());
         }

         return this.groovyClassLoader;
      }
   }

   protected GroovyClassLoader buildGroovyClassLoader(@Nullable ClassLoader classLoader) {
      return this.compilerConfiguration != null ? new GroovyClassLoader(classLoader, this.compilerConfiguration) : new GroovyClassLoader(classLoader);
   }

   public String getScriptSourceLocator() {
      return this.scriptSourceLocator;
   }

   @Nullable
   public Class[] getScriptInterfaces() {
      return null;
   }

   public boolean requiresConfigInterface() {
      return false;
   }

   @Nullable
   public Object getScriptedObject(ScriptSource scriptSource, @Nullable Class... actualInterfaces) throws IOException, ScriptCompilationException {
      synchronized(this.scriptClassMonitor) {
         try {
            this.wasModifiedForTypeCheck = false;
            Object var10000;
            Object result;
            if (this.cachedResult != null) {
               result = this.cachedResult.object;
               this.cachedResult = null;
               var10000 = result;
               return var10000;
            } else {
               if (this.scriptClass == null || scriptSource.isModified()) {
                  this.scriptClass = this.getGroovyClassLoader().parseClass(scriptSource.getScriptAsString(), scriptSource.suggestedClassName());
                  if (Script.class.isAssignableFrom(this.scriptClass)) {
                     result = this.executeScript(scriptSource, this.scriptClass);
                     this.scriptResultClass = result != null ? result.getClass() : null;
                     var10000 = result;
                     return var10000;
                  }

                  this.scriptResultClass = this.scriptClass;
               }

               Class scriptClassToExecute = this.scriptClass;
               var10000 = this.executeScript(scriptSource, scriptClassToExecute);
               return var10000;
            }
         } catch (CompilationFailedException var7) {
            this.scriptClass = null;
            this.scriptResultClass = null;
            throw new ScriptCompilationException(scriptSource, var7);
         }
      }
   }

   @Nullable
   public Class getScriptedObjectType(ScriptSource scriptSource) throws IOException, ScriptCompilationException {
      synchronized(this.scriptClassMonitor) {
         Class var10000;
         try {
            if (this.scriptClass == null || scriptSource.isModified()) {
               this.wasModifiedForTypeCheck = true;
               this.scriptClass = this.getGroovyClassLoader().parseClass(scriptSource.getScriptAsString(), scriptSource.suggestedClassName());
               if (Script.class.isAssignableFrom(this.scriptClass)) {
                  Object result = this.executeScript(scriptSource, this.scriptClass);
                  this.scriptResultClass = result != null ? result.getClass() : null;
                  this.cachedResult = new CachedResultHolder(result);
               } else {
                  this.scriptResultClass = this.scriptClass;
               }
            }

            var10000 = this.scriptResultClass;
         } catch (CompilationFailedException var5) {
            this.scriptClass = null;
            this.scriptResultClass = null;
            this.cachedResult = null;
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

   @Nullable
   protected Object executeScript(ScriptSource scriptSource, Class scriptClass) throws ScriptCompilationException {
      try {
         GroovyObject goo = (GroovyObject)ReflectionUtils.accessibleConstructor(scriptClass).newInstance();
         if (this.groovyObjectCustomizer != null) {
            this.groovyObjectCustomizer.customize(goo);
         }

         return goo instanceof Script ? ((Script)goo).run() : goo;
      } catch (NoSuchMethodException var4) {
         throw new ScriptCompilationException("No default constructor on Groovy script class: " + scriptClass.getName(), var4);
      } catch (InstantiationException var5) {
         throw new ScriptCompilationException(scriptSource, "Unable to instantiate Groovy script class: " + scriptClass.getName(), var5);
      } catch (IllegalAccessException var6) {
         throw new ScriptCompilationException(scriptSource, "Could not access Groovy script constructor: " + scriptClass.getName(), var6);
      } catch (InvocationTargetException var7) {
         throw new ScriptCompilationException("Failed to invoke Groovy script constructor: " + scriptClass.getName(), var7.getTargetException());
      }
   }

   public String toString() {
      return "GroovyScriptFactory: script source locator [" + this.scriptSourceLocator + "]";
   }

   private static class CachedResultHolder {
      @Nullable
      public final Object object;

      public CachedResultHolder(@Nullable Object object) {
         this.object = object;
      }
   }
}
