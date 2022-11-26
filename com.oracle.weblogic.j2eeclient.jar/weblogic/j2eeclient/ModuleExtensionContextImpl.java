package weblogic.j2eeclient;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.naming.Environment;
import weblogic.application.utils.BaseModuleExtensionContext;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;

public final class ModuleExtensionContextImpl extends BaseModuleExtensionContext {
   private final List classFinders;
   private GenericClassLoader classLoader;
   private AppClientModule module;
   ClassInfoFinder classInfoFinder;

   public ModuleExtensionContextImpl(ApplicationContextInternal appCtx, AppClientModule appClientModule, GenericClassLoader cl, ClassFinder classFinder, Environment env) {
      this(appCtx, appCtx.getModuleContext(appClientModule.getId()), appClientModule, cl, classFinder, env, (ClassInfoFinder)null);
   }

   public ModuleExtensionContextImpl(ApplicationContextInternal appCtx, ModuleContext extensibleModule, AppClientModule appClientModule, GenericClassLoader cl, ClassFinder classFinder, Environment env, ClassInfoFinder classInfoFinder) {
      super(appCtx, extensibleModule, appClientModule, env);
      this.classFinders = new LinkedList();
      this.classLoader = null;
      this.module = null;
      this.module = appClientModule;
      this.classLoader = cl;
      this.classFinders.add(classFinder);
      this.classInfoFinder = classInfoFinder;
   }

   public List getSources(String relativeURI) {
      List listOfSources = new LinkedList();
      Iterator var3 = this.classFinders.iterator();

      while(var3.hasNext()) {
         ClassFinder cf = (ClassFinder)var3.next();
         Enumeration e = cf.getSources(relativeURI);

         while(e.hasMoreElements()) {
            listOfSources.add(e.nextElement());
         }
      }

      return listOfSources;
   }

   public final Set getAnnotatedClasses(boolean useTempClassLoader, Class... annos) {
      throw new UnsupportedOperationException("Not implemented yet");
   }

   public GenericClassLoader getClassLoader() {
      return this.classLoader;
   }

   public ClassInfoFinder getClassInfoFinder() {
      return this.classInfoFinder;
   }
}
