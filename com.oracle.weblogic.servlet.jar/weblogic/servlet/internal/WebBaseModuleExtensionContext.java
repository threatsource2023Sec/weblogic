package weblogic.servlet.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Extensible;
import weblogic.application.ModuleContext;
import weblogic.application.naming.Environment;
import weblogic.application.utils.BaseModuleExtensionContext;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;

public abstract class WebBaseModuleExtensionContext extends BaseModuleExtensionContext {
   private GenericClassLoader temporaryClassLoader = null;

   public WebBaseModuleExtensionContext(ApplicationContextInternal appCtx, ModuleContext modCtx, Extensible extensibleModule, Environment env) {
      super(appCtx, modCtx, extensibleModule, env);
   }

   protected abstract ClassFinder getResourceFinder(String var1);

   protected abstract ClassLoader getClassLoader();

   public abstract WebAppHelper getWebAppHelper();

   public final List getSources(String relativeURI) {
      ClassFinder resourceFinder = this.getResourceFinder(relativeURI);
      Enumeration e = resourceFinder.getSources(relativeURI);
      if (e == null) {
         return null;
      } else {
         List sources = new ArrayList();

         while(e.hasMoreElements()) {
            Source s = (Source)e.nextElement();
            sources.add(s);
         }

         return sources;
      }
   }

   public final Set getAnnotatedClasses(boolean useTempClassLoader, Class... annos) {
      if (annos != null && annos.length != 0) {
         ClassLoader cl = useTempClassLoader ? this.getTemporaryClassLoader() : this.getClassLoader();
         WebAppHelper helper = this.getWebAppHelper();
         List annoNames = new ArrayList();
         Class[] var6 = annos;
         int var7 = annos.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Class annotation = var6[var8];
            annoNames.add(annotation.getName());
         }

         Set annotatedClassNames = helper.getAnnotatedClasses((String[])annoNames.toArray(new String[0]));
         Set annoClasses = new HashSet();
         Iterator var14 = annotatedClassNames.iterator();

         while(var14.hasNext()) {
            String className = (String)var14.next();

            try {
               Class clazz = ((ClassLoader)cl).loadClass(className);
               annoClasses.add(clazz);
            } catch (ClassNotFoundException var11) {
            }
         }

         return annoClasses;
      } else {
         return Collections.emptySet();
      }
   }

   public ClassInfoFinder getClassInfoFinder() {
      return this.getWebAppHelper().getClassInfoFinder();
   }
}
