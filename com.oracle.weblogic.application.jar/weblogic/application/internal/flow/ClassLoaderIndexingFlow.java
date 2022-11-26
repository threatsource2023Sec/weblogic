package weblogic.application.internal.flow;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import weblogic.application.AppClassLoaderManager;
import weblogic.application.ApplicationContextInternal;
import weblogic.management.DeploymentException;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.DirectoryClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;

public class ClassLoaderIndexingFlow extends BaseFlow {
   private static final boolean isIndexModules = Boolean.valueOf(System.getProperty("weblogic.application.classloader.indexModules", "false"));
   private static final boolean isIndexParent = Boolean.valueOf(System.getProperty("weblogic.application.classloader.indexParent", "false"));
   private static final boolean isDebugClassLoaderPrintout = Boolean.valueOf(System.getProperty("weblogic.application.classloader.debugClassLoaderPrintout", "false"));
   private final AppClassLoaderManager appClassLoaderManager = (AppClassLoaderManager)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManager.class, new Annotation[0]);

   public ClassLoaderIndexingFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      super.prepare();
      GenericClassLoader gcl = this.appCtx.getAppClassLoader();
      if (!isIndexParent) {
         gcl.freezeClassFinder();
      } else {
         while(true) {
            gcl.freezeClassFinder();
            ClassLoader parent = gcl.getParent();
            if (parent == null || !(parent instanceof GenericClassLoader)) {
               break;
            }

            gcl = (GenericClassLoader)parent;
         }
      }

      if (isIndexModules) {
         Iterator it = this.appClassLoaderManager.iterateModuleLoaders(this.appCtx.getApplicationId());

         while(it.hasNext()) {
            ((GenericClassLoader)it.next()).freezeClassFinder();
         }
      }

      if (isDebugClassLoaderPrintout && this.isDebugEnabled()) {
         this.debug("GenericClassLoader at end of prepare:\n " + this.classLoaderPrintout());
      }

   }

   public void activate() throws DeploymentException {
      super.activate();
   }

   public void start(String[] uris) throws DeploymentException {
      super.start(uris);
   }

   private String classLoaderPrintout() {
      StringBuilder sb = new StringBuilder();
      sb.append("-----------------------------------------\n");
      sb.append("-----------------------------------------\n");
      sb.append("app:");
      String name = this.appCtx.getApplicationId();
      if (name != null) {
         sb.append(name);
      }

      sb.append("\n-----------------------------------------\n");
      sb.append('\n');
      GenericClassLoader gcl = this.appCtx.getAppClassLoader();
      this.classLoaderPrintout(sb, gcl);
      sb.append("-----------------------------------------\n");
      sb.append("-----------------------------------------\n");
      return sb.toString();
   }

   private void classLoaderPrintout(StringBuilder sb, GenericClassLoader gcl) {
      ClassFinder cf = gcl.getClassFinder();
      sb.append("gcl type:");
      sb.append(gcl.getClass().getName());
      sb.append('\n');
      weblogic.utils.classloaders.Annotation a = gcl.getAnnotation();
      if (a != null) {
         sb.append("annotation:");
         sb.append(a.getAnnotationString());
         sb.append('\n');
      }

      GenericClassLoader alt = gcl.getAltParent();
      if (alt != null) {
         sb.append("alt parent-------------------------------\n");
         this.classLoaderPrintout(sb, alt);
      }

      this.classFinderPrintout(sb, cf, 0);
      sb.append("-----------------------------------------\n");
      ClassLoader parent = gcl.getParent();
      if (parent instanceof GenericClassLoader) {
         this.classLoaderPrintout(sb, (GenericClassLoader)parent);
      }

   }

   private void classFinderPrintout(StringBuilder sb, ClassFinder finder, int depth) {
      int i;
      for(i = 0; i < depth; ++i) {
         sb.append(' ');
      }

      sb.append("finder type:");
      sb.append(finder.getClass().getName());
      if (finder instanceof DirectoryClassFinder || finder instanceof JarClassFinder) {
         sb.append(": ");
         sb.append(finder.getClassPath());
      }

      sb.append('\n');
      if (finder instanceof MultiClassFinder) {
         for(i = 0; i < depth; ++i) {
            sb.append(' ');
         }

         sb.append('[');
         sb.append('\n');
         ClassFinder[] finders = ((MultiClassFinder)finder).getClassFinders();
         if (finders != null) {
            ClassFinder[] var5 = finders;
            int var6 = finders.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               ClassFinder subFinder = var5[var7];
               this.classFinderPrintout(sb, subFinder, depth + 1);
            }
         }

         for(int i = 0; i < depth; ++i) {
            sb.append(' ');
         }

         sb.append(']');
         sb.append('\n');
      }

   }
}
