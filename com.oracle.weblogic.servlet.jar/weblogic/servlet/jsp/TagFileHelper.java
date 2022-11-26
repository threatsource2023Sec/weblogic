package weblogic.servlet.jsp;

import weblogic.servlet.internal.WebAppServletContext;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;

public class TagFileHelper {
   private static final boolean debug = false;
   private WebAppServletContext context;
   private TagFileClassLoader tagFileClassLoader;
   private long creationTime;
   private String jspWorkingDir;

   public TagFileHelper(WebAppServletContext context) {
      this.context = context;
   }

   public void initClassLoader(ClassFinder finder, ClassLoader parent) {
      this.initClassLoader(finder, parent, false);
   }

   private void initClassLoader(ClassFinder finder, ClassLoader parent, boolean reload) {
      this.tagFileClassLoader = new TagFileClassLoader(finder, parent);
      if (!reload) {
         this.addWorkingDirFinder(this.tagFileClassLoader);
      }

      this.creationTime = this.tagFileClassLoader.getCreationTime();
   }

   public TagFileClassLoader getTagFileClassLoader() {
      return this.tagFileClassLoader;
   }

   public TagFileClassLoader getCompileTimeTagFileClassLoader() {
      ClassFinder finder = this.context.getWarInstance().getClassFinder();
      ClassLoader parent = this.context.getServletClassLoader();
      TagFileClassLoader compilationLoader = new TagFileClassLoader(finder, parent);
      this.addWorkingDirFinder(compilationLoader);
      return compilationLoader;
   }

   long getCLCreationTime() {
      return this.creationTime;
   }

   void reloadIfNecessary(long timeout) {
      if (timeout >= 0L) {
         if (this.checkReloadTimeout(timeout)) {
            synchronized(this) {
               if (this.checkReloadTimeout(timeout) && this.needToReloadCL()) {
                  this.reloadTagFileClassLoader();
               }
            }
         }

      }
   }

   private boolean checkReloadTimeout(long timeout) {
      if (timeout < 0L) {
         return false;
      } else {
         long reloadInterval = timeout * 1000L;
         return System.currentTimeMillis() - reloadInterval > this.tagFileClassLoader.getLastChecked();
      }
   }

   private boolean needToReloadCL() {
      return !this.tagFileClassLoader.upToDate();
   }

   private void reloadTagFileClassLoader() {
      ClassFinder finder = this.context.getWarInstance().getClassFinder();
      ClassLoader parent = this.context.getServletClassLoader();
      this.initClassLoader(finder, parent, false);
   }

   private void addWorkingDirFinder(TagFileClassLoader cl) {
      if (this.jspWorkingDir == null) {
         this.jspWorkingDir = this.context.getJSPManager().getJSPWorkingDir();
      }

      cl.addClassFinderFirst(new ClasspathClassFinder2(this.jspWorkingDir));
   }

   public void close() {
      this.tagFileClassLoader = null;
   }
}
