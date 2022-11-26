package weblogic.deploy.api.internal.utils;

import java.io.IOException;
import java.util.ArrayList;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.utils.classloaders.ClassFinder;

public class ClassLoaderControl {
   private ClassLoader oldCL;
   private WebLogicDeployableObject wldObject;
   private ArrayList resources;
   private ClassLoader cl;

   protected void finalize() throws Throwable {
      if (this.cl != null) {
         this.close();
      }

   }

   public ClassLoaderControl(WebLogicDeployableObject wldObject, ClassLoader oldCl) {
      this.oldCL = null;
      this.resources = new ArrayList();
      this.cl = null;
      this.wldObject = wldObject;
      this.oldCL = oldCl;
   }

   public ClassLoaderControl(WebLogicDeployableObject wldObject) {
      this(wldObject, Thread.currentThread().getContextClassLoader());
   }

   public ClassLoader getClassLoader() throws IOException {
      if (this.cl == null) {
         this.cl = this.createClassLoader();
      }

      return this.cl;
   }

   private ClassLoader createClassLoader() throws IOException {
      return this.wldObject.getOrCreateGCL();
   }

   public void restoreClassloader() {
      this.close();
      Thread.currentThread().setContextClassLoader(this.oldCL);
   }

   public void close() {
      for(int i = 0; i < this.resources.size(); ++i) {
         ClassFinder finder = (ClassFinder)this.resources.get(i);
         finder.close();
      }

      this.resources.clear();
      this.cl = null;
   }
}
