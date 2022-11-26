package com.bea.wls.redef;

import com.bea.wls.redef.agent.ClassRedefiner;
import com.bea.wls.redef.filter.ClassMetaDataFilterFactory;
import com.bea.wls.redef.filter.DefaultMetaDataFilterFactory;
import com.bea.wls.redef.filter.OpenJPAMetaDataFilterFactory;
import com.bea.wls.redef.io.ClassChangeNotifier;
import java.lang.instrument.ClassDefinition;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.classloaders.AbstractClassFinder;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;

public class RedefiningClassLoader extends GenericClassLoader {
   private static final DebugLogger DEBUG;
   private final MetaDataRepository repos;
   private final ConcurrentHashMap cachedClasses = new ConcurrentHashMap();
   private final ClassChangeNotifier notifier;
   private final ClassRedefinitionAccess classRedefinitionAccess = new ClassRedefinitionAccess(this);
   private final InMemoryClassFinder inMemoryFinder = new InMemoryClassFinder();
   private ClassRedefinitionRuntime _redefinitionRuntime;
   private final RedefinableClassFileTransformer _transformer;

   public RedefiningClassLoader(ClassFinder finder, ClassLoader parent, ClassChangeNotifier n) {
      super(finder, parent);
      this.notifier = n;
      this.repos = new MetaDataRepository(this);
      this.repos.setMetaDataFilterFactory(OpenJPAMetaDataFilterFactory.OPENJPA_FACTORY);
      this.repos.addMetaDataFilterFactory(new DefaultMetaDataFilterFactory());
      this._transformer = new RedefinableClassFileTransformer(this.repos, this);
      this.addClassFinder(new AbstractClassFinder() {
         public Source getSource(String name) {
            return null;
         }

         public void close() {
            ClassRedefiner.getInstrumentation().removeTransformer(RedefiningClassLoader.this._transformer);
            RedefiningClassLoader.this.notifier.close();
            RedefiningClassLoader.this.repos.clear();
            RedefiningClassLoader.this.cachedClasses.clear();
         }

         public String getClassPath() {
            return "";
         }
      });
      this.addClassFinder(this.inMemoryFinder);
      this.getRedefinitionRuntime().registerClassLoader(this);
      ClassRedefiner.getInstrumentation().addTransformer(this._transformer);
   }

   public synchronized ClassRedefinitionRuntime getRedefinitionRuntime() {
      if (this._redefinitionRuntime == null) {
         ClassLoader parent = this.getParent();
         if (parent != null && parent instanceof RedefiningClassLoader) {
            this._redefinitionRuntime = ((RedefiningClassLoader)parent).getRedefinitionRuntime();
         }

         if (this._redefinitionRuntime == null) {
            this._redefinitionRuntime = new ClassRedefinitionRuntime();
         }
      }

      return this._redefinitionRuntime;
   }

   synchronized void setRedefinitionRuntime(ClassRedefinitionRuntime rt) {
      this._redefinitionRuntime = rt;
   }

   public Class findClass(String name) throws ClassNotFoundException {
      Class res = (Class)this.cachedClasses.get(name);
      if (res != null) {
         return res;
      } else {
         res = super.findClass(name);
         this.cachedClasses.put(name, res);
         Source source = this.getClassFinder().getClassSource(name);
         if (source != null) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("findClass() -> name '" + name + "' found source " + source);
            }

            this.notifier.updateCache(name, source);
         }

         return res;
      }
   }

   void updateMetaData(List classDefs) {
      if (classDefs != null && !classDefs.isEmpty()) {
         ClassDefinition def;
         String clsName;
         for(Iterator var2 = classDefs.iterator(); var2.hasNext(); this.repos.defineMetaData(clsName, def.getDefinitionClass(), def.getDefinitionClassFile())) {
            def = (ClassDefinition)var2.next();
            clsName = def.getDefinitionClass().getName();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("updateMetaData -> name '" + clsName + "'");
            }
         }

      }
   }

   public final ClassRedefinitionAccess getClassRedefinitionAccess() {
      return this.classRedefinitionAccess;
   }

   public void addGeneratedSource(String name, byte[] bytes) {
      this.inMemoryFinder.addSource(name, bytes);
   }

   public Map scanForUpdates(Set candidates) {
      return this.notifier.scanForUpdates(candidates);
   }

   public void setExcludedClasses(Set classes) {
      ClassMetaDataFilterFactory factory = new ClassMetaDataFilterFactory(classes);
      this.repos.addMetaDataFilterFactory(factory);
   }

   MetaDataRepository getMetaDataRepository() {
      return this.repos;
   }

   static {
      ClassLoader.registerAsParallelCapable();
      DEBUG = DebugLogger.getDebugLogger("RedefiningClassLoader");
   }
}
