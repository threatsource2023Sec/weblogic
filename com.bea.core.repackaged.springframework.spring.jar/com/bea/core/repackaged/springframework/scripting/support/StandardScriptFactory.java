package com.bea.core.repackaged.springframework.scripting.support;

import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scripting.ScriptCompilationException;
import com.bea.core.repackaged.springframework.scripting.ScriptFactory;
import com.bea.core.repackaged.springframework.scripting.ScriptSource;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class StandardScriptFactory implements ScriptFactory, BeanClassLoaderAware {
   @Nullable
   private final String scriptEngineName;
   private final String scriptSourceLocator;
   @Nullable
   private final Class[] scriptInterfaces;
   @Nullable
   private ClassLoader beanClassLoader;
   @Nullable
   private volatile ScriptEngine scriptEngine;

   public StandardScriptFactory(String scriptSourceLocator) {
      this((String)null, scriptSourceLocator, (Class[])null);
   }

   public StandardScriptFactory(String scriptSourceLocator, Class... scriptInterfaces) {
      this((String)null, scriptSourceLocator, scriptInterfaces);
   }

   public StandardScriptFactory(String scriptEngineName, String scriptSourceLocator) {
      this(scriptEngineName, scriptSourceLocator, (Class[])null);
   }

   public StandardScriptFactory(@Nullable String scriptEngineName, String scriptSourceLocator, @Nullable Class... scriptInterfaces) {
      this.beanClassLoader = ClassUtils.getDefaultClassLoader();
      Assert.hasText(scriptSourceLocator, "'scriptSourceLocator' must not be empty");
      this.scriptEngineName = scriptEngineName;
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
      return false;
   }

   @Nullable
   public Object getScriptedObject(ScriptSource scriptSource, @Nullable Class... actualInterfaces) throws IOException, ScriptCompilationException {
      Object script = this.evaluateScript(scriptSource);
      if (!ObjectUtils.isEmpty((Object[])actualInterfaces)) {
         boolean adaptationRequired = false;
         Class[] var5 = actualInterfaces;
         int var6 = actualInterfaces.length;
         int var7 = 0;

         label43: {
            while(true) {
               if (var7 >= var6) {
                  break label43;
               }

               Class requestedIfc = var5[var7];
               if (script instanceof Class) {
                  if (!requestedIfc.isAssignableFrom((Class)script)) {
                     break;
                  }
               } else if (!requestedIfc.isInstance(script)) {
                  break;
               }

               ++var7;
            }

            adaptationRequired = true;
         }

         if (adaptationRequired) {
            script = this.adaptToInterfaces(script, scriptSource, actualInterfaces);
         }
      }

      if (script instanceof Class) {
         Class scriptClass = (Class)script;

         try {
            return ReflectionUtils.accessibleConstructor(scriptClass).newInstance();
         } catch (NoSuchMethodException var9) {
            throw new ScriptCompilationException("No default constructor on script class: " + scriptClass.getName(), var9);
         } catch (InstantiationException var10) {
            throw new ScriptCompilationException(scriptSource, "Unable to instantiate script class: " + scriptClass.getName(), var10);
         } catch (IllegalAccessException var11) {
            throw new ScriptCompilationException(scriptSource, "Could not access script constructor: " + scriptClass.getName(), var11);
         } catch (InvocationTargetException var12) {
            throw new ScriptCompilationException("Failed to invoke script constructor: " + scriptClass.getName(), var12.getTargetException());
         }
      } else {
         return script;
      }
   }

   protected Object evaluateScript(ScriptSource scriptSource) {
      try {
         ScriptEngine scriptEngine = this.scriptEngine;
         if (scriptEngine == null) {
            scriptEngine = this.retrieveScriptEngine(scriptSource);
            if (scriptEngine == null) {
               throw new IllegalStateException("Could not determine script engine for " + scriptSource);
            }

            this.scriptEngine = scriptEngine;
         }

         return scriptEngine.eval(scriptSource.getScriptAsString());
      } catch (Exception var3) {
         throw new ScriptCompilationException(scriptSource, var3);
      }
   }

   @Nullable
   protected ScriptEngine retrieveScriptEngine(ScriptSource scriptSource) {
      ScriptEngineManager scriptEngineManager = new ScriptEngineManager(this.beanClassLoader);
      if (this.scriptEngineName != null) {
         return StandardScriptUtils.retrieveEngineByName(scriptEngineManager, this.scriptEngineName);
      } else {
         if (scriptSource instanceof ResourceScriptSource) {
            String filename = ((ResourceScriptSource)scriptSource).getResource().getFilename();
            if (filename != null) {
               String extension = StringUtils.getFilenameExtension(filename);
               if (extension != null) {
                  ScriptEngine engine = scriptEngineManager.getEngineByExtension(extension);
                  if (engine != null) {
                     return engine;
                  }
               }
            }
         }

         return null;
      }
   }

   @Nullable
   protected Object adaptToInterfaces(@Nullable Object script, ScriptSource scriptSource, Class... actualInterfaces) {
      Class adaptedIfc;
      if (actualInterfaces.length == 1) {
         adaptedIfc = actualInterfaces[0];
      } else {
         adaptedIfc = ClassUtils.createCompositeInterface(actualInterfaces, this.beanClassLoader);
      }

      if (adaptedIfc != null) {
         ScriptEngine scriptEngine = this.scriptEngine;
         if (!(scriptEngine instanceof Invocable)) {
            throw new ScriptCompilationException(scriptSource, "ScriptEngine must implement Invocable in order to adapt it to an interface: " + scriptEngine);
         }

         Invocable invocable = (Invocable)scriptEngine;
         if (script != null) {
            script = invocable.getInterface(script, adaptedIfc);
         }

         if (script == null) {
            script = invocable.getInterface(adaptedIfc);
            if (script == null) {
               throw new ScriptCompilationException(scriptSource, "Could not adapt script to interface [" + adaptedIfc.getName() + "]");
            }
         }
      }

      return script;
   }

   @Nullable
   public Class getScriptedObjectType(ScriptSource scriptSource) throws IOException, ScriptCompilationException {
      return null;
   }

   public boolean requiresScriptedObjectRefresh(ScriptSource scriptSource) {
      return scriptSource.isModified();
   }

   public String toString() {
      return "StandardScriptFactory: script source locator [" + this.scriptSourceLocator + "]";
   }
}
